package com.ecrops.repo;

import java.sql.Timestamp; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.LastLoginTime;

@Repository

public interface LastLoginRepo extends JpaRepository<LastLoginTime, String> {
	
	@Query(value="select lastlogin_ts from user_registration where userid=:userid",nativeQuery = true)
	public String getLastLogin(@Param("userid") String userid);
	
	@Query(value="update user_registration set lastlogin_ts = NOW()   where userid=:userid",nativeQuery = true)
	public Boolean updateLastLogin(@Param("userid") String userid);
	
//	 + INTERVAL '5 hours 30 minutes' 
	 
//	@Query(value="update user_registration set lastlogin_ts=now() where userid=:userid",nativeQuery = true)
//	public void updateLastLogin(@Param("userid") String userid);

}
