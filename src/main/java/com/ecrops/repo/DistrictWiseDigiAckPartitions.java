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
import com.ecrops.entity.DistrictWiseDigiAck;

@Repository
@Transactional
public class DistrictWiseDigiAckPartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DistrictWiseDigiAck> distwiseack(String cropyear,int a){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear >= 2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "sms_mv_" + part_key;
		} else {
			tableName = "sms_mv_" + part_key;
		}


		System.out.println("tableName---------------->" + tableName);

		sql = " select dname,totSmsCnt,sentSmsCnt,remSmsCnt from "+ tableName+" ";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> distack = sesnyr.getResultList();

		List<DistrictWiseDigiAck> entityDetails = new ArrayList<DistrictWiseDigiAck>();

		for (Object[] row : distack) {

			DistrictWiseDigiAck entity = new DistrictWiseDigiAck();

			entity.setDname((String) row[0]);
			entity.setTotSmsCnt(Long.valueOf(row[1].toString()));
			entity.setSentSmsCnt(Long.valueOf(row[2].toString()));
			entity.setRemSmsCnt(Long.valueOf(row[3].toString()));
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
