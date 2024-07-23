package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropnamesRep;
import com.ecrops.repo.CropnamesRepRepository;
import com.ecrops.service.CropnamesRepService;

@Service
public class CropnamesRepServiceImpl implements CropnamesRepService {

	@Autowired 
    private CropnamesRepRepository  cropnamesRepRepository;
	
	
	public List<CropnamesRep> findAll(int cropgrp) {
		return cropnamesRepRepository.getCropNames(cropgrp);
	}
	
	
}
