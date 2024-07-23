//package com.ecrops.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.ecrops.config.RegularExpressionclassMethod;
//import com.ecrops.entity.HoMandal;
//import com.ecrops.entity.WbMaster;
//import com.ecrops.service.JioInsertService;
//import com.ecrops.service.impl.ActiveseasonServiceImpl;
//import com.ecrops.service.impl.DistrictServiceImpl;
//import com.ecrops.service.impl.MandalServiceImpl;
//import com.ecrops.service.impl.VillageServiceImpl;
//
//@Controller
//public class GeoController {
//
//	@Autowired
//	private ActiveseasonServiceImpl activeseasonService;
//
//	@Autowired
//	private DistrictServiceImpl districtService;
//
//	@Autowired
//	private MandalServiceImpl mandalService;
//
//	@Autowired
//	private VillageServiceImpl villageService;
//
//	@Autowired
//	private JioInsertService jioInsertService;
//
//	@GetMapping("/geoupdate/{dcode}/{mcode}")
//	@ResponseBody
//	public List<WbMaster> findAllVillages(@PathVariable("dcode") int dcode, @PathVariable("mcode") int mcode) {
////	  	System.out.println("Villages----->"+villageService.getwbVillages(dcode, mcode));
////		List<WbMaster> villages=villageService.findAll(dcode, mcode);
//		List<WbMaster> wbvillages = villageService.getnotjiovcode(dcode, mcode);
//		return wbvillages;
//
//	}
//
//	@GetMapping("/geoupdate/{dcode}")
//	@ResponseBody
//	public List<HoMandal> findAllMandals(@PathVariable("dcode") int dcode) {
//		// System.out.println("MANDALS----->"+mandalService.findAll(dcode));
//		List<HoMandal> mandals = mandalService.findAll(dcode);
//		//System.out.println("mandals" + mandals.toString());
//		return mandals;
//
//	}
//
//	@GetMapping("/geoupdate")
//	public String getseasondistrict(Model theModel) {
//		// System.out.println("aciveseason------>"+activeseasonService.findAll());
//		theModel.addAttribute("activeseasons", activeseasonService.findAll());
//		theModel.addAttribute("districts", districtService.findAll());
//		return "geoupdate";
//	}
//
//	@PostMapping("/jioflagupdate")
//	public String jioupdate(HttpServletRequest httpServletRequest, Model model,RedirectAttributes redirect) {
//		String dcode = httpServletRequest.getParameter("dcode");
//		String mcode = httpServletRequest.getParameter("mandal");
//		String vcode = httpServletRequest.getParameter("village");
//		System.out.println("dcode: " + dcode);
//		System.out.println("mcode: " + mcode);
//		System.out.println("vcode: " + vcode);
//		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
//		boolean val1 = regexpmethod.districtCode(dcode);
//		boolean val2 = regexpmethod.mandalCode(mcode);
//		boolean val3 = regexpmethod.villageCode(vcode);
//		System.out.println("val1: " + val1);
//		System.out.println("val2: " + val2);
//		System.out.println("val3: " + val3);
//		if(val1 && val2 && val3) {
//			jioInsertService.updatejio(Integer.parseInt(dcode), Integer.parseInt(mcode),
//					Integer.parseInt(vcode));
//			
//			jioInsertService.savel(dcode, mcode, vcode);
//			redirect.addFlashAttribute("msg","Updated Successfully");
//			 return "redirect:/geoupdate";
//		}else {
//			redirect.addFlashAttribute("mg","Please Select All Details");
//			 return "redirect:/geoupdate";
//		}
//		
//	}
//}
