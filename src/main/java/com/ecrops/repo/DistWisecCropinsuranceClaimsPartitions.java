 package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.AuthCropWise;
import com.ecrops.entity.DistWisecCropinsuranceClaims;

@Repository
@Transactional
public class DistWisecCropinsuranceClaimsPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<DistWisecCropinsuranceClaims> distwisecropinsclaims(String cropyear, int a) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "villagecropwise_ci_" + part_key;
		} else {
			tableName = "villagecropwise_ci_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		sql = " select distinct cast(unitcode as varchar),wbdname,cast(a.wbdcode as varchar),\r\n"
				+ "cast(cropcode as varchar),cropname,cast(claim as varchar),cast(trunc(bookedext,2) \r\n"
				+ "as varchar) as bookedext,cast(trunc(claim*bookedext,2) as varchar) as claim_amount,\r\n"
				+ "cast(farmers as varchar) from(select distinct unitcode,wbdcode,cropcode,\r\n"
				+ "cropname,irrmethod ,claim from  insclaims_k_2020_asc,cropnames\r\n"
				+ "where levelofunit=1 and cropcode=cropid and claim>0)\r\n"
				+ "a join (select wbdname,wbdcode,cr_crop,cr_w_draw,sum(bookedext) as bookedext,\r\n"
				+ "sum(dagri_farmers) as farmers from "+ tableName +" group by wbdname,wbdcode,\r\n"
				+ "cr_crop,cr_w_draw)b on a.wbdcode=b.wbdcode and cropcode=cr_crop and\r\n"
				+ "(a.irrmethod=2 or (a.irrmethod=0 and b.cr_w_draw=1) or (a.irrmethod=1 and b.cr_w_draw!=1)) ";

		System.out.println(sql);

		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		// sesnyr.setParameter(1, Integer.parseInt(cropid));

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> cropinsclaim = sesnyr.getResultList();

		List<DistWisecCropinsuranceClaims> entityDetails = new ArrayList<DistWisecCropinsuranceClaims>();

		for (Object[] row : cropinsclaim) {

			DistWisecCropinsuranceClaims entity = new DistWisecCropinsuranceClaims();

			entity.setUnitcode((String) row[0]);
			entity.setWbdname((String) row[1]);
			entity.setWbdcode((String) row[2]);
			entity.setCropcode((String) row[3]);

//			entity.setWbdcode(((BigDecimal) row[2]).intValue());
//			entity.setCropcode(((BigDecimal) row[3]).intValue());
			entity.setCropname((String) row[4]);
			entity.setClaim((String) row[5]);
			entity.setBookedext((String) row[6]);
			entity.setClaim_amount((String) row[7]);
			entity.setFarmers((String) row[8]);
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
