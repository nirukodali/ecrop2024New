package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cr_details_syearFsr;

@Repository
public class Cr_details_swbdcodeyrFsrRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cr_details_syearFsr> getCropwise( String sescrpyear, String wbdcode,String userid) {
        String season = sescrpyear.split("@")[0];
        String cropyear = sescrpyear.split("@")[1];
        int year =Integer.parseInt(cropyear);
        String tableName="";
        System.out.println("wbdcode------------------"+wbdcode);
        if(wbdcode.length() == 1 && year >=2023) {
        	System.out.println("2023>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         tableName = "ecrop"+cropyear+".cr_details_" + season + "0" + wbdcode + cropyear;
        }
        if(wbdcode.length() > 1 && year >=2023) {
        	System.out.println("2023>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	 tableName = "ecrop"+cropyear+".cr_details_" + season + wbdcode + cropyear;
        }
        if(season.equalsIgnoreCase("S")) {
        	System.out.println("2023<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        	 tableName = "cr_details_" + season + wbdcode + cropyear;
        }
        System.out.println("Table Name: " + tableName);

        String qry = "SELECT \r\n"
        		+ "    superchk_remarks AS oremarks,\r\n"
        		+ "    occupname AS occup_name,	\r\n"
        		+ "    occupfname AS occup_fname,  \r\n"
        		+ "    bookingid,\r\n"
        		+ "    kh_no,   \r\n"
        		+ "    cr_sno,  \r\n"
        		+ "    cr_sow_date,\r\n"
        		+ "    cr_no, \r\n"
        		+ "    wbdname,\r\n"
        		+ "    wbmname,\r\n"
        		+ "    wbvname,\r\n"
        		+ "    cropname,\r\n"
        		+ "    varietyname,\r\n"
        		+ "    reason,\r\n"
        		+ "    CASE \r\n"
        		+ "        WHEN superchk_remarks = 'A' THEN 'Entry Found Correct' \r\n"
        		+ "        WHEN superchk_remarks = 'R' THEN 'Entry Found Incorrect'  \r\n"
        		+ "    END AS remarks\r\n"
        		+ "FROM \r\n"
        		+ "  "+  tableName + "  a\r\n"
        		+ "JOIN \r\n"
        		+ "    wbvillage_mst b ON a.cr_dist_code = b.wbdcode \r\n"
        		+ "                    AND a.cr_mand_code = b.wbmcode \r\n"
        		+ "                    AND a.cr_vcode = b.wbvcode\r\n"
        		+ "JOIN \r\n"
        		+ "    cropnames c ON a.cr_crop = c.cropid \r\n"
        		+ "JOIN \r\n"
        		+ "    cr_variety_master v ON a.variety = v.varietycode\r\n"
        		+ "JOIN \r\n"
        		+ "    authority_verify_reasons ar ON a.suprejreason = cast(ar.code as character varying) \r\n"
        		+ "WHERE \r\n"
        		+ "    superchk_remarks ='R' \r\n"
        		+ "    AND cr_dist_code ="+wbdcode+" \r\n"
        		+ "    AND a.cr_season ='"+season+"' \r\n"
        		+ "    AND a.cr_year ="+cropyear+" \r\n"
        		+ "    AND a.supercheck_userid = '"+userid+"' \r\n"
        		+ "ORDER BY \r\n"
        		+ "    wbmname,\r\n"
        		+ "    wbvname";

        Query query = entityManager.createNativeQuery(qry);

        List<Object[]> result = query.getResultList();
        List<Cr_details_syearFsr> list = new ArrayList<>();

        for (Object[] bean : result) {
        	Cr_details_syearFsr crAuthDetails = new Cr_details_syearFsr();
        	
        	crAuthDetails.setOremarks((String) bean[0]);
        	crAuthDetails.setOccupname((String) bean[1]);
        	crAuthDetails.setOccupfname((String) bean[2]);
        	crAuthDetails.setBookingid((int) bean[3]);
        	crAuthDetails.setKh_no((BigDecimal) bean[4]);
        	crAuthDetails.setCr_sno((String) bean[5]);
        	crAuthDetails.setCr_sow_date((Date) bean[6]);
        	crAuthDetails.setCr_no((String) bean[7]);
        	crAuthDetails.setWbdname((String) bean[8]);
        	crAuthDetails.setWbmname((String) bean[9]);
        	crAuthDetails.setWbvname((String) bean[10]);
        	crAuthDetails.setCropname((String) bean[11]);
        	crAuthDetails.setVarietyname((String) bean[12]);
        	crAuthDetails.setReason((String) bean[13]);
        	
            list.add(crAuthDetails);
        }
        
        return list;
    }
}
