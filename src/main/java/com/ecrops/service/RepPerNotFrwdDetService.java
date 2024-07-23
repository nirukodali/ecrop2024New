package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.RepPerFrwdDetModel;

public interface RepPerNotFrwdDetService {

	List<RepPerFrwdDetModel> getPerNotFrwdDet(String tab, String tab2,Integer vcode1);
	
	
	
}
