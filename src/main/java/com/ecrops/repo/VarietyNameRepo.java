package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.SeedGroupEntity;
import com.ecrops.entity.VarietyNameEntity;
import com.ecrops.projection.VillageDetailsProjection;

public interface VarietyNameRepo extends JpaRepository<VarietyNameEntity, String> {

	@Query(value = "select varietyname,varietycode from  cr_variety_master where cropcode=? and varietyname =?", nativeQuery = true)
	public List<VarietyNameEntity> getVarietyName(@Param("cropid") Integer cropid, @Param("varietyname") String varietyname);

	@Query(value = "select max(varietycode) as maxvcode from cr_variety_master", nativeQuery = true)
	public String getVarietyCount();
	
@Query(value="select varietycode, varietyname from ecrop2023.cr_variety_master where cropcode=:cropCode", nativeQuery=true)
	
	List<VillageDetailsProjection> findByVarietycode(@Param("cropCode") int cropCode);

	
}
