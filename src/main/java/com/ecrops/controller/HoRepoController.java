package com.ecrops.controller; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//import com.ecrops.entity.Abstractreport;
//import com.ecrops.partition.AbstractReportsRepo;
import com.ecrops.partition.Villagewise;
 
@Controller
public class HoRepoController {
	
	
	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/reportonunlockedextent")
	public String getunlock(HttpServletRequest request, HttpSession session) {

		return "horoles/reportonunlockedextent";
	}

	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/abstractreport")
	public String getabstract(HttpServletRequest request, HttpSession session) {

		return "horoles/abstractreport";
	}

	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/irrigartionabstract")
	public String getirrigartionabstract(HttpServletRequest request, HttpSession session) {

		return "horoles/irrigartionabstract";
	}
	
/////////////////////// REPORTON village wise  Normal area  //////////////////////////

	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/villagewise")
	public String getvillagewise(HttpServletRequest request, HttpSession session) {

		return "horoles/villagewise";
	}
	
	
/////////////////////// REPORTON Normal area by  crop wise //////////////////////////
	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/cropwise")
	public String getcropwise(HttpServletRequest request, HttpSession session) {

		return "horoles/cropwise";
	}
/////////////////////Reports On Crop Booking details Ho ////////
	@PreAuthorize("hasAuthority('22')")
	@GetMapping("/cropbookingdetailsho")
	public String getcropbookingho(HttpServletRequest request, HttpSession session) {

		return "horoles/cropbookingdetailsho";
	}
	
	//-----------------------------------------------------------------------------------
	
	///Reports on Supercheckkk verifyyy////
	@PreAuthorize("hasAuthority('22')")
		@GetMapping("/supervisoryRecordsAllot")
		public String getsupervisoryRecordsAllot() {
			return "horoles/SupervisoryChkRecsAlloted";
		}
		
	//////Reports on super check report
	@PreAuthorize("hasAuthority('22')")
		@GetMapping("/supervisoryRep")
		public String getsupercheckrep() {
			return "horoles/SupervisoryChkReport";
		}

		
		//////Reports on search form by khaata
	@PreAuthorize("hasAuthority('22')")
		@GetMapping("/searchkhata")
		public String getByKhataandSurveyno(Model model) {
			return "horoles/repsearchformbykhsoHO";
		}
		
		

		//////Reports Details Report on Unlock Extent
	@PreAuthorize("hasAuthority('22')")
		@GetMapping("/unlockho")
		public String getByDetailunlockho(Model model) {
			return "horoles/detailunlockho";
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
