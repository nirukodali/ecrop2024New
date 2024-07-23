package com.ecrops.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.ObjectionalLandPojo;

import com.ecrops.entity.EnableObjectionalEntity;
import com.ecrops.entity.HoDistrict;
import com.ecrops.entity.Obj_unobjEntity;
import com.ecrops.entity.PattadharPojoLand;
import com.ecrops.entity.Villsec_revEntity;
import com.ecrops.repo.InsertlandobjectionalRepository;
import com.ecrops.repo.LandPattadarDetailsRepository;

import com.ecrops.service.ObjkhatanoService;
import com.ecrops.service.impl.ActiveseasonServiceImplLand;

import java.math.BigDecimal;

@Controller
public class LandController {

	@Autowired
	private ActiveseasonServiceImplLand activeseasonService;

//	@Autowired
//	private DistrictServiceImpl districtService;
//
//	@Autowired
//	private MandalServiceImpl mandalService;

//	@Autowired
//	private VillageServiceImpl villageService;
	
	@Autowired
	private LandPattadarDetailsRepository landpattadardetailsrepository;
	
	@Autowired
	private ObjkhatanoService objkhatanoService;
	
	@Autowired 
	private InsertlandobjectionalRepository insertlandobjectionalRepository;
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/landparcelsVill")
	@ResponseBody
	public List<Villsec_revEntity> findAllVillages(HttpSession httpSession) {
		// System.out.println("Villages----->"+villageService.findAll(dcode, mcode));
		String userid = (String) httpSession.getAttribute("userid");
		
//		String district = (String) httpSession.getAttribute("dcode");
//		String mandal = (String) httpSession.getAttribute("mcode");
		Integer rbkcode = Integer.parseInt(userid.substring(4));
		System.out.println("userid" +userid);
		System.out.println("rbkcode" +rbkcode);
		List<Villsec_revEntity> villages = landpattadardetailsrepository.getvillagedrpdwn(rbkcode);
		System.out.println("Villages----->"+landpattadardetailsrepository.getvillagedrpdwn(rbkcode));

		return villages;

	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/landparcels")
	public String getseasondistrict(Model theModel,HttpSession httpSession) {
		theModel.addAttribute("activeseasons", activeseasonService.findAll());
//		theModel.addAttribute("districts", districtService.findAll());
		
		String sesvscode = (String) httpSession.getAttribute("vscode");
		String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
		 System.out.println("activeyear is ::"+activeYear);
		 List<ObjectionalLandPojo> objectionadetails = landpattadardetailsrepository.getobjdetails(Integer.parseInt(sesvscode),activeYear);
		 List<String> Statuses = new ArrayList<>();
		    
		    for (ObjectionalLandPojo requeststs : objectionadetails) {
		        String Status;
		        if (requeststs.getMao_sts() == null && requeststs.getMro_sts() == null && requeststs.getDao_sts() == null && requeststs.getJc_sts() == null) {
		            Status = "Pending";
		        } else if ("A".equals(requeststs.getMao_sts()) && requeststs.getMro_sts() == null && requeststs.getDao_sts() == null && requeststs.getJc_sts() == null) {
		            Status = "Approved by MAO";
		        }  else if ("R".equals(requeststs.getMao_sts())) {
		            Status = "Rejected by MAO";
		        }  else if ("A".equals(requeststs.getMro_sts())  && requeststs.getDao_sts() == null && requeststs.getJc_sts() == null) {
		            Status = "Approved by MRO";
		        } else if ("R".equals(requeststs.getMro_sts())) {
		            Status = "Rejected by MRO";
		        } else if ("A".equals(requeststs.getDao_sts()) && requeststs.getJc_sts() == null) {
		            Status = "Approved by DAO";
		        } else if ("R".equals(requeststs.getDao_sts())) {
		            Status = "Rejected by DAO";
		        } else if ("A".equals(requeststs.getJc_sts())) {
		            Status = "Approved";
		        } else if ("R".equals(requeststs.getJc_sts())) {
		            Status = "Rejected";
		        }else {
		        	 Status = "Approved";
		        }
		        Statuses.add(Status);
		    }

		    System.out.println("objectionadetails " + objectionadetails); 
		    theModel.addAttribute("objectionadetails", objectionadetails);
		    theModel.addAttribute("maoStatuses", Statuses);
		    
		return "rbkroles/landparcels";
	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/landparcels/{vcode}")
	@ResponseBody
	public List<PattadharPojoLand> getkhata(HttpServletRequest httpServletRequest,HttpSession httpSession,Model theModel,@PathVariable("vcode") int vcode) {
		int wbdcode =  (int) (httpSession.getAttribute("wbdcode"));
		String crpgrp = httpServletRequest.getParameter("crpgrp");
    	 System.out.println("crpgrp: " +crpgrp);
    	 String seas = crpgrp.split("@")[0];
    	 Integer year = Integer.parseInt( crpgrp.split("@")[1]);
    	 System.out.println("season" +seas);
    	 System.out.println("year" +year);
    	 String table="pattadarmast_wb_partition_";
    	 if(year>=2023 && seas.equals("S")) {
    		  table="pattadarmast_wb_partition_";
    	 }else {
    		 table="ecrop"+year+".pattadarmast_wb_partition_";
    	 }
    	
//    	 table="ecrop"+ year +".pattadarmast_wb_partition_";
    	if(wbdcode <= 9)
   		 table+= seas+"0"+wbdcode+year;
     	if(wbdcode > 9)
  		 table+= seas+wbdcode+year;
     	 System.out.println("table" +table);
     	 System.out.println("vcode"+vcode);
     	List<PattadharPojoLand> khatanos = landpattadardetailsrepository.getkhatanos(table, vcode);
		theModel.addAttribute("khatanos",landpattadardetailsrepository.getkhatanos(table,vcode));
		//System.out.println("ghvgjvbhjbnkjnjk,ngfyytfthfgyj");
		//for (PattadharPojoLand pojo : khatanos) {
		   // System.out.println("KhatNo: " + pojo.getKhatNo());
		   // System.out.println("cr_vcode: " + pojo.getCr_vcode());
		//}
		return khatanos;
	}
	
	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/landparcels/khatano/{khatano}")
	@ResponseBody 
	public List<Obj_unobjEntity> getobjkcategory(@PathVariable("khatano") int khatano,HttpSession httpSession,Model theModel) {
		System.out.println("code---------------------"+khatano);
	    List<Obj_unobjEntity> categories = objkhatanoService.getcategory(khatano);
	    
//	    System.out.println("Categories for code " + code + ": " + categories.get(0).getCategory()); 


	    return categories; 
	}

	@PreAuthorize("hasAuthority('25')")
    @GetMapping("/landparcels/surveyno/{code}/{vcode}")
    @ResponseBody 
    public List<PattadharPojoLand> getsurveyno(HttpSession httpSession,HttpServletRequest httpServletRequest,@PathVariable("code") int code,@PathVariable("vcode") int vcode,Model theModel){

    	int wbdcode =  (int) (httpSession.getAttribute("wbdcode"));
//    	int year= 2024;
//    	String season="K";
//    	String table="ecrop"+ year +".pattadarmast_wb_partition_";
	    	String crpgrp = httpServletRequest.getParameter("crpgrp");
	   	 System.out.println("crpgrp:-------------------------- " +crpgrp);
	   	 String seas = crpgrp.split("@")[0];
	   	 Integer year = Integer.parseInt( crpgrp.split("@")[1]);
	   	 System.out.println("season" +seas);
	   	 System.out.println("year" +year);
	   	 String table="pattadarmast_wb_partition_";
	   	 if(year>=2023 && seas.equals("S")) {
	   		  table="pattadarmast_wb_partition_";
	   	 }else {
	   		 table="ecrop"+year+".pattadarmast_wb_partition_";
	   	 }
   	
    	if(wbdcode <= 9)
   		 table+= seas+"0"+wbdcode+year;
     	if(wbdcode > 9)
  		 table+= seas+wbdcode+year;
    	List<PattadharPojoLand> pojo = landpattadardetailsrepository.getsurveynodetails( table, vcode,code);
    	for (PattadharPojoLand pattadhar : pojo) {
      	   
     		System.out.println("cr_srno" + pattadhar.getSurveyNo());
     		Integer Rec_id = pattadhar.getRec_id();
     		 System.out.println("Occupant Extent: " + pattadhar.getOccup_extent());
     	    System.out.println("Rec_id " +Rec_id);
     	  
     	    
     	}
		return pojo;
    	
    }
    

	@PreAuthorize("hasAuthority('25')")
    @PostMapping("/getlandparcels")
    public String getlanddetails(HttpSession httpSession, @ModelAttribute("pattadar") HoDistrict pattadar, 
                                 HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirect) {
        BigDecimal occupentExt = BigDecimal.ZERO;
        BigDecimal totextent = BigDecimal.ZERO;
        String dcode = (String) httpSession.getAttribute("dcode");
        String mcode = (String) httpSession.getAttribute("mcode");
        String vcode = httpServletRequest.getParameter("village");
        String khano = httpServletRequest.getParameter("khatanos");
        String crpgrp = httpServletRequest.getParameter("crpgrp");
        System.out.println("crpgrp:-------------------------->>>>>>>>>>>>>>>>>>>> " +crpgrp);
        String surveyno = httpServletRequest.getParameter("surveyno");
        String sug_ext = httpServletRequest.getParameter("suggestedext");
        String recid = httpServletRequest.getParameter("recid");

        //System.out.println("mcode: " + mcode);
       // System.out.println("recid: " + recid);
       //System.out.println("dcode: " + dcode);

        BigDecimal suggestedext = new BigDecimal(sug_ext);
        BigDecimal khatano = new BigDecimal(khano);

        RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
        boolean val3 = regexpmethod.villageCode(vcode);
        boolean val4 = regexpmethod.checkKhataNo(khano);

        System.out.println("val3: " + val3);
        System.out.println("val4: " + val4);

        if (val3 && val4) {
        	
        	
        	  int wbdcode = (int) (httpSession.getAttribute("wbdcode"));
              String sesvscode = (String) httpSession.getAttribute("vscode");
              
              
            EnableObjectionalEntity landDetail = new EnableObjectionalEntity();
            landDetail.setCr_sno(surveyno);
            landDetail.setCr_vcode(Integer.parseInt(vcode));
            landDetail.setKh_no(khatano);
            landDetail.setSug_ext(suggestedext);
            landDetail.setRec_id(Integer.parseInt(recid));
            landDetail.setMcode(Integer.parseInt(mcode));
            landDetail.setDcode(Integer.parseInt(dcode));
            landDetail.setVscode(Integer.parseInt(sesvscode));
          
            
	          String seas = crpgrp.split("@")[0];
	   	   	 Integer year = Integer.parseInt( crpgrp.split("@")[1]);
	   	   	// System.out.println("season" +seas);
	   	   	 //System.out.println("year" +year);
	   	   	 String table="pattadarmast_wb_partition_";
	   	   	 if(year>=2023 && seas.equals("S")) {
	   	   		  table="pattadarmast_wb_partition_";
	   	   	 }else {
	   	   		 table="ecrop"+year+".pattadarmast_wb_partition_";
	   	   	 }
	   	   	 if(wbdcode <= 9)
	    		 table+= seas+"0"+wbdcode+year;
	   	   	 if(wbdcode > 9)
	   	   		 table+= seas+wbdcode+year;
            List<PattadharPojoLand> pojo = landpattadardetailsrepository.validateextent(table, Integer.parseInt(vcode), surveyno, Integer.parseInt(khano), seas);

            for (PattadharPojoLand pattadhar : pojo) {
            	  //System.out.println("calleddddddddddddddddddddddddddddddddddddddddddddddddddd");
                System.out.println("cr_srno: " + pattadhar.getSurveyNo());
                occupentExt = pattadhar.getOccup_extent();
                totextent = pattadhar.getTotalExtent();
              
                System.out.println("Occupent Extent:------------------------------------------------------------ " + occupentExt);
                System.out.println("Total extent: " + totextent);
                landDetail.setOccup_extent(occupentExt);
                System.out.println("Occupent Extent in landDetail: " + landDetail.getOccup_extent());
            }

            if (suggestedext.compareTo(BigDecimal.ZERO) <= 0) {
                redirect.addFlashAttribute("msg", "Suggested Extent cannot be zero or negative. Data not saved.");
                return "redirect:/landparcels";
            }

            int comparisonResult = suggestedext.compareTo(occupentExt);
            System.out.println("Comparison result: " + comparisonResult);

            if (comparisonResult > 0) {
                model.addAttribute("landDetail", landDetail);
                redirect.addFlashAttribute("msg", "Occupent Extent is greater than Suggested Extent.");
                return "redirect:/landparcels";
            } else {
                EnableObjectionalEntity savelist = insertlandobjectionalRepository.save(landDetail);
                model.addAttribute("savelist", "Saved");
                return "redirect:/landparcels";
            }
        } else {
            redirect.addFlashAttribute("msg", "Please select all details.");
            return "redirect:/landparcels";
        }
    }
	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/updatesugestext")
	@ResponseBody
	public String updatefileds( HttpSession httpSession,
			HttpServletRequest httpServletRequest, @RequestParam("khatano") Integer khatano,
			@RequestParam("survey") String survey,@RequestParam("vcode") Integer vcode, RedirectAttributes redirectAttributes, Model model) {
				System.out.println("calling");
				  String crpgrp = httpServletRequest.getParameter("crpgrp");
				  String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
			        System.out.println("crpgrp:-------------------------->>>>>>>>>>>>>>>>>>>> " +crpgrp);
				int update = landpattadardetailsrepository.updatesugest(survey, khatano, vcode,activeYear);
				 System.out.println("update " + update); 
				redirectAttributes.addFlashAttribute("msg", "Successfully Deleted");
				return "Successfully Deleted";
		
	}

}
