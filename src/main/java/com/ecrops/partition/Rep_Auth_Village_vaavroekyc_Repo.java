package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Rep_Auth_Villagewise_Entity;

@Repository
@Transactional
public class Rep_Auth_Village_vaavroekyc_Repo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Rep_Auth_Villagewise_Entity> getrepVillageAuthCrop(String cropyear,String wbdcode) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String part_key = "";
		part_key = seasonType + seasonYear;
		System.out.println("part_key==========>" + part_key);

		String tableName = "cr_authdetails_rbk_mv_"+part_key;
		
		if(seasonYear==2023 && seasonType.equals("S")) {
			 tableName = "cr_authdetails_rbk_mv_" + part_key; 
			}
		
		else if(seasonYear>=2023) {
		 tableName = "ecrop" + seasonYear + "." + "cr_authdetails_rbk_mv_" + part_key; 
		}
	

		String Sql = "select mname,vname,rbkname||' (RBK_'||rbkcode||')' AS rbk, totalbookings,totfarmercount,coalesce(totextent,0) as totextent,vaaauthcount, "
				+ "coalesce(vaaauthextent,0) as vaaauthextent,vroauthcount, coalesce(vroauthextent,0) as vroauthextent,totekycbookings,ekycfarmercount,coalesce(ekycbookedext,0) as ekycbookedext from "
				+ ""+tableName+" where cr_dist_code="+wbdcode+"  order by mname,vname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<Rep_Auth_Villagewise_Entity> detailsEntities = new ArrayList<Rep_Auth_Villagewise_Entity>();

		for (Object[] row : detailsEntities1) {

			Rep_Auth_Villagewise_Entity entity = new Rep_Auth_Villagewise_Entity();

			entity.setMname((String) row[0]);
			entity.setVname((String) row[1]);
			entity.setRbk((String) row[2]);
			entity.setTotalbookings((BigInteger) row[3]);
			entity.setTotfarmercount((BigInteger) row[4]);
			entity.setTotextent((BigDecimal) row[5]);
			entity.setVaaauthcount((BigInteger) row[6]);
			entity.setVaaauthextent((BigDecimal) row[7]);
			entity.setVroauthcount((BigInteger) row[8]);
			entity.setVroauthextent((BigDecimal) row[9]);
			entity.setTotekycbookings((BigInteger) row[10]);
			entity.setEkycfarmercount((BigInteger) row[11]);
			entity.setEkycbookedext((BigDecimal) row[12]);

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}
}

	