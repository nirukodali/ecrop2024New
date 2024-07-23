package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ecrops.entity.DeleteReasonsEntity;
import com.ecrops.entity.WaterResourcesEntity;

public interface GetWaterResources extends JpaRepository<WaterResourcesEntity, Integer> {
	
	 @Query(value="select wsrcid, wsrcdesc from waterresources where active='A'", nativeQuery = true)
	 
	 List<WaterResourcesEntity> getWaterResources();

	
}
