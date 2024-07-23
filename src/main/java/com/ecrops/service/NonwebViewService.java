package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.nonwebViewModel;

public interface NonwebViewService {

	List <nonwebViewModel> getnonwebviewdet(String t1,Integer dcode, Integer mcode,String userid,String season, Integer cropyear1);
	
}
