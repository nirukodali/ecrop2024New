package com.ecrops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecrops.service.impl.CropgroupsRepServiceImpl;


@Controller
public class CropGroupsRepController {

	@Autowired
	private CropgroupsRepServiceImpl cropgroupsRepService;
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/cropgroupsrep")
	public String cropGroupsRep(Model theModel) {
	    theModel.addAttribute("cropgroups", cropgroupsRepService.findAll());
	    return "ddh/cropgroupsrep";
	} 

}