package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.CropbookingdetVAModel;

public interface CropBookingDetVAIntfService {

	public List<CropbookingdetVAModel>  getcropbkingdet(String tab1, Integer dcode,Integer vcode1,String season,Integer year, Integer mcode1, String display, Integer display1,Integer crpid,Integer sesmcode);
		
}
