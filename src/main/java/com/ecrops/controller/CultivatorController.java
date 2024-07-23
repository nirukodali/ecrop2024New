package com.ecrops.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.PattadarAadhaarDto;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.Cultivator;
import com.ecrops.entity.CultivatorEntity;
import com.ecrops.entity.CultivatorPeri;
import com.ecrops.entity.PattadarUpdate;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.projection.PattadarProjection;
import com.ecrops.repo.AddUpdateCultivatorRepo;
import com.ecrops.repo.CultivatorRepository;
import com.ecrops.repo.PattadarAadhaarRepo;
import com.ecrops.service.CultivatorService;
import com.ecrops.service.VillageSecService;
import com.ecrops.service.impl.ActiveSeasonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
public class CultivatorController {

	Logger logger = LoggerFactory.getLogger(CultivatorController.class);

	@Autowired
	private CultivatorService cultivatorService;

	@Autowired
	private CultivatorRepository cultivatorRepository;

	@Autowired
	VillageSecService secService;

	@Autowired
	private ActiveSeasonServiceImpl activeSeasonService;

	@Autowired
	AddUpdateCultivatorRepo rep;

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/cultivator")
	public String loadAddOrUpdateCultivator(Model model, HttpSession session) {

		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();

		List<ActiveSeasonProjection> villageList = secService
				.getVillageList(Integer.parseInt((String) session.getAttribute("vscode")));
		model.addAttribute("vill", villageList);
		model.addAttribute("crYearList", cropYearActiveSeasonList);
		model.addAttribute("cultivator", new Cultivator());
		model.addAttribute("seasonActive", cropYearActiveSeasonList.get(0).getSeason());

		return "rbkroles/cultivatorHomePage";
	}

	@PreAuthorize("hasAuthority('25')")
	@GetMapping("/cultivator/kathaNo/")
	public String getCultivatorDetailsByKathaNo(Cultivator cultivator, Model model, HttpSession session,
			HttpServletRequest httpServletRequest) {
		int crVcode = Integer.parseInt(httpServletRequest.getParameter("village"));
		Integer cropyear = (Integer) session.getAttribute("ACTIVEYEAR");
		String cr_Season = (String) session.getAttribute("seasonActive");
		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
		List<Cultivator> allCultiVatorsList = cultivatorRepository.getCultivatorDetailsByKathaNo(cultivator.getKhNo(),
				crVcode, cropyear, cr_Season);

		List<Cultivator> cultivatorDataByKathaNo = cultivatorRepository.getCultivatorDataByKathaNo(cultivator.getKhNo(),
				crVcode, cropyear, cr_Season);
		model.addAttribute("cultivatorsData", cultivatorDataByKathaNo);
		List<Cultivator> allCcrcList = cultivatorRepository.getCcrcDetailsByKathaNo(cultivator.getKhNo(), crVcode,
				cropyear, cr_Season);

		// BigDecimal kh = new BigDecimal(cultivator.getKhNo());
		List<PattadarProjection> originalExtentList = cultivatorRepository.getOriginalExtent(cultivator.getKhNo(),
				crVcode);
		model.addAttribute("originalExtentList", originalExtentList);

		List<Cultivator> cultivatorsList = allCultiVatorsList.stream().filter(c -> c.getCultivatorType() != null)
				.collect(Collectors.toList());
		List<Cultivator> ownersList = allCultiVatorsList.stream().map(pc -> {
			Float availableExtent = 0.00f;
			if ("O".equals(pc.getOwner_tenant()) || pc.getCultivatorType() == null) {
				availableExtent = pc.getAvailableExtent();
				System.out.println("availableExtent----------->" + availableExtent);
				if ("O".equals(pc.getCultivatorType()) || "C".equals(pc.getCultivatorType())) {
					availableExtent = pc.getAvailableExtent();
				} else if ("L".equals(pc.getCultivatorType())) {
					availableExtent = pc.getAvailableExtent();
				} else if (pc.getCultivatorType() == null) {
					availableExtent = pc.getAvailableExtent();
				}
				for (Cultivator cc : cultivatorsList) {
					if ("K".equals(cc.getCultivatorType()) && pc.getBookingId().equals(cc.getRefBookingId())) {
						availableExtent = pc.getAvailableExtent();
						System.out.println("availableExtent===>>>"+availableExtent);
					}
				}
			}
			pc.setAvailableExtent(Float.valueOf(String.format("%1.4f", availableExtent)));
			return pc;
		}).filter(c -> "O".equalsIgnoreCase(c.getOwner_tenant()) || "T".equalsIgnoreCase(c.getOwner_tenant()))
				.collect(Collectors.toList());

		if (ownersList.size() > 0) {
			model.addAttribute("ownersList", ownersList);
		}
		if (allCultiVatorsList.size() > 0) {
			model.addAttribute("allCultiVatorsList", allCultiVatorsList);
		}
		if (allCcrcList.size() > 0) {
			model.addAttribute("ccrcList", allCcrcList);
		}
		model.addAttribute("cultivator", new Cultivator());
		model.addAttribute("seasonActive", cropYearActiveSeasonList.get(0).getSeason());
		
//		model.addAttribute("session",session);

//		for (int i = 0; i < allCcrcList.size(); i++) {
//			if (ownersList.get(i).getAvailableExtent() >= allCcrcList.get(i).getOccupantExtent()) {
//				System.out.println(
//						"allCcrcList.get(i).getOccupantExtent()------" + allCcrcList.get(i).getOccupantExtent());
//				model.addAttribute("availableCcrc", allCcrcList.get(i).getOccupantExtent());
//			} else {
//				System.out.println(
//						"ownersList.get(i).getAvailableExtent()---------" + ownersList.get(i).getAvailableExtent());
//				model.addAttribute("availableCcrc", ownersList.get(i).getAvailableExtent());
//			}
//		}

		return "rbkroles/addupdatecultivator";
	}

////	@PreAuthorize("hasAuthority('25')")
//	@PostMapping("/cultivator/save")
//	public String saveCultivatorsData(Cultivator cultivator, Model model) {
//		System.out.println("save----------------------------------------------------------------------->");
//
//		cultivatorService.saveCultivatorsData(cultivator);
//		List<Cultivator> cultiVatorsList = cultivatorService.getCultivatorsByKathaNo(cultivator);
//		model.addAttribute("ownersList",
//				cultiVatorsList.stream().filter(
//						c -> "O".equalsIgnoreCase(c.getOwner_tenant()) || "T".equalsIgnoreCase(c.getOwner_tenant()))
//						.collect(Collectors.toList()));
//
//		model.addAttribute("cultivatorsList",
//				cultiVatorsList.stream().filter(c -> c.getCultivatorType() != null).collect(Collectors.toList()));
//
//		model.addAttribute("cultivator", new Cultivator());
//
//		return "rbkroles/addupdatecultivator";
//
//	}

//	@PreAuthorize("hasAuthority('25')")
//	@PutMapping("/cultivator/owner/update")
//	public String updateOwnerOrEnjoerDetails(Cultivator cultivator, RedirectAttributes redirectAttributes) {
//		cultivatorService.updateOwnerOrEnjoerDetails(cultivator);
//		return "addupdatecultivator";
//	}

//	@PreAuthorize("hasAuthority('25')")
//	@PutMapping("/cultivator/ccrc/update")
//	public String updateCcrcDetails(Cultivator cultivator, RedirectAttributes redirectAttributes) {
//		System.out.println("cultivator---->" + cultivator);
//		cultivatorService.updateCcrcDetails(cultivator);
//		return "rbkroles/addupdatecultivator";
//	}

	@PreAuthorize("hasAuthority('25')")
	@PutMapping("/cultivator/update")
	public String updateCultivatorDetails(Cultivator cultivator, RedirectAttributes redirectAttributes) {
		cultivatorService.updateCultivatorDetails(cultivator);
		return "rbkroles/addupdatecultivator";
	}

	@PreAuthorize("hasAuthority('25')")
	@DeleteMapping("/cultivator/delete")
	public String deleteCultivatorDetails(Cultivator cultivator, RedirectAttributes redirectAttributes,HttpSession httpSession,HttpServletRequest httpServletRequest) {
		System.out.println("bookingid----------->"+httpServletRequest.getParameter("bookingId").toString());
		String bookingid= httpServletRequest.getParameter("bookingId").toString();
		cultivatorService.deleteCultivatorDetails(cultivator,httpSession,bookingid);

		return "rbkroles/addupdatecultivator";

	}

//	@PreAuthorize("hasAuthority('25')")
//
//	@GetMapping("/cultivator/extent")
//	public String getAnubhavadarAndOccupantExtent(Cultivator cultivator, Model model, HttpServletRequest request,
//			HttpServletResponse response) throws JsonProcessingException {
//		Float anubhavadarExtent = cultivatorService.getAnubhavadarExtent(cultivator);
//		BigDecimal totalOccupantExtent = cultivatorService.getTotalOccupantExtent(cultivator);
//
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
//		// cultivator.setAnubhavadarExtent(anubhavadarExtent);
//		cultivator.setOccupantExtent(totalOccupantExtent);
//
//		String jsonMap = mapper.writeValueAsString(cultivator);
//
//		response.setContentType("json");
//		PrintWriter out;
//		try {
//			out = response.getWriter();
//			out.println(jsonMap);
//			out.flush();
//			return null;
//		} catch (Exception e) {
//		}
//
//		return null;
//	}

	// balu
	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/cultivator/owner/update")
	public String updateOwnerOrEnjoerDetails(HttpServletRequest httpServletRequest, HttpSession HttpSession,
			Model model) {
		String mobile = httpServletRequest.getParameter("mobile");
		System.out.println("mobile-----------" + mobile);
		String dist_code = HttpSession.getAttribute("wbdcode").toString();
		String vill_code = httpServletRequest.getParameter("cr_vcode");
		String crop_year = HttpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_season = HttpSession.getAttribute("seasonActive").toString();
		String culti_type = httpServletRequest.getParameter("cultivatorType");
		String occup_extent = httpServletRequest.getParameter("occupantExtent");
		String booking_id = httpServletRequest.getParameter("bookingId");
		String cr_sno = httpServletRequest.getParameter("crSno");
		String kh_no = httpServletRequest.getParameter("khNo");
		String cr_farmeruid = httpServletRequest.getParameter("aadharNo");
		String userId = HttpSession.getAttribute("userid").toString();
		String periStatus = "N";
		BigDecimal totalExtent = null, occupantExtent = null;
		int updateCultivatorDetails = 0;
		if (mobile.isEmpty() || mobile == null) {
			mobile = "0";
		}

		String partitionName = "cr_booking_partition_";
		if (Integer.parseInt(dist_code) <= 9) {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + "0" + dist_code + crop_year;

		} else {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + dist_code + crop_year;
		}
		List<CultivatorEntity> list = rep.getExtentDetails(partitionName, vill_code, cr_sno);
		if (list.size() > 0 && list.get(0).getTotalExtent() != null && list.get(0).getOccupantExtent() != null) {
			totalExtent = list.get(0).getTotalExtent();
			occupantExtent = list.get(0).getOccupantExtent();
		}

		List<CultivatorPeri> perenialKharifDetails = rep.getKharifPerenialDetails(vill_code, cr_sno, kh_no,
				cr_farmeruid);
		if (perenialKharifDetails.size() > 0) {
			periStatus = " crop " + perenialKharifDetails.get(0).getCropname() + "," + "variety "
					+ perenialKharifDetails.get(0).getVarietyname() + "  booked in K-23 " + " Sy No "
					+ perenialKharifDetails.get(0).getCr_sno() + " khata no " + perenialKharifDetails.get(0).getKh_no()
					+ " name " + perenialKharifDetails.get(0).getOccupname() + " father name "
					+ perenialKharifDetails.get(0).getOccupfname() + " in "
					+ perenialKharifDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		List<CultivatorPeri> perenialRabiDetails = rep.getRabiPerenialDetails(vill_code, cr_sno, kh_no, cr_farmeruid);
		if (perenialRabiDetails.size() > 0) {
			periStatus = " crop " + perenialRabiDetails.get(0).getCropname() + "," + "variety "
					+ perenialRabiDetails.get(0).getVarietyname() + "  booked in R-23 " + " Sy No "
					+ perenialRabiDetails.get(0).getCr_sno() + " khata no " + perenialRabiDetails.get(0).getKh_no()
					+ " name " + perenialRabiDetails.get(0).getOccupname() + " father name "
					+ perenialRabiDetails.get(0).getOccupfname() + " in "
					+ perenialRabiDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

//		String totalExt = totalExtent.toString();
//		String occupantExt = occupantExtent.toString();
		if (Float.parseFloat(occupantExtent.toString()) <= Float.parseFloat(totalExtent.toString())) {
			if (culti_type.equals("O") || culti_type.equals("R")) {
				updateCultivatorDetails = rep.updateOwnerDetails(partitionName, occup_extent, culti_type, userId,
						periStatus, vill_code, booking_id, kh_no, cr_sno, cr_farmeruid, mobile);
			} else if (culti_type.equals("L")) {
				updateCultivatorDetails = rep.updateEnjoyerDetails(partitionName, occup_extent, culti_type, userId,
						periStatus, vill_code, booking_id, kh_no, cr_sno, cr_farmeruid, mobile);
			}
			if (updateCultivatorDetails > 0) {
				model.addAttribute("success", "Successfully Updated Pattadar as Cultivator.");
			} else {
				model.addAttribute("failure", "Something went wrong,please try again.");
			}

		} else {
			model.addAttribute("empty", "Occupant Extent should not exceed Total Extent.");
		}
		return "rbkroles/addupdatecultivator";
	}
	
	@PreAuthorize("hasAuthority('25')")
	@PostMapping("/cultivator/owner/details")
	public String updateOwnerDetails(HttpServletRequest httpServletRequest, HttpSession HttpSession,
			Model model) {
		String dist_code = HttpSession.getAttribute("wbdcode").toString();
		String vill_code = httpServletRequest.getParameter("cr_vcode");
		String crop_year = HttpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_season = HttpSession.getAttribute("seasonActive").toString();
		String culti_type = httpServletRequest.getParameter("cultivatorType");
		String occup_extent = httpServletRequest.getParameter("occupantExtent");
		String booking_id = httpServletRequest.getParameter("bookingId");
		String cr_sno = httpServletRequest.getParameter("crSno");
		String kh_no = httpServletRequest.getParameter("khNo");
		String cr_farmeruid = httpServletRequest.getParameter("aadharNo");
		String userId = HttpSession.getAttribute("userid").toString();
		String periStatus = "N";
		BigDecimal totalExtent = null, occupantExtent = null;
		int updateCultivatorDetails = 0;

		String partitionName = "cr_booking_partition_";
		if (Integer.parseInt(dist_code) <= 9) {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + "0" + dist_code + crop_year;

		} else {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + dist_code + crop_year;
		}
		List<CultivatorEntity> list = rep.getExtentDetails(partitionName, vill_code, cr_sno);
		if (list.size() > 0 && list.get(0).getTotalExtent() != null && list.get(0).getOccupantExtent() != null) {
			totalExtent = list.get(0).getTotalExtent();
			occupantExtent = list.get(0).getOccupantExtent();
		}

		List<CultivatorPeri> perenialKharifDetails = rep.getKharifPerenialDetails(vill_code, cr_sno, kh_no,
				cr_farmeruid);
		if (perenialKharifDetails.size() > 0) {
			periStatus = " crop " + perenialKharifDetails.get(0).getCropname() + "," + "variety "
					+ perenialKharifDetails.get(0).getVarietyname() + "  booked in K-23 " + " Sy No "
					+ perenialKharifDetails.get(0).getCr_sno() + " khata no " + perenialKharifDetails.get(0).getKh_no()
					+ " name " + perenialKharifDetails.get(0).getOccupname() + " father name "
					+ perenialKharifDetails.get(0).getOccupfname() + " in "
					+ perenialKharifDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		List<CultivatorPeri> perenialRabiDetails = rep.getRabiPerenialDetails(vill_code, cr_sno, kh_no, cr_farmeruid);
		if (perenialRabiDetails.size() > 0) {
			periStatus = " crop " + perenialRabiDetails.get(0).getCropname() + "," + "variety "
					+ perenialRabiDetails.get(0).getVarietyname() + "  booked in R-23 " + " Sy No "
					+ perenialRabiDetails.get(0).getCr_sno() + " khata no " + perenialRabiDetails.get(0).getKh_no()
					+ " name " + perenialRabiDetails.get(0).getOccupname() + " father name "
					+ perenialRabiDetails.get(0).getOccupfname() + " in "
					+ perenialRabiDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

//		String totalExt = totalExtent.toString();
//		String occupantExt = occupantExtent.toString();
		System.out.println(occupantExtent+"--------------"+totalExtent);
		if (Float.parseFloat(occupantExtent.toString()) <= Float.parseFloat(totalExtent.toString())) {
			System.out.println("==============inn");
			if (culti_type.equals("O")) {
				System.out.println("------------"+culti_type);
				updateCultivatorDetails = rep.updateOwnerDetails(partitionName, occup_extent, culti_type, userId,
						periStatus, vill_code, booking_id, kh_no, cr_sno, cr_farmeruid);
			} 
			if (updateCultivatorDetails > 0) {
				model.addAttribute("success", "Successfully Updated Pattadar as Cultivator.");
			} else {
				model.addAttribute("failure", "Something went wrong,please try again.");
			}

		} else {
			model.addAttribute("empty", "Occupant Extent should not exceed Total Extent.");
		}
		return "rbkroles/addupdatecultivator";
	}
	

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/cultivator/save")
	public String updateCultivatorDetails(HttpServletRequest httpServletRequest, HttpSession HttpSession, Model model) {
		String mobile = httpServletRequest.getParameter("mobile");
		System.out.println("mobile-----------" + mobile);
		String dist_code = HttpSession.getAttribute("wbdcode").toString();
		String mand_code = HttpSession.getAttribute("wbmcode").toString();
		String vill_code = httpServletRequest.getParameter("cr_vcode");
		String crop_year = HttpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_season = HttpSession.getAttribute("seasonActive").toString();
		String culti_type = httpServletRequest.getParameter("cultivatorType");
		Double occup_extent = Double.parseDouble(httpServletRequest.getParameter("occupantExtent").toString());
		String booking_id = httpServletRequest.getParameter("refBookingId");
		String cr_sno = httpServletRequest.getParameter("crSno");
		String kh_no = httpServletRequest.getParameter("khNo");
		String cr_farmeruid = httpServletRequest.getParameter("aadharNo");
		String oc_name = httpServletRequest.getParameter("occupname");
		String oc_fname = httpServletRequest.getParameter("occupfname");
		if (mobile.isEmpty() || mobile == null) {
			mobile = "0";
		}

		String userId = HttpSession.getAttribute("userid").toString();

		String partKey = "";
		String periStatus = "N";
		BigDecimal totalExtent = null, occupantExtent = null;
		int updateCultivatorDetails = 0;

		String partitionName = "cr_booking_partition_";
		if (Integer.parseInt(dist_code) <= 9) {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + "0" + dist_code + crop_year;
			partKey = crop_season + "0" + dist_code + crop_year;
		} else {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + dist_code + crop_year;
			partKey = crop_season + dist_code + crop_year;
		}

		List<CultivatorEntity> list = rep.getOrgDetailsForCultivator(partitionName, booking_id);
		String ocName = null;
		String fatherName = null;
		BigDecimal khNo = null;
		String crSno = null;
		String anubhavadar_name = null;
		BigDecimal anubhavadarExtent = null;
		String aadharNo = null;
		String occupname = null;
		BigDecimal mobileno = null;
		String data_src = null;
		String occupfname = null;
		String updatedby = null;
		Integer regno = null;
		BigInteger cr_wsno = null;
		String geo_reffered = null;
		BigDecimal rec_id = null;
		Integer sjointoccupant = null;
		String vs_sel = null;
		String ownerTenant = null;
		Integer empCode = 0;
		if (list.size() > 0) {
			ocName = list.get(0).getOcName();
			fatherName = list.get(0).getFatherName();
			khNo = list.get(0).getKhNo();
			crSno = list.get(0).getCrSno();
			totalExtent = list.get(0).getTotalExtent();
			anubhavadar_name = list.get(0).getAnubhavadar_name();
			anubhavadarExtent = list.get(0).getAnubhavadarExtent();
			aadharNo = list.get(0).getAadharNo();
			occupname = list.get(0).getOccupname();
			occupfname = list.get(0).getOccupfname();
			occupantExtent = list.get(0).getOccupantExtent();
			mobileno = list.get(0).getMobileno();
			data_src = list.get(0).getData_src();
			updatedby = list.get(0).getUpdatedby();
			regno = list.get(0).getRegno();
			cr_wsno = list.get(0).getCr_wsno();
			geo_reffered = list.get(0).getGeo_reffered();
			rec_id = list.get(0).getRec_id();
			sjointoccupant = list.get(0).getSjointoccupant();
			vs_sel = list.get(0).getVs_sel();
			ownerTenant = list.get(0).getOwner_tenant();
			empCode = list.get(0).getEmp_code();
		}

		List<CultivatorPeri> perenialKharifDetails = rep.getKharifPerenialDetails(vill_code, cr_sno, kh_no,
				cr_farmeruid);
		if (perenialKharifDetails.size() > 0) {
			periStatus = " crop " + perenialKharifDetails.get(0).getCropname() + "," + "variety "
					+ perenialKharifDetails.get(0).getVarietyname() + "  booked in K-23 " + " Sy No "
					+ perenialKharifDetails.get(0).getCr_sno() + " khata no " + perenialKharifDetails.get(0).getKh_no()
					+ " name " + perenialKharifDetails.get(0).getOccupname() + " father name "
					+ perenialKharifDetails.get(0).getOccupfname() + " in "
					+ perenialKharifDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		List<CultivatorPeri> perenialRabiDetails = rep.getRabiPerenialDetails(vill_code, cr_sno, kh_no, cr_farmeruid);
		if (perenialRabiDetails.size() > 0) {
			periStatus = " crop " + perenialRabiDetails.get(0).getCropname() + "," + "variety "
					+ perenialRabiDetails.get(0).getVarietyname() + "  booked in R-23 " + " Sy No "
					+ perenialRabiDetails.get(0).getCr_sno() + " khata no " + perenialRabiDetails.get(0).getKh_no()
					+ " name " + perenialRabiDetails.get(0).getOccupname() + " father name "
					+ perenialRabiDetails.get(0).getOccupfname() + " in "
					+ perenialRabiDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		String totalExt = totalExtent.toString();
		String occupantExt = occupantExtent.toString();
		char cultType = culti_type.charAt(0);
		char dataSrc = data_src.charAt(0);
		char geoReffered = geo_reffered.charAt(0);
		char vsSel = vs_sel.charAt(0);
		char ownerTent = ownerTenant.charAt(0);
		String dcode = HttpSession.getAttribute("dcode").toString();
		String mcode = HttpSession.getAttribute("mcode").toString();

		if (Float.parseFloat(occupantExt) <= Float.parseFloat(totalExt)) {
			updateCultivatorDetails = rep.updateCultivatorDetails(partitionName, ocName, fatherName, khNo.toString(),
					crSno, anubhavadar_name, anubhavadarExtent, aadharNo, occupname, occupfname, occup_extent, mobileno,
					dataSrc, cultType, userId, regno, cr_wsno, geoReffered, rec_id, sjointoccupant, vsSel, userId,
					periStatus, vill_code, booking_id, kh_no, cr_sno, cr_farmeruid, partKey, dist_code, mand_code,
					oc_name, oc_fname, crop_year, crop_season, totalExtent, ownerTent, dcode, mcode, empCode, mobile);
			if (updateCultivatorDetails > 0) {
				model.addAttribute("success", "Successfully Updated Pattadar as Cultivator.");
			} else {
				model.addAttribute("failure", "Something went wrong,please try again.");
			}
		} else {
			model.addAttribute("empty", "Occupant Extent should not exceed Total Extent.");
		}
		return "rbkroles/addupdatecultivator";
	}

	@PreAuthorize("hasAuthority('25')")
	@RequestMapping("/cultivator/ccrc/update")
	public String updateCcrcDetails(HttpServletRequest httpServletRequest, HttpSession HttpSession, Model model) {
		String mobile = httpServletRequest.getParameter("mobile");
		System.out.println("mobile-----------" + mobile);
		String dist_code = HttpSession.getAttribute("wbdcode").toString();
		String vill_code = httpServletRequest.getParameter("cr_vcode");
		String crop_year = HttpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_season = HttpSession.getAttribute("seasonActive").toString();
		String culti_type = httpServletRequest.getParameter("cultivatorType");
		String occup_extent = httpServletRequest.getParameter("occupantExtent");
		String booking_id = httpServletRequest.getParameter("bookingId");
		String cr_sno = httpServletRequest.getParameter("crSno");
		String kh_no = httpServletRequest.getParameter("khNo");
		String cr_farmeruid = httpServletRequest.getParameter("aadharNo");
		String userId = HttpSession.getAttribute("userid").toString();
		String periStatus = "N";
		BigDecimal totalExtent = null, occupantExtent = null;
		int updateCultivatorDetails = 0;
		if (mobile.isEmpty() || mobile == null) {
			mobile = "0";
		}
		String partitionName = "cr_booking_partition_";
		if (Integer.parseInt(dist_code) <= 9) {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + "0" + dist_code + crop_year;

		} else {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + dist_code + crop_year;
		}

		List<CultivatorEntity> list = rep.getExtentDetails(partitionName, vill_code, cr_sno);
		if (list.size() > 0) {
			totalExtent = list.get(0).getTotalExtent();
			occupantExtent = list.get(0).getOccupantExtent();
		}

		List<CultivatorPeri> perenialKharifDetails = rep.getKharifPerenialDetails(vill_code, cr_sno, kh_no,
				cr_farmeruid);
		if (perenialKharifDetails.size() > 0) {
			periStatus = " crop " + perenialKharifDetails.get(0).getCropname() + "," + "variety "
					+ perenialKharifDetails.get(0).getVarietyname() + "  booked in K-23 " + " Sy No "
					+ perenialKharifDetails.get(0).getCr_sno() + " khata no " + perenialKharifDetails.get(0).getKh_no()
					+ " name " + perenialKharifDetails.get(0).getOccupname() + " father name "
					+ perenialKharifDetails.get(0).getOccupfname() + " in "
					+ perenialKharifDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		List<CultivatorPeri> perenialRabiDetails = rep.getRabiPerenialDetails(vill_code, cr_sno, kh_no, cr_farmeruid);
		if (perenialRabiDetails.size() > 0) {
			periStatus = " crop " + perenialRabiDetails.get(0).getCropname() + "," + "variety "
					+ perenialRabiDetails.get(0).getVarietyname() + "  booked in R-23 " + " Sy No "
					+ perenialRabiDetails.get(0).getCr_sno() + " khata no " + perenialRabiDetails.get(0).getKh_no()
					+ " name " + perenialRabiDetails.get(0).getOccupname() + " father name "
					+ perenialRabiDetails.get(0).getOccupfname() + " in "
					+ perenialRabiDetails.get(0).getCr_mix_unmix_ext() + " (acres)";
		}

		String totalExt = totalExtent.toString();
		String occupantExt = occupantExtent.toString();
		if (Float.parseFloat(occupantExtent.toString()) <= Float.parseFloat(totalExtent.toString())) {
			if (culti_type.equals("C")) {
				updateCultivatorDetails = rep.updateCcrcDetails(partitionName, occup_extent, culti_type, userId,
						periStatus, vill_code, booking_id, kh_no, cr_sno, cr_farmeruid, mobile);
			}
			if (updateCultivatorDetails > 0) {
				model.addAttribute("success", "Successfully Updated Pattadar as Cultivator.");
			} else {
				model.addAttribute("failure", "Something went wrong,please try again.");
			}
		} else {
			model.addAttribute("empty", "Occupant Extent should not exceed Total Extent.");
		}
		return "rbkroles/addupdatecultivator";
	}

}
