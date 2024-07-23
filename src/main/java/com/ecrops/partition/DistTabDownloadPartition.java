package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DistTabDownload;
import com.ecrops.entity.RbkSurveyNoMapping;

@Repository
@Transactional
public class DistTabDownloadPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<DistTabDownload> disttab(String cropyear) {
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "",tableName="";
		part_key = seasonType + seasonYear;
		if (seasonYear>=2023) {
        	tableName = "ecrop" + seasonYear + "." + "rep_dist_tabdownloaded_v_" + part_key;
        }
        else {
        	tableName = "rep_dist_tabdownloaded_v_" + part_key;
        }
		
		System.out.println("tableName---------------->" + tableName);

	

		String sql="select dname,totrevvill,totprepareddatavill,tabdownloadedvill,totallps,totlps_downloaded,wbdcode \r\n"
				+ "from "+ tableName +" order by dname ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		List<Object[]> dist = sesnyr.getResultList();
		//System.out.println("ekycMao=>"+ekycMao.size());
		//System.out.println("ekycMao=>"+ekycMao.toString());
		List<DistTabDownload> entityDetails = new ArrayList<DistTabDownload>();
		

		for (Object[] row : dist) {

			DistTabDownload entity = new DistTabDownload();
			
			System.out.println("row[0]=>"+row[0]);
			System.out.println("row[1]=>"+row[1]);
			
			entity.setDname((String) row[0]);
			entity.setTotrevvill(((BigDecimal) row[1]).intValue());
			entity.setTotprepareddatavill(((BigDecimal) row[2]).intValue());
			entity.setTabdownloadedvill(((BigDecimal) row[3]).intValue());
			entity.setTotallps(((BigDecimal) row[4]).intValue());
			entity.setTotlps_downloaded(((BigDecimal) row[5]).intValue());
			entity.setWbdcode(((Integer) row[6]).intValue());
			
			entityDetails.add(entity);

		}
		
		return entityDetails;
	
	}
}
