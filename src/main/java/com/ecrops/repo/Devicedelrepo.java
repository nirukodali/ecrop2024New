package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DeviceEntity;
import com.ecrops.projection.DeviceProjection;
@Repository
public interface Devicedelrepo extends JpaRepository<DeviceEntity,Integer> 
{
//	@Query(value="select distinct vsmname,vname,deviceid,blockortehsil,village,userid,mobile_phone,emailid,dt.status, imei1,imei2, dt.dt_crt from user_registration ur, "
//			+ " devicedet dt ,vill_sec_det v where cast(ur.village as character varying)=cast(dt.vcode as character varying ) and dt.vcode=v.vcode and dt.status='A' and cast (blockortehsil as character varying)=:mcode and cast(dt.vcode as character varying )=:vcode and type_user='25' order by village ",nativeQuery = true)
//	public List<DeviceProjection> getdevicedet(@Param("mcode") String mcode,@Param("vcode") String vcode);
	
//	select  distinct b.vcode,b.status,village ,vsmname,vname,deviceid,blockortehsil,village,userid,mobile_phone,emailid,b.status, imei1,imei2, b.dt_crt 
//	from user_registration a,devicedet b, vill_sec_det c where type_user='25' and village='10690748' and cropyear=2024 and season='K'  and 
//	a.village::text=b.vcode::text and a.village::text=c.vcode::text and a.blockortehsil='5007' and b.status='A'
	
	
	@Query(value="select distinct  vsmname,vname,deviceid,blockortehsil,village,userid,mobile_phone,emailid,b.status, imei1,imei2, b.dt_crt from user_registration a, "
			+ " devicedet b ,vill_sec_det c where   village=:vcode and cast(a.village as character varying)=cast(b.vcode as character varying ) and cast(a.village as character varying)=cast(c.vcode as character varying ) and a.blockortehsil=:mcode and b.status='A'and type_user='25' order by village  ",nativeQuery = true)
	public List<DeviceProjection> getdevicedet(@Param("mcode") String mcode,@Param("vcode") String vcode);
	
	
	@Query(value="select vname as wbvname,vcode as vscode from vill_sec_det where mcode=? order by vname",nativeQuery = true)
	public List<village> getVill(@Param("mcode") Integer mcode);
	
	interface village{
		String getWbvname();
		String getVscode();
		
	}
	
	@Query(value="select distinct imei2 from devicedet where imei2!='' and  vcode in (select vscode from vs_rev_villages where vcode =?)",nativeQuery = true)
	public List<DeviceProjection> getdevice(@Param("vcode") Integer vcode);
}
