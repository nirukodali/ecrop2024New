package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetailsPhotoDDAP;
import com.ecrops.entity.FarmerDeatilsEcropIntf;

@Repository
@Transactional
public class CropBookingDetailsPhotoDDAPPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropBookingDetailsPhotoDDAP> cropphoto(String cropyear, String dcode, String mcode, String vcode,
			String wbdcode,String cropid) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String part_key = "", tableName = "";

		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + Integer.parseInt(wbdcode) + seasonYear;
		} else {
			part_key = seasonType + "0" + Integer.parseInt(wbdcode) + seasonYear;
		}

		if (seasonYear >= 2023) {
			tableName = "ecrop" + seasonYear + "." + "cr_crop_det_new_v_" + part_key;
		} else {
			tableName = "cr_crop_det_new_v_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select cast(bookingid as varchar),oc_name,data_src,cr_farmeruid,oc_fname,cast(kh_no as varchar),"
				+ "cr_sno,cropname,varietyname,cast(cr_mix_unmix_ext as varchar),cast(cr_sow_date as varchar),cropnature,watersource,irrmethoddesc\r\n"
				+ "from "+ tableName +"  a where a.dcode=? and mcode=? and cr_vcode=? \r\n"
				+ "and cr_year=? and cr_season=? and cr_crop=? order by oc_name,cr_vcode,kh_no,cr_sno  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, Integer.parseInt(vcode));
		insertQuery.setParameter(4, seasonYear);
		insertQuery.setParameter(5, seasonType);
		insertQuery.setParameter(6, Integer.parseInt(cropid));
		// System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<CropBookingDetailsPhotoDDAP> detailsEntities = new ArrayList<CropBookingDetailsPhotoDDAP>();

		for (Object[] row : detailsEntities1) {

			CropBookingDetailsPhotoDDAP entity = new CropBookingDetailsPhotoDDAP();
			entity.setBookingid((String) row[0]);
			entity.setOc_name((String) row[1]);
			entity.setData_src((String) row[2]);
			entity.setCr_farmeruid((String) row[3]);
			entity.setOc_fname((String) row[4]);
			entity.setKh_no((String) row[5]);
			entity.setCr_sno((String) row[6]);
			entity.setCropname((String) row[7]);
			entity.setVarietyname((String) row[8]);
			entity.setCr_mix_unmix_ext((String) row[9]);
			entity.setCr_sow_date((String) row[10]);
			entity.setCropnature((String) row[11]);
			entity.setWatersource((String) row[12]);
			entity.setIrrmethoddesc((String) row[13]);

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}