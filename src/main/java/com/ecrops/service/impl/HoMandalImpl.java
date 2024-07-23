package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.repo.AddHodetailsRepository;
import com.ecrops.projection.HoDisplay;
import com.ecrops.projection.HoMapping;
import com.ecrops.projection.UnMappingProj;
import com.ecrops.repo.HoMapRepo;
import com.ecrops.service.HoMandalService;
@Service
public class HoMandalImpl implements HoMandalService {

	@Autowired
	private HoMapRepo hoMapRepo;
	
	@Autowired
	private AddHodetailsRepository addHodetailsRepository;

	@Override
	public List<HoMapping> getMandal(Integer dcode) {
		// TODO Auto-generated method stub
		return hoMapRepo.getMandal(dcode);
	}

	@Override
	public List<HoMapping> getunmapMandal(Integer dcode) {
		// TODO Auto-generated method stub
		return hoMapRepo.getunmapMandal(dcode);
	}

	@Override
	public List<HoDisplay> getMappedDetails(Integer dcode) {
		// TODO Auto-generated method stub
		return hoMapRepo.getMappedDetails(dcode);
	}

	@Override
	public int addHoDetails(Integer district, Integer smcode, Integer hmcode) {
		// TODO Auto-generated method stub
		return addHodetailsRepository.addHoDetails(district, smcode, hmcode);
	}

	public List<HoMapping> usercheck(String hmcode) {
		// TODO Auto-generated method stub
		return hoMapRepo.usercheck(hmcode);
	}

	@Override
	public int updatedetails(Integer district, Integer smcode) {
		// TODO Auto-generated method stub
		return addHodetailsRepository.updatedetails(district, smcode) ;
	}

	@Override
	public int insertdetails(Integer district, Integer smcode) {
		// TODO Auto-generated method stub
		return addHodetailsRepository.insertdetails(district, smcode);
	}

	@Override
	public List<UnMappingProj> getheadquarter(Integer dcode) {
		// TODO Auto-generated method stub
		return hoMapRepo.getheadquarter(dcode);
	}

	@Override
	public int deletedetails(Integer district, Integer hmcode, Integer mcode) {
		// TODO Auto-generated method stub
		return addHodetailsRepository.deletedetails(district, hmcode, mcode);
	}
	
	
	
}
