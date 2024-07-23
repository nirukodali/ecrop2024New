package com.ecrops.partition;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.entity.DataSourceWiseBookingReport;

@Repository
@Transactional
public class DataSourceWiseBookingReportPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<DataSourceWiseBookingReport> dataSrcDet(String wbdcode,String cropyear,
			String wbmcode,String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "";
		part_key = seasonType  + seasonYear;
		
//		if (Integer.parseInt(wbdcode) > 9) {
//			part_key = seasonType + Integer.parseInt(wbdcode) + seasonYear;
//		} else {
//			part_key = seasonType + "0" + Integer.parseInt(wbdcode) + seasonYear;
//		}
		
		//String tableName = "ecrop"+seasonYear +"." +"cr_data_src_det_mv_" + part_key;
		
		String tableName = "ecrop" + seasonYear + "." + "cr_data_src_det_mv_" + part_key;
		if(seasonYear >= 2023 && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_data_src_det_mv_" + part_key;
		}else {
			tableName = "cr_data_src_det_mv_" + part_key;
		}
		
		System.out.println("tableName---------------->" + tableName);

		String Sql = " SELECT wbevname, sum(web_farmers) as web_farmers, sum(web_ext) as web_ext, \r\n"
				+ "sum(nweb_farmers) as nweb_farmers , sum(nweb_ext)as nweb_ext ,sum(ccrc_farmers)as ccrc_farmers,\r\n"
				+ "sum(ccrc_ext)as ccrc_ext, sum(rofr_farmers)as rofr_farmers,sum(rofr_ext)as rofr_ext, sum(usus_farmers) \r\n"
				+ "as usus_farmers, sum(usus_ext) as usus_ext,wbdcode, wbedname,wbmcode,wbemname,wbvcode FROM "+tableName+" where wbdcode<>88 \r\n"
				+ "and wbdcode =? and wbmcode =?  group by  wbdcode, wbedname,wbmcode,wbemname,wbvcode,wbevname ORDER BY wbevname \r\n"
				+ " ";
	//	List<DataSourceWiseBookingReport>  getList( wbdcode,wbmcode);
		
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<DataSourceWiseBookingReport> detailsEntities = new ArrayList<DataSourceWiseBookingReport>();
		

		for (Object[] row : detailsEntities1) {
			DataSourceWiseBookingReport entity = new DataSourceWiseBookingReport();
			entity.setWbevname((String) row[0]);
			entity.setWeb_farmers(Double.parseDouble(row[1].toString()));
			entity.setWeb_ext(Double.parseDouble(row[2].toString()));
			entity.setNweb_farmers(Double.parseDouble(row[3].toString()));
			entity.setNweb_ext(Double.parseDouble(row[4].toString()));
			entity.setCcrc_farmers(Double.parseDouble(row[5].toString()));
			entity.setCcrc_ext(Double.parseDouble(row[6].toString()));
			entity.setRofr_farmers(Double.parseDouble(row[7].toString()));
			entity.setRofr_ext(Double.parseDouble(row[8].toString()));
			entity.setUsus_farmers(Double.parseDouble(row[9].toString()));
			entity.setUsus_ext(Double.parseDouble(row[10].toString()));
			
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}
	
//public List<DataSourceWiseBookingReport> values(@RequestParam("wbdcode") int wbdcode){
//	return ;
//}
}

