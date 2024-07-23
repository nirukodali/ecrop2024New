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
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.dto.crop.response.CropYearCCRC;
import com.ecrops.repo.crop.EfishCropRepository;
import com.ecrops.repo.crop.EfishService;

@Controller
public class EfishController {

    @Autowired
    private EfishCropRepository efishCropRepository;

    @Autowired
    private EfishService efishService;
    
	@PreAuthorize("hasAuthority('5')")
    @GetMapping("/getEfishDataEntry")
    public String getEfishDataEntry(Model model) {
        List<CropYearCCRC> activeSeasons = efishCropRepository.getCropYear();
        model.addAttribute("activeSeasons", activeSeasons);

        return "maoroles/eFishCropEntry";
    }
	
	@PreAuthorize("hasAuthority('5')")
    @PostMapping("/efishFormEntryData")
    public String getDataFromEfishEntryPage(@RequestParam("cropyear") String cropyear,
            @RequestParam("village") String village, Model model, HttpSession session, HttpServletRequest request) {
        try {
            if (cropyear == null || cropyear.isEmpty()) {
                throw new IllegalArgumentException("Crop year is required");
            }
            String[] seasonParts = cropyear.split("@");
            if (seasonParts.length != 2) {
                throw new IllegalArgumentException("Invalid crop year format");
            }
            String season = seasonParts[0];
            int cropYear = Integer.parseInt(seasonParts[1]);

            if (village == null || village.isEmpty()) {
                throw new IllegalArgumentException("Village is required");
            }
            int villageCode = Integer.parseInt(village);
            
            String sesdcode = (String) session.getAttribute("dcode");
            String sesmcode = (String) session.getAttribute("mcode");

            int wbdcode = (int) session.getAttribute("wbdcode");
            int wbmcode = (int) session.getAttribute("wbmcode");

            String ipaddress = request.getRemoteAddr();
            String wbvname=efishCropRepository.getwbvname(Integer.parseInt(village));


            int alreadyAvailableRecords = efishService.checkRecordIsAvailableInDatabase(Integer.parseInt(village), season, cropYear);
            if (alreadyAvailableRecords > 0) {
                model.addAttribute("alreadyAvailableRecords", alreadyAvailableRecords);
            

            } 
            
            else {
                int recordCount = efishService.insertEfishDetails(ipaddress, cropYear, season, village,wbdcode,wbmcode,Integer.parseInt(sesdcode),Integer.parseInt(sesmcode));
                System.out.println("village--->"+village);
                System.out.println("wbvname--->"+wbvname);

                model.addAttribute("recordCount", recordCount);
                model.addAttribute("wbvname", wbvname);

                System.out.println("recordCount--->"+recordCount);

            }
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid input format");
            return "maoroles/eFishCropEntry";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "maoroles/eFishCropEntry";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error processing the request");
            return "maoroles/eFishCropEntry";
        }
        return "maoroles/efishDataEntry";
    }
}
