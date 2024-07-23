package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecrops.entity.crop.Cr_detailsPojo;
import com.ecrops.repo.crop.CheckSupervisoryIntfSrvice;
import com.ecrops.repo.crop.SuperCheckAdaRepository;

@Controller
public class CheckSupervisoryContoller {
	
	@Autowired
	private SuperCheckAdaRepository checkRecordsIntf;

	@Autowired
	private CheckSupervisoryIntfSrvice checkService;
	
	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('21') || hasAuthority('20')")
	@GetMapping("/checkSupervisoryIntf")
	public String getcheckSupervisoryIntfEntry(Model model, HttpSession httpSession) {
		
        return "redirect:/viewAfterFormSubmissionCheckSupervisory";	
	}

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('21') || hasAuthority('20')")
	@GetMapping("/viewAfterFormSubmissionCheckSupervisory")
	public String viewAfterFormSubmission(HttpSession httpSession, Model model) {
	    try {
	        String role = (String) httpSession.getAttribute("role");
	        int seswbdcode = (int) httpSession.getAttribute("wbdcode");
	        String sesmcode = (String) httpSession.getAttribute("sesmcode");
	        int seswbmcode = (int) httpSession.getAttribute("wbmcode");
	       
	        String user = (String) httpSession.getAttribute("userid");
	        
	        
	        int wbmcode = 0;
			int wbdcode=0;
			if (role.equals("44") || role.equals("45") || role.equals("9") || role.equals("19") || role.equals("21")
					|| role.equals("31") || role.equals("20")) {
					wbmcode = checkRecordsIntf.getwbmcode(Integer.parseInt(sesmcode));
					 wbdcode= checkRecordsIntf.getwbdcode(Integer.parseInt(sesmcode));
					System.out.println("wbmcode--->" + wbmcode);
					System.out.println("wbdcode--->" + wbdcode);

				}
			else {
				wbmcode = seswbmcode;
				wbdcode=seswbdcode;
			}

//			String cropyear = (String) httpSession.getAttribute("cropYear");
//			String season = (String) httpSession.getAttribute("season");
//	        
	       
	        int cropYear = 2024;
	        String season = "k";
	        
	        
	        
	        List<Cr_detailsPojo> resultList = checkService.checkAndViewCrDtailsPartionData(cropYear, String.valueOf(wbdcode), wbmcode,
	                user, season, role);
	        model.addAttribute("resultList", resultList);
	        model.addAttribute("cropYear", cropYear);
	        model.addAttribute("season", season);

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("An unexpected error occurred");
	    }
	    return "maoroles/check_supervisoryIntf";
	}

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('21') || hasAuthority('20')")
	@PostMapping("/viewAfterFormSubmissionCheckSupervisory")
	public String submitForm(HttpSession httpSession, Model model) {
		
	    return "redirect:/viewAfterFormSubmissionCheckSupervisory";
	}
}
