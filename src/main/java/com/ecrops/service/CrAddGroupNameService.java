package com.ecrops.service;

import java.util.Optional;

import com.ecrops.entity.CrAddGroupNameEntity;
import com.ecrops.repo.UniversalProj;

public interface CrAddGroupNameService {
	public UniversalProj getMaxcrpgid();
	public CrAddGroupNameEntity findByGrpnameeng(String grpnameeng);
	public CrAddGroupNameEntity saveCrAddGroupNameEntity(CrAddGroupNameEntity crAddGroupNameEntity);
	
	
	public Optional<CrAddGroupNameEntity> getByCrpGrpName(String grpname);
	
}
