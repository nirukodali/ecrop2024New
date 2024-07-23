package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.WbMaster;

public interface VillageService {
	public List<WbMaster> findAll(int dcode,int mcode);

	public List<WbMaster> getwbVillages(Integer dcode, Integer mcode);

	public List<WbMaster> getnotjiovcode(Integer dcode, Integer mcode);
}
