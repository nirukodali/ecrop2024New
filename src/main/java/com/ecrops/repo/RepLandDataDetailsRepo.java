package com.ecrops.repo;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.RepLandDataDetails;



public interface RepLandDataDetailsRepo extends JpaRepository<RepLandDataDetails, Integer> {
	
	@Query(value="select mname,totrec,downloadedcnt,notdownloadedcnt,totrev,wbdname,dname,mcode,dcode \r\n"
			+ " from ecrop2023.downloaddet_mand_v where dcode=:dcode and mcode=:mcode order by mname",
			nativeQuery=true)
	List<RepLandDataDetails>getLandDet(@Param("dcode") Integer dcode,
			@Param("mcode") Integer mcode );
	
	
	@Query(value=" SELECt dcode,wbdname,dname,mname,wbvname,mcode,wbmcode,"
			+ "no_of_records from ecrop2023.downloaddet_v\r\n"
			+ " where dcode=:dcode and mcode=:mcode order by dname,mname",nativeQuery=true)
	List<Rep_VillLandDataDetails>getVillData(@Param("dcode") Integer dcode,
			@Param("mcode") Integer mcode);
	
	
	interface Rep_VillLandDataDetails{
		 Integer getDcode();
		 Integer getWbdname();
		 String getDname();
		 String getMname();
		 String getWbvname();
		 Integer getMcode();
		 Integer getWbmcode();
		 Integer getNo_of_records();
	}

}
