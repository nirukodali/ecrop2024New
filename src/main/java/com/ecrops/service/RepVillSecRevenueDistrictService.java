package com.ecrops.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecrops.entity.WbvillageMstEntity;
import com.ecrops.repo.ActiveSeasonRepository.RepVillSecRevenueDistProj;

@Service
public interface RepVillSecRevenueDistrictService {
	public List<WbvillageMstEntity> findByDcode(Integer dcode);

	public List<RepVillSecRevenueDistProj> getRevvillagesMandals(int dcode, int mcode);

}
