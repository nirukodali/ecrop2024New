package com.ecrops.partition;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;

import org.springframework.stereotype.Repository;


import com.ecrops.entity.RepPernnialMand;


@Repository
@Transactional
public class RepPernnialMandPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<RepPernnialMand> getPerrnniaDet( 
			 String dcode,
			 String wbmcode,
			 String vcode,
			 String cropyear,
			 String wbdcode,String cropid) {
		
		
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
		
        String   tableName ="cr_crop_det_new_v_" + part_key ;
     if(Year>=2023  && !(Year==2023 && cseason.equalsIgnoreCase("S"))) {
        tableName ="ecrop" + Year + "." +"cr_crop_det_new_v_" + part_key ;
     }
        
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select oc_name,oc_fname,owner_tenant,cast(kh_no as varchar), cr_sno,cropname,cast(cr_mix_unmix_ext as varchar), \r\n"
				+ "age,cast(mobileno as varchar),email,tot_extent, cultivable_land,\r\n"
				+ "uncultivable_land, cr_month,cr_sow_type,cr_crop, cr_no,cr_w_draw,cr_irr_type,wtr_tax,longitude,\r\n"
				+ "latitude, uploaded,data_src, bookingid, dcode,mcode,cr_dist_code,cr_mand_code,cr_vcode,cr_year,cr_season,  \r\n"
				+ "concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid from "+tableName+" where dcode=?\r\n"
				+ "and cr_mand_code=? and cr_vcode=? and cr_year=? and cr_season=? and cr_crop=? order by cr_vcode,kh_no,cr_sno,oc_name ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		insertQuery.setParameter(3, Integer.parseInt(vcode));
		insertQuery.setParameter(4, Year);
		insertQuery.setParameter(5, cseason);
		insertQuery.setParameter(6, Integer.parseInt((cropid)));
		
		
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<RepPernnialMand> detailsEntities = new ArrayList<RepPernnialMand>();
	

		for (Object[] row : detailsEntities1) {
			
			RepPernnialMand entity = new RepPernnialMand();
			entity.setOc_name((String) row[0].toString());
			entity.setOc_fname((String) row[1].toString());
			entity.setOwner_tenant((String) row[2].toString());
			entity.setKh_no((String) row[3].toString());
			entity.setCr_sno((String) row[4].toString());
			entity.setCropname((String) row[5].toString());
			entity.setCr_mix_unmix_ext((String) row[6].toString());
			entity.setAge((Integer) row[7]);
			String mobileno=(String) row[8];
			if(mobileno == null) {
				entity.setMobileno("0");
			}else {
				entity.setMobileno((String) row[8].toString());
			}
			detailsEntities.add(entity);
		}
		return detailsEntities;

	}

}
