package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropgroupsFCVR;
import com.ecrops.repo.CropgroupsFCVRRepository;
import com.ecrops.service.CropgroupsFCVRService;

@Service
public class CropgroupsFCVRServiceImpl implements CropgroupsFCVRService {
   
	@Autowired
	CropgroupsFCVRRepository cropgroupsFCVRRepository;
	
	@Override
	public List<CropgroupsFCVR> findAll() {
		return cropgroupsFCVRRepository.getCropGroup();
	}

}
