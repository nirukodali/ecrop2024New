package com.ecrops.controller;

import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.entity.AadhaarCN;
import com.ecrops.entity.AbstractExtentBookedEntity;
import com.ecrops.entity.ActiveSeason;
import com.ecrops.entity.AuthCropWise;
import com.ecrops.entity.BookingDET;
import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.CropBookingDetailsPhotoDDAP;
import com.ecrops.entity.DDAPCorrectionSocialAudit;
import com.ecrops.entity.DDAPCorrectionSuperCHK;
import com.ecrops.entity.DaoCropDetEntity;
import com.ecrops.entity.DaoSocialAuditcorrection;
import com.ecrops.entity.DataSourceExtEntity;
import com.ecrops.entity.DeptWise;
import com.ecrops.entity.DigitalAckEntity;
import com.ecrops.entity.DistAgriHortiFishDDAP;
import com.ecrops.entity.DistTabDownload;
import com.ecrops.entity.DistWiseVAAVROeKYCrabi;
import com.ecrops.entity.DistWisecCropinsuranceClaims;
import com.ecrops.entity.DistrictWiseDigiAck;
import com.ecrops.entity.DkrishiEcrop;
import com.ecrops.entity.ExtentBookedVSNormalAreaAbstract;
import com.ecrops.entity.FarmerBookingDetails;
import com.ecrops.entity.FarmerCropBookingDetailsEntity;
import com.ecrops.entity.FarmerDeatilsEcropIntf;
import com.ecrops.entity.FirstCrdetVwise;
import com.ecrops.entity.IrriMethodRBKWise;
import com.ecrops.entity.IrriSrcDetVwise;
import com.ecrops.entity.IrrigationDwise;
import com.ecrops.entity.IrrigationMwise;
import com.ecrops.entity.JowarMaizeEntity;
import com.ecrops.entity.LandOwnershipDwise;
import com.ecrops.entity.LandOwnershipVwise;
import com.ecrops.entity.MandalCorrectionSuperCheckEntity;
import com.ecrops.entity.MandalNormalAreas;
import com.ecrops.entity.MandalWiseNorm;
import com.ecrops.entity.MandalwiseEKYCEntity;
import com.ecrops.entity.NormalAreaAbs;
import com.ecrops.entity.NormalAreasMwiseMao;
import com.ecrops.entity.NormalVsExtentEntity;
import com.ecrops.entity.PhyAckMwise;
import com.ecrops.entity.PhyAckVwise;
import com.ecrops.entity.PhysicalAck;
import com.ecrops.entity.ROFRExtentAbstract;
import com.ecrops.entity.RedownloadCn;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.entity.RepLandDataDetails;
import com.ecrops.entity.Rep_Auth_Cropwise_Dist_Entity;
import com.ecrops.entity.Rep_Auth_Mandalwise_Entity;
import com.ecrops.entity.Rep_Auth_Villagewise_Entity;
import com.ecrops.entity.Rep_Auth_cropwise_Entity;
import com.ecrops.entity.Rep_DownloadedDetailsIntf;
import com.ecrops.entity.Rep_phy_ack_rbk;
import com.ecrops.entity.RofrBookedExtent;
import com.ecrops.entity.RofrBookedExtentPartitions;
import com.ecrops.entity.RofrExtBooked;
import com.ecrops.entity.SuperCHKAlloc;
import com.ecrops.entity.SuperCheckDistWise;
import com.ecrops.entity.SuperCheckIntf;
import com.ecrops.entity.SuperCheckRecordsAlloted;
import com.ecrops.entity.SuperCheckRevenueEntity;
import com.ecrops.entity.SuperChkVeriftyEntity;
import com.ecrops.entity.SupervisoryCheckReportEntity;
import com.ecrops.entity.TopSixCrops;
import com.ecrops.entity.TypeFarmingDetVwise;
import com.ecrops.entity.UnlockExtDDAP;
import com.ecrops.entity.VarietyWiseRBK;
import com.ecrops.model.RequestModel;
import com.ecrops.partition.BookingDETPartitions;
import com.ecrops.partition.DDAPCorrectionSocialAuditPartition;
import com.ecrops.partition.DDAPCorrectionSuperCHKPartitions;
import com.ecrops.partition.DaoCropBookDetRepo;
import com.ecrops.partition.DaoSocialAuditcorrectionRepo;
import com.ecrops.partition.DataSourceExtRepo;
import com.ecrops.partition.DigitalAckSMSRepo;
import com.ecrops.partition.DistTabDownloadPartition;
import com.ecrops.partition.DkrishiEcropPartiton;
import com.ecrops.partition.FarmerBookingDetailsPartition;
import com.ecrops.partition.FarmerDeatilsEcropIntfPartition;
import com.ecrops.partition.IrriSrcDetVwisePartitoins;
import com.ecrops.partition.MandalCorrectionSuperCheckRepo;
import com.ecrops.partition.MandalwiseEKYC;
import com.ecrops.partition.MandalwiseNormalAreas;
import com.ecrops.partition.NormalVsExtentRepo;
import com.ecrops.partition.PhyAckMwisePartition;
import com.ecrops.partition.PhyAckVwisePartition;
import com.ecrops.partition.Rep_Auth_Cropwise_Dist_Repo;
import com.ecrops.partition.Rep_Auth_DAO_cropwise_mand_Repo;
import com.ecrops.partition.Rep_Auth_Village_vaavroekyc_Repo;
import com.ecrops.partition.Rep_Auth_vaavroekyc_Repo;
import com.ecrops.partition.Rep_DownloadedDetailsIntfPartition;
import com.ecrops.partition.Rep_phy_ack_rbkPartition;
import com.ecrops.partition.SuperCHKAllocPartitions;
import com.ecrops.partition.SuperCheckIntfPartitions;
import com.ecrops.partition.SuperCheckRecordsAllotedPartition;
import com.ecrops.partition.SuperCheckRevenueRepo;
import com.ecrops.partition.SuperCheckVerifyPartition;
import com.ecrops.partition.SupervisoryCheckReportRepo;
import com.ecrops.partition.TypeFarmingDetVwisePartitions;
import com.ecrops.partition.AadhaarCNPartitions;
import com.ecrops.partition.AbstractExtentBookedRepo;
import com.ecrops.partition.CropBookingDetailsPhotoDDAPPartitions;
import com.ecrops.projection.MasterProjections;
import com.ecrops.projection.ROFRExtentAbstractPartitions;
import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.repo.AuthCropWisePartition;
import com.ecrops.repo.BookingSummaryExtPartitions;
import com.ecrops.repo.BookingSummaryExtRepo;
import com.ecrops.repo.DamagedJowarMaizeRepo;
import com.ecrops.repo.DeptWisePartition;
import com.ecrops.repo.DistAgriHortiFishDDAPPartitions;
import com.ecrops.repo.DistWiseVAAVROeKYCrabipartitons;
import com.ecrops.repo.DistWisecCropinsuranceClaimsPartitions;
import com.ecrops.repo.DistrictWiseDigiAckPartitions;
import com.ecrops.repo.ExtentBookedVSNormalAreaAbstractPartitions;
import com.ecrops.repo.FarmerBookingDetailsPartitions;
import com.ecrops.repo.FirstCrdetVwisePartitions;
import com.ecrops.repo.IrriMethodRBKWisePartitions;
import com.ecrops.repo.IrrigationDwisePartitions;
import com.ecrops.repo.IrrigationMwisePartitions;
import com.ecrops.repo.LandOwnershipDwisePartitions;
import com.ecrops.repo.LandOwnershipVwisePartitions;
import com.ecrops.repo.MandalLandDetailsRepository;
import com.ecrops.repo.MandalLandDetailsRepository.Mandalview;
import com.ecrops.repo.MandalWiseNormRepository;
import com.ecrops.repo.MaoAuthVaaVroekycRepo;
import com.ecrops.repo.NormalAreaAbsRepository;
import com.ecrops.repo.NormalAreasMwiseMaoRepo;
import com.ecrops.repo.PendingVillMapRepository;
import com.ecrops.repo.PendingVillMapRepository.Pendingvillmapview;
import com.ecrops.repo.PhysicalAckPartitions;
import com.ecrops.repo.RedownloadCnRepository;
import com.ecrops.repo.RepLandDataDetailsRepo;
import com.ecrops.repo.RofrExtBookedPartititons;
import com.ecrops.repo.SeasonCropBookedExtentRepo;
import com.ecrops.repo.VAADeviceDetRepository;
import com.ecrops.repo.VAADeviceDetRepository.VAADetView;
import com.ecrops.repo.VarietyWiseRBKPartitions;
import com.ecrops.repo.VillSecListDistWiseRepository;
import com.ecrops.repo.VillageLandDetailsRepository;
import com.ecrops.repo.wholeSurveyNoMandWiseRepository;
import com.ecrops.repo.wholeSurveyNoMandWiseRepository.wholemandview;
import com.ecrops.repo.SeasonCropBookedExtentRepo.RbkList;
import com.ecrops.repo.SeasonCropBookedExtentRepo.Revenue;
import com.ecrops.repo.SuperCheckDistWisePartitions;
import com.ecrops.repo.TopSixCropsPartitions;
import com.ecrops.repo.UnlockExtDDAPPartitions;
import com.ecrops.repo.VAADetailsRepository;
import com.ecrops.repo.VAADetailsRepository.VAADetailsView;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.util.MasterFunctions;  
import com.ecrops.repo.SeasonCropBookedExtentRepo.RepVaaDetails;
import com.ecrops.repo.SeasonCropBookedExtentRepo.DeviceRegDetails;
import com.ecrops.repo.SeasonCropBookedExtentRepo.NonWebView;
import com.ecrops.repo.SeasonCropBookedExtentRepo.DataCropGr;

@RestController

@RequestMapping("/util")
public class UtilRestController<VillSecView, VilllandView, pendingvillview> {

	@Autowired
	private ActiveSeasonService activeSeasonService;

@Autowired
private	SeasonCropBookedExtentRepo seasonCropBookedExtentRepo;

@Autowired
private BookingSummaryExtRepo booksumrepo;

@Autowired
	private NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;
/*		@Autowired
	private FarmerBookingDetailsRepo farmerBookingDetailsRepo;
	@Autowired
	private FarmerBookingDetailsPartitions partition;
	@Autowired
	private RofrBookedExtentPartitions rofrBookedExtentPartitions;
	@Autowired
	private MaoAuthVaaVroekycRepo maoAuthVaaVroekycRepo;
	@Autowired
	private MaoAuthVaaVroekycPartition maoAuthVaaVroekycPartition;
	@Autowired
	private RbkSurveyNoMappingRepo rbkSurveyNoMappingRepo;

	@Autowired
	private RbkSurveyNoMappingPartition rbkSurveyNoMappingPartition;

	@Autowired
	private AllocataedSurveyNoMappingRepo allocataedSurveyNoMappingRepo; */
	
	@Autowired
	private VAADeviceDetRepository vaadetrepo;
	
	@Autowired
	private ActiveSeasonRepository activeSeasonRepository;

	@GetMapping("/getAllSeason")
	public List<MasterProjections> getAllSeasonn() {
		List<MasterProjections> list = vaadetrepo.getAllSeason();
		return list;
	}
	
	@GetMapping("/getCropGroup")
	public List<MasterProjections> getCropGroup() {
		System.out.println("=======getCropGroup==========");
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllCropGrp();
		System.out.println("=======list==========" + list.size());
		return list;
	}

	
	@GetMapping("/getActiveSeason")
	public List<ActiveSeason> getActiveSeasonn() {
		List<ActiveSeason> list = activeSeasonRepository.findByActiveAndCurrentSeason();
		return list;
	}
	//==========================DistTabDownload=============================
	@Autowired
	private DistTabDownloadPartition disttabdownload;

	@GetMapping("/disttab")
	List<DistTabDownload> getdisttab(String cropyear) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		
		boolean crop1 = method.cropyearseason(cropyear);
		if (crop1) {
			List<DistTabDownload> list = disttabdownload.disttab(cropyear);
			System.out.println("details===================>" + list.size());
	//list.stream().forEach(a->System.out.println(a));
			return  list;
		}
		return null;
	}
	
	@Autowired
	private DkrishiEcropPartiton dkrishipartition;

	@GetMapping("/dkrishi1")
	List<DkrishiEcrop> getdkrishi1(String cropyear) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		
		boolean crop1 = method.cropyearseason(cropyear);
		if (crop1) {
			List<DkrishiEcrop> list = dkrishipartition.dkrishi(cropyear);
			System.out.println("details===================>" + list.size());
	//list.stream().forEach(a->System.out.println(a));
			return  list;
		}
		return null;
	}



	@GetMapping("/getAllCrop")
	public List<MasterProjections> getAllCrop() {
		List<MasterProjections> list = vaadetrepo.getAllCrops();
		return list;
	}

	@GetMapping("/getAllMandals")
	public List<MasterProjections> getMandals(Integer dcode) {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllMandals(dcode);
		return list;
	}
	
	@GetMapping("/getAllwbdcode")
	public List<MasterProjections> getAllwbdcode() {
		List<MasterProjections> list = booksumrepo.getwbdcode();
		return list;
	}

	@GetMapping("/getAllwbmandals")
	public List<MasterProjections> getAllwbmandals(String wbdcode) {
		System.out.println("wbdcode=>" + wbdcode);
		List<MasterProjections> list = booksumrepo.getwbmandal(Integer.parseInt(wbdcode));
		return list;
	}

	@GetMapping("/getAllwbvcode")
	public List<MasterProjections> getAllwbvcode(String wbdcode, String wbmcode) {
		List<MasterProjections> list = booksumrepo.getwbvillages(Integer.parseInt(wbdcode), Integer.parseInt(wbmcode));
		return list;
	}
	
	
	//**************************************************//Villages//*************************************************
		@GetMapping("/getAllVillages")
		public List<MasterProjections> getVillages(Integer dcode, Integer mcode) {
			System.out.println("dcode=>" + dcode);
			System.out.println("mcode=>" + mcode);
			List<MasterProjections> list = seasonCropBookedExtentRepo.getAllVillages(dcode, mcode);
			return list;
		}

    	@GetMapping("/getCrpName")
		public List<MasterProjections> getCrpName() {
			List<MasterProjections> list = seasonCropBookedExtentRepo.getCrpName();
			return list;
		}
    	
    	@GetMapping("/getCropGroupid")
    	public List<MasterProjections> getCropGoupidd(String grpcode) {
    		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllCrpGrpid(Integer.parseInt(grpcode));
    		System.out.println("=======list==========" + list.size());
    		return list;
    	}


	@Autowired
	private RedownloadCnRepository redownrepo;

	  @PostMapping("/getredown")
	    public List<RedownloadCn> getRedown(
	            @RequestHeader(value = "X-Dcode") String dcode,@RequestParam("cropyear") String cropyear) {
		System.out.println("getredown");
		System.out.println("cropyear" + cropyear);
		System.out.println("dcode" + dcode);

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		Integer ddcode = Integer.parseInt(dcode);

		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		List<RedownloadCn> list=null;
		boolean dist = method.districtCode(dcode);
		if (dist) {
			try {
				
				if(seasonYear==2023) {
			 list = redownrepo.getListt(seasonYear, seasonType, ddcode);
				}
				else if(seasonYear==2024) {
					 list = redownrepo.getListt4(seasonYear, seasonType, ddcode);
						}
				else {
					 list = redownrepo.getListtP(seasonYear, seasonType, ddcode);
						}
			}
			catch(Exception e) {
				
			}
			
		}
		return list;
	}
	
	@Autowired
	private BookingDETPartitions bookdetpartition;

	@GetMapping("/getbookdet")
	List<BookingDET> getbookdet(String cropyear, String wbdcode, String wbmcodenew, String wbvcode, String cropid) {
		System.out.println("getbookdet");
		System.out.println("cropyear''''''>" + cropyear);
		System.out.println("wbdcode'''''''>" + wbdcode);
		System.out.println("wbmcode'''''''>" + wbmcodenew);
		System.out.println("wbvcode'''''''>" + wbvcode);
		System.out.println("cropid''''''''>" + cropid);

		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		int a = (activeSeasonService.listAll().get(0).getCropyear());
		boolean crop1 = method.cropyearseason(cropyear);
		boolean dist = method.wbdCode(wbdcode);
		boolean mand = method.wbmcode(wbmcodenew);
		if (crop1 && dist && mand) {
			List<BookingDET> list = bookdetpartition.booking(cropyear, wbdcode, wbmcodenew, a,cropid,wbvcode);
			System.out.println("details===================>" + list.size());

			return list;
		}
		return null;
	}







//	=============================DDAP================================
//=====================AuthCropWise=======================

	@Autowired
	private AuthCropWisePartition authcropwisepartition;

	@GetMapping("/getauth")
	List<AuthCropWise> getauth(String cropyear, HttpSession session, String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);
		return null;
	}

	@GetMapping("/getauth1")
	List<AuthCropWise> getauthcropwise(String cropyear, String userid, String cropid) {
		System.out.println("cropyear===================>" + cropyear);
		System.out.println("userid===================>" + userid);
		System.out.println("cropid===================>" + cropid);

		int a = (activeSeasonService.listAll().get(0).getCropyear());
		List<AuthCropWise> cropwise = authcropwisepartition.authcropwise(cropyear, cropid, a);
		System.out.println("details===================>" + cropwise.size());

		return cropwise;
	}



//		=====================VAADeviceDetails================
	@Autowired
	private VAADeviceDetRepository vaadevicedetrepo;

	@RequestMapping("/getvaadvicedetreport")
	List<VAADetView> getList1(@RequestParam("dcode") String dcode) {
//		
//		String dcode= httpServletRequest.getParameter("inputdcode");
		System.out.println("dcode=>" + dcode);

		List<VAADetView> list = vaadevicedetrepo.getListt(dcode);

		System.out.println("list size=>" + list.size());

		return list;
	}

	@GetMapping("/vaadet")
	public ResponseEntity<?> getVAADet(@RequestParam("dcode") String dcode) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		boolean dist= method.districtCode(dcode);
		System.out.println(""+dist);
		List<RepVaaDetails> vaadet = null ;
		if(dist) {
		vaadet = seasonCropBookedExtentRepo.getVaaDet(dcode);
		}
		return new ResponseEntity<List<RepVaaDetails>>(vaadet, HttpStatus.OK);
	}
	
	//-------------------------------------- Device Reg Details----------------------------------------------//
		@GetMapping("/devregdet")
		public ResponseEntity<?> getDevRegDetails(@RequestParam("dcode") String dcode) {
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();
			boolean dist= method.districtCode(dcode);
			System.out.println(""+dist);
			List<DeviceRegDetails> devregdet = null ;
			if(dist) {
				devregdet = seasonCropBookedExtentRepo.getDevRegDet(dcode);
			}
				return new ResponseEntity<List<DeviceRegDetails>>(devregdet, HttpStatus.OK);
		}


	@GetMapping("/VillageSecretariatList")
	public ResponseEntity<?> getVillages(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		Boolean dist= method.districtCode(dcode);
		Boolean mand= method.mandalCode(mcode);
		List<RbkList> rbklist=null;
		if(dist && mand) {
		 rbklist = seasonCropBookedExtentRepo.getVillages(Integer.parseInt(dcode),
				Integer.parseInt(mcode));
		}
		return new ResponseEntity<List<RbkList>>(rbklist, HttpStatus.OK);
		
	}
	@GetMapping("/RbkVsRevenue")
	public ResponseEntity<?> getRbkVsRevenue(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		Boolean dist= method.districtCode(dcode);
		Boolean mand= method.mandalCode(mcode);
		List<Revenue> rbklist = null;
		if(dist && mand) {
		 rbklist = seasonCropBookedExtentRepo.getRbkVsRevenue(Integer.parseInt(dcode),
				Integer.parseInt(mcode));
		}
		return new ResponseEntity<List<Revenue>>(rbklist, HttpStatus.OK);
	}
	@Autowired
	PhyAckMwisePartition phyAckMwisePartition;

	@PostMapping("/Mphyack")
	List<PhyAckMwise> getPhyAckMwise(@RequestBody RequestModel requestModel) throws SQLException {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		boolean wbdcode= method.districtCode(requestModel.getWbdcode());
		String[] cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(seasonType);
		boolean year = method.year(cropyear[1]);
		List<PhyAckMwise> Mphyack = null;
		
		if(wbdcode && year && season) { 
//			if(seasonYear==2022 ) {
//				return new ArrayList<>(); 
//		}
//			if(seasonYear==2023 && seasonType.equals("S")) {
//				return new ArrayList<>(); 
//		}
			try {
		 Mphyack = phyAckMwisePartition.getMPhyAck(requestModel.getWbdcode(),requestModel.getCropyear());
			}
			catch(Exception e) {
				
			}
		}
		if(Mphyack != null)
		return Mphyack;
		else
			return new ArrayList<>(); 
	}
	
	// ===========================//Abstract Extent Booked//================//
	@Autowired
	AbstractExtentBookedRepo abstractExtentBookedRepo;

	@PostMapping("/abstractExtentBooked")
	public ResponseEntity<?> getabstractExtentBooked(@RequestBody RequestModel requestModel) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		boolean dcode = method.districtCode(requestModel.getDcode());
		boolean cropgrp = method.cropGroup(requestModel.getCropgrp());
		boolean cropid = method.cropid(requestModel.getCropid());
		System.out.println("Cropgrouppppppppppppp"+cropgrp);
		System.out.println("Cropiddddddddddddddddddd"+cropid);

		String  cropgroup ="";   String cropids="";
		String[] cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		
		List<AbstractExtentBookedEntity> abstractExtentBooked = null;
		if(seasonType.equalsIgnoreCase("s") && seasonYear==2023) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
		}
		if((requestModel.getCropgrp() == null || requestModel.getCropgrp().isEmpty())&& (requestModel.getCropid() == null || requestModel.getCropid().isEmpty())) {
			if(dcode && season && year) {
				abstractExtentBooked = abstractExtentBookedRepo.getabstractExtentBooked(requestModel.getDcode(),requestModel.getCropyear(),cropgroup,cropids);
			}
		}
		else if ((requestModel.getCropgrp() != null || !requestModel.getCropgrp().isEmpty()) && (requestModel.getCropid() == null || requestModel.getCropid().isEmpty())) {
			cropgroup = (requestModel.getCropgrp());
		 if(dcode && cropgrp && season && year) {
			 abstractExtentBooked = abstractExtentBookedRepo.getabstractExtentBooked(requestModel.getDcode(),requestModel.getCropyear(),cropgroup,cropids);
				}
		}
		else {
			cropids = (requestModel.getCropid());
			if(dcode && cropid && season && year) {
				abstractExtentBooked = abstractExtentBookedRepo.getabstractExtentBooked(requestModel.getDcode(),requestModel.getCropyear(),cropgroup,cropids);
				}	
		}
		if (abstractExtentBooked == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
        } else {
            return ResponseEntity.ok(abstractExtentBooked);
        }
    }



	@Autowired
	private DaoSocialAuditcorrectionRepo daoSocialAuditcorrectionRepo;

	@PostMapping("/daoSocialAudit")
	public ResponseEntity<?> getMandalSocialAudit(@RequestBody RequestModel requestModel) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		boolean dcode= method.districtCode(requestModel.getDcode());
		String[] cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		
		List<DaoSocialAuditcorrection> mandalSocialAudit = null;
		try {
			if(dcode && season && year) {	
			 mandalSocialAudit = daoSocialAuditcorrectionRepo
					.getMandalSocialAudit(requestModel.getDcode(), requestModel.getCropyear());
			}
			return new ResponseEntity<List<DaoSocialAuditcorrection>>(mandalSocialAudit, HttpStatus.OK);
		} catch (Exception e) {
			return null;
		}
	}
	
	//--------------supercheck------------------------//
	
			// ===========================//SuperCheck Verification List//================//
					@Autowired
					private SuperCheckVerifyPartition superCheckVerifyPartition ;

					@PostMapping("/supercheckRecords")
					public ResponseEntity<?> getsupercheckverify(@RequestBody RequestModel requestModel) {
						RegularExpressionclassMethod method = new RegularExpressionclassMethod();
						boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
						String[] cropyear = requestModel.getCropyear().split("@");
						boolean season = method.season(cropyear[0]);
						boolean year = method.year(cropyear[1]);
						List<SuperChkVeriftyEntity> supercheckverify = null;
						try {
							if(wbdcode && season && year) {
							supercheckverify = superCheckVerifyPartition
									.getsupercheckverify(requestModel.getWbdcode(),requestModel.getUserid(), requestModel.getCropyear());
							System.out.println("mandalspredchck size=>" + supercheckverify.size());
							}
							return new ResponseEntity<List<SuperChkVeriftyEntity>>(supercheckverify, HttpStatus.OK);
						} catch (Exception e) {

							System.out.println("getStackTrace =>" + e.getStackTrace());
							return null;
						}
					}	
					
					// ===========================//SuperCheck Report//================//
					@Autowired
					private SupervisoryCheckReportRepo supervisoryCheckReportRepo  ;

					@PostMapping("/supercheckReports")
					public ResponseEntity<?> getsupercheckreport(@RequestBody RequestModel requestModel) {
						RegularExpressionclassMethod method = new RegularExpressionclassMethod();
						boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
						String[] cropyear = requestModel.getCropyear().split("@");
						boolean season = method.season(cropyear[0]);
						boolean year = method.year(cropyear[1]);
						List<SupervisoryCheckReportEntity> supercheckreport = null;
						try {
							if(wbdcode && season && year) {
							supercheckreport = supervisoryCheckReportRepo
									.getsupercheckreport(requestModel.getWbdcode(),requestModel.getUserid(), requestModel.getCropyear());
							System.out.println("mandalspredchck size=>" + supercheckreport.size());
							}
							return new ResponseEntity<List<SupervisoryCheckReportEntity>>(supercheckreport, HttpStatus.OK);
						} catch (Exception e) {

							System.out.println("getStackTrace =>" + e.getStackTrace());
							return null;
						}
					}	
			// ===========================//Super Check Revenue //===============//
			@Autowired
			private SuperCheckRevenueRepo checkRevenueRepo;

			@PostMapping("/supercheckrevenue")
			public ResponseEntity<?> getsupercheckrevenue(@RequestBody RequestModel requestModel) {
				
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<SuperCheckRevenueEntity> superchckrevenue = null;
				try {
					if(wbdcode && season && year) {
					superchckrevenue = checkRevenueRepo.getsupercheckrevenue(requestModel.getWbdcode(), requestModel.getCropyear());
					}
					return new ResponseEntity<List<SuperCheckRevenueEntity>>(superchckrevenue, HttpStatus.OK);
				} catch (Exception e) {
					return null;
				}
			}
			
			// ===========================//Mandal wise SuperCheck Redressed Report//================//
			@Autowired
			private MandalCorrectionSuperCheckRepo mandalCorrectionSuperCheckRepo;

			@PostMapping("/mandasuperchckredressed")
			public ResponseEntity<?> getmandasuperchckredressed(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<MandalCorrectionSuperCheckEntity> mandalspredchck = null;
				try {
					if(wbdcode && season && year) {
					mandalspredchck = mandalCorrectionSuperCheckRepo
							.getmandasuperchckredressed(requestModel.getWbdcode(), requestModel.getCropyear());
					System.out.println("mandalspredchck size=>" + mandalspredchck.size());
					}
					return new ResponseEntity<List<MandalCorrectionSuperCheckEntity>>(mandalspredchck, HttpStatus.OK);
				} catch (Exception e) {

					System.out.println("getStackTrace =>" + e.getStackTrace());
					return null;
				}
			}	

			//-----------------------------------------DETAILED REPORTS ------------------------------------------------------//
			
	  // ------------------------------------------//Mandal EkYC //--------------------------------//
			@Autowired
			private MandalwiseEKYC mandalwiseEKYC;

			@PostMapping("/mandalekyc")
			public ResponseEntity<?> getMandalwiseEKYC(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<MandalwiseEKYCEntity> mandalekyc = null;
				
				try {
					if(wbdcode && season && year) {
		              mandalekyc = mandalwiseEKYC.getMandalwiseEKYC(requestModel.getWbdcode(),
							requestModel.getCropyear());
					}
					return new ResponseEntity<List<MandalwiseEKYCEntity>>(mandalekyc, HttpStatus.OK);
				} catch (Exception e) {

					System.out.println("getStackTrace =>" + e.getStackTrace());
					return null;
				}
			}
			
			// ========---------===================//DataSourceExtRepo//===========-------------------------------------=====//
			@Autowired
			private DataSourceExtRepo dataSourceExtRepo;

			@PostMapping("/datasourceext")
			public ResponseEntity<?> getDataSourceExt(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<DataSourceExtEntity> datasourceext = null;
				try {
					if(wbdcode && season && year) {
					datasourceext = dataSourceExtRepo.getDataSourceExt(requestModel.getWbdcode(),
							requestModel.getCropyear());
					}
				return new ResponseEntity<List<DataSourceExtEntity>>(datasourceext, HttpStatus.OK);
				} catch (Exception e) {
					return null;
				}
			}
			
			// ================Farmer Crop Booking Details ====================//

			@Autowired
			FarmerBookingDetailsPartition farmerBookingDetailsPartition ;

			@Autowired
			MasterFunctions masterFunctions;

			 @PostMapping("/farmerCrpbooking")
			    public ResponseEntity<?> getFarmerwisecropbooking(@RequestBody RequestModel requestModel) {
				 RegularExpressionclassMethod method = new RegularExpressionclassMethod();
					boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
					boolean dcode = method.districtCode(requestModel.getDcode());
					boolean mcode = method.mandalCode(requestModel.getMcode());
					boolean vcode = method.villageCode(requestModel.getVcode());
					boolean cropid = method.cropid(requestModel.getCropid());
					String[]cropyear = requestModel.getCropyear().split("@");
					String seasonType = cropyear[0];
					Integer seasonYear = Integer.parseInt(cropyear[1]);
					boolean season = method.season(cropyear[0]);
					boolean year = method.year(cropyear[1]);
					System.out.println(""+wbdcode+dcode+mcode+vcode+cropid+season+year);
					List<FarmerCropBookingDetailsEntity> farmerCropBooking = null;
						if(wbdcode && dcode && mcode && vcode && cropid && season && year) {
			             farmerCropBooking = farmerBookingDetailsPartition.getFarmerwisecropbooking(
			                    requestModel.getWbdcode(), requestModel.getDcode(), requestModel.getMcode(), requestModel.getVcode(),
			                    requestModel.getCropyear(), requestModel.getCropid());
						}
			            if (farmerCropBooking.isEmpty()) {
			                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
			            } else {
			                return ResponseEntity.ok(farmerCropBooking);
			            }
			    }

			// ===========================//Mandal Normal Areas//================//
			 
			 
			 @GetMapping("/getDistrict")
				public List<MasterProjections> getDistrict(Integer dcode) {
					List<MasterProjections> list = seasonCropBookedExtentRepo.getDistrict(dcode);
					return list;
				}

			@Autowired
			private MandalwiseNormalAreas mandalwiseNormalAreas;

			@PostMapping("/mandNorm")
			public ResponseEntity<?> getmandalNormalAreas(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean dcode = method.districtCode(requestModel.getDcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<MandalNormalAreas> normalareas  = null;

				try {
					if(dcode && season && year) {
					normalareas = mandalwiseNormalAreas.getmandalNormalAreas(requestModel.getDcode(),
							requestModel.getCropyear(), requestModel.getRole());
					}
					return new ResponseEntity<List<MandalNormalAreas>>(normalareas, HttpStatus.OK);
				} catch (Exception e) {
					System.out.println("getStackTrace =>" + e.getStackTrace());
					return null;
				}
			}
			
			// ===========================//Normal Area Vs Extent //================//
			@Autowired
			private NormalVsExtentRepo normalVsExtentRepo;

			@PostMapping("/getnormalVsextent")
			public ResponseEntity<?> getnormalVsextent(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean dcode = method.districtCode(requestModel.getDcode());
				boolean mcode = method.mandalCode(requestModel.getMcode());
				String  mandal ="";
				boolean cropid = method.cropid(requestModel.getCropid());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<NormalVsExtentEntity> normvsext = null;
				if(requestModel.getMcode() == null || requestModel.getMcode().isEmpty()) {
					if(dcode && cropid && season && year) {
						normvsext = normalVsExtentRepo.getnormalVsextent(requestModel.getCropid(),
								requestModel.getDcode(), mandal, requestModel.getCropyear());
						}	
				}
				else{
				 mandal = (requestModel.getMcode().toString());
				 if(dcode && mcode && cropid && season && year) {
						normvsext = normalVsExtentRepo.getnormalVsextent(requestModel.getCropid(),
								requestModel.getDcode(), mandal, requestModel.getCropyear());
						}
				}
				if (normvsext == null) {
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
	            } else {
	                return ResponseEntity.ok(normvsext);
	            }
		    }

			
			// ===========================//DAO Crop Booking Details Report//================//
					@Autowired
					private DaoCropBookDetRepo cropBookDetRepo;

					@PostMapping("/getdaoCropBookingDetails")
					public ResponseEntity<?> getdaoCropBookingDetails(@RequestBody RequestModel requestModel) {
						RegularExpressionclassMethod method = new RegularExpressionclassMethod();
						boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
						boolean dcode = method.districtCode(requestModel.getDcode());
						boolean mcode = method.mandalCode(requestModel.getMcode());
						String[] cropyear = requestModel.getCropyear().split("@");
						boolean season = method.season(cropyear[0]);
						boolean year = method.year(cropyear[1]);
						List<DaoCropDetEntity> cropbokdet = null;
							if(wbdcode && dcode && mcode && season && year) {
							cropbokdet = cropBookDetRepo.getdaoCropBookingDetails(requestModel.getWbdcode(),
									requestModel.getDcode(), requestModel.getMcode(), requestModel.getCropyear());
							}
							if (cropbokdet.isEmpty()) {
				                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
				            } else {
				                return ResponseEntity.ok(cropbokdet);
				            }
				    }
					
					
			@GetMapping("/getJowarSeasons")
			public List<MasterProjections> getJowarSeasons() {
				List<MasterProjections> list = normalAreasMwiseMaoRepo.getJowarSeasons();
				return list;
			}
			@GetMapping("/getJowarDistricts")
			public List<MasterProjections> getJowarDistricts() {
				List<MasterProjections> list = normalAreasMwiseMaoRepo.getJowarDistricts();
				return list;
			}
			
			@Autowired
			private DamagedJowarMaizeRepo damagedJowarMaizeRepo;
			
			@PostMapping("/jowarMaizeCrops")
			public ResponseEntity<?> getJowarMaizeCrops(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean dcode= method.districtCode(requestModel.getDcode());
				System.out.println(""+dcode);
				List<JowarMaizeEntity> jowarandmaize = null;
					if(dcode) {
						jowarandmaize = damagedJowarMaizeRepo.getJowarMaizeCrops(requestModel.getDcode());
					}
					if (jowarandmaize.isEmpty()) {
		                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
		            } else {
		                return ResponseEntity.ok(jowarandmaize);
		            }
		    }

			// ----Dao Crop Grev
			
			@GetMapping("/dataCropGr")
			public ResponseEntity<?> getdataCropGr(@RequestParam("dcode") String dcode) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean dcodee = method.districtCode(dcode);
				List<DataCropGr> CropGerv  = null;
				try {
					if(dcodee) {
				CropGerv = seasonCropBookedExtentRepo.getdataCropGr(Integer.parseInt(dcode));
					}
					return new ResponseEntity<List<DataCropGr>>(CropGerv, HttpStatus.OK);
				} catch (Exception e) {

					System.out.println("getStackTrace =>" + e.getStackTrace());
					return null;
				}
			}
			
			// ===========================//Digital Ack SMS//============//
			@Autowired
			private DigitalAckSMSRepo digitalAckSMSRepo;

			@PostMapping("/digitalAckSMS")
			public ResponseEntity<?> getDitialAckSMS(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean dcode = method.districtCode(requestModel.getDcode());
				boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				List<DigitalAckEntity> digitalack = null;
				try {
					if (dcode && wbdcode && year && season) {
						digitalack = digitalAckSMSRepo.getDitialAckSMS(requestModel.getWbdcode(), requestModel.getDcode(),requestModel.getCropyear());
					}
					return new ResponseEntity<List<DigitalAckEntity>>(digitalack, HttpStatus.OK);
				} catch (Exception e) {

					return null;
				}
			}
			
			//-------------------Rep_Auth_Mandal_cropwise-----------------------//
			
			
			@Autowired
			private Rep_Auth_vaavroekyc_Repo rep_Auth_vaavroekyc_Repo ;
			
			@PostMapping("/repMandalAuthCrop")
			public ResponseEntity<?> getrepMandalAuthCrop(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean dcode= method.districtCode(requestModel.getDcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				String seasonType = cropyear[0];
				Integer seasonYear = Integer.parseInt(cropyear[1]);
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				
				List<Rep_Auth_Mandalwise_Entity> repMandalAuthCrop = null;
					if(season && year && dcode) {		
						if(seasonYear==2022) {
							return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
						}
						else{
							repMandalAuthCrop = rep_Auth_vaavroekyc_Repo.getrepMandalAuthCrop(requestModel.getCropyear(),requestModel.getDcode());						
						}
					}
					if (repMandalAuthCrop.isEmpty()) {
		                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
		            } else {
		                return ResponseEntity.ok(repMandalAuthCrop);
		            }
		    }	
	
			//-------------------Rep_Auth_Village_cropwise-----------------------//
			
			
			@Autowired
			private Rep_Auth_Village_vaavroekyc_Repo village_vaavroekyc_Repo  ;
			
			@PostMapping("/repVillageAuthCrop")
			public ResponseEntity<?> getrepVillageAuthCrop(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean wbdcode= method.wbdCode(requestModel.getWbdcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				String seasonType = cropyear[0];
				Integer seasonYear = Integer.parseInt(cropyear[1]);
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				
				List<Rep_Auth_Villagewise_Entity> repVillageAuthCrop = null;
					if(season && year && wbdcode) {
						if(seasonType.equalsIgnoreCase("S") && seasonYear==2023) {
							return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
						}
						else if(seasonYear==2022) {
							return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
						}
						else{
							repVillageAuthCrop = village_vaavroekyc_Repo.getrepVillageAuthCrop(requestModel.getCropyear(),requestModel.getWbdcode());						
						}
					}
					if (repVillageAuthCrop.isEmpty()) {
		                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
		            } else {
		                return ResponseEntity.ok(repVillageAuthCrop);
		            }
		    }	
	
			//-------------------Rep_Auth_cropwise_Dist-----------------------//
			
			
			@Autowired
			private Rep_Auth_Cropwise_Dist_Repo rep_Auth_Cropwise_Dist_Repo ;
			
			@PostMapping("/repAuthCropDist")
			public ResponseEntity<?> getrepAuthCropDist(@RequestBody RequestModel requestModel) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean cropid= method.cropid(requestModel.getCropid());
				boolean dcode= method.districtCode(requestModel.getDcode());
				String[] cropyear = requestModel.getCropyear().split("@");
				String seasonType = cropyear[0];
				Integer seasonYear = Integer.parseInt(cropyear[1]);
				boolean season = method.season(cropyear[0]);
				boolean year = method.year(cropyear[1]);
				
				List<Rep_Auth_Cropwise_Dist_Entity> repAuthCropDist = null;
					if(cropid && dcode && season && year) {			
						if(seasonType.equalsIgnoreCase("k") && seasonYear==2022) {
							return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
						}
						else if(seasonType.equalsIgnoreCase("s") && seasonYear==2023) {
							return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
						}
						else {
						repAuthCropDist = rep_Auth_Cropwise_Dist_Repo.getrepAuthCropDist(requestModel.getCropyear(),requestModel.getCropid(),requestModel.getDcode());
						}
						}
					if (repAuthCropDist.isEmpty()) {
		                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
		            } else {
		                return ResponseEntity.ok(repAuthCropDist);
		            }
		    }
	
//-------------------Rep_Auth_cropwise_mand-----------------------//
	
	
	@Autowired
	private Rep_Auth_DAO_cropwise_mand_Repo rep_Auth_DAO_cropwise_mand_Repo;
	
	@PostMapping("/repAuthCrop")
	public ResponseEntity<?> getrepAuthCrop(@RequestBody RequestModel requestModel) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		boolean cropid= method.cropid(requestModel.getCropid());
		boolean dcode= method.districtCode(requestModel.getDcode());
		String[] cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		
		List<Rep_Auth_cropwise_Entity> repAuthCrop = null;
			if(cropid && dcode && season && year) {
				if(seasonType.equalsIgnoreCase("k") && seasonYear==2022) {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
				}
				else if(seasonType.equalsIgnoreCase("s") && seasonYear==2023) {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
				}
				else {
				repAuthCrop = rep_Auth_DAO_cropwise_mand_Repo.getrepAuthCrop(requestModel.getCropyear(),requestModel.getCropid(),requestModel.getDcode());
			}
			}
			if (repAuthCrop.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No results found");
            } else {
                return ResponseEntity.ok(repAuthCrop);
            }
    }
	

//			==============for english cropnames================
			
			@Autowired
			private MaoAuthVaaVroekycRepo maoAuthVaaVroekycRepo;

			@GetMapping("/getAllCropeng")
			public List<MasterProjections> getAllCropeng() {
				List<MasterProjections> list = maoAuthVaaVroekycRepo.getAllCropeng();
				return list;
			}

//			======================districts=========================
			@GetMapping("/getAlldistrict")
			public List<MasterProjections> getAlldistrict() {
				List<MasterProjections> list = booksumrepo.getdistrict();
				return list;
			}
			// ******************************Mandalquery*********************

			@GetMapping("/getAllmandal")
			public List<MasterProjections> getAllmandal(@RequestParam("dcode") String dcode) {
				List<MasterProjections> list = booksumrepo.getmandal(Integer.parseInt(dcode));
				return list;
			}
//			<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<    DDAP      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//			=====================VAADetails================
			@Autowired
			private VAADetailsRepository vaarepo;
			

			@GetMapping("/getvaareport")
			List<VAADetailsView> getList(@RequestParam("dcode") String dcode) {
				System.out.println("dcode=>" + dcode);

				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				Boolean dist = method.districtCode(dcode);
				List<VAADetailsView> list = null;
				if (dist) {
					list = vaarepo.getListt(dcode);
				}
				System.out.println("list size=>" + list.size());

				return list;
			}
			


		//=====================VAASecList================
			@Autowired
			private VillSecListDistWiseRepository villseclistrepo;

			@GetMapping("/villsec")
			List<VillSecView> getListt(String dcode, String mcode) {
				System.out.println("dcode" + dcode);
				System.out.println("mcode" + mcode);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean dist = method.districtCode(dcode);
				boolean mand = method.mandalCode(mcode);
				List<VillSecView> list = null;

				if (dist && mand) {
					list = (List<VillSecView>) villseclistrepo.getListt(Integer.parseInt(dcode), Integer.parseInt(mcode));
					return list;
				}
				return null;
			}


//			==================DistWise==================
		@Autowired
		private DeptWisePartition departwisepartition;

		@GetMapping("/getdepartwise")
		List<DeptWise> getdepartwise(String cropyear) {
			System.out.println("cropyear=>" + cropyear);
			int a = (activeSeasonService.listAll().get(0).getCropyear());

			List<DeptWise> list1 = departwisepartition.distwise(cropyear, a);
			System.out.println("details===================>" + list1.size());
			return list1;
		}

		//=====================bookingsummary==================
			@Autowired
			private BookingSummaryExtPartitions bookingSummaryExtPartitions;

			@GetMapping("/getbookext")
			List<BookingSummaryExtent> getbooksum(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<BookingSummaryExtent> list1 = bookingSummaryExtPartitions.bookingsum(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}
			
//			======================wholeSurveyNoMandWise========================
			@Autowired
			private wholeSurveyNoMandWiseRepository wholemandrepo;

			@GetMapping("/wholemand")
			List<wholemandview> getList(String wbdcode, String wbmcodenew) {
				System.out.println("in getListtttttttt");
				System.out.println("wbdcode===>" + wbdcode);
				System.out.println("wbmcodenew===>" + wbmcodenew);
				int wbdcode1 = Integer.parseInt(wbdcode);
				int wbmcodenew1 = Integer.parseInt(wbmcodenew);

				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean dist = method.wbdCode(wbdcode);
				boolean mand = method.mandalCode(wbmcodenew);
				List<wholemandview> list = null;
				try {
					list = wholemandrepo.getListt(wbdcode1, wbmcodenew1);
					System.out.println("details===================>" + list.size());
				}
				catch(Exception e) {
					System.out.println(e);
				}

					return list;
			}

//			================MandLandDetails===============
		@Autowired
		private MandalLandDetailsRepository mandlandrepo;

		@GetMapping("/mandland")
		List<Mandalview> getList11(@RequestParam("dcode") String dcode) {
			System.out.println("dcode=>" + dcode);

			List<Mandalview> list = mandlandrepo.getListt(Integer.parseInt(dcode));

			System.out.println("list size=>" + list.size());

			return list;

		}

//			================VillageLandDetails===============
		@Autowired
		private VillageLandDetailsRepository villandrepo;

		@GetMapping("/villageland")
		List<VilllandView> getList11(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
			System.out.println("dcode=>" + dcode);
			System.out.println("mcode==>" + mcode);

			List<VilllandView> list = (List<VilllandView>) villandrepo.getListt(Integer.parseInt(dcode),
					Integer.parseInt(mcode));

			System.out.println("list size=>" + list.size());

			return list;

		}
		//==========================RofrExtBooked=============
		@Autowired
		private RofrExtBookedPartititons rofrextpartitions;

		@GetMapping("/getrofrext")
		List<RofrExtBooked> getrofrext(String cropyear) {
			System.out.println("cropyear=>" + cropyear);

			int a = (activeSeasonService.listAll().get(0).getCropyear());
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();

			boolean crop = method.cropyearseason(cropyear);
			if (crop) {
				List<RofrExtBooked> list1 = rofrextpartitions.rofrextbooked(cropyear, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}
			return null;
		}


		//======================================FarmerDeatilsEcropIntf======================
		@Autowired
		private FarmerDeatilsEcropIntfPartition farmerecroppartition;

		@GetMapping("/getfarmerecrop")
		List<FarmerDeatilsEcropIntf> getfarmerecrop(String dcode, String mcode, String vcode, String cropyear,
				HttpSession session) {
			String[] season = cropyear.split("@");
			String seasonType = season[0];
			String year = season[1];
			Integer seasonYear = Integer.parseInt(season[1]);

			Integer ddcode = Integer.parseInt(dcode);
			Integer mmcode = Integer.parseInt(mcode);
			Integer vvcode = Integer.parseInt(vcode);
			int a = (activeSeasonService.listAll().get(0).getCropyear());
			String wbdcode = (String) session.getAttribute("wbdcode");

			List<FarmerDeatilsEcropIntf> Farmerecrop = farmerecroppartition.farmerecrop(cropyear, dcode, mcode, vcode,
					wbdcode, a);

			return Farmerecrop;

		}

		@GetMapping("/getfarmerecrop1")
		List<FarmerDeatilsEcropIntf> getfarmerecrop1(String dcode, String mcode, String vcode, String cropyear,
				String wbdcode, String userid) {
			System.out.println("dcode===================>" + dcode);
			System.out.println("mcode===================>" + mcode);
			System.out.println("vcode===================>" + vcode);
			System.out.println("cropyear===================>" + cropyear);
			System.out.println("wbdcode===================>" + wbdcode);
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();
			int a = (activeSeasonService.listAll().get(0).getCropyear());

			boolean crop1 = method.cropyearseason(cropyear);
			boolean dist = method.districtCode(dcode);
			boolean mand = method.mandalCode(mcode);
			boolean vill = method.villageCode(vcode);

			if (crop1 && dist && mand && vill) {
				List<FarmerDeatilsEcropIntf> list = farmerecroppartition.farmerecrop(cropyear, dcode, mcode, vcode, wbdcode,
						a);
				System.out.println("details===================>" + list.size());

				return list;
			}
			return null;
		}


			//	=====================DistrictWiseDigiAck================
		@Autowired
		private DistrictWiseDigiAckPartitions districtackpartition;

		@GetMapping("/getdistack")
		List<DistrictWiseDigiAck> getdistack(String cropyear) {
			System.out.println("cropyear=>" + cropyear);
			RegularExpressionclassMethod method = new RegularExpressionclassMethod();

			int a = (activeSeasonService.listAll().get(0).getCropyear());

			boolean crop = method.cropyearseason(cropyear);
			if (crop) {
				List<DistrictWiseDigiAck> list1 = districtackpartition.distwiseack(cropyear, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}
			return null;
		}

		//===================NormalSreaAbs=============
		@Autowired
		private NormalAreaAbsRepository normalareaabsrepo;

		@GetMapping("/getnormalabs")
		List<NormalAreaAbs> getList151(String cropyear) {

			String[] season = cropyear.split("@");
			String seasonType = season[0];
			Integer seasonYear = Integer.parseInt(season[1]);

			RegularExpressionclassMethod method = new RegularExpressionclassMethod();
			List<NormalAreaAbs> list= null;
			try {
				 list = normalareaabsrepo.getListt(seasonYear, seasonType);
			}
			catch(Exception e) {
				System.out.println(e);
			}
				return list;
		}


		//================MandalWiseNorm========================
		@Autowired
		private MandalWiseNormRepository mandalnormrepo;

		@GetMapping("/getmandalnorm")
		List<MandalWiseNorm> getList1(String cropyear, String dcode) {
			System.out.println("getmandalnorm");
			System.out.println("cropyear" + cropyear);
			System.out.println("dcode" + dcode);

			String[] season = cropyear.split("@");
			String seasonType = season[0];
			Integer seasonYear = Integer.parseInt(season[1]);

			Integer ddcode = Integer.parseInt(dcode);

			RegularExpressionclassMethod method = new RegularExpressionclassMethod();
			List<MandalWiseNorm> list = null;
			boolean crop1 = method.cropyearseason(cropyear);
			if (crop1) {
				 list = mandalnormrepo.getListt(seasonYear, seasonType, ddcode);
			}
			return list;
		}

























			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			













































			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//====================IrrigationDwise=======================
			@Autowired
			private IrrigationDwisePartitions irridwisepartitions;

			@GetMapping("/getirrdwise")
			List<IrrigationDwise> getirrdwise(String wbdcode, String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				List<IrrigationDwise> list1 = irridwisepartitions.irridwise(cropyear, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}

			
			@GetMapping("/getNormarlAreaReport")
			List<NormalAreasMwiseMao> getList(String dcode, String mcode, String cropyear) {

				String[] season = cropyear.split("@");
				String seasonType = season[0];
				Integer seasonYear = Integer.parseInt(season[1]);
				System.out.println("seasonType=>" + seasonType);
				System.out.println("seasonYear=>" + seasonYear);

				Integer ddcode = Integer.parseInt(dcode);
				Integer mmcode = Integer.parseInt(mcode);

				System.out.println("dcode=>" + dcode);
				System.out.println("mcode=>" + mcode);
				System.out.println("cropyear=>" + cropyear);

				List<NormalAreasMwiseMao> listt = normalAreasMwiseMaoRepo.getListt(ddcode, mmcode, seasonType, seasonYear);
				System.out.println("list size=>" + listt.size());
				System.out.println("list =>" + listt.toString());
				return listt;
			}

		// <-------------FARMER BOOKING DETAILS----------->
			
			@Autowired
			private FarmerBookingDetailsPartitions partition;

			
			
			@GetMapping("/getFbDetails")
			List<FarmerBookingDetails> getFarmers(String dcode, String mcode, String cropyear, HttpSession session,
					String userid) {
				String[] season = cropyear.split("@");
				String year = season[1];
				String wbdcode = (String) session.getAttribute("wbdcode");

				List<FarmerBookingDetails> Farmerlist = partition.farmerBookingDetails(dcode, mcode, year, wbdcode);

				return Farmerlist;
			}

			@GetMapping("/getFbDetails1")
			List<FarmerBookingDetails> getFbDetails(String dcode, String mcode, String cropyear, String wbdcode,
					String userid) {
				System.out.println("dcode===================>" + dcode);
				System.out.println("mcode===================>" + mcode);
				System.out.println("cropyear===================>" + cropyear);
				System.out.println("wbdcode===================>" + wbdcode);

				List<FarmerBookingDetails> list = partition.farmerBookingDetails(dcode, mcode, cropyear, wbdcode);
				System.out.println("details===================>" + list.size());

				return list;
			}

			// <------------------ROFR BOOKED EXTENT----------------->//
			@Autowired
			private RofrBookedExtentPartitions rofrBookedExtentPartitions;

			@GetMapping("/getRofr")
			List<RofrBookedExtent> getRbExt(String wbcode, String wbmcode, String cropyear, HttpSession session,
					String userid) {
				String[] season = cropyear.split("@");
				String year = season[1];
				String wbdcode = (String) session.getAttribute("wbdcode");

				List<RofrBookedExtent> rofrList = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, year);

				return rofrList;
			}

			@GetMapping("/getRofr1")
			List<RofrBookedExtent> getRext(String wbdcode, String wbmcode, String cropyear, String userid) {

				List<RofrBookedExtent> rofr = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, cropyear);
				System.out.println("details===================>" + rofr.size());

				return rofr;
			}

			
			


		//================================ SperCheck Allotment Records==========================================//
			@Autowired
			private SuperCheckRecordsAllotedPartition superCheckRecordsAllotedPartition;

			@PostMapping("/supchkalltrecrds")
			List<SuperCheckRecordsAlloted> getSupChkR(@RequestBody RequestModel requestModel) {
				System.out.println("requestModel=>" + requestModel.toString());

				List<SuperCheckRecordsAlloted> spckr = superCheckRecordsAllotedPartition.getSupchkRds(requestModel.getWbdcode(),
						requestModel.getWbmcode(), requestModel.getUserid(), requestModel.getCropyear());
				System.out.println("details===================>" + spckr.size());
				return spckr;
			}

			// ================PhyAckVwise====================//
			@Autowired
			PhyAckVwisePartition phyAckVwisePartition;

			@PostMapping("/phyack")
			List<PhyAckVwise> getPhyAckVwise(@RequestBody RequestModel requestModel) throws SQLException {
				System.out.println("requestModel=>" + requestModel.toString());

				List<PhyAckVwise> phyack = phyAckVwisePartition.getPhyAck(requestModel.getWbdcode(), requestModel.getWbmcode(),
						requestModel.getCropyear(), requestModel.getUserid());
				return phyack;
			}

			// ================ Rep_phy_ack_rbk====================//
			@Autowired
			Rep_phy_ack_rbkPartition rep_phy_ack_rbkPartition;

			@PostMapping("/phyrbk")
			List<Rep_phy_ack_rbk> getPhyAckRBK(@RequestBody RequestModel requestModel) throws SQLException {
				System.out.println("requestModel=>" + requestModel.toString());

				List<Rep_phy_ack_rbk> phyrbk = rep_phy_ack_rbkPartition.getPhyRbk(requestModel.getWbdcode(),
						requestModel.getWbmcode(), requestModel.getCropyear(), requestModel.getUserid());
				System.out.println("details===================>" + phyrbk.size());
				return phyrbk;
			}

		//==========================Rep_DownloadedDetailsIntf===========================//
			@Autowired
			Rep_DownloadedDetailsIntfPartition rep_DownloadedDetailsIntfPartition;

			@PostMapping("/dwnlddet")
			List<Rep_DownloadedDetailsIntf> getDwnloadedDet(@RequestBody RequestModel requestModel, HttpSession httpSession)
					throws SQLException {
				System.out.println("requestModel=>" + requestModel.toString());
				System.out.println(httpSession.getAttribute("wbdname"));

				List<Rep_DownloadedDetailsIntf> dwnlddet = rep_DownloadedDetailsIntfPartition.getDwnLdDet(
						requestModel.getWbdcode(), requestModel.getWbmcode(), requestModel.getCropyear(),
						requestModel.getUserid());
				System.out.println("details===================>" + dwnlddet.size());
				return dwnlddet;
			}

		//=====================RepLandDataDetails=========================//
			@Autowired
			RepLandDataDetailsRepo repLandDataDetailsRepo;

			@PostMapping("/landatam")
			List<RepLandDataDetails> getDwnloadedDet(@RequestBody RequestModel requestModel) throws SQLException {
				System.out.println("requestModel=>" + requestModel.toString());

				List<RepLandDataDetails> landata = repLandDataDetailsRepo.getLandDet(Integer.parseInt(requestModel.getDcode()),
						Integer.parseInt(requestModel.getMcode()));
				System.out.println("details===================>" + landata.size());
				return landata;
			}

			// ----------------------------------
			// NonWebView---------------------------------------//
			@PostMapping("/nonwebv")
			public ResponseEntity<?> getNonWebV(@RequestBody RequestModel requestModel) {
				System.out.println("requestModel=>" + requestModel.toString());

				try {
					String[] season = requestModel.getCropyear().split("@");
					System.out.println("season=========" + season);
					String cseason = season[0];
					System.out.println("seasonType=========" + cseason);
					Integer Year = Integer.parseInt(season[1]);
					System.out.println("seasonYear=========" + Year);

					List<NonWebView> nonwebv = seasonCropBookedExtentRepo.getNonwebView(
							Integer.parseInt(requestModel.getDcode()), Integer.parseInt(requestModel.getMcode()), cseason,
							Year);
					System.out.println("crpins size=>" + nonwebv.size());
					return new ResponseEntity<List<NonWebView>>(nonwebv, HttpStatus.OK);
				} catch (Exception e) {

					System.out.println("getStackTrace =>" + e.getStackTrace());
					return null;
				}
			}



//			===============ExtentBookedVSNormalArea====================
			@Autowired
			ExtentBookedVSNormalAreaAbstractPartitions extbookvsnormalpartitions;

			@GetMapping("/extbooknorm")
			List<ExtentBookedVSNormalAreaAbstract> getextbooknorm(String cropyear) {
				System.out.println("cropyear==============>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<ExtentBookedVSNormalAreaAbstract> list = extbookvsnormalpartitions.extbooknormalarea(cropyear);
					System.out.println("details===================>" + list.size());

					return list;
				}
				return null;
			}



//			======================DistWiseVAAVROeKYCrabi=================
			@Autowired
			private DistWiseVAAVROeKYCrabipartitons distvaapartitions;

			@GetMapping("/getdistvaa")
			List<DistWiseVAAVROeKYCrabi> getdistvaa(String cropyear) {
				System.out.println("cropyear=>" + cropyear);

				int a = (activeSeasonService.listAll().get(0).getCropyear());
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<DistWiseVAAVROeKYCrabi> list1 = distvaapartitions.distvaavro(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

		//  =====================VarietyWiseRBK=======================
			@Autowired
			private VarietyWiseRBKPartitions varietywisepartition;

			@GetMapping("/getvariety")
			List<AuthCropWise> getvariety(String cropyear, HttpSession session, String userid) {
				String[] season = cropyear.split("@");
				String seasonType = season[0];
				String year = season[1];
				Integer seasonYear = Integer.parseInt(season[1]);
				return null;
			}

			@GetMapping("/getvariety1")
			List<VarietyWiseRBK> getvarietywise(String cropyear, String userid, String cropid) {
//				System.out.println("cropyear===================>" + cropyear);
				System.out.println("cropid===================>" + cropid);

				int a = (activeSeasonService.listAll().get(0).getCropyear());
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean crop = method.cropyearseason(cropyear);
				boolean cropcode = method.cropCode(cropid);
				if (crop && cropcode) {
					List<VarietyWiseRBK> varietywiserbk = varietywisepartition.varietywiserbk(cropyear, cropid, a);
					System.out.println("details===================>" + varietywiserbk.size());

					return varietywiserbk;
				}
				return null;
			}

//			==========================DDAPCorrectionSocialAudit==========================
			@Autowired
			private DDAPCorrectionSocialAuditPartition ddapsocialpartition;

			@GetMapping("/getddapsocial")
			List<DDAPCorrectionSocialAudit> getddapsocial(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<DDAPCorrectionSocialAudit> list1 = ddapsocialpartition.ddapsocial(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			===================IrriMethodRBKWise==================
			@Autowired
			private IrriMethodRBKWisePartitions irrimethrbkwisePartitions;

			@GetMapping("/getirrimethod")
			List<IrriMethodRBKWise> getirrimethod(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop = method.cropyearseason(cropyear);
				if (crop) {
					List<IrriMethodRBKWise> list1 = irrimethrbkwisePartitions.irrimethodrbkwise(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			=============================SuperCheckDistWise======================
			@Autowired
			private SuperCheckDistWisePartitions superchckpartitions;

			@GetMapping("/getsuperchckdistwise")
			List<SuperCheckDistWise> getsuperchckdistwise(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop = method.cropyearseason(cropyear);
				if (crop) {
					List<SuperCheckDistWise> list1 = superchckpartitions.supercheckdistwise(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			==========================PhysicalAck========================
			@Autowired
			private PhysicalAckPartitions phyackpartitions;

			@GetMapping("/getphyack")
			List<PhysicalAck> getphyack(String cropyear) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				boolean crop = method.cropyearseason(cropyear);
				if (crop) {
					List<PhysicalAck> list1 = phyackpartitions.phyack(cropyear);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}



//			==================UnlockExtDDAP==================
			@Autowired
			private UnlockExtDDAPPartitions unlockpartitions;

			@GetMapping("/getunlock")
			List<UnlockExtDDAP> getunlochext(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<UnlockExtDDAP> list1 = unlockpartitions.unlockextddap(cropyear);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			===================FirstCrdetVwise=======================
			@Autowired
			private FirstCrdetVwisePartitions firstcrdistvwisepartitions;

			@GetMapping("/getcrdistvwise")
			List<FirstCrdetVwise> getcrdistvwise(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<FirstCrdetVwise> list1 = firstcrdistvwisepartitions.firstcedetvwise(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}



			@Autowired
			private IrrigationMwisePartitions irrimwisepartition;

			@GetMapping("/getirrmwise")
			List<IrrigationMwise> getirrmwise(@RequestParam("wbdcode") String wbdcode, String cropyear) {
				System.out.println();
				System.out.println("cropyear=>" + cropyear);
				System.out.println("wbdcode=>" + wbdcode);
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				List<IrrigationMwise> list1 = irrimwisepartition.irrimwise(cropyear, wbdcode, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}

//			=====================LandOwnershipDwise====================
			@Autowired
			private LandOwnershipDwisePartitions landownerdwisepartitions;

			@GetMapping("/getownerdwise")
			List<LandOwnershipDwise> getownerdwise(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<LandOwnershipDwise> list1 = landownerdwisepartitions.landownerdist(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			===================LandOwnershipVwise=====================
			@Autowired
			private LandOwnershipVwisePartitions landownervwisepartitions;

			@GetMapping("/getownervwise")
			List<LandOwnershipVwise> getownervwise(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<LandOwnershipVwise> list1 = landownervwisepartitions.landownervwise(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			==================DistWisecCropinsuranceClaims========================
			@Autowired
			private DistWisecCropinsuranceClaimsPartitions distwisecropinspartitions;

			@GetMapping("/getcropins")
			List<DistWisecCropinsuranceClaims> getcropins(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				List<DistWisecCropinsuranceClaims> list1 = distwisecropinspartitions.distwisecropinsclaims(cropyear, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}

//			=======================SupercheckAllocation========================
			@GetMapping("/getAllUsertype")
			public List<MasterProjections> getusertype() {
				List<MasterProjections> list = normalAreasMwiseMaoRepo.getusertype();
				return list;
			}

//			=================TopSixCrops===============
			@Autowired
			private TopSixCropsPartitions topsixparttion;

			@GetMapping("/getsixcrops")
			List<TopSixCrops> getsixcrops(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop = method.cropyearseason(cropyear);
				if (crop) {
					List<TopSixCrops> list1 = topsixparttion.topsixcrops(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			=====================PendingVillMap================
			@Autowired
			private PendingVillMapRepository pendingmaprepo;

			@GetMapping("/getvillmap")
			List<Pendingvillmapview> getListt(String wbdcode) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				System.out.println("wbdcode" + wbdcode);

				List<Pendingvillmapview> list = null;
				int dcode = Integer.parseInt(wbdcode);
				if (method.wbdCode(wbdcode))
					list = pendingmaprepo.getListt(dcode);
				return list;
			}

//			======================DistAgriHortiFishDDAP================
			@Autowired
			private DistAgriHortiFishDDAPPartitions distddappartition;

			@GetMapping("/getdhafddap")
			List<DistAgriHortiFishDDAP> getdhafddap(String cropyear) {
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				int a = (activeSeasonService.listAll().get(0).getCropyear());

				List<DistAgriHortiFishDDAP> list1 = distddappartition.disthorddap(cropyear, a);
				System.out.println("details===================>" + list1.size());
				return list1;
			}




//			=========================SuperCheckIntf=====================
			@Autowired
			private SuperCheckIntfPartitions superchkpartition;

			@GetMapping("/getsupercheck")
			List<SuperCheckIntf> getsupercheck(String cropyear, String wbdcode) {
				System.out.println("getredown");
				System.out.println("cropyear" + cropyear);
				System.out.println("wbdcode" + wbdcode);

				String[] season = cropyear.split("@");
				String seasonType = season[0];
				Integer seasonYear = Integer.parseInt(season[1]);

				int a = (activeSeasonService.listAll().get(0).getCropyear());

				List<SuperCheckIntf> list = superchkpartition.superchk(cropyear, wbdcode, a);

				return list;
			}


			

//			====================DDAPCorrectionSuperCHK======================
			@Autowired
			private DDAPCorrectionSuperCHKPartitions ddapsuperchkpartition;

			@GetMapping("/getddapsuperchk")
			List<DDAPCorrectionSuperCHK> getddapsuperchk(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<DDAPCorrectionSuperCHK> list1 = ddapsuperchkpartition.ddapsuperchk(cropyear, a);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			==================CropBookingDetailsPhotoDDAP============================
			@Autowired
			private com.ecrops.partition.CropBookingDetailsPhotoDDAPPartitions cropphotopartition;

			@GetMapping("/getcropphoto")
			List<CropBookingDetailsPhotoDDAP> getcropphoto(String dcode, String mcode, String vcode, String cropyear,
					String cropid, HttpSession session) {
				String[] season = cropyear.split("@");
				String seasonType = season[0];
				String year = season[1];
				Integer seasonYear = Integer.parseInt(season[1]);

				Integer ddcode = Integer.parseInt(dcode);
				Integer mmcode = Integer.parseInt(mcode);
				Integer vvcode = Integer.parseInt(vcode);
				int a = (activeSeasonService.listAll().get(0).getCropyear());
				String wbdcode = (String) session.getAttribute("wbdcode");

				List<CropBookingDetailsPhotoDDAP> photoddap = cropphotopartition.cropphoto(cropyear, dcode, mcode, vcode,
						wbdcode, cropid);

				return photoddap;
			}

			@GetMapping("/getcropphoto1")
			List<CropBookingDetailsPhotoDDAP> getcropphoto1(String dcode, String mcode, String vcode, String cropyear,
					String wbdcode, String cropid) {
				System.out.println("dcode===================>" + dcode);
				System.out.println("mcode===================>" + mcode);
				System.out.println("vcode===================>" + vcode);
				System.out.println("cropyear===================>" + cropyear);
				System.out.println("wbdcode===================>" + wbdcode);
//				System.out.println("request-------------------"+inputvcode);
				System.out.println("cropid======>" + cropid);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				boolean dist = method.districtCode(dcode);
				boolean mand = method.mandalCode(mcode);
				boolean vill = method.villageCode(vcode);
				boolean crpcode = method.cropCode(cropid);
				if (crop1 && dist && mand && vill && crpcode) {
					List<CropBookingDetailsPhotoDDAP> list = cropphotopartition.cropphoto(cropyear, dcode, mcode, vcode,
							wbdcode, cropid);
					System.out.println("details===================>" + list.size());

					return list;
				}
				return null;
			}

//			=============================SuperCHKAlloc=========================
			@Autowired
			private SuperCHKAllocPartitions superalocPartitions;

			@GetMapping("/getsuperallocation")
			List<SuperCHKAlloc> getsuperallocation(String cropyear, String dcode, String userid, String wbdcode) {
				System.out.println("cropyear=>" + cropyear);
				System.out.println("usertype=>" + userid);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				boolean dist = method.districtCode(dcode);
				if (crop1 && dist) {
					List<SuperCHKAlloc> list1 = superalocPartitions.superchkalloc(cropyear, dcode, userid, wbdcode);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			==============================IrriSrcDetVwise========================
			@Autowired
			private IrriSrcDetVwisePartitoins irrisrcdetppartition;

			@GetMapping("/getirrsrcdet")
			List<IrriSrcDetVwise> getirrsrcdet(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<IrriSrcDetVwise> list1 = irrisrcdetppartition.irrisrcwise(cropyear);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			==============================TypeFarmingDetVwise========================
			@Autowired
			private TypeFarmingDetVwisePartitions typepartition;

			@GetMapping("/gettypefarmdet")
			List<TypeFarmingDetVwise> gettypefarmdet(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<TypeFarmingDetVwise> list1 = typepartition.typefarming(cropyear);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			=====================ROFRAbstract================
			@Autowired
			private com.ecrops.projection.ROFRExtentAbstractPartitions rofrabstractpartition;

			@GetMapping("/getrofrabstract")
			List<ROFRExtentAbstract> getrofrabstract(String cropyear) {
				System.out.println("cropyear=>" + cropyear);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();
				int a = (activeSeasonService.listAll().get(0).getCropyear());

				boolean crop1 = method.cropyearseason(cropyear);
				if (crop1) {
					List<ROFRExtentAbstract> list1 = rofrabstractpartition.rofrextabstract(cropyear);
					System.out.println("details===================>" + list1.size());
					return list1;
				}
				return null;
			}

//			===========================AadhaarCN==========================
			@Autowired
			com.ecrops.partition.AadhaarCNPartitions aadhaarpartition;

			@GetMapping("/getaadha")
			List<AadhaarCN> getaadha(String dcode) {
				System.out.println("dcode=>" + dcode);
				RegularExpressionclassMethod method = new RegularExpressionclassMethod();

				List<AadhaarCN> list1 = aadhaarpartition.aadhaar(dcode);
				System.out.println("details===================>" + list1.size());
				return list1;
			}
	
			

}
