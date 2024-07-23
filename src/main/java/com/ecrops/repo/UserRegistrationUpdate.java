package com.ecrops.repo;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component; 

@Component
public class UserRegistrationUpdate {
	
	@javax.persistence.PersistenceContext
	private javax.persistence.EntityManager entityManager;
	
	@javax.transaction.Transactional
	public int updateUserReg(String newpassword, String username, String dcode) {
		String qry="update user_registration set retype_password=?, encpassword=? ,last_pwdupd_dt=now() where userid=?";
	     Query   query =   entityManager.createNativeQuery(qry);
		query.setParameter(1, newpassword);
		query.setParameter(2, newpassword);
		query.setParameter(3, username);
		int executeUpdate = query.executeUpdate();
		return executeUpdate;
	}
	
	@Transactional
	public int insertTracer(String userid, String username, String dcode) {
		String qry="insert into tracerweb(username,status,affecteduser) values(?,?,?)";
	    Query query = entityManager.createNativeQuery(qry);
		query.setParameter(1, userid);
		query.setParameter(2, "Password Reset by Admin");
		query.setParameter(3, username);
		int executeUpdate = query.executeUpdate();
		return executeUpdate;
	}

}
