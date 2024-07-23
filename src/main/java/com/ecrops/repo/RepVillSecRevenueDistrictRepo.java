package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.WbvillageMstEntity;
import com.ecrops.projection.ActiveSeasonProjection;

public interface RepVillSecRevenueDistrictRepo extends JpaRepository<WbvillageMstEntity, Integer> {
	
	//@Query(value = "select distinct mcode, mname  from wbvillage_mst where dcode=:dcode",nativeQuery = true)
	public List<WbvillageMstEntity> findByDcode(Integer dcode);
}
