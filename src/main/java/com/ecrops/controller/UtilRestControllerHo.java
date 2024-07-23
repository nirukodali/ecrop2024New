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

import com.ecrops.entity.Abstractreport;
import com.ecrops.entity.CropBookingDetailsHO;
import com.ecrops.entity.Cropwise;
import com.ecrops.entity.Detailunlock;
import com.ecrops.entity.Iriigationreport;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.entity.RepUnlock;
import com.ecrops.entity.Searchkhataandsurvey;
import com.ecrops.entity.SuperChkVeriftyEntity;
import com.ecrops.entity.SupervisoryCheckReportEntity;
import com.ecrops.entity.VillNormal;
import com.ecrops.model.RequestModel;
import com.ecrops.partition.Rep_unlock_ext;
import com.ecrops.partition.Villagewise;
import com.ecrops.partition.cropwise;
import com.ecrops.partition.irrigationwisecropbooking;
import com.ecrops.partition.AbstractReportsRepo;
import com.ecrops.partition.CropBookingDetailsHOpartitions;
import com.ecrops.partition.DetailUnlockreportHo;
import com.ecrops.partition.SearchByKhataandsurvey;
import com.ecrops.partition.SuperCheckVerifyPartition;
import com.ecrops.partition.SupervisoryCheckReportRepo;
import com.ecrops.projection.MasterProjections;
import com.ecrops.repo.NormalAreasMwiseMaoRepo;
import com.ecrops.repo.SeasonCropBookedExtentRepo;

@RestController

@RequestMapping("/utilHoLogin")
public class UtilRestControllerHo {

	@Autowired
	private  Villagewise  villagewise;
	
	@Autowired
	NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;
	
	
	@Autowired
	SeasonCropBookedExtentRepo  seasonCropBookedExtentRepo;
	
	@Autowired
	private Rep_unlock_ext rep_unlock_ext;
	
	@Autowired
	private com.ecrops.partition.irrigationwisecropbooking repo;
	
	@GetMapping("/getAllCrop")
	public List<MasterProjections> getAllCrop() {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllCrops();
		return list;
	}

	
	@GetMapping("/getallDistricts")
	public List<MasterProjections> getDistrict() {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getDistrict();
		System.out.println("------"+list.size());
		return list;
	}

	@PostMapping("/villwise")
	public ResponseEntity<?> getVillagewise(@RequestBody RequestModel requestModel) {
		
		RegularExpressionclassMethod method=new RegularExpressionclassMethod();
		boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
		boolean mcode = method.mandalCode(requestModel.getMcode());
		String[]cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		
		List<VillNormal>villwise=null;
		try
		{
			if(season&&year&&mcode) {
		  villwise=villagewise.getvillwise(requestModel.getMcode(),requestModel.getCropyear());
			}
		 return new ResponseEntity<List<VillNormal>>(villwise, HttpStatus.OK);
		}
		catch (Exception e) {

			System.out.println("getStackTrace =>" + e.getStackTrace());
		
			return null;
		}
		
	}
	
	@GetMapping("/getAllSeason")
	public List<MasterProjections> getAllSeason() {
		List<MasterProjections> list = normalAreasMwiseMaoRepo.getAllSeason();
		return list;
	}
	
	@GetMapping("/getHoMandals")
	public List<MasterProjections> getHoMandals(String mcode) {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getHoMandals(Integer.parseInt(mcode));
		return list;
	}
	
	@GetMapping("/getAllVillages")
	public List<MasterProjections> getVillages(Integer dcode, Integer mcode) {
		System.out.println("dcode=>" + dcode);
		System.out.println("mcode=>" + mcode);
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllVillages(dcode, mcode);
		return list;
	}

	
	
	@GetMapping("/getIrrigationSource")
	public List<MasterProjections> getIrrigationSource() {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getirrigationsource();
		System.out.println("=======list==========" + list.size());
		return list;
	}
	
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




	@PostMapping("/irrigartionabstract")
	public ResponseEntity<?> repo(@RequestBody RequestModel requestModel) 
	{
		RegularExpressionclassMethod method=new RegularExpressionclassMethod();
		boolean cropid=method.cropid(requestModel.getCropid());
		boolean mcode = method.mandalCode(requestModel.getMcode());
		boolean dcode=method.districtCode(requestModel.getDcode());
		System.out.println(""+cropid+mcode+mcode+dcode);
		try {
			
			List<Iriigationreport> irrigation= repo.getigcropwiseabstrct(requestModel.getCropid(),requestModel.getCropgrpid() ,requestModel.getMcode(),requestModel.getDcode(), requestModel.getCropyear(),requestModel.getDisplay(),requestModel.getWaterId());
			System.out.println("crpins size=>" + irrigation.size());
			return new ResponseEntity<List<Iriigationreport>>(irrigation, HttpStatus.OK);
		} catch (Exception e) {

			System.out.println("getStackTrace =>" + e.getStackTrace());
		
			return null;
		}
		
	}	

	@PostMapping("/repext")
	public ResponseEntity<?> getRep_unlock_ext(@RequestBody RequestModel requestModel,HttpSession httpSession) {
		System.out.println("requestModel=>" + requestModel.toString());
		
		RegularExpressionclassMethod method=new RegularExpressionclassMethod();
//		System.out.println("wbdcode-------->"+httpSession.getAttribute("dcode"));
//		boolean wbdcode = method.wbdCode(httpSession.getAttribute("dcode").toString());
		
		boolean mcode = method.mandalCode(requestModel.getMcode());
		System.out.println("wbmcode-------->"+requestModel.getMcode());
		String[]cropyear = requestModel.getCropyear().split("@");
		String seasonType = cropyear[0];
		Integer seasonYear = Integer.parseInt(cropyear[1]);
		boolean season = method.season(cropyear[0]);
		boolean year = method.year(cropyear[1]);
		List<RepUnlock> repext =null;
		try {
			if( mcode && season && year) {
			 repext = rep_unlock_ext.getRep_unlock_ext(requestModel.getWbdcode(),requestModel.getMcode(),
					requestModel.getCropyear(),httpSession.getAttribute("userid").toString());
			System.out.println("crpins size=>" + repext.size());
			}
			return new ResponseEntity<List<RepUnlock>>(repext, HttpStatus.OK);
		} catch (Exception e) {

			System.out.println("getStackTrace =>" + e.getStackTrace());
		
			return null;
		}
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
//////////Search Khata and Survey 
@Autowired
SearchByKhataandsurvey searchByKhataandsurvey;
@PostMapping("/khatasurvey")
List<Searchkhataandsurvey> getkhataandsurvey(@RequestBody RequestModel requestModel,HttpServletRequest servelet) throws SQLException {
	System.out.println("requestModel=>" + requestModel.toString());
	List<Searchkhataandsurvey> khatno = searchByKhataandsurvey.getkhataandsurvey(requestModel.getWbdcode(), requestModel.getDcode(), requestModel.getWbmcode(), 
			requestModel.getVcode(),requestModel.getMcode(),requestModel.getMandal(),
			requestModel.getCropyear(), requestModel.getCrop(),requestModel.getCropcode(),requestModel.getSelected(),requestModel.getValue());
	System.out.println(" value --->"+requestModel.getValue());
	System.out.println("--->"+requestModel.toString());
	System.out.println("details===================>" + khatno.size());
	return khatno;

}
///////////////////Irrigation Wise On CropBooking////////////////////
//@PostMapping("/irrigartionabstract")
//public ResponseEntity<?> repo2(@RequestBody RequestModel requestModel) 
//{
//RegularExpressionclassMethod method=new RegularExpressionclassMethod();
//boolean cropid=method.cropid(requestModel.getCropid());
//boolean mcode = method.mandalCode(requestModel.getMcode());
//boolean dcode=method.districtCode(requestModel.getDcode());
//System.out.println(""+cropid+mcode+mcode+dcode);
//try {
//
//List<Iriigationreport> irrigation= repo.getigcropwiseabstrct(requestModel.getCropid(),requestModel.getCropgrpid() ,requestModel.getMcode(),requestModel.getDcode(), requestModel.getCropyear(),requestModel.getDisplay(),requestModel.getWaterId());
//System.out.println("crpins size=>" + irrigation.size());
//return new ResponseEntity<List<Iriigationreport>>(irrigation, HttpStatus.OK);
//} catch (Exception e) {
//
//System.out.println("getStackTrace =>" + e.getStackTrace());
//
//return null;
//}
//}

/////////////////////Irrigation Wise On CropBooking////////////////////
//@Autowired
//private irrigiationrepo repo;
//@PostMapping("/irrigartionab")
//public ResponseEntity<?> repo(@RequestBody RequestModel requestModel) 
//{
//
//try {
//
//List<Iriigationreport> irrigation= repo.getigcropwise(requestModel.getCropid(),requestModel.getCropgrpid() ,requestModel.getMcode(),requestModel.getDcode(), requestModel.getCropyear(),requestModel.getDisplay());
//System.out.println("crpins size=>" + irrigation.size());
//return new ResponseEntity<List<Iriigationreport>>(irrigation, HttpStatus.OK);
//} catch (Exception e) {
//
//System.out.println("getStackTrace =>" + e.getStackTrace());
//
//return null;
//}










//////////////////////////Abstarct Report ////////////////////

@Autowired
private AbstractReportsRepo abstractReportsRepo;
@PostMapping("/abstractreport")
public ResponseEntity<?> getAbstractReportsRepo(@RequestBody RequestModel requestModel) 
{


try {

List<Abstractreport> abstarct= abstractReportsRepo.getAbstractReportsRepo(requestModel.getCropid(),requestModel.getCropgrpid() ,requestModel.getMcode(),requestModel.getDcode(), requestModel.getCropyear(),requestModel.getDisplay());
System.out.println("crpins size=>" + abstarct.size());
return new ResponseEntity<List<Abstractreport>>(abstarct, HttpStatus.OK);
} catch (Exception e) {

System.out.println("getStackTrace =>" + e.getStackTrace());

return null;
}




}

/////////////////////// REPORTON  village wise//////////////////////////


//@PostMapping("/villwise")
//public ResponseEntity<?> getVillagewise(@RequestBody RequestModel requestModel) {
//
//RegularExpressionclassMethod method=new RegularExpressionclassMethod();
//boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
//boolean mcode = method.mandalCode(requestModel.getMcode());
//String[]cropyear = requestModel.getCropyear().split("@");
//String seasonType = cropyear[0];
//Integer seasonYear = Integer.parseInt(cropyear[1]);
//boolean season = method.season(cropyear[0]);
//boolean year = method.year(cropyear[1]);
//System.out.println(""+wbdcode+mcode+season+year);
//
//List<VillNormal>villwise=null;
//try
//{
//if(season&&year&&mcode) {
//villwise=villagewise.getvillwise(requestModel.getMcode(),requestModel.getCropyear());
//}
//return new ResponseEntity<List<VillNormal>>(villwise, HttpStatus.OK);
//}
//catch (Exception e) {
//
//System.out.println("getStackTrace =>" + e.getStackTrace());
//
//return null;
//}
//
//}

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
//
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


/////////Report on village wise normal area by cropwise ////////////////////

@Autowired
private cropwise crpwise;

@PostMapping("/cropwise")
public ResponseEntity<?> getCropwise(@RequestBody RequestModel requestModel) {
try
{
 List<Cropwise> cropwise=crpwise.getcropwise(requestModel.getCropgrpid(),requestModel.getCropyear(),requestModel.getDcode(),requestModel.getCropcode());
 System.out.println("requestModel.getCropgrpid()***********"+requestModel.getCropcode());
 return new ResponseEntity<List<Cropwise>>(cropwise, HttpStatus.OK);
}
catch (Exception e) {

	System.out.println("getStackTrace =>" + e.getStackTrace());
	
	return null;
}

}

/////////////Crop booking on HO////////	

@Autowired
CropBookingDetailsHOpartitions cropBookingDetailsHO;

@PostMapping("/crpHO")
List<CropBookingDetailsHO> getCropHo(@RequestBody RequestModel requestModel) throws SQLException {
System.out.println("requestModel=>" + requestModel.toString());

List<CropBookingDetailsHO> crdbho = cropBookingDetailsHO.getCropDetailsHO(
		requestModel.getWbdcode(), requestModel.getDcode(), requestModel.getWbmcode(), 
		requestModel.getVcode(),requestModel.getMcode(),requestModel.getMandal(),
		
		requestModel.getCropyear(), requestModel.getCrop(),requestModel.getCropcode());
System.out.println("details===================>" + crdbho.size());
return crdbho;
}

//////////Details Report Unlock 
@Autowired
DetailUnlockreportHo detailUnlockreportHo;
@PostMapping("/detailho")

List<Detailunlock> getdetailunlock(@RequestBody RequestModel requestModel) throws SQLException {
System.out.println("requestModel=>" + requestModel.toString());

RegularExpressionclassMethod method=new RegularExpressionclassMethod();
boolean wbdcode = method.wbdCode(requestModel.getWbdcode());
boolean mcode = method.mandalCode(requestModel.getMcode());
String[]cropyear = requestModel.getCropyear().split("@");
String seasonType = cropyear[0];
Integer seasonYear = Integer.parseInt(cropyear[1]);
boolean season = method.season(cropyear[0]);
boolean year = method.year(cropyear[1]);
System.out.println(""+wbdcode+mcode+season+year);
List<Detailunlock> detailunlock = detailUnlockreportHo.getdetailunlock( requestModel.getWbdcode(), requestModel.getMcode(),requestModel.getCropyear(),requestModel.getDcode(),requestModel.getApproval());
 


System.out.println("details===================>" + detailunlock.size());
return detailunlock;
}



	
}
