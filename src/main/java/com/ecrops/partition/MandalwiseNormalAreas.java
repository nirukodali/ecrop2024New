package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.MandalNormalAreas;

@Repository
@Transactional
public class MandalwiseNormalAreas {
	@PersistenceContext
	private EntityManager entityManager;

	public List<MandalNormalAreas> getmandalNormalAreas(String dcode, String cropyear,String role) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String tab1 = "village_crop_normalareas";
		String tab2 = "district_2011_cs";
		String tab3 = "mandal_2011_cs";

		if (seasonYear == 2023 && seasonType.equals("S")) {
			tab1 = "village_crop_normalareas";
		}else if(seasonYear>=2023) {
			tab1 = "ecrop" + seasonYear + "." + tab1; 
		}
		else {
			tab1 = "village_crop_normalareas";
		}
		System.out.println("tableName==================" + tab1);

		String Sql="";
		if (role.equals("9")) {
			 Sql = "select dname,mname,sum(normalarea) as normalarea from " + tab1 + " a, " + tab2 + " b , " + tab3 + " c  where a.dcode=b.dcode and b.dcode!=999  and a.dcode=c.dcode and a.mcode=c.mcode and cropyear="+seasonYear+" and season='"+seasonType+"'and a.dcode="+dcode+" group by dname,mname order by dname,mname ";
		}
		else if (role.equals("17") || role.equals("48")) {
            if (dcode != null) {
            		Sql = "select dname,mname,sum(normalarea) as normalarea from " + tab1 + " a, " + tab2 + " b , " + tab3 + "  c where a.dcode=b.dcode and b.dcode!=999  and a.dcode=c.dcode and a.mcode=c.mcode and cropyear="+seasonYear+" and season='"+seasonType+"' and a.dcode=" + dcode + " group by dname,mname order by dname,mname ";
            } else {
            		Sql = "select dname,mname,sum(normalarea) as normalarea from "  + tab1 + " a, " + tab2 + " b , " + tab3 + "c where a.dcode=b.dcode and b.dcode!=999  and a.dcode=c.dcode and a.mcode=c.mcode and cropyear="+seasonYear+" and season='"+seasonType+"' group by dname,mname order by dname,mname ";
            }
        }

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<MandalNormalAreas> detailsEntities = new ArrayList<MandalNormalAreas>();

		try {

			for (Object[] row : detailsEntities1) {
				MandalNormalAreas entity = new MandalNormalAreas();
				entity.setDname((String) row[0]);
				entity.setMname((String) row[1]);
				entity.setNormalarea((BigDecimal) row[2]);
				
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
