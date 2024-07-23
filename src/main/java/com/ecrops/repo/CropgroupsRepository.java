package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cropgroups;

@Repository
public interface CropgroupsRepository extends JpaRepository<Cropgroups, Integer> {
	
	@Query(value="select cropgrpid,grpname from cropgroups  where active='A'",nativeQuery=true)
	List<Cropgroups> getCropGroup();
	


}