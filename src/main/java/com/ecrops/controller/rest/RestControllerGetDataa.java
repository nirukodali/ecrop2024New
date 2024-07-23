package com.ecrops.controller.rest;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.SuperChkRequestModel;
import com.ecrops.entity.SeedNameEntity;
import com.ecrops.entity.SuperCheckRecordsAlloted;
import com.ecrops.entity.SuperChkReport;
import com.ecrops.entity.SuperChkReportEntity;
import com.ecrops.entity.WbvillageMstEntity;
import com.ecrops.model.RequestModel;
import com.ecrops.repo.SeedNameRepo;
import com.ecrops.repo.SuperChkReportRepo;
import com.ecrops.service.RepVillSecRevenueDistrictService;

@RestController
public class RestControllerGetDataa {

	@Autowired
	private SeedNameRepo seedNameRepo;

	@Autowired
	private RepVillSecRevenueDistrictService repVillSecRevenueDistrictService;

	@GetMapping("/getSeedNames")
	public List<SeedNameEntity> getSeedNamesByGroup(@RequestParam("groupCode") String cropGrpId) {

		List<SeedNameEntity> seedName = seedNameRepo.getSeedName(Integer.parseInt(cropGrpId));
		System.out.println("seedName -----------> " + seedName.get(0).getCropid());

		return seedName;
	}

	@GetMapping("/getAllMandalsByDcode")
	public List<WbvillageMstEntity> findByDcode(String dcode) {
		System.out.println("dcode=>" + dcode);

		return repVillSecRevenueDistrictService.findByDcode(Integer.parseInt(dcode));
	}

	// =========================SuperCheck Report===============================//

	@Autowired
	SuperChkReportRepo superChkReportPartition;

	@PostMapping("/supcheckReport")
	List<SuperChkReportEntity> getSupChk(@RequestBody SuperChkRequestModel superChkRequestModel) {
		System.out.println("requestModel=>" + superChkRequestModel.toString());

		List<SuperChkReportEntity> supkr = superChkReportPartition.getSupchkRep(superChkRequestModel.getWbdcode(),
				superChkRequestModel.getWbmcode(), superChkRequestModel.getUserid(),
				superChkRequestModel.getCropyear());
		System.out.println("details===================>" + supkr.size());
		return supkr;
	}

//*********************************   Rep_SupervisoryVerify  *******************************************

//	@Autowired
//	private SuperCheckRecordsAllotedPartition superCheckRecordsAllotedPartition;
//
//	@PostMapping("/supervisoryPending")
//	List<SuperCheckRecordsAlloted> getSupChkR(@RequestBody RequestModel requestModel) {
//		System.out.println("requestModel=>" + requestModel.toString());
//
//		List<SuperCheckRecordsAlloted> spckr = superCheckRecordsAllotedPartition.getSupchkRds(requestModel.getWbdcode(),
//				requestModel.getWbmcode(), requestModel.getUserid(), requestModel.getCropyear());
//		System.out.println("details===================>" + spckr.size());
//		return spckr;
//	}
	
}













