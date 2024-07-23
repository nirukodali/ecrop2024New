package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DistAgriHortiFishDDAP;


@Repository
@Transactional
public class DistAgriHortiFishDDAPPartitions {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DistAgriHortiFishDDAP> disthorddap(String cropyear,int a){

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "",tableName="";

		part_key = seasonType + seasonYear;
		if (activeYear == (seasonYear) ) {
        	tableName = "ecrop" + activeYear + "." + "distwisestat_" + part_key;
        }
        else {
        	tableName = "distwisestat_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbdname,cast(tmandals as varchar),cast(tvillages as varchar),\r\n"
				+ "cast(dagri_mandals as varchar),cast(dagri_villages as varchar), \r\n"
				+ "cast(dagri_ext as varchar),cast(dagri_farmers as varchar), \r\n"
				
				+ "cast(dhorti_mandals as varchar),cast(dhorti_villages as varchar),\r\n"
				+ "cast(dhorti_ext as varchar),cast(dhorti_farmers as varchar),\r\n"
				
				+ "cast(dseri_mandals as varchar),cast(dseri_villages as varchar),\r\n"
				+ "COALESCE(cast(dseri_ext as varchar), '0'),cast(dseri_farmers as varchar),\r\n"
				
				+ "cast(dsoc_mandals as varchar),cast(dsoc_villages as varchar),\r\n"
				+ "cast(dsoc_ext as varchar),cast(dsoc_farmers as varchar) \r\n"
				+ " from "+ tableName +" order by wbdname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<DistAgriHortiFishDDAP> detailsEntities = new ArrayList<DistAgriHortiFishDDAP>();

		for (Object[] row : detailsEntities1) {

			DistAgriHortiFishDDAP entity = new DistAgriHortiFishDDAP();
			entity.setWbdname((String) row[0]);
			entity.setTmandals((String) row[1]);
			entity.setTvillages((String) row[2]);
			
			entity.setDagri_mandals((String) row[3]);
			entity.setDagri_villages((String) row[4]);
			//entity.setDagrinormalarea((String) row[5]);
			entity.setDagri_ext((String) row[5]);
			entity.setDagri_farmers((String) row[6]);
			
			
			entity.setDhorti_mandals((String) row[7]);
			entity.setDhorti_villages((String) row[8]);
			//entity.setDhortinormalarea((String) row[10]);
			entity.setDhorti_ext((String) row[9]);
			entity.setDhorti_farmers((String) row[10]);
			
			
			//entity.setDfish_mandals((String) row[13]);
			//entity.setDfish_villages((String) row[14]);
			//entity.setDfish_farmers((String) row[15]);
			//entity.setDfish_ext((String) row[16]);
			
			entity.setDseri_mandals((String) row[11]);
			entity.setDseri_villages((String) row[12]);
			entity.setDseri_ext((String) row[13]);
			entity.setDseri_farmers((String) row[14]);
			
			
			//entity.setDsnormalarea((String) row[21]);
			//entity.setDfisheriesnormalarea((String) row[22]);
			//entity.setDfnormalarea((String) row[23]);
			
			entity.setDsoc_mandals((String) row[15]);
			entity.setDsoc_villages((String) row[16]);
			entity.setDsoc_ext((String) row[17]);
			entity.setDsoc_farmers((String) row[18]);
			
			
			
			//entity.setDsocnormalarea((String) row[28]);
			detailsEntities.add(entity);
		}

		return detailsEntities;

	}

}

