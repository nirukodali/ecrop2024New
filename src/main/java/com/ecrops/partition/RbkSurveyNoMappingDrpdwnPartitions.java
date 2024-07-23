package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.ecrops.entity.RbkSurveyNoMappingDrpdwn;

@Repository
@Transactional
public class RbkSurveyNoMappingDrpdwnPartitions {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<RbkSurveyNoMappingDrpdwn> getRBK( String mcode,String cropyear,String wbdcode,String userid) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String seasonType = season[0];
		System.out.println("seasonType========="+seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear========="+seasonYear);
	
		

		String part_key = "";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;	 System.out.println("part_key==========>"+part_key);
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear; System.out.println("part_key==========>"+part_key);
		}
		
		String tableName = "ecrop" + seasonYear + "." + "rbk_surveyno_mapping_" + part_key; 
		if(seasonYear>= 2023 && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
		}
		else {
			tableName = "rbk_surveyno_mapping_" + part_key; 
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select vcode,vname from vill_sec_det where vcode in (select distinct rbkcode from \r\n"
				+ "	"+tableName+"  where mcode=?) ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(mcode));
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<RbkSurveyNoMappingDrpdwn> detailsEntities = new ArrayList<RbkSurveyNoMappingDrpdwn>();
		

		for (Object[] row : detailsEntities1) {

			RbkSurveyNoMappingDrpdwn entity = new RbkSurveyNoMappingDrpdwn();
			entity.setVcode((Integer) ( row[0]));
			entity.setVname((String) row[1]);
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}
	
	

}
