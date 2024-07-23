package com.ecrops.controller.rest;

import java.util.ArrayList; 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.dto.crop.response.InterCropNaturePattern;
import com.ecrops.dto.crop.response.SeasonCropYear;
import com.ecrops.dto.crop.response.VcodeWbvnameForSocialGrievance;
import com.ecrops.repo.crop.InterCropRepo;
import com.ecrops.service.InterSocialGrievanceService;

@RestController
@RequestMapping("/rest/api")
public class SocialGrievanceRestController {
	
	@Autowired
	private InterSocialGrievanceService interSocialGrievanceService;
	
	
	@Autowired
	private InterCropRepo cropRepo;
	
    @GetMapping("/interSocial/getSeasonCropYears")
    public List<SeasonCropYear> getSeasonValueCropyear() {
    	
    	List<Object[]> resultList=	cropRepo.getSeasonCropYears();
    	
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

    

	@GetMapping("/interSocial/getVillages")
	private List<VcodeWbvnameForSocialGrievance> getVillageCropPattern( HttpSession httpSession) {
		
		String vscode = (String) httpSession.getAttribute("vscode");
		
		System.out.println("vscode----"+vscode);

		List<VcodeWbvnameForSocialGrievance> result= interSocialGrievanceService.getVillageCodeAndName(Integer.parseInt(vscode));
		   
				return result;
	}
    
    
    @GetMapping("/interSocial/getIDNaturedesc")
    public List<InterCropNaturePattern> getCroppingPatternDetails() {
    	
    	List<Object[]> resultList=	cropRepo. getCroppingPattern();
    	
    	List<InterCropNaturePattern> getIDNaturedesc = new ArrayList<>();
		for (Object[] ob : resultList) {

			int id = (int) ob[0];
			String naturedesc = (String) ob[1];

			InterCropNaturePattern setData = new InterCropNaturePattern();

			setData.setId(id);
			setData.setNaturedesc(naturedesc);

			getIDNaturedesc.add(setData);

		}
		return getIDNaturedesc;
	
    }


}
