package com.ecrops.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.AllocationSurveyNoModel;
import com.ecrops.entity.AuthrbkvaavroekycModel;
import com.ecrops.entity.CropbookingdetVAModel;
import com.ecrops.entity.CropnamesModel;
import com.ecrops.entity.DataSrcWiseBkedExtModel;
import com.ecrops.entity.DataSrcwiseCntModel;
import com.ecrops.entity.RBKVAAAuthComplModel;
import com.ecrops.entity.RbkEkycCompltdModel;
import com.ecrops.entity.RbkVaaAuthPendingModel;
import com.ecrops.entity.RbkVroAuthComplModel;
import com.ecrops.entity.RbkVroAuthPend;
import com.ecrops.entity.RepPerFrwdDetModel;
import com.ecrops.entity.RepRbkekycPendingModel;
import com.ecrops.entity.RepVaaFinalListModel;
import com.ecrops.entity.Rep_SearchFormbyKhsnoVAModel;
import com.ecrops.entity.Rep_StatusOnSurveyNo_extModel;
import com.ecrops.entity.Rep_VsPerennialModel;
import com.ecrops.entity.Rep_vaa_draft_listModel;
import com.ecrops.entity.SearchByUIDModel;
import com.ecrops.entity.SearchbyBookingIdModel;
import com.ecrops.entity.SuperChkUpdStatusModel;
import com.ecrops.entity.SuperChk_rejReportModel;
import com.ecrops.entity.SurveyNodetIntfModel;
import com.ecrops.entity.VillageSec;
import com.ecrops.entity.VroRejdetModel;
import com.ecrops.entity.eFishVaaDetModel;
import com.ecrops.entity.nonwebViewModel;
import com.ecrops.entity.pendingphyackModel;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.service.AuthrbkvaavroekycService;
import com.ecrops.service.CropBookingDetVAIntfService;
import com.ecrops.service.Cropnamesrvc;
import com.ecrops.service.DataSourceWiseBookedExtService;
import com.ecrops.service.DataSrcWiseCntService;
import com.ecrops.service.GetCropYearService;
import com.ecrops.service.GetVillageService;
import com.ecrops.service.GetvroRejIntfDetService;
import com.ecrops.service.NonwebViewService;
import com.ecrops.service.PendPhyAckService;
import com.ecrops.service.RbkEkycCompltdService;
import com.ecrops.service.RbkVaaAuthComplService;
import com.ecrops.service.RbkVaaAuthPendingService;
import com.ecrops.service.RbkVroAuthComplService;
import com.ecrops.service.RbkVroAuthPendingService;
//import com.ecrops.service.PerFrwdDetService;
import com.ecrops.service.RepPerFrwdDetService;
import com.ecrops.service.RepPerNotFrwdDetService;
import com.ecrops.service.RepRbkekycPendingService;
import com.ecrops.service.RepSurveyNodetIntfService;
import com.ecrops.service.RepVaaFinalListService;
import com.ecrops.service.Rep_VsPerennialService;
import com.ecrops.service.Rep_searchFormby_KhsnoVAService;
import com.ecrops.service.Rep_vaa_draft_listService;
import com.ecrops.service.SearchByUIDService;
import com.ecrops.service.SearchbyBookingIdService;
import com.ecrops.service.StatusonSurveyNo_extService;
import com.ecrops.service.SuperChkUpdStatusService;
import com.ecrops.service.efishVaaDetService;
import com.ecrops.service.superChk_rejService;

@PreAuthorize("hasAuthority('25')")
@Controller
public class RBKController {

	@PersistenceContext
	private EntityManager entityManager;
//	@Autowired
//	private MasterFunctions masterFunctions;
	@Autowired
	private GetCropYearService getCropYearService;
	@Autowired
	private GetvroRejIntfDetService getvroRejIntfDetService;
	@Autowired
	private ActiveSeasonService activeSeasonService;
	@Autowired
	private superChk_rejService superChkrejService;
	@Autowired
	private RbkVaaAuthComplService rbkVaaAuthComplService;
	@Autowired
	private RbkVaaAuthPendingService rbkVaaAuthPendingService;
	@Autowired
	private RbkVroAuthComplService rbkVroAuthComplService;
	@Autowired
	private RbkVroAuthPendingService rbkVroAuthPendingService;
	@Autowired
	private NonwebViewService nonwebViewService;
	@Autowired
	private AuthrbkvaavroekycService authrbkvaavroekycService;
	@Autowired
	private DataSourceWiseBookedExtService dataSrcWisebkedExtService;

	@Autowired
	private GetVillageService getVillService;
	@Autowired
	private RepPerNotFrwdDetService repPerNotFrwdDetService;
	@Autowired
	private efishVaaDetService efishVaaDetservice;
	@Autowired
	private StatusonSurveyNo_extService stsonSrvyNo_extSrvc;
	@Autowired
	private SuperChkUpdStatusService superChkUpdStsService;
	@Autowired
	private CropBookingDetVAIntfService cropbkingdetSrvc;
	@Autowired
	private DataSrcWiseCntService dataSrcWiseCntSrvc;
	@Autowired
	private PendPhyAckService pendPhyAckSrvc;
	@Autowired
	private RepVaaFinalListService repVaaFinalList;
	@Autowired
	private RepSurveyNodetIntfService repSurveyNodetIntfsrvc;
	@Autowired
	private RepRbkekycPendingService repRbkekycPendingSrvc;
	@Autowired
	private Rep_searchFormby_KhsnoVAService srchformbyKhsnoVASrvc;
	@Autowired
	private SearchbyBookingIdService searchbyBkingIdSrvc;
	@Autowired
	private SearchByUIDService searchByUIDSrvc;
	@Autowired
	private Cropnamesrvc cropnamesSrvc;
	@Autowired
	private Rep_VsPerennialService VsPerennialSrvc;
	private final Rep_vaa_draft_listService vaadraftlistSrvc;
	@Autowired
	private RepPerFrwdDetService perFrwdDetService;

	RBKController(Rep_vaa_draft_listService vaadraftlistSrvc) {
		this.vaadraftlistSrvc = vaadraftlistSrvc;
	}
//	@Autowired
//	private Rep_vaa_draft_listService  vaadraftlistSrvc;
//	

	// =======================REPORT ALLOCATION SURVEY NUMBERS
	// INVOICE=========================

		@GetMapping("/AlloSrvyNo")
	public String detailPer(Model model) {
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/RepAllocatedSurveyNo";
	}

	@PostMapping("/RepAlloSrvyNo")
	public String repAlloSrvyNoReport(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.mandalCode(mcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.year(cropyear);
		System.out.println("val2----->" + val2);

		if (val1 && val2) {

			String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode:::" + wbdcode);

			if (wbdcode.length() < 2) {
				wbdcode = '0' + wbdcode;
			}
			t1 = "cr_booking_partition_" + season + wbdcode + cropyear;

			if (cropyear1 >= 2023) {
				t1 = "ecrop" + cropyear + "." + t1;
			} else {
				t1 = "cr_booking_partition_" + season + wbdcode + cropyear;
			}
			System.out.println("t1=======>" + t1);
			List<AllocationSurveyNoModel> list = null;
			try {
				list = getCropYearService.getAllSurveryNos(t1, Integer.parseInt(mcode), userid, cropyear1, season);
				System.out.println("--------------------" + list.size());
				double a = 0.00;

				for (AllocationSurveyNoModel bean : list) {
  			System.out.println("bean.---->"+bean.getTot_extent());
					a = a + Double.parseDouble(bean.getTot_extent());
					
				}	
				a = Math.round(a * 100.0) / 100.0;
				model.addAttribute("Total", a);
				System.out.println("list=>" + list.size());

				if (list.size() == 0) {
					
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "rbkroles/RepAllocatedSurveyNo";
	}

	// ================================================

	@GetMapping("/DataSrcWiseBkedExt")
	public String detailDataSrcWiseBkedExt(Model model) {
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/DataSourceWiseBookedExt";
	}

	@PostMapping("/RepDataSrcWiseBkedExt")
	public String detDataSrcWiseBkedExt(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		System.out.println("=================");

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.season(season);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.year(cropyear);
		System.out.println("val3--->" + val3);

		if (val1 && val2 && val3) {

			String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode:::" + wbdcode);

			if (wbdcode.length() < 2) {
				wbdcode = '0' + wbdcode;
			}
			t1 = "cr_data_src_det_rbk_mv_" + season + cropyear;
			if (cropyear1 >= 2023  && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				t1 = "ecrop" + cropyear + "." + t1;

			} else {
				t1 = "cr_data_src_det_rbk_mv_" + season + cropyear;
			}

			System.out.println("t1=====>" + t1);
			List<DataSrcWiseBkedExtModel> list = null;
			try {
//				if (cropyear1 == 2022) {
//					list = null;
//					model.addAttribute("NoRecordsFound", true);
//				} else if (cropyear1 == 2023 && season.equals("S")) {
//					list = null;
//					model.addAttribute("NoRecordsFound", true);
//				} else {

				list = dataSrcWisebkedExtService.getdatasrcwisebkedextdet(t1, userid);
				if (list.size() > 0) {
					model.addAttribute("list", list);

					int a = 0, a2 = 0, a4 = 0, a6 = 0, a8 = 0, a10 = 0, a12 = 0;
					float a1 = 0, a3 = 0, a5 = 0, a7 = 0, a9 = 0, a11 = 0, a13 = 0;

					for (DataSrcWiseBkedExtModel bean : list) {
//	  			System.out.println("bean.getTotfarmercount()---->"+bean.getTotfarmercount());
						a = a + Integer.parseInt(bean.getWeb_farmers());
						a1 = a1 + Float.parseFloat(bean.getWeb_ext());
						a2 = a2 + Integer.parseInt(bean.getNweb_farmers());
						a3 = a3 + Float.parseFloat(bean.getNweb_ext());
						a4 = a4 + Integer.parseInt(bean.getCcrc_farmers());
						a5 = a5 + Float.parseFloat(bean.getCcrc_ext());
						a6 = a6 + Integer.parseInt(bean.getRofr_farmers());
						a7 = a7 + Float.parseFloat(bean.getRofr_ext());
						a8 = a8 + Integer.parseInt(bean.getUsus_farmers());
						a10 = Integer.parseInt(bean.getWeb_farmers()) + Integer.parseInt(bean.getNweb_farmers())
								+ Integer.parseInt(bean.getCcrc_farmers()) + Integer.parseInt(bean.getRofr_farmers())
								+ Integer.parseInt(bean.getUsus_farmers());
						a11 = Float.parseFloat(bean.getWeb_ext()) + Float.parseFloat(bean.getNweb_ext())
								+ Float.parseFloat(bean.getCcrc_ext()) + Float.parseFloat(bean.getRofr_ext())
								+ Float.parseFloat(bean.getUsus_ext());
						a12 = (int) (a12 + a10);
						a13 = a13 + a11;

					}
					model.addAttribute("Web_farmers", a);
					model.addAttribute("Web_ext", a1);
					model.addAttribute("Nweb_farmers", a2);
					model.addAttribute("Nweb_ext", a3);
					model.addAttribute("Ccrc_farmers", a4);
					model.addAttribute("Ccrc_ext", a5);
					model.addAttribute("Rofr_farmers", a6);
					model.addAttribute("Rofr_ext", a7);
					model.addAttribute("Usus_farmers", a8);
					model.addAttribute("Usus_ext", a9);
					model.addAttribute("a12", a12);
					model.addAttribute("a13", a13);
					model.addAttribute("a10", a10);
					model.addAttribute("a11", a11);
					System.out.println("list=>" + list.size());

				} else {
					model.addAttribute("NoRecordsFound", true);
				}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "rbkroles/DataSourceWiseBookedExt";

	}

	// ==================VRO REJECT==============================
	@GetMapping("/vroRejIntf")
	public String getvrorejdet(Model model) {
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/vroRejIntf";
	}

	@PostMapping("/RepvroRejIntf")
	public String getvrorejdetails(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		System.out.println("=================");
		// String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_year= httpSession.getAttribute("ACTIVEYEAR").toString();

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String village = (String) httpSession.getAttribute("vscode");
		System.out.println("village:::" + village);
		int vill = Integer.parseInt(village);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		// int year=Integer.parseInt(cropyear);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.villageCode(village);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.season(season);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.year(cropyear);
		System.out.println("val3----->" + val3);

		if (val1 && val2 && val3) {

			t1 = "vro_rej_details";
//			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) 
			t1 = "ecrop" + crop_year + "." + t1;

			System.out.println("t1=====>" + t1);

			List<VroRejdetModel> list = null;
			try {
				list = getvroRejIntfDetService.getVroRejdets(t1, vill, season, cropyear1);
				if (list.size() > 0) {
					model.addAttribute("list", list);

//				System.out.println("list msg");
				} else {
					model.addAttribute("msg", "NO DATA FOUND");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "rbkroles/vroRejIntf";
	}

	@GetMapping("/superchkrej")
	public String getsuperchkrejdet(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String tab, crpses, season, tab2, year, activeYear;

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		return "rbkroles/superChk_rejReport";
	}

	@PostMapping("/Repsuperchkrej")
	public String getsuperchkrejdetails(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		System.out.println("=================");

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);
		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String village = (String) httpSession.getAttribute("vscode");
		System.out.println("village:::" + village);

		t1 = "vro_rej_details";
		String superchkrejmv = "superchk_rej_"+ season + cropyear + "_mv";

		if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
			t1 = "ecrop" + cropyear + "." + t1;
			superchkrejmv = "ecrop" + cropyear + "." + superchkrejmv ;
			System.out.println("superchkrejmv" + superchkrejmv);

		} else {
			t1 = "vro_rej_details";

			System.out.println("t1=====>" + t1);
		}

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode::" + wbdcode);
		int wbdcode1 = Integer.parseInt(wbdcode);

		String wbmcode = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("wbmcode::" + wbmcode);
		int wbmcode1 = Integer.parseInt(wbmcode);

		System.out.println("wbdcode1----->" + wbdcode1);
		System.out.println("wbmcode1----->" + wbmcode1);

		Integer rbkcode = Integer.parseInt(userid.substring(4));
		System.out.println("rbkcode:" + rbkcode);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(wbdcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.wbmandcode(wbmcode);
		System.out.println("val2----->" + val2);

		if (val1 && val2) {

			List<SuperChk_rejReportModel> list = null;
			try {
				list = superChkrejService.getsuperchkrejdet(superchkrejmv, wbdcode1, wbmcode1, rbkcode);

				System.out.println("list=>" + list.size());

				if (list.isEmpty()) {
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return "rbkroles/superChk_rejReport";
	}

	@GetMapping("/VaaAuthCompl")
	public String repVaaAuthCompl(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String tab, crpses, season, tab2, year, activeYear;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/Rep_RBK_vaaauth_completed";
	}

	@PostMapping("/RepVaaAuthCompl")
	public String repVaaAuthComplDet(Model model, HttpSession httpSession, HttpServletRequest request) {

		String wbdcode = "", t1, wbmcode = "";
		System.out.println("=================");
		// String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String season = crYear.split("@")[0];
		System.out.println("season::" + season);

		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		System.out.println("cropyear:::" + cropyear);

//		String dcode = (String) httpSession.getAttribute("dcode");
//		System.out.println("dcode:::" + dcode);
//
//		String mcode = (String) httpSession.getAttribute("mcode");
//		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

//		vscode = (String) httpSession.getAttribute("vscode");
//		System.out.println("vscode:::" + vscode);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		wbmcode = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("wbmcode:::" + wbmcode);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(wbdcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.season(season);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.year(cropyear);
		System.out.println("val3---->" + val3);

		if (val1 && val2 && val3) {
//			wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
//			wbdcode= (String) httpSession.getAttribute("wbdcode");
//			System.out.println("wbdcode:::" + wbdcode);
			int intwbdcode = Integer.parseInt(wbdcode);

			if (intwbdcode >= 0 && intwbdcode <= 9) {
				t1 = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
			} else {
				t1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
			}

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				t1 = "ecrop" + cropyear + "." + t1;

			} 
			List<RBKVAAAuthComplModel> list = null;
			try {

				list = rbkVaaAuthComplService.getVaaAuthComplDet(t1, userid);
				System.out.println("list=>" + list.size());
				if (list.size() > 0) {
					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);
				}

			} catch (Exception e) {

			}
		}
		return "rbkroles/Rep_RBK_vaaauth_completed";

	}

	@GetMapping("/VaaAuthPend")
	public String repVaaAuthPend(Model model) {
		String tab, crpses, season, tab2, year;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/Rep_RBK_vaaauth_pendingIntf";
	}

	@PostMapping("/RepVaaAuthPend")
	public String repVaaAuthPendDet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1, wbdcode;

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);
		int intwbdcode = Integer.parseInt(wbdcode);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.season(season);
		System.out.println("val2----->" + val2);
		boolean val3 = regexpmethod.year(cropyear);
		System.out.println("val3----->" + val3);

		if (val1 && val2 && val3) {

			if (intwbdcode >= 0 && intwbdcode <= 9) {
				t1 = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;

			} else {
				t1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
				boolean b = season.equalsIgnoreCase("R");
				System.out.println(b);

			}
			System.out.println("tname is :" + t1);
			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				t1 = "ecrop" + cropyear + "." + t1;

			} 
			List<RbkVaaAuthPendingModel> list = null;

			try {

				list = rbkVaaAuthPendingService.getVaaAuthPendingDet(t1, userid);
				if (list.size() > 0) {
					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "rbkroles/Rep_RBK_vaaauth_pendingIntf";
	}

	@GetMapping("/VroAuthCompl")
	public String repVroAuthCompl(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String tab, crpses, season, tab2, year, activeYear;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/Rep_RBK_vroauth_completedIntf";
	}

	@PostMapping("/RepVroAuthCompl")
	public String repVroAuthComplDet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1, wbdcode;

		System.out.println("=================");

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);
		int intwbdcode = Integer.parseInt(wbdcode);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		boolean val2 = regexpmethod.season(season);
		boolean val3 = regexpmethod.year(cropyear);

		if (val1 && val2 && val3) {

			if (intwbdcode >= 0 && intwbdcode <= 9) {
				t1 = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
			} else {
				t1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
			}
			if (cropyear1 >= 2023&& !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				t1 = "ecrop" + cropyear + "." + t1;
			} else {
				t1 = t1;
			}
			List<RbkVroAuthComplModel> list = null;
			try {
				list = rbkVroAuthComplService.getVroAuthComplDet(t1, userid);
				System.out.println("list=>" + list.size());

				if (list.isEmpty()) {
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "rbkroles/Rep_RBK_vroauth_completedIntf";
	}

	@GetMapping("/VroAuthPend")
	public String repVroAuthPend(Model model) {
		String tab, crpses, season, tab2, year, activeYear;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/Rep_RBK_vroauth_pendingIntf";
	}

	@PostMapping("/RepVroAuthPend")
	public String repVroAuthPendDet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		String wbdcode;
		System.out.println("=================");
		// String activeYear = httpSession.getAttribute("ACTIVEYEAR").toString();

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);

		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		int intwbdcode = Integer.parseInt(wbdcode);
		if (intwbdcode >= 0 && intwbdcode <= 9) {
			t1 = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
		} else {
			t1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
		}

		if (cropyear1 >= 2023&& !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
			t1 = "ecrop" + cropyear + "." + t1;
		} 
		List<RbkVroAuthPend> list = null;
		try {
			list = rbkVroAuthPendingService.getVroAuthPendingDet(t1, userid);
			System.out.println("list=>" + list.size());
//		model.addAttribute("list", list);
			if (list.size() > 0) {
				model.addAttribute("list", list);
			} else {
				model.addAttribute("noResults", true);
			}
		} catch (Exception e) {

		}
		return "rbkroles/Rep_RBK_vroauth_pendingIntf";
	}

	@GetMapping("/nwbview")
	public String nwbview(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String tab, crpses, season, tab2, year, activeYear;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/nonwebView";
	}

	@PostMapping("/nwbviewrep")
	public String nwbviewrep(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		System.out.println("=================");

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		System.out.println("season=>" + season);
		System.out.println("cropyear=>" + cropyear);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1--->" + val1);
		boolean val2 = regexpmethod.mandalCode(mcode);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3---->" + val3);
		boolean val4 = regexpmethod.year(cropyear);
		System.out.println("val4----->" + val4);

		if (val1 && val2 && val3 && val4) {

			t1 = "pattmast_nonwebland";

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				t1 = "ecrop" + cropyear + "." + t1;
			} else {
				t1 = "pattmast_nonwebland";

			}
			System.out.println("t1=======>" + t1);
			List<nonwebViewModel> list = null;
			try {

				list = nonwebViewService.getnonwebviewdet(t1, Integer.parseInt(dcode), Integer.parseInt(mcode), userid,
						season, cropyear1);

				System.out.println("list=>" + list.size());
				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}

			} catch (Exception e) {
				System.out.println("exception  e------->>>" + e);
			}
		}
		return "rbkroles/nonwebView";
	}

	@GetMapping("/Arbkvaavroekyc")
	public String nwbview(Model model) {
		String tab, crpses, season, tab2, year, activeYear;
		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/Rep_Auth_rbk_vaavroekyc";
	}

	@PostMapping("/Authrbkvaavroekyc")
	public String Authrbkvaavroekyc(Model model, HttpSession httpSession, HttpServletRequest request) {
		String t1;
		System.out.println("=================");

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];

		int cropyear1 = Integer.parseInt(cropyear);

		System.out.println("season=>" + season);
		System.out.println("cropyear=>" + cropyear);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.isNumber(vscode);
		boolean val2 = regexpmethod.season(season);
		boolean val3 = regexpmethod.year(cropyear);

		if (val1 && val2 && val3) {

			String tab = "rep_cr_authdetails_vill_";

//		t1 = "rep_cr_authdetails_vill_";

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab = "ecrop" + cropyear + "." + tab + season + cropyear;
			} else {

				tab = tab + season + cropyear;
			}

			try {

				List<AuthrbkvaavroekycModel> list = authrbkvaavroekycService.getAuthrbkvaavroekycdet(tab,
						Integer.parseInt(vscode), cropyear1, season);

				int a = 0, a1 = 0, a3 = 0, a5 = 0, a7 = 0, a8 = 0;
				double a2 = 0.0, a4 = 0.0, a6 = 0.0, a9 = 0.0;
				for (AuthrbkvaavroekycModel bean : list) {
					System.out.println("bean.getTotfarmercount()---->" + bean.getTotfarmercount());
					a = a + Integer.parseInt(bean.getTotfarmercount());
					a1 = a1 + Integer.parseInt(bean.getTotalbookings());
					a2 = a2 + Double.parseDouble(bean.getTotextent());
					a3 = a3 + Integer.parseInt(bean.getVaaauthcount());
					a4 = a4 + Double.parseDouble(bean.getVaaauthextent());
					a5 = a5 + Integer.parseInt(bean.getVroauthcount());
					a6 = a6 + Double.parseDouble(bean.getVroauthextent());
					a7 = a7 + Integer.parseInt(bean.getTotekycbookings());
					a8 = a8 + Integer.parseInt(bean.getEkycfarmercount());
					a9 = a9 + Double.parseDouble(bean.getEkycbookedext());

				}
				System.out.println("a---->" + a);

				model.addAttribute("totfarmercount", a);
				model.addAttribute("Totalbookings", a1);
				model.addAttribute("totextent", a2);
				model.addAttribute("Vaaauthcount", a3);
				model.addAttribute("Vaaauthextent", a4);
				model.addAttribute("Vroauthcount", a5);
				model.addAttribute("Vroauthextent", a6);
				model.addAttribute("Totekycbookings", a7);
				model.addAttribute("Ekycfarmercount", a8);
				model.addAttribute("Ekycbookedext", a9);

				System.out.println("list=>" + list.size());

				if (list.size() > 0) {

					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return "rbkroles/Rep_Auth_rbk_vaavroekyc";
	}

	@GetMapping("/Perfwd")
	public String perfwddet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/RepPerFrwdDet";
	}

	@PostMapping("/RepPerfwd")
	public String detailPer(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String season;
		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		// String activeYear = session.getAttribute("ACTIVEYEAR").toString();
		String crpses = "", year = "", wbdcode = "", tab = "", tab2 = "";

		String vcode = httpServletRequest.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		crpses = httpServletRequest.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);

		year = crpses.split("@")[1];
		System.out.println("year==" + year);
		int year1 = Integer.parseInt(year);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.villageCode(vcode);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3---->" + val3);
		boolean val4 = regexpmethod.year(year);
		System.out.println("val4---->" + val4);

		if (val1 && val2 && val3 && val4) {

//			wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
			wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode::" + wbdcode);
			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			tab = "peri_" + season + year;
			tab2 = "cr_details_" + season + wbdcode + year;
			if (year1 >= 2023 && !(year1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab = "ecrop" + year + "." + tab;
				tab2 = "ecrop" + year + "." + tab2;

				System.out.println("tab-----------------------" + tab);
				System.out.println("tab2==================" + tab2);

			} else {
				tab = "peri_" + season + year;
				tab2 = "cr_details_" + season + wbdcode + year;
				System.out.println("tab==================" + tab);
				System.out.println("tab2==================" + tab2);

			}
			List<RepPerFrwdDetModel> list = null;
			try {

				list = perFrwdDetService.getPerFrwdDet(tab, tab2, vcode1);

				System.out.println("list=>" + list.size());
				if (list.isEmpty()) {
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}

			} catch (Exception e) {
				model.addAttribute("NoRecordsFound", true);
			}
		}
		return "rbkroles/RepPerFrwdDet";
	}

	@GetMapping("PerNotFrwdDet")
	public String perNotfwddet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/RepPerennialNotForwardedDetails";
	}

	@PostMapping("/RepPerNotfwd")
	public String repPerNotfwd(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String season;
		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		String crpses = "", year = "", wbdcode = "", tab = "", tab2 = "";

		String vcode = httpServletRequest.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		crpses = httpServletRequest.getParameter("crYear");

		season = crpses.split("@")[0]; // System.out.println("season==" + season);
		year = crpses.split("@")[1]; // System.out.println("year==" + year);
		int year1 = Integer.parseInt(year);
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.villageCode(vcode);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3--->" + val3);
		boolean val4 = regexpmethod.year(year);
		System.out.println("val4----->" + val4);

		if (val1 && val2 && val3 && val4) {

			wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode:::" + wbdcode);

//			wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
//			System.out.println("wbdcode::" + wbdcode);
			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			tab = "peri_" + season + year;
			tab2 = "cr_details_" + season + wbdcode + year;
			if (year1 >= 2023 && !(year1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab = "ecrop" + year + "." + tab;
				tab2 = "ecrop" + year + "." + tab2;

				System.out.println("tab-----------------------" + tab);
				System.out.println("tab2==================" + tab2);

			} else {
				tab = "peri_" + season + year;
				tab2 = "cr_details_" + season + wbdcode + year;
				System.out.println("tab==================" + tab);
				System.out.println("tab2==================" + tab2);

			}
			List<RepPerFrwdDetModel> list = null;
			try {
				list = repPerNotFrwdDetService.getPerNotFrwdDet(tab, tab2, vcode1);

				System.out.println("list=>" + list.size());
				if (list.isEmpty()) {
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}
			} catch (Exception e) {
				model.addAttribute("NoRecordsFound", true);
			}
		}
		return "rbkroles/RepPerennialNotForwardedDetails";

	}

	@GetMapping("StatusOnSurveyNoExt")
	public String StatusonSurveyExtDet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/Rep_statusOnSurveyNo_ext_Intf";
	}

		@PreAuthorize("hasAuthority('25')")
	@PostMapping("/RepStatusOnSurveyNoExt")

	public String RepStatusOnSurveyNoExtDet(Model model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {

		try {
			String season;
			String mcode = (String) httpSession.getAttribute("mcode");
			System.out.println("mcode:::" + mcode);

			String vscode = (String) httpSession.getAttribute("vscode");
			System.out.println("vscode:::" + vscode);
			String dcode = (String) httpSession.getAttribute("dcode");
			System.out.println("dcode:::" + dcode);
			Integer wbdcode = (Integer) httpSession.getAttribute("wbdcode");
			System.out.println("wbdcode---->" + wbdcode);
//		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
//		model.addAttribute("activeseason", activeSeason);

			List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
			List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
			model.addAttribute("activeseason", activeSeason);

			List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
			model.addAttribute("villname", villname);

			// String activeYear = session.getAttribute("ACTIVEYEAR").toString();
			String crpses = "", cropyear = "", tab = "", tab2 = "", activeYear = "";

			String vcode = httpServletRequest.getParameter("vcode");
			System.out.println("vcode=====>" + vcode);
			Integer vcode1 = Integer.parseInt(vcode);

			crpses = httpServletRequest.getParameter("crYear");

			season = crpses.split("@")[0]; // System.out.println("season==" + season);
			cropyear = crpses.split("@")[1]; // System.out.println("year==" + year);

			int cropyear1 = Integer.parseInt(cropyear);
			RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

			boolean val1 = regexpmethod.districtCode(dcode);
			System.out.println("val1------>" + val1);
			boolean val2 = regexpmethod.villageCode(vcode);
			System.out.println("val2====>" + val2);
			boolean val3 = regexpmethod.season(season);
			System.out.println("val3----->" + val3);
			boolean val4 = regexpmethod.year(cropyear);
			System.out.println("val4---->" + val4);

			if (val1 && val2 && val3 && val4) {

				String tname1 = "", tname2 = "", tname3 = "";
				if (wbdcode >= 0 && wbdcode <= 9) {
					tname1 = "pattadarmast_wb_partition_" + season + "0" + wbdcode + cropyear;
					System.out.println("tname1" + tname1);

					tname2 = "cr_booking_partition_" + season + "0" + wbdcode + cropyear;
					System.out.println("tname2" + tname2);

					tname3 = "cr_details_" + season + "0" + wbdcode + cropyear;
					System.out.println("tname3" + tname3);

				} else {
					tname1 = "pattadarmast_wb_partition_" + season + wbdcode + cropyear;
					tname2 = "cr_booking_partition_" + season + wbdcode + cropyear;
					tname3 = "cr_details_" + season + wbdcode + cropyear;
				}

				if (cropyear1 >= 2023  && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
					tname1 = "ecrop" + cropyear + "." + tname1;
					System.out.println("tname1--->" + tname1);
					tname2 = "ecrop" + cropyear + "." + tname2;
					System.out.println("tname2--->" + tname2);
					tname3 = "ecrop" + cropyear + "." + tname3;
					System.out.println("tname3--->" + tname3);

				} else {
					tname1 = "pattadarmast_wb_partition_" + season + wbdcode + cropyear;
					tname2 = "cr_booking_partition_" + season + wbdcode + cropyear;
					tname3 = "cr_details_" + season + wbdcode + cropyear;

				}
				List<Rep_StatusOnSurveyNo_extModel> list = null;
				try {
					list = stsonSrvyNo_extSrvc.getStatusonSurveyNo_Ext(tname1, tname2, tname3, vcode1);

					double a1 = 0.00, a2 = 0.00, a3 = 0.00, a4 = 0.00, a5 = 0.00, a6 = 0.00;
					System.out.println("list size=>" + list.size());
					for (Rep_StatusOnSurveyNo_extModel bean : list) {

						a1 = a1 + Double.parseDouble(bean.getTot_extent());
						// System.out.println("a1=========>" +a1);
						a2 = a2 + Double.parseDouble(bean.getOccup_extent());
						// System.out.println("a2=========>" +a2);
						String crBookingTotExtent = bean.getCr_booking_tot_extent();

						if (!crBookingTotExtent.isEmpty()) {
							a3 = a3 + Double.parseDouble(bean.getCr_booking_tot_extent());
						}
						String crBookingOccupExtent = bean.getCr_booking_occupant_extent();
						if (!crBookingTotExtent.isEmpty()) {
							a4 = a4 + Double.parseDouble(bean.getCr_booking_occupant_extent());
						}
						String crDetailstotExtent = bean.getCr_details_tot_extent();
						if (!crDetailstotExtent.isEmpty()) {

							a5 = a5 + Double.parseDouble(bean.getCr_details_tot_extent());
						}
						String crDetailsOccupExtent = bean.getCr_details_occupant_extent();

						if (!crDetailsOccupExtent.isEmpty()) {

							a6 = a6 + Double.parseDouble(bean.getCr_details_occupant_extent());
						}
					}
					System.out.println("a1---->" + a1);
					double A1 = a1;
					double roundedValuea1 = Math.round(A1 * 100.0) / 100.0;
					model.addAttribute("roundedValuea1", roundedValuea1);
					System.out.println("roundedValuea1--->" + roundedValuea1);

					System.out.println("a2====>" + a2);
					double A2 = a2;
					double roundedValuea2 = Math.round(A2 * 100.0) / 100.0;
					model.addAttribute("roundedValuea2", roundedValuea2);
					System.out.println("roundedValuea2--->" + roundedValuea2);

					System.out.println("a3====>" + a3);

					double A3 = a3;
					double roundedValuea3 = Math.round(A3 * 100.0) / 100.0;
					model.addAttribute("roundedValuea3", roundedValuea3);
					System.out.println("roundedValuea3--->" + roundedValuea3);

//				model.addAttribute("Crbkingtotextent", a3);
					System.out.println("a4====>" + a4);

					double A4 = a4;
					double roundedValuea4 = Math.round(A4 * 100.0) / 100.0;
					model.addAttribute("roundedValuea4", roundedValuea4);
					System.out.println("roundedValuea4--->" + roundedValuea4);

//				model.addAttribute("Crbkingoccext", a4);

					System.out.println("a5====>" + a5);

					double A5 = a5;
					double roundedValuea5 = Math.round(A5 * 100.0) / 100.0;
					model.addAttribute("roundedValuea5", roundedValuea5);
					System.out.println("roundedValuea5--->" + roundedValuea5);

//					model.addAttribute("Crdtlstotext", a5);
					
					
					System.out.println("a6====>" + a6);

					double A6 = a6;
					double roundedValuea6 = Math.round(A6 * 100.0) / 100.0;
					model.addAttribute("roundedValuea6", roundedValuea6);
					System.out.println("roundedValuea6--->" + roundedValuea6);
		
					
					
					
					model.addAttribute("Crdtlsoccpext", a6);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("NoRecordsFound", true);
				}

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			}
		} catch (NumberFormatException e) {
			model.addAttribute("NoRecordsFound", true);
			e.printStackTrace();
		}

		return "rbkroles/Rep_statusOnSurveyNo_ext_Intf";
	}
		@GetMapping("efishvaadets")
		public String efishvaadets(Model model, HttpSession httpSession) {

			List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
			model.addAttribute("activeseason", activeSeason);

			String vscode = (String) httpSession.getAttribute("vscode");
			System.out.println("vscode:::" + vscode);

			List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
			model.addAttribute("villname", villname);

			return "rbkroles/Rep_efish_Vaa_dtlsintf";
		}

	@PostMapping("/Repefishvaadets")
	public String Repefishvaadets(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String season;
		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		// String activeYear = session.getAttribute("ACTIVEYEAR").toString();
		String crpses = "", cropyear = "", wbdcode = "", tab = "", tab2 = "", activeYear = "";

		String vcode = httpServletRequest.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		crpses = httpServletRequest.getParameter("crYear");

		season = crpses.split("@")[0]; // System.out.println("season==" + season);
		cropyear = crpses.split("@")[1]; // System.out.println("year==" + year);
		int cropyear1 = Integer.parseInt(cropyear);
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1----->" + val1);
		boolean val2 = regexpmethod.mandalCode(mcode);
		System.out.println("val2----->" + val2);
		boolean val3 = regexpmethod.villageCode(vcode);
		System.out.println("val3----->" + val3);

		if (val1 && val2 && val3) {

			tab = "cr_details_efish";
			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab = "ecrop" + cropyear + "." + tab + "_" + cropyear;
				List<eFishVaaDetModel> list = efishVaaDetservice.getefishVaaDet(Integer.parseInt(dcode),
						Integer.parseInt(mcode), vcode1, tab);
				if (list.isEmpty() || list.size()==0) {
					model.addAttribute("NoRecordsFound", true);

				} else {
					model.addAttribute("list", list);
				}
				System.out.println("list=>" + list.size());

				System.out.println("tab--->" + tab);

			} else {
//				tab = "cr_details_efish";
				model.addAttribute("NoDataFound", true);

			}

		}
		return "rbkroles/Rep_efish_Vaa_dtlsintf";
	}

	@Autowired
	private RbkEkycCompltdService rbkEkycCompltd;

	@GetMapping("RBKekycompltd")
	public String RBKekycompltd(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/RepRBKekyccompleted";
	}

	@PostMapping("/RepRBKekycompltd")
	public String RepRBKekycompltd(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String season = "", tname = "";
		String mcode = (String) httpSession.getAttribute("mcode");
		System.out.println("mcode:::" + mcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		// String activeYear = session.getAttribute("ACTIVEYEAR").toString();
		String crpses = "", year = "", wbdcode = "", tab = "", tab2 = "";

		String vcode = httpServletRequest.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		crpses = httpServletRequest.getParameter("crYear");

		season = crpses.split("@")[0]; // System.out.println("season==" + season);
		year = crpses.split("@")[1]; // System.out.println("year==" + year);
		Integer year1 = Integer.parseInt(year);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		int intwbdcode = Integer.parseInt(wbdcode);// System.out.println("user::"+user);
		if (intwbdcode >= 0 && intwbdcode <= 9) {
			tname = "cr_crop_det_new_v_" + season + "0" + wbdcode + year;
		} else {
			tname = "cr_crop_det_new_v_" + season + wbdcode + year;
		}

		if (year1 >= 2023 && !(year1 == 2023 && season.equalsIgnoreCase("S"))) {
			tname = "ecrop" + year + "." + tname;
			System.out.println("tname" + tname);
		} 

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.villageCode(vcode);
		System.out.println("val1====>" + val1);

		if (val1) {

			List<RbkEkycCompltdModel> list = null;
			try {
				list = rbkEkycCompltd.getRbkEkycCompltddet(tname, vcode1);

				System.out.println("list=>" + list.size());
				if (list.size() > 0) {
					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "rbkroles/RepRBKekyccompleted";
	}

	@GetMapping("SupChkUpdSts")
	public String SupChkUpdSts(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/RepSupercheckUpdStatusForVA";
	}

	@PostMapping("/RepSupChkUpdSts")
	public String RepSupChkUpdSts(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		String season = "", tname = "", crpses = "", year = "", wbdcode = "", activeYear = "", partition = "",
				wbmcode = "", sesmcode = "", vscode = "", sesdcode = "", vcode = "";

//		sesmcode = (String) httpSession.getAttribute("mcode");
//		System.out.println("sesmcode:::" + sesmcode);
//
		vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
//		
//		sesdcode = (String) httpSession.getAttribute("dcode");
//		System.out.println("sesdcode:::" + sesdcode);

		wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		wbmcode = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("wbmcode:::" + wbmcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		vcode = httpServletRequest.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

//                wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
//                System.out.println("wbdcode::" +wbdcode);
//                if (wbdcode.length() == 1) {
//                    wbdcode = "0" + wbdcode;
//                }
		crpses = httpServletRequest.getParameter("crYear");

		season = crpses.split("@")[0]; // System.out.println("season==" + season);
		year = crpses.split("@")[1]; // System.out.println("year==" + year);
		Integer year1 = Integer.parseInt(year);
//		wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
		String supercheckupd = "supercheck_upd";
		Integer wbdcode1 = Integer.parseInt(wbdcode);
		Integer wbmcode1 = Integer.parseInt(wbmcode);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(wbdcode);
		boolean val2 = regexpmethod.wbmandcode(wbmcode);
		boolean val3 = regexpmethod.villageCode(vcode);
		boolean val4 = regexpmethod.season(season);
		boolean val5 = regexpmethod.year(year);

		if (val1 && val2 && val3 && val4 && val5) {

			int intwbdcode = Integer.parseInt(wbdcode);// System.out.println("user::"+user);
			if (intwbdcode <= 9) {
				partition = "cr_details_" + season + "0" + wbdcode + year;
			} else {
				partition = "cr_details_" + season + wbdcode + year;
			}
			if (year1 >= 2023 && !(year1 == 2023 && season.equalsIgnoreCase("S"))) {
				partition = "ecrop" + year + "." + partition;
				supercheckupd = "ecrop" + year + "." + supercheckupd;
			} 

			List<SuperChkUpdStatusModel> list = null;
			try {
				list = superChkUpdStsService.getSuperChkUpdStatusdet(partition, supercheckupd, wbdcode1, wbmcode1,
						vcode1);

				System.out.println("list=>" + list.size());
//			for(String superchecklist: list) {
//				System.out.println("superchecklist---->" +superchecklist);
//			}
				if (list.size() > 0) {

					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
		return "rbkroles/RepSupercheckUpdStatusForVA";
	}

	@GetMapping("CropbkingdetVA")
	public String CropbkingdetVA(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);
		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		List<CropnamesModel> cropnames = cropnamesSrvc.getcropnames();
		model.addAttribute("cropnames", cropnames);
		String mandal = httpSession.getAttribute("wbemname").toString();
		String rbk = httpSession.getAttribute("wbevname").toString();
		System.out.println(mandal+"------------------"+rbk);
		model.addAttribute("rbk",rbk);
		model.addAttribute("mandal",mandal);

		return "rbkroles/Rep_cropbookingdetailsVAIntf";
	}

	@PostMapping("/RepCropbkingdetVA")
	public String RepCropbkingdetVA(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		String display = request.getParameter("display");
		System.out.println("display::" + display);
		Integer display1 = Integer.parseInt(display);
		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

//		String mcode1 = MasterFunctions.MasterFunction(sesmcode, "wbmcode");
//		System.out.println("mcode1::" + mcode1);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));

		model.addAttribute("villname", villname);

		List<CropnamesModel> cropnames = cropnamesSrvc.getcropnames();
		model.addAttribute("cropnames", cropnames);

		String vcode = request.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);
		String year = crpses.split("@")[1]; // System.out.println("year==" + year);
		int year1 = Integer.parseInt(year);
		String crpid = request.getParameter("crpid");
		System.out.println("crpid---------->" + crpid);
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.villageCode(vcode);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3---->" + val3);
		boolean val4 = regexpmethod.year(year);
		System.out.println("val4--->" + val4);
		boolean val5 = regexpmethod.mandalCode(sesmcode);
		System.out.println("val5--->" + val5);
		
		boolean val6 = regexpmethod.isNumber(display);
		System.out.println("val6--->" + val6);
		boolean val7 = true;
		if (crpid.equalsIgnoreCase("0"))
		System.out.println("val7--->" + val7);
		if (val1 && val2 && val3 && val4 && val5 && val6 && val7) {

//			String wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");

			String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode::::" + wbdcode);

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab1 = "cr_crop_det_new_v_" + season + wbdcode + year;
			if (year1 >= 2023  && !(year1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab1 = "ecrop" + year + "." + tab1;

			} else {
				tab1 = "cr_crop_det_new_v_" + season + wbdcode + year;
			}
			List<CropbookingdetVAModel> list = null;
			String mandal = httpSession.getAttribute("wbemname").toString();
			String rbk = httpSession.getAttribute("wbevname").toString();
			System.out.println(mandal+"------------------"+rbk);
			model.addAttribute("rbk",rbk);
			model.addAttribute("mandal",mandal);
			try {
				list = cropbkingdetSrvc.getcropbkingdet(tab1, Integer.parseInt(dcode), vcode1, season,
						Integer.parseInt(year), Integer.parseInt(mcode1), display, display1, Integer.parseInt(crpid),Integer.parseInt(sesmcode));

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
			}
		}
		return "rbkroles/Rep_cropbookingdetailsVAIntf";
	}

//	@PreAuthorize("hasAuthority('25') || hasAuthority('30')")
	@GetMapping("datasrcwiseCnt")
	public String datasrcwiseCnt(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getActiveSeason();
		model.addAttribute("activeseason", activeSeason);
//		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
//		model.addAttribute("activeseason", cropYearActiveSeasonList);

		return "rbkroles/Rep_datasrcwiseCnt";
	}

@PreAuthorize("hasAuthority('25') || hasAuthority('30')")
	@PostMapping("/RepdatasrcwiseCnt")
	public String RepdatasrcwiseCnt(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "", datasrcName = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

//		String dcode = (String) httpSession.getAttribute("dcode");
//		System.out.println("dcode:::" + dcode);
//
		int role = Integer.parseInt( (String) httpSession.getAttribute("role"));
		System.out.println("userid:::" + role);
		
		

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
        int cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

//		String District = MasterFunctions.masterFunction(dcode, "dcode");
//		model.addAttribute("District", District);

//		String Mandal = MasterFunctions.masterFunction(sesmcode, "mcode");
//		System.out.println("Mandal::" + Mandal);
//
//		model.addAttribute("Mandal", Mandal);
//		String mcode1 = MasterFunctions.masterFunction(sesmcode, "wbmcode");
//
//		System.out.println("mcode1::" + mcode1);
//
//		String rbkName = MasterFunctions.masterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

//		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
//		model.addAttribute("activeseason", activeSeason);


//		List<ActiveSeason> cropYearActiveSeasonList = activeSeasonService.listAll();
//		model.addAttribute("activeseason", cropYearActiveSeasonList);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getActiveSeason();
		model.addAttribute("activeseason", activeSeason);
		
		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);
		String year = crpses.split("@")[1]; // System.out.println("year==" + year);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.villageCode(vscode);
		System.out.println("val1---->" +val1);


		if (val1) {
			
			String tab1 = "verify_datadownload";
			String tab2 = "villsec_rev_v";
			
			 if (cropyear1>=2023  && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				 tab1 = "ecrop" + cropyear + "." + tab1;
					System.out.println("tab1--->" + tab1);
					tab2 = "ecrop" + cropyear + "." + tab2;
					System.out.println("tab2--->" + tab2);	
					
				}else {
					tab1 = "ecrop2024." + tab1;
				}	
			 List<DataSrcwiseCntModel> list= null;
			 
			 if(role == 30 ) {
					int wbvcode= (int) httpSession.getAttribute("wbvcode");
					try {
						 list = dataSrcWiseCntSrvc.getdatasrcwisecntdet(wbvcode, tab1, tab2,role);
							}
							catch(Exception e) {
								System.out.println("exception  e------->>>"+e);
								model.addAttribute("NoRecordsFound", true);
							}
				}
			 else if(role==25) {
			try {
		 list = dataSrcWiseCntSrvc.getdatasrcwisecntdet(Integer.parseInt(vscode), tab1, tab2,role);
			}
			catch(Exception e) {
				System.out.println("exception  e------->>>"+e);
				model.addAttribute("NoRecordsFound", true);
			}
			 }
			if (list.size()>0) {
				model.addAttribute("list", list);
			} else {
				model.addAttribute("NoRecordsFound", true);
			}
		}

		return "rbkroles/Rep_datasrcwiseCnt";
	}
	@GetMapping("pndingphyack")
	public String pndingphyack(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		return "rbkroles/Rep_pending_phy_ack";
	}

	@PostMapping("/Reppndingphyack")
	public String Reppndingphyack(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "", datasrcName = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);

		String year = crpses.split("@")[1]; // System.out.println("year==" + year);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.season(season);
		boolean val2 = regexpmethod.year(year);

		if (val1 && val2) {

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab = "cr_details_" + season + wbdcode + cropyear;

			if (cropyear1 >= 2023   && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab = "ecrop" + cropyear + "." + tab;

			} else {
				tab = "cr_details_" + season + wbdcode + cropyear;
			}

			List<pendingphyackModel> list = null;
			try {
				list = pendPhyAckSrvc.getPendPhyAckdet(tab, Integer.parseInt(wbdcode), userid);

//			System.out.println("list=>" + list.size());
				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return "rbkroles/Rep_pending_phy_ack";
	}

	@GetMapping("vaafinallist")
	public String vaafinallist(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/Rep_vaa_final_list";
	}

	@PostMapping("Repvaafinallist")
	public String Repvaafinallist(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));

		model.addAttribute("villname", villname);

		String vcode = request.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);
//		String year = crpses.split("@")[1]; // System.out.println("year==" + year);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1---->" + val1);
		boolean val2 = regexpmethod.villageCode(vcode);
		System.out.println("val2--->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3--->" + val3);
		boolean val4 = regexpmethod.year(cropyear);
		System.out.println("val4--->" + val4);

		if (val1 && val2 && val3 && val4) {

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab1 = "cr_details_" + season + wbdcode + cropyear;

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab1 = "ecrop" + cropyear + "." + tab1;

			} else {
				tab1 = "cr_details_" + season + wbdcode + cropyear;
			}
			List<RepVaaFinalListModel> list = null;
			try {

				list = repVaaFinalList.getVaafinallist(tab1, userid, vcode1, Integer.parseInt(cropyear), season);

//			System.out.println("list=>" + list.size());

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {

			}
		}
		return "rbkroles/Rep_vaa_final_list";
	}

	@GetMapping("surveynodetIntf")
	public String surveynodet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		//System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/Rep_SurveynodetIntf";
	}

	@PostMapping("/RepsurveynodetIntf")
	public String getsurveynodet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "", table, tab2;
		String sesmcode = (String) httpSession.getAttribute("mcode");
		//System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		//System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		//System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		//System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		//System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		//System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		//System.out.println("wbdcode:::" + wbdcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));

		model.addAttribute("villname", villname);

		String choiceId = request.getParameter("choiceId");

		//System.out.println("choiceId--------->" + choiceId);

		String vcode = request.getParameter("vcode");
		//System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);
		String year = crpses.split("@")[1]; // System.out.println("year==" + year);

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.villageCode(vcode);
		//System.out.println("val1--->" + val1);
		boolean val2 = regexpmethod.season(season);
		//System.out.println("val2--->" + val2);
		boolean val3 = regexpmethod.year(year);
		//System.out.println("val3--->" + val3);
		boolean val4 = regexpmethod.isNumber(choiceId);
		//System.out.println("val4--->" + val4);

		if (val1 && val2 && val3 && val4) {

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			table = "pattadarmast_wb_partition_" + season + wbdcode + year;
			System.out.println("table:" + table);

			tab2 = "cr_booking_partition_" + season + wbdcode + year;
			String rbksrnoMapTab="rbk_surveyno_mapping_"+ season + wbdcode + year;

			

			System.out.println("tab2:" + tab2);
			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				table = "ecrop" + cropyear + "." + table;
				tab2 = "ecrop" + cropyear + "." + tab2;
				rbksrnoMapTab="ecrop"+cropyear+"."+rbksrnoMapTab;
			} else {
				table = "pattadarmast_wb_partition_" + season + wbdcode + year;
				tab2 = "cr_booking_partition_" + season + wbdcode + year;
			}
			List<SurveyNodetIntfModel> list = null;
			try {
				list = repSurveyNodetIntfsrvc.getSurveyNodet(table, tab2,rbksrnoMapTab, userid, vcode1, choiceId);

				System.out.println("list=>" + list.size());

				if (list.size() > 0) {

					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
System.out.println("----------"+e);
			}
		}
		return "rbkroles/Rep_SurveynodetIntf";
	}

	@GetMapping("ekycauthpendingIntf")
	public String ekycauthpendingdet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/Rep_ekycauth_pendingIntf";
	}

	@PostMapping("/Repekycauthpending")
	public String getekycauthpendingdet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];

		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		int intwbdcode = Integer.parseInt(wbdcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));

		model.addAttribute("villname", villname);

		String vcode = request.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);

		String crpses = request.getParameter("crYear");

		season = crpses.split("@")[0];
		System.out.println("season==" + season);
		String year = crpses.split("@")[1]; // System.out.println("year==" + year);

		if (intwbdcode >= 0 && intwbdcode <= 9) {
			tname = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
		} else {
			tname = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
		}

		if (cropyear1 >= 2023&& !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
			tname = "ecrop" + cropyear + "." + tname;

		}

		List<RepRbkekycPendingModel> list = null;
//		try {
			list = repRbkekycPendingSrvc.getRbkekycPendingdet(tname, vcode1);
			System.out.println("list=12334>" + list.size());
			if (list.size() > 0) {

				model.addAttribute("list", list);

			} else {
				model.addAttribute("NoRecordsFound", true);

			}

//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
		return "rbkroles/Rep_ekycauth_pendingIntf";

	}

	@GetMapping("/searchFormbykhsno")
	public String searchFormbykhsnodet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);
		return "rbkroles/Rep_searchFormby_khsnoVAIntf";
	}

	@PostMapping("/RepsearchFormbykhsno")
	public String getsearchFormbykhsnodet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		boolean val6 = false, val5 = false;
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		Integer cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

//		String District = MasterFunctions.MasterFunction(dcode, "dcode");
//		model.addAttribute("District", District);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));

		model.addAttribute("villname", villname);

//		String mandalcode = MasterFunctions.MasterFunction(sesmcode, "wbmcode");
//		System.out.println("mandalcode---->" + mandalcode);

		String vcode = request.getParameter("vcode");
		System.out.println("vcode=" + vcode);

		Integer vcode1 = Integer.parseInt(vcode);
		String searchParam = request.getParameter("searchParam");
		System.out.println("searchParam:::" + searchParam);
		String surveyno = request.getParameter("surveyno");
		System.out.println("surveyno:::" + surveyno);

		String khno = request.getParameter("khno");
		System.out.println("khno:::" + khno);

		String crpses = request.getParameter("crYear");
		season = crpses.split("@")[0];
		System.out.println("season==" + season);
//        String   year = crpses.split("@")[1]; //System.out.println("year==" + year);   

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

//		String wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");

		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}
		System.out.println(wbdcode);
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		System.out.println("val1--->" + val1);
		boolean val2 = regexpmethod.villageCode(vcode);
		System.out.println("val2---->" + val2);
		boolean val3 = regexpmethod.season(season);
		System.out.println("val3---->" + val3);
		boolean val4 = regexpmethod.year(cropyear);
		System.out.println("val4---->" + val4);
		if (surveyno != "" && surveyno != null) {
			val5 = regexpmethod.checkSuveyNo(surveyno);
			System.out.println("val5---->" + val5);
		}
		if (khno != "" && khno != null) {
			val6 = regexpmethod.checkKhataNo(khno);
			System.out.println("val6--->" + val6);
		}
		boolean val7 = regexpmethod.wbmandcode(mcode1);
		System.out.println("val7--->" + val7);
		boolean val8 = regexpmethod.isNumber(searchParam);
		System.out.println("val8--->" + val8);

		if (val1 && val2 && val3 && val4 && val5 || val6 && val7 && val8) {

			String tab1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
			System.out.println("tab1---->" + tab1);

			if (cropyear1 >= 2023) {
				tab1 = "ecrop" + cropyear + "." + tab1;
			} else {
				tab1 = tab1;
			}
			List<Rep_SearchFormbyKhsnoVAModel> list = null;
			try {
				System.out.println("tab1:::" + tab1);
				list = srchformbyKhsnoVASrvc.getsearchformbykhsnodet(tab1, searchParam, Integer.parseInt(dcode), vcode1,
						Integer.parseInt(cropyear), season, surveyno, khno, Integer.parseInt(mcode1), vcode);
				if (list.size() > 0) {

					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {

				System.out.println("exception  e------->>>" + e);

			}
			System.out.println("list---------->" + list);

		}
		return "rbkroles/Rep_searchFormby_khsnoVAIntf";
	}

	@GetMapping("SearchBybookingId")
	public String searchBybookingIddet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/SearchBybookingId";
	}

	@PostMapping("/RepSearchBybookingId")
	public String getsearchBybookingIddet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

//		String District = MasterFunctions.MasterFunction(dcode, "dcode");
//		model.addAttribute("District", District);

//		String Mandal = MasterFunctions.MasterFunction(sesmcode, "mcode");
//		System.out.println("Mandal::" + Mandal);
//		model.addAttribute("Mandal", Mandal);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

//		String mcode1 = MasterFunctions.MasterFunction(sesmcode, "wbmcode");
//		System.out.println("mcode1::" + mcode1);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

//     String	mandalcode =	MasterFunctions.MasterFunction(sesmcode, "wbmcode");

		String bkId = request.getParameter("bkId");
		System.out.println("bkId:::" + bkId);

		String crpses = request.getParameter("crYear");
		season = crpses.split("@")[0];
		System.out.println("season==" + season);
//       String   year = crpses.split("@")[1];
//      System.out.println("year==" + year);   

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.districtCode(dcode);
		boolean val2 = regexpmethod.season(season);
		boolean val3 = regexpmethod.year(cropyear);
		boolean val4 = regexpmethod.isNumber(bkId);

		if (val1 && val2 && val3 && val4) {

//			String wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");
			int intwbdcode = Integer.parseInt(wbdcode);
			if (intwbdcode >= 0 && intwbdcode <= 9) {
				tname = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
			} else {
				tname = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
			}

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tname = "ecrop" + cropyear + "." + tname;

			} else {
				tname = tname;
			}

			List<SearchbyBookingIdModel> list = null;
			try {

				list = searchbyBkingIdSrvc.getSearchbyBookingIddet(tname, userid, Long.parseLong(bkId));

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
				System.out.println("exception  e------->>>" + e);

			}
		}
		return "rbkroles/SearchBybookingId";
	}

	@GetMapping("searchByUID")
	public String searchByUIDdet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);
		return "rbkroles/SearchByUID";
	}

	@PostMapping("/RepsearchByUID")
	public String getsearchByUIDdet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
		model.addAttribute("season", season);
		model.addAttribute("cropyear", cropyear);

		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("mcode1:::" + mcode1);

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();

		model.addAttribute("activeseason", activeSeason);

		String aNo = request.getParameter("aNo");
		System.out.println("aNo:::" + aNo);

//     String   crpses =  request.getParameter("crYear");
//       season = crpses.split("@")[0]; 
//       System.out.println("season==" + season);
//        String   year = crpses.split("@")[1]; //System.out.println("year==" + year);   

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();
		boolean val1 = regexpmethod.season(season);
		boolean val2 = regexpmethod.year(cropyear);
		boolean val3 = regexpmethod.isNumber(aNo);
		if (val1 && val2 && val3) {

			int intwbdcode = Integer.parseInt(wbdcode);
			if (intwbdcode >= 0 && intwbdcode <= 9) {
				tname = "cr_crop_det_new_v_" + season + "0" + wbdcode + cropyear;
			} else {
				tname = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
			}

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tname = "ecrop" + cropyear + "." + tname;

			} else {
				tname = tname;
			}
			List<SearchByUIDModel> list = null;
			try {
				list = searchByUIDSrvc.getSearchByUIDdet(tname, userid, aNo);

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);
				}
			} catch (Exception e) {
				System.out.println("exception  e------->>>" + e);

			}
		}
		return "rbkroles/SearchByUID";
	}

	@GetMapping("VsPerennialIntf")
	public String VsPerennialIntfdet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		List<CropnamesModel> cropnames = cropnamesSrvc.getcropnamesPeri();
		model.addAttribute("cropnames", cropnames);

		return "rbkroles/Rep_VsPerennialIntf";
	}

	@PostMapping("/RepVsPerennialIntf")
	public String getVsPerennialIntfdet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode:::" + dcode);
		Integer dcode1 = Integer.parseInt(dcode);

		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

//		String District = MasterFunctions.MasterFunction(dcode, "dcode");
//		model.addAttribute("District", District);

//		String Mandal = MasterFunctions.MasterFunction(sesmcode, "mcode");
//		System.out.println("Mandal::" + Mandal);
//		model.addAttribute("Mandal", Mandal);

		String wbmcode = (String) httpSession.getAttribute("wbmcode").toString();
		System.out.println("wbmcode:::" + wbmcode);

//		String mcode1 = MasterFunctions.MasterFunction(sesmcode, "wbmcode");
//		System.out.println("mcode1::" + mcode1);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

//		String wbmcode = MasterFunctions.MasterFunction(sesmcode, "wbmcode");

//		String wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");

		String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
		System.out.println("wbdcode:::" + wbdcode);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		List<CropnamesModel> cropnames = cropnamesSrvc.getcropnames();
		model.addAttribute("cropnames", cropnames);

//     String	mandalcode =	MasterFunctions.MasterFunction(sesmcode, "wbmcode");

		String vcode = request.getParameter("vcode");
		System.out.println("vcode:::" + vcode);
		int vcode1 = Integer.parseInt(vcode);
		String crpid = request.getParameter("crpid");
		System.out.println("crpid:::" + crpid);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
Integer cropyear1 = Integer.parseInt(cropyear);
//     String   crpses =  request.getParameter("crYear");
//       season = crpses.split("@")[0]; 
//       System.out.println("season==" + season);
//        String   year = crpses.split("@")[1]; //System.out.println("year==" + year);   
		
		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpmethod.districtCode(dcode.toString());
		boolean val2 = regexpmethod.mandalCode(sesmcode.toString());
		boolean val3 = regexpmethod.villageCode(vcode.toString());
		boolean val4 = regexpmethod.season(season.toString());
		boolean val5 = regexpmethod.year(cropyear.toString());
		boolean val6 = regexpmethod.cropCode(crpid);

		if (val1 && val2 && val3 && val4 && val5 && val6) {

			model.addAttribute("season", season);
			model.addAttribute("cropyear", cropyear);

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab1 = "cr_crop_det_new_v_" + season + wbdcode + cropyear;
	
			if (cropyear1>=2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab1 = "ecrop" + cropyear + "." + tab1;

			}else {
				tab1 = tab1;
			}
			
			List<Rep_VsPerennialModel> list = null;
			
			try {
				 list = VsPerennialSrvc.getVsPerennialdet(tab1, dcode, dcode1,
						Integer.parseInt(wbmcode), vcode, Integer.parseInt(cropyear), season, Integer.parseInt(crpid),
						vcode1);

				if (list.size() > 0) {
					model.addAttribute("list", list);

				} else {
					model.addAttribute("NoRecordsFound", true);

				}

			} catch (Exception e) {
				System.out.println("exception  e------->>>" + e);

			}
		}

		return "rbkroles/Rep_VsPerennialIntf";
	}

	@GetMapping("Vaadraftlist")
	public String Vaadraftlistdet(Model model, HttpSession httpSession) {

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return "rbkroles/Rep_vaa_draft_list";
	}

	@PostMapping("/RepVaadraftlist")
	public String getVaadraftlistdet(Model model, HttpSession httpSession, HttpServletRequest request) {
		String season = "", tname = "", partition = "";
		String sesmcode = (String) httpSession.getAttribute("mcode");
		System.out.println("sesmcode:::" + sesmcode);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		String dcode = (String) httpSession.getAttribute("dcode").toString();
		System.out.println("dcode:::" + dcode);

//		Integer dcode1 = Integer.parseInt(dcode);
		String userid = (String) httpSession.getAttribute("userid");
		System.out.println("userid:::" + userid);

//		String District = MasterFunctions.MasterFunction(dcode, "dcode");
//		model.addAttribute("District", District);

//		String Mandal = MasterFunctions.MasterFunction(sesmcode, "mcode");
//		System.out.println("Mandal::" + Mandal);
//		model.addAttribute("Mandal", Mandal);

//		String mcode1 = MasterFunctions.MasterFunction(sesmcode, "wbmcode");
//		System.out.println("mcode1::" + mcode1);

//		String mcode1 = (String) httpSession.getAttribute("wbmcode").toString();
//		System.out.println("mcode1:::" + mcode1);

//		String rbkName = MasterFunctions.MasterFunction(userid.substring(4), "VSName");
//		model.addAttribute("rbkName", rbkName);

		List<ActiveSeasonProjection> activeSeason = getCropYearService.getAllSeason();
		model.addAttribute("activeseason", activeSeason);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

//     String	mandalcode =	MasterFunctions.MasterFunction(sesmcode, "wbmcode");

		String vcode = request.getParameter("vcode");
		System.out.println("vcode:::" + vcode);
		int vcode1 = Integer.parseInt(vcode);

		String crYear = request.getParameter("crYear");
		System.out.println("crYear::" + crYear);

		season = crYear.split("@")[0];
		String cropyear = crYear.split("@")[1];
		int cropyear1 = Integer.parseInt(cropyear);
//		String activeYear = "2023";

		RegularExpressionclassMethod regexpmethod = new RegularExpressionclassMethod();

//		boolean val1 = regexpmethod.districtCode(dcode.toString());
//		boolean val2 = regexpmethod.mandalCode(sesmcode.toString());
		boolean val1 = regexpmethod.villageCode(vcode.toString());
		boolean val2 = regexpmethod.season(season.toString());
		boolean val3 = regexpmethod.year(cropyear.toString());

		if (val1 && val2 && val3) {

//			String wbmcode = MasterFunctions.MasterFunction(sesmcode, "wbmcode");

//			String wbdcode = MasterFunctions.MasterFunction(dcode, "wbdcode");

			String wbmcode = (String) httpSession.getAttribute("wbmcode").toString();
			System.out.println("wbmcode:::" + wbmcode);

			String wbdcode = (String) httpSession.getAttribute("wbdcode").toString();
			System.out.println("wbdcode:::" + wbdcode);

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}
			String supercheckupd = "supercheck_upd";

			String tab1 = "cr_details_" + season + wbdcode + cropyear;

			if (cropyear1 >= 2023 && !(cropyear1 == 2023 && season.equalsIgnoreCase("S"))) {
				tab1 = "ecrop" + cropyear + "." + tab1;
				supercheckupd = "ecrop" + cropyear + "." + supercheckupd;
			} else {
				supercheckupd = "supercheck_upd";
				tab1 = "cr_details_" + season + wbdcode + cropyear;
			}
			System.out.println("tab1----------->" + tab1);
			List<Rep_vaa_draft_listModel> list = null;
			try {
				list = vaadraftlistSrvc.getvaadraftlistdet(supercheckupd, tab1, userid, cropyear1, season, vcode1);

				if (list.size() > 0) {
					model.addAttribute("list", list);
				} else {
					model.addAttribute("NoRecordsFound", true);

				}
			} catch (Exception e) {
				System.out.println(e);

			}
		}

		return "rbkroles/Rep_vaa_draft_list";
	}

}
