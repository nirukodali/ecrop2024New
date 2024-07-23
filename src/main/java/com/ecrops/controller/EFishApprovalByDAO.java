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
import com.ecrops.repo.UpdateEfishApprovalByDAORepo;
import com.ecrops.repo.UpdateEfishApprovalByMAORepo;
import com.ecrops.repo.VillSecRepo;

@Controller
public class EFishApprovalByDAO {
	
	@Autowired
	private EFishApprovalRepo eFishApprovalRepo;
	
	@Autowired
	UpdateEfishApprovalByDAORepo updateEfishApprovalByDAORepo;
	
	@GetMapping("/eFishApprovalByDAO")
	public String socialAuditByMAO(Model model, HttpSession httpSession) {

		String wbdcode = httpSession.getAttribute("wbdcode").toString();  
         
		List<EFishApprovalDto> viewData = eFishApprovalRepo.getEFishApprovalForDAO(wbdcode);
		System.out.println("viewData ------------> "+viewData.toString());

		model.addAttribute("dataList", viewData);

		return "dao/eFishApprovalByDAO";
	}
	
	@ResponseBody
	@PostMapping(path = "/saveEFishDetailsByDAO")
	public String saveSocialAuditSelectionByMAO(@RequestBody Map<String, String> request, HttpSession httpSession) {
		
		System.out.println("******* Entered in saveEFishDetailsByDAO *********");
		
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
//	    String wbmcode = httpSession.getAttribute("wbmcode").toString();
	    
	    String status = "";
	    int updateDetails = updateEfishApprovalByDAORepo.updateEFishDetailsByDAO(wbdcode, RecIdOrg, OccupantNameOrg, OccupantFNameOrg, 
	    		KhathaNoOrg, SurveyNoOrg, OccupantExtentOrg, MappedExtentOrg, AllowableExtentOrg, ReqExtentOrg, RemarksOrg, approvalStsOrg, httpSession);
	    
	    System.out.println("updateDetails ----------> "+updateDetails);
	    
	    if(updateDetails > 0) {
	    	status = "Data Updated SuccessFully";
	    } else {
	    	status ="Failed to Update";
	    }
		
		return status;

	}

}
