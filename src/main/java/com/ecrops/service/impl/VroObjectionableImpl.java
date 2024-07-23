package com.ecrops.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.dto.VroObjectionableDto;
import com.ecrops.repo.VroObjectionableRepo;

@Service
public class VroObjectionableImpl {

	@Autowired
	VroObjectionableRepo repo;

//	@Transactional
//	public int code(List<VroObjectionableDto> dtoo) {
//		int cod=0;
//		for (VroObjectionableDto dto : dtoo) {
//			try {
//				 cod= repo.val(dto.getCategory());
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		}
//
//		return cod;
//	}

	@Transactional
	public void impl(List<VroObjectionableDto> dtoo, String tab, String vcod, String part, String name, String loc,
			String partitionName) {
		BigDecimal vcode = new BigDecimal(vcod);
		for (VroObjectionableDto dto : dtoo) {
			try {
				repo.insert(tab, dto.getCr_dist_code(), dto.getCr_mand_code(), vcode, dto.getBookingid(),
						dto.getCr_crop(), dto.getCr_no(), dto.getVariety(), dto.getKh_no(), dto.getCr_sno(),
						dto.getCr_sow_date(), dto.getCategory(), part, name, loc);
				repo.update(partitionName, dto.getCr_dist_code(), dto.getBookingid(), dto.getCr_crop(), dto.getCr_no(),
						vcode, dto.getVariety());
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

}
