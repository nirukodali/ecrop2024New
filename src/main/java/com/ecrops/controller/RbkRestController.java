package com.ecrops.controller;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecrops.entity.CropYearEntity;
import com.ecrops.entity.RepPerFrwdDetModel;
import com.ecrops.entity.VillageSec;
import com.ecrops.projection.ActiveSeasonProjection;
import com.ecrops.projection.EmployeeName;
import com.ecrops.projection.VillageDetailsProjection;
import com.ecrops.projection.WbVillageRepository;
import com.ecrops.repo.CropyearRepo;
import com.ecrops.repo.EmpRepo;
import com.ecrops.repo.VarietyNameRepo;
import com.ecrops.repo.WbvillageRepository;
import com.ecrops.service.EmployeeService;
import com.ecrops.service.GetVillageService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/utilRBK")
public class RbkRestController {
	

	@Autowired
	private EmployeeService employeeService;
	

	@Autowired
	private WbvillageRepository wbVillageRepository;
	
	@Autowired
	private EmpRepo empRepo;
	
	@Autowired
	private VarietyNameRepo nameRepo;
	
	@Autowired
	private CropyearRepo cropRepo;
	
	@Autowired
	private GetVillageService getVillService;

//	@GetMapping("/getRevenueVillage")
//	public List<WbVillageRepository> getRevenueVillage(@RequestParam("rbkCode") Integer rbkCode) {
//		System.out.println("rbkcode--->"+rbkCode);
//		List<WbVillageRepository> entities = new ArrayList<>();
//		try {
//			entities = wbVillageRepository.getWebLandDet(rbkCode);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return entities;
//
//	
//	}
	
	
//	@GetMapping("/getEmployeeByRevenue")
//	public List<EmployeeName> getEmployeeByRevenue(HttpSession httpSession, @RequestParam("rbkCode") Integer rbkCode) {
//        String district = (String) httpSession.getAttribute("dcode");
//          System.out.println("district"+district);
//		String mandal = (String) httpSession.getAttribute("mcode");
//		  System.out.println("mandal"+mandal);
//		List<WbVillageRepository> webLandDetails = employeeService.getWebLandDetails(Integer.parseInt(district),
//				Integer.parseInt(mandal));
//		String wbdcode2 = webLandDetails.get(0).getWbdcode();
//		String wbmcode2 = webLandDetails.get(0).getWbmcode();
//		System.out.println("wbdcode2"+wbdcode2);
//		System.out.println("wbmcode2"+wbmcode2);
//		System.out.println("rbkcode"+rbkCode);
//		List<EmployeeName> entities = new ArrayList<>();
//		try {
//			
//
//			entities = empRepo.getEmpByRevenue(rbkCode, Integer.parseInt(wbdcode2), Integer.parseInt(wbmcode2));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return entities;
//
//	
//	}
	
	@GetMapping("/getVillage")
	public List<VillageDetailsProjection> getVillage(@RequestParam("cr_crop") Integer cr_crop) {
		System.out.println("cr_crop--->"+cr_crop);
		List<VillageDetailsProjection> entities = new ArrayList<>();
		try {
			entities = nameRepo.findByVarietycode(cr_crop);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entities;

	}
	
	
//	@Autowired
//	private RepunlockextHoRepo repunlockextHoRepo;
	
//	@Autowired
//	private CropyearRepo cropyearRepo;
	
//	@GetMapping("/unlock")
//	public List<RepunlockextHOEntity>  getYear(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest,@RequestParam("season")Character season,@RequestParam("cr_dist_code")Integer cr_dist_code,@RequestParam("divcode")Integer divcode) {
	 
		//List<RepunlockextHOEntity> unlock = repunlockextHoRepo.findByYear(season,cr_dist_code,divcode);
//	    String crpyr = httpServletRequest.getParameter("crYear");
//		System.out.println("crpyr:::" + crpyr);
//		
//		String cropyear = crpyr.split("@")[1];
//		System.out.println("cropyear:::" + cropyear);
//
//       cr_dist_code=20;
//       divcode = (Integer) httpSession.getAttribute("mcode");
   //   return unlock;
	//}
		
	
	@GetMapping("/hello")
	public Map<String, String> hello() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "jayasri");
		map.put("name", "jayasri");
		map.put("name", "jayasri");
		map.put("name", "jayasri");
		return map;
	}
	
	
	@PostMapping("/Perfwds")
	public List perfwddet(Model model, HttpSession httpSession) {
		

//		List<ActiveSeasonProjection> activeSeason = getCropYearService.getActiveSeason();
//		model.addAttribute("activeseason", activeSeason);

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return villname;
	}

	@GetMapping("/PerNotFrwdDets")
	public List perNotfwddet(Model model, HttpSession httpSession) {

		String vscode = (String) httpSession.getAttribute("vscode");
		System.out.println("vscode:::" + vscode);

		List<VillageSec> villname = getVillService.getvillagedrpdwn(Integer.parseInt(vscode));
		model.addAttribute("villname", villname);

		return villname;
	}

	
	
}
	

