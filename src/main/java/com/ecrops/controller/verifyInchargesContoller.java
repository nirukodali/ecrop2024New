package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecrops.dto.crop.response.VerifyInchargeUserRegistrationData;
import com.ecrops.repo.crop.VerifyInchargeService;

@Controller
public class verifyInchargesContoller {

	@Autowired
	private VerifyInchargeService inchargeService;

	@PreAuthorize("hasAuthority('5') || hasAuthority('2')")
	@GetMapping("/verifyInchargesIntf")
	public String getverifyinchargeIntf(Model model, HttpSession httpSession) {

		String role = httpSession.getAttribute("role").toString();
		
		System.out.println("role---"+role);

		String label = "";
		String label1 = "VAA/VHA/VSA";
		String label2 = "VRO";
		if (role.equals("5")) {
			label = label1;
		} else if (role.equals("2")) {
			label = label2;
		}

		model.addAttribute("label", label);

		String sesdcode =(String) httpSession.getAttribute("dcode");
		String sesmcode = httpSession.getAttribute("mcode").toString();

		System.out.println("sesmcode---" + sesmcode);

		List<VerifyInchargeUserRegistrationData> resultList = inchargeService
				.viewAndGetDataFromUserRegistration(sesdcode, sesmcode, role);

		model.addAttribute("incharges", resultList);

		return "mro/verifyInchargesIntf";
	}
	
	@PreAuthorize("hasAuthority('5') || hasAuthority('2')")
    @PostMapping("/apprInchargeSave")
    public String apprInchargeSave() {
    	
    return "mro/redirect:/verifyInchargesIntf";
    }

}
