package com.ecrops.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecrops.entity.CropgroupsFCWR;
import com.ecrops.entity.CropnamesFCWR;
import com.ecrops.service.ActiveseasonFcwebServiceImpl;
//import com.ecrops.entity.CropgroupsFCWR;
//import com.ecrops.entity.CropnamesFCWR;
//import com.ecrops.entity.CropwiseExtBooked;
//import com.ecrops.serviceimpl.ActiveseasonFcwebServiceImpl;
//import com.ecrops.serviceimpl.CropgroupsFCWRServiceImpl;
//import com.ecrops.serviceimpl.CropnamesFCWRServiceImpl;
//import com.ecrops.serviceimpl.CropwiseExtBookedServiceImpl;
import com.ecrops.service.CropgroupsFCWRServiceImpl;
import com.ecrops.service.CropnamesFCWRServiceImpl;
import com.ecrops.service.CropwiseExtBookedServiceImpl;

//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;


@Controller
public class CropwiseExtBookedRepController {
	@Autowired 
    private ActiveseasonFcwebServiceImpl  activeseasonFcwebService;
	
	@Autowired 
    private CropgroupsFCWRServiceImpl  cropgroupsFCWRService;
	
	@Autowired 
    private CropnamesFCWRServiceImpl  cropnamesFCWRService;
	
	@Autowired 
    private CropwiseExtBookedServiceImpl  cropwiseExtBookedService;
	
	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('46')")
	@GetMapping("/cropwiseextbookedrep")
	public String CropwiseExtBooked(Model model, HttpServletRequest httpServletRequest,HttpSession  httpSession) {
		String dcode=(String) httpSession.getAttribute("dcode");
		String role=(String) httpSession.getAttribute("role");
		//System.out.println("role--------->"+role);
		
		model.addAttribute("cropyears",activeseasonFcwebService.findAll());
		model.addAttribute("cropgroups",cropgroupsFCWRService.findAll());
		model.addAttribute("repgrop",new CropgroupsFCWR());
		
		String dateWithTime = LocalDate.now()+"  "+ LocalTime.now();
		System.out.println("LocalDate------>"+dateWithTime);
		model.addAttribute("theDate",dateWithTime);
//		if(role.equals(44))
	    return "jc/cropwiseextbookedrep"; 
//		if(role.equals(45))
//		    return "dc/cropwiseextbookedrep";
//		return null; 
	}
	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('46')")
	@GetMapping("/crop-report/{grpcode}")
	@ResponseBody
	public List<CropnamesFCWR> findAllVi(@PathVariable("grpcode") int grpcode,Model model)
	{
		System.out.println("grpcode---------->"+grpcode);
	  	System.out.println("Villages----->"+cropnamesFCWRService.findAll(grpcode));
		List<CropnamesFCWR> cropnames=cropnamesFCWRService.findAll(grpcode);
		
//		System.out.println("cropnames--------------->"+cropnames);
		
	    return cropnames;   
		
	} 

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('19') || hasAuthority('31') || hasAuthority('46')")
	@GetMapping("/cropReport/{cropyear}/{cropname}/{crpgrp}")
	@ResponseBody

	public List<com.ecrops.entity.CropwiseExtBooked> findAllV(@PathVariable("cropyear") String cr_year,@PathVariable("cropname") String cropname,@PathVariable("crpgrp") String crpgrp,HttpSession httpSession,Model model,HttpServletRequest request)
	
	
	{
	  	//System.out.println("Villages----->"+repcropnamesRepository.getCropwise(dcode).toString());
		
		
	   
	   
//		System.out.println("************************************************");
		System.out.println(cropname+"cr_year------------>"+cr_year+"---------"+crpgrp);
		String dcode=(String) httpSession.getAttribute("dcode");
		int role= Integer.parseInt((String)httpSession.getAttribute("role"));
		System.out.println("role-------------"+role);
		
		
//		System.out.println("dcode---------->"+dcode);
		List<com.ecrops.entity.CropwiseExtBooked> cropreport= null;
if(role==31) {
	String mcode= (String) httpSession.getAttribute("mcode");
	System.out.println("mcode----------->"+mcode);
	try {
	cropreport=cropwiseExtBookedService.getCropwise(Integer.parseInt(dcode), cr_year,cropname,crpgrp,mcode);
	}
	catch(Exception e) {
		
	}
		}
else {
	try {
		cropreport=cropwiseExtBookedService.getCropwise(Integer.parseInt(dcode), cr_year,cropname,crpgrp,role);
	}
	catch(Exception e) {
		
	}
}
		model.addAttribute("cropreport", cropreport);
//		System.out.println("LocalDate------>"+LocalDate.now());
//		model.addAttribute("theDate", LocalDate.now());
	    return cropreport;   
		
	} 
 
	
	
}