package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AllocatedSurveyNoMapping;


@Repository
@Transactional
public class AllocatedSurveyNoMappingPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<AllocatedSurveyNoMapping> allocatedSnoDetails(String wbdcode, String cropyear, String mcode, String userid,String rbkid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "";
		if (Integer.parseInt(wbdcode)> 9) {
			part_key = seasonType + wbdcode + seasonYear;
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear;
		}
		String tableName = "ecrop" + seasonYear + "." + "rbk_surveyno_mapping_" + part_key;
if(seasonYear >= 2023) {
	tableName = "ecrop" + seasonYear + "." + "rbk_surveyno_mapping_" + part_key;
}else {
	tableName = "rbk_surveyno_mapping_" + part_key;
}
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select lgdvname,data_src,occup_extent,kh_no,cr_sno,tot_extent from "+tableName+" \r\n"
				+ "a, wbvillage_mst b where a.vcode=wbvcode and a.mcode=? and rbkcode=? "
				+ " and cr_season=? and cr_year=? \r\n"
				+ "order by lgdvname,cr_sno,kh_no ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(mcode));
		insertQuery.setParameter(2, Integer.parseInt(rbkid));System.out.println("rbkcode----"+ Integer.parseInt(rbkid));
		insertQuery.setParameter(3, seasonType);
		insertQuery.setParameter(4,  seasonYear);
		
		
		System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<AllocatedSurveyNoMapping> detailsEntities = new ArrayList<AllocatedSurveyNoMapping>();
		

		for (Object[] row : detailsEntities1) {

			AllocatedSurveyNoMapping entity = new AllocatedSurveyNoMapping();
			entity.setLgdvname((String) row[0]);
			entity.setData_src(String.valueOf(row[1]));
			entity.setOccup_extent((BigDecimal)row[2]);
			entity.setKh_no(((BigDecimal) row[3]).intValue());
			entity.setCr_sno((String) row[4]);
			entity.setTot_extent((BigDecimal)row[5]);
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}
	

}
