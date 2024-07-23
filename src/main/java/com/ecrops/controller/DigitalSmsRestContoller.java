package com.ecrops.controller;

import java.util.Collections; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.McodeWbemname;
import com.ecrops.repo.crop.DigitalSmsServiceImpl;

@RestController
@RequestMapping("/rest/api")
public class DigitalSmsRestContoller {

	@Autowired
	private DigitalSmsServiceImpl digitalSmsServiceImpl;

	@GetMapping("/Digitalsms/getmcodewbemname")
	private ResponseEntity<List<McodeWbemname>> getMcodeWbemnameData(
	        @RequestParam("cropyear") String selectedCropYear,
	        @RequestParam("districtcode") String districtCode) {

	    try {
	        String[] seasonParts = selectedCropYear.split("@");
	        if (seasonParts.length != 2) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        }

	        String season = seasonParts[0];
	        int activeYear = Integer.parseInt(seasonParts[1]);
	        List<McodeWbemname> resultList = digitalSmsServiceImpl.getMcodeWbemname(
	            Integer.parseInt(districtCode), season, activeYear);
	        
	        return ResponseEntity.ok(resultList);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(Collections.emptyList());
	    }
	}


	
	
	@PostMapping("/Digital_ack_sms_action")
    public ResponseEntity<String> processDigitalAckSMS(@RequestBody McodeWbemname formData, HttpServletRequest request,HttpSession session) {
        try {
           
            String userid = session.getAttribute("userid").toString();

            String[] cropYearWithSeason = formData.getCropyear().split("@");
            String season=cropYearWithSeason[0];        
            int cropYear=Integer.parseInt(cropYearWithSeason[1]);
           
            int dcode = formData.getDcode();
            String mandals = formData.getMandals();
           

            System.out.println("dcode---"+dcode+mandals+season+cropYear);
          
            String ipaddress = request.getRemoteAddr();

            int smscount = digitalSmsServiceImpl.getSmsTotalCount(cropYear, userid, ipaddress, String.valueOf(dcode),  String.valueOf(mandals), season, cropYear);
            System.out.println("SMS count: " + smscount);

    		JSONObject jsonResponse = new JSONObject();
    		
    		jsonResponse.put("smscount", smscount);
    		
    		return ResponseEntity.ok(jsonResponse.toString());
        } catch (NumberFormatException e) {
            return  ResponseEntity.ok("Error: Invalid number format");
        }
    }
}
