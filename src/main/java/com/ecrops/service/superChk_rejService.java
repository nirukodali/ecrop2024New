package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.SuperChk_rejReportModel;

public interface superChk_rejService {

	public	List<SuperChk_rejReportModel> getsuperchkrejdet(String superchkrejmv,Integer wbdcode1,Integer wbmcode1,Integer rbkcode);

}
