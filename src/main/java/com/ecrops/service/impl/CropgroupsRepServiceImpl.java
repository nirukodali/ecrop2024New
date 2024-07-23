package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropgroupsRep;
import com.ecrops.repo.CropgroupsRepRepository;
import com.ecrops.service.CropgroupsRepService;

@Service
public class CropgroupsRepServiceImpl implements CropgroupsRepService {

	@Autowired 
    private CropgroupsRepRepository  cropgroupsRepRepository;
	
	public List<CropgroupsRep> findAll() {
		return cropgroupsRepRepository.getCropGroup();
	}
	
	
}
