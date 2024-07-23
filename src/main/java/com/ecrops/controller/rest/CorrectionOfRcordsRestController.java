package com.ecrops.controller.rest;

import com.ecrops.dto.crop.response.cr_variety_master;
import com.ecrops.repo.crop.CorrectionOfRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/cropRejEdit")
public class CorrectionOfRcordsRestController {

    @Autowired
    private CorrectionOfRecords correctionOfRecords;

    @GetMapping("/varietyCode")
    private List<cr_variety_master> getVarietyName(@RequestParam("cropId") String cropID) {
        try {
            int selectedCropId = Integer.parseInt(cropID);
            List<cr_variety_master> varieties = correctionOfRecords.findvarietycodevarietyname(selectedCropId);
            return varieties;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for crop ID parameter", e);
        }
    }
}
