package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropnamesRep;

@Repository
public interface CropnamesRepRepository extends JpaRepository<CropnamesRep, Integer> {
	
	@Query(value="select cropid,cropname,cropnameeng from  cropnames  where active='A' and grpcode=? order by cropname",nativeQuery=true)
	List<CropnamesRep> getCropNames(@Param("grpcode") Integer grpcode );

	
}