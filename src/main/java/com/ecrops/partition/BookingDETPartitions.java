package com.ecrops.partition;

import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingDET;
//import com.ecrops.entity.SuperCheckIntf;

@Repository
@Transactional
public class BookingDETPartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<BookingDET> booking(String cropyear,String wbdcode,String wbmcode,int a,String cropid,String wbvcode){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int vcode= Integer.parseInt(wbvcode);
		int activeYear = a;

		String part_key = "",tableName;
		
		part_key = seasonType + seasonYear;
		
		if (seasonYear>=2023) {
        	tableName = "ecrop" + seasonYear + "." + "cr_booking_det_" + part_key;
        }
        else {
        	tableName = "cr_booking_det_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String sql = " select lgdvname,vname,cast(bookingid as varchar),occupname,\r\n"
				+ "occupfname,cast(kh_no as varchar),cast(cr_sno as varchar),\r\n"
				+ "cropname,varietyname,cast(cr_mix_unmix_ext as varchar),\r\n"
				+ "cast(seed_production as varchar) from "+tableName  +" \r\n"
				+ "where wbdcode=? and wbmcode=? and wbvcode=? and cr_crop=?";

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1, Integer.parseInt(wbdcode));
		sesnyr.setParameter(2, Integer.parseInt(wbmcode));
		sesnyr.setParameter(3, vcode);
		sesnyr.setParameter(4, Integer.parseInt(cropid));

		System.out.println("sesnyr=>" + sesnyr);

		List<Object[]> superchk = sesnyr.getResultList();
		List<BookingDET> entityDetails = new ArrayList<BookingDET>();

		for (Object[] row : superchk) {

			BookingDET entity = new BookingDET();


			entity.setLgdvnam((String) row[0]);
			entity.setVname((String) row[1]);
			entity.setBookingid((String) row[2]);
			entity.setOccupname((String) row[3]);
			entity.setOccupfname((String) row[4]);
			entity.setKh_no((String) row[5]);
			entity.setCr_sno((String) row[6]);
			entity.setCropname((String) row[7]);
			entity.setVarietyname((String) row[8]);
			entity.setCr_mix_unmix_ext((String) row[9]);
			entity.setSeed_production((String) row[10]);

			entityDetails.add(entity);

		}

		return entityDetails;

	}

}

