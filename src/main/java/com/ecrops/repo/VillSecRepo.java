package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.Village;
import com.ecrops.projection.ActiveSeasonProjection;

public interface VillSecRepo extends JpaRepository<Village, Integer> {
	

	@Query(value = "select vcode, wbvname from ecrop2024.villsec_rev_v where vscode=:vcode and vcode in ( select cr_vcode from ecrop2024.verify_datadownload where cr_year=:cryear and cr_season=:season and dcode=:dcode and mcode=:mcode)", nativeQuery = true)
	public List<ActiveSeasonProjection> getRbk(@Param("vcode") int vcode,@Param("cryear") int crYear,@Param("season") String season,@Param("dcode") int distCode,@Param("mcode") int mandCode);
	
	@Query(value = "select distinct vcode,vname from vill_sec_det where vcode in (select rbkcode from ecrop2024.emp_rbk_map where mcode=5066) order by vname", nativeQuery = true)
	public List<ActiveSeasonProjection> getRbk();


	@Query(value = "select distinct vcode,vname from vill_sec_det where dcode=:district and mcode=:mandalcode order by vcode", nativeQuery = true)
	public List<ActiveSeasonProjection> getVname(@Param("district") Integer district,@Param("mandalcode") Integer mandalcode);

//AJAXIMEI
	@Query(value="select imei1 from devicedet where  vcode=:villagecode",nativeQuery = true)
	public List<ActiveSeasonProjection> getVcode(@Param("villagecode") Integer villagecode);
	
	
}


