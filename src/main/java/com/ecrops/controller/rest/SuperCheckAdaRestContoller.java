package com.ecrops.controller.rest;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.dto.crop.response.HomandalsnewView;
import com.ecrops.dto.crop.response.SeasonCropYear;
import com.ecrops.dto.crop.response.SuperCheckVillageData;
import com.ecrops.entity.crop.CrCropDetNewV;
import com.ecrops.repo.crop.SuperCheckAdaRepository;
import com.ecrops.repo.crop.SuperCheckAdaService;

@RestController
@RequestMapping("/rest/api/superCheck")
public class SuperCheckAdaRestContoller {

	@Autowired(required = true)
	private SuperCheckAdaRepository checkAdaRepository;

	@Autowired
	private SuperCheckAdaService checkService;

	@GetMapping("/getSeasonCropYears")
	public List<SeasonCropYear> getSeasonValueCropyear() {

		List<Object[]> resultList = checkAdaRepository.getSeasonCropYears();

		List<SeasonCropYear> getseasonvaluecropyear = new ArrayList<>();
		for (Object[] ob : resultList) {

			String seasonvalue = (String) ob[0];
			String cropyear = (String) ob[1];

			SeasonCropYear setData = new SeasonCropYear();

			setData.setSeasonvalue(seasonvalue);
			setData.setCropyear(cropyear);

			getseasonvaluecropyear.add(setData);

		}
		return getseasonvaluecropyear;

	}

	@GetMapping("/getMandalsSupAda")
	private List<HomandalsnewView> getMacodeAndMname(HttpSession httpSession) {
		String mCode = (String) httpSession.getAttribute("mcode");
		String role = (String) httpSession.getAttribute("role");

		String Smcode = "";
		int hqcode = 0;

		if (mCode.length() == 7) {
			Smcode = mCode.substring(3);
			hqcode = checkAdaRepository.getHqcode(Integer.parseInt(Smcode));
			System.out.println("sesionMandcodewithsubstring---" + Smcode);
			System.out.println("hqcode---" + hqcode);
		} else {
			hqcode = Integer.parseInt(mCode);
			System.out.println("hqcode---" + hqcode);
		}
		List<HomandalsnewView> result = null;
//		if (role.equals("21")) {
//			result = checkAdaRepository.getMcodeMnamefromadhmandals_v(hqcode);
//		} else if (role.equals("31")) {
//			result = checkAdaRepository.getMcodeMnamefromadamandals_v(hqcode);
//		} else {
//			result = checkAdaRepository.getMcodeMnamefromhomandalsnew_v(hqcode);
//		}
		result = checkAdaRepository.getMcodeMnamefromhomandalsnew_v(hqcode);

		for (HomandalsnewView mandal : result) {
			System.out.println("mandal code: " + mandal.getmcode() + ", mandalName: " + mandal.getmname());
		}
		return result;
	} 

	@GetMapping("/getVillagesWithoutMcode")
	private List<SuperCheckVillageData> getVillagesWithoutMcode(@RequestParam("cropyear") String cropyear,
			HttpSession httpSession) {
		String role = (String) httpSession.getAttribute("role");
		System.out.println("role--" + role);
		String userId = (String) httpSession.getAttribute("userid");
		System.out.println("userId--" + userId);

		int sWbmcode = (int) httpSession.getAttribute("wbmcode");
		String sesMcode = (String) httpSession.getAttribute("mcode");

		System.out.println("sWbmcode--" + sWbmcode);

//		int sWbdcode = (int) httpSession.getAttribute("wbdcode");
//		System.out.println("sWbdcode--" + sWbdcode);
		
		int wbdcode= checkAdaRepository.getwbdcode(Integer.parseInt(sesMcode));



		String seasonWithYear[] = cropyear.split("@");
		String season = seasonWithYear[0];
		int cropYear = Integer.parseInt(seasonWithYear[1]);

		httpSession.setAttribute("cropYear", cropYear);
		httpSession.setAttribute("season", season);

		List<Object> result = null;
		List<SuperCheckVillageData> superCheckVillageDataList = null;
		result = checkService.getData(cropYear, String.valueOf(wbdcode), sWbmcode, userId, season);
		superCheckVillageDataList = new ArrayList<>();
		for (Object obj : result) {
			Object[] data = (Object[]) obj;
			int wbvcode = (int) data[0];
			String wbvname = (String) data[1];
			SuperCheckVillageData superCheckVillageData = new SuperCheckVillageData();
			superCheckVillageData.setWbvcode(wbvcode);
			superCheckVillageData.setWbvname(wbvname);

			superCheckVillageDataList.add(superCheckVillageData);
		}

		return superCheckVillageDataList;
	}

	@GetMapping("/getVillagesWithMcode")
	private List<SuperCheckVillageData> getVillages(@RequestParam("cropyear") String cropyear,
			@RequestParam("mcode") String mcode, HttpSession httpSession) {
		System.out.println("selectedmcode--" + mcode);
		String role = (String) httpSession.getAttribute("role");
		System.out.println("role--" + role);
		String userId = (String) httpSession.getAttribute("userid");
		System.out.println("userId--" + userId);

//		int sWbmcode = (int) httpSession.getAttribute("wbmcode");
//		System.out.println("sWbmcode--" + sWbmcode);
//
//		String sWbdcode = (String) httpSession.getAttribute("wbdcode");
//		System.out.println("sWbdcode--" + sWbdcode);
//
//		String wbdcode = String.valueOf(sWbdcode);

		String seasonWithYear[] = cropyear.split("@");
		String season = seasonWithYear[0];
		int cropYear = Integer.parseInt(seasonWithYear[1]);

		httpSession.setAttribute("cropYear", cropYear);
		httpSession.setAttribute("season", season);

		int wbmcode = 0;
		List<Object> result = null;
		List<SuperCheckVillageData> superCheckVillageDataList = null;
		if (role.equals("44") || role.equals("45") || role.equals("9") || role.equals("19") || role.equals("46")
				|| role.equals("31") || role.equals("22")) {
			if (mcode != null) {
				wbmcode = checkAdaRepository.getwbmcode(Integer.parseInt(mcode));
				System.out.println("BasedOnMcodeGotwbmcode---" + wbmcode);
			}
			int wbdcode= checkAdaRepository.getwbdcode(Integer.parseInt(mcode));
			System.out.println("BasedOnMcodeGotwbdcode---" + wbdcode);

			result = checkService.getData(cropYear, String.valueOf(wbdcode), wbmcode, userId, season);
			superCheckVillageDataList = new ArrayList<>();

			for (Object obj : result) {
				Object[] data = (Object[]) obj;
				int wbvcode = (int) data[0];
				String wbvname = (String) data[1];
				SuperCheckVillageData superCheckVillageData = new SuperCheckVillageData();
				superCheckVillageData.setWbvcode(wbvcode);
				superCheckVillageData.setWbvname(wbvname);

				superCheckVillageDataList.add(superCheckVillageData);
			}
		}
		return superCheckVillageDataList;
	}

	@PostMapping("/getcodeAndreason")
	public List<AuthorityVerifyReasons> getCodeAndreason() {

		List<AuthorityVerifyReasons> result = checkService.getCodeAndReason();
		return result;

	}

	@PostMapping("/insertDataAndShowMessage")
	public String insertDataAndShowMessageForAda(@RequestBody CrCropDetNewV request, HttpSession session,
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
		String ipAddress = sevRequest.getRemoteAddr();

		System.out.println("cr_sow_date---->" + cr_sow_date);
		System.out.println("cr_vcode---->" + cr_vcode);
		System.out.println("bookingID---->" + bookingid);
		System.out.println("apprsts---->" + apprsts);
		System.out.println("variety---->" + variety);
		System.out.println("selectedReasons---->" + selectedReasons);
		System.out.println("cropyear---->" + cropYear);
		System.out.println("season---->" + season);

		if (bookingid == 0 || variety == 0 || cr_vcode == 0 || cr_crop == 0
				|| cr_dist_code.compareTo(BigDecimal.ZERO) == 0 || cr_mand_code.compareTo(BigDecimal.ZERO) == 0
				|| kh_no.compareTo(BigDecimal.ZERO) == 0) {
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
					String.valueOf(cr_dist_code), String.valueOf(cr_mand_code), oc_name, oc_fname,
					String.valueOf(cropYear), season);

			System.out.println("status---" + status);

			if (status != null) {
				return status;
			} else {
				return "Failed to insert data.";
			}
		}
	}

}
