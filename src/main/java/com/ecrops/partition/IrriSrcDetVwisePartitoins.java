package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.IrriSrcDetVwise;

@Repository
@Transactional
public class IrriSrcDetVwisePartitoins {
	@PersistenceContext
	private EntityManager entityManager;

	public List<IrriSrcDetVwise> irrisrcwise(String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (seasonYear >= 2023   && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_irr_src_det_mv_" + part_key;
		} else {
			tableName = "cr_irr_src_det_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select dname, mname, vname, bore_farmers as bwf, bore_ext as bwe, \r\n"
				+ " lift_farmers as lf, lift_ext as le, can_farmers as cf, can_ext as ce, \r\n"
				+ " tank_farmers as tf, tank_ext as te, open_farmers as of, open_ext as oe, \r\n"
				+ " rainfed_farmers as rf, rainfed_ext as re from " + tableName + " \r\n"
				+ " order by dname, mname, vname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<IrriSrcDetVwise> detailsEntities = new ArrayList<IrriSrcDetVwise>();

		for (Object[] row : detailsEntities1) {

			IrriSrcDetVwise entity = new IrriSrcDetVwise();
			entity.setDname((String) row[0]);
			entity.setMname((String) row[1]);
			entity.setVname((String) row[2]);
			entity.setBwf(Long.valueOf(row[3].toString()));
			entity.setBwe(((BigDecimal) row[4]));
			entity.setLf(Long.valueOf(row[5].toString()));
			entity.setLe(((BigDecimal) row[6]));
			entity.setCf(Long.valueOf(row[7].toString()));
			entity.setCe(((BigDecimal) row[8]));
			entity.setTf(Long.valueOf(row[9].toString()));
			entity.setTe(((BigDecimal) row[10]));
			entity.setOf(Long.valueOf(row[11].toString()));
			entity.setOe(((BigDecimal) row[12]));
			entity.setRf(Long.valueOf(row[13].toString()));
			entity.setRe(((BigDecimal) row[14]));
			detailsEntities.add(entity);
		}

		return detailsEntities;
	}
}
