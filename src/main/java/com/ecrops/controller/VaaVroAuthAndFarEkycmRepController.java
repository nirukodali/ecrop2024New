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

import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycm;
import com.ecrops.service.impl.ActiveseasonFfekycmServiceImpl;
import com.ecrops.service.impl.Cr_authdetails_mand_cropwise_v_syearFfekycmServiceImpl;
import com.ecrops.service.impl.CropnamesFekycmServiceImpl;


@Controller
public class VaaVroAuthAndFarEkycmRepController {
	@Autowired 
    private ActiveseasonFfekycmServiceImpl  activeseasonFfekycmService;
	
	@Autowired 
    private CropnamesFekycmServiceImpl  cropnamesFekycmService;
	
	@Autowired 
    private Cr_authdetails_mand_cropwise_v_syearFfekycmServiceImpl  cr_authdetails_mand_cropwise_v_syearFfekycmService;
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@GetMapping("/farmerekycm")
	public String findAllEntri(Model theModel)
	{
		theModel.addAttribute("cropyears",activeseasonFfekycmService.findAll());
		theModel.addAttribute("cropnames",cropnamesFekycmService.findAll());
	    return "jc/farmerekycm";   
		
	}
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@PostMapping("/crop-report")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,@RequestParam("cropname") String cropname,Model model)
	{
	  	
		System.out.println("************************************************"+cropname);
		int cropid= Integer.parseInt(cropname);
		String dcode=(String) httpSession.getAttribute("dcode");
		System.out.println("dcode---------->"+dcode);
		
		List<Cr_authdetails_mand_cropwise_v_syearFfekycm> cropreport=cr_authdetails_mand_cropwise_v_syearFfekycmService.getCropwise(Integer.parseInt(dcode), cr_year,cropid);
		 for (Cr_authdetails_mand_cropwise_v_syearFfekycm Cr_authdetails_mand_cropwise_v_syearFfekycm : cropreport) {
	           System.out.println("crop details: " + Cr_authdetails_mand_cropwise_v_syearFfekycm.getWbmname());
	     }
		model.addAttribute("cropreport", cropreport);
		model.addAttribute("theDate", LocalDate.now());
	    return "jc/farmerekycm";   
		
	} 
	
	
}