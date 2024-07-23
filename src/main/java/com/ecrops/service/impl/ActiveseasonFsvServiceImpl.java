package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.ActiveseasonFsv;
import com.ecrops.repo.ActiveseasonFsvRepository;
import com.ecrops.service.ActiveseasonFsvService;

@Service
public class ActiveseasonFsvServiceImpl implements ActiveseasonFsvService{
	@Autowired
	ActiveseasonFsvRepository activeseasonFfekycRepository;
	
	public List<ActiveseasonFsv> findAll() {
		return activeseasonFfekycRepository.getCropyear();
	}

}
