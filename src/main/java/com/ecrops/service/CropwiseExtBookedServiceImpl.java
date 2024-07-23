package com.ecrops.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecrops.entity.CropwiseExtBooked;
import com.ecrops.repo.CropwiseExtBookedRepository;

@Service
public class CropwiseExtBookedServiceImpl implements CropwiseExtBookedService{
	@Autowired
	CropwiseExtBookedRepository cropwiseExtBookedRepository;
	
	public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear,String cropname,String crpgrp,int role){
		return cropwiseExtBookedRepository.getCropwise(dcode,sescrpyear,cropname,crpgrp,role);
	}
	
	public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear,String cropname,String crpgrp,String mcode){
		return cropwiseExtBookedRepository.getCropwise(dcode,sescrpyear,cropname,crpgrp,mcode);
	}
		
		public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear){
			return cropwiseExtBookedRepository.getCropwise(dcode,sescrpyear);
			

		
	}

}
