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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.Unsetteled_UnsurveyedDto;
import com.ecrops.dto.UnsurveydDataDto;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.Cr_BookingEntity;
import com.ecrops.entity.Cultivator;
import com.ecrops.entity.NonWebLandData;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.entity.UnsDownloadDetails;
import com.ecrops.entity.UnsdownloadDetailsEntity;
import com.ecrops.entity.Unsurveyed_UnsettledDdetEntity;
import com.ecrops.entity.WbMaster;
import com.ecrops.repo.Cr_booking_PartitionRepo;
import com.ecrops.repo.NonWebLandRepo;
import com.ecrops.repo.NonWebReasonsRepo;
import com.ecrops.repo.Patt_Mst_NonWebLandRepo;
import com.ecrops.service.DynamicTableService;
import com.ecrops.service.NonWebLandDataEntryService;
import com.ecrops.service.WbMasterService;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;
import com.ecrops.service.impl.UnsdownloadDetailsServiceImpl;
import com.ecrops.util.ECropUtility;

@Controller
public class UnsurveyedAndUnsetteldController {
	@Autowired
	private NonWebLandData cr_booking_nwb;

	@Autowired
	private UnsdownloadDetailsServiceImpl unsdownloadDetailsServiceImpl;

	@Autowired
	private DynamicTableService dynamicTableService;

//	@Autowired
//	private Unsurveyed_UnsettledDdetEntity unsurveyed_UnsettledDdetEntity;
	
	@Autowired
	WbMasterService wbMasterService;

	@Autowired
	private ActiveSeasonServiceImpl activeSeasonService;

	@Autowired
	private Cr_booking_PartitionRepo cr_booking_PartitionRepo;

	@Autowired
	private UnsDownloadDetails unsDownloadDetails;

@Autowired 
	
	private RegularExpressionclassMethod regularExpressionclassMethod;
	
	@PreAuthorize("hasAuthority('2')")
	@GetMapping("/unsurveyed")
	public String unsurvey(HttpSession httpSession, Model model, RedirectAttributes redirectAttributes) {
		List<ActiveSeason> crandseason = activeSeasonService.listAll();

		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String curren_Season = cropYearActiveSeasonList.get(0).getSeasonvalue();
		String a[] = curren_Season.split("@");
		String season = a[0];
		String year = a[1];
		if (season.equalsIgnoreCase("R")) {
			curren_Season = "Rabi";
		} else if (season.equalsIgnoreCase("K")) {
			curren_Season = "Kharif";
		} else {
			curren_Season = "Summer";
		}
		String cropYear = curren_Season + year;
		model.addAttribute("cropYear", cropYear);

		int dcode = Integer.parseInt((String) httpSession.getAttribute("dcode"));
		int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		model.addAttribute("unsurveydDataDto", new UnsurveydDataDto());
		List<WbMaster> village = wbMasterService.findUnsurveyedVilageList(dcode, mcode);
		model.addAttribute("village", village);
		model.addAttribute("crandseason", crandseason);
		String message = (String) redirectAttributes.getFlashAttributes().get("message");
		if (message != null) {
			model.addAttribute("message", message);
		}
		return "mro/unsurveyeddata";
	}

	@PreAuthorize("hasAuthority('2')")
	@PostMapping(path = "/UnsurveyedSearch")
	public String unsurveySearch(@Valid @ModelAttribute("unsurveydDataDto") UnsurveydDataDto unsurveydDataDto,
			BindingResult bindingResult, Model model, HttpSession httpSession, Cultivator cultivator,
			@RequestParam(value = "selectedVillageCode", required = false) Integer selectedVillageCode,
			RedirectAttributes redirectAttributes) {
		String surveyno = unsurveydDataDto.getSurvyNo();
		if( !regularExpressionclassMethod.checkSuveyNo(surveyno) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/unsurveyed";
		}

		List<ActiveSeason> crandseason = activeSeasonService.listAll();

		int dcode = Integer.parseInt((String) httpSession.getAttribute("dcode"));
		int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		String cr_season = (String) httpSession.getAttribute("seasonActive");

		Integer cropyear = (Integer) httpSession.getAttribute("ACTIVEYEAR");
		Integer activeYear = (Integer) httpSession.getAttribute("ACTIVEYEAR");

		cultivator.setCr_year(ECropUtility.sessionData(httpSession).getCropYear());
		cultivator.setCr_season(ECropUtility.sessionData(httpSession).getCurrentSeason());
		String partKey="";
		if (wbdcode <= 9) {
			
			partKey = cr_season + "0"+wbdcode + cropyear;
		} else {
			partKey = cr_season +wbdcode + cropyear;
		}
		

		String tab = "cr_booking_nwb" ;
		if (activeYear.equals(cropyear)) {
			tab = "ecrop" + activeYear + "." + tab;

		}
		Integer wbvcode = (Integer) httpSession.getAttribute("wbvcode");
		List<WbMaster> village = wbMasterService.findUnsurveyedVilageList(dcode, mcode);
		model.addAttribute("village", village);
		model.addAttribute("selectedVillageCode", selectedVillageCode);
		model.addAttribute("psurveyno", surveyno);
		model.addAttribute("crandseason", crandseason);
		if (bindingResult.hasErrors()) {
			System.out.println("bindig result called");
			return "mro/unsurveyeddata";
		}
		System.out.println("selectedVillageCode"+selectedVillageCode);
		 
			String srno = dynamicTableService.findSurveyNo(tab, selectedVillageCode,surveyno);
			//surveyno="US-"+surveyno;
			String psrno="US-"+surveyno;
			if (psrno.equalsIgnoreCase(srno)|| surveyno.equalsIgnoreCase(srno) ) {
				String msg = "Survey number already exists";
				redirectAttributes.addFlashAttribute("message", msg);
				return "redirect:/unsurveyed";
			} 
			
			else {
				model.addAttribute("unsetteled_UnsurveyedDto", new Unsetteled_UnsurveyedDto());
				return "mro/settledUnsettledEntry";
			} 
				
				
		}

	
	@PreAuthorize("hasAuthority('2')")
	@PostMapping(path = "/saveUnsurveyedData")
	public String postdata(
			@Valid @ModelAttribute("unsetteled_UnsurveyedDto") Unsetteled_UnsurveyedDto unsetteled_UnsurveyedDto,
			BindingResult bindingResult, Model model, HttpSession httpSession,
			@RequestParam("cultfarmerName") String cultfarmerName,
			@RequestParam("cultfatherName") String cultfatherName,
			@RequestParam(value = "totext", required = false) Double totext,
			@RequestParam(value = "cultext", required = false) Double cultext,
			@RequestParam("farmerUid") String farmerUid, @RequestParam("objGender") String objGender,
			@RequestParam(value = "objMobileno", required = false) Long objMobileno,
			@RequestParam("objcat") String objcat, @RequestParam("surveyNo") String surveyNo, Cultivator cultivator,
			HttpServletRequest httpServletRequest,
			@RequestParam(value = "selectedVillageCode", required = false) Integer selectedVillageCode,
			RedirectAttributes redirectAttributes) {
		System.out.println("surveyNo"+surveyNo);
		
		if( !regularExpressionclassMethod.checkSuveyNo(surveyNo) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/UnsurveyedSearch";
		}
		
		
//		if( !regularExpressionclassMethod.checkKhataNo(kh_no) )
//		{
//			redirectAttributes.addFlashAttribute("message","Improper Data Found");
//			return "redirect:/surveyno";
//		}
		if( !regularExpressionclassMethod.checkAadharNumber(farmerUid) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/UnsurveyedSearch";
		}
		if( !regularExpressionclassMethod.checkMobileNumber(objMobileno.toString()) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/UnsurveyedSearch";
		}
		if( !regularExpressionclassMethod.checkExtent(totext.toString()) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/UnsurveyedSearch";
		}
		if( !regularExpressionclassMethod.checkExtent(cultext.toString()) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/UnsurveyedSearch";
		}
		
	
		
		String ipaddress = httpServletRequest.getRemoteAddr();
		Long mobileNo = (objMobileno);
		int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		String cr_season = (String) httpSession.getAttribute("seasonActive");
		Integer cropyear = (Integer) httpSession.getAttribute("ACTIVEYEAR");
		Integer wbmcode = (Integer) httpSession.getAttribute("wbmcode");
		
		
		Integer wbvcode = selectedVillageCode;
		
		String partKey="";
		
if (wbdcode <= 9) {
			
			partKey = cr_season + "0"+wbdcode + cropyear;
		} else {
			partKey = cr_season +wbdcode + cropyear;
		}
		
	//	String partKey = cr_season + wbdcode + cropyear;
		int dcode=Integer.parseInt((String) httpSession.getAttribute("dcode"));
//		int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		
		//System.out.println(dcode+"--------------------------------------"+mcode);

//		if (bindingResult.hasErrors()) {
//			return "mro/settledUnsettledEntry";
//		} else {
//			model.addAttribute("unsetteled_UnsurveyedDto", new Unsetteled_UnsurveyedDto());
//		}

		try {
//			unsdownloadDetailsServiceImpl.saveUnsurveyed_UnsettledDdet(unsurveyed_UnsettledDdetEntity, dcode, mcode,
//					mobileNo, wbdcode, wbmcode, cropyear, cr_season, farmerUid, cultfarmerName, cultfatherName,
//					surveyNo, totext, cultext, objGender, wbvcode, objcat);

			unsdownloadDetailsServiceImpl.saveCr_booking_nwb(cr_booking_nwb, wbdcode, wbvcode, wbmcode, partKey,
					"US-"+surveyNo, farmerUid, cr_season, objGender, totext, mobileNo, cultfarmerName, cultfatherName,
					cultext, cropyear,objcat,dcode,mcode);

			unsdownloadDetailsServiceImpl.saveUnsDownloadDetails(unsDownloadDetails, cropyear, ipaddress, mcode,
					partKey, mcode, cr_season, wbvcode);
			System.out.println("data inserted");

			model.addAttribute("message", "Data successfully added");
			return "mro/unsurveyeddata";

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("data is not inserted");
			model.addAttribute("message", "Data  is not  inserted");
			return "mro/unsurveyeddata";
		}
	}

}
