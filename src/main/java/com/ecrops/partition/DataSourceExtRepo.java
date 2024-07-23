package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DataSourceExtEntity;

@Repository
@Transactional
public class DataSourceExtRepo {
	@PersistenceContext
	private EntityManager entityManager;

	public List<DataSourceExtEntity> getDataSourceExt(String wbdcode, String cropyear) {
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String tableName = "cr_data_src_det_mv_";

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop"+seasonYear+"."+"cr_data_src_det_mv_" + seasonType + seasonYear;

			System.out.println("tableName-----------------------" + tableName);
		} else {
			tableName = "cr_data_src_det_mv_" + seasonType + seasonYear;

			System.out.println("tableName==================" + tableName);

		}

		String Sql = "SELECT wbemname, sum(web_farmers) as web_farmers, sum(web_ext) as web_ext, sum(nweb_farmers) as nweb_farmers , sum(nweb_ext)as nweb_ext ,\r\n"
				+ "sum(ccrc_farmers)as ccrc_farmers,sum(ccrc_ext)as ccrc_ext, sum(rofr_farmers)as rofr_farmers,sum(rofr_ext)as rofr_ext, sum(usus_farmers) as usus_farmers, \r\n"
				+ "sum(usus_ext) as usus_ext FROM " + tableName + " where wbdcode<>88 and wbdcode =" + wbdcode
				+ " group by wbdcode, wbedname,wbmcode,wbemname ORDER BY wbemname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<DataSourceExtEntity> detailsEntities = new ArrayList<DataSourceExtEntity>();

		try {

			for (Object[] row : detailsEntities1) {
				DataSourceExtEntity entity = new DataSourceExtEntity();
				entity.setWbemname((String) row[0]);
				entity.setWeb_farmers((BigDecimal) row[1]);
				entity.setWeb_ext((BigDecimal) row[2]);
				entity.setNweb_farmers((BigDecimal) row[3]);
				entity.setNweb_ext((BigDecimal) row[4]);
				entity.setCcrc_farmers((BigDecimal) row[5]);
				entity.setCcrc_ext((BigDecimal) row[6]);
				entity.setRofr_farmers((BigDecimal) row[7]);
				entity.setRofr_ext((BigDecimal) row[8]);
				entity.setUsus_farmers((BigDecimal) row[9]);
				entity.setUsus_ext((BigDecimal) row[10]);
				detailsEntities.add(entity);

			}
			System.out.println("detailsEntities===========>" + detailsEntities.size());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;

	}

}
