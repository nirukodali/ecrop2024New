package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.FarmerBookingDetails;
import com.ecrops.entity.FarmerDeatilsEcropIntf;

@Repository
@Transactional
public class FarmerDeatilsEcropIntfPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<FarmerDeatilsEcropIntf> farmerecrop(String cropyear, String dcode, String mcode, String vcode,
			String wbdcode, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", tableName = "";

		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + Integer.parseInt(wbdcode) + seasonYear;
		} else {
			part_key = seasonType + "0" + Integer.parseInt(wbdcode) + seasonYear;
		}

		if (seasonYear >= 2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_crop_det_new_v_" + part_key;
		} else {
			tableName = "cr_crop_det_new_v_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select  v.vname, cast(owner_tenant as varchar),oc_name,oc_fname,cast(kh_no as varchar),cr_sno,\r\n"
				+ "cast(tot_extent as varchar),cast(occupant_extent as varchar),cropname,  \r\n"
				+ "cast(cr_mix_unmix_ext as varchar),cast(mobileno as varchar),cast(age as varchar) \r\n"
				+ "from "+ tableName +" c\r\n"
				+ "LEFT JOIN vill_sec_det v ON c.dcode=v.dcode where c.dcode=? and c.mcode=? \r\n"
				+ "and c.cr_vcode=? and c.cr_year=? and c.cr_season=? \r\n"
				+ "order by c.dcode,c.mcode,c.cr_vcode,v.vname\r\n";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, Integer.parseInt(vcode));
		insertQuery.setParameter(4, seasonYear);
		insertQuery.setParameter(5, seasonType);

		// System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<FarmerDeatilsEcropIntf> detailsEntities = new ArrayList<FarmerDeatilsEcropIntf>();

		for (Object[] row : detailsEntities1) {

			FarmerDeatilsEcropIntf entity = new FarmerDeatilsEcropIntf();
			entity.setVname((String) row[0]);
			entity.setOwner_tenant((String) row[1]);
			entity.setOc_name((String) row[2]);
			entity.setOc_fname((String) row[3]);
			entity.setKh_no((String) row[4]);
			entity.setCr_sno((String) row[5]);
			entity.setTot_extent((String) row[6]);
			entity.setOccupant_extent((String) row[7]);
			entity.setCropname((String) row[8]);
			entity.setCr_mix_unmix_ext((String) row[9]);
			entity.setMobileno((String) row[10]);
			entity.setAge((String) row[11]);

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}