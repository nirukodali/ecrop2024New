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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.entity.UserRegEntity;
import com.ecrops.projection.DeviceProjection;
import com.ecrops.repo.UserDeactiVationRepo;
import com.ecrops.repo.UserRegRepo;
import com.ecrops.repo.UserRegistrationRepoForProfile;
import com.ecrops.repo.UserRegistrationRepository;
import com.ecrops.repo.UserRegistrationUpdate;
import com.ecrops.repo.UserTypesRepo;
import com.itextpdf.text.log.SysoCounter;


@Controller
public class UserManageController {
	
	
	
	@Autowired
	UserRegRepo userRepo;
	
	@Autowired
	UserRegistrationRepoForProfile userRegRepo;
	
	
	@Autowired
	UserDeactiVationRepo userDeactivation;
	
	@Autowired
	UserTypesRepo userTypeRepo;
	

	@PreAuthorize("!hasAuthority('25')")

	@GetMapping("/updateProfiles")
	public String changePwd(HttpSession session,Model model) {

		 String userid=(String) session.getAttribute("name").toString();
		 String role=(String) session.getAttribute("role").toString();
        UserRegEntity userDetails=userRepo.getUserDetailsForProfileUpdate(userid);
      	model.addAttribute("userDetails", userDetails);
      	model.addAttribute("userid",userid);
      	model.addAttribute("role",userTypeRepo.getUserType(Integer.parseInt(role)).get(0).getName());
      	System.out.println(userTypeRepo.getUserType(Integer.parseInt(role)).get(0).getName()+"kkk");
     System.out.println("useredd::"+session.getAttribute("wbedname"));
      
      	if(session.getAttribute("wbedname")!=null) {
      		model.addAttribute("district",session.getAttribute("wbedname").toString());
      	}
      	else {
      		model.addAttribute("district","");
      	}
      	
      	return "usermanagement/ChangePwd";
	}
	
	@PreAuthorize("!hasAuthority('25')")
	@PostMapping("/updateProfiles")
	public String pwdUpdated(@RequestParam("name") String name,@RequestParam("mobile") String mobile,@RequestParam("email") String email,@RequestParam("aadhaar") String aadhaar,Model model,HttpSession session,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if( name == null || name.equals("") || mobile.equals("") || mobile==null    || email==null || email.isEmpty() || aadhaar==null || aadhaar.equals("") || aadhaar.length()!=12 || mobile.length()!=10  ) {
	    	return "ErrorPage";
	    }
    String userid=(String) session.getAttribute("name").toString();
   
    String role =(String) session.getAttribute("role").toString();
    String sts="";
    if(Integer.parseInt(role)==30) {
    	 sts=request.getParameter("inchargeSts");
    	System.out.println("Status---------->"+sts);
    }
     
      if(userRegRepo.findByAadharIdAndUseridNot(aadhaar,userid).size()>0) {
    	 // model.addAttribute("msg", "Aadhaar No Already Exists");
    	  System.out.println("userRegRepo.findByAadharIdAndUseridNot(aadhaar,userid).size()>>>>>>>>>>>>>>"+userRegRepo.findByAadharIdAndUseridNot(aadhaar,userid).size());
    	  redirectAttributes.addFlashAttribute("msg", "Aadhaar No Already Exists");
      }else {
    	  int i=  userDeactivation.insertIntoUserDeactivation(userid);
    	if(i==1) {
    		System.out.println("userRegRepo.findByAadharIdAndUseridNot(aadhaar,userid).size()-----------------------------------"+i);
    		  int status =userDeactivation.userUpdation(name,mobile,email,aadhaar,userid,sts,role);
    		  if(status==1) {
    			userDeactivation.insertIntoTraceWeb(name,userid,role,request );

    		  }
    	  }
    	// model.addAttribute("msg", "Profile Updated successfully");
    	 redirectAttributes.addFlashAttribute("msg", "Profile Updated successfully");
      }
    
	return "redirect:/updateProfiles";
	}
	
	
	@PreAuthorize("!hasAuthority('25')")
	@GetMapping("/updateProfile")
	public String updateProfile(HttpSession session,Model model) {
	return "UpdateProfile";
	}
}
