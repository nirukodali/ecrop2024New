package com.ecrops.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrops.config.RegularExpressionclassMethod;
import com.ecrops.dto.CultivatorDto;
import com.ecrops.dto.CultivatorEmbedableDto;
import com.ecrops.entity.Cultivator;
import com.ecrops.entity.CultivatorPeri;
import com.ecrops.repo.AddUpdateCultivatorRepo;
import com.ecrops.repo.CultivatorCompositeRepository;
import com.ecrops.repo.CultivatorPeriRepo;
import com.ecrops.repo.CultivatorRepository;
import com.ecrops.service.CultivatorService;

@Service
public class CultivatorServiceImpl implements CultivatorService {

	@Autowired
	private CultivatorRepository repo;

	@Autowired
	CultivatorCompositeRepository cultivatorCompositeRepository;
	
	@Autowired
	CultivatorPeriRepo periRepo;

	public List<Cultivator> listAll() {

		return repo.findAll();
	}

	public List<Cultivator> getCultivatorsByKathaNo(BigDecimal fromkhno, Integer crVcode, Integer cryear,	String crSeason) {
		Cultivator cultivator=new Cultivator();
		cultivator.setKhNo(fromkhno);
		cultivator.setCr_vcode(crVcode);
		cultivator.setCr_year(cryear);
		cultivator.setCr_season(crSeason);
		
		
		return repo.getCultivatorDetailsByKathaNo(cultivator.getKhNo(), cultivator.getCr_vcode(),cultivator.getCr_year(),cultivator.getCr_season());
	}
	
	public List<Cultivator> getCultivatorsByKathaNo(Cultivator cultivator) {
		
		
		return repo.getCultivatorDetailsByKathaNo(cultivator.getKhNo(), cultivator.getCr_vcode(),cultivator.getCr_year(),cultivator.getCr_season());
	}

//	@Override
//	public int updateOwnerOrEnjoerDetails(Cultivator cultivator) {
//System.out.println("-------------------------------"+cultivator.getBookingId());
//System.out.println("-------------------------------"+cultivator.getPart_key());
//		Optional<CultivatorDto> optionalDto = cultivatorCompositeRepository
//				.findById(new CultivatorEmbedableDto(cultivator.getBookingId(), cultivator.getPart_key()));
//		System.out.println("optionalDto--------------------------->"+optionalDto.toString());
//		System.out.println("------------------>"+optionalDto.get().getAadharNo());
//		System.out.println("------------------>"+optionalDto.get().getAnubhavadar_name());
//		CultivatorDto cultivatorDto = null;
//		if(optionalDto.isPresent()) {
//			cultivatorDto = optionalDto.get();
//		}
//		else {
//			
//			cultivatorDto=new CultivatorDto();
//			
//		}
//		
//		if (cultivator.getCultivatorType() != null && cultivator.getCultivatorType().equalsIgnoreCase("L")) {
//			cultivatorDto.setAadharNo(cultivator.getAadharNo());
//		}
//		//System.out.println(cultivator.getCr_vcode()+"-----"+cultivator.getCrSno()+"-----"+  cultivator.getKhNo()+"-----"+ cultivator.getAadharNo());
//		CultivatorDto cDtoResult = null;
//		String periStatus="N";
//		List<CultivatorPeri> periList=	periRepo.getPeri(cultivator.getCr_vcode(),cultivator.getCrSno(),  cultivator.getKhNo(), cultivator.getAadharNo());
//		RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
//		Boolean checkString= expressionclassMethod.checkstring(cultivator.getUpdatedby());
//		if(checkString ) {
//		if(periList.size()>0) {
//	
// periStatus =" crop " + periList.get(0).getCropname() + "," + "variety " + periList.get(0).getVarietyname() + "  booked in K-23 " + " Sy No " + periList.get(0).getCr_sno() + " khata no " + periList.get(0).getKh_no() + " name " 
//	+ periList.get(0).getOccupname() + " father name " + periList.get(0).getOccupfname()+ " in " + periList.get(0).getCr_mix_unmix_ext() + " (acres)" ;
//	//System.out.println("periList----------->"+periStatus);
//		}
//		
//	
//		cultivatorDto.setOccupantExtent(cultivator.getOccupantExtent());
//		//System.out.println(cultivator.getOccupantExtent()+"aadhaar-------->"+cultivator.getAadharNo());
//		cultivatorDto.setAadharNo(cultivator.getAadharNo());
//		cultivatorDto.setCultivatorType(cultivator.getCultivatorType());
//		cultivatorDto.setUpdatedby(cultivator.getUpdatedby());
//		cultivatorDto.setCult_updatedby(cultivator.getUpdatedby());
//		cultivatorDto.setCult_updateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		cultivatorDto.setPeri_status(periStatus);
//		cultivatorDto.setUpdateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		 cDtoResult = cultivatorCompositeRepository.saveAndFlush(cultivatorDto);
//		}
//		return cDtoResult != null ? 1 : 0;
//	}
	
//	@Override
//	public int updateCcrcDetails(Cultivator cultivator) {
//System.out.println(cultivator.getBookingId()+"----"+ cultivator.getPart_key());
//		Optional<CultivatorDto> optionalDto = cultivatorCompositeRepository
//				.findById(new CultivatorEmbedableDto(cultivator.getBookingId(), cultivator.getPart_key()));
//		CultivatorDto cultivatorDto = optionalDto.get();
//		if (cultivator.getCultivatorType() != null && cultivator.getCultivatorType().equalsIgnoreCase("L")) {
//			cultivatorDto.setAadharNo(cultivator.getAadharNo());
//		}
//		RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
//		Boolean checkString= expressionclassMethod.checkstring(cultivator.getUpdatedby());
//System.out.println(cultivator.getCr_vcode()+"------"+cultivator.getCrSno()+"------"+  cultivator.getKhNo()+"------"+ cultivator.getAadharNo());
//		String periStatus="N";
//		CultivatorDto cDtoResult = null ;
//		List<CultivatorPeri> periList=	periRepo.getPeri(cultivator.getCr_vcode(),cultivator.getCrSno(),  cultivator.getKhNo(), cultivator.getAadharNo());
//		if(checkString) {
//		if(periList.size()>0) {
//	
// periStatus =" crop " + periList.get(0).getCropname() + "," + "variety " + periList.get(0).getVarietyname() + "  booked in K-23 " + " Sy No " + periList.get(0).getCr_sno() + " khata no " + periList.get(0).getKh_no() + " name " 
//	+ periList.get(0).getOccupname() + " father name " + periList.get(0).getOccupfname()+ " in " + periList.get(0).getCr_mix_unmix_ext() + " (acres)" ;
//	//System.out.println("periList----------->"+periStatus);
//		}
//		cultivatorDto.setOccupantExtent(cultivator.getOccupantExtent());
//		cultivatorDto.setCultivatorType(cultivator.getCultivatorType());
//		cultivatorDto.setUpdatedby(cultivator.getUpdatedby());
//		cultivatorDto.setCult_updatedby(cultivator.getUpdatedby());
//		cultivatorDto.setCult_updateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		cultivatorDto.setPeri_status(periStatus);
//		cultivatorDto.setUpdateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		 cDtoResult = cultivatorCompositeRepository.saveAndFlush(cultivatorDto);
//		}
//		return cDtoResult != null ? 1 : 0;
//	}

/*	public Cultivator saveCultivatorsData(Cultivator cultivator) {
		cultivator.setUpdateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
		cultivator.setCult_updatedby(cultivator.getUpdatedby());
		cultivator.setCult_updateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
		cultivator.setSrno_userid(cultivator.getUpdatedby());

		return repo.save(cultivator);
	} */
	
//	 public Cultivator saveCultivatorsData(Cultivator cultivator) {
//		//System.out.println("cultivator--->"+cultivator.getRefBookingId());
//		Optional<CultivatorDto> ownerRecord = cultivatorCompositeRepository
//				.findById(new CultivatorEmbedableDto(cultivator.getRefBookingId(), cultivator.getPart_key()));
//		CultivatorDto ownerData = ownerRecord.get();
//		String periStatus="N";
//		List<CultivatorPeri> periList=	periRepo.getPeri(cultivator.getCr_vcode(),cultivator.getCrSno(),  cultivator.getKhNo(), cultivator.getAadharNo());
//		if(periList.size()>0) {
//	RegularExpressionclassMethod expressionclassMethod = new RegularExpressionclassMethod();
//	Boolean checkString= expressionclassMethod.checkstring(ownerData.getAnubhavadar_name());
//	Boolean checkString2= expressionclassMethod.checkstring(ownerData.getOcName());
//	Boolean checkString3= expressionclassMethod.checkstring(ownerData.getFatherName());
//	if(checkString && checkString2 && checkString3) {
//			periStatus =" crop " + periList.get(0).getCropname() + "," + "variety " + periList.get(0).getVarietyname() + "  booked in K-23 " + " Sy No " + periList.get(0).getCr_sno() + " khata no " + periList.get(0).getKh_no() + " name " 
//	+ periList.get(0).getOccupname() + " father name " + periList.get(0).getOccupfname()+ " in " + periList.get(0).getCr_mix_unmix_ext() + " (acres)" ;
//	//System.out.println("periList----------->"+periStatus);
//		}
//		}
//		cultivator.setRegno(ownerData.getRegno());
//		cultivator.setTotalExtent(ownerData.getTotalExtent());
//		
//		cultivator.setCr_wsno(ownerData.getCr_wsno());
//		cultivator.setGeo_reffered(ownerData.getGeo_reffered());
//		cultivator.setRec_id(ownerData.getRec_id());
//		System.out.println("RecIds---->>" + cultivator.getRec_id());
//		
//		cultivator.setVs_sel(ownerData.getVs_sel());
//		cultivator.setPeri_status(periStatus);
//		cultivator.setAnubhavadar_name(ownerData.getAnubhavadar_name());
////		cultivator.setOccupname(ownerData.getOccupname());
////		cultivator.setOccupfname(ownerData.getOccupfname());
//		cultivator.setOcName(ownerData.getOcName());
//		cultivator.setFatherName(ownerData.getFatherName());
//		cultivator.setCult_updateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		cultivator.setUpdateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
//		
//		cultivator.setData_src(ownerData.getData_src());
//		cultivator.setCr_tr_d_ext(ownerData.getCr_tr_d_ext());
//		cultivator.setCr_tr_i_ext(ownerData.getCr_tr_i_ext()); 
//		cultivator.setEmp_code(ownerData.getEmp_code());
//		cultivator.setMobileno(ownerData.getMobileno());
//		cultivator.setSjointoccupant(ownerData.getSjointoccupant());
//		cultivator.setAnubhavadarExtent(ownerData.getAnubhavadarExtent());
//		return repo.save(cultivator);
//	}



	@Override
	public int updateCultivatorDetails(Cultivator cultivator) {

		Optional<CultivatorDto> optionalDto = cultivatorCompositeRepository
				.findById(new CultivatorEmbedableDto(cultivator.getBookingId(), cultivator.getPart_key()));
		CultivatorDto cultivatorDto = optionalDto.get();
		cultivatorDto.setOcName(cultivator.getOcName());
		cultivatorDto.setFatherName(cultivator.getFatherName());
		cultivatorDto.setOccupantExtent(cultivator.getOccupantExtent());
		cultivatorDto.setAadharNo(cultivator.getAadharNo());
		cultivatorDto.setCultivatorType(cultivator.getCultivatorType());
		cultivatorDto.setUpdatedby(cultivator.getUpdatedby());
		cultivatorDto.setUpdateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
		cultivatorDto.setCult_updatedby(cultivator.getUpdatedby());
		cultivatorDto.setCult_updateon(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
		
		CultivatorDto cDtoResult = cultivatorCompositeRepository.save(cultivatorDto);

		return cDtoResult != null ? 1 : 0;
	}

	@Autowired
	AddUpdateCultivatorRepo rep;
	
	@Override
	public void deleteCultivatorDetails(Cultivator cultivator,HttpSession httpSession,String bookingid) {
		String dist_code = httpSession.getAttribute("wbdcode").toString();
		String crop_year = httpSession.getAttribute("ACTIVEYEAR").toString();
		String crop_season = httpSession.getAttribute("seasonActive").toString();
		String partitionName = "cr_booking_partition_";
		if (Integer.parseInt(dist_code) <= 9) {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + "0" + dist_code + crop_year;
		} else {
			partitionName = "ecrop" + crop_year + "." + partitionName + crop_season + dist_code + crop_year;
		}
		Optional<CultivatorDto> optionalDto = cultivatorCompositeRepository
				.findById(new CultivatorEmbedableDto(cultivator.getBookingId(), cultivator.getPart_key()));
		System.out.println("-------------"+optionalDto.get().getCultivatorType());
		if(optionalDto.get().getCultivatorType().equalsIgnoreCase("K"))
		cultivatorCompositeRepository.delete(optionalDto.get());
		else {
			rep.updateRecord(optionalDto.get(),partitionName,bookingid);
		}

	}

	public Float getAnubhavadarExtent(Cultivator cultivator) {
		Float anubhavadarExtent = repo.getAnubhavadarExtent(cultivator.getPart_key(), cultivator.getKhNo(),
				cultivator.getCr_vcode(), cultivator.getCrSno());
		anubhavadarExtent = anubhavadarExtent == null ? 0.0f : anubhavadarExtent;
		return anubhavadarExtent;
	}
	
//	public Float getTotalOccupantExtent(Cultivator cultivator) {
//		Float totalOccupantExtent = repo.getTotalOccupantExtent(cultivator.getPart_key(), cultivator.getKhNo(),
//				cultivator.getCr_vcode(), cultivator.getCrSno());
//		totalOccupantExtent = totalOccupantExtent == null ? 0.0f : totalOccupantExtent;
//		return totalOccupantExtent;
//	}

	
	
//	public Float getTotalOccupantExtent(Cultivator cultivator) {
//		Float totalOccupantExtent = repo.getTotalOccupantExtent(cultivator.getPart_key(), cultivator.getKhNo(),
//				cultivator.getCr_vcode(), cultivator.getCrSno());
//		totalOccupantExtent = totalOccupantExtent == null ? 0.0f : totalOccupantExtent;
//		return totalOccupantExtent;
//	}

	@Override
	public List<Cultivator> getCcrcDetailsByKathaNo(BigDecimal fromkhno, Integer crVcode, Integer cryear,	String crSeason) {
		Cultivator cultivator=new Cultivator();
		cultivator.setKhNo(fromkhno);
		cultivator.setCr_vcode(crVcode);
		cultivator.setCr_year(cryear);
		cultivator.setCr_season(crSeason);
		
		
		return repo.getCcrcDetailsByKathaNo(cultivator.getKhNo(), cultivator.getCr_vcode(),cultivator.getCr_year(),cultivator.getCr_season());
	}

	@Override
	public BigDecimal getTotalOccupantExtent(Cultivator cultivator) {
		// TODO Auto-generated method stub
		return null;
	}


}
