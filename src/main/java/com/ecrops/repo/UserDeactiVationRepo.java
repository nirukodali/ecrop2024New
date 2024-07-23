package com.ecrops.repo;



//import java.net.http.HttpRequest;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class UserDeactiVationRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int insertIntoUserDeactivation(String userId) {
		int executeUpdate=0;
			
	String query ="INSERT INTO users_deactivated SELECT *  FROM user_registration  WHERE userid = '"+userId+"'";
	System.out.println(query+"y");
	 Query insertQuery = entityManager.createNativeQuery(query);
	 executeUpdate = insertQuery.executeUpdate();
		return executeUpdate;
		
	}
	
	
@Transactional
	public int userUpdation(String name, String mobile, String email, String aadhaar, String userid,String sts,String role) {
		int executeUpdate=0;
		 String query="";
	  if(Integer.parseInt(role)==30 && sts.equalsIgnoreCase("I")) {
		   query ="UPDATE user_registration  SET regular_sts='"+sts+"', name=?,mobile_phone=?,emailid=?,tempuid=? WHERE userid =? and status='A' ";
	  }
	  else if(Integer.parseInt(role)==30 && sts.equalsIgnoreCase("R")) {
		   query ="UPDATE user_registration  SET regular_sts='"+sts+"', name=?,mobile_phone=?,emailid=?,aadhaar_id=? WHERE userid =? and status='A' ";
	  }
	  else {
		   query ="UPDATE user_registration SET name=?,mobile_phone=?,emailid=?,aadhaar_id=? WHERE userid =? and status='A' ";
	  }
	 Query insertQuery = entityManager.createNativeQuery(query);
	 
	 insertQuery.setParameter(1, name);
	 insertQuery.setParameter(2, mobile);
	 insertQuery.setParameter(3, email);
	 insertQuery.setParameter(4, aadhaar);
	 insertQuery.setParameter(5, userid);
	 
	 executeUpdate = insertQuery.executeUpdate();
		return executeUpdate;
	}


@Transactional
public int insertIntoTraceWeb(String name, String userid, String role,HttpServletRequest httpRequest) {
	int executeUpdate=0;
		
String query ="insert into tracerweb (username,affecteduser,status,clientip,role) values (?,?,?,?,?) ";
  
 Query insertQuery = entityManager.createNativeQuery(query);
 insertQuery.setParameter(1, name);
 insertQuery.setParameter(2, userid);
 insertQuery.setParameter(3, "updated by self");
 insertQuery.setParameter(4, httpRequest.getRemoteAddr());
 insertQuery.setParameter(5, role);
 
 executeUpdate = insertQuery.executeUpdate();
	return executeUpdate;
}


}


