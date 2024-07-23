package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.SeedGroupEntity;

public interface SeedGroupRepo extends JpaRepository<SeedGroupEntity, Integer> {

	@Query(value = "select cropgrpid,grpname from cropgroups  where active='A'", nativeQuery = true)
	public List<SeedGroupEntity> getGroups();

	
	
}
