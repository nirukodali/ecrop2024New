package com.ecrops.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ecrops.entity.HoUserReg;
import com.ecrops.projection.HoDisplay;
import com.ecrops.projection.HoMapping;
import com.ecrops.projection.UnMappingProj;

public interface HoMandalService {

	public List<HoMapping> getMandal(Integer dcode);

	public List<HoMapping> getunmapMandal( Integer dcode);
	
	public List<HoDisplay> getMappedDetails(Integer dcode);
	
	public int addHoDetails(Integer district,Integer smcode,Integer hmcode);
	 
	//public List<HoUserReg>getBlockortehsil(Integer hmcode);
	 
	public List<HoMapping> usercheck(String hmcode);
	
	public int updatedetails(Integer district,Integer hmcode);
	
	public int insertdetails(Integer district,Integer hmcode);
	
	public List<UnMappingProj> getheadquarter(Integer dcode);
	
	public int deletedetails(Integer district,Integer hmcode,Integer mcode);
	
}
