package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.VillSecListDistWise;

public interface VillSecListDistWiseRepository extends JpaRepository<VillSecListDistWise, String>{
	@Query(value = " select vsdname,vcode,vname from Vill_sec_det WHERE dcode=:dcode and  mcode=:mcode order by vsdname,vname",nativeQuery=true)
	public List<VillSecView> getListt(@Param("dcode") Integer dcode,@Param("mcode") Integer mcode);
	
	interface VillSecView{
		String getVsdname();
		Integer getVcode();
		String getVname();
		
	}
	

}
