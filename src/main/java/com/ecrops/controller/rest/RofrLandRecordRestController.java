package com.ecrops.controller.rest;


import com.ecrops.dto.crop.response.VillageDataRofr;
import com.ecrops.repo.crop.RofrLandRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class RofrLandRecordRestController {

	@Autowired
	private RofrLandRecordRepository recordRepository;

	
	@GetMapping("/rofrLandRecord/villages")
	private List<VillageDataRofr> getVillages(@RequestParam("activeSeason") String activeSeason,
			HttpSession session) {
		if (activeSeason == null || session == null) {
			throw new IllegalArgumentException("Active season or session is null");
		}

		try {
			String sesmcode=(String)session.getAttribute("mcode");
			
			String season = activeSeason.split("@")[0];
			int activeYear = Integer.parseInt(activeSeason.split("@")[1]);

			
			List<VillageDataRofr> villages = recordRepository.getWbvcodeWbvname(Integer.parseInt(sesmcode), activeYear, season);

			
			return villages;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while processing the request");
		}
	}
}
