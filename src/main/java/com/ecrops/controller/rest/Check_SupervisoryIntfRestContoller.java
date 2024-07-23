package com.ecrops.controller.rest;

import java.math.BigDecimal; 
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.entity.crop.Cr_detailsPojo;
import com.ecrops.repo.crop.CheckSupervisoryIntfSrvice;

@RestController
@RequestMapping("/rest/api/checkSupervisory")
public class Check_SupervisoryIntfRestContoller {

	@Autowired
	private CheckSupervisoryIntfSrvice checkService;

	@GetMapping("/getcodeAndreason")
	public List<AuthorityVerifyReasons> getCodeAndreason() {

		List<AuthorityVerifyReasons> result = checkService.getcodeAndreason();
		return result;

	}

	@PostMapping("/insertDataAndShowMessage")
	public String insertDataAndShowMessageForCheckSupervisory(@RequestBody Cr_detailsPojo request, HttpSession session,
			HttpServletRequest sevRequest) {
		int bookingid = request.getBookingid();
		String apprsts = request.getApprsts();
		BigDecimal cr_dist_code = request.getCr_dist_code();
		BigDecimal cr_mand_code = request.getCr_mand_code();
		String oc_name = request.getOc_name();
		String oc_fname = request.getOc_fname();
		int variety = request.getVariety();
		Date cr_sow_date = request.getCr_sow_date();
		int cr_vcode = request.getCr_vcode();
		BigDecimal kh_no = request.getKh_no();
		String cr_sno = request.getCr_sno();
		int cr_crop = request.getCr_crop();
		String cr_no = request.getCr_no();
		String selectedReasons = request.getSelectedReasons();
		String cropYear = request.getCropYear();
		String season = request.getSeason();


		String user = (String) session.getAttribute("userid");

		System.out.println("userrest----" + user);

		String ipAddress = sevRequest.getRemoteAddr();
//		int cropyear = 2023;
//		String season = "K";

		System.out.println("cr_sow_date---->" + cr_sow_date);
		System.out.println("cr_vcode---->" + cr_vcode);
		System.out.println("bookingID---->" + bookingid);
		System.out.println("apprsts---->" + apprsts);
		System.out.println("variety---->" + variety);
		System.out.println("selectedReasons---->" + selectedReasons);
		System.out.println("cr_crop---->" + cr_crop);

		
		
		if (bookingid == 0 || variety == 0 || cr_vcode == 0 || cr_crop == 0 || cr_dist_code.compareTo(BigDecimal.ZERO) == 0 || cr_mand_code.compareTo(BigDecimal.ZERO) == 0 || kh_no.compareTo(BigDecimal.ZERO) == 0) {
		    return "cr_crop, bookingid, variety, cr_vcode, cr_dist_code, cr_mand_code, and kh_no cannot be zero(0).";
		} else if (apprsts == null || oc_name == null || oc_fname == null || cr_sno == null || cr_no == null) {
		    return "apprsts, oc_name, oc_fname, cr_sno, and cr_no cannot be null.";
		} else if (user == null || ipAddress == null) {
		    return "user, ipAddress parameters cannot be null.";
		} else if (Integer.parseInt(cropYear) == 0 || season == null) {
		    return "cropyear, season parameters cannot be zero or null.";
		} else {

		
		String status = checkService.rejOrApprSupck(String.valueOf(cropYear), String.valueOf(bookingid),
				String.valueOf(cr_crop), cr_no, String.valueOf(cr_sow_date), String.valueOf(kh_no), cr_sno,
				String.valueOf(variety), selectedReasons, apprsts, user, ipAddress, String.valueOf(cr_vcode),
				String.valueOf(cr_dist_code), String.valueOf(cr_mand_code), oc_name, oc_fname, String.valueOf(cropYear),
				season);

		System.out.println("status---" + status);

		if (status != null) {
			return status;
		} else {
			return ("Failed to insert data.");
		}
		}
	}

}
