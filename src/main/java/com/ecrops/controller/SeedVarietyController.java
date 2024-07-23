package com.ecrops.controller;

import java.io.UnsupportedEncodingException;
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

import com.ecrops.dto.SeedFormData;
import com.ecrops.entity.SeedGroupEntity;
import com.ecrops.entity.VarietyNameEntity;
import com.ecrops.repo.SaveSeedVarietyRepo;
import com.ecrops.repo.SeedGroupRepo;
import com.ecrops.repo.VarietyNameRepo;

@Controller
public class SeedVarietyController {

	@Autowired
	private SeedGroupRepo seedGroupRepo;

	@Autowired
	private VarietyNameRepo varietyNameRepo;

	@Autowired
	private SaveSeedVarietyRepo saveSeedVarietyRepo;

	@PreAuthorize("hasAuthority('17')")
	@GetMapping("/seedVariety")
	public String getSeedGroup(Model model, HttpSession httpSession) {

		List<SeedGroupEntity> seedGroup = seedGroupRepo.getGroups();
		model.addAttribute("seedGroup", seedGroup);

		model.addAttribute("seedVariety", new SeedFormData());
		return "ddap/seedVariety";
	}

	@PreAuthorize("hasAuthority('17')")
	@PostMapping("/saveSeedVariety")
	public String saveSeedData(@Valid @ModelAttribute("seedVariety") SeedFormData seedVariety,
			BindingResult bindingResult, HttpSession httpSession, HttpServletRequest httpServletRequest, Model model)
			throws UnsupportedEncodingException {
		System.out.println("seedVariety=>" + seedVariety.toString());

		if (bindingResult.hasErrors()) {
			System.out.println("haserror");
			// If there are validation errors, redirect back to the form page
			return "ddap/seedVariety";
		}

		String msg = "";
		String user = (String) httpSession.getAttribute("userid");
		System.out.println("user  --------> " + user);

//		String groupId = httpServletRequest.getParameter("cropGroup");
//		String VarietyName = httpServletRequest.getParameter("VarietyName");
//		String cropId = httpServletRequest.getParameter("cropNameId");
		
		String groupId = seedVariety.getCropGroup();
		String VarietyName = seedVariety.getVarietyName();
		String cropId = seedVariety.getCropName().trim();
		
		System.out.println(groupId+"---------->"+VarietyName+"---------------"+cropId);
		int cropid = Integer.parseInt(cropId);

		String encodedVariety = new String(VarietyName.getBytes("ISO-8859-1"), "UTF-8");

		List<VarietyNameEntity> varietyNames = varietyNameRepo.getVarietyName(cropid, encodedVariety);
		String variety = varietyNameRepo.getVarietyCount();
		int varietyCount = Integer.parseInt(variety);

		if (varietyNames.isEmpty()) {
			int saveSeedVariety = saveSeedVarietyRepo.insertSeedDetails(varietyCount, encodedVariety, cropId, user);
			if (saveSeedVariety > 0) {
				model.addAttribute("msg", "<span style= 'color:green; font-size : 30px'>Variety Name Added Successfully</span>");
			}

		}
		else {
			model.addAttribute("msg", "<span style= 'color:red; font-size : 30px'>Variety Name Already Exist</span>");
		}
		List<SeedGroupEntity> seedGroup = seedGroupRepo.getGroups();
		model.addAttribute("seedGroup", seedGroup);

		model.addAttribute("seedVariety", new SeedFormData());

		return "ddap/seedVariety";

	}

}
