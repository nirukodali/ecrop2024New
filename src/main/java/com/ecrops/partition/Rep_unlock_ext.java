package com.ecrops.partition;

import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.ecrops.entity.RepUnlock;

@Repository
@Transactional
public class Rep_unlock_ext {
	@PersistenceContext
	private EntityManager entityManager;

	public List<RepUnlock> getRep_unlock_ext(String wbdcode, String divcode, String cropyear,String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "";

		part_key = seasonType + seasonYear;

		String tableName = "ecrop" + seasonYear + "." + "cr_unlock_ext_mv_" + part_key;

		System.out.println("tableName---------------->" + tableName);

//		STRING SQL = "SELECT MNAME,LGDVNAME, SUM(BOOKINGS) AS BOOKINGS, SUM(EXT) AS EXT, SUM(MAO_HO_APRR) AS MAO_HO_APRR, SUM(MAO_APPR_EXT) AS MAO_APPR_EXT,\R\N"
//				+ "ROUND(SUM(MAO_HO_APRR)/SUM(BOOKINGS)*100,2)  AS PERCENTAGE \R\N"
//				+ "FROM "+TABLENAME+" WHERE SEASON='"+SEASONTYPE+"' AND CR_DIST_CODE="+WBDCODE+" AND MCODE\R\N"
//				+ "IN(SELECT MCODE FROM HOMANDALS_V WHERE DIVCODE="+DIVCODE+") GROUP BY DNAME,MNAME,LGDVNAME ORDER BY DNAME,MNAME ";
		
		String Sql = "select mname,lgdvname, sum(bookings) as bookings, sum(ext) as ext, sum(mao_ho_aprr) as mao_ho_aprr, sum(mao_appr_ext) as mao_appr_ext,\r\n"
				+ "round(sum(mao_ho_aprr)/sum(bookings)*100,2)  as percentage \r\n"
				+ "from "+tableName+" where season='"+seasonType+"' and cr_dist_code= (select distinct wbdcode from public.wbvillage_mst where dcode =( select cast(district as integer) from user_registration where userid='"+userid+"')) and mcode\r\n"
				+ "in(select mcode from homandals_v where divcode="+divcode+") group by dname,mname,lgdvname order by dname,mname ";
		
		

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
			System.out.println("qry----->"+Sql);
		System.out.println("insertQuery=>" + insertQuery.toString());
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		
		List<RepUnlock> detailsEntities = new ArrayList<RepUnlock>();

		try {

			for (Object[] row : detailsEntities1) {
				RepUnlock entity = new RepUnlock();
				entity.setMname((String) row[0]);
				entity.setLgdvname((String) row[1]);
				entity.setBookings((BigDecimal) row[2]);
				entity.setExt((BigDecimal) row[3]);
				entity.setMao_ho_appr((BigDecimal) row[4]);
				entity.setMao_appr_ext((BigDecimal) row[5]);
				entity.setPercentage((BigDecimal) row[6]);
			
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
