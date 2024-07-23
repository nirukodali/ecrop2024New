package com.ecrops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Cropnames;
import com.ecrops.repo.CropnamesRepository;
import com.ecrops.service.CropnamesService;

@Service
public class CropnamesServiceImpl implements CropnamesService{
	@Autowired
	private CropnamesRepository cropnamesRepository;
	
	
	@Override
	public Cropnames addCropname(Cropnames cropnames) {
		return cropnamesRepository.save(cropnames);
	}

}


