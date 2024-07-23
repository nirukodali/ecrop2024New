package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.EfishdetailEntity;
import com.ecrops.repo.EfishBookingUpdateRepository;
import com.ecrops.repo.EfishdetailRepo;
import com.ecrops.service.EfishDetailsService;

@Service
public class EfishDetailsServiceImpl implements EfishDetailsService {

	@Autowired
	private EfishdetailRepo efishdetailrepo;
	
	@Autowired
	private EfishBookingUpdateRepository efishbookingupdaterepository;
	
	@Override
	public List<EfishdetailEntity> getefishdetails(String vcode, String khano) {
		// TODO Auto-generated method stub
		return efishdetailrepo.getefishdetails(vcode, khano);
	}

	@Override
	public int updatebooking(Integer recid,String allowable) {
		// TODO Auto-generated method stub
		return efishbookingupdaterepository.updatebooking(recid,allowable);
	}

}
