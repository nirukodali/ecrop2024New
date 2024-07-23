package com.ecrops.controller;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.AuthenticationRequest;
import com.ecrops.dto.PattadarAadhaarDto;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.PattadarUpdate;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.AadhaarUpdationRepo;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.PattadarAadhaarRepo;
import com.ecrops.repo.VillageRevRepo;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.UserRegService;
import com.ecrops.service.impl.PattadarAadhaarImpl;

@Validated
@Controller
public class AadhaarUpdationController {

	@Autowired
	private ActiveSeasonService activeSeasonService;

	@Autowired
	PattadarAadhaarImpl imp;

	@Autowired
	UserRegService userRegService;

	@Autowired
	AadhaarUpdationRepo repo;
	
	@Autowired
	private VillageRevRepo villageRevRepo;

	@Autowired
	PattadarAadhaarRepo rep;

	@Autowired
	private DatabaseRepo wbdcodeRepo;

	String partitionName = "";

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/aadhaarSearch")
	public String aadhaarSearch(@ModelAttribute AuthenticationRequest authenticationRequest, Model model,
			HttpServletRequest request, HttpSession httpSession) {
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
//	List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
//		String vscode = (String) httpSession.getAttribute("vscode");
//		
		Integer vscode=Integer.parseInt( (String) httpSession.getAttribute("vscode"));
		List<ActiveSeasonProjection> list = villageRevRepo.getVillageListByRbk(vscode);
		
	//	List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
		
		
		model.addAttribute("list", list);
		model.addAttribute("cropYear", cropYearActiveSeasonList);

		return "rbkroles/aadhaarSearch";
	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/aadhaarupdation")
	public String aadhaarUpdation(HttpSession httpSession, HttpServletRequest httpServletRequest, Model model) {
		String dcode = (String) httpSession.getAttribute("dcode");
//		int vcode = (int) httpSession.getAttribute("wbvcode");
		int vcode= Integer.parseInt(httpServletRequest.getParameter("villageNames"));
		System.out.println("vcode--------------------------->"+vcode);
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = "", partitionName = "cr_booking_partition_";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String crpses = httpServletRequest.getParameter("cropyear");
		String cropyear = crpses.split("@")[1];
		season = crpses.split("@")[0];

		String activeYear = cropyear;

		if (Integer.parseInt(wbdcode) <= 9) {
			partitionName = partitionName + season + "0" + wbdcode + cropYear;

		} else {
			partitionName = partitionName + season + wbdcode + cropYear;
		}
		if (activeYear.equals(cropyear)) {
			partitionName = "ecrop" + cropYear + "." + partitionName;
		}
		String surveyNo = null;
		String khataNo = null;
		String selectedValue = httpServletRequest.getParameter("selectedValue");

		if (selectedValue != null) {
			if (selectedValue.equalsIgnoreCase("Survey No")) {
				surveyNo = httpServletRequest.getParameter("value");
				khataNo = "";
				
			} else if (selectedValue.equalsIgnoreCase("Khata No")) {
				khataNo = httpServletRequest.getParameter("value");
				surveyNo = "";
				
			}
		}
//		System.out.println("partiiiii"+partitionName); ecrop2024.cr_booking_partition_K202023
		try {
//			List<PattadarUpdate> list = rep.getDetails(partitionName, cropYear, season, vcode, surveyNo, khataNo);
			List<PattadarUpdate> list = rep.getDetails( partitionName,cropYear, season, vcode, surveyNo, khataNo);
			if(list.size() >0) {
			model.addAttribute("list", list);
//			model.addAttribute("aadhaarUpdationValidation", new PattadarAadhaarDto());
			}
			else
				model.addAttribute("empty", "No records Found");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		model.addAttribute("partitionName", partitionName);
		model.addAttribute("vcode",vcode);
		return "rbkroles/aadhaarupdation";
	}
	
//@RequestParam(name = "data") String jsonData,
	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/updated")
	public String update( 
			@RequestBody List<PattadarAadhaarDto> aadhaarDtos, 		
			HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
		System.out.println("update--------------------------------------------->"+aadhaarDtos.get(0).getAadhaar());
		String dcode = (String) httpSession.getAttribute("dcode");
		RegularExpressionclassMethod regularExpressionclassMethod = new RegularExpressionclassMethod();
		Boolean valid= regularExpressionclassMethod.validateVerhoeff(aadhaarDtos.get(0).getAadhaar());
		//		String[] formDataArray  =  httpServletRequest.getParameter("formDataArray").split(",");
//		System.out.println("formDataArray=>"+formDataArray.length);
//		System.out.println("formData=>"+formDataArray[0]);
		
//		int vcode= Integer.parseInt(httpServletRequest.getParameter("cr_vcode"));
//		System.out.println("vcode--------------------------->"+vcode);
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();.
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		
		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = "", partitionName = "cr_booking_partition_";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String cropyear = String.valueOf(cropYear);
		String activeYear = cropyear;

		if (Integer.parseInt(wbdcode) <= 9) {
			partitionName = partitionName + season + "0" + wbdcode + cropYear;

		} else {
			partitionName = partitionName + season + wbdcode + cropYear;
		}
		if (activeYear.equals(cropyear)) {
			partitionName = "ecrop" + cropYear + "." + partitionName;
		} 
System.out.println("partitionName------->"+partitionName);
if(valid) {
		imp.update(aadhaarDtos, partitionName);
}
		return "rbkroles/aadhaarSearch";
	}

}
//