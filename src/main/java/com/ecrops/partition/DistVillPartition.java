package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DistVill;

@Repository
@Transactional
public class DistVillPartition {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DistVill> distvill(String cropyear,Integer wbdcode,Integer wbmcode){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		String part_key = "",tableName="";
		part_key = seasonType + seasonYear;
		if (seasonYear>=2023) {
        	tableName = "ecrop" + seasonYear + "." + "rep_vill_tabdownloaded_v_" + part_key;
        }
        else {
        	tableName = "rep_vill_tabdownloaded_v_" + part_key;
        }
		
		System.out.println("tableName---------------->" + tableName);

	System.out.println("cropyear======================>>>"+cropyear);

		String sql="select dname,mname,vname,COALESCE(totallps,0) as totallps,COALESCE(lps_downloaded,0) as lps_downloaded,\r\n"
				+ "COALESCE(tvil_ext,0) as  tvil_ext,\r\n"
				+ "COALESCE(vil_ext_downloaded,0) as vil_ext_downloaded from \r\n"
				+ " "+ tableName +" where wbdcode=? and wbmcode=? order by vname  ";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, wbdcode);
		sesnyr.setParameter(2, wbmcode);

		
		List<Object[]> vill = sesnyr.getResultList();
		List<DistVill> entityDetails = new ArrayList<DistVill>();
		

		for (Object[] row : vill) {

			DistVill entity = new DistVill();
			
			
			entity.setDname((String) row[0]);
			entity.setMname((String) row[1]);
			entity.setVname((String) row[2]);
			entity.setTotallps(Long.valueOf(row[3].toString()));
			entity.setLps_downloaded(Long.valueOf(row[4].toString()));
			entity.setTvil_ext((Number) row[5]);
//			entity.setTvil_ext((double) Math.round(Double.valueOf(row[5].toString())));
//			entity.setVil_ext_downloaded((double) Math.round(Double.valueOf(row[6].toString())));

			entity.setVil_ext_downloaded((Number) row[6]);
			
			
			entityDetails.add(entity);

		}
		
		return entityDetails;
	
	}
}
