package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.CropInsGrievance;


@Repository
@Transactional
public class CropInsGrievancePartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropInsGrievance> getCropIns(String dcode, String mcode, String cropyear) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
//
//		String part_key = "";
//
//		part_key = seasonType + seasonYear;
		
		String tableName =  "rep_cropins_gri_v" ;
		
//		if (seasonYear >= 2023) {
//			tableName = "ecrop" + seasonYear + "." + "rep_cropins_gri_v" ;
//		} else {
//			tableName = "rep_cropins_gri_v" ;
//		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbdname,wbmname, wbvname,claimid,claim_type,gri_type,old_cropname, new_cropname,old_varietyname,\r\n"
				+ "new_varietyname,old_extent, new_extent,old_name,new_name,old_uid,concat('********',substr(new_uid,8,4)) as newuid ,remarks \r\n"
				+ "from "+tableName+" where dcode=? and mcode=? and cropyear=? and season=?";
		

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, seasonYear);
		insertQuery.setParameter(4, seasonType);

		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		List<CropInsGrievance> detailsEntities = new ArrayList<CropInsGrievance>();

		try {

			for (Object[] row : detailsEntities1) {
				CropInsGrievance entity = new CropInsGrievance();System.out.println("row[0]===========>"+row[0].toString());
				entity.setWbdname((String) row[0]);
				entity.setWbmname((String) row[1]);
				entity.setWbvname((String) row[2]);
				entity.setClaimid(((String) row[3]));
				entity.setClaim_type((String) row[4]);
				entity.setGri_type((String) row[5]);
				if(row[6]!=null) {
				entity.setOld_cropname((String) row[6]);
				}
				if(row[7]!=null) {
				entity.setNew_cropname((String) row[7]);
				}
				if(row[8]!=null) {
				entity.setOld_varietyname((String) row[8]);
				}
				if(row[9]!=null) {
				entity.setNew_varietyname((String) row[9]);
				}
				if(row[10]!=null) {
				entity.setOld_extent((String) row[10]);
				}
				if(row[11]!=null) {
				entity.setNew_extent((String) row[11]);
				}
				if(row[12]!=null) {
				entity.setOld_name((String) row[12]);
				}
				if(row[13]!=null) {
				entity.setNew_name((String) row[13]);
				}
				if(row[14]!=null) {
				entity.setOld_uid((String) row[14]);
				}
				if(row[15]!=null) {
				entity.setNewuid((String) row[15]);
				}
				entity.setRemarks((String) row[16]);
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
