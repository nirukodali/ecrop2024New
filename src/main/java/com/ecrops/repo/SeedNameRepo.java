package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.SeedNameEntity;

public interface SeedNameRepo extends JpaRepository<SeedNameEntity, Integer> {

	@Query(value = "select cropid, cropname from public.cropnames where grpcode =:cropgrpid", nativeQuery = true)
	public List<SeedNameEntity> getSeedName(@Param("cropgrpid") int cropgrpid);

	
	
}
