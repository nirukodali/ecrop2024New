package com.ecrops.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cropnames;

@Repository
public interface CropnamesRepository extends JpaRepository<Cropnames, Integer> {

	
	@Query(value="select max(cropid) from cropNames  where active='A'",nativeQuery=true)
	Integer getMaxCropId();
}
