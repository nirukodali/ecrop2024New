package com.ecrops.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddAdditionalVROContoller {

	
	@PreAuthorize("hasAuthority('2')")
    @GetMapping("/addlAuthOficersIntf")
    public String getAddlAuthOficersIntf() {
        return "mro/addlauthoficersintf";
    }

	@PreAuthorize("hasAuthority('2')")
    @PostMapping("/addlAuthOffsave")
    public String postAddlAuthOffsave() {
    	
        return "mro/redirect:/addlAuthOficersIntf"; 
    }
}
