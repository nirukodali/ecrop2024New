package com.ecrops.controller;

import com.ecrops.dto.crop.request.WeblandPullRequest;

import com.ecrops.dto.crop.response.CropYear;
//import com.ecrops.dto.crop.response.VillageData;
import com.ecrops.dto.crop.response.WeblandPullResponse;
import com.ecrops.repo.crop.WeblandCropRepository;
import com.ecrops.repo.crop.WeblandCropServiceRepo;
//import com.ecrops.repo.crop.WeblandVillageRepository;
import com.ecrops.repo.crop.WeblandNewCropService;
import com.ecrops.service.impl.WbMasterServiceCcrc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WeblandCropNewController {

	@Autowired
	private WeblandCropServiceRepo serviceRepo;

	@Autowired
	private WeblandCropRepository cropRepository;

	

	@Autowired
	private	WeblandNewCropService weblandNewCropService;
	// Handle requests to the weblandCropEntry page
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/weblandCropEntry")
	public String webland(Model model, HttpSession httpSession) {
		String userId = (String) httpSession.getAttribute("userid");
		List<CropYear> activeSeasons = cropRepository.getCropYear();
		System.out.println();
		
		model.addAttribute("activeSeasons", activeSeasons);
		model.addAttribute("weblandPullRequest", new WeblandPullRequest());
		return "maoroles/WeblandCropEntry";
	}

	// Handle the form submission for fetching webland details
	@PreAuthorize("hasAuthority('5')")
	@PostMapping("/fetch-crop-data")
	public String fetchWeblandDetails(@RequestParam("cropyear")String cropyear,@RequestParam("village")String village, Model model, HttpSession session,
			HttpServletRequest request) {

		try {

			// Extract user information from the session
			String userId = (String) session.getAttribute("userid");
			int wbdcode = (int) session.getAttribute("wbdcode");
			int wbmcode = (int) session.getAttribute("wbmcode");
			String sesdcode = (String) session.getAttribute("dcode");
			String sesmcode = (String) session.getAttribute("mcode");

			// Extract crop year information from the form
			String[] seasonandcropyear=cropyear.split("@");
			String season = seasonandcropyear[0];
			int cropYear = Integer.parseInt(seasonandcropyear[1]);
			
		
			
			int villageCode = Integer.parseInt(village);
			String ipAddress = request.getRemoteAddr();

			String wbvname=cropRepository.getwbvname(Integer.parseInt(village));
			
			model.addAttribute("selectedVillageName", wbvname);
			
			
			
		int totalcount=	weblandNewCropService.checkInsertAndWeblandCropDetails(userId, ipAddress,
					String.valueOf(wbdcode), String.valueOf(wbmcode), String.valueOf(villageCode), season, cropYear, Integer.parseInt(sesdcode),
					Integer.parseInt(sesmcode));

			
			
			
			model.addAttribute("selectedCropYear", season);
			model.addAttribute("selectedVillageCode", villageCode);
			model.addAttribute("Size", totalcount);


			System.out.println("selectedVillageCode:-------------->" + villageCode);
			System.out.println("******");
			System.out.println("selectedVillageName:-------------->" + wbvname);

			// Redirect to the selected data page
			return "maoroles/selected-data";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error processing the request");
			return "maoroles/WeblandCropEntry";
		}
	}
}
