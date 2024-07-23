package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropnamesFCWR;

@Repository
public interface CropnamesFCWRRepository extends JpaRepository<CropnamesFCWR,Integer>{
	@Query(value="select cropid,cropname,cropnameeng from cropnames  where grpcode=? order by cropname",nativeQuery=true)
	List<CropnamesFCWR> getCropname(@Param("grpcode") Integer grpcode );

}
