package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BlockedEfishExtent;


@Repository
@Transactional
public class BlockedEfishExtentPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<BlockedEfishExtent> findByExtent(String dcode, String mcode,String wbvcode) {

//		String[] season = cropyear.split("@");
//		System.out.println("season=========" + season);
//		String seasonType = season[0];
//		System.out.println("seasonType=========" + seasonType);
//		Integer seasonYear = Integer.parseInt(season[1]);
//		System.out.println("seasonYear=========" + seasonYear);

		String tableName;
		String tableName1;
		
		
			tableName = "ecrop2023." + "cr_details_efish_2023";
		//	tableName1 = "ecrop" + seasonYear + "." + "wbvillage_mst";
			

		
			
			
			tableName1 = "wbvillage_mst";
			

		

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);
		

		String Sql = "select village_name as wbvname,pattadar_name as ocname,pattadar_father_name as ocfname,occupant_name  as farmer_name,\r\n"
				+ "occupant_father_name  as father_name, cr_sno  as survey_no, kh_no,total_extent as tot_extent, \r\n"
				+ "occupant_extent,( cast(occupant_extent as numeric)-cast(mapped_extent as numeric) ) as avail_ext,mapped_extent, \r\n"
				+ "b.wbvcode,booking_available,allowable_ext,b.dcode,b.mcode from "+tableName+" a,\r\n"
				+ " "+tableName1+" b where booking_available is null and  \r\n"
				+ "a.cr_vcode=cast(b.wbvcode as character varying)  and  b.dcode=? and b.mcode=? \r\n"
				+ "and b.wbvcode=? order by wbvname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, Integer.parseInt(wbvcode));

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<BlockedEfishExtent> detailsEntities = new ArrayList<BlockedEfishExtent>();

		for (Object[] row : detailsEntities1) {

			BlockedEfishExtent entity = new BlockedEfishExtent();

			entity.setWbvname((String) row[0]);
			entity.setOcname((String) row[1]);
			entity.setOcfname((String) row[2]);
			entity.setFarmer_name((String) row[3]);
			entity.setFather_name((String) row[4]);
			entity.setSurvey_no((String) row[5]);
			entity.setKh_no((String) row[6]);
			entity.setTot_extent((String) row[7]);
			entity.setOccupant_extent((String) row[8]);
			entity.setAvail_ext((BigDecimal) row[9]);
			entity.setMapped_extent((String) row[10]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
