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

import com.ecrops.entity.Cr_details_syearFsr;
import com.ecrops.service.impl.ActiveseasonFsrServiceImpl;
import com.ecrops.service.impl.Cr_details_swbdcodeyrFsrServiceImpl;

@Controller
public class SupervisoryChkRepController {
	@Autowired 
    private ActiveseasonFsrServiceImpl  activeseasonFsrService;
	
	@Autowired 
    private Cr_details_swbdcodeyrFsrServiceImpl  Cr_details_swbdcodeyrService;
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@GetMapping("/farmerekycsr")
	public String findAllEntri(Model theModel)
	{
		theModel.addAttribute("cropyears",activeseasonFsrService.findAll());
	    return "jc/farmerekycsr";   
		
	} 
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@PostMapping("/getSuperchkrep")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,Model model)
	{
		String wbdcode="";
		System.out.println("************************************************"+cr_year);
		 try {
			wbdcode= httpSession.getAttribute("wbdcode").toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("wbdcode---------->"+wbdcode);
		String userid= (String) httpSession.getAttribute("userid");
		System.out.println("userid---------->"+userid);
		List<Cr_details_syearFsr> cropreport = null;
		try {
			cropreport = Cr_details_swbdcodeyrService.getCropwise( cr_year,wbdcode,userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("cropreport", cropreport);
		model.addAttribute("theDate", LocalDate.now());
		model.addAttribute("cropyears",activeseasonFsrService.findAll());
	    return "jc/farmerekycsr";   
		
	} 

}