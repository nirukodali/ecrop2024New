package com.ecrops.service;

import java.util.List;

import com.ecrops.entity.SurveyNodetIntfModel;

public interface RepSurveyNodetIntfService {
	
	public List<SurveyNodetIntfModel> getSurveyNodet(String table, String tab2,String rbksrnoMapTab, String userid, Integer vcode1, String choiceId) ;

}
