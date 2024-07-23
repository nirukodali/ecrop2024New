package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.dto.crop.response.PerinialCropYear;
import com.ecrops.entity.WbVillageMastEntity;

public interface DigitalSmsRepository extends JpaRepository<WbVillageMastEntity, Integer> {

	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear "
			+ "from activeseason a, season b where a.season = b.season and a.active = 'A' and current_season = 'C' order by a.cropyear, a.season", nativeQuery = true)
	List<PerinialCropYear> getCropYear();

	@Query(value = "select wbdcode, dname from district_2011_cs order by dname", nativeQuery = true)
	List<Object[]> getWbvcodeWbvname();

}
