package com.ecrops.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.dao.BookingDataDAO;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.Cr_details;
import com.ecrops.entity.LockUnlockReasonsEntity;
import com.ecrops.model.BookingData;
import com.ecrops.model.Cr_images;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.repo.LockUnlockReasons;
import com.ecrops.repo.VillageRevRepo;
import com.ecrops.service.WbMasterService;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;

@Controller
public class PerinnealController {

	@Autowired
	private VillageRevRepo villageRevRepo;

	@Autowired
	private ActiveSeasonServiceImpl activeSeasonService;

	@Autowired
	WbMasterService wbMasterService;

	@Autowired
	private BookingDataDAO bookingDataDAO;

	@Autowired
	private LockUnlockReasons lockUnlockReasons;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/forwardPerinneal")
	public String rofrperinneal(HttpSession httpSession, Model model) {
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String curren_Season = cropYearActiveSeasonList.get(0).getSeasonvalue();
		String a[] = curren_Season.split("@");
		String season = a[0];
		String year = a[1];
		if (season.equalsIgnoreCase("R")) {
			season = "Rabi  ";
		}
		if (season.equalsIgnoreCase("K")) {
			season = "Kharif  ";
		}
		if (season.equalsIgnoreCase("S")) {
			season = "Summer  ";
		}
		String cropYear = season + year;
		model.addAttribute("cropYear", cropYear);
		model.addAttribute("crYearList", cropYearActiveSeasonList);
		Integer vscode = Integer.parseInt((String) httpSession.getAttribute("vscode"));
		List<ActiveSeasonProjection> village = villageRevRepo.getVillageListByRbk(vscode);
		model.addAttribute("village", village);
		return "rbkroles/forPerinneal";
	}

	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/perinnealview")
	public String pview(Model model, @RequestParam("vcode") String selectedVillage,
			RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		if (selectedVillage.isEmpty() || selectedVillage.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Please select a Village");
			return "redirect:/forwardPerinneal";
		}

		Integer cropyear = (Integer) session.getAttribute("ACTIVEYEAR");
		Integer activeYear = (Integer) session.getAttribute("ACTIVEYEAR");
		String cr_Season = (String) session.getAttribute("seasonActive");
		String partitionName = "cr_booking_partition_";
		String partitionName1 = "cr_details_";
		String wbdcode = session.getAttribute("wbdcode").toString();

		if (wbdcode.length() == 1) {
			partitionName = "cr_booking_partition_" + cr_Season + "0" + wbdcode + cropyear;
			partitionName1 = "cr_details_" + cr_Season + "0" + wbdcode + cropyear;
		} else {
			partitionName = "cr_booking_partition_" + cr_Season + wbdcode + cropyear;
			partitionName1 = "cr_details_" + cr_Season + wbdcode + cropyear;
		}
		partitionName = "ecrop" + activeYear + "." + partitionName;
		partitionName1 = "ecrop" + activeYear + "." + partitionName1;
		List<BookingData> bookingDataList = bookingDataDAO.pattadharDetails(partitionName, partitionName1,
				Integer.parseInt(selectedVillage));
		List<LockUnlockReasonsEntity> reasons = lockUnlockReasons.getReason();
		if (bookingDataList.size() > 0) {
			model.addAttribute("bookingDataList", bookingDataList);
			model.addAttribute("reasons", reasons);
		} else if (bookingDataList.size() == 0) {
			model.addAttribute("empty", "No Records Found");
		}
		return "rbkroles/perinnealforward";
	}

	@SuppressWarnings("unused")
	@PreAuthorize("hasAuthority('25')")
	@PostMapping(path = "/saveperinneal", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String saveperinneal(HttpServletRequest httpServletRequest, HttpSession httpSession,
			RedirectAttributes redirectAttributes, Model model) {
		int cr_crop = 0, cr_sow_type = 0, cr_vcode = 0, croptypebyirr = 0, dcode = 0, irrcategory = 0, irrMethod = 0,
				mcode = 0, variety = 0, soc_category = 0, secondBookingid = 0;
		String anubhavadar_name = "", cr_no = "", cr_sno = "", dataSrc = "", oc_fname = "", oc_name = "",
				occupfname = "", occupname = "", seedprod_agency = "", cr_farmeruid = "";
		BigDecimal cr_dist_code = null, cr_mand_code = null, cr_year = null, kh_no = null, mixUnmixExt = null,
				occupant_extent = null, getwDraw = null;
		Character cr_season = null, cropseed_scheme = null, cultivator_type = null, landownership_type = null,
				owner_tenant = null, tarahacode = null;

		String userId = (String) httpSession.getAttribute("userid");
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		String cropSeason = cropYearActiveSeasonList.get(0).getSeason();
		Integer cropYear = cropYearActiveSeasonList.get(0).getCropyear();
		String[] bookingId = httpServletRequest.getParameter("currBookIdLst").split(",");
		if (bookingId.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] khataNo = httpServletRequest.getParameter("currKhnoLst").split(",");
		if (khataNo.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] surveyNo = httpServletRequest.getParameter("currsurnoLst").split(",");
		if (surveyNo.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] extentId = httpServletRequest.getParameter("currtotextLst").split(",");
		if (extentId.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] aadharNo = httpServletRequest.getParameter("curruidLst").split(",");
		if (aadharNo.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] prevBookId = httpServletRequest.getParameter("prevBookIdLst").split(",");
		if (prevBookId.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] prevKhno = httpServletRequest.getParameter("prevKhnoLst").split(",");
		if (prevKhno.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] prevsurno = httpServletRequest.getParameter("prevsurnoLst").split(",");
		if (prevsurno.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] prevtotext = httpServletRequest.getParameter("prevtotextLst").split(",");
		if (prevtotext.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] prevsowdate = httpServletRequest.getParameter("prevsowdateLst").split(",");
		if (prevsowdate.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] previousbookedext = httpServletRequest.getParameter("previousbookedextLst").split(",");
		if (previousbookedext.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] sowtype = httpServletRequest.getParameter("sowtypeLst").split(",");
		if (sowtype.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] cropCode = httpServletRequest.getParameter("prevcropcodeLst").split(",");
		if (cropCode.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String[] varietyCode = httpServletRequest.getParameter("prevVarietyLst").split(",");
		if (varietyCode.equals(null)) {
			redirectAttributes.addFlashAttribute("message", "Improper Data Found");
			return "redirect:/forwardPerinneal";
		}
		String distCode = httpServletRequest.getParameter("dcode");
		String[] crop_years = httpServletRequest.getParameter("cropyearLst").split(",");
		String[] crop_season = httpServletRequest.getParameter("seasonLst").split(",");
		int villcode = Integer.parseInt(httpServletRequest.getParameter("villcode"));

		String[] extentSug = httpServletRequest.getParameter("preExtId").split(",");
		String[] forwardSug = httpServletRequest.getParameter("forwardLst").split(",");
		String[] reasonSug = httpServletRequest.getParameter("reasonLst").split(",");

		for (int i = 0; i < bookingId.length; i++) {
			String prevCrDetails = "cr_details_";
			String partitionName1 = "cr_booking_partition_";
			String prevCrImages = "cr_images_";
			String currCrDetails = "cr_details_";
			String curCrImages = "cr_images_";
			String partKey = "";
			if (distCode.length() == 1) {
				distCode = 0 + distCode;
			} else {
				distCode = distCode;
			}
			if (Integer.parseInt(distCode) <= 9) {
				currCrDetails = "ecrop" + cropYear + "." + currCrDetails + cropSeason + distCode + cropYear;
				partitionName1 = "ecrop" + cropYear + "." + partitionName1 + cropSeason + distCode + cropYear;
				curCrImages = "ecrop" + cropYear + "." + curCrImages + cropSeason + distCode + cropYear;
				prevCrDetails = "ecrop" + crop_years[i] + "." + prevCrDetails + crop_season[i] + distCode
						+ crop_years[i];
				prevCrImages = "ecrop" + crop_years[i] + "." + prevCrImages + crop_season[i] + distCode + crop_years[i];
				partKey = cropSeason  + distCode + (cropYear);

			} else {
				currCrDetails = "ecrop" + cropYear + "." + currCrDetails + cropSeason + distCode + cropYear;
				partitionName1 = "ecrop" + cropYear + "." + partitionName1 + cropSeason + distCode + cropYear;
				curCrImages = "ecrop" + cropYear + "." + curCrImages + cropSeason + distCode + cropYear;
				prevCrDetails = "ecrop" + crop_years[i] + "." + prevCrDetails + crop_season[i] + distCode
						+ crop_years[i];
				prevCrImages = "ecrop" + crop_years[i] + "." + prevCrImages + crop_season[i] + distCode + crop_years[i];
				partKey = cropSeason + distCode + (cropYear);
			}
			if (forwardSug[i].equals("1") || forwardSug[i].equals("2")) {
				List<Cr_details> prevCr_details = bookingDataDAO.getPrevCr_details(Integer.parseInt(prevBookId[i]),
						prevsurno[i], Integer.parseInt(prevKhno[i]), Integer.parseInt(cropCode[i]),
						Integer.parseInt(varietyCode[i]), Date.valueOf(prevsowdate[i]), Integer.parseInt(crop_years[i]),
						crop_season[i], prevCrDetails);
				if (prevCr_details.size() > 0) {
					secondBookingid = prevCr_details.get(0).getBookingid();
					anubhavadar_name = prevCr_details.get(0).getAnubhavadar_name();
					cr_crop = prevCr_details.get(0).getCr_crop();
					cr_dist_code = prevCr_details.get(0).getCr_dist_code();
					cr_farmeruid = prevCr_details.get(0).getCr_farmeruid();
					cr_mand_code = prevCr_details.get(0).getCr_mand_code();
					cr_no = prevCr_details.get(0).getCr_no();
					cr_season = prevCr_details.get(0).getCr_season();
					cr_sno = prevCr_details.get(0).getCr_sno();
					Date cr_sow_date = prevCr_details.get(0).getCr_sow_date();
					cr_sow_type = prevCr_details.get(0).getCr_sow_type();
					cr_vcode = prevCr_details.get(0).getCr_vcode();
					cr_year = prevCr_details.get(0).getCr_year();
					cropseed_scheme = prevCr_details.get(0).getCropseed_scheme();
					croptypebyirr = prevCr_details.get(0).getCroptypebyirr();
					cultivator_type = prevCr_details.get(0).getCultivator_type();
					dataSrc = prevCr_details.get(0).getDataSrc();
					dcode = prevCr_details.get(0).getDcode();
					irrcategory = prevCr_details.get(0).getIrrcategory();
					irrMethod = prevCr_details.get(0).getIrrMethod();
					kh_no = prevCr_details.get(0).getKh_no();
					landownership_type = prevCr_details.get(0).getLandownership_type();
					mcode = prevCr_details.get(0).getMcode();
					mixUnmixExt = prevCr_details.get(0).getMixUnmixExt();
					oc_fname = prevCr_details.get(0).getOc_fname();
					oc_name = prevCr_details.get(0).getOc_name();
					occupant_extent = prevCr_details.get(0).getOccupant_extent();
					occupfname = prevCr_details.get(0).getOccupfname();
					occupname = prevCr_details.get(0).getOccupname();
					owner_tenant = prevCr_details.get(0).getOwner_tenant();
					seedprod_agency = prevCr_details.get(0).getSeedprod_agency();
					variety = prevCr_details.get(0).getVariety();
					getwDraw = prevCr_details.get(0).getwDraw();
					tarahacode = prevCr_details.get(0).getTarahacode();
					soc_category = prevCr_details.get(0).getSoc_category();
					List<Cr_images> prevCr_Images = bookingDataDAO.getPrevCr_Images(Integer.parseInt(prevBookId[i]),
							prevsurno[i], Integer.parseInt(prevKhno[i]), Integer.parseInt(crop_years[i]),
							crop_season[i], prevCrImages);
					String photo = prevCr_Images.get(0).getPhoto();
					List<Cr_details> currCr_Booking_Partition = bookingDataDAO.getCurrCr_Booking_Partition(
							Integer.parseInt(bookingId[i]), surveyNo[i], Integer.parseInt(khataNo[i]), partitionName1);
					BigDecimal occupant_extent2 = currCr_Booking_Partition.get(0).getOccupant_extent();
					BigDecimal tot_extent = currCr_Booking_Partition.get(0).getTot_extent();
					int insertToCrDetails = bookingDataDAO.insertToCrDetails(currCrDetails,
							Integer.parseInt(bookingId[i]), cr_dist_code, cr_mand_code, cr_vcode,
							Integer.parseInt(khataNo[i]), surveyNo[i], cropYear, cropSeason, cr_sow_type,
							Integer.parseInt(cropCode[i]), Double.parseDouble(extentId[i]), cr_no, getwDraw,
							cr_sow_date, userId, dcode, mcode, Integer.parseInt(varietyCode[i]), irrMethod, dataSrc,
							cropseed_scheme, croptypebyirr, tarahacode, irrcategory, soc_category, cr_farmeruid,
							owner_tenant, occupname, occupfname, tot_extent, occupant_extent2, oc_name, oc_fname,
							anubhavadar_name, landownership_type, cultivator_type, Integer.parseInt(prevBookId[i]),
							seedprod_agency, partKey);
					int insertToCrImages = bookingDataDAO.insertToCrImages(curCrImages, Integer.parseInt(bookingId[i]),
							cr_dist_code, cr_mand_code, cr_vcode, Integer.parseInt(khataNo[i]), surveyNo[i], cropYear,
							cropSeason, dcode, mcode, Integer.parseInt(cropCode[i]), cropseed_scheme, photo, partKey);
					if (insertToCrDetails > 0 && insertToCrImages > 0) {
						String peri_forward = "Y";
						String periForward = "M";
						if (forwardSug[i].equals("1")) {
							if (sowtype[i].equals("1")) {
								int updateCr_booking_partition = bookingDataDAO.updateCr_booking_partition(
										partitionName1, Integer.parseInt(prevBookId[i]), Integer.parseInt(distCode),
										Integer.parseInt(bookingId[i]), villcode, Integer.parseInt(khataNo[i]),
										surveyNo[i], periForward);

							} else {
								int updateCr_booking_partition = bookingDataDAO.updateCr_booking_partition(
										partitionName1, Integer.parseInt(prevBookId[i]), Integer.parseInt(distCode),
										Integer.parseInt(bookingId[i]), villcode, Integer.parseInt(khataNo[i]),
										surveyNo[i], peri_forward);
							}
						} else {
							if (sowtype[i].equals("1")) {
								int updateCr_booking_partition1 = bookingDataDAO.updateCr_booking_partition1(
										partitionName1, Integer.parseInt(prevBookId[i]), Integer.parseInt(distCode),
										Integer.parseInt(bookingId[i]), villcode, Integer.parseInt(khataNo[i]),
										surveyNo[i], periForward);
							} else {
								int updateCr_booking_partition1 = bookingDataDAO.updateCr_booking_partition1(
										partitionName1, Integer.parseInt(prevBookId[i]), Integer.parseInt(distCode),
										Integer.parseInt(bookingId[i]), villcode, Integer.parseInt(khataNo[i]),
										surveyNo[i], peri_forward);
							}
						}
						redirectAttributes.addFlashAttribute("message", "Data Updated successfully");
						return "redirect:/forwardPerinneal";	
					} else {
						redirectAttributes.addFlashAttribute("message", "Something went wrong, please try again.");
						return "redirect:/forwardPerinneal";

					}
				}
			} else {
				String peri_forward = "N";
				if (reasonSug != null) {
					int notToForward = bookingDataDAO.notToForward(partitionName1, reasonSug[i],
							Integer.parseInt(prevBookId[i]), Integer.parseInt(distCode), Integer.parseInt(bookingId[i]),
							villcode, Integer.parseInt(khataNo[i]), surveyNo[i], peri_forward);
					redirectAttributes.addFlashAttribute("message", "Data Updated successfully");
					return "redirect:/forwardPerinneal";
				}
			}
		}
		redirectAttributes.addFlashAttribute("message", "Data Updated successfully");
		return "redirect:/forwardPerinneal";
	}
}
