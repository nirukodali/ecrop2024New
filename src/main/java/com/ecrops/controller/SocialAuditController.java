package com.ecrops.controller;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.entity.CropNamesEntity;
import com.ecrops.entity.CropNatureEntity;
import com.ecrops.entity.CropSeedSchemeEntity;
import com.ecrops.entity.DeleteReasonsEntity;
import com.ecrops.entity.ViewCropBookedDetailsEntity;
import com.ecrops.entity.WaterResourcesEntity;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.GetCropNames;
import com.ecrops.repo.GetCropNature;
import com.ecrops.repo.GetCropSeedScheme;
import com.ecrops.repo.GetCropYearRepo;
import com.ecrops.repo.GetReasonsForDelete;
import com.ecrops.repo.GetWaterResources;
import com.ecrops.repo.SaveSocialAudit;
import com.ecrops.repo.UpdateSocialAudit;
import com.ecrops.repo.ViewCropBookedDetailsRepo;
import com.ecrops.repo.VillSecRepo;
import com.ecrops.repo.VillageRevRepo;

@Controller
public class SocialAuditController {
	@Autowired
	private GetCropYearRepo cropYearRepo;

	@Autowired
	private VillSecRepo villSecRepo;

	@Autowired
	private GetReasonsForDelete getReasonsForDelete;

	@Autowired
	private ViewCropBookedDetailsRepo viewCropBookedDetailsRepo;

	@Autowired
	private GetCropNames getCropNames;

	@Autowired
	private GetWaterResources getWaterResources;

	@Autowired
	private GetCropNature getCropNature;

	@Autowired
	private SaveSocialAudit saveSocialAudit;

	@Autowired
	private UpdateSocialAudit updateSocialAudit;

	@Autowired
	private GetCropSeedScheme getCropSeedScheme;
	
	@Autowired
	private VillageRevRepo villageRevRepo;
	

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/socialAudit")
	public String allocOfSurveyNo(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {

		try {
			String role = (String) httpSession.getAttribute("role");
			if (!(role.equals("25"))) {
				model.addAttribute("message", "You are not Eligible to access this Page");
				return "unauthorized";
			}
			List<ActiveSeasonProjection> activeSeason = cropYearRepo.getActiveSeason();
			
			Integer vcode = Integer.parseInt((String) httpSession.getAttribute("vscode"));
			List<ActiveSeasonProjection> rbk = villageRevRepo.getVillageListByRbk(vcode);

			model.addAttribute("activeseason", activeSeason);
			model.addAttribute("rbk", rbk);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An unexpected error occured");
		}

		return "rbkroles/socialAudit";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/cropBookedDetails")
	public String viewCropBookedDetails(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) {

		try {

			String role = (String) httpSession.getAttribute("role");
			if (!(role.equals("25"))) {
				model.addAttribute("message", "You are not Eligible to access this Page");
				return "unauthorized";
			}
			String crYear = httpServletRequest.getParameter("crYear");
			String rbk = httpServletRequest.getParameter("rbk"); 
			int vscode = Integer.parseInt(rbk);
			String applicantName = httpServletRequest.getParameter("applicantName");

			String applicantNumber = httpServletRequest.getParameter("applicantNumber"); 
																							
			String applicantSerialNo = httpServletRequest.getParameter("applicantSerialNo"); 
																								
			String applicantBookingId = httpServletRequest.getParameter("applicantBookingId");
			Integer bookingId = Integer.parseInt(applicantBookingId);

			String wbdcode = httpSession.getAttribute("wbdcode").toString();

			String[] cropYear = crYear.split("\\@");
			String season = cropYear[0];
			String year = cropYear[1];
			RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
			Boolean valid= expressionclassMethod.checkstring(applicantName);
			Boolean valid2= expressionclassMethod.checkstring(applicantSerialNo);
			Boolean valid3= expressionclassMethod.checkstring(applicantBookingId);

			if (applicantName == null || applicantName.isEmpty() || applicantNumber == null || applicantNumber.isEmpty()
					|| applicantSerialNo == null || applicantSerialNo.isEmpty() || applicantBookingId == null
					|| applicantBookingId.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Improper Data");
				return "redirect:/socialAudit";
			}

			String partitionName = "cr_crop_det_new_v_";
			if (Integer.parseInt(wbdcode) <= 9) {
				partitionName = partitionName + season + "0" + wbdcode + year;
			} else {
				partitionName = partitionName + season + wbdcode + year;
			}

			String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();

			if (activeYear.equals(year)) {
				partitionName = "ecrop" + activeYear + "." + partitionName;
			}

			int cr_sow_type = 0;
			
			List<ViewCropBookedDetailsEntity> viewData = viewCropBookedDetailsRepo.viewCropBookedDetails(vscode,
					bookingId, partitionName);
			List<DeleteReasonsEntity> cropDeleteReason = getReasonsForDelete.getReasonsForDelete();
			List<CropNamesEntity> cropNames = getCropNames.getCropNames();
			List<WaterResourcesEntity> waterResources = getWaterResources.getWaterResources();

			List<CropNatureEntity> cropNature = new ArrayList<>();
			if (cr_sow_type == 1) {
				cropNature = getCropNature.getCropNatureForId1();
			} else {
				cropNature = getCropNature.getCropNature();
			}
			List<CropSeedSchemeEntity> cropSeedScheme = getCropSeedScheme.getCropSeedScheme();

			model.addAttribute("applID", applicantSerialNo);
			model.addAttribute("mobId", applicantNumber);
			model.addAttribute("applName", applicantName);
				model.addAttribute("rbk",rbk);
			model.addAttribute("season", season);
			model.addAttribute("year", year);

			model.addAttribute("viewData", viewData);
			model.addAttribute("cropDeleteReason", cropDeleteReason);
			model.addAttribute("cropNames", cropNames);
			model.addAttribute("waterResources", waterResources);
			model.addAttribute("cropNature", cropNature);
			model.addAttribute("cropSeedScheme", cropSeedScheme);

			return "rbkroles/cropBookedDetails";

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "An unexpected error occured");
			return "rbkroles/redirect:/socialAudit";

		}
	}

	@PreAuthorize("hasAuthority('25')")
	@ResponseBody
	@PostMapping(path = "/saveSocialAudit")
	public String saveSocialAuditSelection(@RequestBody Map<String, String> request, HttpSession httpSession,
			Model model, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

		String status = "";
		try {

			String role = (String) httpSession.getAttribute("role");
			if (!(role.equals("25"))) {
				model.addAttribute("message", "You are not Eligible to access this Page");
				return "unauthorized";
			}

			String clientip = httpServletRequest.getRemoteAddr();
			String applicatonId = request.get("applIDOrg");
			String appMobId = request.get("mobIdOrg");
			String applName = request.get("applNameOrg");
			String season = request.get("seasonOrg");
			String year = request.get("yearOrg");
			int vscode =Integer.parseInt(request.get("rbk").toString());
			int wbdcode = (Integer) httpSession.getAttribute("wbdcode");
			int mandcode = (Integer) httpSession.getAttribute("wbmcode");
			String[] bookingIdsLst = request.get("bookingIdLst").split(",");
			String[] kh_noLst = request.get("khathaNoLst").split(",");
			String[] sr_noLst = request.get("surveyNoLst").split(",");
			String[] sowDtLst = request.get("sownDateLst").split(",");
			String[] cropNameLst = request.get("cropNameLst").split(",");
			String[] varietyNameLst = request.get("varietyNameLst").split(",");
			String[] totExtentLst = request.get("totExtentLst").split(",");
			String[] farmernameLst = request.get("farmerNameLst").split(",");
			String[] farmerFatherLst = request.get("farmerFatherNameLst").split(",");
			String[] aadharIdLst = request.get("aadhaarNoLst").split(",");
			String[] waterIdLst = request.get("waterSourceLst").split(",");
			String[] cropNatureIdLst = request.get("cropingPatternLst").split(",");
			String[] farmingTypeLst = request.get("farmingTypeLst").split(",");
			String[] action_typeLst = request.get("actionTypeLst").split(",");
			String[] del_listLst = request.get("delReasonLst").split(",");

			String[] bookingIds_orgLst = request.get("bookingIdOrgLst").split(",");
			String[] kh_no_orgLst = request.get("khathaNoOrgLst").split(",");
			String[] sr_no_orgLst = request.get("surveyNoOrgLst").split(",");
			String[] sowDt_orgLst = request.get("sownDateOrgLst").split(",");
			String[] cropName_orgLst = request.get("cropNameOrgLst").split(",");
			String[] varietyName_orgLst = request.get("varietyNameOrgLst").split(",");
			String[] totExtent_orgLst = request.get("totExtentOrgLst").split(",");
			String[] farmername_orgLst = request.get("farmerNameOrgLst").split(",");
			String[] farmerFather_orgLst = request.get("farmerFatherNameOrgLst").split(",");
			String[] aadharId_orgLst = request.get("aadhaarNoOrgLst").split(",");
			String[] waterId_orgLst = request.get("waterSourceOrgLst").split(",");
			String[] cropNatureId_orgLst = request.get("cropingPatternOrgLst").split(",");
			String[] farmingType_orgLst = request.get("farmingTypeOrgLst").split(",");
			String[] cr_no_orgLst = request.get("cropNoOrgLst").split(",");

			if (bookingIdsLst == null || bookingIdsLst.length == 0 || kh_noLst == null || kh_noLst.length == 0
					|| sr_noLst == null || sr_noLst.length == 0 || sowDtLst == null || sowDtLst.length == 0
					|| cropNameLst == null || cropNameLst.length == 0 || varietyNameLst == null
					|| varietyNameLst.length == 0 || totExtentLst == null || totExtentLst.length == 0
					|| farmernameLst == null || farmernameLst.length == 0 || farmerFatherLst == null
					|| farmerFatherLst.length == 0 || aadharIdLst == null || aadharIdLst.length == 0
					|| waterIdLst == null || waterIdLst.length == 0 || cropNatureIdLst == null
					|| cropNatureIdLst.length == 0 || farmingTypeLst == null || farmingTypeLst.length == 0
					|| action_typeLst == null || action_typeLst.length == 0) {

				redirectAttributes.addFlashAttribute("message", "Improper Data");
				return "redirect:/cropBookedDetails";
			}

//			RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
//			Boolean valid= expressionclassMethod.checkstring(farmernameLst);
//			Boolean valid2= expressionclassMethod.checkstring(farmerFatherLst);
//			Boolean valid3= expressionclassMethod.checkstring(applicantBookingId);
//			Boolean valid4= expressionclassMethod.checkstring(applicantName);
//			Boolean valid5= expressionclassMethod.checkstring(applicantSerialNo);
//			Boolean valid6= expressionclassMethod.checkstring(applicantBookingId);
			String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
			status = saveSocialAudit.saveSocialAuditDetails(bookingIdsLst, kh_noLst, sr_noLst, sowDtLst,
					cropNameLst, varietyNameLst, totExtentLst, farmernameLst, farmerFatherLst, aadharIdLst, waterIdLst,
					cropNatureIdLst, farmingTypeLst, bookingIds_orgLst, cr_no_orgLst, kh_no_orgLst, sr_no_orgLst,
					sowDt_orgLst, cropName_orgLst, varietyName_orgLst, totExtent_orgLst, farmername_orgLst,
					farmerFather_orgLst, aadharId_orgLst, waterId_orgLst, cropNatureId_orgLst, farmingType_orgLst,
					action_typeLst, del_listLst, applName, appMobId, applicatonId, clientip, season, year, wbdcode,
					mandcode, vscode, activeYear);

		} catch (Exception e) {
			e.printStackTrace();
			status = "An unexpected error occured";
		}

		return status;

	}

}
