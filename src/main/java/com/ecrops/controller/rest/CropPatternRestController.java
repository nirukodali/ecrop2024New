package com.ecrops.controller.rest;


import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.CropnatureCropidCropname;
import com.ecrops.dto.crop.response.VarietyCodeVarietyNameCropPattern;
import com.ecrops.dto.crop.response.VcodeWbvnameForCropPattern;
import com.ecrops.repo.crop.CropPatternService;

@RestController
@RequestMapping("/rest/api")
public class CropPatternRestController {

	
	@Autowired
	private CropPatternService patternService;

	@GetMapping("/cropPattern/getVillages")
	private List<VcodeWbvnameForCropPattern> getVillageCropPattern( HttpSession httpSession) {
		
		String vscode = (String) httpSession.getAttribute("vscode");
		
		System.out.println("vscode----"+vscode);

		List<VcodeWbvnameForCropPattern> result= patternService.getVillageCodeAndName(Integer.parseInt(vscode));
		   System.out.println("result-----------"+result.get(0).getWbvname());
				return result;
	}
	
	 @RequestMapping("/cropPattern/getCropTypes")
	 private List<CropnatureCropidCropname> getCropList(@RequestParam("cropcode")String cropcode){
		 System.out.println("-----------------------");
		 System.out.println(patternService .getCropList(cropcode).get(0).getCropid()+"croppp");
		 return patternService .getCropList(cropcode);
	 }
	 
	 @PostMapping("/cropPattern/getCropNames")
	 private List<CropnatureCropidCropname> getCropNames(){
		 return patternService .getCropNames();
	 }
	 
	 @RequestMapping("/cropPattern/getCropTypesSec")
	 private List<CropnatureCropidCropname> getCropTypesSec(@RequestParam("cropcode")String cropcode){
		 System.out.println(patternService .getCropList(cropcode).get(0).getCropid()+"croppy");
		 return patternService .getCropList(cropcode);
		 
		  
	 }
	
	 
	 @GetMapping("/cropPattern/getVarietyCodeVarietyNameCropname")
	 public List<VarietyCodeVarietyNameCropPattern> getVarietyCodeVarietyName(@RequestParam("cropname") String cropcode) {
		 System.out.println("hiiii");
	     try {
	         int cropId = Integer.parseInt(cropcode);

	         List<VarietyCodeVarietyNameCropPattern> varietyCodeVarietyName = patternService.getVarietyCodeVarietyName(cropId);
	         System.out.println("cropnameCropCode-->" + cropcode);
	         System.out.println("varietyCodeVarietyName-->" + varietyCodeVarietyName.toString());

	         return varietyCodeVarietyName;
	     } catch (NumberFormatException e) {
	         System.err.println("Invalid crop code provided: " + cropcode);
	         return Collections.emptyList(); // Return an empty list or another suitable response
	     } catch (Exception e) {
	         e.printStackTrace();
	         return Collections.emptyList(); // Return an empty list or another suitable response
	     }
	 }
	 
	 @GetMapping("/cropPattern/getVarietyCodeVarietyNameCropnameSec")
	 public List<VarietyCodeVarietyNameCropPattern> getVarietycodeVarietyname(@RequestParam("cropnameSec") String cropcodeSec) {
		    try {
		        if (!cropcodeSec.isEmpty()) {
		            int cropIdSec = Integer.parseInt(cropcodeSec);

		            // Call the service method with the parsed cropIdSec
		            List<VarietyCodeVarietyNameCropPattern> varietyCodeVarietyNameSec = patternService.getVarietyCodeVarietyName(cropIdSec);

		            // Debugging prints
		            System.out.println("cropnameSecCropCode --> " + cropcodeSec);
		            System.out.println("varietyCodeVarietyNameSec --> " + varietyCodeVarietyNameSec.toString());

		            return varietyCodeVarietyNameSec;
		        } else {
		            // Handle the case when cropcodeSec is empty
		            System.out.println("cropnameSecCropCode is empty");
		            return Collections.emptyList(); // Or any appropriate response for an empty input
		        }
		    } catch (NumberFormatException e) {
		        // Handle the case when cropcodeSec is not a valid integer
		        //System.out.println("Invalid cropnameSecCropCode format");
		        return Collections.emptyList(); // Or any appropriate response for an invalid input
		    }
		}

	
	 
	 
	 
	 
	  	
	}
