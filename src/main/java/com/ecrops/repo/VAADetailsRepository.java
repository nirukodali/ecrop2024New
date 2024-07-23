package com.ecrops.repo;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.VAADetails;


public interface VAADetailsRepository extends JpaRepository<VAADetails, String>{
	
	
	@Query(value="SELECT userid,districtname,mandalname,villagename,name,mobile_phone,emailid,to_char(regdate,'dd-MM-yyyy') as regdate,status \r\n"
			+ "from user_registration_vs_v  where status='A' and  dcode=:dcode and type_user='25' order by mandalname,villagename ",nativeQuery=true)
	List<VAADetailsView> getListt(@Param("dcode") String dcode);
	
	interface VAADetailsView{
		String getUserid();
		String getDistrictname();
		String getMandalname();
		String getVillagename();
		String getName();
		String getMobile_phone();
		String getEmailid();
		String getRegdate();
		String getStatus();
		
	}
	

}
