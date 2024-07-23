package com.ecrops.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.ecrops.entity.CropSeedSchemeEntity;


public interface GetCropSeedScheme extends JpaRepository<CropSeedSchemeEntity, Character> {
	
	 @Query(value="select * from  cropseed_scheme where active='A'", nativeQuery = true)
	 
	 List<CropSeedSchemeEntity> getCropSeedScheme();
	 
}
