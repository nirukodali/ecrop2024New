package com.ecrops.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.CrAddGroupNameEntity;

public interface CrAddGroupNameRepo extends JpaRepository<CrAddGroupNameEntity, String> {
	
	@Query(value="select max(cropgrpid)  as maxcrpid from cropgroups",nativeQuery = true)
	public UniversalProj getMaxcrpgid();
	
	
	public CrAddGroupNameEntity findByGrpnameeng(String grpnameeng);
	
	
	
}
