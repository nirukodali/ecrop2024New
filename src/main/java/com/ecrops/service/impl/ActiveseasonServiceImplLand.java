package com.ecrops.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.ActiveseasonLand;
import com.ecrops.repo.ActiveseasonRepositoryLand;



@Service
public class ActiveseasonServiceImplLand {
	
	@Autowired
	ActiveseasonRepositoryLand activeseasonRepository;
	
	public List<ActiveseasonLand> findAll(){
		return activeseasonRepository.getActiveseason();
	}

}
