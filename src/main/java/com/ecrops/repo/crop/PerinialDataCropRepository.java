package com.ecrops.repo.crop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.dto.crop.response.PerinialCropYear;
import com.ecrops.dto.crop.response.PerinialVillageData;
import com.ecrops.entity.WbVillageMastEntity;

public interface PerinialDataCropRepository extends JpaRepository<WbVillageMastEntity, Integer> {

	@Query(value = "select distinct on (a.cropyear,a.season) concat(a.season,'@',cropyear) as seasonvalue,concat(b.seasonname,' ',cropyear) as cropyear  "
			+ "		from activeseason a,season b  where a.season=b.season and a.active='A' and current_season='C' order by a.cropyear,a.season ", nativeQuery = true)
	List<PerinialCropYear> getCropYear();

	@Query(value = "select wbvcode, wbevname from wbvillage_mst where mcode = :mcode and"
			+ " wbvcode in (select cr_vcode from ecrop2024.verify_datadownload where efish='Y'and wbland='Y' and ccrc='Y' and rofr='Y'  and peri_cnt is null and cr_year = :cr_year and cr_season = :cr_season)", nativeQuery = true)
	List<PerinialVillageData> getWbvcodeWbvname(@Param("mcode") int mcode, @Param("cr_year") int cropyear,
			@Param("cr_season") String season);

	@Query(value = "select wbevname from wbvillage_mst where wbvcode= :wbvcode", nativeQuery = true)
	String getwbvname(@Param("wbvcode") int wbvcode);

	@Query(value = "SELECT COUNT(*) FROM ecrop2024.peri_k2024 WHERE cr_vcode = :cr_vcode AND cr_year = :cr_year AND cr_season = :cr_season", nativeQuery = true)
	int getRecordCount(@Param("cr_vcode") int cr_vcode, @Param("cr_year") int cr_year,
			@Param("cr_season") String cr_season);

	@Query(value = "SELECT COUNT(*) FROM ecrop2024.peri_r2024 WHERE cr_vcode = :cr_vcode AND cr_year = :cr_year AND cr_season = :cr_season", nativeQuery = true)
	int getRecordCountRabiSeason(@Param("cr_vcode") int cr_vcode, @Param("cr_year") int cr_year,
			@Param("cr_season") String cr_season);

}
