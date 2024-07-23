package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.RbkSurveyNoMapping;
import com.ecrops.entity.SuperCheckIntf;

@Repository
@Transactional
public class SuperCheckIntfPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<SuperCheckIntf> superchk(String cropyear, String wbdcode,int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
        	tableName = "ecrop" + seasonYear + "." + "supercheck_status_distwise_" + part_key;
        }
        else {
        	tableName = "supercheck_status_distwise_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String sql = " select mname,cast(dao_allotted as varchar),cast(dao_approved as varchar),\r\n"
				+ "cast(dao_rejected as varchar),cast(ada_allotted as varchar),\r\n"
				+ "cast(ada_approved as varchar),cast(ada_rejected as varchar),\r\n"
				+ "cast(mao_allotted as varchar),cast(mao_approved as varchar),\r\n"
				+ "cast(mao_rejected as varchar),cast(dho_allotted as varchar),\r\n"
				+ "cast(dho_approved as varchar),cast(dho_rejected as varchar),\r\n"
				+ "cast(ho_allotted as varchar),cast(ho_approved as varchar),\r\n"
				+ "cast(ho_rejected as varchar),cast(rdo_allotted as varchar),\r\n"
				+ "cast(rdo_approved as varchar),cast(rdo_rejected as varchar),\r\n"
				+ "cast(tah_allotted as varchar),cast(tah_approved as varchar),\r\n"
				+ "cast(tah_rejected as varchar),cast(jc_allotted as varchar),\r\n"
				+ "cast(jc_approved as varchar),cast(jc_rejected as varchar),\r\n"
				+ "cast(dc_allotted as varchar),cast(dc_approved as varchar),\r\n"
				+ "cast(dc_rejected as varchar),cast(hod_allotted as varchar),\r\n"
				+ "cast(hod_approved as varchar),cast(hod_rejected as varchar) \r\n" + "from " + tableName
				+ " where cr_dist_code=? ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, Integer.parseInt(wbdcode));

		System.out.println("sesnyr=>" + sesnyr);

		List<Object[]> superchk = sesnyr.getResultList();
		List<SuperCheckIntf> entityDetails = new ArrayList<SuperCheckIntf>();

		for (Object[] row : superchk) {

			SuperCheckIntf entity = new SuperCheckIntf();


			entity.setMname((String) row[0]);
			entity.setDao_allotted((String) row[1]);
			entity.setDao_approved((String) row[2]);
			entity.setDao_rejected((String) row[3]);

			entity.setAda_allotted((String) row[4]);
			entity.setAda_approved((String) row[5]);
			entity.setAda_rejected((String) row[6]);

			entity.setMao_allotted((String) row[7]);
			entity.setMao_approved((String) row[8]);
			entity.setMao_rejected((String) row[9]);

			entity.setDho_allotted((String) row[10]);
			entity.setDho_approved((String) row[11]);
			entity.setDho_rejected((String) row[12]);

			entity.setHo_allotted((String) row[13]);
			entity.setHo_approved((String) row[14]);
			entity.setHo_rejected((String) row[15]);

			entity.setRdo_allotted((String) row[16]);
			entity.setRdo_approved((String) row[17]);
			entity.setRdo_rejected((String) row[18]);

			entity.setTah_allotted((String) row[19]);
			entity.setTah_approved((String) row[20]);
			entity.setTah_rejected((String) row[21]);

			entity.setJc_allotted((String) row[22]);
			entity.setJc_approved((String) row[23]);
			entity.setJc_rejected((String) row[24]);

			entity.setDc_allotted((String) row[25]);
			entity.setDc_approved((String) row[26]);
			entity.setDc_rejected((String) row[27]);

			entity.setHod_allotted((String) row[28]);
			entity.setHod_approved((String) row[29]);
			entity.setHod_rejected((String) row[30]);

			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
