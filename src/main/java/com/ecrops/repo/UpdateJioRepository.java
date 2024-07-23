package com.ecrops.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class UpdateJioRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int updatejio(Integer dcode,Integer mcode,Integer vcode) {
		String updateQry ="update ecrop2023.wbvillage_mst set jioreference='Y' where wbdcode=? and wbmcode=? and wbvcode=?";
		Query sql = entityManager.createNativeQuery(updateQry);
		sql.setParameter(1, dcode);		
		sql.setParameter(2, mcode);
		sql.setParameter(3, vcode);
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
	}

	
}
