package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.AuthenticationRequest;
import com.ecrops.dto.DeceasedFarmerDto;
import com.ecrops.entity.AadhaarUpdation;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.DeceasedFarmer;
import com.ecrops.entity.PattadarUpdate;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.AadhaarUpdationRepo;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.DeceasedFarmerRepo;
import com.ecrops.repo.VillageRevRepo;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.impl.DeceasedFarmerImpl;

@Controller
public class DeceasedFarmerSearchController {

	@Autowired
	ActiveSeasonService activeSeasonService;

	@Autowired
	DeceasedFarmerImpl impl;

	@Autowired
	AadhaarUpdationRepo repo;

	@Autowired
	private DatabaseRepo wbdcodeRepo;

	@Autowired
	DeceasedFarmerRepo rep;
	
	@Autowired
	private VillageRevRepo villageRevRepo;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/deceasedSearch")
	public String nom(@ModelAttribute AuthenticationRequest authenticationRequest, Model model,
			HttpServletRequest request, HttpSession httpSession) {
		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		
		Integer vscode=Integer.parseInt( (String) httpSession.getAttribute("vscode"));
		List<ActiveSeasonProjection> list = villageRevRepo.getVillageListByRbk(vscode);
//		String vscode = (String) httpSession.getAttribute("vscode");
//		System.out.println(vscode + "____" + cropYearActiveSeasonList.get(0).getSeason());
//		List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
		System.out.println(list.get(0).getWbvname());
		model.addAttribute("list", list);
		model.addAttribute("cropYear", cropYearActiveSeasonList);
		return "rbkroles/deceasedFarmerSearch";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/deceasedDetails")
	public String details(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {
		String dcode = (String) httpSession.getAttribute("dcode");
		String mcode = (String) httpSession.getAttribute("mcode");
		String userid = (String) httpSession.getAttribute("userid");
//		int vcode = (int) httpSession.getAttribute("wbvcode");
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
//		int cropYear = cropYearActiveSeasonList.get(0).getYear();
//		String season = cropYearActiveSeasonList.get(0).getSeason();
		
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String season = cropYearActiveSeasonList.get(0).getSeason();
		int vcode= Integer.parseInt(httpServletRequest.getParameter("villageNames").toString());
		String wbdcode = "", partitionName = "cr_details_";
		String crd_orgtab = "cr_details_org_details";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String crpses = httpServletRequest.getParameter("cropyear");
		System.out.println(crpses);
		String cropyear = crpses.split("@")[1];
		season = crpses.split("@")[0];
		RegularExpressionclassMethod expressionclassMethod=new RegularExpressionclassMethod();
		
		String activeYear = cropyear;
		System.out.println(activeYear);
//		String partitionName = "cr_booking_partition_";

		if (Integer.parseInt(wbdcode) <= 9) {
			partitionName = partitionName + season + "0" + wbdcode + cropYear;

		} else {
			partitionName = partitionName + season + wbdcode + cropYear;
		}
		// partitionName ="ecrop"+ cropYear+"."+partitionName;
		if (activeYear.equals(cropyear)) {
			partitionName = "ecrop" + cropYear + "." + partitionName;
			// crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
		} else {
			partitionName = partitionName;
		}
		String surveyNo = null;
		String khataNo = null;
		String aadhaarNo = null;
		System.out.println(httpServletRequest.getParameter("selectedValue"));
		System.out.println(httpServletRequest.getParameter("value"));
		System.out.println(
				partitionName + "=====" + crd_orgtab + "=====" + cropYear + "======" + season + "======" );
		String selectedValue = httpServletRequest.getParameter("selectedValue");
		String value = httpServletRequest.getParameter("value");
		Boolean valid= expressionclassMethod.checkstring( httpServletRequest.getParameter("value"));
		if (selectedValue != null) {
			if (selectedValue.equalsIgnoreCase("Survey No")) {
				surveyNo = httpServletRequest.getParameter("value");
				khataNo = "";
				aadhaarNo = "";
				System.out.println(surveyNo);
			} else if (selectedValue.equalsIgnoreCase("Khata No")) {
				khataNo = httpServletRequest.getParameter("value");
				surveyNo = "";
				aadhaarNo = "";
				System.out.println(khataNo);
			} else if (selectedValue.equalsIgnoreCase("Aadhaar No")) {
				aadhaarNo = httpServletRequest.getParameter("value");
				surveyNo = "";
				khataNo = "";
				System.out.println(aadhaarNo);
			}
		}
		try {
			if(valid) {
			List<DeceasedFarmer> list = rep.getDetails(partitionName, crd_orgtab, cropYear, season, vcode, surveyNo,
					khataNo, aadhaarNo);
			if(list.size()>0)
			model.addAttribute("list", list);
			}
			else
				model.addAttribute("empty", "No records Found");
		} catch (Exception e) {

			model.addAttribute("empty", "No records Found");

		}
		model.addAttribute("partitionName", partitionName);
		model.addAttribute("selectedValue", selectedValue);
		model.addAttribute("value", value);
		return "rbkroles/deceasedFarmerDetails";

	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/updateDeceased")
	public String updateDeceased(HttpSession httpSession, @RequestBody List<DeceasedFarmerDto> deceasedFarmerDtos) {
		System.out.println("=======" + deceasedFarmerDtos.get(0).getCr_no());
		String dcode = (String) httpSession.getAttribute("dcode");
		int vcode = (int) httpSession.getAttribute("wbvcode");
		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid________>" + userid);
		int mcode = (int) httpSession.getAttribute("wbmcode");
		System.out.println(mcode);
		String vname = (String) httpSession.getAttribute("wbevname");
		System.out.println(vname);
		String crbooking_orgtab = "cr_booking_org_details";
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		String vscode = (String) httpSession.getAttribute("vscode");
		List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
//		int cropYear = cropYearActiveSeasonList.get(0).getYear();
//		String season = cropYearActiveSeasonList.get(0).getSeason();
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = "";
		String crd_orgtab = "cr_details_org_details";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String cropyear = String.valueOf(cropYear);
		System.out.println(cropyear);
		String activeYear = cropyear;
		String tab1 = "cr_booking_partition_";
		String tab2 = "cr_details_", partkey = "",String ;
		System.out.println(activeYear);
		String selectedValue = deceasedFarmerDtos.get(0).getSelectedValue();
		String value = deceasedFarmerDtos.get(0).getValue();
//			String partitionName = "cr_booking_partition_";
		System.out.println(selectedValue + "============" + value);

		if (Integer.parseInt(wbdcode) <= 9) {
			partkey = season + "0" + wbdcode + cropYear;
			tab2 = tab2 + season + "0" + wbdcode + cropYear;
			tab1 = tab1 + season + "0" + wbdcode + cropYear;

		} else {
			partkey = season + wbdcode + cropYear;
			tab2 = tab2 + season + wbdcode + cropYear;
			tab1 = tab1 + season + wbdcode + cropYear;
		}
		// partitionName ="ecrop"+ cropYear+"."+partitionName;
		if (activeYear.equals(cropyear)) {
			tab2 = "ecrop" + cropYear + "." + tab2;
			tab1 = "ecrop" + activeYear + "." + tab1;
			// tab2 = "ecrop" + activeYear + "." + tab2;
			crbooking_orgtab = "ecrop" + activeYear + "." + crbooking_orgtab;
			crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
			// crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
		} else {
			tab2 = tab2;
		}
		String surveyNo = null;
		String khataNo = null;
		String aadhaarNo = null;
		if (selectedValue != null) {
			if (selectedValue.equalsIgnoreCase("Survey No")) {
				surveyNo = value;
				khataNo = "";
				aadhaarNo = "";
				System.out.println(surveyNo);
			} else if (selectedValue.equalsIgnoreCase("Khata No")) {
				khataNo = value;
				surveyNo = "";
				aadhaarNo = "";
				System.out.println(khataNo);
			} else if (selectedValue.equalsIgnoreCase("Aadhaar No")) {
				aadhaarNo = value;
				surveyNo = "";
				khataNo = "";
				System.out.println(aadhaarNo);
			}
		}

		System.out.println(deceasedFarmerDtos.get(0).getDateofDeath()+"----"+ deceasedFarmerDtos.get(0).getHeirfName());
//		System.out.println(tab2 + "=====" + crbooking_orgtab + "=====" + cropYear + "======" + season + "======" + vcode+"---------"+tab1);
		
		impl.updateDeceasedFarmer(deceasedFarmerDtos, partkey, season, wbdcode, cropYear, vcode, userid, mcode, vname,
				tab2, surveyNo, khataNo, aadhaarNo,crbooking_orgtab,tab1,crd_orgtab);

		return "rbkroles/deceasedFarmerSearch";
	}

}
