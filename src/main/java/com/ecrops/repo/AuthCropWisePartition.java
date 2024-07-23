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

@Repository

@Transactional
public class AuthCropWisePartition {

	@PersistenceContext
	private EntityManager entityManager;

	public List<AuthCropWise> authcropwise(String cropyear, String cropid, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear >=2023) {
			tableName = "ecrop" + seasonYear + "." + "cr_authdetails_dist_cropwise_v_" + part_key;
		} else {
			tableName = "cr_authdetails_dist_cropwise_v_" + part_key;
		}


		System.out.println(cropid + "tableName---------------->" + tableName);

		sql = " select distinct b.dname as wbdname,coalesce(x.totfarmercount,0) as totfarmercount, coalesce(x.totalbookings,0) as totalbookings,\r\n"
				+ "coalesce(x.totextent,0) as totextent,coalesce(x.vaaauthcount,0) as vaaauthcount, coalesce(x.vaaauthextent,0) as vaaauthextent, \r\n"
				+ "coalesce(x.vroauthcount,0) as vroauthcount, coalesce(x.vroauthextent,0) as vroauthextent, coalesce(x.totekycbookings,0) as totekycbookings,  \r\n"
				+ "coalesce(x.ekycfarmercount,0) as ekycfarmercount, coalesce(x.ekycbookedext,0) as ekycbookedext \r\n"
				+ "from (select totalbookings, totextent, vaaauthcount, vaaauthextent, vroauthcount, vroauthextent, totekycbookings, totfarmercount, \r\n"
				+ "ekycfarmercount, ekycbookedext,cr_dist_code,cr_crop from " + tableName
				+ ") x right join district_2011_cs b \r\n"
				+ "on b.wbdcode=x.cr_dist_code and cr_crop=? where wbdcode<>88 order by wbdname ";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, Integer.parseInt(cropid));

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> authcropwise = sesnyr.getResultList();

		List<AuthCropWise> entityDetails = new ArrayList<AuthCropWise>();

		for (Object[] row : authcropwise) {

			AuthCropWise entity = new AuthCropWise();

			entity.setWbdname((String) row[0]);
			entity.setTotfarmercount(((BigDecimal) row[1]).intValue());
			entity.setTotalbookings(((BigDecimal) row[2]).intValue());
			entity.setTotextent(((BigDecimal) row[3]).intValue());
			entity.setVaaauthcount(((BigDecimal) row[4]).intValue());
			entity.setVaaauthextent(((BigDecimal) row[5]).intValue());
			entity.setVroauthcount(((BigDecimal) row[6]).intValue());
			entity.setVroauthextent(((BigDecimal) row[7]).intValue());
			entity.setTotekycbookings(((BigDecimal) row[8]).intValue());
			entity.setEkycfarmercount(((BigDecimal) row[9]).intValue());
			entity.setEkycbookedext(((BigDecimal) row[10]).intValue());

			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
