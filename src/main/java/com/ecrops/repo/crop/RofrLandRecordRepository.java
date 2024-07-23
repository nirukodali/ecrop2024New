package com.ecrops.repo.crop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.dto.crop.response.CropYearROFR;
import com.ecrops.dto.crop.response.VillageDataRofr;
import com.ecrops.entity.WbVillageMastEntity;

public interface RofrLandRecordRepository extends JpaRepository<WbVillageMastEntity, Integer> {

	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear  "
			+ "		from activeseason a,season b  where a.season=b.season and a.active='A' and current_season='C' order by a.cropyear,a.season ", nativeQuery = true)
	List<CropYearROFR> getCropYear();
	
	@Query(value = "select wbvcode, wbevname from wbvillage_mst where mcode = :mcode and"
	        + " wbvcode in (select cr_vcode from ecrop2024.verify_datadownload where wbland='Y' and rofr_cnt is null and cr_year = :cr_year and cr_season = :cr_season)", nativeQuery = true)
	List<VillageDataRofr> getWbvcodeWbvname(@Param("mcode") int mcode, @Param("cr_year") int cropyear,
	        @Param("cr_season") String season);

	@Query(value = "select Lgddcode,Lgdmcode from wbvillage_mst where dcode= :dcode and mcode= :mcode", nativeQuery = true)
	List<Object[]> getLgddcodeLgdmcode(@Param("dcode") int dcode, @Param("mcode") int mcode);

	@Query(value = "select Lgdvname from wbvillage_mst where Lgdvcode= :Lgdvcode ", nativeQuery = true)
	String getLgdvname(@Param("Lgdvcode") int Lgdvcode);

	@Query(value = "select Lgdvcode from wbvillage_mst where wbvcode= :wbvcode ", nativeQuery = true)
	int getwbvcode(@Param("wbvcode") int wbvcode);

}
