package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.HoMandal;
import com.ecrops.repo.MandalRepository;
import com.ecrops.service.MandalService;

@Service
public class MandalServiceImpl implements MandalService {
	
	@Autowired
	MandalRepository mandalRepository;

	@Override
	public List<HoMandal> findAll(int dcode) {
		return mandalRepository.getMandals(dcode);
	}

}
