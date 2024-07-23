package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.CropYearEntity;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.projection.VillageName;

public interface CropyearRepo extends JpaRepository<CropYearEntity, String>{
	
	
@Query(value = "SELECT DISTINCT ON (a.cropyear, a.season)  CONCAT(a.season, '@', a.cropyear) AS seasonvalue,CONCAT(b.seasonname, ' ', a.cropyear) AS cropyear \r\n"
		+ "  FROM activeseason a JOIN season b ON a.season = b.season  WHERE a.active = 'A'  ORDER BY a.cropyear, a.season\r\n",nativeQuery = true)
	 List<CropYearEntity> findAll();

	//List<ActiveSeasonProjection> getActiveSeason();
	}



