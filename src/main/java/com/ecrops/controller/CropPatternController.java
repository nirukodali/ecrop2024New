package com.ecrops.controller;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.crop.response.CropnatureCropidCropname;
import com.ecrops.dto.crop.response.YearCropPattern;
import com.ecrops.entity.crop.CropPatternDetailsEntity;
import com.ecrops.repo.crop.CropPatternRepository;
import com.ecrops.repo.crop.CropPatternService;


@Controller
public class CropPatternController {

	@Autowired
	private CropPatternRepository patternRepository;

	@Autowired
	private CropPatternService patternService;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/cropPattern")
	public String getCropPattern(Model model) {
		List<YearCropPattern> activeSeasons = patternRepository.getCropYear();
		model.addAttribute("activeSeasons", activeSeasons);
		return "rbkroles/socialAuditCropPattern";

	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/getcroppatterndata")
	public String cropPatternDetails(@RequestParam("vcode") String vcode,@RequestParam("cropyear") String seasonwithcropyear, @RequestParam("surveyno") String surveyno,
			@RequestParam("khno_bkid") String khno_bkid,@RequestParam("aadharno") String aadharno,
			@RequestParam("appName") String appName,@RequestParam("appMob") String appMob,
			@RequestParam("appId") String appId, HttpSession httpSession, Model model) {

		System.out.println("surveyno---" + surveyno);
		System.out.println("vcode---" + vcode);
		System.out.println("khno_bkid---" + khno_bkid);
		System.out.println("aadharno---" + aadharno);
		System.out.println("appName---" + appName);
		System.out.println("appMob---" + appMob);
		System.out.println("appId---" + appId);
		RegularExpressionclassMethod method= new RegularExpressionclassMethod();
//		boolean valid= method.checkstring(aadharno);
//		boolean valid2= method.checkstring(khno_bkid);
//		boolean valid3= method.checkstring(surveyno);
		boolean valid4= method.checkstring(appName);
		boolean valid5= method.checkstring(appMob);
		boolean valid6= method.checkstring(appId);
		httpSession.setAttribute("vcode", vcode);
		String [] seasoncropyear=seasonwithcropyear.split("@");
	String season=	seasoncropyear[0];
	int cropyear= Integer.parseInt(seasoncropyear[1]);
	
	System.out.println("season--->"+season);
	System.out.println("cropyear--->"+cropyear);

		int wbdcode = (int) httpSession.getAttribute("wbdcode");

		List<Object[]> cropidCropnameData = patternRepository.getCropidCropname();
		List<CropnatureCropidCropname> setData = new ArrayList<>();
		for (Object[] ob : cropidCropnameData) {
			int cropid = (int) ob[0];
			String cropname = (String) ob[1];

			CropnatureCropidCropname cropnatureDetails = new CropnatureCropidCropname();
			cropnatureDetails.setCropid(cropid);
			cropnatureDetails.setCropname(cropname);

			setData.add(cropnatureDetails);
		}
		model.addAttribute("cropidCropname", setData);

		List<CropPatternDetailsEntity> resultList=null;
		
		try {
			if (vcode != null && !vcode.isEmpty()) {
//				if( valid4 && valid5 && valid6) {
//					valid && valid2 && valid3 &&
				resultList = patternService.getCropViewDetails(String.valueOf(wbdcode), Integer.parseInt(vcode), surveyno,
						khno_bkid, aadharno,season,cropyear);
//				}
				} else {
					System.err.println("vcode cannot be null or zero");
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
   
		model.addAttribute("resultList", resultList);
		if (resultList == null || resultList.size() == 0) {
		    model.addAttribute("NoDataAvailableMessage", "Data is Not Available..");
		}
    
    	
		model.addAttribute("khno_bkid", khno_bkid);
		model.addAttribute("appId", appId);
		model.addAttribute("appName", appName);
		model.addAttribute("appMob", appMob);
		
		model.addAttribute("appMob", appMob);

		
		
		return "rbkroles/cropPatternDetails";
		//return "cropPatternNewPage";

	
	}
		
	
	
	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/SocialAuditCropPatternSave")
	public String cropPatternSave(HttpSession session,Model model, HttpServletRequest request) {
		  
		boolean result=false;
		
		String cropYear = request.getParameter("cropyear");
				
		String season = request.getParameter("season");	
		
		String dcode= request.getParameter("wbldcode");		
		
		String vcode = request.getParameter("vcode");
		
		String[] bookingiIds = request.getParameter("bkIds").split(",");
		
		String[] cropCodeOrg = request.getParameter("orgCrCode").split(",");
		
		String[] khatanos = request.getParameter("khNos").split(",");
		
		String[] surveynos = request.getParameter("surNos").split(",");
		
		String[] sowDates = request.getParameter("sowDts").split(",");
		
		String[] tot_Extents = request.getParameter("extS").split(",");
		
		String[] crSowTypes = request.getParameter("crpNture").split(",");
		
		String[] cropNames = request.getParameter("crCds").split(",");
		
		String[] varietyCodes = request.getParameter("varCds").split(",");
		
		String[] varietyCodesOrg = request.getParameter("orgVarCode").split(",");
		
		String[] crNos = request.getParameter("crNos").split(",");
		
		String[] orgExt = request.getParameter("orgExt").split(",");
		
		String[] crCdsec = request.getParameter("crCdsec").split(",");
		
		String[] varCdsec = request.getParameter("varCdsec").split(",");
		
		String[] croppingType = request.getParameter("croppingType").split(",");
		
		String[] cropingTypeSec = request.getParameter("croppingTypeSec").split(",");		
		String clientIp = request.getRemoteAddr();
		

		try {
			for(int i=0; i<bookingiIds.length;i++) {
				RegularExpressionclassMethod method= new RegularExpressionclassMethod();
				boolean valid= method.checkstring(cropNames[i]);
				boolean valid2= method.checkstring(khatanos[i]);
				boolean valid3= method.checkstring(surveynos[i]);
				boolean valid4= method.checkstring(croppingType[i]);
				boolean valid5= method.checkstring(cropingTypeSec[i]);
				boolean valid6= method.checkstring(crSowTypes[i]);
//				if(valid && valid2 && valid3 && valid4 && valid5 && valid6) {
		 result = patternService.saveCropDetails(sowDates[i], crSowTypes[i], bookingiIds[i], crNos[i], varietyCodesOrg[i], cropCodeOrg[i],
				varietyCodes[i], varCdsec[i], cropNames[i], crCdsec[i], khatanos[i], surveynos[i], tot_Extents[i], orgExt[i], crNos[i], 
				croppingType[i], cropingTypeSec[i], crSowTypes[i], cropYear, season, dcode,vcode,clientIp);
//				}
			}
		if(result) {
			System.out.println("Successfully Updated Rabi 2022-23 Mono To Mixed Cropping");
			model.addAttribute("message", "Successfully Updated Rabi 2022-23 Mono To Mixed Cropping");
		} else {
			model.addAttribute("message", "Updation Failed");
		}
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("message", e.getMessage());
	}
		return "rbkroles/monoMixSavePage";
	}


}
