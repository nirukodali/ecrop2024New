package com.ecrops.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.EnableObjectLandPojo;

import com.ecrops.entity.PattadarMasterEntity;
import com.ecrops.entity.WbVillageMastEntity;
import com.ecrops.repo.DatabaseRepo;

import com.ecrops.repo.EnableObjectLandRepo;
import com.ecrops.repo.PattadarDetailsRepository;

import com.ecrops.repo.WbvillagesRepository;
import com.ecrops.service.EnableObjLandService;


@Service
public class EnableLandServiceImpl implements EnableObjLandService {
	
	

@Autowired
WbvillagesRepository wbvillagesRepository;




@Autowired
DatabaseRepo da;



@Autowired
EnableObjectLandRepo enableObjectLandRepo;
	
	
	
	
	
	
	@Override
	public List<EnableObjectLandPojo> getEnablelandDetailsPoj(HttpSession httpSession) {
		
		Integer  sesdcode =  Integer.parseInt((String) httpSession.getAttribute("dcode"));
		Integer mcode=0;
		if(httpSession.getAttribute("mcode")!= null) {
			
	 mcode= Integer.parseInt((String) httpSession.getAttribute("mcode")) ;
		}
		Integer role=Integer.parseInt(httpSession.getAttribute("role").toString()) ;
    
		
		 List<EnableObjectLandPojo>  villageData=    enableObjectLandRepo.eolList(sesdcode,mcode,role,httpSession);
	    
		     return villageData;
}

	
	
	@Override
	public String updatingData(int id,HttpSession httpSession) {
	
	String role=httpSession.getAttribute("role").toString();
String message=	enableObjectLandRepo.acceptingData(id,role,"A","A",httpSession);

		return message;
		
	}
	
	
	
	
	
	@Override
	public String updatingRejectedData(HttpServletRequest request, HttpSession httpSession) {	
		int id =Integer.parseInt(request.getParameter("recid").toString());
		String role=httpSession.getAttribute("role").toString();
		String s= request.getParameter("remark");
		String message="";
		if(!s.isEmpty()) {
		 message=	enableObjectLandRepo.acceptingData(id,role,"R",s,httpSession);
		}else {
			 message ="Please enter the records carefully";
		}
		return message;
}



	@Override
	public List<EnableObjectLandPojo> getEnablelandDetailsPojo(HttpSession httpSession) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String updatingDatAccepting(int id, HttpSession httpSession) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String updatingRejectedDat(HttpServletRequest request, HttpSession httpSession) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}