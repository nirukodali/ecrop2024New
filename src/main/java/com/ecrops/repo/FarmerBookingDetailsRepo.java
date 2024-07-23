package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.FarmerBookingDetails;

import com.ecrops.projection.MasterProjections;

public interface FarmerBookingDetailsRepo  extends JpaRepository<FarmerBookingDetails, Long>{
	
	@Query(value="select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as col1,concat(b.seasonname,' ',cropyear) as col2 \r\n" + 
			" from activeseason a,season b  where   a.season=b.season and a.active='A' order by a.cropyear,a.season",nativeQuery = true)
	public List<MasterProjections> getAllSeason();
	
	

}
