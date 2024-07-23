package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.UserRegForProfileUpdateEntity;
import com.ecrops.entity.User_registration;
import com.ecrops.projection.ActiveSeasonProjection;

@Repository
public interface UserRegistrationRepoForProfile extends JpaRepository<UserRegForProfileUpdateEntity, String> {

	

List<UserRegForProfileUpdateEntity> findByAadharIdAndUseridNot(String adhar,String userid);


	
	
}