package com.ecrops.controller;

import javax.servlet.http.HttpSession; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.service.ChangePasswordToSha;
import com.ecrops.service.impl.UserRegistrationUpdateServiceImpl;

@Controller
public class HoPasswordResetController {
	
	@Autowired 
    private com.ecrops.service.impl.User_registrationServiceImpl  user_registrationService;
	
	@Autowired 
    private UserRegistrationUpdateServiceImpl  userRegistrationUpdateService;
	
	
	@Autowired
	ChangePasswordToSha  changepasswordsha;
	
	@PreAuthorize("hasAuthority('17')")
	@GetMapping("/horesetpwd")
	public String findAl(Model theModel)
	{
	    return "horoles/horesetpwd";   
	} 
	
	@PreAuthorize("hasAuthority('17')")
	@PostMapping("/posthoresetpwd")
	public String fin(Model theModel, @RequestParam("username") String username,
			@RequestParam("newpassword") String newpaessword,
			@RequestParam("retypenewpassword") String retypenewpassword, HttpSession httpSession) {  
		
		
		String md5 = changepasswordsha.encode(newpaessword);
		System.out.println("-------------------------------------------------------------->"+md5);
		String dcode =(String) httpSession.getAttribute("dcode");
		String userId1 = (String)httpSession.getAttribute("userId");
		
	//	System.out.println("newpassword-"+newpassword);
		System.out.println("retypenewpassword"+retypenewpassword);
		
		if(!newpaessword.equals(retypenewpassword)) {
			theModel.addAttribute("msg", "Password and Retype Password are not Matching/User Name does not exists");
			System.out.println("if");
		}else {
			String userId = user_registrationService.findAll(username);
			
			if(userId != null) {
				int updateUserReg = userRegistrationUpdateService.findAll(md5,username,dcode);
				if(updateUserReg > 0) {
					String result="The Reset pasword was done successfully!";
					System.out.println("userid--->"+userId1);
					System.out.println("username--->"+username);
					System.out.println("dcode--->"+dcode);
					int insertTracer = userRegistrationUpdateService.insertTracer(userId1, username, dcode);
					System.out.println("count-->"+insertTracer);
					
				}
			}else {
				theModel.addAttribute("msg", "User Name does not exists");
			}
		}
		
		
		return "horoles/horesetpwd";

	}
	
}