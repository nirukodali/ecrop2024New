package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.IrrigationDwise;
import com.ecrops.entity.IrrigationMwise;

@Repository
@Transactional
public class IrrigationMwisePartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<IrrigationMwise> irrimwise(String cropyear, String wbdcode, int a)

	{
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (activeYear == (seasonYear)) {
			tableName = "ecrop" + activeYear + "." + "cr_irrmethod_det_mv_" + part_key;
		} else {
			tableName = "cr_irrmethod_det_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbmname,sum(Conventional_farmers) as cf,sum(Conventional_ext) as ce,\r\n"
				+ "sum(dripirr_farmers) as df,sum(dripirr_ext) as de,sum(sprinkler_farmers) as sf,\r\n"
				+ "sum(sprinkler_ext) as se,sum(rainfed_farmers) as rf,sum(rainfed_ext) as re  \r\n" + "from "
				+ tableName + " where wbdcode=? group by wbmname order by wbmname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<IrrigationMwise> detailsEntities = new ArrayList<IrrigationMwise>();

		for (Object[] row : detailsEntities1) {

			IrrigationMwise entity = new IrrigationMwise();
			entity.setWbmname((String) row[0]);
			entity.setCf(((BigDecimal) row[1]).intValue());
			entity.setCe(((BigDecimal) row[2]).intValue());
			entity.setDf(((BigDecimal) row[3]).intValue());
			entity.setDe(((BigDecimal) row[4]).intValue());
			entity.setSf(((BigDecimal) row[5]).intValue());
			entity.setSe(((BigDecimal) row[6]).intValue());
			entity.setRf(((BigDecimal) row[7]).intValue());
			entity.setRe(((BigDecimal) row[8]).intValue());

			detailsEntities.add(entity);

		}

		return detailsEntities;
	}

}