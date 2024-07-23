package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ecrops.entity.DistwiseSupChk;
import com.ecrops.repo.DistwiseSupChkRepo;

@Controller
public class DistwiseSupChkRepController {
	
	@Autowired
	DistwiseSupChkRepo distwiseSupChkRepo;
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/distwisesupchkrep")
	public String Distwisesupchkrep() {
		return "ddh/distwisesupchkrep";
		
	}
	
	@PreAuthorize("hasAuthority('18')")
	@GetMapping("/distwisesupchkreps")
    @ResponseBody // Indicates that the return value should be written directly to the HTTP response body
    public List<DistwiseSupChk> cropGroupsRep(Model theModel,HttpSession session) {
		System.out.println("in method");
        return distwiseSupChkRepo.getCropwise(session);
    }

}
