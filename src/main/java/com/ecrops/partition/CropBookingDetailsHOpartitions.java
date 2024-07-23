package com.ecrops.partition;
 
import java.math.BigDecimal; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetailsHO;
import com.ecrops.repo.SeasonCropBookedExtentRepo;
import com.ecrops.util.MasterFunctions;

@Repository
@Transactional
public class CropBookingDetailsHOpartitions {
	@Autowired
	MasterFunctions masterFunctions;
	SeasonCropBookedExtentRepo  seasonCropBookedExtentRepo;
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropBookingDetailsHO> getCropDetailsHO( 
			String wbdcode, String dcode,
			String wbmcode,String vcode,
			String mcode,String Mandal,
			String cropyear,String crop, String cropcode) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		
		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
		 String tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
         System.out.println(tab);
         
         if (seasonYear>=2023 ) {
             tab = "ecrop" + seasonYear + "." + tab;
         }
         System.out.println("tab1:"+tab);
     	String Sql = null;
        	 if (cropcode != "" && cropcode != null) { 
        		 System.out.println("enter ed ifff");


//		 Sql =  "select  bookingid,   dcode,mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
//                + "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
//                + "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,"
//                + "      uploaded,  cropname, data_src  from " + tab + " where dcode="+dcode+" and cr_mand_code="+mcode+" and cr_vcode="+vcode+" and cr_year= "+seasonYear+" and cr_season='"+seasonType+"' and cr_crop="+cropcode+" order by cr_vcode,kh_no,cr_sno,oc_name ";
        	 
        	 Sql = " select wbemname,wbvname,oc_name,oc_fname,owner_tenant,kh_no,cr_sno,cropname,cr_mix_unmix_ext, mobileno,age from "+tab+" a join wbvillage_mst w on a.dcode=w.dcode and a.mcode=w.mcode and a.cr_vcode=w.wbvcode where a.dcode="+dcode+" and a.mcode="+mcode+" and a.cr_vcode="+vcode+" and cr_year= "+seasonYear+" and cr_season='"+seasonType+"' and cr_crop="+cropcode+" order by cr_vcode,kh_no,cr_sno,oc_name";
        	 }      
        	 else {
        		 
        		 System.out.println("enter else");

//        		  Sql = "select  bookingid,   dcode,mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
//                         + "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
//                         + "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,"
//                         + "      uploaded,  cropname, data_src  from " + tab + " where dcode="+dcode+"and cr_mand_code="+mcode+" and cr_vcode="+vcode+"and cr_year="+seasonYear+"and cr_season='"+seasonType+"'  order by  kh_no,cr_sno,oc_name ";
            
        		 Sql = " select wbemname,wbvname,oc_name,oc_fname,owner_tenant,kh_no,cr_sno,cropname,cr_mix_unmix_ext, mobileno,age from "+tab+" a join wbvillage_mst w on a.dcode=w.dcode and a.mcode=w.mcode and a.cr_vcode=w.wbvcode where a.dcode="+dcode+" and a.mcode="+mcode+" and a.cr_vcode="+vcode+" and cr_year= "+seasonYear+" and cr_season='"+seasonType+"' order by cr_vcode,kh_no,cr_sno,oc_name";

        	 }
		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		
        	 
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<CropBookingDetailsHO> detailsEntities = new ArrayList<CropBookingDetailsHO>();
		for (Object[] row : detailsEntities1) {

			CropBookingDetailsHO entity = new CropBookingDetailsHO();
		
			entity.setWbemname((String) row[0].toString());
			entity.setWbvname((String) row[1].toString());
			entity.setOc_name((String) row[2].toString());
			entity.setOc_fname((String) row[3].toString());
			entity.setOwner_tenant((String) row[4].toString());
			entity.setKh_no((String) row[5].toString());
			entity.setCr_sno((String) row[6].toString());
			entity.setCropname((String) row[7].toString());
			entity.setCr_mix_unmix_ext((String) row[8].toString());
			entity.setMobileno((BigDecimal) row[9]);
			entity.setAge((Integer)row[10]);
			detailsEntities.add(entity);
		
		}
        	 
        	 
			return detailsEntities;
		

	
        	 
	}

}
