package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.ActiveseasonFsr;
import com.ecrops.repo.ActiveseasonFsrRepository;
import com.ecrops.service.ActiveseasonFsrService;

@Service
public class ActiveseasonFsrServiceImpl implements ActiveseasonFsrService{
	@Autowired
	ActiveseasonFsrRepository activeseasonFsrRepository;
	
	public List<ActiveseasonFsr> findAll() {
		return activeseasonFsrRepository.getCropyear();
	}

}
