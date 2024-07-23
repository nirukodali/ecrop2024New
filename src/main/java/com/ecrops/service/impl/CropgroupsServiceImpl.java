package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.Cropgroups;
import com.ecrops.repo.CropgroupsRepository;
import com.ecrops.service.CropgroupsService;

@Service
public class CropgroupsServiceImpl implements CropgroupsService {

	@Autowired 
    private CropgroupsRepository  cropgroupsRepository;
	
	public List<Cropgroups> findAll() {
		return cropgroupsRepository.getCropGroup();
	}
	
	
}

