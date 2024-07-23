package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.projection.VroRejectDropdownProjection;
import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	@Autowired private ActiveSeasonRepository activeSeasonRepository;
	
	@Override
	public List<VroRejectDropdownProjection> getActiveSeason() {
		// TODO Auto-generated method stub
		return null;
	//	return activeSeasonRepository.getActiveSeason();
	}

}
