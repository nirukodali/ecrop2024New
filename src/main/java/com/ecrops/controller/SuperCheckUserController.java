package com.ecrops.controller;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.entity.crop.CrCropDetNewV;
import com.ecrops.entity.crop.Cr_detailsPojo;
import com.ecrops.repo.crop.SuperCheckAdaRepository;
import com.ecrops.repo.crop.SuperCheckUsersService;

@Controller
public class SuperCheckUserController {

	@Autowired(required = true)
	private SuperCheckAdaRepository checkRecordsIntf;

	@Autowired
	private SuperCheckUsersService checkService;

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('46') || hasAuthority('22')")
	@GetMapping("/superCheckUserIntf")
	public String getSuperCheckUserEntry(Model model, HttpSession httpSession) {

		String role = httpSession.getAttribute("role").toString();
		String sesdcode = (String) httpSession.getAttribute("dcode");
		String sesmcode = (String) httpSession.getAttribute("mcode");

		model.addAttribute("role", role);
		model.addAttribute("sesdcode", sesdcode);
		model.addAttribute("sesmcode", sesmcode);

		return "maoroles/supchkUsersIntf";
	}

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('46') || hasAuthority('22')")
	@PostMapping("/getDataSuperCheckUserIntf")
	public String getDataSuperCheckUserIntf(@RequestParam("cropyear") @NotBlank String cropyear,
			@RequestParam("vcode") @NotBlank String vcode, HttpSession httpSession, Model model,
			HttpServletRequest request) {
		try {
			String user = httpSession.getAttribute("userid").toString();
			String role = httpSession.getAttribute("role").toString();
			
			int seswbdcode = (int) httpSession.getAttribute("wbdcode");
			int seswbmcode = (int) httpSession.getAttribute("wbmcode");


			String[] seasoncropyear = cropyear.split("@");
			String season = seasoncropyear[0];
			String cropYear = seasoncropyear[1];

			String villcode = vcode;
			System.out.println("villcode---" + villcode);
//			System.out.println("cropyear---" + cropyear);
//			System.out.println("season--->" + season);
//			System.out.println("cropYear--->" + cropYear);

			int wbmcode = 0;
			int wbdcode=0;
			if (role.equals("44") || role.equals("45") || role.equals("9") || role.equals("19") || role.equals("46")
					|| role.equals("31") || role.equals("22")) {
				String mcode = request.getParameter("mcode");
				System.out.println("mcode--->" + mcode);
				if (mcode != null) {
					wbmcode = checkRecordsIntf.getwbmcode(Integer.parseInt(mcode));
					 wbdcode= checkRecordsIntf.getwbdcode(Integer.parseInt(mcode));
					System.out.println("wbmcode--->" + wbmcode);
					System.out.println("wbdcode--->" + wbdcode);

				}
			} else {
				wbmcode = seswbmcode;
				wbdcode=seswbdcode;
			}
			httpSession.setAttribute("cropYear", cropYear);
			httpSession.setAttribute("season", season);
			httpSession.setAttribute("wbmcode", wbmcode);
			httpSession.setAttribute("wbdcode", wbdcode);
			httpSession.setAttribute("villcode", villcode);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An unexpected error occurred");
		}
		return "redirect:/viewAfterFormSubmissionIntfusers";
	}

	@PreAuthorize("hasAuthority('44') || hasAuthority('45') || hasAuthority('9') || hasAuthority('19')|| hasAuthority('31') || hasAuthority('2') || hasAuthority('5') || hasAuthority('46') || hasAuthority('22')")
	@RequestMapping("/viewAfterFormSubmissionIntfusers")
	public String viewAfterFormSubmission(HttpSession httpSession, Model model) {
		try {
			String cropYear = (String) httpSession.getAttribute("cropYear");
			String season = (String) httpSession.getAttribute("season");
			String role = (String) httpSession.getAttribute("role");
			String user = (String) httpSession.getAttribute("userid");
			String villcode = (String) httpSession.getAttribute("villcode");
			int wbdcode = (int) httpSession.getAttribute("wbdcode");
			int wbmcode = (int) httpSession.getAttribute("wbmcode");

			List<AuthorityVerifyReasons> codeAndReason = checkService.getcodeAndreason();
			model.addAttribute("codeAndReason", codeAndReason);

			List<CrCropDetNewV> resultList = checkService.checkAndViewHomeMandalViewData(Integer.parseInt(cropYear),
					String.valueOf(wbdcode), wbmcode, user, season, role, villcode);
			model.addAttribute("resultList", resultList);
			model.addAttribute("cropYear", cropYear);
			model.addAttribute("season", season);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An unexpected error occurred");
		}
		return "maoroles/checksuperintfusers";
	}

}
