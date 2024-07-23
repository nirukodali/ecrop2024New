package com.ecrops.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.dto.LockDto;
import com.ecrops.repo.LockRepo;
import com.lowagie.tools.Executable;

@Service
public class LockImpl {
	
	@Autowired
	LockRepo lockRepo;
	
	@Transactional
	public void update(List<LockDto> dtos,String tab,String lock) {
		
		for(LockDto dto : dtos) {
			System.out.println("imppll---"+lock);
			int res =0 ;
			try {
			String[] reason = dtos.get(0).getReason().split("\\.");
			res = Integer.parseInt(reason[0]);
			}
			catch(Exception e) {
				
			}
			System.out.println(res);
			try {
				
				
				if(lock.equals("lock")) {
					if(!(dto.getExtlock() == null) && res != 0) {
				lockRepo.update(tab,dto.getCr_no(),res,dto.getBookingid(),dto.getFarmeruid(),dto.getKh_no(),dto.getMix(),
						dto.getCr_crop(),dto.getVariety(),dto.getExtlock());
					}
			}
				if(lock.equals("unlock")) {
					if(!(dto.getExtlock() == null) && res != 0) {
				lockRepo.updateUnlock(tab,dto.getCr_no(),Integer.parseInt(dto.getReason()),dto.getBookingid(),dto.getFarmeruid(),dto.getKh_no(),dto.getMix(),
						dto.getCr_crop(),dto.getVariety(),dto.getExtlock());}
			}
			}
			catch (Exception e) {
System.out.println(e);
			}
			
		}
		
	}

}
