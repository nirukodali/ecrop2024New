package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.WbMaster;
import com.ecrops.repo.VillageRepository;
import com.ecrops.service.VillageService;

@Service
public class VillageServiceImpl implements VillageService {
	
	@Autowired 
	VillageRepository villageRepository;

	@Override
	public List<WbMaster> findAll(int dcode, int mcode) {
		return villageRepository.getVillages(dcode, mcode);
	}

	@Override
	public List<WbMaster> getwbVillages(Integer dcode, Integer mcode) {
		// TODO Auto-generated method stub
		return villageRepository.getwbVillages(dcode, mcode);
	}

	@Override
	public List<WbMaster> getnotjiovcode(Integer dcode, Integer mcode) {
		// TODO Auto-generated method stub
		return villageRepository.getnotjiovcode(dcode, mcode);
	}

	

}
