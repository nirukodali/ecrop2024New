package com.ecrops.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class ADA_Incharge_Save_Repo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int updateADAIch(String maoRecList, String justifyIdList, Integer district, Integer mandal,
			String rbkusersList, String empcodeList) {
		String updateQry = "update ecrop2023.emp_rbk_map_incharges set ts_ada_appr=now(),ada_appr=?,ada_remarks=? where   dcode=? and mcode=? and rbkuserid=? and  cast(empcode as character varying)=?";
		Query sql = entityManager.createNativeQuery(updateQry);
		sql.setParameter(1, maoRecList);
		sql.setParameter(2, justifyIdList);
		sql.setParameter(3, district);
		sql.setParameter(4, mandal);
		sql.setParameter(5, rbkusersList);
		sql.setParameter(6, empcodeList);
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
	}
}
