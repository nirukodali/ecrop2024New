package com.ecrops.service;

import java.util.ArrayList;

import java.util.List;

import com.ecrops.entity.AllocationSurveyNoModel;

import com.ecrops.projection.ActiveSeasonProjection;

public interface GetCropYearService {
	
	public List<ActiveSeasonProjection> getActiveSeason();
	public List<ActiveSeasonProjection> getAllSeason();

	public List<AllocationSurveyNoModel> getAllSurveryNos(String t1,Integer mcode,String userid, Integer cropyear1, String season);

	
}
