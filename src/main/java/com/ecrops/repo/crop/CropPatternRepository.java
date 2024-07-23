package com.ecrops.repo.crop;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.dto.crop.response.CropNamesData;
import com.ecrops.dto.crop.response.CropNatureIdNaturedesc;
import com.ecrops.dto.crop.response.CropnatureCropidCropname;
import com.ecrops.dto.crop.response.YearCropPattern;
import com.ecrops.entity.WbMaster;

public interface CropPatternRepository extends JpaRepository<WbMaster, Integer> {
	
	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear "
	               + "from activeseason a,season b  where a.season=b.season and a.season='R' and a.cropyear=2023 order by a.cropyear,a.season", nativeQuery = true)
    List<YearCropPattern> getCropYear();
	
	
	@Query(value = "select id,naturedesc from cropnature where active='A' and dept_type is null and id=3", nativeQuery = true)
    List<CropNatureIdNaturedesc> getIdNaturedesc();
	
	
	@Query(value ="select * from cropnames where cropnature in ('A','H','S')", nativeQuery = true)
	List<Object[]> getCropidCropname();
	
	
	
	 		
 

}
