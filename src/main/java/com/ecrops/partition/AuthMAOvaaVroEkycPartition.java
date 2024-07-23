package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AuthMAOvaaVroEkyc;
import com.ecrops.entity.CropwiseExtBookedRBKwise;

@Repository
@Transactional
public class AuthMAOvaaVroEkycPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<AuthMAOvaaVroEkyc> getAuthMaoVaaVroEkyc(String mcode, String cropyear){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "";
		
		part_key = seasonType  + seasonYear;
		String tableName;
		if(seasonYear >=2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
		 tableName = "ecrop" + seasonYear + "." + "cr_authdetails_rbk_mv_" + part_key;
		}else {
			tableName = "cr_authdetails_rbk_mv_" + part_key;
		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select cast(rbkname||' (RBK_'||rbkcode||')' as varchar) AS rbk,  cast(totfarmercount as varchar), \r\n"
				+ "cast(totalbookings as varchar),cast(totextent as varchar),cast(vaaauthcount as varchar), cast(vaaauthextent as varchar),\r\n"
				+ "cast(vroauthcount as varchar), cast(vroauthextent as varchar), CAST(totekycbookings as varchar), \r\n"
				+ "cast(ekycfarmercount as varchar),cast(ekycbookedext as varchar) from  "+tableName+" where mcode =?";
	
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(mcode));
		//insertQuery.setParameter(2, Integer.parseInt(mcode));
		//insertQuery.setParameter(3, Integer.parseInt(cropid));
		//insertQuery.setParameter(4, seasonYear);
		//insertQuery.setParameter(5, seasonType);
		
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<AuthMAOvaaVroEkyc> detailsEntities = new ArrayList<AuthMAOvaaVroEkyc>();
		
try {
	

		for (Object[] row : detailsEntities1) {
			AuthMAOvaaVroEkyc entity = new AuthMAOvaaVroEkyc();
			entity.setRbk((String) row[0]);
			entity.setTotfarmercount((String) row[1]);
			entity.setTotalbookings((String) row[2]);
			entity.setTotextent((String) row[3]);
			entity.setVaaauthcount((String) row[4]);
			entity.setVaaauthextent((String) row[5]);
			entity.setVroauthcount((String) row[6]);
			entity.setVroauthextent((String) row[7]);
			entity.setTotekycbookings((String) row[8]);
			entity.setEkycfarmercount((String) row[9]);
			entity.setEkycbookedext((String) row[10]);
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
