package com.ecrops.partition;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.SupercheckVill;
import com.ecrops.entity.Superchekupdstatus;

@Repository
@Transactional
public class SupercheckVillPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<SupercheckVill> getSupVill(String wbdcode, String wbmcode ){
//		String[] season = cropyear.split("@");
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);
		
		
		String part_key = "";
//		if (Integer.parseInt(wbdcode) > 9) {
//			part_key = seasonType + wbdcode + seasonYear;	 
//		} else {
//			part_key = seasonType + "0" + wbdcode + seasonYear; 
//		}
		 String tableName;
		String tableName1;
//		if(seasonYear>=2023) {
//		 tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key;
//		 tableName1 = "ecrop" + seasonYear + "." + "supercheck_upd";
//		 } 
			  tableName = "supercheck_status_mandwise" ;
			
		 
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select wbvname,hod_allotted,hod_approved,hod_rejected,dao_allotted,dao_approved,dao_rejected,ada_allotted,\r\n"
				+ "ada_approved,ada_rejected,mao_allotted,mao_approved,mao_rejected,dho_allotted,dho_approved,dho_rejected,\r\n"
				+ "ho_allotted,ho_approved,ho_rejected,rdo_allotted, rdo_approved,rdo_rejected,tah_allotted,tah_approved,tah_rejected,\r\n"
				+ "dc_allotted,dc_approved,dc_rejected,jc_allotted,jc_approved,\r\n"
				+ "jc_rejected, cast(cr_dist_code as varchar),cast(cr_mand_code as varchar),mcode,dname,mname\r\n"
				+ "from "+tableName+" where \r\n"
				+ "cast(cr_dist_code as varchar)=? and cast(cr_mand_code as varchar)=? order by wbvname";

		System.out.println("Sql==============>"+Sql);
		Query insertQuery =  (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, wbdcode);
		insertQuery.setParameter(2, wbmcode);
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<SupercheckVill> detailsEntities = new ArrayList<SupercheckVill>();
		

	
		try {
		for (Object[] row : detailsEntities1) {
			SupercheckVill entity = new SupercheckVill();
			entity.setWbvname((String) row[0]);
			
			entity.setHod_allotted((BigInteger) row[1]); 
			entity.setHod_approved((BigInteger) row[2]); 
			entity.setHod_rejected((BigInteger) row[3]); 
			
			entity.setDao_allotted((BigInteger) row[4]); 
			entity.setDao_approved((BigInteger) row[5]); 
			entity.setDao_rejected((BigInteger) row[6]);
			
			entity.setAda_allotted((BigInteger) row[7]); 
			entity.setAda_approved((BigInteger) row[8]);
			entity.setAda_rejected(((BigInteger) row[9]));
			
			entity.setMao_allotted((BigInteger) row[10]);
			entity.setMao_approved((BigInteger) row[11]);
			entity.setMao_rejected((BigInteger) row[12]);
			
			entity.setDho_allotted((BigInteger) row[13]);
			entity.setDho_approved((BigInteger) row[14]);
			entity.setDho_rejected((BigInteger) row[15]);
			
			entity.setHo_allotted((BigInteger) row[16]);
			entity.setHo_approved((BigInteger) row[17]);
			entity.setHo_rejected((BigInteger) row[18]);
			
			entity.setRdo_allotted((BigInteger) row[19]);
			entity.setRdo_approved((BigInteger) row[20]);
			entity.setRdo_rejected((BigInteger) row[21]);
			
			entity.setTah_allotted((BigInteger) row[22]);
			entity.setTah_approved((BigInteger) row[23]);
			entity.setTah_rejected((BigInteger) row[24]);
			
			entity.setDc_allotted((BigInteger) row[25]);
			entity.setDc_approved((BigInteger) row[26]);
			entity.setDc_rejected((BigInteger) row[27]);
			
			entity.setJc_allotted((BigInteger) row[28]);
			entity.setJc_approved((BigInteger) row[29]);
			entity.setJc_rejected((BigInteger) row[30]);
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
