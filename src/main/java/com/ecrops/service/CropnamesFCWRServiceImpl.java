package com.ecrops.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropnamesFCWR;
import com.ecrops.repo.CropnamesFCWRRepository;

@Service
public class CropnamesFCWRServiceImpl implements CropnamesFCWRService{
	
	@Autowired
	CropnamesFCWRRepository cropnamesFCWRRepository;
	
	public List<CropnamesFCWR> findAll(int grpcode){
       return cropnamesFCWRRepository.getCropname(grpcode);
	}

}
