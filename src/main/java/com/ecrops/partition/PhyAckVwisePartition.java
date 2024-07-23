package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.PhyAckVwise;


@Repository
@Transactional
public class PhyAckVwisePartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<PhyAckVwise> getPhyAck( String wbdcode,String wbmcode,String cropyear,String userid) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String seasonType = season[0];
		System.out.println("seasonType========="+seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear========="+seasonYear);

		String part_key = "";

		part_key = seasonType  + seasonYear; System.out.println("part_key==========>"+part_key);
		String tableName;
		String tableName1;
		if(seasonYear >=2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
		 tableName = "ecrop" + seasonYear + "." + "ekycgenerated_vill_mv_" + part_key; 
		 tableName1 = "ecrop" + seasonYear + "." + "cr_authdetails_vill_mv_" + part_key; 
		}else {
			 tableName = "ekycgenerated_vill_mv_" + part_key; 
			 tableName1 = "cr_authdetails_vill_mv_" + part_key; 	
		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select vname as wbvname, coalesce(a.totfarmers,0) as ackcount,coalesce(c.ekycfarmercount,0) as ekycfarmercount ,\r\n"
				+ "cast(c.cr_dist_code as character varying) as wbdcode,cast(c.cr_mand_code as character varying) as wbmcode,c.dname as wbdname,\r\n"
				+ "c.mname as wbmname from "+tableName+" a \r\n"
				+ "right join ( select * from "+tableName1+" ) c on a.wbvcode=c.cr_vcode  where c.cr_dist_code=? \r\n"
				+ "and c.cr_mand_code=?  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<PhyAckVwise> detailsEntities = new ArrayList<PhyAckVwise>();
		

		for (Object[] row : detailsEntities1) {

			PhyAckVwise entity = new PhyAckVwise();
			
			entity.setWbvname((String) row[0]);
			entity.setEkycfarmercount(Long.valueOf(row[1].toString()));
			entity.setAckcount(Long.valueOf(row[2].toString()));
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}
	
	

}
