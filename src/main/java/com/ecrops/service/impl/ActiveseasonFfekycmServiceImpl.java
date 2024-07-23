package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.ActiveseasonFfekycm;
import com.ecrops.repo.ActiveseasonFfekycmRepository;
import com.ecrops.service.ActiveseasonFfekycmService;

@Service
public class ActiveseasonFfekycmServiceImpl implements ActiveseasonFfekycmService{
	@Autowired
	ActiveseasonFfekycmRepository activeseasonFfekycRepository;
	
	public List<ActiveseasonFfekycm> findAll() {
		return activeseasonFfekycRepository.getCropyear();
	}

}
