package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AbstractExtentBookedEntity;

@Repository
@Transactional
public class AbstractExtentBookedRepo {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<AbstractExtentBookedEntity> getabstractExtentBooked(String dcode ,String cropyear,String cropgrp, String cropid) {
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String tableName = "rep_vill_wise_cropwise_ext_v_" ;
		
		tableName = tableName+seasonType+seasonYear;
				
		if (seasonYear>=2023){
			tableName = "ecrop" + seasonYear + "." + tableName;
		} else {
			tableName = tableName ;
		}
		System.out.println("tableName==================" + tableName);
	
		String Sql="";
		
		if((cropgrp == "" || cropgrp.isEmpty()) && (cropid == "" || cropid.isEmpty())) {
		 Sql = "select mname,sum(totextent) as totext from "+tableName +" where dcode="+dcode+" and cr_year="+seasonYear+" "
			     + "and cr_season='"+seasonType+"' group by mcode,mname order by mname";
		}
		else if((cropgrp != "" || !cropgrp.isEmpty()) && (cropid == "" || cropid.isEmpty())) {
			Sql = "select mname,sum(totextent) as totext from "+tableName +" where dcode="+dcode+" and grpcode="+cropgrp+" and cr_year="+seasonYear+" "
					+ "and cr_season= '"+seasonType+"' group by mcode,mname order by mname ";
		}
		else{
			Sql= "select mname,sum(totextent) as totext from "+tableName +" where dcode="+dcode+" and cr_crop="+cropid+" and cr_year="+seasonYear+" "
					+ "and cr_season= '"+seasonType+"' group by mcode,mname order by mname ";	
		}
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<AbstractExtentBookedEntity> detailsEntities = new ArrayList<AbstractExtentBookedEntity>();
		try {
			for (Object[] row : detailsEntities1) {
				AbstractExtentBookedEntity entity = new AbstractExtentBookedEntity();
				entity.setMname((String) row[0]);
				entity.setTotext((BigDecimal) row[1]);
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
