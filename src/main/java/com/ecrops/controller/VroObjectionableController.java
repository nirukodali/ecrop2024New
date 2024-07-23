package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecrops.dto.VroObjectionableDto;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.VroObjectionable;
import com.ecrops.entity.VroObjectionableDrop;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.DatabaseRepo;
import com.ecrops.repo.VroObjectionableRepo;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.impl.VroObjectionableImpl;

@Controller
public class VroObjectionableController {

	@Autowired
	VroObjectionableRepo repo;

	@Autowired
	private DatabaseRepo wbdcodeRepo;

	@Autowired
	ActiveSeasonService activeSeasonService;

	@Autowired
	VroObjectionableImpl impl;

	@GetMapping("/vroObjectionable")
	public String data(HttpSession httpSession, Model model) {

//		041df7b64c899a51cbd15c0c2e6406b9
		String dcode = (String) httpSession.getAttribute("dcode");
		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println(vscode + dcode);
		int vcode = Integer.parseInt(vscode);
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String season = cropYearActiveSeasonList.get(0).getSeason();
		String partitionName = "cr_crop_det_new_v_";
		String wbdcode = wbdcodeRepo.getWbdCode(dcode);

		if (Integer.parseInt(wbdcode) <= 9) {
			partitionName = partitionName + season + "0" + wbdcode + cropYear;

		} else {
			partitionName = partitionName + season + wbdcode + cropYear;
		}
		// partitionName ="ecrop"+ cropYear+"."+partitionName;
//		if (activeYear.equals(cropyear)) {
		partitionName = "ecrop" + cropYear + "." + partitionName;
//		}

		List<VroObjectionable> list = repo.getData(partitionName, vcode);
		List<VroObjectionableDrop> drop = repo.drop();
		System.out.println(drop.size());
		if (list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("drop", drop);
		} else
			model.addAttribute("empty", "No Records Found");

		return "vroObjectionable";
	}

	@PostMapping("/vroObjectionableSave")
	public String vroObjectionableSave(@RequestBody List<VroObjectionableDto> dto,HttpSession httpSession,HttpServletRequest httpServletRequest) {
//		List<ActiveSeasonProjection> cropYearActiveSeasonList = activeSeasonService.getActiveSeason();
//		int cropYear = cropYearActiveSeasonList.get(0).getYear();
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		int cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String season = cropYearActiveSeasonList.get(0).getSeason();
		System.out.println(dto.get(0).getBookingid()+ dto.get(0).getCategory()+dto.get(0).getVarietyname()+dto.get(0).getVariety());
		
		String tab="vro_obj_details";
		tab= "ecrop" + cropYear + "." + tab;
		String dcode = (String) httpSession.getAttribute("dcode");
		String vscode = (String) httpSession.getAttribute("vscode");
//		String season = cropYearActiveSeasonList.get(0).getSeason();
		String wbdcode = wbdcodeRepo.getWbdCode(dcode);
		String name = (String) httpSession.getAttribute("name");
		String loc = httpServletRequest.getRemoteAddr();
		String part,partitionName="cr_details_";
//		int code= repo.val(dto.get(0).getCategory());
		
		if (Integer.parseInt(wbdcode) <= 9) 
				part=	season + "0" + wbdcode + cropYear;
		else
			part=	season + wbdcode + cropYear;
		partitionName = "ecrop"+cropYear +"."+partitionName + part;
		
		System.out.println(part+"--------------"+dto.get(0).getCategory()+"----------"+dto.get(0).getCr_no());
		
		impl.impl(dto, tab,vscode,part,name,loc,partitionName);
		
		return "vroObjectionable";
	}

}
