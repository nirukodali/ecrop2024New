package com.ecrops.service.impl;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.WbvillageMstEntity;
import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.repo.ActiveSeasonRepository.RepVillSecRevenueDistProj;
import com.ecrops.repo.RepVillSecRevenueDistrictRepo;
import com.ecrops.service.RepVillSecRevenueDistrictService;

@Service
public class RepVillSecRevenueDistrictServiceImpl implements RepVillSecRevenueDistrictService {

	@Autowired
	RepVillSecRevenueDistrictRepo repVillSecRevenueDistrictRepo;

	@Autowired
	ActiveSeasonRepository activeSeasonRepository;

	@Override
	public List<WbvillageMstEntity> findByDcode(Integer dcode) {

		return repVillSecRevenueDistrictRepo.findByDcode(dcode);
	}

	public List<RepVillSecRevenueDistProj> getRevvillagesMandals(int dcode, int mcode) {
		return activeSeasonRepository.getRevvillagesMandals(dcode, mcode);
	}

}
