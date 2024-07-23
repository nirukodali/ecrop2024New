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
import com.ecrops.entity.UnlockExtDDAP;

@Repository
@Transactional
public class UnlockExtDDAPPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<UnlockExtDDAP> unlockextddap(String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023   && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_unlock_ext_mv_" + part_key;
		} else {
			tableName = "cr_unlock_ext_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select dname, sum(bookings) as bookings, sum(ext) as ext, sum(mao_ho_aprr) as mao_ho_aprr, sum(mao_appr_ext) as mao_appr_ext,\r\n"
				+ " round(sum(mao_ho_aprr)/sum(bookings)*100,2)  as percentage from " + tableName
				+ " group by dname order by dname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		List<UnlockExtDDAP> detailsEntities = new ArrayList<UnlockExtDDAP>();

		for (Object[] row : detailsEntities1) {

			UnlockExtDDAP entity = new UnlockExtDDAP();
			entity.setDname((String) row[0]);
			entity.setBookings(((BigDecimal) row[1]).intValue());
			entity.setExt(((BigDecimal) row[2]).intValue());
			entity.setMao_ho_aprr(((BigDecimal) row[3]).intValue());
			entity.setMao_appr_ext(((BigDecimal) row[4]).intValue());
			entity.setPercentage(((BigDecimal) row[5]).intValue());

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
