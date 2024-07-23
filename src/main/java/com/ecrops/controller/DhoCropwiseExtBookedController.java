package com.ecrops.controller;

import java.time.LocalDate; 
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ecrops.entity.CropgroupsFCWR;
import com.ecrops.entity.CropnamesFCWR;
import com.ecrops.entity.CropwiseExtBooked;
import com.ecrops.service.CropgroupsFCWRServiceImpl;
import com.ecrops.service.CropnamesFCWRServiceImpl;
import com.ecrops.service.CropwiseExtBookedServiceImpl;


@Controller
public class DhoCropwiseExtBookedController {
	@Autowired 
    private com.ecrops.service.ActiveseasonFcwebServiceImpl  activeseasonFcwebService;
	
	@Autowired 
    private CropgroupsFCWRServiceImpl  cropgroupsFCWRService;
	
	@Autowired 
    private CropnamesFCWRServiceImpl  cropnamesFCWRService;
	
	@Autowired 
    private CropwiseExtBookedServiceImpl  cropwiseExtBookedService;
	
	@PreAuthorize("hasAuthority('19')")
	@GetMapping("/cropwiseextbooked")
	public String CropwiseExtBooked(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute("cropyears",activeseasonFcwebService.findAll());
		model.addAttribute("cropgroups",cropgroupsFCWRService.findAll());
		model.addAttribute("repgrop",new CropgroupsFCWR());
	    return "dho/cropwiseextbookedrepdho"; 
	}
	
	@PreAuthorize("hasAuthority('19')")
	@RequestMapping("/cropreportdho/{grpcode}")
	@ResponseBody
	public List<CropnamesFCWR> findAllVi(@PathVariable("grpcode") int grpcode,Model model)
	{
	  	System.out.println("VillagesDho----->"+cropnamesFCWRService.findAll(grpcode));
		List<CropnamesFCWR> cropnames=cropnamesFCWRService.findAll(grpcode);
	    return cropnames;   
		
	} 
	
	@PreAuthorize("hasAuthority('19')")
	@RequestMapping("/cropreport")
	public String cropreport(Model model) {
		model.addAttribute("cropyears",activeseasonFcwebService.findAll());
		model.addAttribute("cropgroups",cropgroupsFCWRService.findAll());
		model.addAttribute("repgrop",new CropgroupsFCWR());
		 return "dho/cropwiseextbookedrepdho";
	}

	@PreAuthorize("hasAuthority('19')")
	@GetMapping("/cropreportDho/{cropyear}/{cropname}/{crpgrp}")
	@ResponseBody
	public String findAllV(@PathVariable("cropyear") String cr_year,@PathVariable("cropname") String cropname,@PathVariable("crpgrp") String crpgrp,HttpSession httpSession,Model model,HttpServletRequest request)
	{
	  	//System.out.println("Villages----->"+repcropnamesRepository.getCropwise(dcode).toString());
		System.out.println("DHoooooooo************************************************"+cr_year);
		String dcode=(String) httpSession.getAttribute("dcode");
		System.out.println("dcode---------->"+dcode);
		
		List<CropwiseExtBooked> cropreport=cropwiseExtBookedService.getCropwise(Integer.parseInt(dcode),cr_year);
		System.out.println("cropreport.size()----------->"+cropreport.size());
		model.addAttribute("cropreport", cropreport);
		
		model.addAttribute("theDate", LocalDate.now());
	    return "dho/cropwiseextbookedrepdho";   
		
	} 
	
	
	
}