/**Cropbooking details report*/
package com.ecrops.controller;

import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecrops.entity.CropBookingDetails;
import com.ecrops.repo.ActiveseasonFcbdRepo;
import com.ecrops.repo.CropBookingDetailsRepo;
import com.ecrops.repo.CropnamesFcbdRepo;

@PreAuthorize("hasAuthority('30')")
@Controller
public class CropBookingDetailsRepController {
	@Autowired
	ActiveseasonFcbdRepo activeseasonFcbdRepo;

	@Autowired
	CropnamesFcbdRepo cropnamesFcbdRepo;

	

	@Autowired
	CropBookingDetailsRepo cropBookingDetailsRepo;

	// get the form
	@GetMapping("/cropbookingdetails")
	public String cropbookingdetails(Model theModel, HttpSession session) {
		theModel.addAttribute("cropyears", activeseasonFcbdRepo.getCropyear());
		theModel.addAttribute("cropnames", cropnamesFcbdRepo.getcropnames());
		theModel.addAttribute("theDate", LocalDate.now());
		return "vro/cropbookingdetails";
	}

	// get the report
	@PostMapping("/cropbookingdetails-report")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,
			@RequestParam("cropname") String cropname, @RequestParam("farmertypename") String display, Model model) {

		int dcode = Integer.parseInt(httpSession.getAttribute("dcode").toString());
		int wbmcode = Integer.parseInt(httpSession.getAttribute("wbmcode").toString());
		int vcode = (int) httpSession.getAttribute("vcode");
		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();

		List<CropBookingDetails> cropreport = null;
		try {
			cropreport = cropBookingDetailsRepo.getCropBookingDetails(dcode, wbmcode, vcode, cropname, cr_year, wbdcode,
					activeYear, display);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cropyears", activeseasonFcbdRepo.getCropyear());
		model.addAttribute("cropnames", cropnamesFcbdRepo.getcropnames());
		model.addAttribute("theDate", LocalDate.now());
		model.addAttribute("crpreprt", cropreport);
		
		return "vro/cropbookingdetails";
	}
}
