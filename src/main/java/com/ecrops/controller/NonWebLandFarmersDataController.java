package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

//import com.ecrops.dao.EcropDaoImpl;
import com.ecrops.dto.FormData;
import com.ecrops.dto.NonWebData;
import com.ecrops.dto.UnsurveydDataDto;
import com.ecrops.entity.ActiveSeason;
//import com.ecrops.entity.Cr_Booking_PartitionEntity;
import com.ecrops.entity.NonWebLandData;
import com.ecrops.entity.NonWebLandDto;
import com.ecrops.entity.NonWebReasonsEntity;
import com.ecrops.entity.PattMst_NonWebland;
import com.ecrops.entity.Patta_mst_data;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.entity.VillageEntity;
import com.ecrops.entity.WbMaster;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.projection.SurveyNoProjection;
import com.ecrops.repo.Cr_booking_PartitionRepo;
import com.ecrops.repo.NonWebLandRepo;
import com.ecrops.repo.NonWebReasonsRepo;
import com.ecrops.repo.Patt_Mst_NonWebLandRepo;
import com.ecrops.repo.VillageRevRepo;
import com.ecrops.repo.WbMasterRepo;
//import com.ecrops.repo.VillageSecRevRepo;
import com.ecrops.service.DynamicTableService;
import com.ecrops.service.NonWebLandDataEntryService;
import com.ecrops.service.WbMasterService;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;
import com.ecrops.util.ECropUtility;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
public class NonWebLandFarmersDataController {
	@Autowired

	private PattMst_NonWebland pattMst_NonWebland;

	@Autowired
	private NonWebLandData nonWebLandData;
	@Autowired

	private NonWebLandRepo nonWebLandRepo;

	@Autowired

	private DynamicTableService dynamicTableService;

	@Autowired

	private Patt_Mst_NonWebLandRepo patt_Mst_NonWebLandRepo;

	@Autowired

	private NonWebLandDataEntryService nonWebLandDataEntryService;

	@Autowired
	private VillageRevRepo villageRevRepo;
	@Autowired

	private NonWebReasonsRepo nonWebReasonsRepo;

	@Autowired

	private AadharValidationController aadharValidationController;


	@Autowired
	private ActiveSeasonServiceImpl activeSeasonService;

	@Autowired
	private Cr_booking_PartitionRepo cr_booking_PartitionRepo;
	
	@Autowired
	WbMasterRepo wbMasterRepo;
	
	@Autowired 
	
	private RegularExpressionclassMethod regularExpressionclassMethod;
	

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/nonweblandfarmersdata")

	public String farmersdata(RedirectAttributes redirectAttributes, Model model, HttpSession httpSession) {
		List<ActiveSeason> crandseason = activeSeasonService.listAll();
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String curren_Season = cropYearActiveSeasonList.get(0).getSeasonvalue();
		String a[] = curren_Season.split("@");
		String season = a[0];
		String year = a[1];
		if (season.equalsIgnoreCase("R")) {
			curren_Season = "Rabi";
		} else if (season.equalsIgnoreCase("K")) {
			curren_Season = "Kharif";
		} else {
			curren_Season = "Summer";
		}
		String cropYear = curren_Season + year;
		model.addAttribute("cropYear", cropYear);
		Integer vscode = Integer.parseInt((String) httpSession.getAttribute("vscode"));
		List<ActiveSeasonProjection> village = villageRevRepo.getVillageListByRbk(vscode);
		model.addAttribute("village", village);
		model.addAttribute("crandseason", crandseason);
		model.addAttribute("formData", new FormData());
		String message = (String) redirectAttributes.getFlashAttributes().get("message");
		if (message != null) {
			model.addAttribute("message", message);
		}
		return "rbkroles/nonwebland";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping(path = "/surveyno", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)

	public String post(@Valid @ModelAttribute("formData") FormData formData, BindingResult bindingResult, Model model,
			HttpSession httpSession, RedirectAttributes redirectAttributes,
			@RequestParam("vcode") String selectedVillageCode) {
	
		if (bindingResult.hasErrors()) {
			return "nonwebland";
		}
		String surveyno = formData.getSurvyNo();
		
		
		
		if( !regularExpressionclassMethod.checkSuveyNo(surveyno) )
		{
			System.out.println("surveyno122333"+surveyno);

			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/nonweblandfarmersdata";
		}

		int wbvcode = Integer.parseInt(selectedVillageCode);
		List<ActiveSeason> crandseason = activeSeasonService.listAll();
		
		
		String villagecode= wbMasterRepo.getVillageName(wbvcode);
		model.addAttribute("psurveyno", surveyno);

		model.addAttribute("villagecode", villagecode);
		
		Integer activeYear = (Integer) httpSession.getAttribute("ACTIVEYEAR");

		// String a=aadharValidationController.validateAadhar(aadharno);
		String season = (String) httpSession.getAttribute("seasonActive");
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		String partion_table = "pattadarmast_wb_partition_";
		String nwbTab="cr_booking_nwb";
		
		if (wbdcode <= 9) {
			partion_table = partion_table + season + "0" + wbdcode + activeYear;
		} else {
			partion_table = partion_table + season + wbdcode + activeYear;
		}

		// if (activeYear == cropyear) {
	partion_table = "ecrop" + activeYear + "." + partion_table;
	nwbTab = "ecrop" + activeYear + "." + nwbTab;
	
		// }
	System.out.println("wbvcode--------------->"+wbvcode);
	
	System.out.println("nwbTab--------------->"+nwbTab);
	
	System.out.println("partion_table--------------->"+partion_table);
	
	System.out.println("surveyno--"+surveyno);
	//	partion_table="ecrop2024.pattadarmast_wb_partition_r202024";
		String srno = cr_booking_PartitionRepo.findSurveyNo(partion_table, wbvcode, surveyno);
		String srnFromCr_nwb=cr_booking_PartitionRepo.findSurveyNoFromCr_nwb(nwbTab, wbvcode, surveyno);

	

		model.addAttribute("srno", srno);
		model.addAttribute("surveyno", surveyno);
		
		model.addAttribute("nonWebLandDto", new NonWebLandDto());
		
	List<Patta_mst_data> data= null;
	System.out.println("srnFromCr_nwb"+srnFromCr_nwb);
		if (srno !=null ) {
		
			data =cr_booking_PartitionRepo.findData(partion_table, wbvcode, surveyno);
			String msg = "Sy No: "+ data.get(0).getCr_sno()+"  already exists in Webland. farmerName : " +data.get(0).getFarmername() +", fatherName : "+data.get(0).getFathername()+" and  extent: "+data.get(0).getOccup_extent()+"Ac";
			redirectAttributes.addFlashAttribute("message", msg);
			return "redirect:/nonweblandfarmersdata";
		} 
		else if(srnFromCr_nwb!=null) {
			data =cr_booking_PartitionRepo.findDataCr_nwb(nwbTab, wbvcode, surveyno);
			String msg = "Sy No: "+ data.get(0).getCr_sno()+"  already Entered under NonWebland. farmerName : " +data.get(0).getOc_name() +", fatherName : "+data.get(0).getOc_fname()+" and  extent: "+data.get(0).getOccupant_extent()+"Ac";
			redirectAttributes.addFlashAttribute("message", msg);
			return "redirect:/nonweblandfarmersdata";
			
		}
			else {
			
			int vcode = Integer.parseInt(selectedVillageCode);
			String msg = "Survey number doesnot exist";
			model.addAttribute("msg", msg);
			
			List<NonWebReasonsEntity> reasons = null;
			try {
				reasons = nonWebReasonsRepo.findReason();
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("reasons", reasons);
			Integer dcode = Integer.parseInt((String) httpSession.getAttribute("dcode"));
			Integer mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
			Integer vscode = Integer.parseInt((String) httpSession.getAttribute("vscode"));
			List<ActiveSeasonProjection> village = villageRevRepo.getVillageListByRbk(vscode);

			model.addAttribute("village", wbvcode);
			model.addAttribute("vcode" + vcode);

			return "rbkroles/surveynodoesnotexist";
		}

	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping(path = "/postdata", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String postdata(@Valid @ModelAttribute("nonWebLandDto") NonWebLandDto NonWebLandDto,
			BindingResult bindingResult, Model model, HttpSession httpSession, @RequestParam("vcode") int villageCode,
			@RequestParam("cr_farmeruid") String aadharNo, @RequestParam("cr_sno") String cr_sno,
			@RequestParam(value = "kh_no", required = false) String kh_no,
			@RequestParam("cr_farmeruid") String aadharno, @RequestParam("rson") String reason,
			@RequestParam("oc_name") String oc_name, @RequestParam("oc_fname") String oc_fname,
			@RequestParam("occupname") String occupname, @RequestParam("occupfname") String occupfname,
			@RequestParam(value = "tot_extent", required = false) Double tot_extent,
			@RequestParam("occupant_extent") Double occupant_extent, @RequestParam("gender") String gender,
			@RequestParam("mobileno") String mobileno, RedirectAttributes redirectAttributes) {
		
		
		kh_no="888888";
	
System.out.println("entered ====================}}}}}}");

		if( !regularExpressionclassMethod.checkSuveyNo(cr_sno) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			
			return "redirect:/surveyno";
		}
		System.out.println("kh_no=============>>>" +kh_no);
		if( !regularExpressionclassMethod.checkKhataNo(kh_no) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/surveyno";
		}
		if( !regularExpressionclassMethod.checkAadharNumber(aadharNo) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/surveyno";
		}
		if( !regularExpressionclassMethod.checkMobileNumber(mobileno) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/surveyno";
		}
		if( !regularExpressionclassMethod.checkExtent(occupant_extent.toString())  )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/surveyno";
		}
		if( !regularExpressionclassMethod.checkExtent(tot_extent.toString()) )
		{
			redirectAttributes.addFlashAttribute("message","Improper Data Found");
			return "redirect:/surveyno";
		}
		if (bindingResult.hasErrors()) {
			return "surveynodoesnotexist";
		}
		List<ActiveSeason> crandseason = activeSeasonService.listAll();
		String a = aadharValidationController.validateAadhar(aadharno);
		String season = crandseason.get(0).getSeason();
		String[] str = season.split("@");
		Integer crop_year = (Integer) httpSession.getAttribute("ACTIVEYEAR");
		String crop_Season = str[0];
		Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
		int wbmcode = (int) httpSession.getAttribute("wbmcode");
		String userid =  httpSession.getAttribute("userid").toString();
		int dcode=Integer.parseInt((String) httpSession.getAttribute("dcode"));
		int mcode = Integer.parseInt((String) httpSession.getAttribute("mcode"));
		
		//System.out.println(dcode+"--------------------------------------"+mcode);
		Integer vscode = Integer.parseInt((String) httpSession.getAttribute("vscode"));

		nonWebLandData.setCr_vcode(villageCode);
		List<ActiveSeasonProjection> village = villageRevRepo.getVillageListByRbk(vscode);

		model.addAttribute("village", village);

		nonWebLandData.setBookingId(nonWebLandData.getBookingId());
		nonWebLandData.setCr_dist_code(wbdcode);
		nonWebLandData.setCr_mand_code(wbmcode);
		nonWebLandData.setOccupname(nonWebLandData.getOccupname());
		nonWebLandData.setOccupfname(nonWebLandData.getOccupfname());
		nonWebLandData.setOc_fname(nonWebLandData.getOc_fname());
		String part_key = "";
		if (wbdcode <= 9) {
			part_key = crop_Season + "0" + wbdcode + crop_year;

		} else {
			part_key = crop_Season + wbdcode + crop_year;
		}
		nonWebLandData.setPart_key(part_key);
		pattMst_NonWebland.setCr_dist_code(wbdcode);
		pattMst_NonWebland.setCr_mand_code(wbmcode);
		pattMst_NonWebland.setCr_season(crop_Season);
		pattMst_NonWebland.setCr_year(crop_year);
		pattMst_NonWebland.setReason(reason);
		String aadhar = aadharNo.replace(" ", "");
		String trimmedAadhar = aadhar.substring(0, Math.min(aadhar.length(), 12));
		nonWebLandData.setCr_farmeruid(trimmedAadhar);
		nonWebLandData.setOccupant_extent(occupant_extent);
		nonWebLandData.setCr_season(crop_Season);

		pattMst_NonWebland.setCr_farmeruid(trimmedAadhar);
		pattMst_NonWebland.setCr_vcode(villageCode);
		String crSno = nonWebLandData.getCr_sno();
		
		pattMst_NonWebland.setCr_sno(cr_sno);
		pattMst_NonWebland.setPart_key(part_key);

		String dynamictable = "ecrop"+crop_year + "." + "cr_booking_nwb";
		String dynamictable2 = "ecrop" +crop_year+ "." + "pattmast_nonwebland";
		int cr_dist_code = nonWebLandData.getCr_dist_code();
		int cr_mand_code = nonWebLandData.getCr_mand_code();
		//System.out.println("cr_vcode-------->"+villageCode);
		int cr_vcode = nonWebLandData.getCr_vcode();
		int cr_year = crop_year;
		Long mobile =Long.parseLong(mobileno);
		String cr_season = nonWebLandData.getCr_season();
		
		String cr_farmeruid = nonWebLandData.getCr_farmeruid();
	
		try {
			dynamicTableService.insertIntoDynamicTable(dynamictable, cr_sno, part_key, cr_dist_code, cr_mand_code,
					cr_vcode, cr_year, cr_season, cr_farmeruid, Integer.parseInt(kh_no), oc_name, oc_fname, occupname, occupfname,
					tot_extent, occupant_extent, gender,mobile,dcode,mcode);

			dynamicTableService.insertIntoDynamicTable2(dynamictable2, cr_sno, part_key, cr_dist_code, cr_mand_code,
					cr_vcode, cr_year, cr_season, cr_farmeruid, Integer.parseInt(kh_no), oc_name, oc_fname, occupname, occupfname,
					tot_extent, occupant_extent, gender, reason, mobile,dcode,mcode,userid);
			redirectAttributes.addFlashAttribute("message", "Data added successfully");
			
			return "redirect:/nonweblandfarmersdata";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Data insertion Unsuccessful");
			return "redirect:/nonweblandfarmersdata";
		}
	}

}