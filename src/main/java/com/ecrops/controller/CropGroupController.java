package com.ecrops.controller;

import java.io.UnsupportedEncodingException; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecrops.dto.CrAddGrpForm;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.ApplicationModel;
import com.ecrops.entity.CrAddGroupNameEntity;
import com.ecrops.entity.SuperCheckRecordsAlloted;
import com.ecrops.entity.SuperChkReportEntity;
import com.ecrops.model.RequestModel;
import com.ecrops.projection.VroRejectDropdownProjection;
import com.ecrops.repo.SuperCheckRecordsAllotedRepo;
import com.ecrops.repo.SuperChkReportRepo;
import com.ecrops.repo.UniversalProj;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.ApplicationService;
import com.ecrops.service.CrAddGroupNameService;

@Controller
public class CropGroupController {

	@Autowired
	private CrAddGroupNameService crAddGroupNameService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private SuperChkReportRepo superChkReportPartition;
	@Autowired
	private com.ecrops.partition.SuperCheckRecordsAllotedPartition superCheckRecordsAllotedPartition;
	
	@Autowired
	private ActiveSeasonService activeSeasonService;
	
	@PreAuthorize("hasAuthority('17')")

	@GetMapping("/CrAddGroupName")
	public String showAddCropGroupForm(Model model) {
		model.addAttribute("CrAddGroupName", new CrAddGrpForm());
		System.out.println("**************groupname");
		return "ddap/CrAddGroupName";
	}
	@PreAuthorize("hasAuthority('17')")

	@PostMapping("saveCrpgrp")
	public String saveCrpgrp(@Valid @ModelAttribute("CrAddGroupName") CrAddGrpForm crAddGroupNameEntity,
			BindingResult bindingResult, Model model, HttpSession session) throws UnsupportedEncodingException {
		System.out.println("CrAddGroupName=>" + crAddGroupNameEntity.toString());

		if (bindingResult.hasErrors()) {
			return "ddap/crAddGroupName";
		}

		String user = session.getAttribute("name").toString();

		String encodedGrpname = new String(crAddGroupNameEntity.getGrpname().getBytes("8859_1"), "UTF-8");

		UniversalProj count = crAddGroupNameService.getMaxcrpgid();

		System.out.println("bean=======>" + crAddGroupNameEntity.getGrpname());
		Optional<CrAddGroupNameEntity> bean = crAddGroupNameService.getByCrpGrpName(crAddGroupNameEntity.getGrpname());
//		System.out.println("bean ----> "+bean.toString());

		if (!bean.isPresent()) {
			System.out.println("bea == nill");
			String maxCrpidAsString = String.valueOf(count.getMaxcrpid());
			System.out.println("maxCrpidAsString  " + maxCrpidAsString);

			int maxCrpid = Integer.parseInt(maxCrpidAsString);

			CrAddGroupNameEntity crAddGroupNameEntityy = new CrAddGroupNameEntity();

			crAddGroupNameEntityy.setCropgrpid(maxCrpid + 1);
			crAddGroupNameEntityy.setGrpname(encodedGrpname);
			crAddGroupNameEntityy.setCrt_user(user);

			CrAddGroupNameEntity savedBean = crAddGroupNameService.saveCrAddGroupNameEntity(crAddGroupNameEntityy);

			model.addAttribute("CrAddGroupName", new CrAddGroupNameEntity());
			model.addAttribute("msg", "<span style= 'color:green; font-size : 25px'>Group Name Added Successfully</span>");

			return "ddap/CrAddGroupName";
			
		} else {
			model.addAttribute("msg", "<span style= 'color:red; font-size : 25px'>Group Name is Already Exist</span>");
			return "ddap/CrAddGroupName";
		}

	}

	// *********************************superChkReport*******************************************//
	@PreAuthorize("hasAuthority('46')")
	@GetMapping("/supchk")
	public String getSupChR(Model model) {
		System.out.println("supchk");

		List<ActiveSeason> activeSeason = activeSeasonService.listAll();
		System.out.println("activeseasonslist=>" + activeSeason.size());
		int cropYear = activeSeason.get(0).getCropyear();
		String season = activeSeason.get(0).getSeason();
		
		System.out.println("cropYear----->>>>"+cropYear);
		System.out.println("season----->>>>"+season);
		
//		List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
	//	System.out.println("activeseasonslist=>" + activeSeason.size());

		Map<String, String> map = new HashMap<String, String>();
		String seasonname = "";
		for (ActiveSeason bean : activeSeason) {

			if (bean.getSeason().equalsIgnoreCase("K")) {
				seasonname = "Kharif " + bean.getCropyear();
			}
			if (bean.getSeason().equalsIgnoreCase("R")) {
				seasonname = "Rabi " + bean.getCropyear();
			}
			if (bean.getSeason().equalsIgnoreCase("S")) {
				seasonname = "Summer " + bean.getCropyear();
			}

			map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
		}

		model.addAttribute("activeSeasons", map);
		model.addAttribute("applicationModel", new ApplicationModel());

		return "rdo/SuperChkReport";

	}
	
	@PreAuthorize("hasAuthority('46')")
	@PostMapping("/getSuperCheckReport")
	public String getSuperCheckReport(Model model,
			@ModelAttribute("applicationModel") ApplicationModel applicationModel, HttpSession httpSession) {
		System.out.println("getSuperCheckReport=>applicationModel=>" + applicationModel.toString());
		
		List<ActiveSeason> activeSeason = activeSeasonService.listAll();

		//List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
		System.out.println("activeseasonslist=>" + activeSeason.size());

		Map<String, String> map = new HashMap<String, String>();
		String seasonname = "";
		for (ActiveSeason bean : activeSeason) {

			if (bean.getSeason().equalsIgnoreCase("K")) {
				seasonname = "Kharif " + bean.getCropyear();
			}
			if (bean.getSeason().equalsIgnoreCase("R")) {
				seasonname = "Rabi " + bean.getCropyear();
			}
			if (bean.getSeason().equalsIgnoreCase("S")) {
				seasonname = "Summer " + bean.getCropyear();
			}

			map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
		}

		model.addAttribute("activeSeasons", map);

		try {
			String wbdcode = (String) httpSession.getAttribute("dcode");
			String wbmcode = (String) httpSession.getAttribute("mcode");
			
			//String wbdcode = httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode=>" + wbdcode);
		//	String wbmcode = httpSession.getAttribute("wbmcode").toString();
			System.out.println("wbmcode=>" + wbmcode);
			String userid = httpSession.getAttribute("userid").toString();
			System.out.println("userid=>" + userid);

			List<SuperChkReportEntity> supkr = superChkReportPartition.getSupchkRep(wbdcode, wbmcode, userid,
					applicationModel.getCropyear());
			System.out.println("details===================>" + supkr.size());

			if (supkr.size() == 0) {
				model.addAttribute("msg", "No data found with selected crop");
			}
			model.addAttribute("supkr", supkr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "No data found with selected crop");
			return "SuperChkReport";
		}

		return "rdo/SuperChkReport";
	}
	
//	//****************************************************   Rep_SupervisoryVerify  **********************************************
//	@PreAuthorize("hasAuthority('17')")
//
//	
//	@GetMapping("/supervisoryVerify")
//	public String supervisoryVerify(Model model) {
//		
//		List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
//		System.out.println("activeseasonslist=>" + activeSeason.size());
//
//		Map<String, String> map = new HashMap<String, String>();
//		String seasonname = "";
//		for (VroRejectDropdownProjection bean : activeSeason) {
//
//			if (bean.getSeason().equalsIgnoreCase("K")) {
//				seasonname = "Kharif " + bean.getCropyear();
//			}
//			if (bean.getSeason().equalsIgnoreCase("R")) {
//				seasonname = "Rabi " + bean.getCropyear();
//			}
//			if (bean.getSeason().equalsIgnoreCase("S")) {
//				seasonname = "Summer " + bean.getCropyear();
//			}
//
//			map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
//		}
//
//		model.addAttribute("activeSeasons", map);
//		
//		model.addAttribute("requestModel", new RequestModel());
//		
//		return "ddap/SupervisoryVerify";
//	}
//	@PreAuthorize("hasAuthority('17')")
//
//	@PostMapping("getSupervisorVerifyReport")
//	public String getSupervisorVerifyReport(@ModelAttribute("requestModel") RequestModel requestModel, Model model,HttpSession session) {
//		System.out.println("requestModel=>"+requestModel.toString());
//		
//		List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
//		System.out.println("activeseasonslist=>" + activeSeason.size());
//
//		Map<String, String> map = new HashMap<String, String>();
//		String seasonname = "";
//		for (VroRejectDropdownProjection bean : activeSeason) {
//
//			if (bean.getSeason().equalsIgnoreCase("K")) {
//				seasonname = "Kharif " + bean.getCropyear();
//			}
//			if (bean.getSeason().equalsIgnoreCase("R")) {
//				seasonname = "Rabi " + bean.getCropyear();
//			}
//			if (bean.getSeason().equalsIgnoreCase("S")) {
//				seasonname = "Summer " + bean.getCropyear();
//			}
//
//			map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
//		}
//
//		model.addAttribute("activeSeasons", map);
//		
//		model.addAttribute("requestModel", new RequestModel());
//		
//		try {
//			String wbdcode = session.getAttribute("wbdcode").toString();
//			String wbmcode = session.getAttribute("wbmcode").toString();
//			String userid = session.getAttribute("userid").toString();
//			
//			List<SuperCheckRecordsAlloted> spckr = superCheckRecordsAllotedPartition.getSupchkRds(wbdcode, wbmcode, userid, requestModel.getCropyear());
//			System.out.println("details===================>" + spckr.size());
//			
//			if (spckr.size() == 0) {
//				model.addAttribute("msg", "No data found with selected crop");
//			}
//			
//			model.addAttribute("supkChkVrfy", spckr);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			model.addAttribute("msg", "No data found with selected crop");
//			return "SuperChkReport";
//		}
//		
//		return "ddap/SupervisoryVerify";
//	}
//	
//	
//	
//	
//	//****************************************************   Rep_SupervisoryPending  **********************************************
//	
		@PreAuthorize("hasAuthority('46')")
		@GetMapping("/supervisoryPending")
		public String supervisoryPending(Model model) {
			
//			List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
//			System.out.println("activeseasonslist=>" + activeSeason.size());
		
		List<ActiveSeason> activeSeason = activeSeasonService.listAll();

			Map<String, String> map = new HashMap<String, String>();
			String seasonname = "";
			for (ActiveSeason bean : activeSeason) {

				if (bean.getSeason().equalsIgnoreCase("K")) {
					seasonname = "Kharif " + bean.getCropyear();
				}
				if (bean.getSeason().equalsIgnoreCase("R")) {
					seasonname = "Rabi " + bean.getCropyear();
				}
				if (bean.getSeason().equalsIgnoreCase("S")) {
					seasonname = "Summer " + bean.getCropyear();
				}

				map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
			}	

			model.addAttribute("activeSeasons", map);
			
			model.addAttribute("requestModel", new RequestModel());
			
			return "rdo/SupervisoryPending";
		}
	
	@PreAuthorize("hasAuthority('46')")
		@PostMapping("getSupervisorPendingReport")
		public String getSupervisorPendingReport(@ModelAttribute("requestModel") RequestModel requestModel, Model model,HttpSession session) {
			System.out.println("requestModel=>"+requestModel.toString());
			
//			List<VroRejectDropdownProjection> activeSeason = applicationService.getActiveSeason();
//			System.out.println("activeseasonslist=>" + activeSeason.size());
			List<ActiveSeason> activeSeason = activeSeasonService.listAll();

			Map<String, String> map = new HashMap<String, String>();
			String seasonname = "";
			for (ActiveSeason bean : activeSeason) {

				if (bean.getSeason().equalsIgnoreCase("K")) {
					seasonname = "Kharif " + bean.getCropyear();
				}
				if (bean.getSeason().equalsIgnoreCase("R")) {
					seasonname = "Rabi " + bean.getCropyear();
				}
				if (bean.getSeason().equalsIgnoreCase("S")) {
					seasonname = "Summer " + bean.getCropyear();
				}

				map.put(bean.getSeason() + "@" + bean.getCropyear(), seasonname);
			}

			model.addAttribute("activeSeasons", map);
			
			model.addAttribute("requestModel", new RequestModel());
			
			try {
				String wbdcode = (String) session.getAttribute("dcode");
				String wbmcode = (String) session.getAttribute("mcode");
				
//				String wbdcode = session.getAttribute("wbdcode").toString();
//				String wbmcode = session.getAttribute("wbmcode").toString();
				String userid = session.getAttribute("userid").toString();
				
				List<SuperCheckRecordsAlloted> spckPndg = superCheckRecordsAllotedPartition.getSupchkRds(wbdcode, wbmcode, userid, requestModel.getCropyear());
				System.out.println("details===================>" + spckPndg.size());
				
				if (spckPndg.size() == 0) {
					model.addAttribute("msg", "No data found with selected crop");
				}
				
				model.addAttribute("supckPndg", spckPndg);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "No data found with selected crop");
				return "ddap/SuperChkReport";
			}
			
			return "rdo/SupervisoryPending";
		}

}
