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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.entity.DeviceEntity;
import com.ecrops.projection.DeviceProjection;
import com.ecrops.repo.Devicedelrepo;
import com.ecrops.repo.Devicedelrepo.village;
import com.ecrops.service.EmployeeService;

@Controller
public class DeviceDeregisterController {

	@Autowired

	private EmployeeService employeeService;

	@Autowired
	private Devicedelrepo repo;

	@PreAuthorize("hasAuthority('5')")
	@RequestMapping("/devicederegister")
	public String deviceRegis(HttpSession httpSession, Model model) {

		String mcode = (String) httpSession.getAttribute("mcode");
		List<village> vill = repo.getVill(Integer.parseInt(mcode));
		model.addAttribute("vill", vill);
		return "maoroles/deviceVcode";
	}

	@PreAuthorize("hasAuthority('5')")
	@RequestMapping("/devicederegistervcode")
	public String DeviceDeregister(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {
		System.out.println("devicederegistervcode");
		String mcode = (String) httpSession.getAttribute("mcode");
		String vcode = (String) httpServletRequest.getParameter("villageNames");

		System.out.println("------------>" + vcode);

		List<DeviceProjection> deviceList = employeeService.getdevicedet(mcode, vcode);

		if (deviceList.isEmpty()) {
			model.addAttribute("noResults", true);
		} else {
			model.addAttribute("deviceList", deviceList);
			model.addAttribute("vcode", vcode);
		}

		return "maoroles/devicederegister";
	}

	@PreAuthorize("hasAuthority('5')")
	@RequestMapping("/SaveDevicedelt")
	public String SaveDevicedelt(DeviceEntity deviceEntity, HttpSession httpSession,
			RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest, Model model) {

		String[] devIdarr = httpServletRequest.getParameter("devIds").split(",");

		System.out.println("dev----------->" + devIdarr[0].toString());

		String check = httpServletRequest.getParameter("devIds");
		String vcode = (String) httpServletRequest.getParameter("vcode");
		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode====================>>" + mcode);

		System.out.println(check + " devIdarr.length----------->" + devIdarr[0].length());
		if (check.isEmpty()) {
			List<DeviceProjection> deviceList = employeeService.getdevicedet(mcode, vcode);

			if (deviceList.isEmpty()) {
				model.addAttribute("noResults", true);
				return "maoroles/devicederegister";
			} else {
				model.addAttribute("deviceList", deviceList);
				model.addAttribute("vcode", vcode);
			}

//			model.addAttribute("msg", "please seleect one check box ");

			return "maoroles/devicederegister";
		}

		String[] rbkcodes = httpServletRequest.getParameter("rbks").split(",");
		String[] firstDevices = httpServletRequest.getParameter("firstDevs").split(",");
		String[] addlDevices = httpServletRequest.getParameter("addlDevs").split(",");
		String stsarr[] = httpServletRequest.getParameter("statuses").split(",");

		redirectAttributes.addFlashAttribute("vcode", vcode);
		System.out.println("vcodee====================>>" + vcode);
		int SaveDelDevice =0;

		try {
			for (int i = 0; i < devIdarr.length; i++) {

				 SaveDelDevice = employeeService.SaveDelDevice(Integer.parseInt(devIdarr[i]), stsarr[i]);
				
			}
			
		
			List<DeviceProjection> deviceList = employeeService.getdevicedet(mcode, vcode);

			if (deviceList.isEmpty()) {

				model.addAttribute("noResults", true);
			} else {
				model.addAttribute("deviceList", deviceList);
				model.addAttribute("vcode", vcode);
			}
			
			model.addAttribute("successMsg", "Device deregistered successfully!");
			return "maoroles/devicederegister";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "something went wrong, please try again");
			return "maoroles/devicederegister";
		}
	}
}
