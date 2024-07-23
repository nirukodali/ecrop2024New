package com.ecrops.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.dto.EditUidDto;
import com.ecrops.repo.EditUidRepo;

@Service
public class EditUidImpl {

	@Autowired
	EditUidRepo repo;

	@Transactional
	public void edit(List<EditUidDto> edit, String tab2, String selectedValue, String value,String surveyNo,String khataNo,String aadhaarNo,
			int year,String season,int vcode) {

		for (EditUidDto dto : edit) {
			try {
				System.out.println("====" + dto.getBookingid() + "===" + dto.getFarmeruid() + "===" + dto.getOc_fname()
						+ "===" + dto.getOccupname() + "===" + dto.getCr_sow_date() + "===" + dto.getCr_no());
				
				repo.insert(tab2,selectedValue,value,surveyNo,khataNo,aadhaarNo,dto.getBookingid(),dto.getCr_sow_date(),dto.getCr_crop(),vcode,dto.getVariety(),year,season,dto.getCr_no());
				repo.update(tab2,selectedValue,value,surveyNo,khataNo,aadhaarNo,dto.getFarmeruid(),dto.getAadhaar(),dto.getBookingid(),dto.getCr_sow_date(),dto.getCr_crop(),vcode,dto.getVariety(),year,season,dto.getCr_no());
			} catch (Exception e) {
				System.out.println("exception------->>" + e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
