package com.ecrops.partition;

import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetailsDhoIntf;
//import com.ecrops.entity.SuperChk_rejReport;
import com.ecrops.util.MasterFunctions;
 
@Repository
@Transactional
public class CropBookingDetailsDhoIntfPartition {
	@Autowired
	MasterFunctions masterFunctions;	
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropBookingDetailsDhoIntf> getCropDetailsDho( 
			String wbdcode, String dcode,
			String wbmcode,String vcode,
			String cropyear,String crop) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String cseason = season[0];
		System.out.println("cseason========="+cseason);
		Integer Year = Integer.parseInt(season[1]);
		System.out.println("Year========="+Year);

		String part_key = "",part_key1="";
		
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = cseason + wbdcode + Year;
			System.out.println("part_key==========>"+part_key);
		} else {
			part_key = cseason + "0" + wbdcode + Year; 
			System.out.println("part_key==========>"+part_key);
		}
	       
        String   tableName ="ecrop" + Year + "." +"cr_crop_det_new_v_" + part_key ;
     
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select CAST(bookingid as character varying)as bookingid,oc_name,concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,oc_fname,\r\n"
				+ "owner_tenant,cast(kh_no  as character varying)as kh_no, cr_sno,cropname,varietyname, cast(cr_mix_unmix_ext as character varying) as \r\n"
				+ "cr_mix_unmix_ext, cast(cr_sow_type as character varying) as cr_sow_type, cropnature,watersource,irrmethoddesc,\r\n"
				+ "cast(seed_production as character varying) as seed_production ,farmingtype,a.dcode,mcode,cr_dist_code,cr_mand_code,cr_vcode,cr_year,cr_season,\r\n"
				+ "mobileno,age,email, tot_extent, cultivable_land,uncultivable_land,cr_month,    cr_crop,  \r\n"
				+ "cr_no,cr_w_draw, cr_irr_type,   wtr_tax,   longitude,   latitude,cr_sow_date,      uploaded,\r\n"
				+ " data_src  from "+tableName+" a  where a.dcode=? and cr_mand_code=? and cr_vcode=? \r\n"
				+ "and cr_year=? and cr_season=? and cr_crop=?   order by cr_vcode,kh_no,cr_sno,oc_name limit 100 ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		insertQuery.setParameter(3, Integer.parseInt(vcode));
		insertQuery.setParameter(4, Year);
		insertQuery.setParameter(5, cseason);
		insertQuery.setParameter(6, Integer.parseInt(crop));
		//insertQuery.setParameter(3, userid);
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<CropBookingDetailsDhoIntf> detailsEntities = new ArrayList<CropBookingDetailsDhoIntf>();
		

		for (Object[] row : detailsEntities1) {

			CropBookingDetailsDhoIntf entity = new CropBookingDetailsDhoIntf();
			
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
			try {
				entity.setPhoto(masterFunctions.getCropImageMao( wbdcode,  (String) row[0],  cropyear,  cseason,  crop, String.valueOf(Year)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				entity.setPhoto("");
			}
			detailsEntities.add(entity);
	
		}
		
		return detailsEntities;

	}


}
