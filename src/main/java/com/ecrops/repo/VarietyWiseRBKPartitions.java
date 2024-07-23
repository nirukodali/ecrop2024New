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
import com.ecrops.entity.VarietyWiseRBK;

@Repository
@Transactional
public class VarietyWiseRBKPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<VarietyWiseRBK> varietywiserbk(String cropyear, String cropid, int a) {
		String[] season = cropyear.split("@");System.out.println("cropyear---------------------"+cropyear);
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_details";
		} else {
			tableName = "cr_details";
		}

		System.out.println("tableName---------------->" + tableName);

		sql = " SELECT wbdname, wbmname, wbvname, updatedby, vname, varietyname, \r\n"
				+ "CAST(COUNT(DISTINCT cr_farmeruid) AS VARCHAR) AS uid, \r\n"
				+ "cast(SUM(cr_mix_unmix_ext) as varchar)AS ext\r\n"
				+ "FROM "+ tableName +" a\r\n"
				+ "JOIN wbvillage_mst b ON a.cr_vcode = b.wbvcode\r\n"
				+ "JOIN vill_sec_det c ON c.vcode = CAST(SUBSTRING(updatedby, 5) AS INTEGER)\r\n"
				+ "JOIN cr_variety_master d ON a.variety = d.varietycode WHERE cr_season = ? \r\n"
				+ " and a.cr_year = ? AND d.cropcode = ? AND a.cr_crop = ? and b.wbdcode<>88 \r\n"
				+ "GROUP BY wbdname, wbmname, wbvname, updatedby, vname, varietyname \r\n";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, seasonType);
		sesnyr.setParameter(2, seasonYear);
		sesnyr.setParameter(3, Integer.parseInt(cropid));
		sesnyr.setParameter(4, Integer.parseInt(cropid));

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> variety = sesnyr.getResultList();

		List<VarietyWiseRBK> entityDetails = new ArrayList<VarietyWiseRBK>();

		for (Object[] row : variety) {

			VarietyWiseRBK entity = new VarietyWiseRBK();

			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setUpdatedby((String) row[3]);
			entity.setVname((String) row[4]);
			entity.setVarietyname((String) row[5]);
			entity.setUid(Long.valueOf(row[6].toString()));
			entity.setExt(((String) row[7]));

			entityDetails.add(entity);

		}
		return entityDetails;

	}

}
