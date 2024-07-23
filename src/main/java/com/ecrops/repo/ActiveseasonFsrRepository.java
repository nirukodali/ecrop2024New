package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.ActiveseasonFsr;

@Repository
public interface ActiveseasonFsrRepository extends JpaRepository<ActiveseasonFsr,String>{

	@Query(value="select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear,a.season "
			+" from activeseason a,season b  where   a.season=b.season and a.active='A' order by a.cropyear,a.season",nativeQuery=true)
	 List<ActiveseasonFsr>  getCropyear();

}
