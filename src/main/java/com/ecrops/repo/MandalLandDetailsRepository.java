package com.ecrops.repo;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.MandLandDetails;

public interface MandalLandDetailsRepository extends JpaRepository<MandLandDetails, Integer>{
	@Query(value="SELECt dcode,totrec,wbdname,dname,mname,mcode,wbmcode,totrev,downloadedcnt,notdownloadedcnt \r\n"
			+ "from ecrop2023.downloaddet_mand_v where dcode=:dcode  order by dname,mname",nativeQuery=true)
	List<Mandalview> getListt(@Param("dcode") Integer dcode);
	
	interface Mandalview{
		Integer getDcode();
		Long getTotrec();
		String getWbdname();
		String getDname();
		String getMname();
		Integer getMcode();
		Integer getWbmcode();
		Long getTotrev();
		Long getDownloadedcnt();
		Long getNotdownloadedcnt();
	}

}
