package com.ecrops.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.ActiveseasonFDWSRFAC;

@Repository
public interface ActiveseasonFDWSRFACRepository extends JpaRepository<ActiveseasonFDWSRFAC, String> {
	
	@Query(value="select \r\n"
			+ "distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,\r\n"
			+ "concat(b.seasonname,' ',cropyear) as cropyear,a.season \r\n"
			+ "from activeseason a,season b \r\n"
			+ "where a.season=b.season and a.active='A' order by a.cropyear,a.season",nativeQuery=true)
	List<ActiveseasonFDWSRFAC> getCropyear();

}