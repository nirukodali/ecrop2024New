package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.RepVaaFinalListModel;

public interface RepVaaFinalListService {

	public List<RepVaaFinalListModel> getVaafinallist(String tab1,String userid,Integer vcode1,Integer year,String season);
	
}
