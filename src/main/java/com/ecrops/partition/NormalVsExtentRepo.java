package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.NormalVsExtentEntity;

@Repository
@Transactional
public class NormalVsExtentRepo {
	@PersistenceContext
	private EntityManager entityManager;

	public List<NormalVsExtentEntity> getnormalVsextent(String cropid ,String dcode,String mcode, String cropyear) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String tableName = "mand_crop_normalareas_v" ;
		if (seasonYear>=2023){
			tableName = "ecrop" + seasonYear + "." + tableName;
		} else {
			tableName = "mand_crop_normalareas_v" ;
		}
		System.out.println("tableName==================" + tableName);
		
		
		String Sql = "select mname,normalarea,bookedext from "+tableName+" where cropcode="+cropid+" and\r\n"
				+ "dcode="+dcode+"  and cropyear="+seasonYear+" and season='"+seasonType+"'";
		
		if(mcode == "" || mcode.isEmpty() || mcode == null) {
		}
		else{
			Sql+= "	 and mcode =	"+mcode;
		}
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<NormalVsExtentEntity> detailsEntities = new ArrayList<NormalVsExtentEntity>();
		try {
			for (Object[] row : detailsEntities1) {
				NormalVsExtentEntity entity = new NormalVsExtentEntity();
				entity.setMname((String) row[0]);
				entity.setNormalarea((BigDecimal) row[1]);
				entity.setBookedext((BigDecimal) row[2]);	
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
