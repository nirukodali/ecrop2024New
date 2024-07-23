package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.RbkSurveyNoMappingDrpdwn;
import com.ecrops.entity.SeasonCropBookedExtent;

@Repository
@Transactional
public class SeasonCropBookedExtentPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<SeasonCropBookedExtent> getAllCrops( String wbmcode,String cropyear,String userid,String wbdcode) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String seasonType = season[0];
		System.out.println("seasonType========="+seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear========="+seasonYear);
	
		

		String part_key = "";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;	 System.out.println("part_key==========>"+part_key);
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear; System.out.println("part_key==========>"+part_key);
		}
		
		String tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key; 
		if(seasonYear>= 2023 && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
		}
		else {
			tableName = "cr_details_" + part_key; 
		}

		System.out.println("tableName---------------->" + tableName);

		String Sql = "select  b.cropname,b.cropnameeng, cast(a.bookedext  as character varying)as bookedext,cast(a.cr_crop as character varying) as cr_crop\r\n"
				+ "  from(select cr_crop,sum(cr_mix_unmix_ext) as bookedext from "+tableName+" where cr_mand_code=? group by cr_mand_code,\r\n"
				+ "	 cr_crop)a,cropnames b where a.cr_crop=b.cropid order by b.cropnameeng ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(wbmcode));
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<SeasonCropBookedExtent> detailsEntities = new ArrayList<SeasonCropBookedExtent>();
		

		for (Object[] row : detailsEntities1) {

			SeasonCropBookedExtent entity = new SeasonCropBookedExtent();
			
			entity.setCropnameeng((String) row[1]);
			entity.setCropname((String) row[0]);
			entity.setBookedext((String) row[2]);
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}
	
	

}
