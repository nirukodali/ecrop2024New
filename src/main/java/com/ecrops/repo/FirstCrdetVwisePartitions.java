package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.FirstCrdetVwise;

@Repository
@Transactional
public class FirstCrdetVwisePartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<FirstCrdetVwise> firstcedetvwise(String cropyear,int a){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "",tableName="";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
        	tableName = "ecrop" + seasonYear + "." + "cr_sown_type_rep_mv_" + part_key;
        }
        else {
        	tableName = "cr_sown_type_rep_mv_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbedname,wbemname,wbevname,cast(int_farmers as varchar)as inf,\r\n"
				+ "cast(int_ext as varchar) as ine,cast(sin_farmers as varchar) as sf,\r\n"
				+ "cast(sin_ext as varchar) as se,cast(mix_farmers as varchar) as mxf,\r\n"
				+ "cast(mix_ext as varchar) as me from "+ tableName +" \r\n"
				+ "order by wbedname,wbemname,wbevname  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<FirstCrdetVwise> detailsEntities = new ArrayList<FirstCrdetVwise>();

		for (Object[] row : detailsEntities1) {

			FirstCrdetVwise entity = new FirstCrdetVwise();
			entity.setWbedname((String) row[0]);
			entity.setWbemname((String) row[1]);
			entity.setWbevname((String) row[2]);
			entity.setInf((String) row[3]);
			entity.setIne((String) row[4]);
			entity.setSf((String) row[5]);
			entity.setSe((String) row[6]);
			entity.setMxf((String) row[7]);
			entity.setMe((String) row[8]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
