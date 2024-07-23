package com.ecrops.repo;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.DistWiseVAAVROeKYCrabi;

@Repository
@Transactional
public class DistWiseVAAVROeKYCrabipartitons {
	@PersistenceContext
	private EntityManager entityManager;
	public List<DistWiseVAAVROeKYCrabi> distvaavro(String cropyear, int a){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "",tableName="";

		part_key = seasonType + seasonYear;

        if (seasonYear >= 2023 && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S")) ) {
        	tableName = "ecrop" + seasonYear + "." + "cr_authdetails_dist_v_" + part_key;
        }
        else {
        	tableName = "cr_authdetails_dist_v_" + part_key;
        }
//		String tableName = "ecrop" + activeYear + "." + "cr_authdetails_dist_v_" + part_key;

		System.out.println("tableName---------------->" + tableName);

		String Sql = "select distinct dname,cast(a.totalbookings as varchar),cast(a.totextent as varchar),cast(a.vaaauthcount as varchar),\r\n"
				+ "cast(a.vaaauthextent as varchar),cast(a.vroauthcount as varchar),cast(a.vroauthextent as varchar),\r\n"
				+ "cast(a.totekycbookings as varchar),cast(a.totfarmercount as varchar),cast(a.ekycfarmercount as varchar),\r\n"
				+ "cast(a.ekycbookedext as varchar) from "+ tableName +" a  left join district_2011_cs b on a.cr_dist_code=b.wbdcode \r\n"
				+ "where cr_dist_code<>88 order by dname ";
		
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		List<DistWiseVAAVROeKYCrabi> detailsEntities = new ArrayList<DistWiseVAAVROeKYCrabi>();
		
		for (Object[] row : detailsEntities1) {

			DistWiseVAAVROeKYCrabi entity = new DistWiseVAAVROeKYCrabi();
			entity.setDname((String) row[0]);
			entity.setTotalbookings(((String) row[1]));
			entity.setTotextent(((String) row[2]));
			entity.setVaaauthcount(((String) row[3]));
			entity.setVaaauthextent(((String) row[4]));
			entity.setVroauthcount(((String) row[5]));
			entity.setVroauthextent(((String) row[6]));
			entity.setTotekycbookings(((String) row[7]));
			entity.setTotfarmercount(((String) row[8]));
			entity.setEkycfarmercount(((String) row[9]));
			entity.setEkycbookedext(((String) row[10]));

			detailsEntities.add(entity);

		}

		return detailsEntities;
	}
	

}
