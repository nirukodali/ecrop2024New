package com.ecrops.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ecrops.partition.CropBookingDetailsMaoIntfPartition;
import com.ecrops.partition.RepPernnialMandPartition;
import com.ecrops.partition.Rep_VillLandDataDetailsPartition;
import com.ecrops.entity.RepPernnialMand;
import com.ecrops.entity.Rep_VillLandDataDetails;
import com.ecrops.model.PerinalReportModel;
import com.ecrops.model.RequestModel;

import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.repo.FarmerBookingDetailsPartitions;
import com.ecrops.repo.FarmerBookingDetailsRepo;
import com.ecrops.repo.RepLandDataDetailsRepo;


//@PreAuthorize("hasAuthority('5')")
@Controller
public class MaoRibbonController {
	
	@Autowired
	RepLandDataDetailsRepo repLandDataDetailsRepo;
	@Autowired
	FarmerBookingDetailsRepo farmerBookingDetailsRepo;
	@Autowired
	FarmerBookingDetailsPartitions partion;
	@Autowired
	ActiveSeasonRepository activerepo;
	@Autowired
	private RepPernnialMandPartition repPernnialMandPartition;

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/normal")
	public String getNormalAreaList(HttpSession httpSession, Model model) {
		String dcode = (String) httpSession.getAttribute("dcode");
		String mcode = (String) httpSession.getAttribute("mcode");

//		System.out.println("dcode===================" + dcode);
//		System.out.println("mcode==============" + mcode);
		model.addAttribute("dcode", "dcode");
		model.addAttribute("mcode", "mcode");
		return "maoroles/normalareasmao";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/crop")
	public String getCropDetails(Model model) {
//	model.addAttribute("crp",crp);
		return "maoroles/cropBookingDetailsMAO";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/farmerdetails")
	public String getFarmerDetails(HttpSession httpSession) {
		return "maoroles/farmerbookingDetails";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/rofr")
	public String roftbkdext(Model model) {
		
		return "maoroles/rofr-extentbooked";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/ekycmao")
	public String MaoEkyc(Model model) {
		return "maoroles/maovaaVroEkyc";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/rbksnomapping")
	public String rSnoMap() {
		return "maoroles/RbkSurveyNoMapping";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/allocatedSnoMap")
	public String allSnoMap(Model model) {
		return "maoroles/Allocated_SurveyNo_Mapping";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/datasrc")
	public String getDataSrc(Model model) {
		return "maoroles/DataSourceWiseExt";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/efishRofr")
	public String getEfishRofr(Model model) {
		return "maoroles/EfishRofrDetails";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/farmdet")
	public String getFarmerDet(Model model) {
		return "maoroles/FarmerDetails";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/allcrops")
	public String getSeasonAllCrops(Model model) {
		return "maoroles/SeasonCropBookedExtent";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/efblkext")
	public String getBlockedEfishExt(Model model) {
		return "maoroles/BlockedEfishExtent";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/objunobj")
	public String getObjUnObj(Model model) {
		return "maoroles/obj_unObj";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/empL")
	public String getRepEmpList(Model model) {
		return "maoroles/EmployeeList";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/repvaadet2")
	public String getRepVAADet2(Model model) {
		return "maoroles/VaaDetails";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/devregdett1")
	public String getDevRegDet(Model model) {
		return "maoroles/DeviceRegDetails";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/cropins")
	public String getCropInsGri(Model model) {
		return "maoroles/CropInsGrivence";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/supchkra")
	public String getSupChkRecrdAlloted(Model model) {
		return "maoroles/SuperCheckRecordsAlloted";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/supchkappr")
	public String getSupChkAppr(Model model) {
		return "maoroles/SpuperChkAppr";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/supchkk")
	public String getSupChR(Model model) {
		return "maoroles/SpuperCheck";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/supchkrejreport")
	public String getSupChRej(Model model) {
		return "maoroles/SuperChk_rejReport";

	}

//======================CropBookingDetailsMaoIntf===========================//
	@Autowired
	CropBookingDetailsMaoIntfPartition cropBookingDetailsMaoIntfPartition;

	@PreAuthorize("hasAuthority('5') || hasAuthority('17')  ")
	@GetMapping("/cropbmao")
	public String getcrpMao(Model model, HttpSession session) {
		String wbemname = (String) session.getAttribute("wbemname");
		String wbevname = (String) session.getAttribute("wbevname");

		System.out.println("wbemname=>" + wbemname);
		System.out.println("wbevname=>" + wbevname);

		return "maoroles/CropBookingDetailsMaoIntf";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/phyackvwise")
	public String getPhyAckVwise(Model model) {
		return "maoroles/PhyAckVwise";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/phyackrbk")
	public String getPhyAckRbk(Model model) {
		return "maoroles/Rep_phy_ack_rbk";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/dwnlddetintf")
	public String getDwnldDetIntf(Model model) {
		return "maoroles/Rep_DownloadedDetailsIntf";

	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/landdatadet")
	public String getLandData(HttpSession httpSession,Model model) {
String activeYear= httpSession.getAttribute("ACTIVEYEAR").toString();
		
		System.out.println(activeYear);
		model.addAttribute("cropyearr",activeYear);
		return "maoroles/RepLandDataDetails";

	}// repLandDataDetailsRepo
//***********************************//
	@Autowired Rep_VillLandDataDetailsPartition rep_VillLandDataDetailsPartition;
	@PreAuthorize("hasAuthority('5')")
	@RequestMapping("/villLandData")
	public String getVillLandData(HttpServletRequest httpServletRequest, 
			Model model,String cropyear,HttpSession httpSession) {
		System.out.println("villLandData");
		String dcode = httpServletRequest.getParameter("dcodee");//System.out.println("dcode==========="+dcode);
		String mcode = httpServletRequest.getParameter("mcodee");//System.out.println("mcode=========="+mcode);
		String Year = httpServletRequest.getParameter("cropyearr");//System.out.println("Year========="+Year);
	Year = httpSession.getAttribute("ACTIVEYEAR").toString();
//		String[] season = Year.split("@");
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);

		System.out.println("dcode=>" + dcode);
		System.out.println("mcode=>" + mcode);
		System.out.println("seasonYear=>" + Year);

		List<Rep_VillLandDataDetails> list = null;

			list = rep_VillLandDataDetailsPartition.getVillData(dcode, mcode, Year);
		

		model.addAttribute("data", list);
		return "maoroles/Rep_VillLandDataDetails";
	}
//**********************************************************************//
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/nonwebview")
	public String getNonwebV(Model model) {
		return "maoroles/nonWebView";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/crpwrbkext")
	public String getcrpWiseRbkExt(Model model, HttpServletRequest httpServletRequest) {
		return "maoroles/CropwiseExtBooked_RBKwise";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/authmaoekycvaavaro")
	public String getAuthMaoEkyc() {
		return "maoroles/Auth_MAO_vaavroekyc";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/supupdsts")
	public String getSupUpdsts() {
		return "maoroles/Superchekupdstatus";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/maosocialauditR")
	public String getMaoSocialAuditCorrectionR() {
		return "maoroles/MaoSocialAuditcorrection";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/repsupchkvill")
	public String getSupCheckVill(Model model) {
		return "maoroles/Rep_supchk_vill";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/cropinsabs")
	public String getCropInsAbs(Model model) {
		return "maoroles/Rep_cropIns_abtract";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/rbksnomaping")
	public String getRbkSurveyNoMapping(Model model) {
		return "maoroles/RbkSurveyNoMapping";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/pernnialmand")
	public String getPernnialMand(Model model) {
		return "maoroles/Rep_Pernnial_Mand";
	}

	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/pernnialMandRep")
	public String pernnialMand(Model model, HttpServletRequest httpServletRequest, HttpSession session) {

		String dcodee = httpServletRequest.getParameter("dcodee");
		String inputwbmcode = httpServletRequest.getParameter("inputwbmcode");
		String vcode = httpServletRequest.getParameter("vcode");
		String cropyear = httpServletRequest.getParameter("cropyear");
		String inputwbdcode = httpServletRequest.getParameter("inputwbdcode");

		String cropid = httpServletRequest.getParameter("cropid");

		System.out.println("cropyear=>" + cropyear);
		System.out.println("vcode=>" + vcode);
		System.out.println("cropid=>" + cropid);
		System.out.println("inputwbdcode=>" + inputwbdcode);

		List<RepPernnialMand> pernnial = repPernnialMandPartition.getPerrnniaDet(dcodee, inputwbmcode, vcode, cropyear,
				inputwbdcode,cropid);
		System.out.println("list size=>" + pernnial.size());
//
//		String wbemname = (String) session.getAttribute("wbemname");
//		String wbevname = (String) session.getAttribute("wbevname");
//
//		System.out.println("wbemname=>" + wbemname);
//		System.out.println("wbevname=>" + wbevname);
//
//		Integer count = 0;
//		String powner_tenant = "", tsno = "";
//		String tkhno = "";
//		List<PerinalReportModel> list = new ArrayList<>();
//
//		for (RepPernnialMand bean : pernnial) {
//			PerinalReportModel entity = new PerinalReportModel();
//
//			count = count + 1;
//			entity.setCount(count);
//			entity.setWbemname(wbemname);
//			entity.setWbevname(wbevname);
//
//			entity.setOc_name(bean.getOc_name());
//			entity.setOc_fname(bean.getOc_fname());
//
//			powner_tenant = bean.getOwner_tenant();
//			if (powner_tenant.equals("O")) {
//				powner_tenant = "Pattadar";
//			} else {
//				powner_tenant = "Cultivator";
//			}
//			entity.setOwner_tenant(powner_tenant);
//
//			entity.setKh_no(bean.getKh_no());
//			entity.setCr_sno(bean.getCr_sno());
//			entity.setCropname(bean.getCropname());
//
//			entity.setCr_mix_unmix_ext(bean.getCr_mix_unmix_ext());
//			entity.setAge(bean.getAge());
//			entity.setMobileno(bean.getMobileno());
//
//			list.add(entity);
//		}

		model.addAttribute("list", pernnial);

		return "maoroles/Rep_Pernnial_Mand";
	}
	
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/allSno")
	public String getAllocatedSno(Model model) {
		return "maoroles/Rep_AllocatedSurveyNo";
	}

}
