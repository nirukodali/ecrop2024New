package com.ecrops.controller;

import java.util.ArrayList; 
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

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.crop.response.FormData;
import com.ecrops.entity.crop.InterCropDetailsEntity;
import com.ecrops.repo.crop.InterCropRepo;
import com.ecrops.service.InterSocialGrievanceService;

@Controller
public class InterCropDetailsController {

	@Autowired
	private InterCropRepo cropRepo;

	@Autowired
	private InterSocialGrievanceService socialGrievanceService;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/getGrivanceRed")
	public String getinterCropDetail(Model model) {

	//	model.addAttribute("formData", new FormData());
		return "rbkroles/interCropRabi";

	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/SocialAuditInterCropViewDetailsRabi")
	public String viewInterCropDetails( @RequestParam("bookingid") String bookingId,
			@RequestParam("vcode") String vcode, @RequestParam("cropyear") String cropyear,
			@RequestParam("appId") String appId, @RequestParam("appName") String appName,
			@RequestParam("appMob") String appMob, HttpSession session, Model model, HttpServletRequest request) {

		/*
		 * @Valid @ModelAttribute("formData") FormData formData, BindingResult
		 * bindingResult, if (bindingResult.hasErrors()) { return "interCropRabi"; }
		 * else {
		 */
		RegularExpressionclassMethod method= new RegularExpressionclassMethod();
		boolean valid4= method.checkstring(appName);
		boolean valid5= method.checkstring(appMob);
		boolean valid6= method.checkstring(appId);
			System.out.println("bookingId---" + bookingId);
			System.out.println("vcode---" + vcode);

			session.setAttribute("vcode", vcode);

			String season = cropyear.split("@")[0];
			int cropYear = Integer.parseInt(cropyear.split("@")[1]);

			System.out.println("season---" + season);
			System.out.println("cropyear---" + cropYear);

			int wbdcode = (int) session.getAttribute("wbdcode");

			List<InterCropDetailsEntity> resultList = null;
			try {
				if(valid4 && valid5 && valid6) {
				resultList = socialGrievanceService.getInterCropData(bookingId,
						Integer.parseInt(vcode), String.valueOf(wbdcode), season);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("resultList--->"+resultList);
			String cr_season_str = "";
			if ('K' == resultList.get(0).getCr_season()) {
				cr_season_str = "Kharif";
			} else if ('R' == resultList.get(0).getCr_season()) {
				cr_season_str = "Rabi";
			}

			model.addAttribute("resultList", resultList);
			model.addAttribute("size", resultList.size());
			model.addAttribute("cr_season_str", cr_season_str);
			model.addAttribute("vcode", vcode);

			model.addAttribute("appId", appId);
			model.addAttribute("appName", appName);
			model.addAttribute("appMob", appMob);

			System.out.println("resultList---->" + resultList.toString());

			return "rbkroles/interCropViewDetalis";

		
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/getDatainterCropViewDetails")
	public String insertInterCropDetails(@RequestParam("wbldcode") String wbldcode,
			@RequestParam("mandcode") String mandcode, @RequestParam("cropyear") String cropyear,
			@RequestParam("seasonStr") String seasonStr, @RequestParam("season") String season,
			@RequestParam("vscode") String vscode, @RequestParam("crpNature") String crpNature,
			@RequestParam("cropName") String cropName, @RequestParam("varietyName") String varietyName,
			@RequestParam("tot_Extent") String tot_Extent, @RequestParam("occupFather") String occupFather,
			@RequestParam("aadharId") String aadharId, @RequestParam("bookingIds") String bookingIds,
			@RequestParam("crNos") String crNos, @RequestParam("khatano") String khatano,
			@RequestParam("surveyno") String surveyno, @RequestParam("sowDt") String sowDt,
			@RequestParam("farmername") String farmername, @RequestParam("ratioOne") String ratioOne,
			@RequestParam("applID") String applID, @RequestParam("mobId") String mobId,
			@RequestParam("applName") String applName, @RequestParam("crcropCode") String crcropCode,
			@RequestParam("varietyCode") String varietyCode, @RequestParam("crpNatSuges") String crpNatSuges,
			HttpSession session, Model model, HttpServletRequest request) {

		System.out.println("============= START ====================");
		System.out.println("wbldcode : " + wbldcode);
		System.out.println("mandcode : " + mandcode);
		System.out.println("cropyear : " + cropyear);
		System.out.println("season : " + season);
		System.out.println("crpNature : " + crpNature);
		System.out.println("cropName : " + cropName);

		System.out.println("varietyName : " + varietyName);
		System.out.println("occupFather : " + occupFather);
		System.out.println("aadharId : " + aadharId);
		System.out.println("bookingIds : " + bookingIds);
		System.out.println("crNos : " + crNos);
		System.out.println("khatano : " + khatano);
		System.out.println("surveyno : " + surveyno);
		System.out.println("sowDt : " + sowDt);
		System.out.println("farmername : " + farmername);
		System.out.println("ratioOne : " + ratioOne);
		System.out.println("applID : " + applID);
		System.out.println("mobId : " + mobId);
		System.out.println("applName : " + applName);
		System.out.println("crcropCode : " + crcropCode);
		System.out.println("varietyCode : " + varietyCode);
		System.out.println("crpNatSuges : " + crpNatSuges);
		System.out.println("============= END ====================");

		String clientIp = request.getRemoteAddr();

		try {
			boolean status = socialGrievanceService.insertCropDetails(wbldcode, mandcode, cropyear, seasonStr, season,
					vscode, crpNature, cropName, varietyName, tot_Extent, occupFather, aadharId, bookingIds, crNos,
					khatano, surveyno, sowDt, farmername, ratioOne, applID, mobId, applName, crcropCode, varietyCode,
					crpNatSuges, clientIp);

			if (status) {
				model.addAttribute("message", "Record Inserted successfully!!!");
			} else {
				model.addAttribute("message", "Record was not inserted");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}

		return "rbkroles/interCropViewDetalisInsert";
	}

	/*
	 * @GetMapping("/getDatainterCropViewDetails") public String
	 * insInterCropDetails(@RequestParam("crpNatureSug") String crpNatureSug,
	 * HttpSession session, Model model, HttpServletRequest request) {
	 * 
	 * System.out.println("Hidden Param : " + crpNatureSug); return
	 * "interCropViewDetalis"; }
	 */
}
