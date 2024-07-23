package com.ecrops.controller;

import com.ecrops.entity.ActiveSeason; 

import com.ecrops.entity.Cultivator;
import com.ecrops.model.EditCrBookingDtlsEntity;
import com.ecrops.model.EditCropBookingDetailsModel;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.service.EditCrBookingDetailsService;
import com.ecrops.service.VillageSecService;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;
import com.ecrops.util.ECropUtility;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EditCropBookingDtlsController {

    ActiveSeasonServiceImpl activeSeasonService;
    EditCrBookingDetailsService editCrBookingDetailsService;
    VillageSecService villageSecService;

    public EditCropBookingDtlsController(ActiveSeasonServiceImpl activeSeasonService, EditCrBookingDetailsService editCrBookingDetailsService, VillageSecService villageSecService) {
        this.activeSeasonService = activeSeasonService;
        this.editCrBookingDetailsService = editCrBookingDetailsService;
        this.villageSecService = villageSecService;
    }

    @PreAuthorize("hasAuthority('25')")
    @GetMapping("/editCropBookingDtls")
    public String loadEditCropBookingDtlsPage(Model model, HttpSession session) {
        List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
        model.addAttribute("crYearList", cropYearActiveSeasonList);
        List<ActiveSeasonProjection> villageList = villageSecService.getVillageListByRbk(Integer.valueOf(ECropUtility.sessionData(session).getVsCode()));
        model.addAttribute("crVillageList", villageList);

        return "rbkroles/editCropBookingDtlsSearch";
    }

    @PreAuthorize("hasAuthority('25')")
    @GetMapping("/editCropBookingDtls/details")
    public String geEditCrBookingDetails(EditCropBookingDetailsModel ecbd, Model model,Cultivator cultivator,HttpSession session) {
    	
    	
    	/* String khataNo=null;
    	if(khataNo == null)
    	{
    		redirectAttributes.addFlashAttribute("message","Improper Data Found");
    		return "redirect:/editCropBookingDtls";
    	} */
//    	if(bindingResult.hasErrors()) {
//    		System.out.println("ewrorrrrrrrrrrr");
//    		return "editCropBookingDtlsSearch";
//    	}
//    	else {
 //   		System.out.println("-----------iiugvf");
    	
        final String season = ecbd.getSeason();
        final Integer cropYear = ecbd.getCropYear();
        
        String partKey = ecbd.getSeason() +  ECropUtility.getDcode(ECropUtility.sessionData(session).getWbdcode()) + ecbd.getCropYear();
        
        System.out.println("partKey---->>>"+partKey);

        List<EditCrBookingDtlsEntity> ecbdList = editCrBookingDetailsService.geEditCrBookingDetails(ecbd, partKey);
        ecbdList = ecbdList.stream().map(ec -> {
            ec.setSeason(season);
            ec.setCropyear(cropYear);
            return ec;
        }).collect(Collectors.toList());
        model.addAttribute("ecbdList", ecbdList);
        return getCorrectionPage(ecbd.getCorrectionType());

    }

    @PreAuthorize("hasAuthority('25')")
    @PostMapping("/editCropBookingDtls/update")
    public String updateEditCropBookingDetails(EditCrBookingDtlsEntity ecbd) {
    	
        if(3 == ecbd.getCorrectionType()){
            editCrBookingDetailsService.updateOthersNameChange(ecbd);
        }else if(1 == ecbd.getCorrectionType()){
            editCrBookingDetailsService.updateAadharNoOfPattaOrEnjoyer(ecbd);
        }else if(5 == ecbd.getCorrectionType()){
            editCrBookingDetailsService.updateCropDetails(ecbd);
        }else if(4 == ecbd.getCorrectionType()){
            editCrBookingDetailsService.updateCcrcCultivatorDetails(ecbd);
        }else{
            editCrBookingDetailsService.updateCrAadharNo(ecbd);
        }
        return getCorrectionPage(ecbd.getCorrectionType());
    }

    public String getCorrectionPage(Integer correctionType){
        if(3 == correctionType){
            return "rbkroles/othersNameChange";
        }else if(1 == correctionType){
            return "rbkroles/correctionForAadhaarNoOfPattadar_Enjoyer";
        }else if(5 == correctionType){
            return "rbkroles/correctionForCropDetails";
        }else if(4 == correctionType){
            return "rbkroles/correctionForCcrcCultivatorDetails";
        }else{
            return "rbkroles/aadhaarMismatchAtEkyc";
        }
    }

}
