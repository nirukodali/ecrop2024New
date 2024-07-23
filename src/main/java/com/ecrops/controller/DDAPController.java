package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.entity.DistTabMandal;
import com.ecrops.entity.DistVill;
import com.ecrops.entity.DkrishiMand;
import com.ecrops.entity.ExcessBookingKhataProjections;
import com.ecrops.entity.ExcessBookingSurveyProjections;
import com.ecrops.partition.DistTabMandalPartition;
import com.ecrops.partition.DistVillPartition;
import com.ecrops.partition.DkrishiMandPartition;
import com.ecrops.projection.DistrictProjections;
import com.ecrops.repo.ABSValidationNoRepository;
import com.ecrops.repo.ABSValidationNoRepository.absview;
import com.ecrops.repo.AadhaarCNRepository;
import com.ecrops.repo.AadhaarCNRepository.aadharview;
import com.ecrops.repo.AadhaarCNmandRepository;
import com.ecrops.repo.AadhaarCNmandRepository.aadharmandview;
import com.ecrops.repo.AadhaarNotUpdatedRepository;
import com.ecrops.repo.AadhaarNotUpdatedRepository.aadhaarnotupdateview;
import com.ecrops.repo.DDAPCorrectionRepository;
import com.ecrops.repo.DDAPCorrectionRepository.ddapcorrectionview;
import com.ecrops.repo.DistrictRepository;
import com.ecrops.repo.ExcessBookingSurveyRepository;
import com.ecrops.repo.ExcessBookingkhataRepository;
import com.ecrops.repo.LandDetailsRepository;
import com.ecrops.repo.LandDetailsRepository.Landdetailsview;
import com.ecrops.repo.MandalLandDetailsRepository;
import com.ecrops.repo.MandalLandDetailsRepository.Mandalview;
import com.ecrops.repo.MandalWiseEmpAllocationRepository;
import com.ecrops.repo.MandalWiseEmpAllocationRepository.manAlloView;
import com.ecrops.repo.MandalWiseNormRepository;
import com.ecrops.repo.NormalABSValidationRepository;
import com.ecrops.repo.NormalABSValidationRepository.normalabsview;
import com.ecrops.repo.UnSurveyedViewProjections;
import com.ecrops.repo.UnSurveyedViewRepository;
import com.ecrops.repo.UnSurveyedVillViewProjections;
import com.ecrops.repo.UnSurveyedVillViewRepository;
import com.ecrops.repo.VillageLandDetailsRepository;
import com.ecrops.repo.VillageLandDetailsRepository.VilllandView;

@PreAuthorize("hasAuthority('17')")
@Controller
public class DDAPController {

//	=====================VAADetails================
	@Autowired
	private DistrictRepository distrepo;

	@GetMapping("/vaa")
	public String getvaaList(HttpSession httpSession, Model model) {
		System.out.println("/vaa");

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode" + dcode);

		List<DistrictProjections> dist = distrepo.getList();
		System.out.println("dist" + dist.size());

		model.addAttribute("dcode", dcode);
		model.addAttribute("dist", dist);

		return "ddap/VAADet";
	}
	
	//===============================AadhaarCN===================================
		@Autowired
		private AadhaarCNRepository aadhaarcnrepo;

				@GetMapping("/aadhaarcn")
				public String getaadhaarcn(Model model) {
					List<aadharview> cn = aadhaarcnrepo.getlist();
					model.addAttribute("cn", cn);
					return "ddap/aadharCN";
				}
				
//				===================================AadhaarCNmand==========================	
				@Autowired 
				private AadhaarCNmandRepository aadhaarmandreop;
				
				@GetMapping("/aadhaarcnmand")
				public String getaadhaarcnmand(Model model,@RequestParam Integer dcode)  {
					System.out.println("dcode" + dcode);
					List<aadharmandview> cnmand =aadhaarmandreop.getlist(dcode.toString());
					model.addAttribute("dcode",dcode);
					model.addAttribute("cnmand", cnmand);

					System.out.println("dcode"+dcode);
					return "ddap/aadharCNmand";
				}	
				
				
//				===================================AadhaarNotUpdated=============================
				@Autowired
				private AadhaarNotUpdatedRepository aadhaarnotpartition;
				
				@GetMapping("/aadhaarnot")
				public String getaadhaarnot(Model model,@RequestParam Integer dcode)  {
					System.out.println("dcode" + dcode);
					List<aadhaarnotupdateview> notupdate =aadhaarnotpartition.getList(dcode.toString());
					model.addAttribute("dcode",dcode);
					model.addAttribute("notupdate", notupdate);

					System.out.println("dcode"+dcode);
					return "ddap/aadharnotupadate";
				}	
				
				
//	========================ABSValidationNo====================
	@Autowired
	private NormalABSValidationRepository absrepo1;
	@GetMapping("/abs1")
	public String getabs(Model model) {
		List<normalabsview> abs = absrepo1.getList();
		model.addAttribute("abs", abs);
		return "ddap/ABSvalidation";
	}	
	
//  ==========================DistTabDownload=============================
			@GetMapping("/disttabdownload")
			public String getdisttabdownload(Model model) {
				return "ddap/DistTabDownload";
			}	

//			==========================DistMandal=============================
					@Autowired
					private DistTabMandalPartition distmandpartition;
					
					@GetMapping("/disttabmand")
					public String getdisttabmand(Model model,@RequestParam String wbdcode,@RequestParam String cropyear) {
						System.out.println("wbdcode==>"+wbdcode);
						System.out.println("cropyear==>"+cropyear);
						List<DistTabMandal> list = distmandpartition.distmand(cropyear, Integer.parseInt(wbdcode));
						model.addAttribute("list", list);
						model.addAttribute("cropyear", cropyear);
//						list.stream().forEach(a->System.out.println(a));

						return "ddap/DistMand";
					}	
					
//					=========================DistVill=========================
					@Autowired
					private DistVillPartition villpartition;
							
					@GetMapping("/disttabvill")
					public String getdisttabvill(Model model,@RequestParam String wbdcode,@RequestParam String cropyear,@RequestParam String wbmcode) {
						System.out.println("wbdcode==>"+wbdcode);
						System.out.println("cropyear==>"+cropyear);
						System.out.println("wbmcode==>"+wbmcode);
						List<DistVill> list = villpartition.distvill(cropyear, Integer.parseInt(wbdcode), Integer.parseInt(wbmcode));
						model.addAttribute("list", list);
						return "ddap/Distvill";
					}	
					
					@GetMapping("/dkrish")
					public String getdkrish(Model model) {
						return "ddap/Dkrish";
					}	
					
					@Autowired
					private DkrishiMandPartition krishimand;
					
					@GetMapping("/mand1")
					public String getmand1(Model model,@RequestParam String wbdcode,@RequestParam String cropyear) {
						System.out.println("wbdcode==>"+wbdcode);
						System.out.println("cropyear==>"+cropyear);
						List<DkrishiMand> dmand = krishimand.dkrishimand(cropyear, wbdcode);
						
						model.addAttribute("dmand", dmand);
						model.addAttribute("cropyear", cropyear);

						return "ddap/Dkrishimand";
					}	
					

					
//	=====================VAADeviceDetails================
	@PreAuthorize("hasAuthority('17')")
	@GetMapping("/vaadevice")
	public String getvaadeviceList(HttpSession httpSession, Model model) {
		System.out.println("/vaadevice");

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode" + dcode);

		List<DistrictProjections> dist = distrepo.getList();
		System.out.println("dist" + dist.size());

		model.addAttribute("dcode", dcode);
		model.addAttribute("dist", dist);

		return "ddap/VAADevice";
	}


//	=====================VAASecList================
	@GetMapping("/villseclist")
	public String getvillseclistList(HttpSession httpSession, Model model) {
		System.out.println("/villseclist");

		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode" + dcode);

		List<DistrictProjections> dist = distrepo.getList();
		System.out.println("dist" + dist.size());

		// List<Mandal> mand = mandrepo.getList(dcode);

		model.addAttribute("dcode", dcode);
		model.addAttribute("dist", dist);

		/// model.addAttribute("mand", mand);

		return "ddap/VillSecList";
	}

//	========================ABSValidationNo====================
	@Autowired
	ABSValidationNoRepository absrepo;

	@GetMapping("/absrep")
	public String getabsrep(Model model) {
		List<absview> absdata = absrepo.getlist();
		model.addAttribute("absdata", absdata);
		return "ddap/ABS";
	}

//	==================DistWise==================
	@GetMapping("/dept")
	public String dept(HttpSession httpSession) {

		return "ddap/DeptWise";
	}
	
	// =====================bookingsummary==================
		@GetMapping("/bookingsummary")
		public String getbookingsummary(HttpSession httpSession) {

			return "ddap/BookingSummary";
		}
	
//		======================wholeSurveyNoMandWise========================
		@GetMapping("/wholesurveymand")
		public String wholesurveymand(Model model) {
			return "ddap/WholeMand";

		}

//		==================LandDetails======================
		@Autowired
		private LandDetailsRepository landedetrepo;

		@GetMapping("/landdetails")
		public String getList41(Model model) {
			List<Landdetailsview> land = landedetrepo.getList();
			model.addAttribute("land", land);
			return "ddap/LandDetails";
		}

//		================MandLandDetails===============
		@Autowired
		private MandalLandDetailsRepository mandalLandDetailsRepository;

		@RequestMapping("/mandlanddet")
		public String getmand(@RequestParam("dcode") String dcode, Model model) {
			System.out.println("mandlanddet=>" + dcode);
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();

			boolean dist = method.districtCode(dcode);
			if (dist) {
				List<Mandalview> list = mandalLandDetailsRepository.getListt(Integer.parseInt(dcode));
				System.out.println("size list=>" + list.size());
				model.addAttribute("mand", list);

				model.addAttribute("dcode", dcode);

				return "ddap/MandDetails";

			}
			return null;
		}

//		================VillageLandDetails===============
		@Autowired
		private VillageLandDetailsRepository villlanddetailsrepo;

		@RequestMapping("/villand")
		public String getvill(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode, Model model) {
			System.out.println("dcode=>" + dcode);
			System.out.println("mcode=>" + mcode);
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();

			boolean dist = method.districtCode(dcode);
			boolean mand = method.mandalCode(mcode);
			if (dist && mand) {
				List<VilllandView> list = villlanddetailsrepo.getListt(Integer.parseInt(dcode), Integer.parseInt(mcode));
				System.out.println("list------------>" + list.size());
				System.out.println(
						"size listland=>" + list.get(0).getWbvname() + "========" + list.get(0).getNo_of_records());
				model.addAttribute("list", list);

				model.addAttribute("dcode", dcode);
				model.addAttribute("mcode", mcode);

				return "ddap/VillageDetails";
			}
			return null;
		}

//		==========================RofrExtBooked=============
		@GetMapping("/rofrextbook")
		public String getrofrextbook(HttpSession httpSession) {

			return "ddap/RofrExtBooked";
		}

		
//		===================RedownloadCn==========================

		@PreAuthorize("hasAuthority('17')")
		@GetMapping("/redownload")
		public String redownload(HttpSession httpSession, Model model) {
			String dcode = (String) httpSession.getAttribute("dcode");
			System.out.println("dcode" + dcode);

			List<DistrictProjections> dist = distrepo.getList();
			System.out.println("dist" + dist.size());

			model.addAttribute("dcode", dcode);
			model.addAttribute("dist", dist);
			return "ddap/Redown";
		}
		
		//======================================FarmerDeatilsEcropIntf======================
		@GetMapping("/farmerecrop")
		public String getFarmerDetails(HttpSession httpSession, Model model) {
			String dcode = (String) httpSession.getAttribute("dcode");
			System.out.println("dcode" + dcode);

			List<DistrictProjections> dist = distrepo.getList();
			System.out.println("dist" + dist.size());

			model.addAttribute("dcode", dcode);
			model.addAttribute("dist", dist);

			return "ddap/FarmerEcrop";
		}		
		
		//========================BookingDET==============
		@PreAuthorize("hasAuthority('17')")
		@GetMapping("/bookdet")
		public String bookdet(Model model) {
			return "ddap/BookingDet";

		}
		
// 		===================FirstCrdetVwise=======================
 		@GetMapping("/crdistvwise")
 		public String getcrdistvwise(HttpSession httpSession) {

 			return "ddap/FirstCrdistVwise";
 		}

		
 	//  =====================AuthCropWise=======================
 		@PreAuthorize("hasAuthority('17')")
 		@GetMapping("/authcropwise")
 		public String authcropwise(Model model) {
 			return "ddap/AuthCropwise";
 		}
	
// 		=====================DistrictWiseDigiAck================
 		@GetMapping("/distwisedigiack")
 		public String distwisedigiack(HttpSession httpSession) {

 			return "ddap/DistDigiAck";
 		}

// 		================ExcessBookingKhataWise=====================
 		@Autowired
 		private ExcessBookingkhataRepository Krepo;

 		@GetMapping("/bookingkhata")
 		public String getList1(Model model) {
 			List<ExcessBookingKhataProjections> list = Krepo.getList();
 			model.addAttribute("list", list);
 			return "ddap/ExcessbookingKhata";
 		}

// 		===============ExcessBookingSurveyWise==================
 		@Autowired
 		private ExcessBookingSurveyRepository Repo;

 		@GetMapping("/bookingsurvey")
 		public String getList(Model model) {
 			List<ExcessBookingSurveyProjections> list = Repo.getList();

 			RegularExpressionclassMethod method = new RegularExpressionclassMethod();
 			model.addAttribute("list", list);
 			return "ddap/ExcessBookingSurvey";

 		}		
 		
// 		===============UnSurveyedView=================
 		@Autowired
 		private UnSurveyedViewRepository unsurveyedrepo;

 		@GetMapping("/unsurveyedRep")
 		public String getList2(Model model) {
 			List<UnSurveyedViewProjections> unsurveyed = unsurveyedrepo.getList();
 			model.addAttribute("unsurveyed", unsurveyed);
 			return "ddap/UnSurveyedView";
 		}
	
// 		===============UnSurveyedVillView=================
 		@Autowired
 		private UnSurveyedVillViewRepository unsurveyedvillrepo;

 		@GetMapping("/unsurveyedvill")
 		public String getList21(Model model) {
 			List<UnSurveyedVillViewProjections> unsurveyedvill = unsurveyedvillrepo.getList();
 			model.addAttribute("unsurveyedvill", unsurveyedvill);
 			return "ddap/UnSurveyedVillView";
 		}

// 		=====================NormalSreaAbs====================
 		@GetMapping("/normalabs")
 		public String getnormalabs(HttpSession httpSession, Model model) {

 			return "ddap/NormalAbs";
 		} 		
 		

// 		================MandalWiseNorm========================
 		@Autowired
 		private MandalWiseNormRepository mandnormrepo;

 		@GetMapping("/mandnormwise")
 		public String getmandnormwise(HttpSession httpSession, Model model) {
 			String dcode = (String) httpSession.getAttribute("dcode");
 			System.out.println("dcode" + dcode);

 			List<DistrictProjections> dist = distrepo.getList();
 			System.out.println("dist" + dist.size());

 			model.addAttribute("dcode", dcode);
 			model.addAttribute("dist", dist);

 			return "ddap/MandNormWise";
 		}


 		@GetMapping("/irrigidwise")
 		public String getirrigidwise() {

 			return "ddap/IrriDwise";
 		}	
		

//	=====================ROFRAbstract================
	@GetMapping("/rofrext")
	public String getList221(Model model) {
		return "ddap/ROFR";
	}


//	===============ExtentBookedVSNormalArea====================
	@GetMapping("/extbooknormal")
	public String getextbooknormal(String cropyear) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		boolean crop1 = method.cropyearseason(cropyear);
		return "ddap/ExternalNormal";
	}


//	======================DistWiseVAAVROeKYCrabi=================
	@GetMapping("/distvaavro")
	public String getdistvaavro(HttpSession httpSession) {

		return "ddap/DistVAA";
	}

//  =====================VarietyWiseRBK=======================
	@GetMapping("/variety")
	public String variety(Model model) {
		return "ddap/VarietyWiseRBK";
	}

//	==========================DDAPCorrectionSocialAudit==========================
	@GetMapping("/ddapsocialaudit")
	public String getddapcorrection(HttpSession httpSession) {

		return "ddap/DDAPsocialaudit";
	}

//	==============MandalWiseAllocation===============
	@Autowired
	private MandalWiseEmpAllocationRepository mandalwiseempallocationrepo;

	@GetMapping("/mandalwiseempalloc")
	public String mandalwiseempallo(Model model) {
		List<manAlloView> mandalloc = mandalwiseempallocationrepo.getlist();
		model.addAttribute("mandalloc", mandalloc);
		System.out.println("details===================>" + mandalloc.size());
		return "ddap/Mandallocationemp";
	}

//	===================IrriMethodRBKWise==================
	@GetMapping("/irrirbkwise")
	public String getirrirbkwise(HttpSession httpSession) {

		return "ddap/Irrimethodrbkwise";
	}

//	=============================SuperCheckDistWise======================
	@GetMapping("/superchck")
	public String getsuperchck(HttpSession httpSession) {

		return "ddap/SuperChkDist";
	}

//	==========================PhysicalAck========================
	@GetMapping("/physicalack")
	public String getphysicalack(HttpSession httpSession) {

		return "ddap/PhyAck";
	}


//	==================UnlockExtDDAP==================
	@GetMapping("/unlockextddap")
	public String getunlockextddap(HttpSession httpSession) {

		return "ddap/UnlockDDAP";
	}



	@GetMapping("/irrigimwisee")
	public String irrigimwisee(String wbdcode, String cropyear) {
		System.out.println("wbdcode=>" + wbdcode);
		System.out.println("cropyear=>" + cropyear);

		return "ddap/IrriMwise";
	}

//	=====================LandOwnershipDwise====================
	@GetMapping("/ownerdwise")
	public String getownerdwise(HttpSession httpSession) {

		return "ddap/LandOwnershipDwise";
	}

//	===================LandOwnershipVwise=====================
	@GetMapping("/ownervwise")
	public String getownervwise(HttpSession httpSession) {

		return "ddap/LandOwnershipvwise";
	}

//	========================DistWisecCropinsuranceClaims===============
	@GetMapping("/cropinsclaim")
	public String getcropinsclaim(HttpSession httpSession) {

		return "ddap/Cropinsclaim";
	}

//	=================TopSixCrops===============
	@GetMapping("/topsixcrops")
	public String topsixcrops(HttpSession httpSession) {

		return "ddap/TopsixCrops";
	}

	@GetMapping("/pendvillmap")
	public String pendvillmap(Model model) {
		return "ddap/Pendingvillmap";

	}

//	======================DistAgriHortiFishDDAP================
	@GetMapping("/disthorddap")
	public String getdisthorddap(HttpSession httpSession) {

		return "ddap/DistDDAP";
	}


	@GetMapping("/superchkintf")
	public String superchkintf(Model model) {
		return "ddap/SuperCHK";

	}

	

	


	@GetMapping("/ddapsuperchk")
	public String getddap(HttpSession httpSession) {

		return "ddap/DDAPCorrectionSuperchk";
	}

//	====================DDAPCorrectionSuperCHK======================
	@Autowired
	private DDAPCorrectionRepository ddapcorrectionrepo;

	@GetMapping("/ddapcorrection")
	public String getddapcorrection(Model model) {
		List<ddapcorrectionview> ddapcor = ddapcorrectionrepo.getlist();
		model.addAttribute("ddapcor", ddapcor);
		return "ddap/DDAPCorrection";
	}

//	==================CropBookingDetailsPhotoDDAP============================
	@GetMapping("/photoddap")
	public String getphotoddap(HttpSession httpSession, Model model) {
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode" + dcode);

		List<DistrictProjections> dist = distrepo.getList();
		System.out.println("dist" + dist.size());

		model.addAttribute("dcode", dcode);
		model.addAttribute("dist", dist);

		return "ddap/CropPhotoDDAP";
	}

//	=============================SuperCHKAlloc=========================
	@GetMapping("/superalloc")
	public String getsuperalloc(HttpSession httpSession, Model model) {
		String dcode = (String) httpSession.getAttribute("dcode");
		System.out.println("dcode" + dcode);

		List<DistrictProjections> dist = distrepo.getList();
		System.out.println("dist" + dist.size());

		model.addAttribute("dcode", dcode);
		model.addAttribute("dist", dist);

		return "ddap/SuperAlloc";
	}

//	==============================IrriSrcDetVwise========================
	@GetMapping("/irrisrcdet")
	public String getirrisrcdet(HttpSession httpSession) {

		return "ddap/IrriSrcDet";
	}

//	==============================TypeFarmingDetVwise========================
	@GetMapping("/typefarmdet")
	public String gettypefarmdet(HttpSession httpSession) {

		return "ddap/TypefarmDet";
	}

	

	@GetMapping("/Aadhaar")
	public String getAadhaar(HttpSession httpSession) {

		return "ddap/AadharCn";
	}

}
