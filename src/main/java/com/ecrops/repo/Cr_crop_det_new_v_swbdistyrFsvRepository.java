package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cr_crop_det_new_v_swbdistyear;

@Repository
public class Cr_crop_det_new_v_swbdistyrFsvRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cr_crop_det_new_v_swbdistyear> getCropwise( String sescrpyear, String wbdist,String userid) {
        String season = sescrpyear.split("@")[0];
        String cropyear = sescrpyear.split("@")[1];
        String tableName1="cr_crop_det_new_v_" + season +wbdist + cropyear;
        String tableName2="supercheck_exceptions_" + season  + cropyear;
        System.out.println(wbdist.length()+"-------------"+wbdist);
        
        int year =Integer.parseInt(cropyear);
        if( year >=2023) {
        	//System.out.println("2023>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	 if(wbdist.length() == 1) {
                 tableName1 = "ecrop"+cropyear+".cr_crop_det_new_v_" + season + "0" + wbdist+ cropyear;
        	 tableName2="ecrop"+cropyear+".supercheck_exceptions_" + season  + cropyear;
        	 }
                if(wbdist.length() > 1) {
                	 tableName1 = "ecrop"+cropyear+".cr_crop_det_new_v_" + season + wbdist + cropyear;
                tableName2="ecrop"+cropyear+".supercheck_exceptions_" + season + cropyear;
                }
        }
        if(season.equalsIgnoreCase("S") || year<2023) {
        	System.out.println("2023<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        	 if(wbdist.length() == 1) {
        	 tableName1 = "cr_crop_det_new_v_" + season + "0" +wbdist+ cropyear;
        	 }
        	 if(wbdist.length() > 1) {
        		 tableName1 = "cr_crop_det_new_v_" + season +wbdist + cropyear;
        	 }
        	 tableName2="supercheck_exceptions_" + season  + cropyear;
        }
        
        
//        String tableName2="ecrop2023.supercheck_exceptions_" + season + cropyear;
//        if(wbdist.length() == 1)
//         tableName1 = "ecrop2023.cr_crop_det_new_v_" + season + "0" + wbdist+ cropyear;
//        if(wbdist.length() > 1)
//        	 tableName1 = "ecrop2023.cr_crop_det_new_v_" + season + wbdist + cropyear;
        System.out.println("Table Name: " + tableName1);

        String qry = "SELECT\r\n"
        		+ "    ekycfarmername,\r\n"
        		+ "    se.exception_type,\r\n"
        		+ "    a.cr_dist_code,\r\n"
        		+ "    a.cr_mand_code,\r\n"
        		+ "    oc_name,\r\n"
        		+ "    oc_fname,\r\n"
        		+ "    variety,\r\n"
        		+ "    mobileno,\r\n"
        		+ "    cr_sow_date,\r\n"
        		+ "    varietyname,\r\n"
        		+ "    bookingid,\r\n"
        		+ "    watersource AS wsrcdesc,\r\n"
        		+ "    cropname,\r\n"
        		+ "    a.dname,\r\n"
        		+ "    a.mname,\r\n"
        		+ "    a.vname,\r\n"
        		+ "    part_key,\r\n"
        		
        		+ "    a.cr_vcode,\r\n"
        		+ "    a.dcode,\r\n"
        		+ "    a.mcode,\r\n"
        		+ "    kh_no,\r\n"
        		+ "    cr_sno,\r\n"
        		+ "    a.cr_crop,\r\n"
        		+ "    cr_mix_unmix_ext,\r\n"
        		+ "    a.cr_no,\r\n"
        		+ "    cr_w_draw,\r\n"
//        		+ "    oc_name AS occupname,\r\n"
//        		+ "    oc_fname AS occupfname,\r\n"
        		+ "    occupant_extent\r\n"
        		+ "FROM \r\n"
        		+ "    " + tableName1 + " a\r\n"
        		+ "LEFT JOIN\r\n"
        		+ "    " + tableName2 + " se\r\n"
        		+ "ON \r\n"
        		+ "    a.exception_catg = se.exception_catg \r\n"
        		+ "WHERE \r\n"
        		+ "    a.cr_dist_code = '" + wbdist + "'\r\n"
        		+ "    AND supercheck_userid = '" + userid + "'\r\n"
        		+ "ORDER BY\r\n"
        		+ "    a.dname,\r\n"
        		+ "    a.mname,\r\n"
        		+ "    a.vname,\r\n"
        		+ "    cr_sno,\r\n"
        		+ "    kh_no;\r\n";
        		

        Query query = entityManager.createNativeQuery(qry);

        List<Object[]> result = query.getResultList();
        List<Cr_crop_det_new_v_swbdistyear> list = new ArrayList<>();

        for (Object[] bean : result) {
        	Cr_crop_det_new_v_swbdistyear crAuthDetails = new Cr_crop_det_new_v_swbdistyear();
        	
        	
        	crAuthDetails.setEkycfarmername((String) bean[0]);
        	crAuthDetails.setException_type((String) bean[1]);
        	crAuthDetails.setCr_dist_code((BigDecimal) bean[2]);
        	crAuthDetails.setCr_mand_code((BigDecimal) bean[3]);
        	crAuthDetails.setOc_name((String) bean[4]);
        	crAuthDetails.setOc_fname((String) bean[5]);
        	crAuthDetails.setVariety((int) bean[6]);
        	crAuthDetails.setMobileno((BigDecimal) bean[7]);
        	crAuthDetails.setCr_sow_date((Date) bean[8]);
        	crAuthDetails.setVarietyname((String) bean[9]);
        	crAuthDetails.setBookingid((int) bean[10]);
        	crAuthDetails.setWsrcdesc((String) bean[11]);
        	crAuthDetails.setCropname((String) bean[12]);
        	crAuthDetails.setDname((String) bean[13]);
        	crAuthDetails.setMname((String) bean[14]);
        	crAuthDetails.setVname((String) bean[15]);
        	crAuthDetails.setPart_key((String) bean[16]);
        	crAuthDetails.setCr_vcode((int) bean[17]);
        	crAuthDetails.setDcode((int) bean[18]);
        	crAuthDetails.setMcode((int) bean[19]);
        	crAuthDetails.setKh_no((BigDecimal) bean[20]);
        	crAuthDetails.setCr_sno((String) bean[21]);
        	crAuthDetails.setCr_crop((int) bean[22]);
        	crAuthDetails.setCr_mix_unmix_ext((BigDecimal) bean[23]);
        	crAuthDetails.setCr_no((String) bean[24]);
        	crAuthDetails.setCr_w_draw((BigDecimal) bean[25]);
        	crAuthDetails.setOccupant_extent((BigDecimal) bean[26]);
            list.add(crAuthDetails);
        }
        
        return list;
    }
}
