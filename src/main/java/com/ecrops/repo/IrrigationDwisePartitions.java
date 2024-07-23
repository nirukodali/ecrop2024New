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
import com.ecrops.entity.IrrigationDwise;

@Repository
@Transactional
public class IrrigationDwisePartitions {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<IrrigationDwise> irridwise(String cropyear,int a){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "",tableName="";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
        	tableName = "ecrop" + seasonYear + "." + "cr_irrmethod_det_mv_" + part_key;
        }
        else {
        	tableName = "cr_irrmethod_det_mv_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String Sql = " SELECT wbdname,sum(Conventional_farmers) as cf,sum(Conventional_ext) as ce,sum(dripirr_farmers) as df,sum(dripirr_ext) as de,\r\n"
				+ "sum(sprinkler_farmers) as sf,sum(sprinkler_ext) as se,sum(rainfed_farmers) as rf,sum(rainfed_ext) as re \r\n"
				+ ",wbdcode from "+ tableName +" group by wbdcode,wbdname order by wbdname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<IrrigationDwise> detailsEntities = new ArrayList<IrrigationDwise>();

		for (Object[] row : detailsEntities1) {

			IrrigationDwise entity = new IrrigationDwise();
			entity.setWbdname((String) row[0]);
			entity.setCf(((BigDecimal) row[1]).intValue());
			entity.setCe(((BigDecimal) row[2]).intValue());
			entity.setDf(((BigDecimal) row[3]).intValue());
			entity.setDe(((BigDecimal) row[4]).intValue());
			entity.setSf(((BigDecimal) row[5]).intValue());
			entity.setSe(((BigDecimal) row[6]).intValue());
			entity.setRf(((BigDecimal) row[7]).intValue());
			entity.setRe(((BigDecimal) row[8]).intValue());
			entity.setWbdcode((Integer)row[9]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}
	

}
