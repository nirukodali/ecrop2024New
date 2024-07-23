package com.ecrops.repo;

	
import org.springframework.stereotype.Repository;

import com.ecrops.dto.EFishApprovalDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface EFishApprovalRepo extends JpaRepository<EFishApprovalDto, String> {
	
	@Query(value = "select dfo_sug_ext,dist_code,dist_name,mand_code,mand_name, cr_vcode,village_name, recid,occupant_name, occupant_father_name, kh_no, cr_sno, mapped_extent, occupant_extent, allowable_ext, req_ext "
			+ "from ecrop2024.cr_details_efish_2024 where cr_vcode=:rbk and cr_sno=:SurveyNo and kh_no=:KhathaNo and req_ext is null", nativeQuery = true)
	List<EFishApprovalDto> getEFishApprovalDetails(@Param("KhathaNo") String KhathaNo, @Param("SurveyNo") String SurveyNo, @Param("rbk") String rbk);
	
	@Query(value = "select dfo_sug_ext,dist_code,dist_name,mand_code,mand_name, cr_vcode,village_name, recid,occupant_name, occupant_father_name, kh_no, cr_sno, occupant_extent, mapped_extent, allowable_ext, req_ext "
			+ "from ecrop2024.cr_details_efish_2024 where dist_code=:wbdcode and mand_code=:wbmcode and req_ext is not null and  mao_appr_sts is null", nativeQuery = true)
	List<EFishApprovalDto> getEFishApprovalForMAO(@Param("wbdcode") String wbdcode, @Param("wbmcode") String wbmcode); 
	
	@Query(value = "select dfo_sug_ext,dist_code,dist_name,mand_code,mand_name, cr_vcode,village_name, recid,occupant_name, occupant_father_name, kh_no, cr_sno, occupant_extent, mapped_extent, allowable_ext, req_ext "
			+ "from ecrop2024.cr_details_efish_2024 where dist_code=:wbdcode and mao_appr_sts='A' and  dao_appr_sts is null", nativeQuery = true)
	List<EFishApprovalDto> getEFishApprovalForDAO(@Param("wbdcode") String wbdcode);
	
	@Query(value = "select dfo_sug_ext,dist_code,dist_name,mand_code,mand_name, cr_vcode,village_name, recid,occupant_name, occupant_father_name, kh_no, cr_sno, occupant_extent, mapped_extent, allowable_ext, req_ext "
			+ "from ecrop2024.cr_details_efish_2024 where dist_code=:wbdcode and dao_appr_sts='A' and  dfo_appr_sts is null", nativeQuery = true)
	List<EFishApprovalDto> getEFishApprovalForDFO(@Param("wbdcode") String wbdcode);
	
	@Query(value = "select dfo_sug_ext,dist_code,dist_name,mand_code,mand_name, cr_vcode,village_name, recid,occupant_name, occupant_father_name, kh_no, cr_sno, occupant_extent, mapped_extent, allowable_ext, req_ext "
			+ "from ecrop2024.cr_details_efish_2024 where dist_code=:wbdcode and dfo_appr_sts='A' and jc_appr_sts is null", nativeQuery = true)
	List<EFishApprovalDto> getEFishApprovalForJC(@Param("wbdcode") String wbdcode);
	
}
