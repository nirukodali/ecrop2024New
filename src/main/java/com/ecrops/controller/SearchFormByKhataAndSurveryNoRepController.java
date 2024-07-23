package com.ecrops.controller;

import java.math.BigDecimal; 
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.entity.SearchFormByKhataAndSurveryNo;
import com.ecrops.entity.Villsec_rev_v;
import com.ecrops.repo.ActiveseasonFsfbkasnoRepo;
import com.ecrops.repo.SearchFormByKhataAndSurveryNoRepo;
import com.ecrops.repo.Villsec_rev_vRepo;
import com.ecrops.repo.Wbvillage_mstsfbkasnoRepo;

@Controller
public class SearchFormByKhataAndSurveryNoRepController {
	
	@Autowired
	ActiveseasonFsfbkasnoRepo activeseasonFsfbkasnoRepo; 
	

	@Autowired
	Villsec_rev_vRepo villsec_rev_vRepo; 
	
	@Autowired
	SearchFormByKhataAndSurveryNoRepo searchFormByKhataAndSurveryNoRepo; 
	
	@Autowired
	Wbvillage_mstsfbkasnoRepo wbvillage_mstsfbkasnoRepo; 
	
	@PreAuthorize("hasAuthority('30')")
	@GetMapping("/searchformbykhasno")
    public String searchformbykhsno(Model theModel,HttpSession session) {
		String vcode=(String)session.getAttribute("vcode").toString();
        System.out.println("vcode----------->"+vcode);
        
        
        
        theModel.addAttribute("cropyears", activeseasonFsfbkasnoRepo.getCropyear());
        
        List<Villsec_rev_v> lt=null;
		try {
			lt = villsec_rev_vRepo.getVillagenames(Integer.parseInt(vcode));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        theModel.addAttribute("villages", villsec_rev_vRepo.getVillagenames(Integer.parseInt(vcode)));
        
        for(Villsec_rev_v villsec_rev_v:lt) {
        	System.out.println(villsec_rev_v.toString());
        }
        
		theModel.addAttribute("theDate", LocalDate.now());
        return "vro/searchformbykhasno";
    }

	@PreAuthorize("hasAuthority('30')")
	@PostMapping("/searchformbykhataandsurveyno-report")
	public String findAllV(HttpSession httpSession,Model model,@RequestParam("cropyear") String cr_year,
																@RequestParam("villagename") String villagename,
																@RequestParam("searchbyname") String searchbyname,HttpServletRequest httpsr)
																
	{
		String vcode1=(String)httpSession.getAttribute("vcode").toString();
        System.out.println("vcode----------->"+vcode1);
        
        String kh_no= httpsr.getParameter("kh_no");
        System.out.println("kh_no---------"+kh_no);
        String survey= httpsr.getParameter("survey");
        System.out.println("survey---------"+survey);
        
        //theModel.addAttribute("cropyears", activeseasonFsfbkasnoRepo.getCropyear());
        
       
		  String[] parts = cr_year.split(",");
		    if (parts.length > 0) {
		        cr_year = parts[0].trim();
		    }
		    
		    Integer dcode= Integer.parseInt(httpSession.getAttribute("dcode").toString());
			System.out.println("dcode---------->"+dcode);
			
		System.out.println("************************************************"+cr_year);
		
		Integer wbdcode= Integer.parseInt(httpSession.getAttribute("wbdcode").toString());
		System.out.println("wbdcode---------->"+wbdcode);
		String mcode=(String) httpSession.getAttribute("mcode").toString();
		System.out.println("mcode---------->"+mcode);
		String wbemname=(String) httpSession.getAttribute("wbemname").toString();
		System.out.println("wbemname---------->"+wbemname);
	   int wbmcode=wbvillage_mstsfbkasnoRepo.findDistinctWbmcodeByMcode(Integer.parseInt(mcode));
		 
		List<SearchFormByKhataAndSurveryNo> cropreport=null;
		
		
		try {
			BigDecimal cr_mand_code = null;
			int cr_vcode = 0;
			cr_vcode= Integer.parseInt(httpSession.getAttribute("wbvcode").toString());
			System.out.println("cr_vcode---------->"+cr_vcode);
		//cropreport = searchFormByKhataAndSurveryNoRepo.getCropwise(Integer.parseInt(dcode),wbmcode,cr_vcode,cr_year);
				cropreport = searchFormByKhataAndSurveryNoRepo.getCropwise(dcode,
						                                                   mcode,
						                                                   searchbyname,
						                                                    wbdcode.toString(),
						                                                    wbmcode,
						                                                    cr_vcode,
						                                                    cr_year,vcode1,kh_no,survey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		

			String vcode=(String)httpSession.getAttribute("vcode").toString();
	        System.out.println("vcode----------->"+vcode);
	        
	        
	        
	        model.addAttribute("cropyears", activeseasonFsfbkasnoRepo.getCropyear());
	        
	        List<Villsec_rev_v> lt=null;
			try {
				lt = villsec_rev_vRepo.getVillagenames(Integer.parseInt(vcode));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
	        
			
	        
			model.addAttribute("villname", lt.get(0).getWbvname());
			model.addAttribute("villages", lt);
	        model.addAttribute("mname", wbemname);
	        model.addAttribute("theDate", LocalDate.now());
	        model.addAttribute("kh_no", kh_no);
		model.addAttribute("crpreprt", cropreport);
		
		
	    return "vro/searchformbykhasno";   
		
	} 
	
	
}
