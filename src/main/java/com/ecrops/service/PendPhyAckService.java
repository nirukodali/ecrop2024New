package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.pendingphyackModel;

public interface PendPhyAckService {

public List<pendingphyackModel> getPendPhyAckdet(String tab,Integer wbdcode,String userid);	
		
}
