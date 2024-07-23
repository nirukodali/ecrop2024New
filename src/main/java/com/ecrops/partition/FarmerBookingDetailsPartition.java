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

import com.ecrops.entity.FarmerCropBookingDetailsEntity;
import com.ecrops.util.MasterFunctions;

@Repository
@Transactional
public class FarmerBookingDetailsPartition {
	@Autowired
	MasterFunctions masterFunctions;	
	@PersistenceContext
	private EntityManager entityManager;

	public List<FarmerCropBookingDetailsEntity> getFarmerwisecropbooking( 
			String wbdcode, String dcode,
			String mcode,String vcode,
			String cropyear,String cropid) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String part_key = "";

		part_key = seasonType + seasonYear;
		System.out.println("part_key==========>" + part_key);
		
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;
			System.out.println("part_key==========>"+part_key);
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear; 
			System.out.println("part_key==========>"+part_key);
		}
		String tableName = "cr_crop_det_new_v_" + part_key ;
		
	    if (seasonYear == 2023 && seasonType.equals("S")) {
          tableName ="cr_crop_det_new_v_" + part_key ;
	    }
	    else if (seasonYear>=2023) {  
	    tableName ="ecrop" + seasonYear + "." +"cr_crop_det_new_v_" + part_key ; 
	    }
	    else {
	    	tableName ="cr_crop_det_new_v_" + part_key ;
	    }
		System.out.println("tableName---------------->" + tableName);

		String Sql ="SELECT bookingid,wbemname,wbvname,oc_name,CONCAT('XXXXXXX', SUBSTR(cr_farmeruid, 9, 4)) AS cr_farmeruid,oc_fname,owner_tenant,\r\n"
				+ "kh_no,cr_sno,cropname,varietyname,cr_mix_unmix_ext,cr_sow_date,cropnature,watersource,irrmethoddesc FROM "+tableName+" a\r\n"
				+ "JOIN wbvillage_mst w ON a.dcode = w.dcode AND a.mcode = w.mcode AND a.cr_vcode = w.wbvcode WHERE a.dcode ="+dcode+" \r\n"
				+ "AND a.mcode ="+mcode+" AND a.cr_vcode ="+vcode+" AND cr_year ="+seasonYear+" AND cr_season ='"+seasonType+"' AND cr_crop ="+cropid+" \r\n"
				+ "ORDER BY cr_vcode, kh_no, cr_sno, oc_name\r\n"
				+ "";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
				
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		List<FarmerCropBookingDetailsEntity> detailsEntities = new ArrayList<FarmerCropBookingDetailsEntity>();
		

		for (Object[] row : detailsEntities1) {

			FarmerCropBookingDetailsEntity entity = new FarmerCropBookingDetailsEntity();
			
			entity.setBookingid((Integer) row[0]);
			entity.setWbemname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setOc_name((String) row[3].toString());
			entity.setCr_farmeruid((String) row[4].toString());
			entity.setOc_fname((String) row[5].toString());
			entity.setOwner_tenant((String) row[6].toString());
			entity.setKh_no((String) row[7].toString());
			entity.setCr_sno((String) row[8].toString());
			entity.setCropname((String) row[9].toString());
			entity.setVarietyname((String) row[10].toString());
			entity.setCr_mix_unmix_ext((String) row[11].toString());
			entity.setCr_sow_date((String) row[12].toString());
			entity.setCropnature((String) row[13].toString());
			entity.setWatersource((String) row[14].toString());
			entity.setIrrmethoddesc((String) row[15].toString());
			try {
				entity.setPhoto(masterFunctions.getCropImageMao1( wbdcode,  
						(Integer) row[0],  
						cropyear,  
						seasonType,  
						cropid,
						seasonYear));
			} catch (SQLException e) {
				e.printStackTrace();
				entity.setPhoto("");
			}
			detailsEntities.add(entity);
	
		}
		
		return detailsEntities;

	}


}
