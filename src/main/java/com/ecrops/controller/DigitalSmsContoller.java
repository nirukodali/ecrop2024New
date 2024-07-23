package com.ecrops.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecrops.dto.crop.response.DistrictCodeAndDistrictName;
import com.ecrops.dto.crop.response.PerinialCropYear;
import com.ecrops.repo.DigitalSmsRepository;
import com.ecrops.repo.crop.DigitalSmsServiceImpl;

@Controller
public class DigitalSmsContoller {

    @Autowired
    private DigitalSmsRepository digitalSmsRepository;

    @Autowired
    private DigitalSmsServiceImpl digitalSmsServiceImpl;
    
	@PreAuthorize("hasAuthority('17')")
    @GetMapping("/getdigitalsmsentry")
    public String getgetdigitalsmsentry(Model model) {
        List<PerinialCropYear> activeSeasons = digitalSmsRepository.getCropYear();
        model.addAttribute("activeSeasons", activeSeasons);
        
        List<DistrictCodeAndDistrictName> districtCodeAndDistrictName =  digitalSmsServiceImpl.getDistrictCodeAndDistrictName();
        model.addAttribute("districtCodeAndDistrictName", districtCodeAndDistrictName);
        return "ddap/smssenttofarintf";
    }
	

}
