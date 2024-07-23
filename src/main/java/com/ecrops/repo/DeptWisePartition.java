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
import com.ecrops.entity.DeptWise;

@Repository
@Transactional
public class DeptWisePartition {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DeptWise> distwise(String cropyear,int a){
		
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		int activeYear = a;
		String part_key = "", sql = "", tableName = "",tab1 = "",tab2 = "",tab3 = "",tab4 = "",tab5 = "",tab6 = "";

		part_key = seasonType + seasonYear;

		if (seasonYear>=2023) {
			tableName = "ecrop" + seasonYear + "." + "cr_booking_dist_agri_v";
			tab1 = "ecrop" + seasonYear + "." + "cr_booking_dist_hort_v";
			tab2 = "ecrop" + seasonYear + "." + "cr_booking_dist_seri_v";
			
			tab3 = "ecrop" + seasonYear + "." + "cr_booking_dist_soc_forestry_v";
			tab4 = "ecrop" + seasonYear + "." + "cr_booking_dist_fodder_v";
			tab5 = "ecrop" + seasonYear + "." + "cr_authdetails_dist_v_" + part_key;
			tab6 = "ecrop" + seasonYear + "." + "cr_authdetails_vill_dept_mv_" + part_key;

		} else {
			tableName = "cr_booking_dist_agri_v";
			tab1 = "cr_booking_dist_hort_v";
			tab2 = "cr_booking_dist_seri_v";
			
			tab3 = "cr_booking_dist_soc_forestry_v";
			tab4 = "cr_booking_dist_fodder_v";
			tab5 = "cr_authdetails_dist_v_" + part_key;
			tab6 = "cr_authdetails_vill_dept_mv_" + part_key;

		}


		System.out.println("tableName---------------->" + tableName);
		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);
		System.out.println("tab3---------------->" + tab3);
		System.out.println("tab4---------------->" + tab4);
		System.out.println("tab5---------------->" + tab5);


		sql = "select distinct dist.dname,a.normalarea as agri_normalarea, a.cultivable_land as agri_cultivable_land,\r\n"
				+ " h.hnormalarea as hnormalarea,h.cultivable_land as hcultivable_land,\r\n"
				+ " s.cultivable_land as seri_cultivable_land,sf.normalarea as   soc_forestry_normalarea,\r\n"
				+ " sf.cultivable_land as soc_forestry_cultivable_land,f.cultivable_land as fodder_cultivable_land,\r\n"
				+ " dt.cr_dist_code,a_vaaauthextent,a_vroauthextent,a_ekycbookedext,h_vaaauthextent,h_vroauthextent,\r\n"
				+ " h_ekycbookedext,s_vaaauthextent,s_vroauthextent,s_ekycbookedext,r_vaaauthextent,r_vroauthextent,\r\n"
				+ " r_ekycbookedext from district_2011_cs dist, "+ tableName +" a, "+ tab1 +" h,"+ tab2 +" s,\r\n"
				+ " "+ tab3 +" sf,"+ tab4 +" f,"+ tab5 +",\r\n"
				+ " (SELECT cr_dist_code, dcode,sum(coalesce(a_vaaauthextent,0)) as a_vaaauthextent,\r\n"
				+ " sum(coalesce(a_vroauthextent,0)) as a_vroauthextent,sum(coalesce(a_ekycbookedext,0)) \r\n"
				+ " as a_ekycbookedext,sum(coalesce(h_vaaauthextent,0)) as h_vaaauthextent,\r\n"
				+ " sum(coalesce(h_vroauthextent,0)) as h_vroauthextent,sum(coalesce(h_ekycbookedext,0)) as h_ekycbookedext,\r\n"
				+ " sum(coalesce(s_vaaauthextent,0)) as s_vaaauthextent,sum(coalesce(s_vroauthextent,0)) as s_vroauthextent,\r\n"
				+ " sum(coalesce(s_ekycbookedext,0)) as s_ekycbookedext,sum(coalesce(r_vaaauthextent,0)) as r_vaaauthextent,\r\n"
				+ " sum(coalesce(r_vroauthextent,0)) as r_vroauthextent,sum(coalesce(r_ekycbookedext,0)) as r_ekycbookedext \r\n"
				+ " FROM "+ tab6 +" group by cr_dist_code,dcode) dt where\r\n"
				+ " a.dcode = dist.dcode and a.dcode = h.dcode and a.dcode=s.dcode and a.dcode=sf.dcode and a.dcode=f.dcode \r\n"
				+ " and a.dcode=dt.dcode and a.cr_year=? and a.cr_season=? and h.cr_year=? and h.cr_season=? and s.cr_year=? \r\n"
				+ " and s.cr_season=? and sf.cr_year=? and sf.cr_season=? and dist.dcode!=999 and f.cr_year=? \r\n"
				+ " and f.cr_season=? and dt.cr_dist_code=dist.wbdcode ";


		Query sesnyr = (Query) entityManager.createNativeQuery(sql);
		sesnyr.setParameter(1,  seasonYear);
		sesnyr.setParameter(2, seasonType);
		sesnyr.setParameter(3,  seasonYear);
		sesnyr.setParameter(4, seasonType);
		sesnyr.setParameter(5,  seasonYear);
		sesnyr.setParameter(6, seasonType);
		sesnyr.setParameter(7,  seasonYear);
		sesnyr.setParameter(8, seasonType);
		sesnyr.setParameter(9,  seasonYear);
		sesnyr.setParameter(10, seasonType);

		System.out.println("sesnyr=>" + sesnyr);
		List<Object[]> departwise = sesnyr.getResultList();

		List<DeptWise> entityDetails = new ArrayList<DeptWise>();

		for (Object[] row : departwise) {

			DeptWise entity = new DeptWise();

			entity.setDname((String) row[0]);
			entity.setAgri_cultivable_land(((BigDecimal) row[1]).intValue());
			entity.setA_vaaauthextent(((BigDecimal) row[2]).intValue());
			entity.setA_vroauthextent(((BigDecimal) row[3]).intValue());
			entity.setA_ekycbookedext(((BigDecimal) row[4]).intValue());
			
			entity.setHcultivable_land(((BigDecimal) row[5]).intValue());
			entity.setH_vaaauthextent(((BigDecimal) row[6]).intValue());
			entity.setH_vroauthextent(((BigDecimal) row[7]).intValue());
			entity.setH_ekycbookedext(((BigDecimal) row[8]).intValue());
			
			entity.setSeri_cultivable_land(((BigDecimal) row[9]).intValue());
			entity.setS_vaaauthextent(((BigDecimal) row[10]).intValue());
			entity.setS_vroauthextent(((BigDecimal) row[4]).intValue());
			entity.setS_ekycbookedext(((BigDecimal) row[5]).intValue());
			
			entity.setSoc_forestry_cultivable_land(((BigDecimal) row[6]).intValue());
			entity.setR_vaaauthextent(((BigDecimal) row[7]).intValue());
			entity.setR_vroauthextent(((BigDecimal) row[8]).intValue());
			entity.setR_ekycbookedext(((BigDecimal) row[9]).intValue());
			entityDetails.add(entity);

		}

		return entityDetails;

	}

}
