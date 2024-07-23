package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.CropwiseExtBooked;

public interface CropwiseExtBookedService {
	public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear,String cropname,String crpgrp,int role);
	
	public List<CropwiseExtBooked> getCropwise(int dcode,String sescrpyear);

}
