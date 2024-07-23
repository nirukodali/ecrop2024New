package com.ecrops.service;
import java.util.List;
import com.ecrops.entity.Rep_StatusOnSurveyNo_extModel;

public interface StatusonSurveyNo_extService {

List<Rep_StatusOnSurveyNo_extModel>	 getStatusonSurveyNo_Ext(String tname1,String tname2, String tname3,Integer vcode1);
	
}
