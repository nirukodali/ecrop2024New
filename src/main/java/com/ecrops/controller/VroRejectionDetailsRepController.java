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
import com.ecrops.entity.ActiveseasonFVroRej;
import com.ecrops.entity.VroRejDetails;
import com.ecrops.repo.ActiveseasonFVroRejRepository;
import com.ecrops.repo.VroRejectionDetailsRepo;

@Controller
public class VroRejectionDetailsRepController {
	
	@Autowired
	ActiveseasonFVroRejRepository activeseasonFVroRejRepository; 
	
	@Autowired
	VroRejectionDetailsRepo vroRejectionDetailsRepo; 
	
	@PreAuthorize("hasAuthority('30')")
	@GetMapping("/vrorej")
    public String vroRej(Model theModel) {
		List<ActiveseasonFVroRej> li=activeseasonFVroRejRepository.getCropyear();
		System.out.println("*******"+li);
        
        theModel.addAttribute("cropyears", activeseasonFVroRejRepository.getCropyear());
		theModel.addAttribute("theDate", LocalDate.now());
        return "vro/vrorej";
    }
	
	@PreAuthorize("hasAuthority('30')")
	@PostMapping("/vrorej-report")
	
	public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year,Model model)
	{
	  	
		System.out.println("************************************************"+cr_year);
		
		String vcode=(String) httpSession.getAttribute("vscode").toString();
		System.out.println("vcode---------->"+vcode);
		
		List<VroRejDetails> cropreport=null;
		try {
		   

		        cropreport = vroRejectionDetailsRepo.getCropwise(Integer.parseInt(vcode), cr_year);
		    } 
		 catch (Exception e) {
		    e.printStackTrace();
		}

		 List<ActiveseasonFVroRej> li=activeseasonFVroRejRepository.getCropyear();
	        
			model.addAttribute("cropyears", activeseasonFVroRejRepository.getCropyear());
			model.addAttribute("theDate", LocalDate.now());
		model.addAttribute("crpreport", cropreport);
		
		
	    return "vro/vrorej";   
		
	} 
	
	

}
