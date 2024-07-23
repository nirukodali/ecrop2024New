package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropgroupsRep;

@Repository
public interface CropgroupsRepRepository extends JpaRepository<CropgroupsRep, Integer> {
	
	@Query(value="select cropgrpid,grpname,grpnameeng from cropgroups WHERE active='A' order by grpname",nativeQuery=true)
	List<CropgroupsRep> getCropGroup();
	
}


