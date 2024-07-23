package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.CropNamesEntity;
import com.ecrops.entity.DeleteReasonsEntity;

public interface GetCropNames extends JpaRepository<CropNamesEntity, Integer> {
	
	 @Query(value="select cropid, cropname, croptype from cropnames where cropnature in ('A','H','S') order by cropname", nativeQuery = true)
	 
	 List<CropNamesEntity> getCropNames();

	
}
