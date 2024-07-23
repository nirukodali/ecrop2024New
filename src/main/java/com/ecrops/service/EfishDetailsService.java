package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.EfishdetailEntity;


public interface EfishDetailsService {

	public List<EfishdetailEntity> getefishdetails(String vcode ,String khano);
	
	public int updatebooking(Integer recid,String allowable);
	
}
