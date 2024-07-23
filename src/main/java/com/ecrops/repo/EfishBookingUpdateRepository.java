package com.ecrops.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class EfishBookingUpdateRepository {

	
	public EfishBookingUpdateRepository() {
		// TODO Auto-generated constructor stub
	}
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int updatebooking(Integer recid,String allowable) {
		String updateQry ="update ecrop2023.cr_details_efish_2023  set booking_available='Y' , allowable_ext=?  where  recid=?  ";
		Query sql = entityManager.createNativeQuery(updateQry);
		sql.setParameter(1, allowable);	
		sql.setParameter(2, recid);	
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
		
	}
	
}
