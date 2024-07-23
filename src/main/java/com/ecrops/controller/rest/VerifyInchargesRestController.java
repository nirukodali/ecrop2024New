package com.ecrops.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.VerifyInchargeUserRegistrationData;
import com.ecrops.repo.crop.VerifyInchargeService;

@RestController
@RequestMapping("/rest/api")
public class VerifyInchargesRestController {

	@Autowired
	private VerifyInchargeService inchargeService;

	@PostMapping("/apprInchargeSave")
	public String insertDataAndShowMessage(@RequestBody VerifyInchargeUserRegistrationData request, HttpSession session,
	        HttpServletRequest sevRequest) {

	    String userid = request.getUserid();
	    String tempuid = request.getTempuid();
	    String mobile_phone = request.getMobile_phone();
	     if(userid==null || tempuid==null || mobile_phone==null)
	     {
	    	 
	    	 System.err.println("tempuid,userid and mobile_phone cannot be null ");
	    	 throw new RuntimeException("tempuid,userid and mobile_phone cannot be null");
	     }


	    String role = session.getAttribute("role").toString();
	    String sesdcode = session.getAttribute("dcode").toString();
	    String sesmcode = session.getAttribute("mcode").toString();
	    
	    if(role==null || sesdcode==null || sesmcode==null)
	     {
	    	 
	    	 System.err.println("role,sesdcode and sesmcode cannot be null ");
	    	 throw new RuntimeException("role,sesdcode and sesmcode cannot be null");
	     }

	    System.out.println("Received Values:");
	    System.out.println("rbkusrs: " + userid);
	    System.out.println("uuid: " + tempuid);
	    System.out.println("phoneNo: " + mobile_phone);
	    System.out.println("role: " + role);
	    System.out.println("sesdcode: " + sesdcode);
	    System.out.println("sesmcode: " + sesmcode);

	    String status = inchargeService.approveRecords(userid, tempuid, mobile_phone, sesdcode, sesmcode, role);

	    System.out.println("status---" + status);

	    if (status != null) {
	        return status;
	    } else {
	        return "Failed to insert data.";
	    }
	}
}
