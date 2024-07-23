package com.ecrops.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.dto.DeceasedFarmerDto;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.repo.DeceasedFarmerRepo;

@Service
public class DeceasedFarmerImpl {

	@Autowired
	DeceasedFarmerRepo deceasedFarmerRepo;

	@Transactional
	public void updateDeceasedFarmer(List<DeceasedFarmerDto> deceasedFarmerDtos, String partkey, String season,
			String wbdcode, int cropYear, int vcode, String userid, int mcode, String vname, String tab2,
			String surveyNo, String khataNo, String aadhaarNo, String crd_booking_orgtab, String tab1,
			String crd_orgtab) {
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		RegularExpressionclassMethod expressionclassMethod=new RegularExpressionclassMethod();

		for (DeceasedFarmerDto dto : deceasedFarmerDtos) {
			boolean aadhaar = method.checkAadharNumber(dto.getAadhaar());
			boolean name = method.checkName(dto.getHeirName());
			boolean fname = method.checkName(dto.getHeirfName());
			boolean mobileNumber = method.checkMobileNumber(dto.getMobile());
			boolean khata = method.checkKhataNo(dto.getKh_no());
			boolean survey = method.checkSuveyNo(dto.getCr_sno());
			boolean valid4= expressionclassMethod.validateVerhoeff(dto.getAadhaar());
			boolean valid = expressionclassMethod.checkstring(dto.getAadhaar());
			boolean valid2 = expressionclassMethod.checkstring(dto.getHeirName());
			boolean valid3 = expressionclassMethod.checkstring(dto.getHeirfName());

			try {

				System.out.println("====" + dto.getBookingid() + "===" + dto.getDateofDeath() + "===d"+ dto.getHeirfName() + "===f" + dto.getRelation() + "===r" + dto.getAadhaar());
				if (aadhaar && name && fname && mobileNumber && khata && survey && valid && valid2 && valid3 && valid4) {

					deceasedFarmerRepo.insertDeceasedFarmer(partkey, dto.getBookingid(), wbdcode, vcode,
							dto.getDateofDeath(), dto.getRelation(), dto.getCr_crop(), dto.getVariety(), dto.getCr_no(),
							dto.getCr_sow_date(), userid, vname, cropYear, season, dto.getKh_no(), dto.getCr_sno(),
							mcode);
					deceasedFarmerRepo.insert1(crd_booking_orgtab, tab1, surveyNo, khataNo, aadhaarNo,
							dto.getBookingid(), vcode, cropYear, season);
					deceasedFarmerRepo.insert2(crd_orgtab, tab2, surveyNo, khataNo, aadhaarNo, dto.getBookingid(),
							vcode, cropYear, season, dto.getCr_crop(), dto.getCr_no(), dto.getCr_sow_date(),
							dto.getVariety());
//					deceasedFarmerRepo.update1(tab2, surveyNo, khataNo, aadhaarNo, dto.getBookingid(), vcode, cropYear,
//							season, dto.getCr_crop(), dto.getCr_no(), dto.getCr_sow_date(), dto.getVariety(),
//							dto.getAadhaar(), dto.getHeirName(), dto.getHeirfName(), dto.getMobile());
//					deceasedFarmerRepo.update2(tab1, surveyNo, khataNo, aadhaarNo, dto.getBookingid(), vcode, cropYear,
//							season, dto.getCr_crop(), dto.getCr_no(), dto.getCr_sow_date(), dto.getVariety(),
//							dto.getAadhaar(), dto.getHeirName(), dto.getHeirfName(), dto.getMobile(), dto.getKh_no(),
//							dto.getCr_sno());
				}
			} catch (Exception e) {
				System.out.println("exception------->>" + e.getMessage());
				e.printStackTrace();
			}
		}

	}
}
