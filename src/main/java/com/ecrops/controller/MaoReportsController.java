package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.ecrops.dto.SearchDetailsPojo;
import com.ecrops.dto.UnsurveyedDetailsPojo;
//import com.ecrops.dto.UnsurvySetlePojo;
import com.ecrops.dto.UnsurvyedVillagePojo;
import com.ecrops.entity.ActiveSeason;
//import com.ecrops.repo.CrCropDetNewMv;
import com.ecrops.repo.MaoReportsRepo;



@Controller
public class MaoReportsController {
	
	@Autowired
	MaoReportsRepo maoReportsRepo;
	

	// 2nd report like Rep_UnsurveyedViewIntf
	@PreAuthorize("hasAuthority('2')")
	@GetMapping("/unServeyedReports")
	public String getUnSurveyedUnsettledReport(Model model ,HttpSession httpSession ){
		System.out.println("dcode------------------"+httpSession.getAttribute("wbdcode"));
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		List<UnsurveyedDetailsPojo> l =maoReportsRepo.getUnsurveyDetails(wbdcode);
		model.addAttribute("data",l);
		 return "mro/unServeyedReport";
	}
	

	//3 rd report
	@PreAuthorize("hasAuthority('2')")
	@GetMapping("/unsrvyedUnsettldReportVillageWise")
	public String getunsrvyedUnsettldReportVillageWise(HttpSession httpSession,Model model  ){
		System.out.println("dcode------------------"+httpSession.getAttribute("wbdcode"));
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		List<UnsurvyedVillagePojo> l =maoReportsRepo.getUnsurveyDetailsVilageWise(wbdcode);
		model.addAttribute("data",l);
		 
	return "mro/unsurveyedUnSettledReportsVillageWise";
	}
	
	
	
	
	

}
