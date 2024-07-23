package com.ecrops.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropgroupsFCWR;
import com.ecrops.repo.CropgroupsFCWRRepository;

@Repository
public class CropgroupsFCWRServiceImpl {
	
	@Autowired
	CropgroupsFCWRRepository cropgroupsFCWRRepository;
	
	public List<CropgroupsFCWR> findAll(){
		return cropgroupsFCWRRepository.getCropGroup();
	}

}
