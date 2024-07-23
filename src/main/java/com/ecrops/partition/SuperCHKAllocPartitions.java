package com.ecrops.partition;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingDET;
import com.ecrops.entity.SuperCHKAlloc;

@Repository
@Transactional
public class SuperCHKAllocPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<SuperCHKAlloc> superchkalloc(String cropyear, String dcode, String userid,String wbdcode) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tableName;

		part_key = seasonType + seasonYear;

		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + Integer.parseInt(wbdcode) + seasonYear;
		} else {
			part_key = seasonType + "0" + Integer.parseInt(wbdcode) + seasonYear;
		}

		if (seasonYear >= 2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key;
		} else {
			tableName = "cr_details_" + part_key;
		}

		System.out.println("tableName---------------->" + tableName);
String sql = null;
		  if (userid.equals("2")) {
		        sql = "select dname, supercheck_userid, count(*) from district_2011_cs a left join " + tableName
		                + " b on wbdcode=cr_dist_code where supercheck_userid = 'TAH2' group by supercheck_userid, dname order by dname";
		    } else if (userid.equals("31")) {
		    	sql = " select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'ADA%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("9")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'DAO%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("19")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'DHO%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("22")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'HO%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("46")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'RDO%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("44")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'JC%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("45")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'DC%' group by supercheck_userid,dname order by dname";
          } else if (userid.equals("5")) {
          	sql = "select dname,supercheck_userid,count(*) from district_2011_cs a left join " + tableName + " b "
                      + " on wbdcode=cr_dist_code where supercheck_userid like 'MAO%' group by supercheck_userid,dname order by dname";
          }


		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		System.out.println("sesnyr=>" + sesnyr);

		List<Object[]> superchkall = sesnyr.getResultList();
		List<SuperCHKAlloc> entityDetails = new ArrayList<SuperCHKAlloc>();

		for (Object[] row : superchkall) {

			SuperCHKAlloc entity = new SuperCHKAlloc();

			entity.setDname((String) row[0]);
			entity.setSupercheck_userid((String) row[1]);
			entity.setCount(Long.valueOf(row[2].toString()));

			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
