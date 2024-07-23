package com.ecrops.partition;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.ecrops.entity.ObjUnobj;

@Repository
@Transactional
public class ObjUnobjPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<ObjUnobj> getObjObj() {

//		String[] season = cropyear.split("@");
//		System.out.println("season=========" + season);
//		String seasonType = season[0];
//		System.out.println("seasonType=========" + seasonType);
//		Integer seasonYear = Integer.parseInt(season[1]);
//		System.out.println("seasonYear=========" + seasonYear);

		String tableName;
		String tableName1;
			tableName = "obj_unobj";

		System.out.println("tableName---------------->" + tableName);

		String Sql = "select cast(code as character varying) as code ,category,crb_remarks,remarks \r\n"
				+ "from "+tableName+" order by crb_remarks";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<ObjUnobj> detailsEntities = new ArrayList<ObjUnobj>();

		for (Object[] row : detailsEntities1) {

			ObjUnobj entity = new ObjUnobj();

			entity.setCode((String) row[0]);
			entity.setCategory((String) row[1]);
			entity.setCrb_remarks((String) row[2]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
