package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.RbkVroAuthPend;

public interface RbkVroAuthPendingService {

		List<RbkVroAuthPend> getVroAuthPendingDet(String t1, String userid);

}
