package com.ecrops.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecrops.dto.EFishApprovalDto;
import com.ecrops.repo.EFishApprovalRepo;
import com.ecrops.repo.UpdateEfishApprovalByJCRepo;

@Controller
public class EFishApprovalByJC {
	
	@Autowired
	private EFishApprovalRepo eFishApprovalRepo;
	
	@Autowired
	UpdateEfishApprovalByJCRepo updateEfishApprovalByJCRepo;
	
	@GetMapping("/eFishApprovalByJC")
	public String socialAuditByMAO(Model model, HttpSession httpSession) {
		
		String wbdcode = httpSession.getAttribute("wbdcode").toString(); 

		List<EFishApprovalDto> viewData = eFishApprovalRepo.getEFishApprovalForJC(wbdcode);
		System.out.println("viewData ------------> "+viewData.toString());
		
		
		model.addAttribute("dataList", viewData);

		return "jc/eFishApprovalByJC";
	}
	
	@ResponseBody
	@PostMapping(path = "/saveEFishDetailsByJC")
	public String saveSocialAuditSelectionByMAO(@RequestBody Map<String, String> request, HttpSession httpSession) {
		
		String RecIdOrg = request.get("RecIdOrg");
	    String OccupantNameOrg = request.get("OccupantNameOrg");
	    String OccupantFNameOrg = request.get("OccupantFNameOrg"); 
	    String KhathaNoOrg = request.get("KhathaNoOrg");
	    String SurveyNoOrg = request.get("SurveyNoOrg");  
	    String OccupantExtentOrg = request.get("OccupantExtentOrg");
	    String MappedExtentOrg = request.get("MappedExtentOrg");   
	    String AllowableExtentOrg=request.get("AllowableExtentOrg");
	    String ReqExtentOrg = request.get("ReqExtentOrg");  
	    String DfoSugExtentOrg = request.get("SugExtentOrg"); 
	    String RemarksOrg = request.get("RemarksOrg");
	    String approvalStsOrg = request.get("approvalStsOrg");
	    String villCode = request.get("VillageOrg");
	    
	    String wbdcode = httpSession.getAttribute("wbdcode").toString();  
//	    String wbmcode = httpSession.getAttribute("wbmcode").toString();
	    
	    String status = "";
	    status = updateEfishApprovalByJCRepo.updateEFishDetailsByJC(wbdcode, RecIdOrg, OccupantNameOrg, OccupantFNameOrg, 
	    		KhathaNoOrg, SurveyNoOrg, OccupantExtentOrg, MappedExtentOrg, AllowableExtentOrg, ReqExtentOrg, RemarksOrg, approvalStsOrg, DfoSugExtentOrg, villCode, httpSession);
	    
	    System.out.println("status ----------> "+status);
	    
		
		return status;

	}

}
