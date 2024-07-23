package com.ecrops.repo.crop;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.WbMaster;

public interface InterCropRepo extends JpaRepository<WbMaster, Integer>{

    @Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear  "
    		+ "from activeseason a,season b  where a.season=b.season and a.active='A' and current_season='C' order by a.cropyear,a.season ", nativeQuery = true)
    List<Object[]> getSeasonCropYears();
    
    
    
    @Query(value ="select id,naturedesc from cropnature  where active='A' and dept_type is null and id=3", nativeQuery = true)
    
    List<Object[]> getCroppingPattern();

	
}
