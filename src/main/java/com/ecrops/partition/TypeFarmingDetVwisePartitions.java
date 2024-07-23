package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.IrriSrcDetVwise;
import com.ecrops.entity.TypeFarmingDetVwise;

@Repository
@Transactional
public class TypeFarmingDetVwisePartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<TypeFarmingDetVwise> typefarming(String cropyear){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		part_key = seasonType + seasonYear;
		if (seasonYear >= 2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_farmingdet_type_mv_" + part_key;
		} else {
			tableName = "cr_farmingdet_type_mv_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbedname, wbemname, wbevname, conv_farmers, con_ext,nat_farmers, \r\n"
				+ "nat_ext, org_farmers, org_ext from "+ tableName +"  \r\n"
				+ "order by wbedname, wbemname, wbevname \r\n";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<TypeFarmingDetVwise> detailsEntities = new ArrayList<TypeFarmingDetVwise>();

		for (Object[] row : detailsEntities1) {

			TypeFarmingDetVwise entity = new TypeFarmingDetVwise();
			entity.setWbedname((String) row[0]);
			entity.setWbemname((String) row[1]);
			entity.setWbevname((String) row[2]);
			entity.setConv_farmers(Long.valueOf(row[3].toString()));
			entity.setCon_ext(((BigDecimal) row[4]));
			entity.setNat_farmers(Long.valueOf(row[5].toString()));
			entity.setNat_ext(((BigDecimal) row[6]));
			entity.setOrg_farmers(Long.valueOf(row[7].toString()));
			entity.setOrg_ext(((BigDecimal) row[8]));
			detailsEntities.add(entity);
		}

		return detailsEntities;
	}
}
