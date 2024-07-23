package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.SuperChkUpdStatusModel;

public interface SuperChkUpdStatusService {

	List<SuperChkUpdStatusModel> getSuperChkUpdStatusdet(String partition,String supercheckupd,Integer wbdcode1,Integer wbmcode1,Integer vcode1 );

//	List<SuperChkUpdStatusModel> getSuperChkUpdStatusdet(String partition, String supercheckupd, Object println);
}
