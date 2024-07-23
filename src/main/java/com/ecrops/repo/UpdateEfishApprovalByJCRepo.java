package com.ecrops.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateEfishApprovalByJCRepo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Transactional
	public String updateEFishDetailsByJC(String dcode, String RecIdOrg, String OccupantNameOrg, String OccupantFNameOrg, String KhathaNoOrg, String SurveyNoOrg, 
			String OccupantExtentOrg, String MappedExtentOrg, String AllowableExtentOrg, String ReqExtentOrg, 
			String RemarksOrg, String apprStatus, String DfoSugExtentOrg, String vCode, HttpSession session) {
		
		int executeUpdate = 0, executeUpdate1 = 0, executeUpdate2 = 0, executeUpdate3 = 0;   // seasonActive
		int distCode = Integer.parseInt(dcode);
		
		Integer activeYear = (Integer) session.getAttribute("ACTIVEYEAR");
		String season = (String) session.getAttribute("seasonActive");
		
		String Status = "";
		String partitionName = "cr_details_efish_";
		
		String bookingTable = "cr_booking_partition_";
		String surveyNumTable = "rbk_surveyno_mapping_";
		String pattadarTable = "pattadarmast_wb_partition_";
		
        
//        if(activeYear.equals("2024")){
        	partitionName = "ecrop"+activeYear+"."+partitionName+activeYear;
        	if(distCode <= 9) {
        		bookingTable = "ecrop"+activeYear+"."+bookingTable+season+"0"+distCode+activeYear;
        		surveyNumTable = "ecrop"+activeYear+"."+surveyNumTable+season+"0"+distCode+activeYear;
        		pattadarTable = "ecrop"+activeYear+"."+pattadarTable+season+"0"+distCode+activeYear;
        	} else {
        		bookingTable = "ecrop"+activeYear+"."+bookingTable+season+distCode+activeYear;
        		surveyNumTable = "ecrop"+activeYear+"."+surveyNumTable+season+distCode+activeYear;
        		pattadarTable = "ecrop"+activeYear+"."+pattadarTable+season+distCode+activeYear;
        	}
        	
//        } else {
//        	partitionName = partitionName+activeYear;
//        	if(distCode <= 9) {
//        		bookingTable = bookingTable+season+"0"+distCode+activeYear;
//        		surveyNumTable = surveyNumTable+season+"0"+distCode+activeYear;
//        		pattadarTable = pattadarTable+season+"0"+distCode+activeYear;
//        	} else {
//        		bookingTable = bookingTable+season+distCode+activeYear;
//        		surveyNumTable = surveyNumTable+season+distCode+activeYear;
//        		pattadarTable = pattadarTable+season+distCode+activeYear;
//        	}
//        	
//        }
        
        if (apprStatus.equals("A")) {
    		
    		if (isDataPresentInSurveyTable(surveyNumTable, SurveyNoOrg, Integer.parseInt(vCode), Integer.parseInt(KhathaNoOrg))) {
    			
    			String updateSurveyQuery = "update "+surveyNumTable+" set occup_extent=?"    //jc_appr_sts,jc_ts,jc_remarks
    	        		+ "where vcode=? and kh_no=? and cr_sno =?";
    	        Query updateSQL1 = entityManager.createNativeQuery(updateSurveyQuery);
    	        
    	        if(Double.parseDouble(DfoSugExtentOrg) > 0) {
            		updateSQL1.setParameter(1, new BigDecimal(DfoSugExtentOrg));
            	} else {
            		updateSQL1.setParameter(1, new BigDecimal(ReqExtentOrg));
            	}
    	        updateSQL1.setParameter(2, Integer.parseInt(vCode));
    	        updateSQL1.setParameter(3, Integer.parseInt(KhathaNoOrg));
    	        updateSQL1.setParameter(4, SurveyNoOrg);
    	        
    	        executeUpdate1 = updateSQL1.executeUpdate();
    			
    		} else if (isDataPresentInBookingTable(bookingTable, SurveyNoOrg, Integer.parseInt(vCode), Integer.parseInt(KhathaNoOrg))) {
    			
    			String updateBookingQuery = "update "+bookingTable+" set occup_extent=?"    
    	        		+ "where cr_vcode=? and kh_no=? and cr_sno =?";
    	        Query updateSQL2 = entityManager.createNativeQuery(updateBookingQuery);
    	        
    	        if(Double.parseDouble(DfoSugExtentOrg) > 0) {
            		updateSQL2.setParameter(1, new BigDecimal(DfoSugExtentOrg));
            	} else {
            		updateSQL2.setParameter(1, new BigDecimal(ReqExtentOrg));
            	}
    	        updateSQL2.setParameter(2, Integer.parseInt(vCode));
    	        updateSQL2.setParameter(3, Integer.parseInt(KhathaNoOrg));
    	        updateSQL2.setParameter(4, SurveyNoOrg);
    	        
    	        executeUpdate2 = updateSQL2.executeUpdate();
    	        
    		} else {
    			
    			String updatePattadarQuery = "update "+pattadarTable+" set req_ext=?"      
    	        		+ "where cr_vcode=? and kh_no=? and cr_sno =?";
    	        Query updateSQL3 = entityManager.createNativeQuery(updatePattadarQuery);
    	        
    	        if(Double.parseDouble(DfoSugExtentOrg) > 0) {
            		updateSQL3.setParameter(1, new BigDecimal(DfoSugExtentOrg));
            	} else {
            		updateSQL3.setParameter(1, new BigDecimal(ReqExtentOrg));
            	}
    	        updateSQL3.setParameter(2, Integer.parseInt(vCode));
    	        updateSQL3.setParameter(3, Integer.parseInt(KhathaNoOrg));
    	        updateSQL3.setParameter(4, SurveyNoOrg);
    	        
    	        executeUpdate3 = updateSQL3.executeUpdate();
    	        
    		}

        }
        
		
        String updateQuery = "update "+partitionName+" set jc_ts=now(), jc_appr_sts=?, allowable_ext = ?, booking_available = ?, jc_remarks=?"    
        		+ "where dist_code=? and recid=? and cr_sno=? and kh_no=?";   // and mand_code=? 
        Query updateSQL = entityManager.createNativeQuery(updateQuery);

        if (apprStatus.equals("A")) {
        	updateSQL.setParameter(1, apprStatus);
        	if(Double.parseDouble(DfoSugExtentOrg) > 0) {
        		updateSQL.setParameter(2, new BigDecimal(DfoSugExtentOrg));
        	} else {
        		updateSQL.setParameter(2, new BigDecimal(ReqExtentOrg));
        	}
        	
        	updateSQL.setParameter(3, 'Y');
        	
        } else if (apprStatus.equals("R")) {
        	updateSQL.setParameter(1, apprStatus);
        	
        	updateSQL.setParameter(2, AllowableExtentOrg);
        	updateSQL.setParameter(3, null);
        }
        
		updateSQL.setParameter(4, RemarksOrg);
		updateSQL.setParameter(5, dcode);
//		updateSQL.setParameter(6, wbmcode);
		updateSQL.setParameter(6, Integer.parseInt(RecIdOrg));
		updateSQL.setParameter(7, SurveyNoOrg.trim());
		updateSQL.setParameter(8, KhathaNoOrg.trim());
       	
       	 executeUpdate = updateSQL.executeUpdate();
       	 
       	 if(executeUpdate1 > 0) {
       		Status = "Updated the Requested Extent For eFish and Also Updated the Anubhavadar Extent in Survey Number Mapping";
       	 } else if (executeUpdate2 > 0) {
       		Status = "Updated the Requested Extent For eFish and Also Updated the Anubhavadar Extent in Booking";
       	 } else if (executeUpdate3 > 0) {
       		Status = "Updated the Requested Extent For eFish and Also Updated the Anubhavadar Extent in Pattadar Master";
       	 } else if (executeUpdate > 0){
       		Status = "Updated the Requested Extent For eFish";
       	 } else {
       		Status = "Failed to Update";
       	 }
        
		return Status;
		
	}
	
	public boolean isDataPresentInSurveyTable(String surveyNumTable, String SurveyNoOrg, Integer vCode, Integer KhathaNoOrg) {
        String sql = "select COUNT(*) from "+surveyNumTable+" where vcode="+vCode+" and kh_no="+KhathaNoOrg+" and cr_sno ='"+SurveyNoOrg+"'";
        		//"SELECT COUNT(*) FROM other_table WHERE column_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] {}, Integer.class);
        return count != null && count > 0;
    }
	
	public boolean isDataPresentInBookingTable(String bookingTable, String SurveyNoOrg, Integer vCode, Integer KhathaNoOrg) {
        String sql = "select COUNT(*) from "+bookingTable+" where cr_vcode="+vCode+" and kh_no="+KhathaNoOrg+" and cr_sno ='"+SurveyNoOrg+"'";
        		//"SELECT COUNT(*) FROM other_table WHERE column_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] {}, Integer.class);
        return count != null && count > 0;
    }

}
