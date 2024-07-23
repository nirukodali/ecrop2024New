package com.ecrops.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.AuthrbkvaavroekycModel;
import com.ecrops.entity.Rep_StatusOnSurveyNo_extModel;
import com.ecrops.service.StatusonSurveyNo_extService;
@Service
public class StatusonSuryveyNo_ExtServiceImpl implements StatusonSurveyNo_extService {
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<Rep_StatusOnSurveyNo_extModel> getStatusonSurveyNo_Ext(String tname1,
			String tname2, String tname3, Integer vcode1) {
		// TODO Auto-generated method stub
		
	String sql= "select t1.cr_vcode,t1.farmername,t1.occupname,t1.cr_sno,t1.kh_no,t1.tot_extent,t1.occup_extent,"
			+ " t2.oc_name as cr_booking_oc_name,t2.occupname as cr_booking_occupname,t2.cr_sno as cr_booking_cr_sno,CAST(t2.kh_no AS INTEGER) as cr_booking_kh_no,"
			+ "  CAST(t2.tot_extent AS INTEGER)as cr_booking_tot_extent, CAST(t2.occupant_extent AS INTEGER) as cr_booking_occupant_extent, CASE WHEN t2.owner_tenant='O' THEN 'OWNER' "
			+ "  WHEN t2.owner_tenant='K' THEN 'CULTIVATOR' END as cr_booking_owner_tenant,t2.data_src as cr_booking_data_src,"
			+ "  t3.oc_name as cr_details_oc_name,t3.occupname as cr_details_occupname,t3.cr_sno as cr_details_cr_sno,CAST(t3.kh_no AS INTEGER) as cr_details_kh_no,"
			+ " CAST(t3.tot_extent AS INTEGER) as cr_details_tot_extent,CAST(t3.occupant_extent AS INTEGER) as cr_details_occupant_extent, CASE WHEN t3.owner_tenant='O' THEN 'OWNER'"
			+ "  WHEN t3.owner_tenant='K' THEN 'CULTIVATOR' END as cr_details_owner_tenant,t3.data_src as cr_details_data_src"
			+ " from (select cr_vcode,farmername,occupname,cr_sno,kh_no,tot_extent,occup_extent from " + tname1 + " "
			+ " where cr_vcode=? and (kh_no not in (select code from obj_unobj)))t1"
			+ " left join"
			+ " (select cr_vcode,oc_name,occupname,cr_sno,kh_no,tot_extent,occupant_extent,owner_tenant,data_src "
			+ "  from " + tname2 + " where cr_vcode=? and cultivator_type is not null)t2"
			+ "  on t1.cr_vcode=t2.cr_vcode and t1.cr_sno=t2.cr_sno and t1.kh_no=t2.kh_no"
			+ "   left join"
			+ "  (select cr_vcode,oc_name,occupname,cr_sno,kh_no,tot_extent,occupant_extent,owner_tenant,data_src "
			+ "   from " + tname3 + " where cr_vcode=? and cultivator_type is not null)t3"
			+ "  on t3.cr_vcode=t2.cr_vcode and t3.cr_sno=t2.cr_sno and t3.kh_no=t2.kh_no"
			+ "   order by t1.cr_sno,t1.kh_no,t2.data_src";

	
	System.out.println("Query:::" +sql);
	Query insertQuery = (Query) entityManager.createNativeQuery(sql);
	insertQuery.setParameter(1, vcode1);
	insertQuery.setParameter(2, vcode1);
	insertQuery.setParameter(3, vcode1);

	
	
	System.out.println("insertQuery=>"+insertQuery);
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	
	List<Rep_StatusOnSurveyNo_extModel> list = new ArrayList<Rep_StatusOnSurveyNo_extModel>();

	for(Object[] row: detailsEntities1) {
		Rep_StatusOnSurveyNo_extModel statusOnSurveyNo_extModel = new Rep_StatusOnSurveyNo_extModel();
		statusOnSurveyNo_extModel.setFarmername((String) row[1].toString());
		statusOnSurveyNo_extModel.setOccupname((String)row[2].toString());
		statusOnSurveyNo_extModel.setCr_sno((String)row[3].toString());
		statusOnSurveyNo_extModel.setKh_no((String)row[4].toString());
		BigDecimal bd = new BigDecimal((String)row[5].toString());
	       
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        
     
        String roundedString = bd.toString();
        BigDecimal bds = new BigDecimal(row[6].toString());
	       
        bds = bds.setScale(2, RoundingMode.HALF_UP);
        
     
        String roundedStrings = bd.toString();
        
	statusOnSurveyNo_extModel.setTot_extent(roundedString);
	statusOnSurveyNo_extModel.setOccup_extent(roundedStrings);
		String bookingoc_name= (String)row[7];
		if(bookingoc_name == null) {
			statusOnSurveyNo_extModel.setCr_booking_oc_name("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_oc_name(row[7].toString());
		}
//		statusOnSurveyNo_extModel.setCr_booking_oc_name(row[7].toString());
		
		String booking_occupname = (String)row[8];
		if(booking_occupname == null) {
			statusOnSurveyNo_extModel.setCr_booking_occupname("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_occupname(row[8].toString());
		}

//		statusOnSurveyNo_extModel.setCr_booking_occupname(row[8].toString());
		String Cr_booking_cr_sno = (String)row[9];
		if(Cr_booking_cr_sno == null) {
			statusOnSurveyNo_extModel.setCr_booking_cr_sno("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_cr_sno(row[9].toString());
		}	
//		statusOnSurveyNo_extModel.setCr_booking_cr_sno(row[9].toString());
Integer book =(Integer)row[10];
		if(book == null) {
			statusOnSurveyNo_extModel.setCr_booking_kh_no("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_kh_no(book.toString());
		}	
		
		
		Integer Crbkingtotext = (Integer)row[11];
		if(Crbkingtotext == null) {
			statusOnSurveyNo_extModel.setCr_booking_tot_extent("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_tot_extent(Crbkingtotext.toString());
		}	
		
	
//		statusOnSurveyNo_extModel.setCr_booking_tot_extent(row[11].toString());
	
		Integer Crbkgoccupanext = (Integer)row[12];
		if(Crbkgoccupanext == null) {
			statusOnSurveyNo_extModel.setCr_booking_occupant_extent("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_occupant_extent(Crbkgoccupanext.toString());
		}	
	
//		statusOnSurveyNo_extModel.setCr_booking_occupant_extent(row[12].toString());
		
		String Cr_booking_owner_tenant = (String)row[13];
		if(Cr_booking_owner_tenant == null) {
			statusOnSurveyNo_extModel.setCr_booking_owner_tenant("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_owner_tenant(row[13].toString());
		}	
		
		
//		statusOnSurveyNo_extModel.setCr_booking_owner_tenant(row[13].toString());
		
		Character Crbkgdatasrc = (Character)row[14];
		if(Crbkgdatasrc == null) {
			statusOnSurveyNo_extModel.setCr_booking_data_src("");
		}else {
			statusOnSurveyNo_extModel.setCr_booking_data_src(Crbkgdatasrc.toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_booking_data_src(row[14].toString());
		
		String Cr_details_oc_name = (String)row[15];
		if(Cr_details_oc_name == null) {
			statusOnSurveyNo_extModel.setCr_details_oc_name("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_oc_name(row[15].toString());
		}	
			
		
	//	statusOnSurveyNo_extModel.setCr_details_oc_name(row[15].toString());
		
		String Cr_details_occupname = (String)row[16];
		if(Cr_details_occupname == null) {
			statusOnSurveyNo_extModel.setCr_details_occupname("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_occupname(row[16].toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_details_occupname(row[16].toString());
		
		String Cr_details_cr_sno = (String)row[17];
		if(Cr_details_cr_sno == null) {
			statusOnSurveyNo_extModel.setCr_details_cr_sno("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_cr_sno(row[17].toString());
		}	
		
		
//		statusOnSurveyNo_extModel.setCr_details_cr_sno(row[17].toString());
		
		Integer Crdetskhno = (Integer)row[18];
		if(Crdetskhno == null) {
			statusOnSurveyNo_extModel.setCr_details_kh_no("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_kh_no(Crdetskhno.toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_details_kh_no(row[18].toString());
		
		Integer Crdettotext = (Integer)row[19];
		if(Crdettotext == null) {
			statusOnSurveyNo_extModel.setCr_details_tot_extent("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_tot_extent(Crdettotext.toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_details_tot_extent(row[19].toString());
		
		Integer Crdetoccuptext = (Integer)row[20];
		if(Crdetoccuptext== null) {
			statusOnSurveyNo_extModel.setCr_details_occupant_extent("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_occupant_extent(Crdetoccuptext.toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_details_occupant_extent(row[20].toString());
		
		String Cr_details_owner_tenant = (String)row[21];
		if(Cr_details_owner_tenant == null) {
			statusOnSurveyNo_extModel.setCr_details_owner_tenant("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_owner_tenant(row[21].toString());
		}	
		
//		statusOnSurveyNo_extModel.setCr_details_owner_tenant(row[21].toString());
		
		String Crdetdatasrc = (String)row[22];
		if(Crdetdatasrc == null) {
			statusOnSurveyNo_extModel.setCr_details_data_src("");
		}else {
			statusOnSurveyNo_extModel.setCr_details_data_src(Crdetdatasrc.toString());
		}	

		
//		statusOnSurveyNo_extModel.setCr_details_data_src(row[22].toString());

		
		list.add(statusOnSurveyNo_extModel);

	}
		
		return list;
	}	
	
}
