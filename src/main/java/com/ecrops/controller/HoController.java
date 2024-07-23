package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.entity.HoMandal;
import com.ecrops.entity.HoMandalsMap;
import com.ecrops.projection.HoDisplay;
import com.ecrops.projection.HoMapping;
import com.ecrops.projection.UnMappingProj;
import com.ecrops.service.HoMandalService;

@Controller
public class HoController {

	@Autowired
	private HoMandalService homandalservice;

	@GetMapping("/HoMappingIntf")
	public String HoMapping(HttpSession httpSession, Model model) {
		System.out.println("controller");
		String district = (String) httpSession.getAttribute("dcode");

		List<HoMapping> getMandals = homandalservice.getMandal(Integer.parseInt(district));
		System.out.println("district  "+district);
		

		List<HoMapping> getunmapmandals = homandalservice.getunmapMandal(Integer.parseInt(district));

		List<HoDisplay> getdetails = homandalservice.getMappedDetails(Integer.parseInt(district));

		model.addAttribute("getmandals", getMandals);
		model.addAttribute("getunmapmandals", getunmapmandals);
		model.addAttribute("getdetails", getdetails);
	
		model.addAttribute("add", new HoMandal());
		return "HoMappingIntf";
	}
	
	


	@PostMapping("/HoMappingIntf")
	public String saveHoMapping(@Valid @ModelAttribute("add") HoMandal add , HttpSession httpSession,
			HttpServletRequest httpServletRequest, Model model,RedirectAttributes redirect,BindingResult bindingresult) {

		String district = (String) httpSession.getAttribute("dcode");

		String hmcode = httpServletRequest.getParameter("hmcode");
	
		
		boolean userexists = false;
	
		String smcode = httpServletRequest.getParameter("smcode");
		
		  if(hmcode.equalsIgnoreCase("0") && smcode.equalsIgnoreCase("0")) {
			 // bindingresult.rejectValue("mcode","hmcode","Please Select HeadQuarters and Mandal");
			  redirect.addFlashAttribute("msg","Please select HeadQuarters and Mandal");
			  return "redirect:/HoMappingIntf";
		  }
		  else if(hmcode.equalsIgnoreCase("0")) {
			 // System.out.println("is caleed----------------");
			  bindingresult.rejectValue("mcode","hmcode","Please Select HeadQuarters");
			  redirect.addFlashAttribute("msg","Please select HeadQuarters");
			  return "redirect:/HoMappingIntf";
			  }
		  else if(smcode .equalsIgnoreCase("0")) { 
			  redirect.addFlashAttribute("msg","Please select Mandal");
			 // bindingresult.rejectValue("wbmcode","hmcode","Please Select Mandal");
			 return "redirect:/HoMappingIntf";
		  }
		 
		int addHoDetails = homandalservice.addHoDetails(Integer.parseInt(district), Integer.parseInt(hmcode),
				Integer.parseInt(smcode));
		
		List<HoMapping> usercheck = homandalservice.usercheck(hmcode);
		//System.out.println("usercheck=>" + usercheck.size());

		if (usercheck.size() > 0) {
			userexists = true;
		}
		if (userexists) {
			int update = homandalservice.updatedetails(Integer.parseInt(district), Integer.parseInt(hmcode));
			model.addAttribute("msg", "Record Updated Successfully ");
		} else {
			int insert = homandalservice.insertdetails(Integer.parseInt(district), Integer.parseInt(hmcode));
			model.addAttribute("msg", " Records Added Successfully ");
		}
		List<HoDisplay> getdetails = homandalservice.getMappedDetails(Integer.parseInt(district));

		model.addAttribute("getdetails", getdetails);
		return "HoMappingIntf";
	}

	@GetMapping("/unMappingInf")
	public String UnMap(HttpSession httpSession, Model model) {

		String district = (String) httpSession.getAttribute("dcode");
		
		List<UnMappingProj> unmap = homandalservice.getheadquarter(Integer.parseInt(district));
		model.addAttribute("unmap", unmap);
		return "unMappingInf";
	}

	@PostMapping("/deleteunmap")
	@ResponseBody
	public String deletedata(@ModelAttribute("unmapping") HoMandalsMap unmapping, HttpSession httpSession,
			HttpServletRequest httpServletRequest, @RequestParam("hqcode") Integer hqcode,
			@RequestParam("mcode") Integer mcode, RedirectAttributes redirectAttributes, Model model) {
		
		String district = (String) httpSession.getAttribute("dcode");
		int delete = homandalservice.deletedetails(Integer.parseInt(district), hqcode, mcode);
		System.out.println(delete);
		 redirectAttributes.addFlashAttribute("message", "Successfully Deleted");

		//return "redirect:/unMappingInf";
		 return "Successfully Deleted";

	}
	

}
