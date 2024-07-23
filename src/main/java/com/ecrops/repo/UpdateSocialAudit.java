package com.ecrops.repo;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class UpdateSocialAudit {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int updateSocialAuditDetails(Integer villcode, String activeYear, String wbldcode, String season, String cropyear, String [] bookingIds_org, String [] cropName_org, 
			String [] varietyName_org, String [] cr_no_org) {
		
		int executeUpdate = 0;
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		
		
		String partitionName = "cr_details_";
        if (Integer.parseInt(wbldcode) <= 9) {
            wbldcode = "0" + wbldcode;
        } 
        partitionName =partitionName+ season+wbldcode+cropyear;
     
        if(activeYear.equals(cropyear)){
        	partitionName="ecrop"+activeYear+"."+partitionName;
        }
        
//        String griev_comp = "Y";
        String updateQuery = "update " + partitionName + " set griev_comp='Y' where cr_dist_code=? and bookingid=? and cr_crop=? and "
        		+ "cr_no=? and cr_vcode=? and variety=?";
        Query updateSQL = entityManager.createNativeQuery(updateQuery);
        
        for (int j = 0; j < bookingIds_org.length; j++) {
//        	updateSQL.setParameter(1, griev_comp);
        	System.out.println(Integer.parseInt(wbldcode)+"  "+Integer.parseInt(bookingIds_org[j])+"  "
            +Integer.parseInt(cropName_org[j])+" "+cr_no_org[j]+" "+villcode+" "+
        			Integer.parseInt(varietyName_org[j]));
        	
        	updateSQL.setParameter(1, Integer.parseInt(wbldcode));
        	updateSQL.setParameter(2, Integer.parseInt(bookingIds_org[j]));
        	updateSQL.setParameter(3, Integer.parseInt(cropName_org[j]));
        	updateSQL.setParameter(4, cr_no_org[j]);
        	updateSQL.setParameter(5, villcode);
        	updateSQL.setParameter(6, Integer.parseInt(varietyName_org[j]));
        	executeUpdate = updateSQL.executeUpdate();
        }
		
		return executeUpdate;
		
	}
	
}
