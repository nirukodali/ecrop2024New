package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.VillageLandDetails;

public interface VillageLandDetailsRepository extends JpaRepository<VillageLandDetails, Integer>{
	@Query(value = "SELECt dcode,wbdname,dname,mname,wbvname,mcode,wbmcode,no_of_records from ecrop2023.downloaddet_v\r\n"
			+ "where dcode=:dcode and mcode=:mcode order by dname,mname",nativeQuery=true)
	List<VilllandView> getListt(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode);
	
	interface VilllandView{
		Integer getDcode();
		String getWbdname();
		String getDname();
		String getMname();
		String getWbvname();
		Integer getMcode();
		Integer getWbmcode();
		Integer getNo_of_records();
		
	}

}
