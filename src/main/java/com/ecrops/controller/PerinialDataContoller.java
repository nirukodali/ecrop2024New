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

import com.ecrops.dto.crop.response.PerinialCropYear;
import com.ecrops.repo.crop.PerinialDataCropRepository;
import com.ecrops.repo.crop.PernialDataServiceRepo;

@Controller
public class PerinialDataContoller {

	
	@Autowired
	private PerinialDataCropRepository perinialDataCropRepository;
	
	@Autowired
	private PernialDataServiceRepo dataServiceRepo;
	
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/perinialdataEntry")
	public String perinialData(Model model, HttpSession httpSession) {
		List<PerinialCropYear> activeSeasons = perinialDataCropRepository.getCropYear();
		model.addAttribute("activeSeasons", activeSeasons);

		return "maoroles/perinialdataentry";
	}
	
	
	@PreAuthorize("hasAuthority('5')")
	@PostMapping("/fetchperinialdataentry")
		public String getVillageAndCropYear(@RequestParam("cropyear") String cropyear,@RequestParam("village") String village,
				HttpSession session, HttpServletRequest request,Model model) {
		
			if (cropyear == null || village == null ) {
				System.err.println("cropyear and villageCode Not Received From UIPage ");
				throw new IllegalArgumentException("Crop year, village code,  attributes cannot be null");

			}

			String[] parts = cropyear.split("@");
			String Season = parts[0];
			int CropYear = Integer.parseInt(parts[1]);
			int wbdcode = (int) session.getAttribute("wbdcode");
			int wbmcode = (int) session.getAttribute("wbmcode");
			if (wbdcode == 0 || wbmcode == 0) {
				System.err.println("wbdcode and wbmcode Not Received From Session ");
				throw new IllegalArgumentException("wbdcode, wbmcode,  attributes cannot be 0");
			}

			int villagecode=Integer.parseInt(village);
			String VillageName = perinialDataCropRepository.getwbvname(villagecode);
			String status = dataServiceRepo.InsertPerinialData(String.valueOf(wbdcode), villagecode, CropYear, Season);
			int recordcount = perinialDataCropRepository.getRecordCount(villagecode, (CropYear-1), Season);
			int recordcountforRabi = perinialDataCropRepository.getRecordCountRabiSeason(villagecode, (CropYear-1), Season);
			
			int totalcount=recordcount+recordcountforRabi;
			System.out.println("recordcountforKarif--->" + recordcount);
			System.out.println("recordcountforRabi--->" + recordcountforRabi);
			String updateStatus="";
			
			 updateStatus = dataServiceRepo.updateVerifyDataDownload(totalcount, wbdcode, wbmcode, villagecode,
					CropYear, Season);
			 model.addAttribute("recordcount", recordcount);
			 model.addAttribute("recordcountforRabi", recordcountforRabi);
			 model.addAttribute("VillageName", VillageName);
			 model.addAttribute("status", status);
		
			return "maoroles/selectedDataPerinialDataEntry";
		}
	
	
	}
