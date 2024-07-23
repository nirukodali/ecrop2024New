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
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycd;

@Controller
public class VaaVroAuthAndFarEkycdRepController {
	@Autowired 
    private com.ecrops.service.impl.ActiveseasonFfekycdServiceImpl  activeseasonFfekycdService;
	
	@Autowired 
    private com.ecrops.service.impl.CropnamesFekycdServiceImpl  cropnamesFekycdService;
	
	@Autowired 
    private com.ecrops.service.impl.Cr_authdetails_mand_cropwise_v_syearFfekycdServiceImpl  cr_authdetails_mand_cropwise_v_syearFfekycdService;
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@GetMapping("/farmerekycd")
	public String findAllEntri(Model theModel)
	{
		theModel.addAttribute("cropyears",activeseasonFfekycdService.findAll());
		theModel.addAttribute("cropnames",cropnamesFekycdService.findAll());
	    return "jc/farmerekycd";   
		
	}
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@PostMapping("/crp-report")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,@RequestParam("cropname") String cropname,Model model)
	{
	  	
		System.out.println("************************************************"+cropname);
		int cropid= Integer.parseInt(cropname);
		String dcode=(String) httpSession.getAttribute("dcode");
		System.out.println("dcode---------->"+dcode);
		
		List<Cr_authdetails_mand_cropwise_v_syearFfekycd> cropreport=cr_authdetails_mand_cropwise_v_syearFfekycdService.getCropwise(Integer.parseInt(dcode), cr_year,cropid);
		 for (Cr_authdetails_mand_cropwise_v_syearFfekycd Cr_authdetails_mand_cropwise_v_syearFfekycd : cropreport) {
	           System.out.println("crop details: " + Cr_authdetails_mand_cropwise_v_syearFfekycd.getWbmname());
	     }
		model.addAttribute("cropreport", cropreport);
		model.addAttribute("theDate", LocalDate.now());
		model.addAttribute("cropyears",activeseasonFfekycdService.findAll());
		model.addAttribute("cropnames",cropnamesFekycdService.findAll());
	    return "jc/farmerekycd";   
		
	} 
	
	
}