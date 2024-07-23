package com.ecrops.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.dto.crop.response.CropYearROFR;
import com.ecrops.dto.crop.response.FormData;
import com.ecrops.repo.crop.ROFRServiceRepo;
import com.ecrops.repo.crop.RofrLandRecordRepository;

@Controller
public class RofrLandRecordContoller {

	@Autowired
	private ROFRServiceRepo serviceRepo;

	@Autowired
	private RofrLandRecordRepository recordRepository;
	
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/rofrLandRecordEntry")
	public String ccrcData(Model model, HttpSession httpSession) {
		List<CropYearROFR> activeSeasons = recordRepository.getCropYear();
		model.addAttribute("activeSeasons", activeSeasons);

		return "maoroles/rofrLandRecordEntry";
	}
	
	@PreAuthorize("hasAuthority('5')")
	@PostMapping("/fetchRofrLandRecordData")
	public String fetchRofrNandDetailsDetails( @RequestParam("cropyear") String cropyearAndSeason,
			@RequestParam("village") String wbvcode, Model model, HttpSession session, HttpServletRequest request) {
		try {
			if (cropyearAndSeason == null || wbvcode == null || session == null) {
				throw new IllegalArgumentException("Crop year, village code, or session attributes cannot be null");
			}

			String sesmcode = (String) session.getAttribute("mcode");
			String sesdcode = (String) session.getAttribute("dcode");

			String userId = (String) session.getAttribute("userid");
			int wbdcode = (int) session.getAttribute("wbdcode");
			int wbmcode = (int) session.getAttribute("wbmcode");

			int lgdvcode= recordRepository.getwbvcode(Integer.parseInt(wbvcode));
			System.out.println("lgdvcode-->"+lgdvcode);
			System.out.println("wbvcode-->"+wbvcode);

		
			String seasonWithYear = cropyearAndSeason;
			String season = seasonWithYear.split("@")[0];
			int cropYear = Integer.parseInt(seasonWithYear.split("@")[1]);

			int[] lgCodes = getLgddcodeLgmcode(session);

			if (lgCodes.length < 2) {
				throw new IllegalArgumentException("LG codes not found in session attributes");
			}

			int lgddcode = lgCodes[0];
			int lgdmcode = lgCodes[1];

			String ipaddress = request.getRemoteAddr();
			System.out.println("ROFRServiceRepo.getKeyValue();--------------------"+ROFRServiceRepo.getKeyValue());
			String key = ROFRServiceRepo.getKeyValue();


			
			int alreadyAvailableRecords = serviceRepo.checkRecordIsAvailableInDatabase(lgdvcode,
					season, cropYear);
			if (alreadyAvailableRecords > 0) {
				model.addAttribute("alreadyAvailableRecords", alreadyAvailableRecords);
			} else {
				int newRecordcount = serviceRepo.insertROFRLandRecords(String.valueOf(lgddcode),
						String.valueOf(lgdmcode), String.valueOf(lgdvcode), Integer.parseInt(wbvcode),wbdcode,wbmcode, key, season, sesdcode, sesmcode, cropYear, userId,
						ipaddress);
				System.out.println("newRecordcount---"+newRecordcount);
				model.addAttribute("newRecordcount", newRecordcount);
			}

			
			String lgdvname = recordRepository.getLgdvname(lgdvcode);
			System.out.println("lgdvname-->"+lgdvname);
			model.addAttribute("lgdvname", lgdvname);

			return "maoroles/selectedDataRofr";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An unexpected error occurred");
			return "maoroles/rofrLandRecordEntry";
		}
	}

	public int[] getLgddcodeLgmcode(HttpSession session) {
		String sesmcode = (String) session.getAttribute("mcode");
		String sesdcode = (String) session.getAttribute("dcode");

		List<Object[]> lgddcodeLgmcode = recordRepository.getLgddcodeLgdmcode(Integer.parseInt(sesdcode),
				Integer.parseInt(sesmcode));
		if (!lgddcodeLgmcode.isEmpty()) {
			Object[] ob = lgddcodeLgmcode.get(0);
			int lgddcode = (int) ob[0];
			int lgdmcode = (int) ob[1];
			return new int[] { lgddcode, lgdmcode };
		} else {
			System.err.println("Lgddcode and Lgmcode Not Received From Service ");
			return new int[] {};
		}
	}
}
