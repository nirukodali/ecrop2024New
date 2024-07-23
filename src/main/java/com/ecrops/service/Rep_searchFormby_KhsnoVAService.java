package com.ecrops.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecrops.entity.Rep_SearchFormbyKhsnoVAModel;

@Service
public interface Rep_searchFormby_KhsnoVAService {
public List<Rep_SearchFormbyKhsnoVAModel> getsearchformbykhsnodet(String tab1,
		String  searchParam,Integer dcode,Integer vcode1,Integer year,String season,String surveyno,String khno,
		Integer mandalcode, String vcode);
	
	
	
}
