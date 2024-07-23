package com.ecrops.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ecrops.entity.ActiveseasonFDWSRFAC;
import com.ecrops.entity.DistwiseStatusRepForAllCrops;
import com.ecrops.repo.ActiveseasonFDWSRFACRepository;
import com.ecrops.repo.DistwiseStatusRepForAllCropsRepo;
@Controller
public class DistwiseStatusRepForAllCropsController {
	@Autowired
	DistwiseStatusRepForAllCropsRepo distwiseStatusRepForAllCropsRepo;
	
	@Autowired
	ActiveseasonFDWSRFACRepository activeseasonFDWSRFACRepository;
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/distwisestatusrepforallcrops")
	public String Distwisestatusrepforallcrop(Model theModel) {
		List<ActiveseasonFDWSRFAC> cropyr=activeseasonFDWSRFACRepository.getCropyear();
		System.out.println(cropyr);
        theModel.addAttribute("cropyears", activeseasonFDWSRFACRepository.getCropyear());
		 theModel.addAttribute("theDate", LocalDate.now());
		return "ddh/distwisestatusrepforallcrops";
	}
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/cropReport/{cropyrid}")
	@ResponseBody
	public List<DistwiseStatusRepForAllCrops> findAllV(@PathVariable("cropyrid") String cropyear,Model theModel,RedirectAttributes redirectAttributes){
	
		List<DistwiseStatusRepForAllCrops> cropreport=null;
		try {
			cropreport = distwiseStatusRepForAllCropsRepo.getCropwise(cropyear);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			theModel.addAttribute("message", "table relation does not exist!");
			 return cropreport;   
		}
		
		System.out.println("cropreport----->"+cropreport);
		 for (DistwiseStatusRepForAllCrops distwiseStatusRepForAllCrops : cropreport) {
	           System.out.println("distwiseStatusRepForAllCrops: " + distwiseStatusRepForAllCrops.getWbdname());
	     }
		 if (cropreport.isEmpty()){
			 redirectAttributes.addFlashAttribute("message", "table data not found!");
	 
		 }
		return cropreport;   
		
	}

	
	


}
