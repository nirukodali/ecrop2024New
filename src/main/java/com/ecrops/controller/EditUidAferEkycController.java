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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecrops.dto.AuthenticationRequest;
import com.ecrops.dto.EditUidDto;
import com.ecrops.entity.AadhaarUpdation;
import com.ecrops.entity.DeceasedFarmer;
import com.ecrops.entity.EditUid;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.AadhaarUpdationRepo;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.EditUidRepo;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.impl.EditUidImpl;

@Controller
public class EditUidAferEkycController {

	@Autowired
	ActiveSeasonService activeSeasonService;
	
	@Autowired
	AadhaarUpdationRepo repo;

	@Autowired
	EditUidRepo rep;

	@Autowired
	private DatabaseRepo wbdcodeRepo;

	@Autowired
	EditUidImpl imp;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/edituid")
	public String nom(@ModelAttribute AuthenticationRequest authenticationRequest, Model model,
			HttpServletRequest request, HttpSession httpSession) {
		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println(vscode + "____" + cropYearActiveSeasonList.get(0).getSeason());
		List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
		System.out.println(list.get(0).getWbvname());
		model.addAttribute("list", list);
		model.addAttribute("cropYear", cropYearActiveSeasonList);
		return "rbkroles/editUid";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/editinguidafterekyc")
	public String details(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {
		System.out.println("-------------------------------->>>>>>>>>>>>>>>>Surya");
		String dcode = (String) httpSession.getAttribute("dcode");
		String mcode = (String) httpSession.getAttribute("mcode");
		String userid = (String) httpSession.getAttribute("userid");
//		int vcode = (int) httpSession.getAttribute("wbvcode");
		int vcode= Integer.parseInt(httpServletRequest.getParameter("villageNames"));
		System.out.println("vcode--------------------------->"+vcode);
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
//		int cropYear =  cropYearActiveSeasonList.get(0).getYear();
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
			List<EditUid> list = rep.getDetails(partitionName, crd_orgtab, Integer.parseInt(cropyear), season, vcode, surveyNo, khataNo,
					aadhaarNo);
			System.out.println(list.size());
			System.out.println(list.get(0).getCr_no());
			model.addAttribute("list", list);
		} catch (Exception e) {

			model.addAttribute("empty", "No Records Found");

		}
		model.addAttribute("cropyear",crpses);
		model.addAttribute("vcode",vcode);
		model.addAttribute("partitionName", partitionName);
		model.addAttribute("selectedValue", selectedValue);
		model.addAttribute("value", value);
		return "rbkroles/editUidDetails";

	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/updatingaadhaar")
	public String update(HttpServletRequest httpServletRequest, @RequestBody List<EditUidDto> dto,
			HttpSession httpSession) {
		System.out.println("====");
		String dcode = (String) httpSession.getAttribute("dcode");
//		int vcode = (int) httpSession.getAttribute("wbvcode");
//		System.out.println(httpServletRequest.getParameter("selectedValue"));
//		System.out.println(httpServletRequest.getParameter("vcode2"));
//		int vcode= Integer.parseInt(httpServletRequest.getParameter("vcode2").toString());
		String vcode=dto.get(0).getVcode();
		
		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid________>" + userid);
		int mcode = (int) httpSession.getAttribute("wbmcode");
		System.out.println(mcode);
		String vname = (String) httpSession.getAttribute("wbevname");
		System.out.println(vname);
		System.out.println("=-----------"+httpServletRequest.getParameter("cropyear"));
		String crbooking_orgtab = "cr_booking_org_details";
		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
		System.out.println(cropYearActiveSeasonList.get(0).getCropyear());
		System.out.println(cropYearActiveSeasonList.get(0).getSeasonvalue());
//		String vscode = (String) httpSession.getAttribute("vscode");
//		List<AadhaarUpdation> list = repo.getVillage(Integer.parseInt(vscode));
//		int cropYear = cropYearActiveSeasonList.get(0).getYear();
//		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = "";
		String crpses = cropYearActiveSeasonList.get(0).getSeasonvalue();
		System.out.println(crpses);
		String cropYear = crpses.split("@")[1];
		String	season = crpses.split("@")[0];
		String crd_orgtab = "cr_details_org_details";
		wbdcode = wbdcodeRepo.getWbdCode(dcode);
		System.out.println(cropYear);
		String activeYear = cropYear;
		String tab1 = "cr_booking_partition_";
		String tab2 = "cr_details_", partkey = "";
		System.out.println(activeYear);
		String selectedValue = dto.get(0).getSelectedValue();
		String value = dto.get(0).getValue();
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
		if (activeYear.equals(cropYear)) {
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

		imp.edit(dto, tab2, selectedValue, value, surveyNo, khataNo, aadhaarNo,Integer.parseInt(cropYear), season,Integer.parseInt(vcode));

		return "rbkroles/editUid";
	}
}
