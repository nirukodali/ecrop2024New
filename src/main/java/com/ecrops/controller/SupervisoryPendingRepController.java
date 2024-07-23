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
import com.ecrops.entity.Cr_crop_det_new_v_swbdistyear;

@Controller
public class SupervisoryPendingRepController {
	@Autowired 
    private com.ecrops.service.impl.ActiveseasonFsvServiceImpl  activeseasonFsvService;
	
	@Autowired 
    private com.ecrops.service.impl.Cr_crop_det_new_v_swbdistyrFsvServiceImpl  cr_crop_det_new_v_swbdistyrFsvServiceImpl;
	
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@GetMapping("/farmerekycsp")
	public String findAllEntri(Model theModel)
	{
		theModel.addAttribute("cropyears",activeseasonFsvService.findAll());
	    return "jc/farmerekycsp";   
		
	} 
	
	@PreAuthorize("hasAuthority('44')  || hasAuthority('45')")
	@PostMapping("/getSuperverifypen")
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,Model model)
	{
	  	
		//System.out.println(httpSession.getAttribute("wbdcode").toString()+"************************************************"+cr_year);
		String wbdcode= httpSession.getAttribute("wbdcode").toString();
		//System.out.println("wbdcode---------->"+wbdcode);
		String userid= (String) httpSession.getAttribute("userid");
		//System.out.println("userid---------->"+userid);
		List<Cr_crop_det_new_v_swbdistyear> cropreport=cr_crop_det_new_v_swbdistyrFsvServiceImpl.getCropwise( cr_year,wbdcode,userid);
		model.addAttribute("cropreport", cropreport);
		model.addAttribute("theDate", LocalDate.now());
	    return "jc/farmerekycsv";   
		
	} 
}