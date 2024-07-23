package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropgroupsFCVR;

@Repository
public interface CropgroupsFCVRRepository extends JpaRepository<CropgroupsFCVR, Integer> {
	
	@Query(value="select cropgrpid,grpname from cropgroups  where active='A'",nativeQuery=true)
	List<CropgroupsFCVR> getCropGroup();

}