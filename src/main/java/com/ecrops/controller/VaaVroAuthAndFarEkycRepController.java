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

import com.ecrops.entity.Cr_authdetails_mand_mv_kyearFfekyc;
import com.ecrops.service.impl.ActiveseasonFfekycServiceImpl;
import com.ecrops.service.impl.Cr_authdetails_mand_mv_kyearFfekycServiceImpl;


@Controller

public class VaaVroAuthAndFarEkycRepController {
	
	@Autowired 
    private ActiveseasonFfekycServiceImpl  activeseasonFfekycService;
	
	@Autowired 
    private Cr_authdetails_mand_mv_kyearFfekycServiceImpl  cr_authdetails_mand_mv_kyearService;
	
	@PreAuthorize("hasAuthority('44') || hasAuthority('45')")
	@GetMapping("/farmerekyc")
	public String findAllEntri(Model theModel)
	{
		theModel.addAttribute("cropyears",activeseasonFfekycService.findAll());
	    return "jc/farmerekyc";   
		
	} 
	
	
	@PreAuthorize("hasAuthority('44') || hasAuthority('45')")
	@PostMapping("/farmerekycRep")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,Model model)
	{
	  	
		String dcode=(String) httpSession.getAttribute("dcode");
		List<Cr_authdetails_mand_mv_kyearFfekyc> cropreport=null;
		try {
		cropreport=cr_authdetails_mand_mv_kyearService.getCropwise(Integer.parseInt(dcode), cr_year);
		}
		catch(Exception e) {
			
		}
		model.addAttribute("cropreport", cropreport);
		model.addAttribute("theDate", LocalDate.now());
	    return "jc/farmerekycRep";   
		
	} 
	
}