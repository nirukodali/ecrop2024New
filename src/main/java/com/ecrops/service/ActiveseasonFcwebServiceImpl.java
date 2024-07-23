package com.ecrops.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.ActiveseasonFcweb;
import com.ecrops.repo.ActiveseasonFcwebRepository;

@Service
public class ActiveseasonFcwebServiceImpl implements ActiveseasonFcwebService{
	
	@Autowired
	ActiveseasonFcwebRepository activeseasonFcwebRepository;
	
	public List<ActiveseasonFcweb> findAll(){
		return activeseasonFcwebRepository.getCropyear();
	}

}
