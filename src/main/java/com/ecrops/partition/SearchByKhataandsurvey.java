package com.ecrops.partition;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetailsHO;
import com.ecrops.entity.Searchkhataandsurvey;
import com.ecrops.repo.SeasonCropBookedExtentRepo;
import com.ecrops.util.MasterFunctions;

@Repository
@Transactional
public class SearchByKhataandsurvey {
	@Autowired
	MasterFunctions masterFunctions;
	SeasonCropBookedExtentRepo  seasonCropBookedExtentRepo;
	@PersistenceContext
	private EntityManager entityManager;

	public List<Searchkhataandsurvey> getkhataandsurvey( 
			String wbdcode, String dcode,
			String wbmcode,String vcode,
			String mcode,String Mandal,
			String cropyear,String crop, String cropcode, String selected,String value) {		

		System.out.println("selected value----> "+selected);
		System.out.println(" value --->"+value);

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
		 String tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
	
         System.out.println(tab);
         
         if (seasonYear >=2023 ) {
             tab = "ecrop" + seasonYear + "." + tab;
         }
         else {
    		  tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
         }
         System.out.println("tab1:"+tab);
     	
     	String Sql = "select w.wbemname,w.wbvname,oc_name,oc_fname,owner_tenant,kh_no,cr_sno,cropname, mobileno,age from "+tab+" a "
     			+ "join wbvillage_mst w on a.dcode=w.dcode where a.dcode="+dcode+" and w.mcode="+mcode+" and a.cr_vcode="+vcode+" and"
     		    + " cr_year="+seasonYear+" and cr_season='"+seasonType+"' and uploaded='Y' ";
		
     	 if (selected.equals("survey")) {
     		Sql += "  and cr_sno= " + Integer.parseInt(value) + " order by kh_no, cr_sno, oc_name";
         } else if (selected.equals("khata")) {
        	 Sql += " and kh_no = " + Integer.parseInt(value) + " order by kh_no, cr_sno, oc_name";
        	 
         }
     	Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		 
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		List<Searchkhataandsurvey> detailsEntities = new ArrayList<Searchkhataandsurvey>();
		for (Object[] row : detailsEntities1) {

			Searchkhataandsurvey entity = new Searchkhataandsurvey();
			entity.setWbemname((String) row[0]);
			entity.setWbvname((String) row[1]);
			entity.setOc_name((String) row[2]);
			entity.setOc_fname((String) row[3]);
			entity.setOwner_tenant((Character) row[4]);
			entity.setKh_no((BigDecimal) row[5]);
			entity.setCr_sno((String) row[6]);
			entity.setCropname((String) row[7]);
			entity.setMobileno((BigDecimal) row[8]);
			entity.setAge((Integer)row[9]);
			detailsEntities.add(entity);		
		}  	 
        	 return detailsEntities;		      	 
	}
}
