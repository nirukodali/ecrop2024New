package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DkrishiMand;
import com.ecrops.entity.IrrigationMwise;

@Repository
@Transactional
public class DkrishiMandPartition {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DkrishiMand> dkrishimand(String cropyear,String wbdcode){
		String[] season = cropyear.split("@");
		String seasonType = season[0];

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName = "";

		tableName = "farmers.dk_ec_comp_data";

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select dname,mname,cropnameeng,sum(no_farmers)as nof,sum(qty_in_kgs) as qty,\r\n"
				+ "sum(no_same) as no_same,sum(no_others) as no_others,sum(no_noecrop) as no_noecrop,\r\n"
				+ "COALESCE(SUM(qty_nocrop), 0) AS qty_nocrop from "+ tableName +" where wbdcode=? and season= ? and cropyear = ? \r\n"
				+ "group by dname,mname,cropnameeng order by dname,mname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, seasonType);
		insertQuery.setParameter(3, seasonYear);


		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<DkrishiMand> detailsEntities = new ArrayList<DkrishiMand>();

		for (Object[] row : detailsEntities1) {

			DkrishiMand entity = new DkrishiMand();
			entity.setDname((String) row[0]);
			entity.setMname((String) row[1]);
			entity.setCropnameeng((String) row[2]);
			entity.setNof((Number) row[3]);
			entity.setQty((Number) row[4]);
			entity.setNo_same((Number) row[5]);
			entity.setNo_others((Number) row[6]);
			entity.setNo_noecrop((Number) row[7]);
			entity.setQty_nocrop((Number) row[8]);

			detailsEntities.add(entity);

		}

		return detailsEntities;
	}


}
