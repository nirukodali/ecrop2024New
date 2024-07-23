package com.ecrops.repo;

import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.Cr_authdetails_mand_mv_kyearFfekyc;

@Repository
public class Cr_authdetails_mand_mv_kyearFfekycRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cr_authdetails_mand_mv_kyearFfekyc> getCropwise(int dcode, String sescrpyear) {
        String season = sescrpyear.split("@")[0];
        String cropyear = sescrpyear.split("@")[1];
        String tableName =  "cr_authdetails_mand_mv_" + season + cropyear;
        if(season.equalsIgnoreCase("S") || season =="S") {
        	tableName= tableName;
        }
        else if(Integer.parseInt(cropyear)>2022) {
       tableName = "ecrop"+cropyear+"."+ "cr_authdetails_mand_mv_" + season + cropyear;
        }
        System.out.println("Table Name: " + tableName);

        String qry = "select distinct b.wbmname, x.totalbookings, x.totextent, x.vaaauthcount, x.vaaauthextent, " +
                        "x.vroauthcount, x.vroauthextent, x.totekycbookings, x.totfarmercount, x.ekycfarmercount, " +
                         "x.ekycbookedext " +
                     "from (select totalbookings, totextent, vaaauthcount, vaaauthextent, vroauthcount, vroauthextent, " +
                            "totekycbookings, totfarmercount, ekycfarmercount, ekycbookedext, cr_dist_code, cr_mand_code " +
                             "from " + tableName + ") x, wbvillage_mst b " +
                     "where x.cr_dist_code = b.wbdcode and x.cr_mand_code = b.wbmcode and b.dcode = ?";

        Query query = entityManager.createNativeQuery(qry);
        query.setParameter(1, dcode);

        List<Object[]> result = query.getResultList();
        List<Cr_authdetails_mand_mv_kyearFfekyc> list = new ArrayList<>();

        for (Object[] bean : result) {
        	Cr_authdetails_mand_mv_kyearFfekyc crAuthDetails = new Cr_authdetails_mand_mv_kyearFfekyc();
        	crAuthDetails.setWbmname((String) bean[0]);
        	crAuthDetails.setTotalbookings(((BigDecimal) bean[1]));
        	crAuthDetails.setTotextent((BigDecimal) bean[2]);
        	crAuthDetails.setVaaauthcount(((BigDecimal) bean[3]));
        	crAuthDetails.setVaaauthextent((BigDecimal) bean[4]);
        	crAuthDetails.setVroauthcount(((BigDecimal) bean[5]));
        	crAuthDetails.setVroauthextent((BigDecimal) bean[6]);
        	crAuthDetails.setTotekycbookings(((BigDecimal) bean[7]));
        	crAuthDetails.setTotfarmercount(((BigDecimal) bean[8]));
        	crAuthDetails.setEkycfarmercount(((BigDecimal) bean[9]));
        	crAuthDetails.setEkycbookedext((BigDecimal) bean[10]);
            list.add(crAuthDetails);
        }
        
        return list;
    }
}
