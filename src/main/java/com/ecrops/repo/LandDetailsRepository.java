package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.LandDetails;

public interface LandDetailsRepository extends JpaRepository<LandDetails, Integer>{
	@Query(value="select dcode,totrec,wbdname,dname,totrev,downloadedcnt,notdownloadedcnt from ecrop2023.downloaddet_dist_v order by dname",nativeQuery=true)
	List<Landdetailsview> getList();
	
	
	interface Landdetailsview{
		Integer getDcode();
		Long getTotrec();
		String getWbdname();
		String getDname();
		Long getTotrev();
		Long getDownloadedcnt();
		Long getNotdownloadedcnt();
	}

}
