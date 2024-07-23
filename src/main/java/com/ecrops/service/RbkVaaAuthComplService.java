package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.RBKVAAAuthComplModel;

public interface RbkVaaAuthComplService {

	List<RBKVAAAuthComplModel> getVaaAuthComplDet(String t1, String userid);

	
}
