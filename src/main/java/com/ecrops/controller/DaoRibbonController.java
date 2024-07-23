package com.ecrops.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@PreAuthorize("hasAuthority('9')")
@Controller
public class DaoRibbonController {
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/repvaadet")
	public String getRepVAADet(Model model) {
		return "dao/VaaDetails";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/devregdett")
	public String getDevRegDet(Model model) {
		return "dao/DeviceRegDetails";
	}

	
	@PreAuthorize("hasAuthority('9')")
	
	@GetMapping("/VillageSecretariatList")
	public String getRbkList() {
		return "dao/VillageSecretariatList";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/RbkVsRevenue")
	public String getRbkVsRevenue() {
		return "dao/RbkVsRevenue";
	}
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/phyackmwise")
	public String getPhyAckVwise(Model model) {
		return "dao/PhyAckMwise";
	}
	
	@PreAuthorize("hasAuthority('9')")
	@GetMapping("/abstractExtentBooked")
	public String getabstractExtentBooked(Model model) {
		return "dao/Abstract_Extent_Booked";
	}

	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/daosocialauditR")
	public String getMaoSocialAuditCorrectionR() {
		return "dao/DaoSocialAuditcorrection";
	}
		
	//-------supercheck---//
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/supervisoryRecordsAlloted")
	public String getsupervisoryRecordsAlloted() {
		return "dao/SupervisoryChkRecsAlloted";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/supervisoryReport")
	public String getsupercheckreport() {
		return "dao/SupervisoryChkReport";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/supercheckrevenue")
	public String getsupercheckrevenue() {
		return "dao/SuperCheckRevenue";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/mandasuperchckredressed")
	public String getmandasuperchckredressed() {
		return "dao/MandalSupercheckRedressedReport";
	}

	//-----DETAILED Reports-------
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/mandalwiseEKYC")
	public String getMandalwiseEKYC() {
		return "dao/MandalwiseEKYC";
	}
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/dataSourceExt")
	public String getDataSourceExt() {
		return "dao/DataSourceExt";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/farmerwisecropbooking")
	public String getFarmerwisecropbooking() {
		return "dao/FarmerwiseCropBookingDetails";
	}	
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/mandalNormalAreas")
	public String getmandalNormalAreas() {
		return "dao/mandalNormalAreas";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/normalVsextent")
	public String getnormalVsextent() {
		return "dao/NormalAreaVsExtentBooked";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/daoCropBookingDetails")
	public String getdaoCropBookingDetails() {
		return "dao/DaoCropBookingDetails";
	}
	
	@PreAuthorize("hasAuthority('9')")

	@GetMapping("/damagedJowarMaize")
	public String getdamagedJowarMaize() {
		return "dao/DamagedJowarMaizeCrops";
	}

	@GetMapping("/repAuthDAOvaavroekyc")
	public String getrepAuthDAOvaavroekyc() {
		return "dao/Rep_Auth_DAO_vaavroekyc";
	}
	
	@GetMapping("/repAuthDAOrbkvaavroekyc")
	public String getrrepAuthDAOrbkvaavroekyc() {
		return "dao/Rep_Auth_DAO_rbkvaavroekyc";
	}
	
	@GetMapping("/repAuthDAOcropwisedist")
	public String getrepAuthDAOcropwisedist() {
		return "dao/Rep_Auth_DAO_cropwise_dist";
	}
	
	@GetMapping("/repAuthDaocropwise")
	public String getrepAuthDaocropwise() {
		return "dao/Rep_Auth_DAO_cropwise_mand";
	}
	
	
	@GetMapping("/cropInsGrev")
	public String getcropInsGrev() {
		return "dao/daoCropInsGrev";
	}
	@GetMapping("/digitalACKSMS")
	public String getdigitalACKSMS() {
		return "dao/DigitalAckSMS";
	}

}
