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
import com.ecrops.entity.TopSixCrops;

@Repository
@Transactional
public class TopSixCropsPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<TopSixCrops> topsixcrops(String cropyear, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "top6_crops_" + part_key;
		} else {
			tableName = "top6_crops_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		sql = "  select\r\n" + "dname,\r\n" + "        cast(\"Paddy\" as varchar),\r\n"
				+ "  CAST(COALESCE(\"Cotton\", '0') AS VARCHAR) AS Cotton,\r\n"
				+ "	 CAST(COALESCE(\"Red Chillies\", '0') AS VARCHAR) AS Red_Chillies,  \r\n"
				+ "	 CAST(COALESCE(\"Groundnut\", '0') AS VARCHAR) AS Groundnut,  \r\n"
				+ "  CAST(\"Mango\" as varchar),\r\n"
				+ "  CAST(COALESCE(\"Redgram\", '0') AS VARCHAR) AS Redgram\r\n"
				+ "    from "+ tableName +" ";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		// sesnyr.setParameter(1, Integer.parseInt(cropid));

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> topsix = sesnyr.getResultList();

		List<TopSixCrops> entityDetails = new ArrayList<TopSixCrops>();

		for (Object[] row : topsix) {

			TopSixCrops entity = new TopSixCrops();

			entity.setDname((String) row[0]);
			entity.setPaddy((String) row[1]);
			entity.setCotton((String) row[2]);
			entity.setRedChillies((String) row[3]);
			entity.setGroundnut((String) row[4]);
			entity.setMango((String) row[5]);
			entity.setRedgram((String) row[6]);
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
