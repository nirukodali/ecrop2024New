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
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.EFishApprovalRepo;
import com.ecrops.repo.UpdateEfishApprovalByMAORepo;
import com.ecrops.repo.VillSecRepo;

@Controller
public class EFishApprovalByMAO {
	
	@Autowired
	private EFishApprovalRepo eFishApprovalRepo;
	
	@Autowired
	UpdateEfishApprovalByMAORepo updateEfishApprovalByMAORepo;
	
	@GetMapping("/eFishApprovalByMAO")
	public String socialAuditByMAO(Model model, HttpSession httpSession) {
		
		String wbdcode = httpSession.getAttribute("wbdcode").toString();  
	    String wbmcode = httpSession.getAttribute("wbmcode").toString();
	    
	    System.out.println("wbdcode ----> "+wbdcode+"   wbmcode -------> "+wbmcode);
	    
		List<EFishApprovalDto> viewData = eFishApprovalRepo.getEFishApprovalForMAO(wbdcode, wbmcode);
		System.out.println("viewData ------------> "+viewData.toString());
		
		
		model.addAttribute("dataList", viewData);

		return "maoroles/eFishApprovalByMAO";
	}
	
	@ResponseBody
	@PostMapping(path = "/saveEFishDetailsByMAO")
	public String saveSocialAuditSelectionByMAO(@RequestBody Map<String, String> request, HttpSession httpSession) {
		
		System.out.println("******* Entered in saveEFishDetailsByMAO *********");

		String RecIdOrg = request.get("RecIdOrg");
	    String OccupantNameOrg = request.get("OccupantNameOrg");
	    String OccupantFNameOrg = request.get("OccupantFNameOrg"); 
	    String KhathaNoOrg = request.get("KhathaNoOrg");
	    String SurveyNoOrg = request.get("SurveyNoOrg");  
	    String OccupantExtentOrg = request.get("OccupantExtentOrg");
	    String MappedExtentOrg = request.get("MappedExtentOrg");   
	    String AllowableExtentOrg=request.get("AllowableExtentOrg");
	    String ReqExtentOrg = request.get("ReqExtentOrg");  
	    String RemarksOrg = request.get("RemarksOrg");
	    String approvalStsOrg = request.get("approvalStsOrg");
	    
	    String wbdcode = httpSession.getAttribute("wbdcode").toString();  
	    String wbmcode = httpSession.getAttribute("wbmcode").toString();
	    
	    String status = "";
	    int updateDetails = updateEfishApprovalByMAORepo.updateEFishDetailsByMAO(wbdcode, wbmcode, RecIdOrg, OccupantNameOrg, OccupantFNameOrg, 
	    		KhathaNoOrg, SurveyNoOrg, OccupantExtentOrg, MappedExtentOrg, AllowableExtentOrg, ReqExtentOrg, RemarksOrg, approvalStsOrg, httpSession);
	    
	    if(updateDetails > 0) {
	    	status = "Data Updated SuccessFully";
	    } else {
	    	status ="Failed to Update";
	    }
		
		
		return status;

	}

}
