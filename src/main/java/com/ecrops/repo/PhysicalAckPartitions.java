package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.PhysicalAck;
import com.ecrops.entity.SuperCheckDistWise;

@Repository
@Transactional
public class PhysicalAckPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<PhysicalAck> phyack(String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String part_key = "", tab1 = "", tab2 = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tab1 = "ecrop" + seasonYear + "." + "ekycgen_rbk_dist_mv_" + part_key;
			tab2 = "ecrop" + seasonYear + "." + "cr_authdetails_rbk_dist_mv_" + part_key;

		} else {
			tab1 = "ekycgen_rbk_dist_mv_" + part_key;
			tab1 = "cr_authdetails_rbk_dist_mv_" + part_key;

		}

		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);

		String Sql = "select r1.wbdname as dname,r2.ekycfarmercount,r1.totfarmers as fcount from ( select * from "
				+ tab1 + ") r1,\r\n" + "(select cr_dist_code,ekycfarmercount from " + tab2
				+ " order by cr_dist_code) r2 \r\n" 
				+ "where r1.wbdcode=r2.cr_dist_code order by r1.wbdname\r\n ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		List<PhysicalAck> detailsEntities = new ArrayList<PhysicalAck>();
		
		for (Object[] row : detailsEntities1) {
			PhysicalAck entity = new PhysicalAck();
			entity.setDname((String) row[0]);
			entity.setEkycfarmercount(((BigDecimal) row[1]).intValue());
			entity.setFcount(((BigDecimal) row[2]).intValue());

			detailsEntities.add(entity);

		}

		return detailsEntities;
	}

}
