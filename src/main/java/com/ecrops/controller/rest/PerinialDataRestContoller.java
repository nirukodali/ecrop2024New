package com.ecrops.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.PerinialCropYearAndVillageCode;
import com.ecrops.dto.crop.response.PerinialVillageData;
import com.ecrops.repo.crop.PerinialDataCropRepository;
import com.ecrops.repo.crop.PernialDataServiceRepo;
import org.json.JSONObject;

@RestController
@RequestMapping("/rest/api")
public class PerinialDataRestContoller {

	@Autowired
	private PerinialDataCropRepository perinialDataCropRepository;

	@GetMapping("/perinialdata/villages")
	private List<PerinialVillageData> getVillages(@RequestParam("activeSeason") String activeSeason,
			HttpSession session) {
		
		System.out.println("activeSeason-->"+activeSeason);
		try {
			String sesmcode = (String) session.getAttribute("mcode");
			String[]seasonwithyear=activeSeason.split("@");			

			String season =seasonwithyear [0];
			int activeYear = Integer.parseInt(seasonwithyear[1]);
			System.out.println("season--->"+season);

			System.out.println("activeYear--->"+activeYear);


			List<PerinialVillageData> villages = perinialDataCropRepository
					.getWbvcodeWbvname(Integer.parseInt(sesmcode), activeYear, season);
			

			return villages;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while processing the request");
		}
	}

	

}
