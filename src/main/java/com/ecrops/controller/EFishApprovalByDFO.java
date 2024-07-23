package com.ecrops.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecrops.dto.EFishApprovalDto;
import com.ecrops.repo.EFishApprovalRepo;
import com.ecrops.repo.UpdateEfishApprovalByDFORepo;

@Controller
public class EFishApprovalByDFO {
	
	@Autowired
	private EFishApprovalRepo eFishApprovalRepo;
	
	@Autowired
	UpdateEfishApprovalByDFORepo updateEfishApprovalByDFORepo;
	
	@RequestMapping("/eFishApprovalByDFO")
	public String socialAuditByMAO(Model model, HttpSession httpSession) {
		
		String wbdcode = httpSession.getAttribute("wbdcode").toString();  
	    
		List<EFishApprovalDto> viewData = eFishApprovalRepo.getEFishApprovalForDFO(wbdcode);
		System.out.println("viewData ------------> "+viewData.toString());
		
		model.addAttribute("dataList", viewData);

		return "dfo/eFishApprovalByDFO";
	}
	
	@ResponseBody
	@PostMapping(path = "/saveEFishDetailsByDFO")
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
	    
	    String wbdcode = httpSession.getAttribute("wbdcode").toString();  
	    System.out.println("**********************");
//	    String wbmcode = httpSession.getAttribute("wbmcode").toString();
	    
	    String status = "";
	    int updateDetails = updateEfishApprovalByDFORepo.updateEFishDetailsByDFO(wbdcode, RecIdOrg, OccupantNameOrg, OccupantFNameOrg, 
	    		KhathaNoOrg, SurveyNoOrg, OccupantExtentOrg, MappedExtentOrg, AllowableExtentOrg, ReqExtentOrg, RemarksOrg, approvalStsOrg, DfoSugExtentOrg, httpSession);
	    
	    System.out.println("updateDetails ----------> "+updateDetails);
	    
	    if(updateDetails > 0) {
	    	status = "Data Updated SuccessFully";
	    } else {
	    	status ="Failed to Update";
	    }
		
		
		return status;

	}

}
