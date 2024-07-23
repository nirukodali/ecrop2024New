package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.MaoAuthVaaVroekyc;
import com.ecrops.entity.RbkSurveyNoMapping;

@Repository
@Transactional
public class RbkSurveyNoMappingPartition {
	
	

	@PersistenceContext
	private EntityManager entityManager;

	public List<RbkSurveyNoMapping> rbkSno(String wbdcode, String mcode,String cropyear) {
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "",tableName1="",tableName2="";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + Integer.parseInt(wbdcode) + seasonYear;
		} else {
			part_key = seasonType + "0" + Integer.parseInt(wbdcode) + seasonYear;
		}
		
		if(seasonYear>=2023 && !(seasonYear== 2023 && seasonType.equalsIgnoreCase("S"))) {
			 tableName1 = "ecrop" + seasonYear + "." + "cr_booking_partition_" + part_key;
			 tableName2 = "ecrop" + seasonYear + "." + "rbk_surveyno_mapping_" + part_key;
		}
		else {
			 tableName1 = "cr_booking_partition_" + part_key;
			 tableName2 = "rbk_surveyno_mapping_" + part_key;
		}
		

		System.out.println("tableName1---------------->" + tableName1);
		System.out.println("tableName2---------------->" + tableName2);

		

	

//		String sql="  select rbkuserid,mao_alloted_ext,vaa_alloted_ext,occupant_extent from (select rbkuserid,round(sum(tot_extent),2) as mao_alloted_ext from \r\n"
//		 		+ " (select distinct mcode,vcode,rbkuserid,kh_no,cr_sno,updatedby,tot_extent from "+tableName2+" where updatedby=?  \r\n"
//		 		+ "  order by updatedby,vcode,cr_sno) a1 group by rbkuserid) a, (select srno_userid,round(sum(tot_extent),2) as vaa_alloted_ext,  \r\n"
//		 		+ "round(sum(occupant_extent),2) as occupant_extent from (select distinct cr_vcode,cr_sno,srno_userid,tot_extent,occupant_extent \r\n"
//		 		+ " from "+tableName1+" where mcode=?) b  group by srno_userid) b where rbkuserid=? ";
//		
		
//		String sql="select  y.vname,srno_userid,sum(mao_allotted_ext) as mao_allotted_ext,sum(vaa_selected_ext) as vaa_allotted_ext from(\r\n"
//				+ "(select sum (occup_extent)  as mao_allotted_ext ,vcode,rbkuserid,cr_sno,mcode \r\n"
//				+ " from "+tableName2+" where mcode=? group by  mcode,vcode,cr_sno,rbkuserid) a \r\n"
//				+ "right join \r\n"
//				+ "(select sum (occupant_extent) as vaa_selected_ext,cr_vcode,srno_userid,cr_sno,mcode from \r\n"
//				+ " "+tableName1+" where srno_userid is not null and cultivator_type is not null and mcode=? \r\n"
//				+ " group by mcode,cr_vcode,cr_sno,srno_userid) b\r\n"
//				+ " on a.vcode=b.cr_vcode and a.rbkuserid=b.srno_userid and a.cr_sno=b.cr_sno)x ,vill_sec_det y where x.srno_userid='RBK_'||cast(y.vcode as varchar) \r\n" 
//				+"group by vname,srno_userid";
		
		String sql=" select b.vsname as vname,b.wbvname,sum (occup_extent)  as mao_allotted_ext , sum (occup_extent) filter\r\n"
				+ " (where vs_sel='Y') as vaa_selected_ext,a.vcode,rbkuserid ,a.mcode \r\n"
				+ " from "+tableName2+" a, villsec_rev_v b \r\n"
				+ " where a.rbkcode=b.vscode and a.vcode=b.vcode\r\n"
				+ " and a.mcode=? group by  a.mcode,a.vcode,rbkuserid ,b.vsname,b.wbvname";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, Integer.parseInt(mcode));
//		sesnyr.setParameter(2, Integer.parseInt(mcode));
		
//		sesnyr.setParameter(3,  seasonYear);
//		sesnyr.setParameter(4, seasonType);
		
		System.out.println("sesnyr=>"+sesnyr);
		List<Object[]> ekycMao = sesnyr.getResultList();
		//System.out.println("ekycMao=>"+ekycMao.size());
		//System.out.println("ekycMao=>"+ekycMao.toString());
		List<RbkSurveyNoMapping> entityDetails = new ArrayList<RbkSurveyNoMapping>();
		

		for (Object[] row : ekycMao) {

			RbkSurveyNoMapping entity = new RbkSurveyNoMapping();
			entity.setVname((String) row[0]);
			entity.setWbvname((String) row[1]);
			entity.setMao_allotted_ext(((BigDecimal) row[2]));
			entity.setVaa_allotted_ext(((BigDecimal) row[3]));
			//entity.setOccupant_extent(((BigDecimal) row[3]));
			entityDetails.add(entity);

		}
		
		return entityDetails;
	
	}

}
