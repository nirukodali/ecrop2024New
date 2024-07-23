package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.CropvarietiesRep;

@Repository
public interface CropvarietiesRepRepository extends JpaRepository<CropvarietiesRep, Integer> {

	@Query(value="select b.cropname ,a.varietycode,a.varietyname,a.cropcode,a.releasedays,coalesce(a.newreleasedays,0) as newreleasedays,c.grpname,c.cropgrpid,b.cropnameeng  "
	   		+ " from cr_variety_master a, cropnames b, cropgroups c  "
	   		+ "	where a.cropcode=b.cropid and b.grpcode=c.cropgrpid and c.cropgrpid=?  and  b.cropnature in('A','H','S','R') and status='A'  "
	   		+ "	order by grpname,cropname,a.varietyname",nativeQuery=true)
	     List<CropvarietiesRep> findByCropnamesFCVR_CropgroupsFCVRCropgrpid(@Param("grpcode") Integer grpcode);
}
	