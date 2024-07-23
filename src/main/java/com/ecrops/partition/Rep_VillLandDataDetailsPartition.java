package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.ecrops.entity.Rep_VillLandDataDetails;

@Repository
@Transactional
public class Rep_VillLandDataDetailsPartition {
	
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Rep_VillLandDataDetails> getVillData(String dcode,
			String mcode,String cropyear) {
		
//		String[] season = cropyear.split("@");System.out.println("season========"+season);
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);
		
//	System.out.println("seasonType========"+seasonType);
//	System.out.println("seasonYear========"+seasonYear);

		String tableName;
		if(Integer.parseInt(cropyear) >= 2023) {
			 tableName = "ecrop" + cropyear + "." + "downloaddet_v" ;
		
		}else {
			 tableName =  "downloaddet_v";	
		}
		
		System.out.println("tableName---------------->" + tableName);

		String Sql = "SELECt wbvname,no_of_records,mcode,wbmcode,\r\n"
				+ "dcode,wbdname,dname,mname from "+tableName+"\r\n"
				+ " where dcode=? and mcode=? order by dname,mname";

		
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<Rep_VillLandDataDetails> detailsEntities = new ArrayList<Rep_VillLandDataDetails>();
		

		for (Object[] row : detailsEntities1) {
			Rep_VillLandDataDetails entity = new Rep_VillLandDataDetails();
			entity.setWbvname((String) row[0]);
			entity.setNo_of_records((Integer)row[1]);
			
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}

}
