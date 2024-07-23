package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecrops.projection.InchargeRbkProjection;
import com.ecrops.service.impl.Delete_Incharge_ServiceImpl;

@PreAuthorize("hasAuthority('5')")
@Controller
public class MAO_InchargeDeletionController {
	
	@Autowired
	private Delete_Incharge_ServiceImpl delete_Incharge_ServiceImpl;
	
	@GetMapping("/maoInchargeDeletion")
	public String getmaoapproval(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {

		String district = (String) httpSession.getAttribute("dcode");
		String mandal = (String) httpSession.getAttribute("mcode");

		List<InchargeRbkProjection> getmaoDMcode = delete_Incharge_ServiceImpl.getmaoDMcode(Integer.parseInt(district),
				Integer.parseInt(mandal));

		if (getmaoDMcode.isEmpty()) {
			model.addAttribute("noResults", true);
		} else {
			model.addAttribute("getmaoDMcode", getmaoDMcode);
		}

		return "maoroles/maoInchargeDeletion";

	}

	@PostMapping("/detetingMAO-Incharge")
	public String maoapproval(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {

		String mandal = (String) httpSession.getAttribute("mcode");
		String district = (String) httpSession.getAttribute("dcode");
		String pempcodeLst[] = httpServletRequest.getParameter("pempcodeLst").split(",");
		String prbkusrLst[] = httpServletRequest.getParameter("prbkusrLst").split(",");
		if (pempcodeLst.length == 0 || pempcodeLst[0].isEmpty()) {		
			List<InchargeRbkProjection> getmaoDMcode = delete_Incharge_ServiceImpl.getmaoDMcode(Integer.parseInt(district),
					Integer.parseInt(mandal));
			if (getmaoDMcode.isEmpty()) {
				model.addAttribute("noResults", true);
			} else {
				model.addAttribute("getmaoDMcode", getmaoDMcode);
			}
			model.addAttribute("msg", "Please select atleast one record");
			return "maoroles/maoInchargeDeletion";
		}
		for (int i = 0; i < pempcodeLst.length; i++) {
			int deletemaoIch = delete_Incharge_ServiceImpl.deletemaoIch(Integer.parseInt(district), Integer.parseInt(mandal),
					prbkusrLst[i], pempcodeLst[i]);
			if (deletemaoIch > 0) {
				model.addAttribute("msg", "Successfully Deleted Details");
			} else {
				model.addAttribute("msg", "Deletion Failed");
			}

			List<InchargeRbkProjection> getmaoDMcode = delete_Incharge_ServiceImpl.getmaoDMcode(Integer.parseInt(district),
					Integer.parseInt(mandal));

			if (getmaoDMcode.isEmpty()) {
				model.addAttribute("noResults", true);
			} else {
				model.addAttribute("getmaoDMcode", getmaoDMcode);
			}
		}
		return "maoroles/maoInchargeDeletion";

	}
}
