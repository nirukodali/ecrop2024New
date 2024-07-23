package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.RofrExtBooked;
import com.ecrops.entity.SuperCheckDistWise;

@Repository
@Transactional
public class RofrExtBookedPartititons {
	@PersistenceContext
	private EntityManager entityManager;

	public List<RofrExtBooked> rofrextbooked(String cropyear, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear >= 2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_data_src_det_mv_" + part_key;
		} else {
			tableName = "cr_data_src_det_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = "select wbedname,\r\n"
				+ " cast(sum(web_farmers) as varchar)as web_farmers,\r\n"
				+ " cast(sum(web_ext) as varchar)as web_ext,\r\n"
				+ " cast(sum(nweb_farmers) as varchar)as nweb_farmers ,\r\n"
				+ " cast(sum(nweb_ext) as varchar)as nweb_ext ,\r\n"
				+ " cast(sum(ccrc_farmers) as varchar)as ccrc_farmers,\r\n"
				+ " cast(sum(ccrc_ext) as varchar)as ccrc_ext,\r\n"
				+ " cast(sum(rofr_farmers) as varchar)as rofr_farmers,\r\n"
				+ " cast(sum(rofr_ext) as varchar)as rofr_ext,\r\n"
				+ " cast(sum(usus_farmers)as varchar)   as usus_farmers,\r\n"
				+ " cast(sum(usus_ext) as varchar) as usus_ext \r\n"
				+ " FROM "+ tableName +" where wbdcode<>88 group by \r\n"
				+ " wbedname ORDER BY wbedname  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		List<RofrExtBooked> detailsEntities = new ArrayList<RofrExtBooked>();

		for (Object[] row : detailsEntities1) {
			RofrExtBooked entity = new RofrExtBooked();
			entity.setWbedname((String) row[0]);
			entity.setWeb_farmers((String) row[1]);
			entity.setWeb_ext((String) row[2]);
			entity.setNweb_farmers((String) row[3]);
			entity.setNweb_ext((String) row[4]);
			entity.setCcrc_farmers((String) row[5]);
			entity.setCcrc_ext((String) row[6]);
			entity.setRofr_farmers((String) row[7]);
			entity.setRofr_ext((String) row[8]);
			entity.setUsus_farmers((String) row[9]);
			entity.setUsus_ext((String) row[10]);
			detailsEntities.add(entity);

		}

		return detailsEntities;
	}

}
