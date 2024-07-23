package com.ecrops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.WatersourcesRep;
import com.ecrops.repo.WatersourcesRepRepository;
import com.ecrops.service.WatersourcesRepService;

@Service
public class WatersourcesRepServiceImpl implements WatersourcesRepService {

	@Autowired 
    private WatersourcesRepRepository  repwatersourcesRepository;
	
	public List<WatersourcesRep> findAll() {
		return repwatersourcesRepository.getWaterSources();
	}
	
	
}
