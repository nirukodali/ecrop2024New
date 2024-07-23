package com.ecrops.repo.crop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.dto.crop.response.HomandalsnewView;
import com.ecrops.entity.WbMaster;

public interface SuperCheckAdaRepository extends JpaRepository<WbMaster, Integer> {

	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear  "
			+ "		from activeseason a,season b  where a.season=b.season and a.active='A' and current_season='C' order by a.cropyear,a.season ", nativeQuery = true)
	List<Object[]> getSeasonCropYears();

	@Query(value = "select  hqcode from homandalsnew_v where mcode= :mcode order by mname", nativeQuery = true)
	int getHqcode(@Param("mcode") int mcode);

	@Query(value = "select dcode from district_2011_cs where wbdcode=:wbdcode", nativeQuery = true)
	int getDcode(@Param("wbdcode") int wbdcode);

	@Query(value = "select  mcode,mname from adhmandals_v where divcode=:divcode order by mname", nativeQuery = true)
	List<HomandalsnewView> getMcodeMnamefromadhmandals_v(@Param("divcode") int divcode);
	
	@Query(value = "select  mcode,mname from adamandals_v where divcode=:divcode order by mname", nativeQuery = true)
	List<HomandalsnewView> getMcodeMnamefromadamandals_v(@Param("divcode") int divcode);

	@Query(value = "select  mcode,mname from homandalsnew_v where hqcode= :hqcode order by mname", nativeQuery = true)
	List<HomandalsnewView> getMcodeMnamefromhomandalsnew_v(@Param("hqcode") int hqcode);

	@Query(value = "select distinct wbmcode from wbvillage_mst  where mcode= :mcode and wbdcode!=88", nativeQuery = true)
	int getwbmcode(@Param("mcode") int mcode);


	@Query(value = "select distinct wbdcode from wbvillage_mst  where mcode= :mcode and wbdcode!=88", nativeQuery = true)
	int getwbdcode(@Param("mcode") int mcode);


	@Query(value = "select code, reason from authority_verify_reasons where active='A'", nativeQuery = true)
	List<AuthorityVerifyReasons> getcodeAndreason();
}