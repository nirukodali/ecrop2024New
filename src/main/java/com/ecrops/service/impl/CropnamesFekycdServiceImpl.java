package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropnamesFekycd;
import com.ecrops.repo.CropnamesFekycdRepository;
import com.ecrops.service.CropnamesFekycdService;

@Service
public class CropnamesFekycdServiceImpl implements CropnamesFekycdService {

	@Autowired 
    private CropnamesFekycdRepository  cropnamesFekycdRepository;

	@Override
	public List<CropnamesFekycd> findAll() {
		
		 return cropnamesFekycdRepository.getCropNames();
	}
	
	
}
