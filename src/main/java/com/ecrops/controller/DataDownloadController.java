package com.ecrops.controller;

import java.sql.SQLException;
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

import com.ecrops.entity.ActiveSeason;
import com.ecrops.repo.DataReDownloadRepo;
import com.ecrops.repo.DataReDownloadRepo.Activeseason;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.EmployeeService;
import com.ecrops.repo.GetDownloadStatus;
import com.ecrops.util.MasterFunctions;

@Controller
public class DataDownloadController {
	
	@Autowired
	private DataReDownloadRepo dataReDownloadRepo;
	
	@Autowired
	GetDownloadStatus downloadStatus;
	
	@Autowired
	private ActiveSeasonService activeSeasonService;
	@Autowired
	private EmployeeService employeeService;
	
	
	@PreAuthorize("hasAuthority('5')")
	@GetMapping("/datadownload")
	public String Datadownload(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {
		System.out.println("datta-->>>");
		
		List<ActiveSeason> activeSeason = activeSeasonService.listAll();

		model.addAttribute("seasonvalue", activeSeason.get(0).getSeasonvalue());
		
		
		
		/*
		 * Activeseason activeSeason = dataReDownloadRepo.getActiveseason();
		 * model.addAttribute("activeSeason", activeSeason);
		 */
		return "maoroles/datadownload";
	}

	@PreAuthorize("hasAuthority('5')")
	@PostMapping("/saveDatadownload")
	public String saveDatadownload(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) throws SQLException {
		String tbname1 = "", tseason = "", wbdcode = "";
		
		
		List<ActiveSeason> activeSeason = activeSeasonService.listAll();

		model.addAttribute("seasonvalue", activeSeason.get(0).getSeasonvalue());
		String season =  null;
		
		String dcode = (String) httpSession.getAttribute("dcode");
		String userid = (String) httpSession.getAttribute("userid");
		String sescrpyear = httpServletRequest.getParameter("cropyear");

		season = sescrpyear.split("@")[0];
		if (season == null || season.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", " Please Select Crop Year ");
			return "redirect:/datadownload";
		}
		String activeyear = "2024";
		String vcode = httpServletRequest.getParameter("vcode");
		if (vcode == null || vcode.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", " Please Select vcode ");
			return "redirect:/datadownload";
		}
		String preasonId = httpServletRequest.getParameter("reasonId");
		if (preasonId == null || preasonId.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", " Please Select Reasons  ");
			return "redirect:/datadownload";
		}
		String deviceId = httpServletRequest.getParameter("deviceId");
		if (deviceId == null || deviceId.isEmpty()) {
			redirectAttributes.addFlashAttribute("msg", " Please Select Device Id  ");
			return "redirect:/datadownload";
		}
		String remoteaddr = httpServletRequest.getRemoteAddr();
		String cropyear = sescrpyear.split("@")[1];
		tseason = season.toLowerCase();
		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}
		tbname1 = "cr_booking_partition_" + tseason + wbdcode + cropyear;
		if (activeyear.equals(cropyear)) {
			tbname1 = "ecrop" + activeyear + "." + tbname1;
		}
		Character downloadedStatus='Q';
		try {
		 downloadedStatus = downloadStatus.getDownloadStatus(wbdcode, vcode, cropyear, tseason);
		}
		catch(Exception e) {
		System.out.println("cATCHHHHHH----------->"+e);	
		}
		System.out.println("----------->"+downloadedStatus);
			if (downloadedStatus == 'Y') {
				int updatedownload = employeeService.savedownload(tbname1, Integer.parseInt(vcode));
				System.out.println("vcode----------------------"+vcode);
				int insertiondownload = employeeService.insertdownload(activeyear, Integer.parseInt(vcode), userid,
						remoteaddr, Integer.parseInt(preasonId), Integer.parseInt(cropyear), season);
				System.out.println("activeyear----------------------"+activeyear);
				System.out.println("userid----------------------"+userid);
				System.out.println("remoteaddr----------------------"+remoteaddr);
				System.out.println("preasonId----------------------"+preasonId);
				System.out.println("cropyear----------------------"+cropyear);
				System.out.println("season----------------------"+season);
				
				if(updatedownload == 0 || insertiondownload == 0) {
					redirectAttributes.addFlashAttribute("msg", " Failed To Re-Download ");
					return "redirect:/datadownload";
				}
				else if(updatedownload > 0 && insertiondownload > 0){
					redirectAttributes.addFlashAttribute("msg", "Successfully Enabled Data For Re-Download.");
					return "redirect:/datadownload";
				}
					
				}
			else {
					redirectAttributes.addFlashAttribute("msg", "Failed To Re-Download");
					return "redirect:/datadownload";
				}
			return "redirect:/datadownload";
	}
}
