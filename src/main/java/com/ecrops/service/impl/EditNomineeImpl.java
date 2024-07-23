package com.ecrops.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.dto.EditNomineeDto;
import com.ecrops.entity.RegularExpressionclassMethod;
import com.ecrops.repo.EditBiometricNomineeRepo;

@Service
public class EditNomineeImpl {

	@Autowired
	EditBiometricNomineeRepo repo;

	@Transactional
	public int updateNomineet1(List<EditNomineeDto> edit, String season, String cropyear, String tab, String tab2,
			int vcode, String surveyNo, String khataNo, String aadhaarNo, String partkey, int dcode, int mcode,
			String userid, String vname, String crbooking_orgtab, String crd_orgtab) {
//		int a =1;
		RegularExpressionclassMethod method = new RegularExpressionclassMethod();
		for (EditNomineeDto dto : edit) {
			try {
				boolean khata = method.checkKhataNo(dto.getKh_no());
				boolean cr_sno = method.checkSuveyNo(dto.getCr_sno());
				boolean name = method.checkName(dto.getNomineeName());
				boolean fname = method.checkName(dto.getNomineefName());
				boolean aadhaar = method.checkAadharNumber(dto.getAadhaar());
				boolean aadhaar2 = method.checkAadharNumber(dto.getFarmeruid());
				boolean num = method.checkMobileNumber(dto.getMobile());
				boolean valid= method.checkstring(dto.getNomineeName());
				boolean valid2= method.checkstring(dto.getNomineefName());
				boolean valid3= method.checkstring(dto.getAadhaar());
				boolean valid4= method.checkstring(dto.getFarmeruid());
				boolean valid5= method.validateVerhoeff(dto.getAadhaar());
				boolean valid6= method.validateVerhoeff(dto.getFarmeruid());
				if (khata && cr_sno && name && fname && aadhaar && aadhaar2 && num && valid && valid2 && valid3 && valid4 && valid5 && valid6) {
					System.out.println("====" + dto.getBookingid() + "===" + dto.getAadhaar() + "==="
							+ dto.getNomineefName() + "===" + dto.getRelation() + "===" + dto.getNomineeName());
					repo.updateNominee(tab, dto.getBookingid(), dto.getNomineefName(), vcode, dto.getNomineeName(),
							dto.getMobile(), cropyear, season, dto.getKh_no(), dto.getCr_sno(), dto.getAadhaar(),
							surveyNo, khataNo, aadhaarNo);
					repo.updateNominee2(tab2, dto.getBookingid(), dto.getNomineefName(), vcode, dto.getNomineeName(),
							dto.getMobile(), cropyear, season, dto.getKh_no(), dto.getCr_sno(), dto.getAadhaar(),
							surveyNo, khataNo, aadhaarNo, dto.getCr_crop(), dto.getCr_no(), dto.getVariety(),
							dto.getCr_sow_date());
					repo.insertNominee1(partkey, dto.getBookingid(), dcode, vcode, dto.getRelation(), dto.getCr_crop(),
							dto.getVariety(), dto.getCr_no(), dto.getCr_sow_date(), userid, vname, cropyear, season,
							dto.getKh_no(), dto.getCr_sno(), mcode, dto.getNomineeName(), dto.getNomineefName(),
							dto.getOc_name());
					repo.insertNominee2(crd_orgtab, tab2, dto.getBookingid(), dto.getCr_crop(), dto.getCr_no(),
							dto.getVariety(), cropyear, season, dto.getCr_sow_date(), surveyNo, khataNo, aadhaarNo,
							vcode);
					repo.insertNominee3(crbooking_orgtab, tab, dto.getBookingid(), vcode, cropyear, season, surveyNo,
							khataNo, aadhaarNo);
//				a=2;
				}
			} catch (Exception e) {
				System.out.println("exception------->>" + e.getMessage());
				e.printStackTrace();
//				a=3;
			}
		}
		return 0;
	}

}
