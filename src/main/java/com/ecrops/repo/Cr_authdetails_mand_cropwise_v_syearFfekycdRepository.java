package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cr_authdetails_mand_cropwise_v_syearFfekycd;

@Repository
public class Cr_authdetails_mand_cropwise_v_syearFfekycdRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cr_authdetails_mand_cropwise_v_syearFfekycd> getCropwise(int dcode,String sescrpyear,int cropid) {
        String season = sescrpyear.split("@")[0];
        String cropyear = sescrpyear.split("@")[1];
        int year =Integer.parseInt(cropyear);
        String tableName = "cr_authdetails_mand_cropwise_v_" + season + cropyear;
        if( year >=2023) {
        	System.out.println("2023>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         tableName = "ecrop"+cropyear+".cr_authdetails_mand_cropwise_v_" + season + cropyear;
        }
        if(season.equalsIgnoreCase("S")) {
        	System.out.println("2023<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        	 tableName = "cr_authdetails_mand_cropwise_v_" + season  + cropyear;
        }
        System.out.println("Table Name: " + tableName);

        String qry = "select distinct b.wbdname, b.wbmname, x.totalbookings, x.totextent, x.vaaauthcount, x.vaaauthextent, " +
                "x.vroauthcount, x.vroauthextent, x.totekycbookings, x.totfarmercount, x.ekycfarmercount, " +
                 "x.ekycbookedext " +
             "from (select totalbookings, totextent, vaaauthcount, vaaauthextent, vroauthcount, vroauthextent, " +
                    "totekycbookings, totfarmercount, ekycfarmercount, ekycbookedext, cr_dist_code, cr_mand_code,cr_crop " +
                     "from " + tableName + ") x, wbvillage_mst b " +
             "where x.cr_dist_code = b.wbdcode and x.cr_mand_code = b.wbmcode and b.dcode = ? and x.cr_crop= ?";
        
        Query query = entityManager.createNativeQuery(qry);
        query.setParameter(1, dcode);
        query.setParameter(2, cropid);

        List<Object[]> result = query.getResultList();
        List<Cr_authdetails_mand_cropwise_v_syearFfekycd> list = new ArrayList<>();

        for (Object[] bean : result) {
        	Cr_authdetails_mand_cropwise_v_syearFfekycd crAuthDetails = new Cr_authdetails_mand_cropwise_v_syearFfekycd();
        	crAuthDetails.setWbmname((String) bean[0]);
        	crAuthDetails.setWbdname((String) bean[1]);
        	crAuthDetails.setTotalbookings(((BigDecimal) bean[2]).intValue());
        	crAuthDetails.setTotextent((BigDecimal) bean[3]);
        	crAuthDetails.setVaaauthcount(((BigDecimal) bean[4]).intValue());
        	crAuthDetails.setVaaauthextent((BigDecimal) bean[5]);
        	crAuthDetails.setVroauthcount(((BigDecimal) bean[6]).intValue());
        	crAuthDetails.setVroauthextent((BigDecimal) bean[7]);
        	crAuthDetails.setTotekycbookings(((BigDecimal) bean[8]).intValue());
        	crAuthDetails.setTotfarmercount(((BigDecimal) bean[9]).intValue());
        	crAuthDetails.setEkycfarmercount(((BigDecimal) bean[10]).intValue());
        	crAuthDetails.setEkycbookedext((BigDecimal) bean[11]);
            list.add(crAuthDetails);
        }
        
        return list;
    }
}
