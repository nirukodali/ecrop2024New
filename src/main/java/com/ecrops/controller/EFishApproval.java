package com.ecrops.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.EFishApprovalDto;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.EFishApprovalRepo;
import com.ecrops.repo.GetCropYearRepo;
import com.ecrops.repo.UpdateEfishApprovalByVAARepo;
import com.ecrops.repo.VillSecRepo;

@Controller
public class EFishApproval {
	@Autowired
	private GetCropYearRepo cropYearRepo;

	@Autowired
	private VillSecRepo villSecRepo;

	@Autowired
	private EFishApprovalRepo eFishApprovalRepo;
	
	@Autowired
	private UpdateEfishApprovalByVAARepo updateEfishApprovalByVAA;

	@GetMapping("/eFishApproval")
	public String allocOfSurveyNo(Model model,
			HttpSession httpSession, HttpServletRequest httpServletRequest) {
		
		try {
			String role = (String) httpSession.getAttribute("role");
			if (!(role.equals("25"))) {
				model.addAttribute("message", "You are not Eligible to access this Page");
				return "unauthorized";
		    }
			String vscode = (String) httpSession.getAttribute("vscode"); //--,,
			Integer crpyear = (Integer) httpSession.getAttribute("ACTIVEYEAR");
			String season = (String) httpSession.getAttribute("seasonActive");
			String sesdcode = (String) httpSession.getAttribute("dcode");
			String sesmcode = (String) httpSession.getAttribute("mcode");
			
			int vcode = Integer.parseInt(vscode);
			System.out.println("vcode --------------> "+vcode);
			System.out.println("sesdcode --------------> "+sesdcode);
			System.out.println("sesmcode --------------> "+sesmcode);
			
			List<ActiveSeasonProjection> activeSeason = cropYearRepo.getActiveSeason();
			List<ActiveSeasonProjection> rbk = villSecRepo.getRbk(vcode,crpyear,season,Integer.parseInt(sesdcode),Integer.parseInt(sesmcode));
			
			model.addAttribute("activeseason", activeSeason);
			model.addAttribute("rbk", rbk);
		} catch (Exception e){
			e.printStackTrace();
			System.err.println("An unexpected error occured");
		}
		
		return "rbkroles/eFishApproval";
	}
	
	@PostMapping("/eFishDetails")
	public String viewCropBookedDetails( Model model,
			HttpSession httpSession, HttpServletRequest httpServletRequest, 
			RedirectAttributes redirectAttributes) {

	try {

		String KhathaNo = httpServletRequest.getParameter("KhathaNo");
		String SurveyNo = httpServletRequest.getParameter("SurveyNo");
		String rbk = httpServletRequest.getParameter("rbk"); 
		
		List<EFishApprovalDto> viewData = eFishApprovalRepo.getEFishApprovalDetails(KhathaNo.trim(), SurveyNo.trim(), rbk.trim());
		
		model.addAttribute("dataList", viewData);
		model.addAttribute("rbkVillage", rbk);
		model.addAttribute("KhathaNo", KhathaNo);
		model.addAttribute("SurveyNo", SurveyNo);

		return "rbkroles/eFishApprovalDetails";

	} catch (Exception e){
		e.printStackTrace();
		redirectAttributes.addFlashAttribute("message", "An unexpected error occured");
		return "redirect:/eFishApproval";
		
	}
}

	@PostMapping(path = "/saveEFishDetails")
	public String saveSocialAuditSelection(@RequestBody Map<String, String> request, HttpSession httpSession, Model model, 
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
		
		String status = "";
		try {
			String role = (String) httpSession.getAttribute("role");
			if (!(role.equals("25"))) {
				model.addAttribute("message", "You are not Eligible to access this Page");
				return "unauthorized";
			}

			String[] OccExtentLst = request.get("OccExtentLst").split(",");
			String[] MapExtentLst = request.get("MapExtentLst").split(",");
			String[] AllowableExtLst = request.get("AllowableExtLst").split(",");
			String[] RequiredExtLst = request.get("RequiredExtLst").split(",");
			String[] RecIdLst = request.get("RecIdLst").split(",");
			
			System.out.println("*************************");
			
			String village = request.get("VillagetLst");
			String khathaNum = request.get("KhathaLst");
			String surveyNum = request.get("SurveyNoLst");
			
			int updateDetailsCount = updateEfishApprovalByVAA.updateEFishDetailsByVAA(RecIdLst, OccExtentLst, MapExtentLst, AllowableExtLst, 
					RequiredExtLst, village, khathaNum, surveyNum, httpSession);
			
			if (updateDetailsCount > 0) {
				status = "Data Updated SuccessFully";
			} else {
				status = "Data Update Failed";
			}
			
		} catch (Exception e){
			e.printStackTrace();
			status = "An unexpected error occured";
			redirectAttributes.addFlashAttribute("message", status);
			return "redirect:/eFishApproval";
		}
		
		return "redirect:/eFishApproval";

	}

}
