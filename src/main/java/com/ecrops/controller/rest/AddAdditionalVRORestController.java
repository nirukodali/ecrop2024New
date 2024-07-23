package com.ecrops.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.AddAdditionalVroVillageData;
import com.ecrops.dto.crop.response.UserData;
import com.ecrops.repo.crop.AddAdditionalVroService;

@RestController
@RequestMapping("/rest/api")
public class AddAdditionalVRORestController {

    @Autowired
    private AddAdditionalVroService additionalVroService;

    @GetMapping("/AddAdditionalVro/getVillages")
    public List<AddAdditionalVroVillageData> getVillages(HttpSession httpSession) {

        String dcodeString = (String) httpSession.getAttribute("dcode");
        String mcodeString = (String) httpSession.getAttribute("mcode");

        if (dcodeString == null || mcodeString == null) {
            throw new IllegalArgumentException("Session values dcode and mcode cannot be null.");
        }

        try {
            int dcode = Integer.parseInt(dcodeString);
            int mcode = Integer.parseInt(mcodeString);

            if (dcode <= 0 || mcode <= 0) {
                throw new IllegalArgumentException("Invalid session values for dcode and mcode.");
            }

            List<AddAdditionalVroVillageData> villageDataList = additionalVroService.getData(dcode, mcode);

            if (villageDataList.isEmpty()) {
                throw new RuntimeException("No village data found for the given dcode and mcode.");
            }

            return villageDataList;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for session values dcode and mcode.", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred.", e);
        }
    }
    
    @PostMapping("/saveuserdata")
    public ResponseEntity<String> StringaddUser(@RequestBody UserData userData, HttpSession session, HttpServletRequest request) {
        String vill = userData.getVill();
        String name = userData.getName();
        String mobile = userData.getMobile();
        String email = userData.getEmail();
        String aadhaar = userData.getAadhaar();
         
        System.out.println("vill--->" + vill);
        System.out.println("name--->" + name);
        System.out.println("mobile--->" + mobile);
        System.out.println("email--->" + email);
        System.out.println("aadhaar--->" + aadhaar);

        
        String sesdcode = (String) session.getAttribute("mcode");
        String sesmcode = (String) session.getAttribute("dcode");

        
        // Extract user information from the session
        String userId = (String) session.getAttribute("userid");
        
        // Extract district code and mandal code from session
        int wbdcode = (int) session.getAttribute("wbdcode");
        int wbmode = (int) session.getAttribute("wbmcode");

        String clientIPAddress = request.getRemoteAddr();

        String status = additionalVroService.addAdditionalAuthorityOfficer(userId, sesdcode, sesmcode, name, mobile, 
                email, aadhaar, vill, String.valueOf(wbdcode), String.valueOf(wbmode), clientIPAddress);

        System.out.println("status--->" + status);
        
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert data.");
        }
    }
  
}
