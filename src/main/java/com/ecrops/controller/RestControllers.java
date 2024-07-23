package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.entity.RedownloadReasons;
import com.ecrops.projection.DeviceProjection;
import com.ecrops.repo.Devicedelrepo;
import com.ecrops.repo.Devicesentryrepo;
import com.ecrops.repo.Reasons;
import com.ecrops.util.PartitoinsMethods;
import com.ecrops.util.WbVillageModel;

@RestController
@RequestMapping("/utildevice")
public class RestControllers {

	
	@Autowired
	private Devicesentryrepo deviceRepo;
	
	@Autowired
	PartitoinsMethods partitoinsmethods;
	
	@Autowired
	private Reasons reasons;
	
	@Autowired
	private Devicedelrepo devicedelrepo;
	

	
	public RestControllers() { 

	}

	@RequestMapping("/getFirstDeviceVerify")
	public List<DeviceProjection> getImeiVerify(@RequestParam("vcode") String vcode) {
		System.out.println("vcode-------------"+vcode);
		List<DeviceProjection> deviceEntitiesList = deviceRepo.getVcode(Integer.parseInt(vcode));
		return deviceEntitiesList;
	}

	@RequestMapping("/getVillAndMandal")
	public List<WbVillageModel> getVillAndMandal(@RequestParam("distcode") String distcode,
			@RequestParam("mandcode") String mandcode, @RequestParam("sescrpyear") String sescrpyear,HttpSession httpSession) {
		Integer wbdcode= (Integer) httpSession.getAttribute("wbdcode"); 
		List<WbVillageModel> list = partitoinsmethods.getVillAndMandal(distcode, mandcode, sescrpyear,wbdcode);
		return list;
	}
	
	@GetMapping("/getReasons")
	public List<RedownloadReasons> getReasons() {
		List<RedownloadReasons> reaso=reasons.getreasons();
		return reaso;
	}
	
	@GetMapping("/getdevice")
	public List<DeviceProjection> getdevice(String vcode) {
		List<DeviceProjection> list = devicedelrepo.getdevice(Integer.parseInt(vcode));
		return list;
		
	}

	
}
