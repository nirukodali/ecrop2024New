package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Rep_Auth_Mandalwise_Entity;

@Repository
@Transactional
public class Rep_Auth_vaavroekyc_Repo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Rep_Auth_Mandalwise_Entity> getrepMandalAuthCrop(String cropyear,String dcode) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String part_key = "";
		part_key = seasonType + seasonYear;
		System.out.println("part_key==========>" + part_key);

		String tableName = "cr_authdetails_mand_mv_"+part_key;
		
		if(seasonYear==2023 && seasonType.equals("S")) {
			 tableName = "cr_authdetails_mand_mv_" + part_key; 
			}
		
		else if(seasonYear>=2023) {
		 tableName = "ecrop" + seasonYear + "." + "cr_authdetails_mand_mv_" + part_key; 
		}
	

		String Sql = "select distinct b.wbmname, x.totalbookings,x.totfarmercount,coalesce( x.totextent, 0) as totextent, x.vaaauthcount, coalesce( x.vaaauthextent,0) as vaaauthextent, x.vroauthcount, coalesce( x.vroauthextent,0) as vroauthextent, "
				+ "x.totekycbookings,  x.ekycfarmercount, coalesce( x.ekycbookedext,0) as ekycbookedext from (select totalbookings, totextent, vaaauthcount, "
				+ "vaaauthextent, vroauthcount, vroauthextent,totekycbookings, totfarmercount, ekycfarmercount, ekycbookedext,cr_dist_code,cr_mand_code"
				+ " from " + tableName + " )x, wbvillage_mst b where x.cr_dist_code=b.wbdcode and x.cr_mand_code=b.wbmcode and b.dcode="+dcode+"";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<Rep_Auth_Mandalwise_Entity> detailsEntities = new ArrayList<Rep_Auth_Mandalwise_Entity>();

		for (Object[] row : detailsEntities1) {

			Rep_Auth_Mandalwise_Entity entity = new Rep_Auth_Mandalwise_Entity();

			entity.setWbmname((String) row[0]);
			entity.setTotalbookings((BigDecimal) row[1]);
			entity.setTotfarmercount((BigDecimal) row[2]);
			entity.setTotextent((BigDecimal) row[3]);
			entity.setVaaauthcount((BigDecimal) row[4]);
			entity.setVaaauthextent((BigDecimal) row[5]);
			entity.setVroauthcount((BigDecimal) row[6]);
			entity.setVroauthextent((BigDecimal) row[7]);
			entity.setTotekycbookings((BigDecimal) row[8]);
			entity.setEkycfarmercount((BigDecimal) row[9]);
			entity.setEkycbookedext((BigDecimal) row[10]);

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}
}

	