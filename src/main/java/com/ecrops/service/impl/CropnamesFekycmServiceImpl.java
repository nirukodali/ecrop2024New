package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropnamesFekycm;
import com.ecrops.repo.CropnamesFekycmRepository;
import com.ecrops.service.CropnamesFekycmService;

@Service
public class CropnamesFekycmServiceImpl implements CropnamesFekycmService {

	@Autowired 
    private CropnamesFekycmRepository  cropnamesFekycmRepository;

	@Override
	public List<CropnamesFekycm> findAll() {
		
		 return cropnamesFekycmRepository.getCropNames();
	}
	
	
}
