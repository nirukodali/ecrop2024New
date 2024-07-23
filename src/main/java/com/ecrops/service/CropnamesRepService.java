package com.ecrops.service;

import java.util.List;
import com.ecrops.entity.CropnamesRep;

public interface CropnamesRepService {
	
	public List<CropnamesRep> findAll(int cropgrp); 

}
