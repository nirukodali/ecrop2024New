package com.ecrops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecrops.service.impl.WatersourcesRepServiceImpl;

@Controller
public class WatersourcesRepController {
	
	@Autowired 
    private WatersourcesRepServiceImpl  watersourcesRepService;
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/watersourcesrep")
	public String findAllEntri(Model theModel)
	{
	  	
		System.out.println("repwatersourcesService"+watersourcesRepService.findAll());
		theModel.addAttribute("watersources",watersourcesRepService.findAll());
	    return "ddh/watersourcesrep";   
		
	} 

}