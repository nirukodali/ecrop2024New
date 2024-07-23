package com.ecrops.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.VillageSecRevEntity;
import com.ecrops.entity.WbMaster;
import com.ecrops.projection.ActiveSeasonProjection;


public interface VillageSecRevRepository extends JpaRepository<VillageSecRevEntity, Integer> {
	

	@Query(value = "select  vcode, wbvname from ecrop2024.villsec_rev_v where vscode=:vscode", nativeQuery = true)
	public List<ActiveSeasonProjection> getVillageListByRbk(@Param("vscode") Integer vscode);
	
	
	
	@Query(value = "select  a.vcode, a.wbvname from ecrop2024.villsec_rev_v a where vcode in \r\n"
			+ "(select   cr_vcode from ecrop2024.verify_datadownload b where   \r\n"
			+ "b.wbland='Y' and b.ccrc='Y' and b.efish='Y' and b.rofr='Y' and b.peri_data='Y') and vscode=:vscode", nativeQuery = true)
	public List<ActiveSeasonProjection> getVillageList(@Param("vscode") Integer vscode);
	
	@Query(value = "select wb.vcode as vcode, wb.wbdname  as wbedname, wb.wbmname  as wbemname, wb.vsname as wbvname, wb.wbdcode, wb.wbmcode, wb.vscode  from ecrop2024.villsec_rev_v wb where \n" + 
			"wb.wbdcode = :wbdcode and wb.wbmcode = :wbmcode and wb.vscode =:village", nativeQuery = true)

	public List<VillageSecRevEntity> getWbMasterDetailsForVillage(@Param("village") Integer village, @Param("wbmcode") Integer wbmcode,
			@Param("wbdcode") Integer wbdcode);
	
	@Query(value = "select wb.vcode as vcode, wb.wbedname, wb.wbemname, wb.vsname as wbvname, wb.wbdcode, wb.wbmcode, wb.vscode  from ecrop2024.villsec_rev_v wb where \n" + 
			"wb.wbdcode = :wbdcode and wb.wbmcode = :wbmcode and wb.vcode =:village", nativeQuery = true)
	public List<VillageSecRevEntity> getWbMasterDetailsForVillageVro(@Param("village") Integer village, @Param("wbmcode") Integer wbmcode,
			@Param("wbdcode") Integer wbdcode);
	
	

}
