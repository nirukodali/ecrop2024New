package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropvarietiesRep;
import com.ecrops.repo.CropvarietiesRepRepository;

@Service
public class CropvarietiesRepServiceImpl {
	
	@Autowired
	CropvarietiesRepRepository cropvarietiesRepRepository;
	
	public List<CropvarietiesRep> findAll(int grpcode){
		return cropvarietiesRepRepository.findByCropnamesFCVR_CropgroupsFCVRCropgrpid(grpcode);
	}

}
