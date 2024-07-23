package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.ecrops.entity.NonWebView;

@Repository
@Transactional
public class NonWebViewPartition {

	@PersistenceContext
	private EntityManager entityManager;

	public List<NonWebView> getNonwebView(String dcode, String mcode, String cropyear) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String tableName;
		String tableName1;
		
		if (seasonYear >= 2023) {
			tableName = "ecrop" + seasonYear + "." + "pattmast_nonwebland";
			tableName1 = "ecrop" + seasonYear + "." + "wbvillage_mst";
			

		} else {
			
			tableName = "pattmast_nonwebland";
			tableName1 = "wbvillage_mst";
			

		}

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);
		

		String Sql = "select cast(kh_no as character varying) as kh_no,cr_sno,oc_name,oc_fname,occupname,occupfname,\r\n"
				+ "  tot_extent,occupant_extent,'XXXXXXXX'||right(cr_farmeruid,4) as cr_farmeruid,\r\n"
				+ " cast(mobileno as varchar), cast(gender as varchar), wbdname,wbmname,wbvname from "+tableName+" a,\r\n"
				+ "  "+tableName1+" b where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode  \r\n"
				+ " and a.dcode=? and a.mcode=?  and cr_season=? and cr_year=? order by wbdname,wbmname,wbvname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, seasonType);
		insertQuery.setParameter(4, seasonYear);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<NonWebView> detailsEntities = new ArrayList<NonWebView>();

		for (Object[] row : detailsEntities1) {

			try {
				NonWebView entity = new NonWebView();

				entity.setKh_no((String) row[0]);
				entity.setCr_sno((String) row[1]);
				entity.setOc_name((String) row[2]);
				entity.setOc_fname((String) row[3]);
				entity.setOccupname((String) row[4]);
				entity.setOccupfname((String) row[5]);
				entity.setTot_extent((BigDecimal) row[6]);
				entity.setOccupant_extent((BigDecimal) row[7]);
				entity.setCr_farmeruid((String) row[8]);
				entity.setMobileno((String) row[9]);
				entity.setGender((String) row[10]);
				detailsEntities.add(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return detailsEntities;

	}


}
