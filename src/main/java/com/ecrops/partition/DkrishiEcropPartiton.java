package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DkrishiEcrop;
import com.ecrops.entity.FarmerDeatilsEcropIntf;

@Repository
@Transactional
public class DkrishiEcropPartiton {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DkrishiEcrop> dkrishi(String cropyear){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String part_key = "", tableName = "";

		tableName = "farmer.dk_ec_comp_data";

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select dname,sum(no_farmers)as nof,sum(qty_in_kgs) as qty,sum(no_same) as no_same,\r\n"
				+ "sum(no_others) as no_others,sum(no_noecrop) as no_noecrop,sum(qty_nocrop) as qty_nocrop, wbdcode \r\n"
				+ "from farmers.dk_ec_comp_data where season=? and cropyear =?  group by dname,wbdcode order by dname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, seasonType);
		insertQuery.setParameter(2, seasonYear);

		// System.out.println("insertQuery=>"+insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<DkrishiEcrop> detailsEntities = new ArrayList<DkrishiEcrop>();

		for (Object[] row : detailsEntities1) {

			DkrishiEcrop entity = new DkrishiEcrop();
			entity.setDname((String) row[0]);
			entity.setNof((Number) row[1]);
			entity.setQty((Number) row[2]);
			entity.setNo_same((Number) row[3]);
			entity.setNo_others((Number) row[4]);
			entity.setNo_noecrop((Number) row[5]);
			entity.setQty_nocrop((Number) row[6]);
			entity.setWbdcode(((Integer) row[7]).intValue());

			detailsEntities.add(entity);

		}

		return detailsEntities;

	}
}
