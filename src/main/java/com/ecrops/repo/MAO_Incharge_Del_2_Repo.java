package com.ecrops.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class MAO_Incharge_Del_2_Repo {

	@PersistenceContext
	private EntityManager entityManager;
	@Transactional
	public int deletemaoIch(Integer district, Integer mandal, String rbkusersList, String empcodeList) {
		String deleteQry = "delete from  ecrop2024.emp_rbk_map_incharges  where   dcode=? and mcode=? and rbkuserid=? and  cast(empcode as character varying)=?";
		Query sql = entityManager.createNativeQuery(deleteQry);
		sql.setParameter(1, district);
		sql.setParameter(2, mandal);
		sql.setParameter(3, rbkusersList);
		sql.setParameter(4, empcodeList);
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
	}
}
