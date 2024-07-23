package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropgroupsFCNR;
import com.ecrops.repo.CropgroupsFCNRRepository;

@Service
public class CropgroupsFCNRServiceImpl {
	
	@Autowired 
    private CropgroupsFCNRRepository  cropgroupsFCNRRepository;
	
	
	public List<CropgroupsFCNR> findAll() {
		return cropgroupsFCNRRepository.getCropGroup();
	}

}
