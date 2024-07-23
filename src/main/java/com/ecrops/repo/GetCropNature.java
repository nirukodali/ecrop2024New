package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.CropNatureEntity;
import com.ecrops.entity.DeleteReasonsEntity;

public interface GetCropNature extends JpaRepository<CropNatureEntity, String> {
	
	 @Query(value="select id,naturedesc from cropnature  where active='A' and dept_type is null and id=2", nativeQuery = true)
	 
	 List<CropNatureEntity> getCropNatureForId1();
	 
	 
     @Query(value="select id,naturedesc from cropnature  where active='A' and dept_type is null", nativeQuery = true)
	 
	 List<CropNatureEntity> getCropNature();

	
}
