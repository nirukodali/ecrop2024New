package com.ecrops.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.SelectionOfSurvyPojo;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.VillageSecRevRepository;
import com.ecrops.service.SelectionOfSurveyNoSurvice;
import com.ecrops.util.ECropUtility;
import com.ecrops.validations.AllocationValidations;

@Controller
public class SelOfSrvyNoController {
	@Autowired
	private ActiveSeasonRepository cropYearRepo;

	@Autowired
	private VillageSecRevRepository villageRevenueRepo;

	@Autowired
	private SelectionOfSurveyNoSurvice selectionService;

	@Autowired
	private DatabaseRepo databaseRepo;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/selectionOfSurveyNo")
	public String SelectionOfSrveyNO(HttpSession session, HttpServletRequest httpServelHttpServletRequest,
			Model model) {

		String vcode = ECropUtility.sessionData(session).getVsCode();
		
		List<ActiveSeasonProjection> activeSeason = cropYearRepo.getActiveSeason();
		List<ActiveSeasonProjection> rbk = villageRevenueRepo.getVillageListByRbk(Integer.parseInt(vcode));

		model.addAttribute("activeseason", activeSeason);
		model.addAttribute("rbk", rbk);
		return "rbkroles/SelectionOfSuvrveyNo";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/getSurveyNoDetails")
	public String getDetails(HttpServletRequest request, HttpSession session, Model model) {
		String sesdcode = session.getAttribute("dcode").toString();
		String sesmcode = session.getAttribute("mcode").toString();
		String user = session.getAttribute("userid").toString();	
		String sesvcode = ECropUtility.sessionData(session).getVsCode();

		String crpses = request.getParameter("crYear");
		String cropyear = crpses.split("@")[1];
		String season = crpses.split("@")[0];
		
		RegularExpressionclassMethod validation = new RegularExpressionclassMethod();
		
        if(!validation.season(season) || !validation.year(cropyear) ){
        	return "ErrorPage";
		}
		
		if(request.getParameter("searchParam")==null || request.getParameter("searchParam").isEmpty() ) {
			System.out.println("search param is null");
			return "ErrorPage";
		}else {
			if(request.getParameter("searchParam").equals("1")){
				Boolean checkString=validation.checkstring(request.getParameter("surveyno"));
				System.out.println(checkString);
			if(!validation.checkSuveyNo(request.getParameter("surveyno")) || checkString) {
				System.out.println("search param");
				return "ErrorPage";
			}
			}else {
				Boolean checkString=validation.checkstring(request.getParameter("fromKhno"));
				if(!validation.checkKhataNo(request.getParameter("fromKhno")) || checkString) {
					return "ErrorPage";
				}
			}
		}
		List<SelectionOfSurvyPojo> data = null;
//		try {
			data = selectionService.getData(request, session);
//		}
//		catch(Exception e) {
//			System.out.println(e);
//			return "ErrorPage";
//		}
		String vcode = request.getParameter("vcode");
		System.out.println("VCODE"+vcode);
		String activeYear = cropyear;

		model.addAttribute("pattadharDetails", data);
		model.addAttribute("sesdcode", sesdcode);
		model.addAttribute("sesmcode", sesmcode);
		model.addAttribute("cropyear", cropyear);
		model.addAttribute("season", season);
		model.addAttribute("vcode", vcode);
		if (data.size() > 0) {
			model.addAttribute("pwbdcode", data.get(0).getCrDcode());
			model.addAttribute("pwbmcode", data.get(0).getCrMcode());
		}
		return "rbkroles/selectionOfSurveyNoDetails";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/saveSrnoinCrbooking")
	public String saveSelection(HttpServletRequest httpServletRequest, HttpSession httpSession,
			RedirectAttributes redirectAttributes) throws Exception {

		String cropyear = httpServletRequest.getParameter("pcropyear");
		String season = httpServletRequest.getParameter("pseason");
		String vcode = httpServletRequest.getParameter("reqvcode");

		String empCode = httpServletRequest.getParameter("empCode");
		String partkey = httpServletRequest.getParameter("ppartkey");
		String pwbdcode = httpServletRequest.getParameter("pwbdcode");
		String pwbmcode = httpServletRequest.getParameter("pwbmcode");
		String userid = httpSession.getAttribute("userid").toString();
		String sesdcode = httpSession.getAttribute("dcode").toString();
		String sesmcode = httpSession.getAttribute("mcode").toString();
		System.out.println(httpServletRequest.getParameter("dataSrcList"));
		String[] dataSrcArr = httpServletRequest.getParameter("dataSrcList").split(",");
		//System.out.println("dataSrcArr----------------------->"+Arrays.toString(dataSrcArr));
		String[] recordsArr = httpServletRequest.getParameter("bkIdLst").split(",");
		//System.out.println("recordsArr----------------------->"+Arrays.toString(recordsArr));

//		String activeYear = cropyear;
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
		String activeSeason= (String) httpSession.getAttribute("seasonActive");
		String crpyear = cropyear, crpseason = season, user = empCode;
		String[] villCodes = httpServletRequest.getParameter("villcodeLst").split(",");
		String[] regNoArr = httpServletRequest.getParameter("regNoLst").split(",");
		String[] sjointArr = httpServletRequest.getParameter("sjoinLst").split(",");
		String[] khataNos = httpServletRequest.getParameter("KhathNoList").split(",");
		String ipaddress = httpServletRequest.getRemoteAddr();
		String[] WholesrArr = httpServletRequest.getParameter("wholeSurveyNoList").split(",");
		String[] surveyNos = httpServletRequest.getParameter("SurveyNoList").split(",");
		String[] totExts = httpServletRequest.getParameter("totExtentLst").split(","); 
		String[] occExts = httpServletRequest.getParameter("ocExtentLst").split(",");
		
		AllocationValidations validation = new AllocationValidations();
		RegularExpressionclassMethod regular = new RegularExpressionclassMethod();
		
		
//		  if(!regular.year(cropyear)|| !regular.season(season)|| !validation.isDigitPattern(pwbdcode.trim())
//				  || !validation.isDigitPattern(pwbmcode.trim()) || !validation.isDigitPattern(sesdcode.trim()) || !validation.isDigitPattern(sesmcode.trim())  
//				 )  {
//			 
//			  return "ErrorPage";
//		  }
//		  for(int i=0;i<recordsArr.length;i++) {
//			  if(!validation.isDigitPattern(recordsArr[i])|| !validation.oneAlpha(dataSrcArr[i]) || 
//					  !validation.isDigitPattern(villCodes[i]) || !regular.checkKhataNo(khataNos[i]) || !regular.checkSuveyNo(surveyNos[i])) {
//				  System.out.println("true");
//				  return "ErrorPage";
//		  }
//		  }

		int count = databaseRepo.insertCrbkFromPattadar(dataSrcArr, recordsArr, activeYear, crpyear, crpseason,
				pwbdcode, pwbmcode, villCodes, regNoArr, sjointArr, khataNos, ipaddress, userid, WholesrArr, surveyNos,
				totExts, occExts,activeSeason);
		if(count>0) {
		redirectAttributes.addFlashAttribute("msg", count+"  Records are successfully inserted");
		}else {
			redirectAttributes.addFlashAttribute("msg", "no records are inserted");
		}
		return "redirect:/selectionOfSurveyNo";
	}

}
