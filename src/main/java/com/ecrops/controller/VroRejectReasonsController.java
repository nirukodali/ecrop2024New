package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dto.AuthenticationRequest;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.VroRejectDropdownEntity;
import com.ecrops.entity.VroRejectReasonsEntity;
import com.ecrops.repo.SaveVroRejectReasons;
import com.ecrops.repo.UpdateVroRejectReasons;
import com.ecrops.repo.VroRejectDropdownRepo;
import com.ecrops.repo.VroRejectReasonsRepoImpl;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;


@Controller
public class VroRejectReasonsController {
	
	@Autowired 
	private VroRejectReasonsRepoImpl vroRejectReasonsRepoImpl;
	

	@Autowired
	private ActiveSeasonServiceImpl activeSeasonService;


	@Autowired
	private VroRejectDropdownRepo vroRejectDropdownRepo;
	
	@Autowired
	private SaveVroRejectReasons saveVroRejectReasons;
	
	@Autowired
	private UpdateVroRejectReasons updateVroRejectReasons;
	
	@PreAuthorize("hasAuthority('30')")
	@GetMapping("/VroRejectReasons") 
	public String showData(@ModelAttribute AuthenticationRequest authenticationRequest, Model model, 
			RedirectAttributes redirectAttributes, HttpSession httpSession) {
		try {
			int vscode = (Integer) httpSession.getAttribute("wbvcode");
			List<ActiveSeason> activeSeason = activeSeasonService.listAll();
			
			String season = activeSeason.get(0).getSeason();
		

			Integer cropYear =  (Integer) activeSeason.get(0).getCropyear(); 
			
			Integer activeYear = (Integer) httpSession.getAttribute("ACTIVEYEAR");        
			Integer wbdcodeInt = (Integer) httpSession.getAttribute("wbdcode");
			String wbdcode = wbdcodeInt.toString();

			String partitionName = "cr_crop_det_new_v_";
		    if(wbdcodeInt <= 9) {
		        partitionName = partitionName + season + "0" + wbdcode + cropYear;
		    } else {
		        partitionName = partitionName + season + wbdcode + cropYear;
		    }

		    System.out.println("activeYear_______>"+activeYear);
		    
		    System.out.println("cropYear-------------------->"+cropYear);

		   // if (activeYear == cropYear) {
		        partitionName = "ecrop" + activeYear + "." + partitionName;
		        System.out.println("partitionName------------------>"+partitionName);
		   // }
		        System.out.println("---------------------"+vscode);
		    List<VroRejectReasonsEntity> dataList=vroRejectReasonsRepoImpl.findDynamicTableData(vscode, partitionName);
	        List<VroRejectDropdownEntity> rejectionList = vroRejectDropdownRepo.findByCode();
	        model.addAttribute("dataList", dataList);
	        model.addAttribute("rejectionList",rejectionList);
	        
	        if (dataList == null || dataList.isEmpty()) {
	        	redirectAttributes.addFlashAttribute("noRecords", "No Records Found");
				return "vro/VroRejectReasons";
	        }
	        
	    } catch (Exception e){
	    	e.printStackTrace();
	    	System.err.println("An unexpected error occured");
	    }
        
	    return "vro/VroRejectReasons"; 
	}
	@PreAuthorize("hasAuthority('30')")
	@ResponseBody
	@PostMapping(path = "/saveSelection", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String saveSelection(HttpServletRequest httpServletRequest, HttpSession httpSession,
			RedirectAttributes redirectAttributes, Model model, HttpSession session) {

		String status = "";
		try {

			String bookingIdList = httpServletRequest.getParameter("bookingIdLst");
			String khathaNumberList = httpServletRequest.getParameter("khathaNoLst");
			String surveyNumberList = httpServletRequest.getParameter("surveyNoLst");
			String cropCodeList = httpServletRequest.getParameter("cropCodeLst");
			String cropNumber = httpServletRequest.getParameter("cropNumberLst");
			String varietyCode = httpServletRequest.getParameter("varietyCodeLst");
			String rejectreason = httpServletRequest.getParameter("selectedItemList");
			String sownDateLst = httpServletRequest.getParameter("sownDateLst");
			String clientip = httpServletRequest.getRemoteAddr();
			System.out.println(httpSession.getAttribute("wbvcode"));
			String userid = httpSession.getAttribute("userid").toString();

			String sesvcode = httpSession.getAttribute("wbvcode").toString();
			String wbldcode = httpSession.getAttribute("wbdcode").toString();
			String mandcode = httpSession.getAttribute("mcode").toString();
			List<ActiveSeason> activeSeason = activeSeasonService.listAll();

			String season = activeSeason.get(0).getSeason();
			Integer cropyear = activeSeason.get(0).getCropyear();
			Integer activeYear = activeSeason.get(0).getCropyear();
			
			if (bookingIdList == null || bookingIdList.isEmpty() || khathaNumberList == null || khathaNumberList.isEmpty() ||
					surveyNumberList == null || surveyNumberList.isEmpty() || cropCodeList == null || cropCodeList.isEmpty() ||
					cropNumber == null || cropNumber.isEmpty() || varietyCode == null || varietyCode.isEmpty() ||
					rejectreason == null || rejectreason.isEmpty() || sownDateLst == null || sownDateLst.isEmpty() ||
					clientip == null || clientip.isEmpty() || userid == null || userid.isEmpty() ||
					sesvcode == null || sesvcode.isEmpty() || wbldcode == null || wbldcode.isEmpty() ||
					mandcode == null || mandcode.isEmpty() || season == null || season.isEmpty() ||
					cropyear == null || cropyear == 0 || activeYear == null || activeYear == 0) {
	        	
				redirectAttributes.addFlashAttribute("message", "Improper Data");
				return "redirect:/VroRejectReasons";
			}

			if (wbldcode.length() == 1) {
				wbldcode = "0" + wbldcode;

			}

			String tableName = "VRO_REJ_DETAILS";

			//if (activeYear.equals(cropyear)) {
				tableName = "ecrop" + activeYear + "." + tableName;
			//}

			String partKey = "";
			partKey = season + wbldcode + cropyear;

			int savedDetailsCount = saveVroRejectReasons.saveVroRejectReasonsDetails(wbldcode, mandcode, sesvcode,
					partKey, userid, clientip, tableName, bookingIdList, cropCodeList, cropNumber, varietyCode,
					khathaNumberList, surveyNumberList, sownDateLst, rejectreason);

			if (savedDetailsCount > 0) {
				int updateDetails = updateVroRejectReasons.updateVroRejectReasonsDetails(activeYear, cropyear, season,
						wbldcode, sesvcode, bookingIdList, cropCodeList, cropNumber, varietyCode);
				
				if (updateDetails > 0) {
					status = "Data Inserted and Updated Successfully";
				} else {
					status = "Data Inserted Successfully and Failed to Updated";
				}
			} else {
				status = "Both Inserted and Updated is Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An unexpected error occured");
		}

		return status;
	}
	   
}
