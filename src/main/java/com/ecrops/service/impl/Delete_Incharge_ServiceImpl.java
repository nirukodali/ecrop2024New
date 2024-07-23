package com.ecrops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.projection.InchargeRbkProjection;
import com.ecrops.repo.MAO_Incharge_Del_2_Repo;
import com.ecrops.repo.MAO_Incharge_Deletion_Repo;
import com.ecrops.service.Delete_Incharge_Service;

@Service
public class Delete_Incharge_ServiceImpl implements Delete_Incharge_Service{

	@Autowired
	private MAO_Incharge_Deletion_Repo incharge_Deletion_Repo;
	
	@Autowired
	private MAO_Incharge_Del_2_Repo incharge_Del_2_Repo;
	
	@Override
	public List<InchargeRbkProjection> getmaoDMcode(Integer district, Integer mandal) {
		return incharge_Deletion_Repo.getmaoDMcode(district, mandal);
	}

	@Override
	public int deletemaoIch(Integer district, Integer mandal, String rbkusersList, String empcodeList) {
		return incharge_Del_2_Repo.deletemaoIch(district, mandal, rbkusersList, empcodeList);
	}

}
