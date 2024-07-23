package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.ecrops.entity.RepLandDataDetails;

@Repository
@Transactional
public class RepLandDataDetailsPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<RepLandDataDetails> getLandDet(String dcode,String mcode,String activeYear
			) {
//		System.out.println("season========"+cropyear);
//		String[] season = cropyear.split("@");	
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);
		int seasonYear = Integer.parseInt(activeYear);
		
		

	System.out.println("seasonYear========"+seasonYear);

		String tableName;
		if(seasonYear >= 2023) {
			 tableName = "ecrop" + seasonYear + "." + "downloaddet_mand_v" ;
		
		}else {
			 tableName =  "downloaddet_mand_v";	
		}
		
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select mname,totrev,downloadedcnt,notdownloadedcnt,totrec,wbdname,dname,mcode,dcode "
				+ "from "+tableName+"\r\n"
				+ "where dcode=? and mcode=? order by mname";

		
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<RepLandDataDetails> detailsEntities = new ArrayList<RepLandDataDetails>();
		

		for (Object[] row : detailsEntities1) {
			RepLandDataDetails entity = new RepLandDataDetails();
			entity.setMname((String) row[0]);

			BigInteger bigIntegerValue = (BigInteger) row[1];

			BigDecimal bigDecimalValue = new BigDecimal(bigIntegerValue);
			entity.setTotrev(bigDecimalValue);
			BigInteger bigIntegerValue2 = (BigInteger) row[2];			
			BigDecimal bigDecimalValue2 = new BigDecimal(bigIntegerValue2);

			entity.setDownloadedcnt(bigDecimalValue2);
			BigInteger bigIntegerValue3 = (BigInteger) row[3];			

			BigDecimal bigDecimalValue3 = new BigDecimal(bigIntegerValue3);
			entity.setNotdownloadedcnt(bigDecimalValue3);

 		//	entity.setNotdownloadedcnt((BigDecimal )row[3]);
		//	entity.setTotrec((BigDecimal )row[4]);
			
			BigInteger bigIntegerValue4 = (BigInteger) row[4];			

			BigDecimal bigDecimalValue4 = new BigDecimal(bigIntegerValue4);
			entity.setTotrec(bigDecimalValue4);
			
			
			detailsEntities.add(entity);
			System.out.println("detailsEntities"+detailsEntities.toString());
			return detailsEntities;


		}
		return null;
		

	}


}
