package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.crop.response.CropIrrgMethod_Master;
import com.ecrops.dto.crop.response.CropNamesData;
import com.ecrops.dto.crop.response.CropSeed_Schema;
import com.ecrops.dto.crop.response.CropYearCrbk;
import com.ecrops.dto.crop.response.RejReasonData;
import com.ecrops.dto.crop.response.WaterResources;
import com.ecrops.dto.webland.CrDetailsEntity;
import com.ecrops.entity.crop.EditFormCRBKValidation;
//import com.ecrops.entity.crop.VillageSec;
import com.ecrops.entity.VillageSec;
import com.ecrops.repo.crop.CorrectionOfRecords;
import com.ecrops.repo.crop.CropRejEditIntf;
import com.ecrops.repo.crop.SaveCultRejByVro;
import com.ecrops.repo.crop.SaveUidRejVro;
import com.ecrops.repo.crop.SaveVroRejdet;

@Controller
public class CRBKFormContoller {

	@Autowired
	private SaveVroRejdet saveVroRejdet;

	@Autowired
	private SaveUidRejVro saveUidRejVro;

	@Autowired
	private SaveCultRejByVro saveCultRejByVro;

	@Autowired
	private CorrectionOfRecords correctionOfRecords;

	@Autowired
	private CropRejEditIntf cropRejEditIntf;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/crbkFormEntry")
	public String getCrbkFormEntry(Model model, HttpSession httpSession) {

		String village = (String) httpSession.getAttribute("vscode");

		if (village == null || village.isEmpty()) {
			String errorMessage = "Village code is missing or empty. Please provide a valid village code.";
			model.addAttribute("errorMessage", errorMessage);
			return "rbkroles/EditFormCRBK";
		}

		try {
			int villageCode = Integer.parseInt(village);

			List<VillageSec> villname = correctionOfRecords.getVillages(villageCode);
			model.addAttribute("villname", villname);

			List<CropYearCrbk> activeSeasons = correctionOfRecords.getCropYear();
			List<RejReasonData> reasons = correctionOfRecords.findRejReasons();
			model.addAttribute("activeSeasons", activeSeasons);
			model.addAttribute("reasons", reasons);
			model.addAttribute("editFormCRBKValidation", new EditFormCRBKValidation());
			return "rbkroles/EditFormCRBK";

		} catch (NumberFormatException e) {
			String errorMessage = "Invalid village code format. Please provide a valid numeric village code.";
			model.addAttribute("errorMessage", errorMessage);
			return "rbkroles/EditFormCRBK"; // Return the page with an error message
		}
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/submitForm")
	public String submitForm(
			@Valid @ModelAttribute("editFormCRBKValidation") EditFormCRBKValidation editFormCRBKValidation,
			BindingResult bindingResult, @RequestParam("cropyear") String cropyear, @RequestParam("vcode") String vcode,
			@RequestParam("searchParam") String searchParam, @RequestParam("khno_bkid") String khno_bkid,
			@RequestParam("surveyno") String surveyno, @RequestParam("uid") String uid,
			@RequestParam("code") String code, Model model, HttpSession httpSession) {

		if (cropyear == null || cropyear.isEmpty() || vcode == null || vcode.isEmpty() || searchParam == null
				|| searchParam.isEmpty()) {
			model.addAttribute("errorMessage", "Required parameters are missing.");
			return "rbkroles/EditFormCRBK";
		}

		RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
		Boolean valid = expressionclassMethod.checkstring(surveyno);
		Boolean valid2 = expressionclassMethod.checkstring(uid);
		Boolean valid3 = expressionclassMethod.checkstring(khno_bkid);

		String[] cropYearParts = cropyear.split("@");
		String cropYear = (cropYearParts.length > 1) ? cropYearParts[1] : "";
		String season = (cropYearParts.length > 0) ? cropYearParts[0] : "";
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");

		List<WaterResources> waterResources = correctionOfRecords.findwsrcidAndwsrcdesc();
		List<CropIrrgMethod_Master> irgcodeAndirgdesc = correctionOfRecords.findirgcodeAndirgdesc();
		List<CropSeed_Schema> cropschtypeAndcropschdesc = correctionOfRecords.findcropschtypeAndcropschdesc();
		List<CropNamesData> cropidCropName = correctionOfRecords.findCropidAndCropName();

		model.addAttribute("cropidCropName", cropidCropName);
		model.addAttribute("waterResources", waterResources);
		model.addAttribute("irgcodeAndirgdesc", irgcodeAndirgdesc);
		model.addAttribute("cropschtypeAndcropschdesc", cropschtypeAndcropschdesc);

//		String activeYear = (String) httpSession.getAttribute("ACTIVEYEAR");
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
//	    activeYear = "2023";
		String seswbdcode = String.valueOf(wbdcode);
		List<CrDetailsEntity> cropDetails = null;
		try {
			cropDetails = cropRejEditIntf.cropRejEditDetails(cropYear, season, searchParam, surveyno, uid, khno_bkid,
					vcode, seswbdcode, code, activeYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cropDetails != null && cropDetails.size() > 0) {
			model.addAttribute("cropDetails", cropDetails);
			model.addAttribute("cropYear", cropYear);
			model.addAttribute("season", season);
			model.addAttribute("surveyno", surveyno);
			model.addAttribute("uid", uid);
			model.addAttribute("khno_bkid", khno_bkid);
			model.addAttribute("vcode", vcode);
		} else {
			model.addAttribute("msg", "No Records Found");
		}
		if (bindingResult.hasErrors()) {
			return "rbkroles/EditFormCRBK";
		}
		String formAction = getFormAction(code);
		model.addAttribute("formAction", formAction);
		return formAction;
	}

	private String getFormAction(String code) {
		if ("5".equals(code) || "6".equals(code)) {
			return "rbkroles/cropRejEditIntf";
		} else if ("2".equals(code)) {
			return "rbkroles/cultUidRejByVro";
		} else if ("1".equals(code)) {
			return "rbkroles/cultRejByVro";
		} else {
			return "rbkroles/EditFormCRBK";
		}
	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/saveUidRejVro")
	public String saveVroRejedit(HttpServletRequest httpServletRequest, Model model, HttpSession httpSession,
			@RequestParam(name = "bookingIds") String bkIdsList, @RequestParam(name = "cropNames") String cropCodes,
			@RequestParam(name = "crNumber") String crNumber, @RequestParam(name = "varietyIds") String varietyCodes,
			@RequestParam(name = "crSowDates") String sowDateList, @RequestParam(name = "cropYear") String cropYear,
			@RequestParam(name = "season") String season, @RequestParam(name = "surveyno") String surveyno,
			@RequestParam(name = "khno_bkid") String khno_bkid, @RequestParam(name = "uid") String uid,
			@RequestParam(name = "vcode") String vcode, @RequestParam(name = "newcrFarmerUids") String newcrFarmerUid,
			@RequestParam(name = "oldcrFarmerUids") String oldcrFarmerUid, RedirectAttributes redirectiveAttribute) {
		RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
		Boolean valid = expressionclassMethod.checkstring(surveyno);
		Boolean valid2 = expressionclassMethod.checkstring(uid);
		Boolean valid3 = expressionclassMethod.checkstring(khno_bkid);
		Boolean valid4 = expressionclassMethod.checkstring(cropCodes);
		Boolean valid5 = expressionclassMethod.checkstring(newcrFarmerUid);
		Boolean valid6 = expressionclassMethod.checkstring(oldcrFarmerUid);

		if (bkIdsList.isEmpty() || cropCodes.isEmpty() || crNumber.isEmpty() || varietyCodes.isEmpty()
				|| sowDateList.isEmpty() || cropYear.isEmpty() || season.isEmpty() || vcode.isEmpty()
				|| newcrFarmerUid.isEmpty() || oldcrFarmerUid.isEmpty()) {
			redirectiveAttribute.addFlashAttribute("errorMessage", "Required parameters are missing.");
			return "rbkroles/crbkFormEntry";
		}

		String status = "";
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		String activeYear = (String) httpSession.getAttribute("ACTIVEYEAR");
//			activeYear = "2023";
		status = saveUidRejVro.saveVroUidRejVroDetails(wbdcode, cropYear, season, surveyno, khno_bkid, uid,
				vcode, activeYear, bkIdsList, cropCodes, varietyCodes, sowDateList, crNumber, oldcrFarmerUid,
				newcrFarmerUid);
		
		redirectiveAttribute.addFlashAttribute("msg", status);
		return "redirect:/crbkFormEntry";

//		redirectiveAttribute.addFlashAttribute("msg", status);
//		return "redirect:/rbkroles/crbkFormEntry";
	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/saveVroRejedit")
	public String saveVroRejedit(HttpServletRequest httpServletRequest, Model model, HttpSession httpSession,
			@RequestParam(name = "bookingIds") String bkIdsList,
			@RequestParam(name = "cropSchDescs") String cropSchDesc, @RequestParam(name = "cropNames") String cropName,
			@RequestParam(name = "varietyIdOrgs") String varietyIdOrg,
			@RequestParam(name = "varietyIds") String varietyId,
			@RequestParam(name = "irrScrIdOrgs") String irrScrIdOrgs, @RequestParam(name = "irrScrId") String irrScrId,
			@RequestParam(name = "irrMethodLists") String irrMethodList,
			@RequestParam(name = "crSowDates") String sowDateList, @RequestParam(name = "occupNames") String occupName,
			@RequestParam(name = "khNos") String khNo, @RequestParam(name = "crSnos") String crSno,
			@RequestParam(name = "crMixUnmixExts") String crMixUnmixExt, @RequestParam(name = "cr_no") String cr_no,
			@RequestParam(name = "cropYear") String cropYear, @RequestParam(name = "season") String season,
			@RequestParam(name = "surveyno") String surveyno, @RequestParam(name = "khno_bkid") String khno_bkid,
			@RequestParam(name = "uid") String uid, @RequestParam(name = "vcode") String vcode,
			@RequestParam(name = "crNumber") String crNumber, RedirectAttributes redirectiveAttribute) {

		if (bkIdsList.isEmpty() || cropSchDesc.isEmpty() || cropName.isEmpty() || varietyIdOrg.isEmpty()
				|| varietyId.isEmpty() || irrScrIdOrgs.isEmpty() || irrScrId.isEmpty() || irrMethodList.isEmpty()
				|| sowDateList.isEmpty() || occupName.isEmpty() || crMixUnmixExt.isEmpty() || cr_no.isEmpty()
				|| cropYear.isEmpty() || season.isEmpty() || khno_bkid.isEmpty() || vcode.isEmpty()
				|| crNumber.isEmpty()) {
			redirectiveAttribute.addFlashAttribute("msg", "Please fill in all the required fields.");
			return "rbkroles/crbkFormEntry";
		}

		String status = "";
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		String activeYear = (String) httpSession.getAttribute("activeYear");
//			activeYear = "2023";

		status = saveVroRejdet.saveVroRejectDetail(wbdcode, cropYear, season, surveyno, khno_bkid, uid, vcode,
				crNumber, activeYear, bkIdsList, cropSchDesc, cropName, varietyIdOrg, varietyId, irrScrIdOrgs, irrScrId,
				irrMethodList, sowDateList, occupName, khNo, crSno, crMixUnmixExt, cr_no);
		
		redirectiveAttribute.addFlashAttribute("msg", status);
		return "redirect:/crbkFormEntry";

//		redirectiveAttribute.addFlashAttribute("msg", status);
//
//		return "rbkroles/crbkFormEntry";
	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/cultRejByVro")
	public String handleCultRejByVro(HttpServletRequest httpServletRequest, Model model, HttpSession httpSession,
			@RequestParam(name = "bookingId") String bookingId, @RequestParam(name = "cropSchDesc") String cropSchDesc,
			@RequestParam(name = "cropId") String cropId, @RequestParam(name = "varietyId") String varietyId,
			@RequestParam(name = "crSowDate") String crSowDate, @RequestParam(name = "irrScrId") String irrScrId,
			@RequestParam(name = "irgDesc") String irgDesc, @RequestParam(name = "oldoccupName") String oldoccupName,
			@RequestParam(name = "oldoccupFatherName") String oldoccupFatherName,
			@RequestParam(name = "newoccupName") String newoccupName,
			@RequestParam(name = "newoccupFatherName") String newoccupFatherName,
			@RequestParam(name = "khNo") String khNo, @RequestParam(name = "crSno") String crSno,
			@RequestParam(name = "crMixUnmixExt") String crMixUnmixExt, @RequestParam(name = "cr_no") String cr_no,
			@RequestParam(name = "cropYear") String cropYear, @RequestParam(name = "season") String season,
			@RequestParam(name = "surveyno") String surveyno, @RequestParam(name = "khno_bkid") String khno_bkid,
			@RequestParam(name = "uid") String uid, @RequestParam(name = "vcode") String vcode,
			RedirectAttributes redirectiveAttribute) {

		System.out.println("*************************************************");

//	    	if (bookingId.isEmpty() || cropSchDesc.isEmpty() || cropId.isEmpty() || varietyId.isEmpty() ||
//	                crSowDate.isEmpty() || irrScrId.isEmpty() || irgDesc.isEmpty() || oldoccupName.isEmpty() ||
//	                oldoccupFatherName.isEmpty() || newoccupName.isEmpty() || newoccupFatherName.isEmpty() ||
//	                khNo.isEmpty() || crSno.isEmpty() || crMixUnmixExt.isEmpty() || cr_no.isEmpty() ||
//	                cropYear.isEmpty() || season.isEmpty() || khno_bkid.isEmpty() || vcode.isEmpty()) {
//	            redirectiveAttribute.addFlashAttribute("msg", "Please fill in all the required fields.");
//	            return "redirect:/rbkroles/crbkFormEntry";
//	        }

		String status = "";
		try {
			Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
//    		String activeYear = (String) httpSession.getAttribute("ACTIVEYEAR");
		String activeYear = "2024";
		RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
		Boolean valid = expressionclassMethod.checkstring(surveyno);
		Boolean valid2 = expressionclassMethod.checkstring(uid);
		Boolean valid3 = expressionclassMethod.checkstring(khno_bkid);
		Boolean valid4 = expressionclassMethod.checkstring(irrScrId);
		Boolean valid5 = expressionclassMethod.checkstring(newoccupFatherName);
		Boolean valid6 = expressionclassMethod.checkstring(newoccupName);

		status = saveCultRejByVro.saveDetailsOfRejectedONAndOFName(wbdcode, cropYear, season, surveyno,
				khno_bkid, uid, vcode, activeYear, bookingId, cropSchDesc, cropId, varietyId, irrScrId, irgDesc,
				crSowDate, oldoccupName, oldoccupFatherName, newoccupName, newoccupFatherName, khNo, crSno,
				crMixUnmixExt, cr_no);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An unexpected error occured");
		}
		System.out.println("-------"+status);
//		return status;

		redirectiveAttribute.addFlashAttribute("msg", status);
//		return status;
		return "redirect:/crbkFormEntry";
	}

}