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

import com.ecrops.dto.AuthenticationRequest;
import com.ecrops.dto.LockDto;
import com.ecrops.entity.AadhaarUpdation;
import com.ecrops.entity.LockDropdown;
import com.ecrops.entity.LockEntity;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.AadhaarUpdationRepo;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.HoUnlockRepo;
import com.ecrops.repo.LockRepo;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.impl.LockImpl;
import org.springframework.http.MediaType;

@Controller
public class UnlockController {

	@Autowired
	ActiveSeasonService activeSeasonService;

	@Autowired
	AadhaarUpdationRepo repo;

	@Autowired
	private DatabaseRepo wbdcodeRepo;

	@Autowired
	private LockRepo rep;
	
	@Autowired
	HoUnlockRepo repo2;


	@Autowired
	LockImpl impl;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/unlockSearch")
	public String nom(@ModelAttribute AuthenticationRequest authenticationRequest, Model model,
			HttpServletRequest request, HttpSession httpSession) {
		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println(vscode + "____" + cropYearActiveSeasonList.get(0).getSeason());
		List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
		System.out.println(list.get(0).getWbvname());
		model.addAttribute("list", list);
		model.addAttribute("cropYear", cropYearActiveSeasonList);
		return "rbkroles/unlockSearch";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/lockUnlock")
	public String display(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {
		System.out.println("-------");
		String dcode = (String) httpSession.getAttribute("dcode");
		String mcode = (String) httpSession.getAttribute("mcode");
		String userid = (String) httpSession.getAttribute("userid");
//		int vcode = (int) httpSession.getAttribute("wbvcode");
		int vcode= Integer.parseInt(httpServletRequest.getParameter("villageNames"));
		System.out.println("vcode--------------------------->"+vcode);
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
//		int cropYear = cropYearActiveSeasonList.get(0).getYear();
//		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = "", partitionName = "cr_details_";
		String crd_orgtab = "cr_details_org_details";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		System.out.println("vcode is" + vcode);
		String crpses = httpServletRequest.getParameter("cropyear");
		System.out.println(crpses);
		String cropyear = crpses.split("@")[1];
		String	season = crpses.split("@")[0];

		String activeYear = cropyear;
		System.out.println(activeYear);
//		String partitionName = "cr_booking_partition_";

		if (Integer.parseInt(wbdcode) <= 9) {
			partitionName = partitionName + season + "0" + wbdcode + cropyear;

		} else {
			partitionName = partitionName + season + wbdcode + cropyear;
		}
		// partitionName ="ecrop"+ cropYear+"."+partitionName;
		if (activeYear.equals(cropyear)) {
			partitionName = "ecrop" + cropyear + "." + partitionName;
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
				partitionName + "=====" + crd_orgtab + "=====" + cropyear + "======" + season + "======" + vcode);
		String selectedValue = httpServletRequest.getParameter("selectedValue");
		String value = httpServletRequest.getParameter("value");
		String lock = httpServletRequest.getParameter("lock");
		String unlock = httpServletRequest.getParameter("unlock");
		System.out.println(lock + "====" + unlock);
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
		List<LockDropdown> drop = null;
		if (lock.equals("lock")) {
			try {
			List<LockEntity> list = rep.details(partitionName, Integer.parseInt(cropyear), season, userid, surveyNo, khataNo, aadhaarNo);
			System.out.println(list.size() + "=====" + list.get(0).getUnlockedext());
		 drop = repo2.dropdown();

			model.addAttribute("list", list);
			}
			catch (Exception e) {

				model.addAttribute("empty", "No records Found");

			}
			model.addAttribute("drop", drop);
			model.addAttribute("lock", lock);
		}
		if (lock.equals("unlock")) {
			try {
			List<LockEntity> list = rep.detailsUnlock(partitionName,Integer.parseInt(cropyear) , season, userid, surveyNo, khataNo,
					aadhaarNo);
			System.out.println(list.size() + "=====" + list.get(0).getCultdesc_loclang());
			model.addAttribute("list", list);
			}
			catch (Exception e) {
				System.out.println("exception");

				model.addAttribute("empty", "No Records Found");

			}
			model.addAttribute("lock", lock);
		}
		return "rbkroles/lockUnlock";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping(value= "/lockentryext")
	
	public String lock(@RequestBody List<LockDto> dto, HttpServletRequest httpServletRequest, HttpSession httpSession) {
		System.out.println(dto.get(0).getOccupname() + "====" + dto.get(0).getBookingid()+"---------"+dto.get(0).getReason());
		String dcode = (String) httpSession.getAttribute("dcode");
//		String userid = (String) httpSession.getAttribute("userid");
//		System.out.println("userid________>" + userid);
//		int mcode = (int) httpSession.getAttribute("wbmcode");
//		System.out.println(mcode);
//		String vname = (String) httpSession.getAttribute("wbevname");
//		System.out.println(vname);
		// String crbooking_orgtab = "cr_booking_org_details";
		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		System.out.println(httpServletRequest.getParameter("cropyear"));
		// String vscode = (String) httpSession1.getAttribute("vscode");
		// List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
		String crpses = cropYearActiveSeasonList.get(0).getSeasonvalue();
		System.out.println(crpses);
		String cropYear = crpses.split("@")[1];
		String	season = crpses.split("@")[0];
		String wbdcode = "";
//		String crd_orgtab = "cr_details_org_details";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String cropyear = String.valueOf(cropYear);
//		System.out.println(cropyear);
		String activeYear = cropyear;
		String tab1 = "cr_booking_partition_";
		String tab2 = "cr_details_";
		// String partkey = "";
		System.out.println(activeYear);
		
//		String lock =  httpServletRequest.getParameter("lock");
//		System.out.println("----->>"+ httpServletRequest.getParameter("lock"));
//			String partitionName = "cr_booking_partition_";
		System.out.println(dto.get(0).getReason());
		System.out.println(dto.get(0).getExtlock());
		System.out.println(dto.get(0).getLock());
		if (Integer.parseInt(wbdcode) <= 9) {
//			partkey = season + "0" + wbdcode + cropYear;
			tab2 = tab2 + season + "0" + wbdcode + cropYear;
			tab1 = tab1 + season + "0" + wbdcode + cropYear;

		} else {
//			partkey = season + wbdcode + cropYear;
			tab2 = tab2 + season + wbdcode + cropYear;
			tab1 = tab1 + season + wbdcode + cropYear;
		}
		// partitionName ="ecrop"+ cropYear+"."+partitionName;
		if (activeYear.equals(cropyear)) {
			tab2 = "ecrop" + cropYear + "." + tab2;
			tab1 = "ecrop" + activeYear + "." + tab1;
			// tab2 = "ecrop" + activeYear + "." + tab2;
//			crbooking_orgtab = "ecrop" + activeYear + "." + crbooking_orgtab;
//			crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
			// crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
		} else {
			tab2 = tab2;
		}
		impl.update(dto, tab2, dto.get(0).getLock());
		return "rbkroles/unlockSearch";
	}

}
