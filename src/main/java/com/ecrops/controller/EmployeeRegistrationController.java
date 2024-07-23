package com.ecrops.controller;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.entity.EmployeeRegister;
import com.ecrops.repo.EmpRegisterRepo;

@PreAuthorize("hasAuthority('5')")
@Controller
public class EmployeeRegistrationController {

	@Autowired
	private EmpRegisterRepo empRegisterRepo;
	
	@GetMapping("/employeeprofile")
	public String employeeprofile(HttpSession httpSession, Model model) {
		model.addAttribute("employee", new EmployeeRegister());
		return "maoroles/employeeprofile";
	}

	@PostMapping("/employeeprofile")
	public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeRegister employee,
			BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes, Model model,
			HttpServletRequest httpServletRequest) {
//		System.out.println("employee.getAadhaarId()--------------"+employee.getAadhaarId());
//		System.out.println("employee.getEmp_name()--------------"+employee.getEmp_name());
//		System.out.println("employee.getEmpCode()--------------"+employee.getEmpCode());
//		System.out.println("employee.getEmail()--------------"+employee.getEmail());
//		System.out.println("employee.getMobile()--------------"+employee.getMobile());
//		System.out.println("employee.getDesignation()--------------"+employee.getDesignation());
		if (bindingResult.hasErrors()) {
//			System.out.println("Binding Result is Called----");
			for (FieldError error : bindingResult.getFieldErrors()) {
//				System.out.println("Field Error in object '" + error.getObjectName() + "', field '" + error.getField()
//						+ "': " + error.getDefaultMessage());
			}
			for (ObjectError error : bindingResult.getGlobalErrors()) {
//				System.out.println(
//						"Object Error in object '" + error.getObjectName() + "': " + error.getDefaultMessage());
			}
			return "maoroles/employeeprofile";
		}

		EmployeeRegister AadhaarExist = empRegisterRepo.findByaadhaarId(employee.getAadhaarId());
//		System.out.println(AadhaarExist);
		if (AadhaarExist == null) {
		} else {
			model.addAttribute("msg", " Aadhaar Number Already Exist");
			return "maoroles/employeeprofile";
		}
		EmployeeRegister EmpCodeExist = empRegisterRepo.findByempCode(employee.getEmpCode());
//		System.out.println("EmpCodeExist---------" + EmpCodeExist);
		if (EmpCodeExist == null) {
		} else {
			model.addAttribute("msg", " Employee Code Already Exist");
			return "maoroles/employeeprofile";
		}

		employee.setDcode(Integer.parseInt((String) httpSession.getAttribute("dcode")));
		employee.setMcode(Integer.parseInt((String) httpSession.getAttribute("mcode")));
		employee.setAadhaarId(employee.getAadhaarId());
		employee.setEmp_name(employee.getEmp_name());
		employee.setEmpCode(employee.getEmpCode());
		employee.setEmail(employee.getEmail());
		employee.setMobile(employee.getMobile());
		employee.setDesignation(employee.getDesignation());
		
		EmployeeRegister savedEmp = empRegisterRepo.save(employee);

		if (savedEmp.getEmpCode() > 0) {
			redirectAttributes.addFlashAttribute("msg", "Successfully Saved Employee Details");
		} else {
			redirectAttributes.addFlashAttribute("msg", " Registration Failed");
		}
		return "redirect:/employeeprofile";
	}
}
