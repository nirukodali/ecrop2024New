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

import com.ecrops.projection.InchargeRbkProjection;
import com.ecrops.projection.VillageName;
import com.ecrops.repo.ADA_Incharge_Save_Repo;
import com.ecrops.repo.ADA_incharge_Repo;
import com.ecrops.repo.MandalRepo;

@PreAuthorize("hasAuthority('31')")
@Controller
public class ADA_Incharge_Controller {

	@Autowired
	private MandalRepo mandalRepo;

	@Autowired
	private ADA_incharge_Repo ada_incharge_Repo;

	@Autowired
	private ADA_Incharge_Save_Repo ada_Incharge_Save_Repo;

	@GetMapping("/adainchargesearch")
	public String getadaapproval(HttpSession httpSession, HttpServletRequest httpServiceRequest, Model model) {

		String district = (String) httpSession.getAttribute("dcode");
		List<VillageName> mandalName = mandalRepo.getmname(Integer.parseInt(district));
		model.addAttribute("mandalName", mandalName);

		return "adaroles/adainchargesearch";
	}

	@PostMapping("/adaincharge")
	public String postadaapproval(RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest,
			HttpSession httpSession, Model model) {

		String mandalcodes = httpServletRequest.getParameter("mandal");

		if (mandalcodes == null || mandalcodes.isEmpty()) {

			redirectAttributes.addFlashAttribute("msg", "Please select a Mandal");
			return "redirect:/adainchargesearch";
		}

		String district = (String) httpSession.getAttribute("dcode");

		List<InchargeRbkProjection> getadaDMcode = ada_incharge_Repo.getadaDMcode(Integer.parseInt(district),
				Integer.parseInt(mandalcodes));

		List<VillageName> mandalName = mandalRepo.getmname(Integer.parseInt(district));
		String mandalNameName = "";
		for (VillageName bean : mandalName) {
			if (bean.getMcode().equalsIgnoreCase(mandalcodes)) {
				mandalNameName = bean.getMname();
			}
		}
		model.addAttribute("mandalNameName", mandalNameName);

		model.addAttribute("mandalcodes", mandalcodes);

		// Check if the result list is empty
		if (getadaDMcode.isEmpty()) {
			model.addAttribute("noResults", true);
		} else {
			model.addAttribute("getadaDMcode", getadaDMcode);
		}

		return "adaroles/adainchargeapproval";
	}

	@PostMapping("/adainchargeapproval")
	public String adaAppRej(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {

		String district = (String) httpSession.getAttribute("dcode");
		String maoRecLst[] = httpServletRequest.getParameter("maoRecLst").split(",");
		String justifyIdList[] = httpServletRequest.getParameter("justifyIdLst").split(",");
		String pempcodeLst[] = httpServletRequest.getParameter("pempcodeLst").split(",");
		System.out.println("empcode----->" + Arrays.toString(pempcodeLst));
		String prbkusrLst[] = httpServletRequest.getParameter("prbkusrLst").split(",");
		String mandalcodes = httpServletRequest.getParameter("mandalcode");

		if (maoRecLst.length == 0) {
			List<VillageName> mandalName = mandalRepo.getmname(Integer.parseInt(district));
			String mandalNameName = "";
			for (VillageName bean : mandalName) {
				if (bean.getMcode().equalsIgnoreCase(mandalcodes)) {
					mandalNameName = bean.getMname();
				}
			}
			model.addAttribute("mandalNameName", mandalNameName);
			model.addAttribute("mandalcodes", mandalcodes);

			List<InchargeRbkProjection> getadaDMcode = ada_incharge_Repo.getadaDMcode(Integer.parseInt(district),
					Integer.parseInt(mandalcodes));

			if (getadaDMcode.isEmpty()) {
				model.addAttribute("noResults", true);
			} else {
				model.addAttribute("getadaDMcode", getadaDMcode);
			}

			model.addAttribute("msg", "Please select atleast one Record ");
			return "adaroles/adainchargeapproval";
		}
		for (int i = 0; i < pempcodeLst.length; i++) {
			System.out.println("pempcodeLs--------------->" + pempcodeLst[i]);
			int updateADAIch = ada_Incharge_Save_Repo.updateADAIch(maoRecLst[i], justifyIdList[i],
					Integer.parseInt(district), Integer.parseInt(mandalcodes), prbkusrLst[i], pempcodeLst[i]);
			if (updateADAIch > 0) {
				List<VillageName> mandalName = mandalRepo.getmname(Integer.parseInt(district));
				String mandalNameName = "";
				for (VillageName bean : mandalName) {
					if (bean.getMcode().equalsIgnoreCase(mandalcodes)) {
						mandalNameName = bean.getMname();
					}
				}
				model.addAttribute("mandalNameName", mandalNameName);
				model.addAttribute("mandalcodes", mandalcodes);

				List<InchargeRbkProjection> getadaDMcode = ada_incharge_Repo.getadaDMcode(Integer.parseInt(district),
						Integer.parseInt(mandalcodes));

				if (getadaDMcode.isEmpty()) {
					model.addAttribute("noResults", true);
				} else {
					model.addAttribute("getadaDMcode", getadaDMcode);
				}

				model.addAttribute("msg", "Successfully Updated Details");
			} else {
				List<VillageName> mandalName = mandalRepo.getmname(Integer.parseInt(district));
				String mandalNameName = "";
				for (VillageName bean : mandalName) {
					if (bean.getMcode().equalsIgnoreCase(mandalcodes)) {
						mandalNameName = bean.getMname();
					}
				}
				model.addAttribute("mandalNameName", mandalNameName);
				model.addAttribute("mandalcodes", mandalcodes);

				List<InchargeRbkProjection> getadaDMcode = ada_incharge_Repo.getadaDMcode(Integer.parseInt(district),
						Integer.parseInt(mandalcodes));

				if (getadaDMcode.isEmpty()) {
					model.addAttribute("noResults", true);
				} else {
					model.addAttribute("getadaDMcode", getadaDMcode);
				}

				model.addAttribute("msg", "Updation Failed");
			}
		}
		return "adaroles/adainchargeapproval";
	}

}
