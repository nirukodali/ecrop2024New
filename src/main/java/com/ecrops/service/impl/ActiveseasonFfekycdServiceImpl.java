package com.ecrops.service.impl;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.ActiveseasonFfekycd;
import com.ecrops.repo.ActiveseasonFfekycdRepository;
import com.ecrops.service.ActiveseasonFfekycdService;

@Service
public class ActiveseasonFfekycdServiceImpl implements ActiveseasonFfekycdService{
	@Autowired
	ActiveseasonFfekycdRepository activeseasonFfekycdRepository;
	
	public List<ActiveseasonFfekycd> findAll() {
		return activeseasonFfekycdRepository.getCropyear();
	}

}
