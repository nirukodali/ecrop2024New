package com.ecrops.service;

import java.util.List;

import com.ecrops.projection.InchargeRbkProjection;

public interface Delete_Incharge_Service {

	public List<InchargeRbkProjection> getmaoDMcode(Integer district, Integer mandal);
	
	public int deletemaoIch(Integer district, Integer mandal, String rbkusersList, String empcodeList);

}
