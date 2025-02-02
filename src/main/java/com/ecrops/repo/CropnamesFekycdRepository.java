package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropnamesFekycd;

@Repository
public interface CropnamesFekycdRepository extends JpaRepository<CropnamesFekycd, Integer> {
	
	@Query(value="select cropid, cropname,cropnameeng  from cropnames where active='A' and cropnature in('A','H','S')",nativeQuery=true)
	List<CropnamesFekycd> getCropNames();

	
}