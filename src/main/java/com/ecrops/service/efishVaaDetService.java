package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.eFishVaaDetModel;

public interface efishVaaDetService {

	
	List<eFishVaaDetModel> getefishVaaDet(Integer dcode, Integer mcode, Integer vcode1, String tab);

}
