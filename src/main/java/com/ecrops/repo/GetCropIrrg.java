package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.CropIrrgEntity;

public interface GetCropIrrg extends JpaRepository<CropIrrgEntity, String> {
	
	 @Query(value="select * from crop_irrg_wsrc_v where wsrccode=:cr_w_draw", nativeQuery = true)
	 
	 List<CropIrrgEntity> getCropIrrgEntity(@Param("cr_w_draw") int cr_w_draw);

	
}
