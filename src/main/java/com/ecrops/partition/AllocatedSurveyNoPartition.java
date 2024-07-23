package com.ecrops.partition;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.AllocatedSurveyNo;

@Repository
@Transactional
public class AllocatedSurveyNoPartition {

	@PersistenceContext
	private EntityManager entityManager;

	public  List<AllocatedSurveyNo> getAllSno(String wbdcode, String mcode, String cropyear) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String part_key = "", part_key1 = "";

		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;
			System.out.println("part_key==========>" + part_key);
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear;
			System.out.println("part_key==========>" + part_key);
		}

		String tableName;
		String tableName1;

		if (seasonYear >= 2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_booking_partition_" + part_key;
			tableName1 =  "wbvillage_mst";

		} else {

			tableName = "cr_booking_partition_" + part_key;
			tableName1 = "wbvillage_mst";

		}

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);

		String Sql = "select lgdvname,cast(data_src as varchar) as data_src,cast(kh_no as varchar) as kh_no ,cr_sno,tot_extent,geo_reffered \r\n"
				+ "from " + tableName + " a," + tableName1 + " b where cr_vcode=wbvcode and a.mcode=? \r\n"
				+ "order by lgdvname,cr_sno,kh_no";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(mcode));

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<AllocatedSurveyNo> detailsEntities = new ArrayList<AllocatedSurveyNo>();

		for (Object[] row : detailsEntities1) {

			AllocatedSurveyNo entity = new AllocatedSurveyNo();

			entity.setLgdvname((String) row[0].toString());
			String datasrc="";
			 if (row[1].toString().equals("R")) {
                 datasrc = "ROFR";
             } else if (row[1].toString().equals("W")) {
                 datasrc = "Web Land";
             } else if (row[1].toString().equals("C")) {
                 datasrc = "CCRC";
             }

			entity.setData_src(datasrc);
			entity.setKh_no((String) row[2].toString());//System.out.println("2"+row[2]);
			entity.setCr_sno((String) row[3].toString());//System.out.println("3"+row[3]);
			entity.setTot_extent((BigDecimal) row[4]);//System.out.println("4"+row[4]);
			if(row[5]!=null) {
				if(row[5].toString().equals("Y")) {
					 String geoRef="Yes";
						entity.setGeo_reffered(geoRef);
				}
				
		
			}else {
				entity.setGeo_reffered("NO");
			}
			detailsEntities.add(entity);

		}
		

		return detailsEntities;

	}

}
