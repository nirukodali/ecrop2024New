package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.WbMaster;

@Repository
public interface VillageRepository extends JpaRepository<WbMaster,Integer> {
	
	@Query(value="select * from ecrop2023.wbvillage_mst where dcode=:dcode and mcode=:mcode  order by wbvname",nativeQuery=true)
    List<WbMaster> getVillages(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode);

	@Query(value="select * from ecrop2023.wbvillage_mst where wbdcode=:dcode and wbmcode=:mcode order by wbvname",nativeQuery=true)
    List<WbMaster> getwbVillages(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode);

	@Query(value="select * from ecrop2023.wbvillage_mst where wbdcode=:dcode and wbmcode=:mcode and wbvcode not in (select wbvcode from masters.villcoords_exempted_villages)",nativeQuery=true)
	List<WbMaster> getnotjiovcode(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode);

}