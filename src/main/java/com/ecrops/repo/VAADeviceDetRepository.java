package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.VAADeviceDet;
import com.ecrops.projection.MasterProjections;

public interface VAADeviceDetRepository extends JpaRepository<VAADeviceDet, String>{
//	@Query(value = "SELECT DISTINCT district,mname,blockortehsil,village,userid,mobile_phone,emailid,dt.status,dt.dt_crt,imei1,imei2\r\n"
//			+ "FROM user_registration ur INNER JOIN devicedet dt ON CAST(ur.village as text) = CAST(dt.vcode as text)\r\n"
//			+ "INNER JOIN mandal_2011_cs m ON CAST(blockortehsil as int) = m.mcode\r\n"
//			+ "WHERE district =:dcode AND type_user = '25'\r\n"
//			+ "ORDER BY blockortehsil,village",nativeQuery=true)
	@Query(value = "SELECT DISTINCT district, mname,vname,blockortehsil,village,userid,\r\n"
			+ "mobile_phone,emailid, dt.status,dt_crt,imei1,imei2 FROM \r\n"
			+ "user_registration ur \r\n"
			+ "INNER JOIN devicedet dt ON CAST(ur.village as text) = CAST(dt.vcode as text)\r\n"
			+ "INNER JOIN mandal_2011_cs m ON CAST(ur.blockortehsil as int) = m.mcode\r\n"
			+ "LEFT JOIN vill_sec_det v ON CAST(ur.village as text) = CAST(v.vcode as text)\r\n"
			+ "WHERE ur.district =:dcode AND ur.type_user ='25'\r\n"
			+ "ORDER BY blockortehsil,village,dt_crt",nativeQuery=true)
	List<VAADetView> getListt(@Param("dcode") String dcode);
	
	interface VAADetView{
		String getMname();
		String getVname();
		String getImei1();
		String getImei2();
		String getMobile_phone();
		String getEmailid();
		String getStatus();
		String getDt_crt();
		String getBlockortehsil();
		String getVillage();
		String getUserid();
		
	}

	
	@Query(value="select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as col1,concat(b.seasonname,' ',cropyear) as col2 \r\n" + 
			" from activeseason a,season b  where   a.season=b.season  and a.active='A' order by a.cropyear,a.season",nativeQuery = true)
	public List<MasterProjections> getAllSeason();
	
	
	@Query(value = "select cropid as col1,cropname as col2 from cropnames  where active='A' order by cropname", nativeQuery = true)
	public List<MasterProjections> getAllCrops();
}
