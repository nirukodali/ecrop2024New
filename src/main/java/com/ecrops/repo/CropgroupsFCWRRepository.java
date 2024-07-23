package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropgroupsFCWR;

@Repository
public interface CropgroupsFCWRRepository extends JpaRepository<CropgroupsFCWR, Integer>{ 
	@Query(value="SELECT cropgrpid,grpname,grpnameeng from cropgroups  where active ='A' and cropnature in('A','H','S','R' )",nativeQuery=true)
	List<CropgroupsFCWR> getCropGroup();

}
