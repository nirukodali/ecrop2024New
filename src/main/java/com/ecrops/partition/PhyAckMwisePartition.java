package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.PhyAckMwise;

@Repository
@Transactional
public class PhyAckMwisePartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<PhyAckMwise> getMPhyAck(String wbdcode,String cropyear) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String part_key = "";
		part_key = seasonType + seasonYear;
		System.out.println("part_key==========>" + part_key);

		String tableName = "ekycgen_rbk_mand_mv_"+part_key;
		String tableName1 = "cr_authdetails_rbk_mand_mv_"+part_key;
		if(seasonYear==2023 && seasonType.equals("S")) {
			tableName = "ekycgen_rbk_mand_mv_"+part_key;
			tableName1 = "cr_authdetails_rbk_mand_mv_"+part_key;
		}
		if(seasonYear>=2023 && !seasonType.equals("S")) {
		 tableName = "ecrop" + seasonYear + "." + "ekycgen_rbk_mand_mv_" + part_key;
		 tableName1 = "ecrop" + seasonYear + "." + "cr_authdetails_rbk_mand_mv_" + part_key;
		}
	

		String Sql = "select r2.mname,coalesce(r2.ekycfarmercount) as ekycfarmercount,coalesce(r1.totfarmers,0) as fcount"
				+ " from (select * from "+ tableName + "  e where wbdcode=?) r1 right join (select cr_mand_code,mname,ekycfarmercount from "
				+ tableName1+ " where cr_dist_code=? order by cr_mand_code) r2 on r1.wbmcode=r2.cr_mand_code order by r2.mname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbdcode));

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<PhyAckMwise> detailsEntities = new ArrayList<PhyAckMwise>();

		for (Object[] row : detailsEntities1) {

			PhyAckMwise entity = new PhyAckMwise();

			entity.setWbmname((String) row[0]);
			entity.setEkycfarmercount(Long.valueOf(row[1].toString()));
			entity.setAckcount(Long.valueOf(row[2].toString()));
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
