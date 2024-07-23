package com.ecrops.controller;

import java.sql.SQLException; 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.entity.AuthCropWise;
import com.ecrops.entity.CropBookingDetailsDhoIntf;
import com.ecrops.entity.DhoCropDetEntity;
import com.ecrops.entity.PhyAckMwise;
import com.ecrops.model.RequestModel;
import com.ecrops.partition.CropBookingDetailsDhoIntfPartition;
import com.ecrops.partition.DhoCropBookDetRepo;
import com.ecrops.partition.PhyAckMwisePartition;
import com.ecrops.projection.MasterProjections;
import com.ecrops.repo.SeasonCropBookedExtentRepo;
import com.ecrops.repo.SeasonCropBookedExtentRepo.RbkList;
import com.ecrops.repo.SeasonCropBookedExtentRepo.RepVaaDetails;
import com.ecrops.repo.SeasonCropBookedExtentRepo.Revenue;
import com.ecrops.repo.VAADeviceDetRepository;
import com.ecrops.repo.VAADeviceDetRepository.VAADetView;
import com.ecrops.service.ActiveSeasonService;
import com.ecrops.util.MasterFunctions;

@RestController

@RequestMapping("/util/dho")
public class UtilRestControllerDho<VillSecView, VilllandView, pendingvillview> {

	@Autowired
	private ActiveSeasonService activeSeasonService;

	@Autowired
	SeasonCropBookedExtentRepo seasonCropBookedExtentRepo;

	/*
	 * @Autowired private NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;
	 * 
	 * @Autowired private FarmerBookingDetailsRepo farmerBookingDetailsRepo;
	 * 
	 * @Autowired private FarmerBookingDetailsPartitions partition;
	 * 
	 * @Autowired private RofrBookedExtentPartitions rofrBookedExtentPartitions;
	 * 
	 * @Autowired private MaoAuthVaaVroekycRepo maoAuthVaaVroekycRepo;
	 * 
	 * @Autowired private MaoAuthVaaVroekycPartition maoAuthVaaVroekycPartition;
	 * 
	 * @Autowired private RbkSurveyNoMappingRepo rbkSurveyNoMappingRepo;
	 * 
	 * @Autowired private RbkSurveyNoMappingPartition rbkSurveyNoMappingPartition;
	 * 
	 * @Autowired private AllocataedSurveyNoMappingRepo
	 * allocataedSurveyNoMappingRepo;
	 */

	@Autowired
	private VAADeviceDetRepository vaadetrepo;

	@GetMapping("/getAllSeason")
	public List<MasterProjections> getAllSeasonn() {
		List<MasterProjections> list = vaadetrepo.getAllSeason();
		return list;
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

//	=============================DDAP================================
//=====================AuthCropWise=======================

//	@Autowired
//	private AuthCropWisePartition authcropwisepartition;
//
//	@GetMapping("/getauth")
//	List<AuthCropWise> getauth(String cropyear, HttpSession session, String userid) {
//		String[] season = cropyear.split("@");
//		String seasonType = season[0];
//		String year = season[1];
//		Integer seasonYear = Integer.parseInt(season[1]);
//		return null;
//	}

//	@GetMapping("/getauth1")
//	List<AuthCropWise> getauthcropwise(String cropyear, String userid, String cropid) {
//		System.out.println("cropyear===================>" + cropyear);
//		System.out.println("userid===================>" + userid);
//		System.out.println("cropid===================>" + cropid);
//
//		int a = (activeSeasonService.listAll().get(0).getCropyear());
//		List<AuthCropWise> cropwise = authcropwisepartition.authcropwise(cropyear, cropid, a);
//		System.out.println("details===================>" + cropwise.size());
//
//		return cropwise;
//	}

//		=====================VAADeviceDetails================
	@Autowired
	private VAADeviceDetRepository vaadevicedetrepo;

	@RequestMapping("/getvaadvicedetreport")
	List<VAADetView> getList1(@RequestParam("dcode") String dcode) {
		System.out.println("dcode=>" + dcode);

		List<VAADetView> list = vaadevicedetrepo.getListt(dcode);

		System.out.println("list size=>" + list.size());

		return list;
	}

	@GetMapping("/vaadet")
	public ResponseEntity<?> getVAADet(@RequestParam("mcode") String mcode, String userid) {
		RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();

		boolean val1 = regexpressionmethod.mandalCode(mcode.toString());
		System.out.println("val-------------->" + val1);

		if (val1) {

			List<RepVaaDetails> vaadet = seasonCropBookedExtentRepo.getVaaDet(mcode);
			return new ResponseEntity<List<RepVaaDetails>>(vaadet, HttpStatus.OK);
		}
		return null;
	}

	@GetMapping("/VillageSecretariatList")
	public ResponseEntity<?> getVillages(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		Boolean dist = method.districtCode(dcode);
		Boolean mand = method.mandalCode(mcode);
		List<RbkList> rbklist = null;
		if (dist && mand) {
			rbklist = seasonCropBookedExtentRepo.getVillages(Integer.parseInt(dcode), Integer.parseInt(mcode));
		}
		return new ResponseEntity<List<RbkList>>(rbklist, HttpStatus.OK);

	}

	@GetMapping("/RbkVsRevenue")
	public ResponseEntity<?> getRbkVsRevenue(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		Boolean dist = method.districtCode(dcode);
		Boolean mand = method.mandalCode(mcode);
		List<Revenue> rbklist = null;
		if (dist && mand) {
			rbklist = seasonCropBookedExtentRepo.getRbkVsRevenue(Integer.parseInt(dcode), Integer.parseInt(mcode));
		}
		return new ResponseEntity<List<Revenue>>(rbklist, HttpStatus.OK);
	}

	@Autowired
	private PhyAckMwisePartition phyAckMwisePartition;

	@PostMapping("/Mphyack")
	List<PhyAckMwise> getPhyAckMwise(@RequestBody RequestModel requestModel) throws SQLException {
		System.out.println("hiiiiiiiiiiiiiiiiiiii");
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();

		Boolean wbdcode = method.districtCode(requestModel.getWbdcode());
		String[] cropyear = requestModel.getCropyear().split("@");
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		List<PhyAckMwise> Mphyack = null;
		if (wbdcode && year && season) {
			Mphyack = phyAckMwisePartition.getMPhyAck(requestModel.getWbdcode(), requestModel.getCropyear());
		}
		return Mphyack;
	}

//----->>DHO Report

	//==============================// Crop Booking Details Report//=====================================================//
	
	
	@Autowired
	private DhoCropBookDetRepo cropBookDetRepo;

	@PostMapping("/getdhoCropBookingDetails")
	public ResponseEntity<?> getdhoCropBookingDetails(@RequestBody RequestModel requestModel) {
		System.out.println("requestModel=>" + requestModel.toString());

		try {
			System.out.println("dcode =================> " + requestModel.getWbdcode());

			List<DhoCropDetEntity> cropbokdet = cropBookDetRepo.getdhoCropBookingDetails(requestModel.getWbdcode(),
					requestModel.getDcode(), requestModel.getMcode(), requestModel.getCropyear());
			System.out.println("cropbokdet size=>" + cropbokdet.size());

			return new ResponseEntity<List<DhoCropDetEntity>>(cropbokdet, HttpStatus.OK);
		} catch (Exception e) {

			System.out.println("getStackTrace =>" + e.getStackTrace());
			return null;
		}
	}

//	// ===========================//Normal Area Vs Extent //================//
//	@Autowired
//	private NormalVsExtentRepo normalVsExtentRepo;
//
//	@PostMapping("/getnormalVsextentBkd")
//	public ResponseEntity<?> getnormalVsextentBkd(@RequestBody RequestModel requestModel) {
//		System.out.println("requestModel=>" + requestModel.toString());
//
//		try {
//
//			List<NormalVsExtentEntity> normvsext = normalVsExtentRepo.getnormalVsextentBkd(requestModel.getCropid(),
//					requestModel.getDcode(), requestModel.getMcode(), requestModel.getCropyear());
//			System.out.println("normvsext size=>" + normvsext.size());
//			return new ResponseEntity<List<NormalVsExtentEntity>>(normvsext, HttpStatus.OK);
//		} catch (Exception e) {
//
//			System.out.println("getStackTrace =>" + e.getStackTrace());
//			return null;
//		}
//	}
//	
//	//==========================================// RBK  LIST//=====================================================//
//
//		@GetMapping("/getVillageSectrList")
//		public ResponseEntity<?> getVillagess(@RequestParam("dcode") String dcode, @RequestParam("mcode") String mcode) {
//			List<RbkList> rbklist = seasonCropBookedExtentRepo.getVillages(Integer.parseInt(dcode),Integer.parseInt(mcode));
//			
//			System.out.println("rbklist" + rbklist.size());
//			return new ResponseEntity<List<RbkList>>(rbklist, HttpStatus.OK);
//		}
//		
//		
//		// ================CropBookingDetailsDhoIntf====================//
//
//		@Autowired
//		CropBookingDetailsDhoIntfPartition cropBookingDetailsDhoIntfPartition;
//
//		@Autowired
//		MasterFunctions masterFunctions;
//
//		@PostMapping("/crpdho")
//		List<CropBookingDetailsDhoIntf> getCropdetDho(@RequestBody RequestModel requestModel) throws SQLException {
//			System.out.println("requestModel=>" + requestModel.toString());
//			
//			String[] season = requestModel.getCropyear().split("@");
//			String seasonType = season[0];
//			Integer seasonYear = Integer.parseInt(season[1]);
//			
//			RegularExpressionclassMethod regexpressionmethod = new RegularExpressionclassMethod();
//			boolean dcode=	regexpressionmethod.districtCode(requestModel.getDcode());System.out.println("dcode--->"+dcode);
//			boolean mcode=	regexpressionmethod.mandalCode(requestModel.getWbmcode());System.out.println("mcode--->"+mcode);
//			boolean vcode=	true;   //regexpressionmethod.villageCode(requestModel.getVcode());System.out.println("vcode--->"+vcode);
//			boolean  year=	regexpressionmethod.year(seasonYear.toString());System.out.println("year--->"+year);
//			if(dcode && vcode &&mcode && year) {
//
//			List<CropBookingDetailsDhoIntf> crdbDho = cropBookingDetailsDhoIntfPartition.getCropDetailsDho(
//					requestModel.getWbdcode(), 
//					requestModel.getDcode(), 
//					requestModel.getWbmcode(), 
//					requestModel.getVcode(),
//					requestModel.getCropyear(),
//					requestModel.getCrop());
//			System.out.println("details===================>" + crdbDho.size());
//			return crdbDho;
//		}
//			return null;
//		}
}
