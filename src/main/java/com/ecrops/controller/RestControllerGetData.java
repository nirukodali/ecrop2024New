package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.entity.CropIrrgEntity;
import com.ecrops.entity.CropVarietyEntity;

import com.ecrops.repo.GetCropIrrg;
import com.ecrops.repo.GetCropVariety;


@RestController
public class RestControllerGetData {
	
	@Autowired
	private GetCropVariety getCropVariety;

	@Autowired
	private GetCropIrrg getCropIrrg;
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/getCropVariety")
	public List<CropVarietyEntity> getVarietyByCrop(@RequestParam("rbkCode") String rbkCode) {
		
		List<CropVarietyEntity> cropVariety = getCropVariety.getCropVariety(Integer.parseInt(rbkCode));
		
		return cropVariety;
		
	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/getIrrMethodBySource")
	public List<CropIrrgEntity> getIrrMethodBySource(@RequestParam("wsrccode") Integer wsrccode) {
		
		List<CropIrrgEntity> cropIrrg = getCropIrrg.getCropIrrgEntity(wsrccode);	
		
		return cropIrrg;
		
	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/getKhathaNo")
	public List<String> getKhathaByVillage(@RequestParam("rbkCode") String rbkCode, HttpSession httpSession) {
		
		List<String> khathaNo = getCropVariety.getKhathaNo(rbkCode);
		
		return khathaNo;
		
	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/getSurveyNo")
	public List<String> getSurveyNo(@RequestParam("KhNo") String KhNo) {
		
		List<String> SurveyNo = getCropVariety.getSurveyNo(KhNo.trim());
		
		return SurveyNo;
		
	}

}
