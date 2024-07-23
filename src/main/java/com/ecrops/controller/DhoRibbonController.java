package com.ecrops.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DhoRibbonController {

//	@Autowired
//	private ApplicationServices applicationServices;
//
//	@Autowired
//	private VillageRevRepo villageRevRepo;

	@PreAuthorize("hasAuthority('19')")
	@GetMapping("/dhoCropBookingDetails")
	public String getdhoCropBookingDetails() {
		return "dho/DhoCropBookingDetails";
	}

//	@PreAuthorize("hasAuthority('19')")
//	@GetMapping("/normalVsextentBkd")
//	public String getnormalVsextent() {
//		return "NormalAreaVsExtentBooked";
//	}
//
//	@PreAuthorize("hasAuthority('19')")
//	@GetMapping("/VillageSectrList")
//	public String getRbkList() {
//		return "VillageSectrList";
//	}
//
//	//======================CropBookingDetailsDhoIntf===========================//
//		@Autowired CropBookingDetailsDhoIntfPartition cropBookingDetailsDhoIntfPartition;
//		
//		@GetMapping("/cropbDho")
//		public String getcrpDho(Model model, HttpSession session) {
//			String wbemname = (String)session.getAttribute("wbemname");
//			String wbevname = (String)session.getAttribute("wbevname");
//			
//			System.out.println("wbemname=>"+wbemname);
//			System.out.println("wbevname=>"+wbevname);
//			
//			return "CropBookingDetailsDhoIntf";
//	
//		}
//		
//	//======================StateWiseCropBookingDetails==================================//
//		@GetMapping("/statewisecrop")
//		public String getStatewsrc(Model model,HttpSession httpSession) {
//			int dcode=Integer.parseInt((String)httpSession.getAttribute("dcode"));	System.out.println("dcode-->"+httpSession.getAttribute("dcode"));
//			int mcode=Integer.parseInt((String)httpSession.getAttribute("mcode"));System.out.println("mcode-->"+httpSession.getAttribute("mcode"));
//			
//			List<Villname>  list = villageRevRepo.getVillages(dcode, mcode);
//			System.out.println("list---->"+list.size());
//			model.addAttribute("list", list);
//			
//			//String village = masterFunctions.masterFunction(cr_vcode, "wbvillage");
//			model.addAttribute("irrigationsList", applicationServices.getWsrcdesc());
//	 
//			return "Rep_StateWiseCropIrriAbsIntf";
//			
//		}
	
	//=====================================Abstract report on extent booked====================================//
	
//	@PreAuthorize("hasAuthority('19')")
//	
//	public class CropwiseExtBookedRepController {
//		@Autowired
//		private ActiveseasonFcwebServiceImpl activeseasonFcwebService;
//
//		@Autowired
//		private CropgroupsFCWRServiceImpl cropgroupsFCWRService;
//
//		@Autowired
//		private CropnamesFCWRServiceImpl cropnamesFCWRService;
//
//		@Autowired
//		private CropwiseExtBookedServiceImpl cropwiseExtBookedService;
//
//		@GetMapping("/cropwiseextbookedrep")
//		public String CropwiseExtBooked(Model model, HttpServletRequest httpServletRequest) {
//			model.addAttribute("cropyears", activeseasonFcwebService.findAll());
//			model.addAttribute("cropgroups", cropgroupsFCWRService.findAll());
//			model.addAttribute("repgrop", new CropgroupsFCWR());
//			return "cropwiseextbookedrep";
//		}
//
//		@GetMapping("/crop-report/{grpcode}")
//		@ResponseBody
//		public List<CropnamesFCWR> findAllVi(@PathVariable("grpcode") int grpcode, Model model) {
//			System.out.println("Villages----->" + cropnamesFCWRService.findAll(grpcode));
//			List<CropnamesFCWR> cropnames = cropnamesFCWRService.findAll(grpcode);
//			return cropnames;
//
//		}
////
////		@RequestMapping("/cropreport")
////		public String findAllV(HttpSession httpSession, @RequestParam("cropyear") String cr_year, Model model,
////				@RequestParam("crpgrp") String cropgroup) {
//			@GetMapping("/cropreport")
//			public String findAllV( ) {
//			// System.out.println("Villages----->"+cr_year);
//			System.out.println("************************************************");
//			//String dcode = (String) httpSession.getAttribute("dcode");
//			//System.out.println("dcode---------->" + dcode);
//			//System.out.println("cropgroup--------->" + cropgroup);
//
//			//List<com.ecrops.entity.CropwiseExtBooked> cropreport = cropwiseExtBookedService
//				//	.getCropwise(Integer.parseInt(dcode), cr_year);
//			//model.addAttribute("cropreport", cropreport);
//
//			//model.addAttribute("theDate", LocalDate.now());
//			return "cropwiseextbookedrep";
//
//		}
//
//	}

}
