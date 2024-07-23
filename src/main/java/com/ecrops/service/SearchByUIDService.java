package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.SearchByUIDModel;

public interface SearchByUIDService {

	public List<SearchByUIDModel> getSearchByUIDdet(String tname,String userid,String aNo); 
	
	
}
