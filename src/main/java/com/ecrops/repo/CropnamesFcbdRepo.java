package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cropnames;
import com.ecrops.entity.CropnamesFcbd;

@Repository
public interface CropnamesFcbdRepo extends JpaRepository<CropnamesFcbd, Integer> {
	@Query(value="select cropid,cropname from cropnames  where active='A' order by cropname\r\n",nativeQuery=true)
	List<CropnamesFcbd> getcropnames();

}
