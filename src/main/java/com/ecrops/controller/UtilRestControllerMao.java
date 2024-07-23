package com.ecrops.controller;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.entity.AllocatedSurveyNo;
import com.ecrops.entity.AllocatedSurveyNoMapping;
import com.ecrops.entity.AuthMAOvaaVroEkyc;
import com.ecrops.entity.BlockedEfishExtent;
import com.ecrops.entity.CropBookingDetailsMaoIntf;
import com.ecrops.entity.CropInsAbstractt;
import com.ecrops.entity.CropInsGrievance;
import com.ecrops.entity.CropwiseExtBookedRBKwise;
import com.ecrops.entity.DataSourceWiseBookingReport;
import com.ecrops.entity.DeviceRegDetails;
import com.ecrops.entity.EfishDetailsRC;
import com.ecrops.entity.EmployeeList;
import com.ecrops.entity.FarmerBookingDetails;
import com.ecrops.entity.FarmerDetails;
import com.ecrops.entity.MaoAuthVaaVroekyc;
import com.ecrops.entity.MaoSocialAuditcorrection;
import com.ecrops.entity.NonWebView;
import com.ecrops.entity.NormalAreasMwiseMao;
import com.ecrops.entity.ObjUnobj;
import com.ecrops.entity.PhyAckVwise;
import com.ecrops.entity.RbkSurveyNoMapping;
import com.ecrops.entity.RbkSurveyNoMappingDrpdwn;
import com.ecrops.entity.RepLandDataDetails;
import com.ecrops.entity.RepPernnialMand;
import com.ecrops.entity.RepVaaDetails;
import com.ecrops.entity.Rep_DownloadedDetailsIntf;
import com.ecrops.entity.Rep_phy_ack_rbk;
import com.ecrops.entity.RofrBookedExtent;
import com.ecrops.entity.RofrBookedExtentPartitions;
import com.ecrops.entity.SeasonCropBookedExtent;
import com.ecrops.entity.SpuperChkAppr;
import com.ecrops.entity.SuperCheckRecordsAlloted;
import com.ecrops.entity.SuperChkReport;
import com.ecrops.entity.SuperChk_rejReport;
import com.ecrops.entity.SupercheckVill;
import com.ecrops.entity.Superchekupdstatus;
import com.ecrops.model.RequestModel;
import com.ecrops.partition.AllocatedSurveyNoMappingPartition;
import com.ecrops.partition.AllocatedSurveyNoPartition;
import com.ecrops.partition.AuthMAOvaaVroEkycPartition;
import com.ecrops.partition.BlockedEfishExtentPartition;
import com.ecrops.partition.CropBookingDetailsMaoIntfPartition;
import com.ecrops.partition.CropInsAbstracttPartition;
import com.ecrops.partition.CropInsGrievancePartition;
import com.ecrops.partition.CropwiseExtBookedRBKwisePartition;
import com.ecrops.partition.DataSourceWiseBookingReportPartitions;
import com.ecrops.partition.DeviceRegDetailsPartition;
import com.ecrops.partition.EfishDetailsRCPartition;
import com.ecrops.partition.EmployeeListPartition;
import com.ecrops.partition.FarmerDetailsPartition;
import com.ecrops.partition.MaoSocialAuditcorrectionPartition;
import com.ecrops.partition.NonWebViewPartition;
import com.ecrops.partition.NormalAreasMwiseMaoPartition;
import com.ecrops.partition.RbkSurveyNoMappingDrpdwnPartitions;
import com.ecrops.partition.RepLandDataDetailsPartition;
import com.ecrops.partition.RepPernnialMandPartition;
import com.ecrops.partition.RepVaaDetailsPartition;
import com.ecrops.partition.Rep_DownloadedDetailsIntfPartition;
import com.ecrops.partition.Rep_phy_ack_rbkPartition;
import com.ecrops.partition.SeasonCropBookedExtentPartition;
import com.ecrops.partition.SpuperChkApprPartition;
import com.ecrops.partition.SuperCheckRecordsAllotedPartition;
import com.ecrops.partition.SuperChkReportPartition;
import com.ecrops.partition.SuperChk_rejReportPartition;
import com.ecrops.partition.SupercheckVillPartition;
import com.ecrops.partition.SuperchekupdstatusPartition;
import com.ecrops.partition.ObjUnobjPartition;
import com.ecrops.partition.PhyAckVwisePartition;
import com.ecrops.projection.MasterProjections;
import com.ecrops.repo.FarmerBookingDetailsPartitions;
import com.ecrops.repo.MaoAuthVaaVroekycPartition;
import com.ecrops.repo.MaoAuthVaaVroekycRepo;
import com.ecrops.repo.MaoSocialAuditcorrectionRepo;
import com.ecrops.repo.NormalAreasMwiseMaoRepo;
import com.ecrops.repo.RbkSurveyNoMappingPartition;
import com.ecrops.repo.SeasonCropBookedExtentRepo;

import com.ecrops.repo.SupercheckVillRepo;
import com.ecrops.util.MasterFunctions;
@RestController
@RequestMapping("/utilMao")
public class UtilRestControllerMao {
	
	@Autowired private SeasonCropBookedExtentRepo seasonCropBookedExtentRepo;
	@Autowired private NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;
	@Autowired private FarmerBookingDetailsPartitions farmerBookingDetailsPartitions;
	@Autowired private RofrBookedExtentPartitions rofrBookedExtentPartitions;
	@Autowired private MaoAuthVaaVroekycRepo maoAuthVaaVroekycRepo;
	@Autowired private MaoAuthVaaVroekycPartition maoAuthVaaVroekycPartition;
	
	@Autowired private RbkSurveyNoMappingPartition rbkSurveyNoMappingPartition;
//
//	@GetMapping("/getAllSeason")
//	public List<MasterProjections> getAllSeasonn() {
//		List<MasterProjections> list = normalAreasMwiseMaoRepo.getAllSeason();
//		return list;
//	}
//
//	@GetMapping("/getAllCrop")
//	public List<MasterProjections> getAllCrop() {
//		List<MasterProjections> list = maoAuthVaaVroekycRepo.getAllCrops();
//		return list;
//	}

//	@GetMapping("/getAllMandals")
//	public List<MasterProjections> getMandals(Integer dcode) {
//		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllMandals(dcode);
//		return list;
//	}

//**************************************************//Villages//*************************************************
//	@GetMapping("/getAllVillages")
//	public List<MasterProjections> getVillages(Integer dcode, Integer mcode) {
//		System.out.println("dcode=>" + dcode);
//		System.out.println("mcode=>" + mcode);
//		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllVillages(dcode, mcode);
//		return list;
//	}
//===========================CropGroups=================================//

	@GetMapping("/getCropGroup")
	public List<MasterProjections> getCropGroup() {
		System.out.println("=======getCropGroup==========");
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllCropGrp();
		System.out.println("=======list==========" + list.size());
		return list;
	}

	// ===============getAllCrpGrp=====================//
	@GetMapping("/getCropGroupid")
	public List<MasterProjections> getCropGoupidd(String grpcode) {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllCrpGrpid(Integer.parseInt(grpcode));
		System.out.println("=======list==========" + list.size());
		return list;
	}

	// ==================Normal Area Report====================//
	@Autowired NormalAreasMwiseMaoPartition normalAreasMwiseMaoPartition;
	@PostMapping("/getNormarlAreaReport")
	List<NormalAreasMwiseMao> getList(@RequestBody RequestModel requestModel) {

		String[] season = requestModel.getCropyear().split("@");System.out.println("cropyear=="+requestModel.getCropyear());
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean val = regexpressionmethod.districtCode(requestModel.getDcode().toString());
		System.out.println("dcode==="+val);
		boolean val1 = regexpressionmethod.mandalCode(requestModel.getMcode().toString());
		System.out.println("mcode==="+val1);
		
		if (val && val1) {
			List<NormalAreasMwiseMao> listt = normalAreasMwiseMaoPartition.getListt(
					requestModel.getDcode(), 
					requestModel.getCropyear(), 
					requestModel.getMcode()
					);

			 System.out.println("list size=>" + listt.size());
			 System.out.println("list =>" + listt.toString());
			return listt;
		}

		return null;
	}

// <-------------FARMER BOOKING DETAILS----------->
	

	@GetMapping("/getFbDetails1")
	List<FarmerBookingDetails> getFbDetails(String dcode, String mcode, String cropyear, String wbdcode,
			String userid) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean val = regexpressionmethod.districtCode(dcode.toString());
		boolean val1 = regexpressionmethod.mandalCode(mcode.toString());
		boolean year = regexpressionmethod.year(seasonYear.toString());
		
		System.out.println(dcode+"val----------"+val);
		System.out.println(dcode+"val1----------"+val1);
		System.out.println(seasonYear+"year----------"+year);

		if (val && val1 && year) {

			List<FarmerBookingDetails> list = farmerBookingDetailsPartitions.farmerBookingDetails(dcode, mcode, cropyear, wbdcode);
			System.out.println("details===================>" + list.size());

			return list;
		}
		return null;
	}
	// <------------------ROFR BOOKED EXTENT----------------->//

	@GetMapping("/getRofr")
	List<RofrBookedExtent> getRbExt(String wbcode, String wbmcode, String cropyear, HttpSession session,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);
		String wbdcode = (String) session.getAttribute("wbdcode");

		List<RofrBookedExtent> rofrList = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, year);

		return rofrList;
	}

	@GetMapping("/getRofr1")
	List<RofrBookedExtent> getRext(String wbdcode, String wbmcode, String cropyear, String userid) {
//		System.out.println("dcode===================>" + dcode);
//		System.out.println("mcode===================>" + mcode);
//		System.out.println("cropyear===================>" + cropyear);
//		System.out.println("wbdcode===================>" + wbdcode);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		System.out.println(wbdcode+"-------------"+wbmcode);

		boolean val = regexpressionmethod.districtCode(wbdcode.toString());
		boolean val1 = regexpressionmethod.mandalCode(wbmcode.toString());
		boolean year1 = regexpressionmethod.year(year.toString());
		boolean season1 = regexpressionmethod.season(seasonType.toString());
		System.out.println("year------------->" + year);

		System.out.println("val-------------->" + val);
		System.out.println("val1-------------->" + val1);
		System.out.println("year1-------------->" + year1);
		System.out.println("season1-------------->" + season1);

		if (val  && year1 && season1) {

			List<RofrBookedExtent> rofr = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, cropyear);
			System.out.println("details===================>" + rofr.size());

			return rofr;
		}
		return null;
	}

	// <------------------MaoAuthVaaVroEkyc----------------->//
	@GetMapping("/getekyc1")
	List<MaoAuthVaaVroekyc> getEkycVaaVro(String wbdcode, String cropyear, String mcode, String userid, String cropid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		try {
			boolean val = regexpressionmethod.districtCode(wbdcode.toString());System.out.println("val===>"+val);
			boolean val1 = regexpressionmethod.mandalCode(mcode.toString());System.out.println("val1===>"+val1);
			boolean year1 = regexpressionmethod.year(year.toString());System.out.println("year1===>"+year1);
			

			if (val && year1 && val1) {
				List<MaoAuthVaaVroekyc> ekyc = maoAuthVaaVroekycPartition.vaaVroEkyc(wbdcode, mcode, cropyear, cropid);
				System.out.println("details===================>" + ekyc.size());
				return ekyc;
			}
			return null;
		} catch (Exception e) {
			System.out.println("Exception===>"+e);
			e.printStackTrace();
		}
		return null;
	}
	// <------------------MaoRbkSurveyNoMapping----------------->//

	@GetMapping("/rbk")
	List<RbkSurveyNoMapping> getSurveyNo(String wbdcode, String cropyear, HttpSession session, String mcode,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];

		// Integer ddcode = Integer.parseInt(dcode);
		// Integer mmcode = Integer.parseInt(mcode);
		// String wbdcode = (String) session.getAttribute("wbdcode");

		// List<MaoAuthVaaVroekyc> ekycList =
		// maoAuthVaaVroekycPartition.vaaVroEkyc(wbdcode, year,mcode);

		return null;
	}

	@GetMapping("/rbk1")
	List<RbkSurveyNoMapping> getRbkSno(String wbdcode, String cropyear, String mcode) {

		System.out.println("wbdcode===================>" + wbdcode);
		System.out.println("mcode===================>" + mcode);
		System.out.println("cropyear===================>" + cropyear);

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val = regexpressionmethod.districtCode(wbdcode.toString());
		System.out.println("val====>" + val);
		boolean val1 = regexpressionmethod.mandalCode(mcode.toString());
		System.out.println("val1====>" + val1);
		boolean year1 = regexpressionmethod.year(year.toString());
		System.out.println("year1====>" + year1);

		if (val && val1 && year1) {

			List<RbkSurveyNoMapping> sno = rbkSurveyNoMappingPartition.rbkSno(wbdcode, mcode, cropyear);
			System.out.println("details===================>" + sno.size());

			return sno;
		}
		return null;
	}

	// <------------------AllocatedSurveyNoMapping----------------->//
	@Autowired
	private AllocatedSurveyNoMappingPartition allocatedSurveyNoMappingPartition;
	@Autowired
	private RbkSurveyNoMappingDrpdwnPartitions rbkSurveyNoMappingDrpdwnPartitions;

	@GetMapping("/drpd")
	List<RbkSurveyNoMappingDrpdwn> getRbkdrpd(String mcode, String cropyear, String wbdcode, String rbkcode,
			HttpSession session, HttpServletRequest request) {
		System.out.println("mcode==========>" + mcode);
		System.out.println("cropyear==========>" + cropyear);
		System.out.println("wbdcode==========>" + wbdcode);
		System.out.println("rbkcode==========>" + rbkcode);

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		cropyear = request.getParameter("cropyear");
		// mcode= (String) session.getAttribute("mcode");
		// wbdcode= (String) session.getAttribute("wbdcode");
		// rbkcode= (String) session.getAttribute("rbkcode");

		List<RbkSurveyNoMappingDrpdwn> aldw = rbkSurveyNoMappingDrpdwnPartitions.getRBK(mcode, cropyear, wbdcode,
				rbkcode);
		System.out.println("details------>" + aldw.size());

		return aldw;
	}
//----------------Allocated SurveryNo Mapping--------------------//

	@GetMapping("/asnom1")
	List<AllocatedSurveyNoMapping> getAllocSnoMapping(String wbdcode, String cropyear, String mcode, String userid,
			String rbkid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val = regexpressionmethod.districtCode(wbdcode.toString());
		boolean valm = regexpressionmethod.mandalCode(mcode.toString());
		boolean year1 = regexpressionmethod.year(year.toString());
		boolean season1 = regexpressionmethod.season(seasonType.toString());

		if (val && valm && year1 && season1) {

			List<AllocatedSurveyNoMapping> asno = allocatedSurveyNoMappingPartition.allocatedSnoDetails(wbdcode,
					cropyear, mcode, userid, rbkid);
			System.out.println("details===================>" + asno.size());
			return asno;
		}
		return null;
	}

	// *******// DataSourceWiseBookingExtent//*******//
	@Autowired
	DataSourceWiseBookingReportPartitions dataSourceWiseBookingReportPartitions;

	@GetMapping("/dtsrcb1")
	List<DataSourceWiseBookingReport> dataSrcDet(String wbdcode, String cropyear, String wbmcode, String userid) {

		//System.out.println("wbdcode===================>" + wbdcode);
		//System.out.println("cropyear===================>" + cropyear);
		//System.out.println("userid===================>" + userid);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val = regexpressionmethod.districtCode(wbdcode.toString());
		boolean wbmcd = regexpressionmethod.mandalCode(wbmcode.toString());
		boolean year1 = regexpressionmethod.year(year.toString());
		boolean season1 = regexpressionmethod.season(seasonType.toString());

		if (val && year1 && season1 && wbmcd) {

			List<DataSourceWiseBookingReport> dsb = dataSourceWiseBookingReportPartitions.dataSrcDet(wbdcode, cropyear,
					wbmcode, userid);
			return dsb;
		}
		return null;
	}

// **********************efishDetails***************************
	@Autowired
	private EfishDetailsRCPartition efishDetailsRCPartition;

	@PostMapping("/efishDetails")
	public List<EfishDetailsRC> efishDetails(@RequestBody RequestModel  requestModel) {

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val = regexpressionmethod.districtCode(requestModel.getDcode().toString());
		boolean wbmcd = regexpressionmethod.mandalCode(requestModel.getMcode().toString());

		if (val && wbmcd) {
			if (requestModel.getData_src().equalsIgnoreCase("R")) {
				List<EfishDetailsRC> efishDetailsR = efishDetailsRCPartition.efishDetailsR(
						requestModel.getDcode(), 
						requestModel.getMcode(),
						requestModel.getCropyear(),
				        requestModel.getData_src());
				return efishDetailsR;
			}
			if (requestModel.getData_src().equalsIgnoreCase("C")) {
				List<EfishDetailsRC> efishDetailsC = efishDetailsRCPartition.efishDetailsC(
						
						requestModel.getDcode(), 
						requestModel.getMcode(),
						requestModel.getCropyear(),
				        requestModel.getData_src());
				return efishDetailsC;
			}
			return null;
		}
		return null;
	}

	// **************** Farmer Details****************
	@Autowired 
	FarmerDetailsPartition farmerDetailsPartition;
	@PostMapping("/farmerdet")
	public List<FarmerDetails> farmerdet(@RequestBody RequestModel requestModel) {
		String[] season = requestModel.getCropyear().split("@"); System.out.println("getCropyear=="+requestModel.getCropyear());
		String seasonType = season[0];
		String year = season[1];

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcd = regexpressionmethod.districtCode(requestModel.getWbdcode().toString());
		boolean wbmcd = regexpressionmethod.mandalCode(requestModel.getWbmcode().toString());
		System.out.println("wbdcd===>"+wbdcd);

		if (wbdcd && wbmcd ) {

			List<FarmerDetails> farmerDetails = farmerDetailsPartition.farmerDetails(
					requestModel.getWbdcode(),
					requestModel.getCropyear(),
					requestModel.getWbmcode(),
					requestModel.getDate());
			return farmerDetails;
		}
		return null;
	}

	// **************************************************************************//
	@Autowired
	SeasonCropBookedExtentPartition seasonCropBookedExtentPartition;

	@GetMapping("/allcrp1")
	List<SeasonCropBookedExtent> getAllCrop(String wbmcode, String cropyear, String userid, String wbdcode) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean wbdcd = regexpressionmethod.districtCode(wbdcode.toString());
		boolean wbmcd = regexpressionmethod.mandalCode(wbmcode.toString());
		boolean year1 = regexpressionmethod.year(year.toString());
		if (year1 && wbdcd && wbmcd) {

			List<SeasonCropBookedExtent> scp = seasonCropBookedExtentPartition.getAllCrops(wbmcode, cropyear, userid,
					wbdcode);
			System.out.println("details===================>" + scp.get(0));
			return scp;
		}
		return null;
	}
//=============================BLOCKED e-fish EXTENT============================//
	@Autowired
	BlockedEfishExtentPartition blockedEfishExtentPartition;
	
	@PostMapping("/blockedext")
	public List<BlockedEfishExtent> blockedext(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		
		System.out.println("wbvcode===="+requestModel.getWbvcode().toString());
		boolean dcode1 = regexpressionmethod.districtCode(requestModel.getDcode().toString());
		boolean mcode1 = regexpressionmethod.mandalCode(requestModel.getMcode().toString());
		boolean wbvcode1 = regexpressionmethod.villageCode(requestModel.getWbvcode().toString());

		if (dcode1 && mcode1 && wbvcode1) {

			List<BlockedEfishExtent> blockedExtent = blockedEfishExtentPartition.findByExtent(
					requestModel.getDcode(), requestModel.getMcode(),
					requestModel.getWbvcode());

			return blockedExtent ;
		}
		return null;
	}
//===========OBJ-UNOBJ//
@Autowired  ObjUnobjPartition objUnobjPartition;
	@GetMapping("/objunobj1")
	public List<ObjUnobj> getObj() {
		List<ObjUnobj> Objunobj = objUnobjPartition.getObjObj();

		return Objunobj ;
	}

///======================EMP LIST=======================///
	@Autowired
	EmployeeListPartition employeeListPartition;

	@PostMapping("/emplist")
	public List<EmployeeList> getEmp1(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		try {
			RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

			boolean val = regexpressionmethod.districtCode(requestModel.getDcode().toString());
			boolean val2 = regexpressionmethod.mandalCode(requestModel.getMcode().toString());

			System.out.println("val-------------->" + val);
			System.out.println("val2-------------->" + val2);

			if (val && val2) {

				List<EmployeeList> scp = employeeListPartition.getEmpList(requestModel.getDcode(),
						requestModel.getMcode(), requestModel.getCropyear());
				System.out.println("details===================>" + scp.size());
				return scp;
			}
		} catch (NumberFormatException e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
		}
		return null;

	}

//***************************************************************//	
	@Autowired
	RepVaaDetailsPartition repVaaDetailsPartition;

	@PostMapping("/vaadet")

	public List<RepVaaDetails> getVAADet(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpressionmethod.mandalCode(requestModel.getMcode().toString());
		System.out.println("val-------------->" + val1);

		if (val1) {
			List<RepVaaDetails> vaadet = repVaaDetailsPartition.getVaaDet(requestModel.getMcode(),
					requestModel.getCropyear());
			System.out.println("details===================>" + vaadet.size());
			return vaadet;
		}
		return null;
	}

//--------------------------------- Device Reg Details-----------------------------------------------//
	@Autowired
	DeviceRegDetailsPartition deviceRegDetailsPartition;

	@PostMapping("/devregdet")
	public List<DeviceRegDetails> getDevRegDetails(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());

		List<DeviceRegDetails> devregdet;
		try {
			String[] season = requestModel.getCropyear().split("@");
			String seasonType = season[0];
			Integer seasonYear = Integer.parseInt(season[1]);

			RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
			boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
			boolean saesonid = regexpressionmethod.year(seasonYear.toString());
			if (mcode && saesonid) {
				devregdet = deviceRegDetailsPartition.getDevRegDet(requestModel.getMcode(), requestModel.getCropyear());
				System.out.println("devregdet size=>" + devregdet.size());
				return devregdet;
			}
		} catch (Exception e) {
			System.out.println("getStackTrace =>" + e.getStackTrace());
		}
		return null;

	}

//---------------------------------- Crop Insurence Grievance---------------------------------------------//
	@Autowired CropInsGrievancePartition cropInsGrievancePartition;
	@PostMapping("/crpinsgri")
	public ResponseEntity<?> getCropInsGri(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());

		String[] season = requestModel.getCropyear().split("@");System.out.println("cropyear===>"+requestModel.getCropyear());
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());System.out.println("dcode===>"+dcode);
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());System.out.println("mcode===>"+mcode);
		boolean saesonid = regexpressionmethod.year(Year.toString());System.out.println("Year===>"+Year);
		if (mcode && saesonid && dcode) {

			try {

				List<CropInsGrievance> crpins = cropInsGrievancePartition.getCropIns(
						requestModel.getDcode(),
						requestModel.getMcode(), 
						requestModel.getCropyear());
				System.out.println("crpins size=>" + crpins.size());
				return new ResponseEntity<List<CropInsGrievance>>(crpins, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("getStackTrace =>" + e.getStackTrace());
				return null;
			}
		}
		return null;
	}

//================================ SperCheck Allotment Records==========================================//
	@Autowired
	private SuperCheckRecordsAllotedPartition superCheckRecordsAllotedPartition;

	@PostMapping("/supchkalltrecrds")
	List<SuperCheckRecordsAlloted> getSupChkR(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {
			List<SuperCheckRecordsAlloted> spckr = superCheckRecordsAllotedPartition.getSupchkRds(
					requestModel.getWbdcode(), requestModel.getWbmcode(), requestModel.getUserid(),
					requestModel.getCropyear());
			System.out.println("details===================>" + spckr.size());
			return spckr;
		}
		return null;
	}

//================SpuperCheck APPR Report==================//
	@Autowired
	SpuperChkApprPartition spuperChkApprPartition;

	@PostMapping("/supchapprintf")
	List<SpuperChkAppr> getSupChkAppr(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {
			List<SpuperChkAppr> spcapr = spuperChkApprPartition.getSupchkAppr(requestModel.getWbdcode(),
					requestModel.getWbmcode(), requestModel.getUserid(), requestModel.getCropyear());
			System.out.println("details===================>" + spcapr.size());
			return spcapr;
		}
		return null;
	}
//=========================SuperCheck Report===============================//

	@Autowired
	SuperChkReportPartition superChkReportPartition;

	@PostMapping("/supcheckReport")
	List<SuperChkReport> getSupChk(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {
			List<SuperChkReport> supkr = superChkReportPartition.getSupchkRep(requestModel.getWbdcode(),
					requestModel.getWbmcode(), requestModel.getUserid(), requestModel.getCropyear());
			System.out.println("details===================>" + supkr.size());
			return supkr;
		}
		return null;
	}
//========================== SuperCheck Rejected===============================//

	@Autowired
	SuperChk_rejReportPartition superChk_rejReportPartition;

//@Autowired MasterFunctions masterFunctions;

	@PostMapping("/supchkrej")
	List<SuperChk_rejReport> getSupChkRejrReport(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());

		// masterFunctions.getCropImageMao(null, null, null, null, null, null)
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {

			List<SuperChk_rejReport> supkrej = superChk_rejReportPartition.getSupchkRej(requestModel.getWbdcode(),
					requestModel.getWbmcode(),
					// requestModel.getUserid(),
					requestModel.getCropyear());
			System.out.println("details===================>" + supkrej.size());
			return supkrej;
		}
		return null;
	}
	// ================CropBookingDetailsMaoIntf====================//

	@Autowired
	CropBookingDetailsMaoIntfPartition cropBookingDetailsMaoIntfPartition;

	@Autowired
	MasterFunctions masterFunctions;

	@PostMapping("/crpmao")
	List<CropBookingDetailsMaoIntf> getCropdetMao(@RequestBody RequestModel requestModel) throws SQLException {
		System.out.println("requestModel=>" + requestModel.toString());

		String[] season = requestModel.getCropyear().split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());System.out.println("dcode--->" + dcode);
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());System.out.println("mcode--->" + mcode);
		boolean vcode = regexpressionmethod.villageCode(requestModel.getVcode());System.out.println("vcode--->" + vcode);
		boolean year = regexpressionmethod.year(seasonYear.toString());System.out.println("year--->" + year);
		if (dcode && vcode && mcode && year) {

			List<CropBookingDetailsMaoIntf> crdbmao = cropBookingDetailsMaoIntfPartition.getCropDetailsMao(
					requestModel.getWbdcode(), requestModel.getDcode(), requestModel.getWbmcode(),
					requestModel.getVcode(), requestModel.getCropyear(), requestModel.getCrop());
			System.out.println("details===================>" + crdbmao.size());
			return crdbmao;
		}
		return null;
	}

	// ================PhyAckVwise====================//
	@Autowired
	PhyAckVwisePartition phyAckVwisePartition;

	@PostMapping("/phyack")
	List<PhyAckVwise> getPhyAckVwise(@RequestBody RequestModel requestModel) throws SQLException {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {

			List<PhyAckVwise> phyack = phyAckVwisePartition.getPhyAck(requestModel.getWbdcode(),
					requestModel.getWbmcode(), requestModel.getCropyear(), requestModel.getUserid());
			return phyack;
		}
		return null;
	}

	// ================ Rep_phy_ack_rbk====================//
	@Autowired
	Rep_phy_ack_rbkPartition rep_phy_ack_rbkPartition;

	@PostMapping("/phyrbk")
	List<Rep_phy_ack_rbk> getPhyAckRBK(@RequestBody RequestModel requestModel) throws SQLException {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbmcode && saesonid) {
			List<Rep_phy_ack_rbk> phyrbk = rep_phy_ack_rbkPartition.getPhyRbk(requestModel.getWbdcode(),
					requestModel.getWbmcode(), requestModel.getCropyear(), requestModel.getUserid());
			System.out.println("details===================>" + phyrbk.size());
			return phyrbk;
		}
		return null;
	}

//==========================Rep_DownloadedDetailsIntf===========================//
	@Autowired
	Rep_DownloadedDetailsIntfPartition rep_DownloadedDetailsIntfPartition;

	@PostMapping("/dwnlddet")
	List<Rep_DownloadedDetailsIntf> getDwnloadedDet(@RequestBody RequestModel requestModel, HttpSession httpSession)
			throws SQLException {
		System.out.println("requestModel=>" + requestModel.toString());

		String[] season = requestModel.getCropyear().split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(seasonYear.toString());
		System.out.println(wbdcode);
		System.out.println(wbmcode);
		System.out.println(saesonid);
		if (wbdcode && wbmcode && saesonid) {

			List<Rep_DownloadedDetailsIntf> dwnlddet = rep_DownloadedDetailsIntfPartition.getDwnLdDet(
					requestModel.getWbdcode(), requestModel.getWbmcode(), requestModel.getCropyear(),
					requestModel.getUserid());
			System.out.println("details===================>" + dwnlddet.size());
			return dwnlddet;
		}
		return null;
	}

//=====================RepLandDataDetails=========================//
	@Autowired
	RepLandDataDetailsPartition repLandDataDetailsPartition;

	@PostMapping("/landatam")
	List<RepLandDataDetails> getDwnloadedDet1(@RequestBody RequestModel requestModel,HttpSession httpSession) throws SQLException {
		System.out.println("requestModel=>" + requestModel.toString());
		
//		String[] season = requestModel.getCropyear().split("@");
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);
		
		String activeYear= httpSession.getAttribute("ACTIVEYEAR").toString();
		
		System.out.println(activeYear);
		List<RepLandDataDetails> landata = null;

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
		boolean year = regexpressionmethod.year(activeYear);
		if (dcode && mcode && year) {

			landata = repLandDataDetailsPartition.getLandDet(requestModel.getDcode(), 
					requestModel.getMcode(),
					activeYear);

			System.out.println("details===================>" + landata.size());
			return landata;
		}

		return null;
	}


	// ----------------------------------// NonWebView---------------------------------------//
	@Autowired NonWebViewPartition nonWebViewPartition;
	@PostMapping("/nonwebv")
	public List<NonWebView> getNonWebV(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
		boolean year = regexpressionmethod.year(Year.toString());
		if (dcode && mcode && year) {

			try {

				List<NonWebView> nonwebv = nonWebViewPartition.getNonwebView(
						requestModel.getDcode(), 
						requestModel.getMcode(), 
						requestModel.getCropyear());
				System.out.println("crpins size=>" + nonwebv.size());
				return nonwebv;
			} catch (Exception e) {

				System.out.println("Exception =>" + e);
				return null;
			}

		}
		return null;
	}

	// =====================CropwiseExtBookedRBKwise=======================//
	@Autowired
	CropwiseExtBookedRBKwisePartition cropwiseExtBookedRBKwisePartition;

	@PostMapping("/rbkext")
	public ResponseEntity<?> getRbkWise(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
		boolean year = regexpressionmethod.year(seasonYear.toString());
		boolean crop = regexpressionmethod.cropCode(requestModel.getCrop());
		if (dcode && mcode && year && crop) {

			try {

				List<CropwiseExtBookedRBKwise> rbkext = cropwiseExtBookedRBKwisePartition.getBkExtRbk(
						requestModel.getDcode(), requestModel.getMcode(), requestModel.getCrop(),
						requestModel.getCropyear());
				System.out.println("crpins size=>" + rbkext.size());
				return new ResponseEntity<List<CropwiseExtBookedRBKwise>>(rbkext, HttpStatus.OK);
			} catch (Exception e) {

				System.out.println("getStackTrace =>" + e.getStackTrace());
				return null;
			}
		}
		return null;
	}

	// =====================AuthMAOvaaVroEkyc=======================//
	@Autowired
	AuthMAOvaaVroEkycPartition authMAOvaaVroEkycPartition;

	@PostMapping("/authMaoVaaVroEkyc")
	public ResponseEntity<?> getAuthEkyc(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		try {

			RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
			boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
			boolean saesonid = regexpressionmethod.year(Year.toString());
			if (mcode && saesonid) {

				List<AuthMAOvaaVroEkyc> maoauth = authMAOvaaVroEkycPartition
						.getAuthMaoVaaVroEkyc(requestModel.getMcode(), requestModel.getCropyear());
				System.out.println("crpins size=>" + maoauth.size());
				return new ResponseEntity<List<AuthMAOvaaVroEkyc>>(maoauth, HttpStatus.OK);
			}
		} catch (Exception e) {

			System.out.println("Exception =>" + e);

		}
		return null;
	}

	// =====================Superchekupdstatus=======================//
	@Autowired
	SuperchekupdstatusPartition superchekupdstatusPartition;

	@PostMapping("/supchkupdstatus")
	public ResponseEntity<?> getSupChkUpdSts(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(Year.toString());
		if (wbdcode && wbmcode && saesonid) {

			try {

				List<Superchekupdstatus> supupds = superchekupdstatusPartition.getSupChkUpdSts(
						requestModel.getWbdcode(), requestModel.getWbmcode(), requestModel.getCropyear());
				System.out.println("crpins size=>" + supupds.size());
				return new ResponseEntity<List<Superchekupdstatus>>(supupds, HttpStatus.OK);
			} catch (Exception e) {

				System.out.println("getStackTrace =>" + e.getStackTrace());
				return null;
			}
		}
		return null;
	}

//===========================//MaoSocialAuditcorrectionRepo//================//
	@Autowired
	private MaoSocialAuditcorrectionPartition maoSocialAuditcorrectionPartition;

	@PostMapping("/maoSocialAudit")
	List<MaoSocialAuditcorrection> getSocialAuditCoorection(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());

		String[] season = requestModel.getCropyear().split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getDcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getMcode());
		boolean saesonid = regexpressionmethod.year(seasonYear.toString());
		if (wbdcode && wbmcode && saesonid) {

			List<MaoSocialAuditcorrection> socialaudit = maoSocialAuditcorrectionPartition.getSocialAudit(	
					requestModel.getWbdcode(),
					requestModel.getDcode(),
					requestModel.getMcode(),	
					requestModel.getCropyear()
					);

			System.out.println("list size=>" + socialaudit.size());
			System.out.println("list =>" + socialaudit.toString());
			return socialaudit;
		}
		return null;

	}

	// ===========================//Rep_Suprcheck_Vill//================//
	@Autowired
	private SupercheckVillPartition supercheckVillPartition;

	@PostMapping("/supvill")
	List<SupercheckVill> getSupchkVill(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getWbdcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());

		if (wbdcode && wbmcode) {

			List<SupercheckVill> supvil = supercheckVillPartition.getSupVill(
					requestModel.getWbdcode(),
					requestModel.getWbmcode());
			System.out.println("list size=>" + supvil.size());
			System.out.println("list =>" + supvil.toString());
			return supvil;
		}
		return null;
	}
	// ===========================//Rep_cropIns_abtract//================//
	 @Autowired private CropInsAbstracttPartition cropInsAbstracttPartition;

	@PostMapping("/cropinsab")
	List<CropInsAbstractt> getCropInsAbstract(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean wbdcode = regexpressionmethod.districtCode(requestModel.getDcode());
		if (wbdcode) {
		try {
			List<CropInsAbstractt> cropinsab = cropInsAbstracttPartition
					.getCropInss(requestModel.getDcode()
							    );
			System.out.println("list size=>" + cropinsab.size());
			// System.out.println("list =>" + cropinsab.toString());
			return cropinsab;
		} catch (Exception e) {
			System.out.println("Exception==>"+e);
			e.printStackTrace();
		}
		}
		return null;
	}

	// ===========================//Rep_Pernnial_Mand//================//
	@Autowired
	private RepPernnialMandPartition repPernnialMandPartition;

	@PostMapping("/pernnialMand")
	List<RepPernnialMand> getPernnialMand(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean dcode = regexpressionmethod.districtCode(requestModel.getDcode());
		boolean wbmcode = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean vcode = regexpressionmethod.villageCode(requestModel.getVcode());
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());
		boolean wbmcodee = regexpressionmethod.mandalCode(requestModel.getWbmcode());
		boolean saesonid = regexpressionmethod.year(seasonYear.toString());
		
//		if (dcode && wbmcode && vcode && mcode && wbmcodee && saesonid) {

			List<RepPernnialMand> pernnial = repPernnialMandPartition.getPerrnniaDet(requestModel.getDcode(),
					requestModel.getWbmcode(), requestModel.getVcode(), requestModel.getCropyear(),
					requestModel.getWbdcode(),requestModel.getCrop());
			System.out.println("list size=>" + pernnial.size());
			return pernnial;
//		}
//		return null;
	}
	// =====================Allocated-Survey-No=======================//
	@Autowired
	AllocatedSurveyNoPartition allocatedSurveyNoPartition;

	@PostMapping("/allsno1")
	public List<AllocatedSurveyNo> getAllSnumber(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());
		String[] season = requestModel.getCropyear().split("@");
		String cseason = season[0];System.out.println("cseason=====>"+cseason);
		Integer Year = Integer.parseInt(season[1]);System.out.println("Year=====>"+Year);

		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
		boolean mcode = regexpressionmethod.mandalCode(requestModel.getMcode());System.out.println("mcode=====>"+mcode);
		boolean saesonid = regexpressionmethod.year(Year.toString());System.out.println("saesonid=====>"+saesonid);
		if (mcode && saesonid) {

			try {

				List<AllocatedSurveyNo> allsno = allocatedSurveyNoPartition.getAllSno(
						requestModel.getWbdcode(),
						requestModel.getMcode(), 
						requestModel.getCropyear());
				System.out.println("list=========>" + allsno.size());
				return allsno;
			} catch (Exception e) {

				System.out.println(" Exception==============>" + e);
				return null;
			}
		}
		return null;
	}

}
