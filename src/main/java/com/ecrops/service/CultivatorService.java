package com.ecrops.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecrops.entity.Cultivator;

@Service
public interface CultivatorService {

	public List<Cultivator> listAll();

	public List<Cultivator> getCultivatorsByKathaNo(BigDecimal fromkhno, Integer crVcode, Integer cryear,String crSeason);
	
	public List<Cultivator> getCultivatorsByKathaNo(Cultivator cultivator);
	
	public List<Cultivator> getCcrcDetailsByKathaNo(BigDecimal fromkhno, Integer crVcode, Integer cryear,String crSeason);

//	public int updateOwnerOrEnjoerDetails(Cultivator cultivator);
	
//	public int updateCcrcDetails(Cultivator cultivator);

//	public Cultivator saveCultivatorsData(Cultivator cultivator);

	public int updateCultivatorDetails(Cultivator cultivator);

	public void deleteCultivatorDetails(Cultivator cultivator,HttpSession httpSession,String bookingid);

//	public Float getAnubhavadarExtent(Cultivator cultivator);

	public BigDecimal getTotalOccupantExtent(Cultivator cultivator);

}
