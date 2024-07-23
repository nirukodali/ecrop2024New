package com.ecrops.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.ecrops.entity.Cropnames;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.repo.CropnamesRepository;
import com.ecrops.service.impl.CropgroupsServiceImpl;
import com.ecrops.service.impl.CropnamesServiceImpl;


@Controller
public class CropNameTxController {
	@Autowired 
    private CropgroupsServiceImpl  cropgroupsService;
	
	@Autowired
	private CropnamesServiceImpl cropnamesService;
	
	@Autowired
	private CropnamesRepository cropnamesRepository;
	
	
	
//	d407d1e4bfae9696a6822e666642fa4c
	
	@PreAuthorize("hasAuthority('17')")
	@GetMapping("/addcropname")
    public String showForm(Model theModel) {
        theModel.addAttribute("cropnames", new Cropnames());
        theModel.addAttribute("cropgroups", cropgroupsService.findAll());
        return "ddap/addcropname";
    }
  
	@PreAuthorize("hasAuthority('17')")
	@PostMapping("/processCropnames")
	public String processForm(@Valid @ModelAttribute("cropnames") Cropnames theCropnames, BindingResult theBindingResult,
                           Model model
                           ) {
	     if (theBindingResult.hasErrors()) {
	    	 System.out.println("in Binding result");
	         model.addAttribute("cropnames", theCropnames);
	         return "addcropname";
	     } else {
	         System.out.println("cropnames------>" + theCropnames.getCropname());
	         System.out.println("cropnameeng------>" + theCropnames.getCropnameeng());
	
	         com.ecrops.config.RegularExpressionclassMethod method= new com.ecrops.config.RegularExpressionclassMethod();
	         
	         if(method.checkstring(theCropnames.getCropnameeng())) {
	
	         Cropnames newCropname = new Cropnames();
	         newCropname.setCropid(cropnamesRepository.getMaxCropId()+1);
	         newCropname.setCropname(theCropnames.getCropname());
	         newCropname.setCropnameeng(theCropnames.getCropnameeng());
	         newCropname.setCropnature(theCropnames.getCropnature());
	         newCropname.setCroptype(theCropnames.getCroptype());
	         newCropname.setCropclass(theCropnames.getCropclass());
	         newCropname.setGrpcode(theCropnames.getGrpcode());
	
	         cropnamesService.addCropname(newCropname);
	         }
	         return "ddap/addcropname";
	     }
    }
}