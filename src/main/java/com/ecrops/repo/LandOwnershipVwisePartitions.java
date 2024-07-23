package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.LandOwnershipDwise;
import com.ecrops.entity.LandOwnershipVwise;

@Repository
@Transactional
public class LandOwnershipVwisePartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<LandOwnershipVwise> landownervwise(String cropyear, int a) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (activeYear == (seasonYear)) {
			tableName = "ecrop" + activeYear + "." + "cr_land_ownership_det_mv_" + part_key;
		} else {
			tableName = "cr_land_ownership_det_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = "select wbedname, wbemname, wbevname, p_farmers as pf, patt_ext as pe, c_farmers as cf, cul_ext as ce, e_farmers as ef,\r\n"
				+ " enj_ext as ee, ccrc_farmers as ccf, ccrc_ext as cce \r\n" + " from " + tableName
				+ "  where wbdcode<>88 order by  wbedname, wbemname, wbevname  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<LandOwnershipVwise> detailsEntities = new ArrayList<LandOwnershipVwise>();

		for (Object[] row : detailsEntities1) {

			LandOwnershipVwise entity = new LandOwnershipVwise();
			entity.setWbedname((String) row[0]);
			entity.setWbemname((String) row[1]);
			entity.setWbevname((String) row[2]);
			entity.setPf(Long.valueOf(row[3].toString()));
			entity.setPe(((BigDecimal) row[4]).intValue());
			entity.setCf(Long.valueOf(row[5].toString()));
			entity.setCe(((BigDecimal) row[6]).intValue());
			entity.setEf(Long.valueOf(row[7].toString()));
			entity.setEe(((BigDecimal) row[8]).intValue());
			entity.setCcf(Long.valueOf(row[9].toString()));
			entity.setCce(((BigDecimal) row[10]).intValue());
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
