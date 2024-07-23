package com.ecrops.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.EnableObjectLandPojo;

import com.ecrops.entity.WbVillageEntity;
import com.ecrops.entity.WbVillageMastEntity;

import com.ecrops.repo.WbvillagesRepository;
import com.ecrops.service.EnableObjLandService;

@Controller
public class EnableLandDetailsCntrl {

	@Autowired
	private EnableObjLandService enableObjLandService;

	@PreAuthorize("hasAuthority('44') ||  hasAuthority('9')  ||  hasAuthority('2') || hasAuthority('5')")
	@GetMapping("/enableLandController")
	public String getSuperCheckUserEntry(Model model, HttpSession httpSession) {
		List<EnableObjectLandPojo> villageData = enableObjLandService.getEnablelandDetailsPoj(httpSession);
		model.addAttribute("resultList", villageData);
		return "mro/objlandDetails";
	}

	@GetMapping("/accept/{id}")
	public String accept(@PathVariable(value = "id") int id, Model model, RedirectAttributes ra, HttpSession session) {
		ra.addFlashAttribute("msg", enableObjLandService.updatingData(id, session));
		return "redirect:/enableLandController";
	}

	@GetMapping("/reject")
	public String rejections(Model model, HttpServletRequest request, RedirectAttributes ra, HttpSession session) {
		ra.addFlashAttribute("msg", enableObjLandService.updatingRejectedData(request, session));
		return "redirect:/enableLandController";
	}

//	@GetMapping("/reject/{id}")
//	public String rejection(@PathVariable ( value = "id") int id, Model model,HttpServletRequest request,RedirectAttributes ra) {
//	 
//	 // get employee from the service
//		Optional<EnableObjLand> obj=repo.findById(id);
//		if (obj.isPresent()) {
//		    EnableObjLand enableObjLand = obj.get();
//		    enableObjLand.setMroSts("R");
//		 String s=   request.getParameter("remark");
//		    System.out.println(s);
//		    enableObjLand.setMroUpdSts(new Timestamp(System.currentTimeMillis()));
//		    repo.save(enableObjLand);
//		    ra.addFlashAttribute("msg" , "saved successfully");
//		}
//	
//		 return "redirect:/enableLandController";
//	}
//	

}
