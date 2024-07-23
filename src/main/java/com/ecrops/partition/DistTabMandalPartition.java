package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DistTabMandal;

@Repository
@Transactional
public class DistTabMandalPartition {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DistTabMandal> distmand(String cropyear,Integer wbdcode){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "",tableName="";
		part_key = seasonType + seasonYear;
		if (seasonYear>=2023) {
        	tableName = "ecrop" + seasonYear + "." + "rep_mand_tabdownloaded_v_" + part_key;
        }
        else {
        	tableName = "rep_mand_tabdownloaded_v_" + part_key;
        }
		
		System.out.println("tableName---------------->" + tableName);

	

		String sql="select mname,totrevvill,totprepareddatavill,tabdownloadedvill,totallps,lps_downloaded,wbdcode,wbmcode\r\n"
				+ "from "+ tableName +" where wbdcode=? order by dname ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, wbdcode);

		
		List<Object[]> mand = sesnyr.getResultList();
		List<DistTabMandal> entityDetails = new ArrayList<DistTabMandal>();
		

		for (Object[] row : mand) {

			DistTabMandal entity = new DistTabMandal();
			
			
			entity.setMname((String) row[0]);
			entity.setTotrevvill(Long.valueOf(row[1].toString()));
			
			if (row[2] != null) {
			    entity.setTotprepareddatavill(Long.valueOf(row[2].toString()));
			} else {
			    entity.setTotprepareddatavill(null);
			}

			if (row[3] != null) {
			    entity.setTabdownloadedvill(Long.valueOf(row[3].toString()));
			} else {
			    entity.setTabdownloadedvill(null);
			}
			
			if (row[4] != null) {
			    entity.setTotallps(Long.valueOf(row[4].toString()));
			} else {
			    entity.setTotallps(null);
			}
			
			if (row[5] != null) {
			    entity.setLps_downloaded(Long.valueOf(row[5].toString()));
			} else {
			    entity.setLps_downloaded(null);
			}
			entity.setWbdcode(((Integer) row[6]).intValue());
			entity.setWbmcode(((Integer) row[7]).intValue());

			entityDetails.add(entity);

		}
		
		return entityDetails;
	
	}
	

}
