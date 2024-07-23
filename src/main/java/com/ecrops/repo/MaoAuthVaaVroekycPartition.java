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
@Repository
@Transactional
public class MaoAuthVaaVroekycPartition {
	

	@PersistenceContext
	private EntityManager entityManager;

	public List<MaoAuthVaaVroekyc> vaaVroEkyc(String wbdcode, String mcode,String cropyear,String cropid) {
		System.out.println("wbdcode====>"+wbdcode);
		System.out.println("mcode====>"+mcode);
		System.out.println("cropyear====>"+cropyear);
		System.out.println("cropid====>"+cropid);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "",sql="",tableName="";
		
			part_key = seasonType + seasonYear;
		if(seasonYear >= 2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
		 tableName = "ecrop" + seasonYear + "." + "cr_authdetails_rbk_mv_cropwise_" + part_key;
		}else {
			 tableName = "cr_authdetails_rbk_mv_cropwise_" + part_key;	
		}
		System.out.println("tableName---------------->" + tableName);

	
	 sql="  select vname,updatedby,totalbookings,totextent,vaaauthcount,vaaauthextent,coalesce(vroauthcount , 0) as vroauthcount, coalesce(vroauthextent,0) as vroauthextent,\r\n"
				+ "totekycbookings,totfarmercount,ekycfarmercount,coalesce(ekycbookedext,0) as ekycbookedext,cr_crop,rbkname  from "+tableName+" \r\n"
				+ "where cr_dist_code =? and mcode = ? and cr_crop = ? ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, Integer.parseInt(wbdcode));
		sesnyr.setParameter(2, Integer.parseInt(mcode));
		sesnyr.setParameter(3, Integer.parseInt(cropid));
//		sesnyr.setParameter(3,  seasonYear);
//		sesnyr.setParameter(4, seasonType);
		
		System.out.println("sesnyr=>"+sesnyr);
		List<Object[]> ekycMao = sesnyr.getResultList();
		//System.out.println("ekycMao=>"+ekycMao.size());
		//System.out.println("ekycMao=>"+ekycMao.toString());
		List<MaoAuthVaaVroekyc> entityDetails = new ArrayList<MaoAuthVaaVroekyc>();
		

		try {
			for (Object[] row : ekycMao) {

				MaoAuthVaaVroekyc entity = new MaoAuthVaaVroekyc();
				
				//System.out.println("row[0]=>"+row[0]);
				//System.out.println("row[1]=>"+row[1]);
				
				entity.setVname((String) row[0]);
				entity.setUpdatedby((String) row[1]);
				entity.setTotalbookings(Long.valueOf(row[2].toString()));
				entity.setTotextent(((BigDecimal) row[3]).intValue());
				entity.setVaaauthcount(Long.valueOf(row[4].toString()));
				entity.setVaaauthextent(((BigDecimal) row[5]).intValue());
				entity.setVroauthcount(Long.valueOf(row[6].toString()));
				entity.setVroauthextent(((BigDecimal) row[7]).intValue());
				entity.setTotekycbookings(Long.valueOf(row[8].toString()));
				entity.setTotfarmercount(Long.valueOf(row[9].toString()));
				entity.setEkycfarmercount(Long.valueOf(row[10].toString()));
				entity.setEkycbookedext(((BigDecimal) row[11]).intValue());
				entity.setRbkname((String) row[13]);
				entityDetails.add(entity);

			}
			
			return entityDetails;
		} catch (NumberFormatException e) {
			System.out.println("Exception===>"+e);
			e.printStackTrace();
		}
		return entityDetails;

	}

}
