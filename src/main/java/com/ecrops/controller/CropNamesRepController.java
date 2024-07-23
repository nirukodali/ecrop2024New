package com.ecrops.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ecrops.entity.CropnamesRep;
import com.ecrops.service.impl.CropgroupsFCNRServiceImpl;
import com.ecrops.service.impl.CropnamesRepServiceImpl;

@Controller
public class CropNamesRepController {

	    @Autowired
	    private CropgroupsFCNRServiceImpl cropgroupsFCNRService;
	    
	    @Autowired
	    private CropnamesRepServiceImpl cropnamesRepService;

	    @PreAuthorize("hasAuthority('18')")
	    @GetMapping("/cropnamesrep")
		public String findAllCrops(Model theModel)
		{
			System.out.println("CropgroupsFCNRService-------------------------------"+cropgroupsFCNRService.findAll());
			theModel.addAttribute("cropgroups",cropgroupsFCNRService.findAll());
		    return "ddh/cropnamesrep";   
		} 
		
	    @PreAuthorize("hasAuthority('18')")
		@GetMapping("/getCropNames/{grpcode}")
		public ResponseEntity<List<CropnamesRep>> getCropGroup(@PathVariable("grpcode") Integer grpcode)
		{
		  	
			
			try {
				List<CropnamesRep> crpGrpNames = cropnamesRepService.findAll(grpcode);

				if (crpGrpNames.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				 System.out.println("FarmBlock:"+crpGrpNames);
				return new ResponseEntity<>(crpGrpNames, HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		
}