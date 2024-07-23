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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecrops.dto.VillageNormalAreasDTO;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.UserRegEntity;
import com.ecrops.entity.VillageUpdation;
import com.ecrops.entity.WbMaster;
import com.ecrops.repo.UpdationRepo;
import com.ecrops.repo.UpdationRepo.updateView;
import com.ecrops.repo.VillageUpdationRepo;
import com.ecrops.repo.VillageUpdationRepo.mandal_2011_cs;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.WbMasterService;
import com.ecrops.service.impl.UpdationServiceImpl;
import com.ecrops.service.impl.VillageUpdationServiceImpl;

@Controller
public class VillageNormalAreasUpdationController {

	@Autowired
	private VillageUpdationServiceImpl impl;

	@Autowired
	private ActiveSeasonService activeSeasonService;

	@Autowired
	private WbMasterService wbMasterService;

	@Autowired
	private UpdationRepo updationRepo;

	@Autowired
	private VillageUpdationRepo vrep;

	@Autowired
	private UpdationServiceImpl updationService;

	@PreAuthorize("hasAuthority('5') || hasAuthority('22')  ")
	@GetMapping("/VillageNormalAreasUpdation")
	public String VillageNormalAreasUpdation(Model model, HttpSession httpSession) {
		int type = Integer.parseInt( (String) httpSession.getAttribute("role"));
		System.out.println("role--------"+type);
		if (type == 5) {

			List<VillageUpdation> crop = impl.villageNormalAreasUpdation();
			
			List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
			model.addAttribute("year", cropYearActiveSeasonList.get(0).getCropyear());
			model.addAttribute("season", cropYearActiveSeasonList.get(0).getSeason());

			model.addAttribute("cropYear", cropYearActiveSeasonList.get(0).getSeasonvalue());
			model.addAttribute("cropNames", crop);
		}
		if (type == 22) {

			int dcode = Integer.parseInt((String) httpSession.getAttribute("dcode"));
			int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
			List<VillageUpdation> crop = impl.villageNormalAreasUpdationHO();
			List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
			List<mandal_2011_cs> mand = vrep.mandName(dcode,mcode);
			System.out.println("-------------"+mand.get(0).getMname());
			model.addAttribute("mand", mand);
			model.addAttribute("year", cropYearActiveSeasonList.get(0).getCropyear());
			model.addAttribute("season", cropYearActiveSeasonList.get(0).getSeason());
			model.addAttribute("cropYear", cropYearActiveSeasonList.get(0).getSeasonvalue());
			model.addAttribute("cropNames", crop);

		}
		return "maoroles/villageNormalAreasUpdation";
	}

	@PreAuthorize("hasAuthority('5') || hasAuthority('22') ")
	@PostMapping("/updation")
	public String updation(@RequestParam("selectedCrop") String selectedCrop, Model model, UserRegEntity userRegEntity,
			HttpSession httpSession, HttpServletRequest httpServletRequest) {
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		Integer wbmcode = (Integer) httpSession.getAttribute("wbmcode");
		Integer mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		Integer dcode = Integer.parseInt((String) httpSession.getAttribute("dcode"));
		System.out.println(mcode+"------------"+dcode);
//		int type = (int) httpSession.getAttribute("userType");
		int type = Integer.parseInt( (String) httpSession.getAttribute("role"));
		String selectedMand = "";
		WbMaster wbMaster = null;
		wbMaster = wbMasterService.getWbMasterDetailsForMandal(wbmcode, wbdcode);
		Integer year = (Integer) httpSession.getAttribute("ACTIVEYEAR");
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String season= cropYearActiveSeasonList.get(0).getSeason();
		System.out.println("season--=------->"+season);

		if (type == 22) {
			selectedMand = httpServletRequest.getParameter("selectedMand");
			String wbdname= (String) httpSession.getAttribute("wbedname");
			System.out.println("wbdname----------"+wbdname);
			try {
				String cropid= selectedCrop.split("-")[1];
				int cropNum= Integer.parseInt(cropid);
				System.out.println(cropNum);
				System.out.println(dcode);
				System.out.println(selectedMand);
				List<updateView> update = updationRepo.getDetailsHo(cropYearActiveSeasonList.get(0).getCropyear(),
						dcode, Integer.parseInt(selectedMand), cropYearActiveSeasonList.get(0).getSeason(), cropNum);
				
				if (update.size() == 0) {
					model.addAttribute("empty", "No Records Found");
				}
				if (update.size() > 0) {
					model.addAttribute("update", update);
					
//					model.addAttribute("wbmname", wbMaster.getWbemname());
				}
			} catch (Exception e) {
				model.addAttribute("empty", "No Records Found");

			}
			model.addAttribute("wbdname", wbdname);
			model.addAttribute("selectedMand", selectedMand);
		}
		System.out.println(cropYearActiveSeasonList.get(0).getCropyear()+"----------------"+cropYearActiveSeasonList.get(0).getSeason());
		if (type == 5) {
			httpSession.setAttribute("wbedname", wbMaster.getWbedname());
			httpSession.setAttribute("year", year);

			try {
				String cropid= selectedCrop.split("-")[1];
				int cropNum= Integer.parseInt(cropid);
				System.out.println(cropNum);
				System.out.println(dcode);
				System.out.println(mcode);
//				cropYearActiveSeasonList.get(0).getCropyear(),cropYearActiveSeasonList.get(0).getSeason(),
				List<updateView> update = updationRepo.getDetails( dcode,
						mcode,  cropNum,season);
				if (update.size() == 0) {
					model.addAttribute("empty", "No Records Found");
				}
				if (update.size() > 0) {
					model.addAttribute("update", update);
					model.addAttribute("wbdname", wbMaster.getWbedname());
					model.addAttribute("wbmname", wbMaster.getWbemname());
				}
			} catch (Exception e) {
				model.addAttribute("empty", "No Records Found");

			}
		}
//		System.out.println("selectedCrop------------>"+selectedCrop);
		String[] name= selectedCrop.split("-");
		System.out.println(selectedCrop+"-------selectedCrop------------>"+name[1]);

		model.addAttribute("year", cropYearActiveSeasonList.get(0).getCropyear());
		model.addAttribute("season", cropYearActiveSeasonList.get(0).getSeason());
		model.addAttribute("cropYear", cropYearActiveSeasonList.get(0).getCropyear());
		httpSession.setAttribute("wbemname", wbMaster.getWbemname());
		httpSession.setAttribute("name", userRegEntity.getName());
		model.addAttribute("selectedCrop", name[0]);
		model.addAttribute("cropid",name[1]);
		return "maoroles/updation";
	}

	@PreAuthorize("hasAuthority('5')|| hasAuthority('22')  ")
	@PostMapping("/updateVillageNormalArea")
	public String update(@RequestBody List<VillageNormalAreasDTO> villageNormalAreasDTOList,HttpSession httpSession,HttpServletRequest httpServletRequest) {
		System.out.println("in the method");
//		int type= (int) httpSession.getAttribute("userType");
		int type = Integer.parseInt( (String) httpSession.getAttribute("role"));
		String user = (String) httpSession.getAttribute("userid");
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String season= cropYearActiveSeasonList.get(0).getSeason();
		String cropid=villageNormalAreasDTOList.get(0).getCropid();
		System.out.println("selectedd crop--------------->"+cropid);
		Integer mcode= Integer.parseInt((String) httpSession.getAttribute("mcode")) ;
		Integer dcode= Integer.parseInt((String) httpSession.getAttribute("dcode"));
		String selectedMand="";
//		String cropid= selectedCrop.split("-")[1];
		int cropNum= Integer.parseInt(cropid);
		if(type==22) {
		 selectedMand= httpServletRequest.getParameter("selected");
		}
		
		updationService.updateNormalAreas(villageNormalAreasDTOList,mcode,dcode,cropNum,user,type,season);
		return "maoroles/villageNormalAreasUpdation";
	}

}
