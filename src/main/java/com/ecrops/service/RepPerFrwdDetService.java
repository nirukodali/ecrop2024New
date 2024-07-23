package com.ecrops.service;

import java.util.List; 
import com.ecrops.entity.RepPerFrwdDetModel;

public interface RepPerFrwdDetService {
	 
	List <RepPerFrwdDetModel> getPerFrwdDet(String tab,String tab2, Integer vcode1);

}
