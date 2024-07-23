package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.PendingVillMap;
@Repository
public interface PendingVillMapRepository extends JpaRepository<PendingVillMap, Integer>{
	@Query(value="select wbdcode,wbdname,wbmcode,wbmname,wbvcode,wbvname from pending_villmap_v \r\n"
			+ " where wbdcode=:dcode order by wbmname,wbvname",nativeQuery=true)
	List<Pendingvillmapview> getListt(@Param("dcode") Integer dcode);

	interface Pendingvillmapview{
		Integer getWbdcode();
		String getWbdname();
		Integer getWbmcode();
		String getWbmname();
		Integer getWbvcode();
		String getWbvname();
		
	}
}
