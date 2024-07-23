package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.ExtentBookedVSNormalAreaAbstract;

@Repository
@Transactional
public class ExtentBookedVSNormalAreaAbstractPartitions {
	
	@PersistenceContext
	private EntityManager entityManager;
	public List<ExtentBookedVSNormalAreaAbstract> extbooknormalarea(String cropyear){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "";
//		
		part_key = seasonType + seasonYear;
		String tab1 = "",tab2 = "",tab3 = "",tab4 = "",tab5 = "",tab6 = "",tab7 = "";

		if (seasonYear >= 2023) {
			tab1 = "ecrop" + seasonYear + "." + "district_2011_cs";
			tab2 = "ecrop" + seasonYear + "." + "cr_booking_dist_agri_v";
			tab3 = "ecrop" + seasonYear + "." + "cr_booking_dist_hort_v";
			tab4 = "ecrop" + seasonYear + "." + "cr_booking_dist_seri_v";
			tab5 = "ecrop" + seasonYear + "." + "cr_booking_dist_soc_forestry_v";
			tab6 = "ecrop" + seasonYear + "." + "cr_booking_dist_fodder_v";
		} else {
			tab1 = "district_2011_cs";
			tab2 = "cr_booking_dist_agri_v";
			tab3 = "cr_booking_dist_hort_v";
			tab4 = "cr_booking_dist_seri_v";
			tab5 = "cr_booking_dist_soc_forestry_v";
			tab6 = "cr_booking_dist_fodder_v";
		}
//		System.out.println("tableName---------------->" + tableName);

		String Sql = "select dist.dname,cast(a.normalarea as varchar) as agri_normalarea, \r\n"
				+ "cast(a.cultivable_land as varchar)as agri_cultivable_land, \r\n"
				+ "cast(h.hnormalarea as varchar)as hnormalarea, cast(h.cultivable_land as varchar)as hcultivable_land, cast(s.cultivable_land as varchar)as seri_cultivable_land, \r\n"
				+ "cast(sf.normalarea as varchar) as soc_forestry_normalarea, cast(sf.cultivable_land as varchar) as soc_forestry_cultivable_land, cast(f.cultivable_land as varchar)\r\n"
				+ "as fodder_cultivable_land from \r\n"
				+ " "+ tab1 +" dist, "+ tab2 +" a, "+ tab3 +" h, "+ tab4 +" s,\r\n"
				+ " "+ tab5 +" sf, "+ tab6 +" f where \r\n"
				+ "a.dcode = dist.dcode and a.dcode = h.dcode and a.dcode=s.dcode and a.dcode=sf.dcode and a.dcode=f.dcode and a.cr_year=? and a.cr_season=? \r\n"
				+ "and h.cr_year=? and h.cr_season=? and s.cr_year=? and s.cr_season=?  and sf.cr_year=? and sf.cr_season=? \r\n"
				+ "and dist.dcode!=999 and  f.cr_year=? and f.cr_season=? order by dname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1,  seasonYear);
		insertQuery.setParameter(2, seasonType);
		insertQuery.setParameter(3,  seasonYear);
		insertQuery.setParameter(4, seasonType);
		insertQuery.setParameter(5,  seasonYear);
		insertQuery.setParameter(6, seasonType);
		insertQuery.setParameter(7,  seasonYear);
		insertQuery.setParameter(8, seasonType);
		insertQuery.setParameter(9,  seasonYear);
		insertQuery.setParameter(10, seasonType);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<ExtentBookedVSNormalAreaAbstract> detailsEntities = new ArrayList<ExtentBookedVSNormalAreaAbstract>();

		for (Object[] row : detailsEntities1) {

			ExtentBookedVSNormalAreaAbstract entity = new ExtentBookedVSNormalAreaAbstract();
			entity.setDname((String) row[0]);
			entity.setAgri_normalarea((String) row[1]);
			entity.setAgri_cultivable_land((String) row[2]);
			entity.setHnormalarea((String) row[3]);
			entity.setHcultivable_land((String) row[4]);
			entity.setSeri_cultivable_land((String) row[5]);
			entity.setSoc_forestry_normalarea((String) row[6]);
			entity.setSoc_forestry_cultivable_land((String) row[7]);
			entity.setFodder_cultivable_land((String) row[8]);
			
			detailsEntities.add(entity);

		}

		return detailsEntities;

		
	}

}
