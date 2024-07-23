package com.ecrops.partition;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetailsMaoIntf;
import com.ecrops.entity.SuperChk_rejReport;
import com.ecrops.util.MasterFunctions;

@Repository
@Transactional
public class CropBookingDetailsMaoIntfPartition {
	@Autowired
	MasterFunctions masterFunctions;	
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropBookingDetailsMaoIntf> getCropDetailsMao( 
			String wbdcode, String dcode,
			String wbmcode,String vcode,
			String cropyear,String crop) {
		
		
		String[] season = cropyear.split("@");
		String cseason = season[0];
		Integer Year = Integer.parseInt(season[1]);

		String part_key = "",part_key1="";
		
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = cseason + wbdcode + Year;
			System.out.println("part_key==========>"+part_key);
		} else {
			part_key = cseason + "0" + wbdcode + Year; 
			System.out.println("part_key==========>"+part_key);
		}
	       
        String   tableName ="cr_crop_det_new_v_" + part_key ;
        String tname = "cr_images_" + cseason + wbdcode + Year;  
        if (Year >= 2023 && !(Year==2023 && cseason.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + Year + "." + "cr_crop_det_new_v_"+ part_key;
			 tname = "ecrop"+Year+".cr_images_" + cseason + wbdcode + Year;  
		}
        
     
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select CAST(a.bookingid as character varying)as bookingid,a.oc_name,concat('XXXXXXX',substr(a.cr_farmeruid,9,4)) as cr_farmeruid,a.oc_fname,\r\n"
				+ "owner_tenant,cast(a.kh_no  as character varying)as kh_no, a.cr_sno,a.cropname,a.varietyname, cast(a.cr_mix_unmix_ext as character varying) as \r\n"
				+ "cr_mix_unmix_ext, cast(a.cr_sow_type as character varying) as cr_sow_type, a.cropnature,a.watersource,a.irrmethoddesc,\r\n"
				+ "cast(a.seed_production as character varying) as seed_production ,a.farmingtype,b.photo,a.cr_sow_date,a.dcode,a.mcode,a.cr_dist_code,a.cr_mand_code,a.cr_vcode,a.cr_year,a.cr_season,\r\n"
				+ "a.mobileno,a.age,a.email, a.tot_extent, a.cultivable_land,a.uncultivable_land,a.cr_month,    a.cr_crop,  \r\n"
				+ "a.cr_no,a.cr_w_draw, a.cr_irr_type,   a.wtr_tax,   a.longitude,   a.latitude,      a.uploaded,\r\n"
				+ " a.data_src  from "+tableName+" a, "+tname+" b  where a.dcode=? and a.cr_mand_code=? and a.cr_vcode=? \r\n"
				+ "and a.cr_year=? and a.cr_season=? and a.cr_crop=? and b.bookingid=a.bookingid and b.cr_crop=?   order by cr_vcode,kh_no,cr_sno,oc_name";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		insertQuery.setParameter(3, Integer.parseInt(vcode));
		insertQuery.setParameter(4, Year);
		insertQuery.setParameter(5, cseason);
		insertQuery.setParameter(6, Integer.parseInt(crop));
		insertQuery.setParameter(7, Integer.parseInt(crop));
		//insertQuery.setParameter(3, userid);
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<CropBookingDetailsMaoIntf> detailsEntities = new ArrayList<CropBookingDetailsMaoIntf>();
		

		for (Object[] row : detailsEntities1) {

			CropBookingDetailsMaoIntf entity = new CropBookingDetailsMaoIntf();
			
			entity.setBookingid((String) row[0].toString());
			entity.setOc_name((String) row[1].toString());
			entity.setCr_farmeruid((String) row[2].toString());
			entity.setOc_fname((String) row[3].toString());
			entity.setOwner_tenant((String) row[4].toString());
			entity.setKh_no((String) row[5].toString());
			entity.setCr_sno((String) row[6].toString());
			entity.setCropname((String) row[7].toString());
			entity.setVarietyname((String) row[8].toString());
			entity.setCr_mix_unmix_ext((String) row[9].toString());
			entity.setCr_sow_type((String) row[10].toString());
			entity.setCropnature((String) row[11].toString());
			entity.setWatersource((String) row[12].toString());
			entity.setIrrmethoddesc((String) row[13].toString());
			entity.setSeed_production((String) row[14].toString());
			entity.setFarmingtype((String) row[15].toString());
			entity.setPhoto((String) row[16]);
			entity.setCr_sow_date((Date) row[17]);
//			try {
//				entity.setPhoto(MasterFunctions.getCropImageMao( wbdcode,  (String) row[0],  cropyear,  cseason,  crop, String.valueOf(Year)));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				entity.setPhoto("");
//			}
			detailsEntities.add(entity);
	
		}
		
		return detailsEntities;

	}


}
