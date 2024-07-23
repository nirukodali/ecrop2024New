package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Rep_DownloadedDetailsIntf;

@Repository
@Transactional
public class Rep_DownloadedDetailsIntfPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Rep_DownloadedDetailsIntf> getDwnLdDet( String wbdcode,String wbmcode,String cropyear,String userid) {
		
		
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
		
		String tableName;
		//String tableName = "ecrop" + seasonYear + "." + "cr_booking_partition_" + part_key;
		if(seasonYear >= 2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
			 tableName = "ecrop" + seasonYear + "." + "cr_booking_partition_" + part_key;	
		}else {
			 tableName =  "cr_booking_partition_" + part_key;
		}
		
		 
		
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select count(*) filter (where data_src='W') as weblandcnt,count(*) filter (where data_src='C') as ccrccnt ,\r\n"
				+ "count(*) filter (where data_src in ('W','C')) as totalcnt,cr_vcode,wbevname  from "+tableName+" \r\n"
				+ "c,wbvillage_mst wb  where  cr_mand_code=?  AND forwarded_booking IS   NULL and downloaded='Y' and c.cr_vcode=wb.wbvcode  group by cr_vcode,wbevname order by cr_vcode,wbevname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		//insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(1, Integer.parseInt(wbmcode));
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<Rep_DownloadedDetailsIntf> detailsEntities = new ArrayList<Rep_DownloadedDetailsIntf>();
		

		for (Object[] row : detailsEntities1) {

			Rep_DownloadedDetailsIntf entity = new Rep_DownloadedDetailsIntf();
			
			entity.setWeblandcnt(Long.valueOf(row[0].toString()));
			entity.setCcrccnt(Long.valueOf(row[1].toString()));
			entity.setTotalcnt(Long.valueOf(row[2].toString()));
			entity.setWbevname((String) row[4]);
			detailsEntities.add(entity);

		}
		
		return detailsEntities;

	}

}
