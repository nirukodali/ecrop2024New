package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {

	AppUser findByUserid(String userid);
	
	
	@Query(value="select dcode,mcode,hqcode,cast(dt_crt as character varying) as dt_crt,status from homandalsmap where dcode =:dcode and mcode=:mcode and hqcode =:hqcode",nativeQuery = true)
	public List<HoMandalsMapProj> checkRecord(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode,@Param("hqcode") Integer hqcode);
	
	interface HoMandalsMapProj{
		Integer getDcode();
		Integer getMcode();
		Integer getHqcode();
		String getDt_crt();
		String getStatus();
	}


}
