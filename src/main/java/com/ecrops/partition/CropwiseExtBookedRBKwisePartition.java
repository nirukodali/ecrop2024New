package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropwiseExtBookedRBKwise;

@Repository
@Transactional
public class CropwiseExtBookedRBKwisePartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropwiseExtBookedRBKwise> getBkExtRbk(String dcode, String mcode, String cropid, String cropyear) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "";

		part_key = seasonType + seasonYear;
		
		String tableName = "ecrop" + seasonYear + "." + "rep_rbk_wise_cropwise_ext_v_" + part_key;
		
		if (seasonYear >= 2023) {
			tableName = "ecrop" + seasonYear + "." + "rep_rbk_wise_cropwise_ext_v_" + part_key;
		} else {
			tableName = "rep_rbk_wise_cropwise_ext_v_" + part_key;
		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbvname,cast(vscode as varchar),cast(sum(totextent) as varchar) as totext, wbvcode,vname \r\n"
				+ "from " + tableName + " \r\n"
				+ "where dcode=? and mcode=? and cr_crop=? and cr_year=? and cr_season=?\r\n"
				+ "group by wbvcode,wbvname,vname,vscode ";
		// List<DataSourceWiseBookingReport> getList( wbdcode,wbmcode);

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, Integer.parseInt(cropid));
		insertQuery.setParameter(4, seasonYear);
		insertQuery.setParameter(5, seasonType);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		List<CropwiseExtBookedRBKwise> detailsEntities = new ArrayList<CropwiseExtBookedRBKwise>();

		try {

			for (Object[] row : detailsEntities1) {
				CropwiseExtBookedRBKwise entity = new CropwiseExtBookedRBKwise();
				// System.out.println("row[0]===========>"+row[0].toString());
				entity.setWbvname((String) row[0]);
				entity.setVscode((String) row[1]);
				entity.setTotext((String) row[2]);
				entity.setWbvcode(((Integer) row[3]));
				entity.setVname((String) row[4]);
				detailsEntities.add(entity);

			}
			System.out.println("detailsEntities===========>" + detailsEntities.size());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;

	}

}
