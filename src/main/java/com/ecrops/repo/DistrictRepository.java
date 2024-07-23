package com.ecrops.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.District;
import com.ecrops.projection.DistrictProjections;


public interface DistrictRepository extends JpaRepository<District, Integer>{
	@Query(value = "select * from district_2011_cs where dcode!=999 order by dname",nativeQuery=true)
	List<DistrictProjections> getList();

}