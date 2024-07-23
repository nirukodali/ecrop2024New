package com.ecrops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.repo.LastLoginRepo;
import com.ecrops.service.LastLoginService;

@Service
public class LastLoginImpl implements LastLoginService{
	
	@Autowired
	LastLoginRepo lastLoginRepo;
	
	public String getLastLogin(String userid) {
		return lastLoginRepo.getLastLogin(userid);
	}
	
	public boolean updateLastLogin(String userid) {
		return lastLoginRepo.updateLastLogin(userid);
	}

}
