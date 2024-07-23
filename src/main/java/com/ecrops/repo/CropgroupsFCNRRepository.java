package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ecrops.entity.CropgroupsFCNR;

public interface CropgroupsFCNRRepository extends JpaRepository<CropgroupsFCNR,Integer>{
	
	@Query(value="SELECT cropgrpid,grpname,grpnameeng from cropgroups  where active ='A' and cropnature in('A','H','S','R' )",nativeQuery=true)
	List<CropgroupsFCNR> getCropGroup();

}
