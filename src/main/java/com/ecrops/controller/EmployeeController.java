package com.ecrops.controller;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecrops.entity.AppUser;
import com.ecrops.entity.Employeename;
import com.ecrops.projection.EmployeeName;
import com.ecrops.projection.InchargeRbkProjection;
import com.ecrops.projection.RbkDetailsProjection;
import com.ecrops.projection.VillageName;
import com.ecrops.projection.WbVillageRepository;
import com.ecrops.repo.EmpLogsSaveRepository;
import com.ecrops.repo.EmpRepo;
import com.ecrops.repo.InchargeRbkRepo;
import com.ecrops.repo.VillRepo;
import com.ecrops.service.EmployeeService;

@PreAuthorize("hasAuthority('5')")
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmpLogsSaveRepository empLogsRepo;

	@Autowired
	private VillRepo villRepo;

	@Autowired
	private EmpRepo empRepo;

	@Autowired
	private InchargeRbkRepo inchargeRbkRepo;
	String email = "";
	AppUser user;

	@GetMapping("/rbkmapping")
	public String rbkmappingTest(Model model, HttpSession httpSession) {
		String district = (String) httpSession.getAttribute("dcode");
		String mandal = (String) httpSession.getAttribute("mcode");

		List<EmployeeName> EmpName = empRepo.findEmpName(Integer.parseInt(mandal));
		List<VillageName> villname = villRepo.findVillName(district, mandal);
		model.addAttribute("EmpName", EmpName);
		model.addAttribute("villname", villname);

		List<RbkDetailsProjection> regDet = employeeService.getRegDMcode(Integer.parseInt(district),
				Integer.parseInt(mandal));
		model.addAttribute("regDetails", regDet);
		return "maoroles/rbkmapping";
	}

	@PostMapping("/rbkSaveprofile")
	public String saverbk(@ModelAttribute("rbk") Employeename employee, HttpServletRequest httpServletRequest,
			HttpSession httpSession, Model model) {
		int updateemployee = 0;
		int InsertEmployee = 0;
		int Employeeincharge = 0;
		
		String district = (String) httpSession.getAttribute("dcode");
		String mandal = (String) httpSession.getAttribute("mcode");
		String emp = httpServletRequest.getParameter("employee");
		String rbkuserCode = httpServletRequest.getParameter("village");
		String inchargeSts = httpServletRequest.getParameter("incharge");
		String rbkuserid = "RBK_"+rbkuserCode;
		
		List<EmployeeName> EmpProfile = empRepo.getEmpProfile(Integer.parseInt(district),
				Integer.parseInt(mandal), Integer.parseInt(emp));

		List<WbVillageRepository> weblanddetails = employeeService.getWebLandDetails(Integer.parseInt(district),
				Integer.parseInt(mandal));
		String wbdcode = weblanddetails.get(0).getWbdcode();
		String wbmcode = weblanddetails.get(0).getWbmcode();

		int valid = 1;
		if (valid == 0) {
		} else {
			boolean prevMap = false;
			String oldEmpcode = "", oldrbkcode = "";
			
			if (inchargeSts.equals("R")) {
				List<InchargeRbkProjection> getmandempcode = inchargeRbkRepo.getmandempcode(Integer.parseInt(mandal),
						Integer.parseInt(emp));
				if (getmandempcode.size() > 0) {
					prevMap = true;
				}
				if (prevMap) {
					System.out.println("---------Data Is Already Inserted");
//					oldEmpcode = getmandempcode.get(0).getEmpcode();
//					oldrbkcode = getmandempcode.get(0).getRbkcode();
//					String emprbkInc_sts = getmandempcode.get(0).getInchargests();
//					List<InchargeRbkProjection> getEmprbkmcode = inchargeRbkRepo.getEmprbkmcode(
//							Integer.parseInt(mandal), Integer.parseInt(oldEmpcode), Integer.parseInt(oldrbkcode));
//					if (getEmprbkmcode.size() > 0) {
//						updateemployee = empLogsRepo.updateEmployeeLogDetails(Integer.parseInt(oldEmpcode),
//								Integer.parseInt(oldrbkcode));
//						if (updateemployee > 0) {
//						}
//					} else {
//						int insertEmployeeLogDetails = empLogsRepo
//								.insertEmployeeLogDetails(Integer.parseInt(oldEmpcode), Integer.parseInt(oldrbkcode));
//					}
//					if (emprbkInc_sts != null) {
//						String incsts = "R";
//						if (emprbkInc_sts.equals("I")) {
//							incsts = "R";
//						}
//						String inchargeStsUpd = "R";
//
//						int insertEmployeeLogincts = empLogsRepo.insertEmployeeLogincts(Integer.parseInt(district),
//								Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
//								Integer.parseInt(rbkuserCode), Integer.parseInt(wbdcode), Integer.parseInt(wbmcode),
//								inchargeStsUpd);
//						
//							System.out.println("emp---->" + emp);
//							System.out.println("rbkUcoDE---->" + rbkuserCode);
//							System.out.println("oldrbkcode---->" + oldrbkcode);
//							try {
//							updateemployee = empLogsRepo.updateEmployeerbkmapstatus(inchargeStsUpd,
//									Integer.parseInt(emp), Integer.parseInt(rbkuserCode), Integer.parseInt(rbkuserCode),
//									Integer.parseInt(oldrbkcode));
//						} catch (Exception e) {
//							model.addAttribute("msg", "Record Exists With This Combination");
//							e.printStackTrace();
//						}
//					} else {
//						System.out.println(rbkuserCode+"-=-------------if true"+oldrbkcode+"------------"+emp);
//						updateemployee = empLogsRepo.updaterbkmapregularstatus(Integer.parseInt(emp),
//								Integer.parseInt(rbkuserCode), Integer.parseInt(rbkuserCode),
//								Integer.parseInt(rbkuserCode));
//					}
					List<EmployeeName> EmpName = empRepo.findEmpName(Integer.parseInt(mandal));
					List<VillageName> villname = villRepo.findVillName(district, mandal);
					model.addAttribute("EmpName", EmpName);
					model.addAttribute("villname", villname);

					List<RbkDetailsProjection> regDet = employeeService.getRegDMcode(Integer.parseInt(district),
							Integer.parseInt(mandal));
					System.out.println("regDet------------------------------------------------->" + regDet.size());
					model.addAttribute("regDetails", regDet);
					model.addAttribute("msg", "Selected Employee Is Already a Regular");
					return "maoroles/rbkmapping";
				} 
				else {

					List<InchargeRbkProjection> rbkstatus = inchargeRbkRepo.rbkstatus(Integer.parseInt(mandal),
							Integer.parseInt(rbkuserCode));
					System.out.println(emp+"else--------------"+rbkuserCode);
					if (rbkstatus.size() > 0) {
						System.out.println("-=-------------if true");
						updateemployee = empLogsRepo.updaterbkmapregularstatus(Integer.parseInt(emp),
								Integer.parseInt(rbkuserCode), Integer.parseInt(rbkuserCode),
								Integer.parseInt(rbkuserCode));
						int insertEmployeeLogincts = empLogsRepo.insertEmployeeLogincts(Integer.parseInt(district),
								Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
								Integer.parseInt(rbkuserCode), Integer.parseInt(wbdcode), Integer.parseInt(wbmcode),
								"R");
					} else {
						System.out.println("-=-------------if false");
						String inchargeStsUpd = "R";
						InsertEmployee = empLogsRepo.insertEmployeeincts(Integer.parseInt(district),
								Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
								Integer.parseInt(rbkuserCode), Integer.parseInt(wbdcode), Integer.parseInt(wbmcode));
						int insertEmployeeLogincts = empLogsRepo.insertEmployeeLogincts(Integer.parseInt(district),
								Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
								Integer.parseInt(rbkuserCode), Integer.parseInt(wbdcode), Integer.parseInt(wbmcode),
								inchargeStsUpd);
					}
				
				}

			}

			else if (inchargeSts.equals("A") || inchargeSts.equals("I")) {
				String status = "N";
				Long countttt = employeeService.checkInchargeExt(Integer.parseInt(mandal), Integer.parseInt(rbkuserCode) ,  Integer.parseInt(emp));
				System.out.println("-0--------count=-----=0-=0-="+countttt);
				if(countttt > 0) {
					List<EmployeeName> EmpName = empRepo.findEmpName(Integer.parseInt(mandal));
					List<VillageName> villname = villRepo.findVillName(district, mandal);
					model.addAttribute("EmpName", EmpName);
					model.addAttribute("villname", villname);

					List<RbkDetailsProjection> regDet = employeeService.getRegDMcode(Integer.parseInt(district),
							Integer.parseInt(mandal));
					System.out.println("regDet------------------------------------------------->" + regDet.size());
					model.addAttribute("regDetails", regDet);
					model.addAttribute("msg", "Selected Employee Is Already Requested for Incharge Approval for this RBK");
					return "maoroles/rbkmapping";
				}
				else {
				Employeeincharge = empLogsRepo.insertEmployeeincharges(Integer.parseInt(district),
						Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
						rbkuserid, Integer.parseInt(wbdcode), Integer.parseInt(wbmcode),
						inchargeSts, status);
//				int insertEmployeeLogincts = empLogsRepo.insertEmployeeLogincts(Integer.parseInt(district),
//						Integer.parseInt(mandal), Integer.parseInt(rbkuserCode), Integer.parseInt(emp),
//						Integer.parseInt(rbkuserCode), Integer.parseInt(wbdcode), Integer.parseInt(wbmcode),
//						"I");
				}
			}

		}
		if ((updateemployee > 0 || InsertEmployee > 0) || Employeeincharge > 0) {
			String msg = "Successfully Added";
			model.addAttribute("msg", msg);
		}
		else {
			String msg = "Update Failed";
			model.addAttribute("msg", msg);
			return "maoroles/rbkmapping";
		}

		
		List<EmployeeName> EmpName = empRepo.findEmpName(Integer.parseInt(mandal));
		List<VillageName> villname = villRepo.findVillName(district, mandal);
		model.addAttribute("EmpName", EmpName);
		model.addAttribute("villname", villname);

		List<RbkDetailsProjection> regDet = employeeService.getRegDMcode(Integer.parseInt(district),
				Integer.parseInt(mandal));
		System.out.println("regDet------------------------------------------------->" + regDet.size());
		model.addAttribute("regDetails", regDet);

		return "maoroles/rbkmapping";

	}

	@GetMapping("/incharge_det")
	public String incharge(HttpSession httpSession, Model model) {

		String district = (String) httpSession.getAttribute("dcode");
		String mandal = (String) httpSession.getAttribute("mcode");
		List<InchargeRbkProjection> regIncDet = employeeService.getDMcode(Integer.parseInt(district),
				Integer.parseInt(mandal));
		if (regIncDet.isEmpty()) {
			model.addAttribute("noResults", true);
		} else {
			model.addAttribute("InchAdd", regIncDet);
		}
		return "maoroles/incharge_det";
	}
}
