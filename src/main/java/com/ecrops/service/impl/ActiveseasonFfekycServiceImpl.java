package com.ecrops.service.impl;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.ActiveseasonFfekyc;
import com.ecrops.repo.ActiveseasonFfekycRepository;
import com.ecrops.service.ActiveseasonFfekycService;

@Service
public class ActiveseasonFfekycServiceImpl implements ActiveseasonFfekycService{
	@Autowired
	ActiveseasonFfekycRepository activeseasonFfekycRepository;
	
	public List<ActiveseasonFfekyc> findAll() {
		return activeseasonFfekycRepository.getCropyear();
	}

}
