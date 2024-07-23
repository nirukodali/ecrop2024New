package com.ecrops.controller;

import com.ecrops.dto.crop.request.CCRCPullRequest;

import com.ecrops.dto.crop.response.CCRCPullResponse;
import com.ecrops.dto.crop.response.CcrcCropDetails;
import com.ecrops.dto.crop.response.CropYearCCRC;
import com.ecrops.dto.webland.CCRCCropData;
import com.ecrops.repo.crop.CcrcCropRepository;
import com.ecrops.repo.crop.CcrcCropServiceRepo;
//import com.ecrops.service.impl.WbMasterService;
import com.ecrops.service.impl.WbMasterServiceCcrc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CcrcController{

    @Autowired
    private CcrcCropServiceRepo ccrcCropServiceRepo;

    @Autowired
    private CcrcCropRepository ccrcCropRepository;
    @Autowired
	private WbMasterServiceCcrc wbMasterServiceCcrc;
    
//    @Autowired
//    private WbMasterService wbMasterService;

    // Handle requests to the weblandCropEntry page
    @PreAuthorize("hasAuthority('5')")
    @GetMapping("/ccrcCropEntry")
	public String ccrcData(Model model, HttpSession httpSession) {
		List<CropYearCCRC> activeSeasons = ccrcCropRepository.getCropYear();
		System.out.println("activeSeasons--------->"+activeSeasons.get(0).getSeasonvalue());
		model.addAttribute("activeSeasons", activeSeasons);
		model.addAttribute("CCRCPullResponse", new CCRCPullResponse());
		return "maoroles/CcrcCropEntry";
	}
    @PreAuthorize("hasAuthority('5')")
	@PostMapping("/fetch-ccrc-crop-data")
	public String fetchCCRCDetails(@RequestParam("cropyear") String cropyear,@RequestParam("village") String village, Model model, HttpSession session,
			HttpServletRequest request) {
		try {

			String mandalCode = (String) session.getAttribute("mcode");
			String districtCode = (String) session.getAttribute("dcode");

			int distCode = Integer.parseInt(districtCode);
			int mandCode = Integer.parseInt(mandalCode);

			// Extract user information from the session
			String userId = (String) session.getAttribute("userid");
			
			// Extract district code and mandal code from session
			int dCode =(int) session.getAttribute("wbdcode");
			int mCode = (int)session.getAttribute("wbmcode");

			// Extract crop year information from the form
			String seasonWithYear = cropyear;
			String season = seasonWithYear.split("@")[0];
			int cropYear = Integer.parseInt(seasonWithYear.split("@")[1]);
			
			System.out.println(cropyear+"season--------------"+season);
			System.out.println("cropYear------------------>"+cropYear);
			System.out.println("village------------------>"+village);

			// Extract village information from the form
			String villageString = village.replaceAll(",", "");
			if (villageString.isEmpty()) {
				throw new IllegalArgumentException("Village number is empty");
			}
			// Parse the village code
			int villageCode = Integer.parseInt(villageString);
			String ipAddress = request.getRemoteAddr();

			String villageName = ccrcCropRepository.getwbvname(villageCode);
//			 String result= ccrcCropServiceRepo.fetchCcrcDetailsFromURL(String.valueOf(dCode),String.valueOf(mCode),String.valueOf(villageCode)) ;
//			 if(result==null) {
//				 System.out.println("datagettingErrorFromUrl----->"+result);
//				 model.addAttribute("errorMessage", "Unable to connect to the server or error reading url..");
//			 }

			int AlreadyAvailableRecordCounts = ccrcCropServiceRepo.checkRecordIsAvailableInDatabase(villageCode, season,
					cropYear);
			
			
			int newCCRCFetchedRecords = ccrcCropServiceRepo.checkInsertAndViewCccrcCropDetails(cropYear, userId,
					ipAddress, dCode, villageCode, mCode, season, cropYear, mandCode, distCode);

			
			model.addAttribute("AlreadyAvailableRecordCounts", AlreadyAvailableRecordCounts);
			model.addAttribute("TotalRecords", newCCRCFetchedRecords);
			model.addAttribute("selectedVillageCode", villageCode);
			model.addAttribute("selectedVillageName", villageName);

			// retrieved crop data
			/*
			 * System.out.println("**************************************");
			 * System.out.println("AlreadyAvailableRecordCounts--" +
			 * AlreadyAvailableRecordCounts);
			 * System.out.println("newCCRCFetchedRecordsFromService----" +
			 * newCCRCFetchedRecords); System.out.println("cropYear:  " + cropYear);
			 * System.out.println("**************************************");
			 * System.out.println("selectedSeason:  " + season);
			 * System.out.println("**************************************");
			 */
			System.out.println("selectedVillageCode:   " + villageCode);
			System.out.println("**************************************");
			System.out.println("selectedVillageName:   " + villageName);

			return "maoroles/selected-data-ccrc";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An unexpected error occurred");
			return "maoroles/CcrcCropEntry";
		}
	}
}