package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AuthCropWise;
import com.ecrops.entity.IrriMethodRBKWise;

@Repository
@Transactional
public class IrriMethodRBKWisePartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<IrriMethodRBKWise> irrimethodrbkwise(String cropyear, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_irrmethod_det_mv_" + part_key;
		} else {
			tableName = "cr_irrmethod_det_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		sql = " select wbdname,wbmname,wbvname,Conventional_farmers as cf,Conventional_ext as ce,\r\n"
				+ "dripirr_farmers as df, dripirr_ext as de,sprinkler_farmers as sf,sprinkler_ext as se,\r\n"
				+ "rainfed_farmers as rf,rainfed_ext as re from " + tableName + " ";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> authcropwise = sesnyr.getResultList();

		List<IrriMethodRBKWise> entityDetails = new ArrayList<IrriMethodRBKWise>();

		for (Object[] row : authcropwise) {

			IrriMethodRBKWise entity = new IrriMethodRBKWise();

			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setCf(Long.valueOf(row[3].toString()));
			entity.setCe(((BigDecimal) row[4]).intValue());
			entity.setDf(Long.valueOf(row[5].toString()));
			entity.setDe(((BigDecimal) row[6]).intValue());
			entity.setSf(Long.valueOf(row[7].toString()));
			entity.setSe(((BigDecimal) row[8]).intValue());
			entity.setRf(Long.valueOf(row[9].toString()));
			entity.setRe(((BigDecimal) row[10]).intValue());
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
