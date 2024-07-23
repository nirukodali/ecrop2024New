package com.ecrops.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.EnableObjectLandPojo;

public interface EnableObjLandService {

	List<EnableObjectLandPojo> getEnablelandDetailsPojo(HttpSession httpSession);

	String updatingData(int id, HttpSession session);

	String updatingRejectedData(HttpServletRequest request, HttpSession session);

	List<EnableObjectLandPojo> getEnablelandDetailsPoj(HttpSession httpSession);

	String updatingDatAccepting(int id, HttpSession httpSession);

	String updatingRejectedDat(HttpServletRequest request, HttpSession httpSession);

	

}
