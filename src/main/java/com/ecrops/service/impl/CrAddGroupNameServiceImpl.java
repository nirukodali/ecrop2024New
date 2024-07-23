package com.ecrops.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.entity.CrAddGroupNameEntity;
import com.ecrops.repo.CrAddGroupNameRepo;
import com.ecrops.repo.UniversalProj;
import com.ecrops.service.CrAddGroupNameService;
@Service
public class CrAddGroupNameServiceImpl implements CrAddGroupNameService{
	
	@Autowired private CrAddGroupNameRepo crAddGroupNameRepo;

	@Override
	public UniversalProj getMaxcrpgid() {
		// TODO Auto-generated method stub
		return crAddGroupNameRepo.getMaxcrpgid();
	}
	
	@Override
	public CrAddGroupNameEntity findByGrpnameeng(String grpnameeng) {
		// TODO Auto-generated method stub
		return crAddGroupNameRepo.findByGrpnameeng(grpnameeng);
	}
	
	@Override
	public CrAddGroupNameEntity saveCrAddGroupNameEntity(CrAddGroupNameEntity crAddGroupNameEntity) {
		// TODO Auto-generated method stub
		return crAddGroupNameRepo.save(crAddGroupNameEntity);
	}
	
	@Override
	public Optional<CrAddGroupNameEntity> getByCrpGrpName(String grpname) {
		return crAddGroupNameRepo.findById(grpname);
	}

}
