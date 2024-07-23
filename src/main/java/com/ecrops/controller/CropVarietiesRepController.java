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
import com.ecrops.entity.CropvarietiesRep;
import com.ecrops.service.impl.CropgroupsFCVRServiceImpl;
import com.ecrops.service.impl.CropvarietiesRepServiceImpl;

@Controller
public class CropVarietiesRepController {
	
	@Autowired 
    private CropgroupsFCVRServiceImpl  CropgroupsFCVRService;
	
	@Autowired 
    private CropvarietiesRepServiceImpl  cropvarietiesRepServiceImpl;
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/cropvarietiesrep")
	public String findCropVarieties(Model theModel)
	{
		theModel.addAttribute("cropgroups",CropgroupsFCVRService.findAll());
	    return "ddh/cropvarietiesrep";   
	}
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/getCropVarieties/{cropgrpid}")
	public ResponseEntity<List<CropvarietiesRep>> getCropVariety(@PathVariable("cropgrpid") Integer grpcode) {
	    try {
	        List<CropvarietiesRep> crpGrpNames = cropvarietiesRepServiceImpl.findAll(grpcode);

	        if (crpGrpNames.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        
	        System.out.println("Crop Varieties: " + crpGrpNames);
	        return new ResponseEntity<>(crpGrpNames, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	

	
}