package com.ecrops.repo;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.BookingSummaryExtent;

public interface BookingSummaryExtRepo extends JpaRepository<BookingSummaryExtent, String> {
//	===============district==============
	@Query(value = "select dcode as col1,dname as col2 from district_2011_cs where dcode!=999 order by dname", nativeQuery = true)
	List<com.ecrops.projection.MasterProjections> getdistrict();

	// ===============mandal==============
	@Query(value = "select mcode as col1,mname as col2 from mandal_2011_cs where dcode=:dcode order by mname", nativeQuery = true)
	List<com.ecrops.projection.MasterProjections> getmandal(@Param("dcode") Integer dcode);

	@Query(value = "select wbdcode as col1,dname as col2 from district_2011_cs order by dname", nativeQuery = true)
	List<com.ecrops.projection.MasterProjections> getwbdcode();
	
	
	@Query(value = "select wbmcodenew as col1,mname as col2 from mandal_2011_cs where wbdcodenew=:wbdcode order by mname ", nativeQuery = true)
	List<com.ecrops.projection.MasterProjections> getwbmandal(@Param("wbdcode") Integer wbdcode);

	
	@Query(value = "select wbvcode as col1,lgdvname as col2  from wbvillage_mst where wbdcode=:wbdcode and wbmcode=:wbmcode order by lgdvname",nativeQuery = true)
	List<com.ecrops.projection.MasterProjections> getwbvillages(@Param("wbdcode") Integer wbdcode,
			@Param("wbmcode") Integer wbmcode);

}

