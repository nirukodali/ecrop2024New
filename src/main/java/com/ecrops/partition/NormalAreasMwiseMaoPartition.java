package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.FarmerDetails;
import com.ecrops.entity.NormalAreasMwiseMao;

@Repository
@Transactional
public class NormalAreasMwiseMaoPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<NormalAreasMwiseMao> getListt(String dcode, String cropyear, String mcode) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String tableName = "ecrop" + seasonYear + "." + "village_crop_normalareas";

		if (seasonYear >= 2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "village_crop_normalareas";
		} else {
			tableName = "village_crop_normalareas";
		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select  b.mname ,c.wbvname ,sum(normalarea) as normalarea  from " + tableName + " \r\n"
				+ "a inner join mandal_2011_cs b on a.mcode=b.mcode inner join wbvillage_mst c on a.vcode=c.wbvcode \r\n"
				+ "where a.dcode=? and a.mcode=? and a.cropyear=? and season=? group by b.mname,c.wbvname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, seasonYear);
		insertQuery.setParameter(4, seasonType);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<NormalAreasMwiseMao> detailsEntities = new ArrayList<NormalAreasMwiseMao>();

		try {
			for (Object[] row : detailsEntities1) {

				NormalAreasMwiseMao entity = new NormalAreasMwiseMao();
				entity.setMname((String) row[0]);
				entity.setWbvname((String) row[1]);
				entity.setNormalarea((BigDecimal) row[2]);
				
				detailsEntities.add(entity);

			}
		} catch (Exception e) {
			System.out.println("Exception=====" + e);
			e.printStackTrace();
		}

		return detailsEntities;

	}

}
