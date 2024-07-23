package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.WatersourcesRep;

@Repository
public interface WatersourcesRepRepository extends JpaRepository<WatersourcesRep, Integer> {
	
	@Query(value="select wsrcid,wsrcdesc from waterresources",nativeQuery=true)
	List<WatersourcesRep> getWaterSources();

}


