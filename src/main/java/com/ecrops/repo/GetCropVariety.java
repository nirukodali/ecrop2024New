package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.CropVarietyEntity;
import com.ecrops.entity.DeleteReasonsEntity;

public interface GetCropVariety extends JpaRepository<CropVarietyEntity, Integer> {
	
	 @Query(value="select * from cr_variety_master  where  cropcode=:cr_crop", nativeQuery = true)
	 List<CropVarietyEntity> getCropVariety(@Param("cr_crop") int cr_crop);
	 
	 @Query(value="select distinct kh_no from ecrop2024.cr_details_efish_2024  where cr_vcode=:vcode order by kh_no", nativeQuery = true)
	 List<String> getKhathaNo(@Param("vcode") String khNo);
	 
    @Query(value="select distinct cr_sno from ecrop2024.cr_details_efish_2024  where kh_no=:KhNo", nativeQuery = true)
	 List<String> getSurveyNo(@Param("KhNo") String KhNo);


	
}
