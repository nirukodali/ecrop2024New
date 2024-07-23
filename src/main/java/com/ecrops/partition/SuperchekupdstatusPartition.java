package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Superchekupdstatus;

@Repository
@Transactional
public class SuperchekupdstatusPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Superchekupdstatus> getSupChkUpdSts(String wbdcode, String wbmcode, String cropyear ){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		
		String part_key = "";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;	 
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear; 
		}
		 String tableName;
		String tableName1;
		if(seasonYear>=2023) {
		 tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key;
		 tableName1 = "ecrop" + seasonYear + "." + "supercheck_upd";
		 }else {
			  tableName = "cr_details_" + part_key;
			  tableName1 = "supercheck_upd";
		 }
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbdname,wbmname,wbvname,x.bookingid,occup_name,occup_fname, cropname,"
				+ "varietyname, cast(x.cr_sow_date as varchar) ,kh_no, cr_sno,\r\n"
				+ " cast(supercheck_userid as varchar),cast(case when oremarks='A' then 'Entry Found Correct'\r\n"
				+ " when  oremarks='R' then 'Entry Found Incorrect'  end as varchar) as remarks,reason,vaa_sup_rem,mao_remarks,mro_remarks,\r\n"
				+ " oremarks , cr_no from  ((select  DISTINCT cr_crop,variety,cr_year,cr_season,\r\n"
				+ "substring(supercheck_userid,1,3) as supercheck_userid, superchk_remarks as oremarks,occupname as occup_name,occupfname ,\r\n"
				+ "cr_dist_code,cr_mand_code,cr_vcode,bookingid,    occupfname as occup_fname,a.kh_no,a.cr_sno,a.cr_sow_date ,a.cr_no,wbdname,\r\n"
				+ "wbmname,wbvname,cropname,varietyname,reason,    case when superchk_remarks='A' then 'Entry Found Correct' when    \r\n"
				+ "superchk_remarks='R' then 'Entry Found Incorrect'  end as remarks  from "+tableName+" a,wbvillage_mst b ,\r\n"
				+ "cropnames c ,  cr_variety_master v,authority_verify_reasons ar  where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.\r\n"
				+ "wbmcode and a.cr_vcode=b.wbvcode and a.cr_crop=c.cropid and a.variety=v.varietycode  and  a.suprejreason = cast(ar.code as text) \r\n"
				+ "and a.cr_dist_code=? and a.cr_mand_code=?  and cr_year=? and cr_season=? ) x left join(select cr_crop_sug,variety_sug,\r\n"
				+ "cr_sow_date,vaa_sup_rem,mro_remarks,mao_remarks,cr_dist_code,cR_mand_code,cr_vcode ,bookingid,cropyear,season from \r\n"
				+ " "+tableName1+" where cr_dist_code=? and cr_mand_code=?  and cropyear=? and season=?) y    \r\n"
				+ "on x.cr_dist_code=y.cr_dist_code  and x.cr_mand_code=y.cr_mand_code and x.cr_vcode=y.cr_vcode and x.bookingid=y.bookingid \r\n"
				+ "and x.cr_year=y.cropyear and x.cr_season=y.season  )  order by wbmname,wbvname";

		System.out.println("Sql==============>"+Sql);
		Query insertQuery =  (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		insertQuery.setParameter(3, seasonYear);
		insertQuery.setParameter(4, seasonType);
		insertQuery.setParameter(5, Integer.parseInt(wbdcode));
		insertQuery.setParameter(6, Integer.parseInt(wbmcode));
		insertQuery.setParameter(7, seasonYear);
		insertQuery.setParameter(8, seasonType);
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<Superchekupdstatus> detailsEntities = new ArrayList<Superchekupdstatus>();
		

	
		try {
		for (Object[] row : detailsEntities1) {
			Superchekupdstatus entity = new Superchekupdstatus();
			
			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]); 
			entity.setWbvname((String) row[2]); 
			entity.setBookingid((Integer) row[3]); 
			entity.setOccup_name((String) row[4]); 
			entity.setOccup_fname((String) row[5]); 
			entity.setCropname((String) row[6]); 
			entity.setVarietyname((String) row[7]); 
			entity.setCr_sow_date((String) row[8].toString());
			entity.setKh_no(((BigDecimal) row[9]).intValue());
			entity.setCr_sno((String) row[10]);
			entity.setSupercheck_userid((String) row[11]);
			entity.setRemarks((String) row[12]);
			entity.setReason((String) row[13].toString());
			
			String vaasuprem=(String) row[14];
			if(vaasuprem == null) {
				entity.setVaa_sup_rem("");
			}else {
				entity.setVaa_sup_rem((String) row[14]);
				
			}
			
			
		String maoremarks=(String) row[15].toString();
			if(maoremarks == null) {
				entity.setMao_remarks("");
			}else {
				entity.setMao_remarks((String) row[15].toString());
			}

			if(row[16] == null) {
				entity.setMro_remarks("");
				
			}else {
				entity.setMro_remarks((String) row[16].toString());	
			}
			
			detailsEntities.add(entity);

		}
		System.out.println("detailsEntities===========>"+detailsEntities.size());

}catch(Exception e) {
	e.printStackTrace();
	System.out.println("erroe==> "+e);
}
		return detailsEntities;

	}
	


}
