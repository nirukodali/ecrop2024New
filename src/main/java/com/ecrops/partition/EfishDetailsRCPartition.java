package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.EfishDetailsRC;


@Repository
@Transactional
public class EfishDetailsRCPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<EfishDetailsRC> efishDetailsR(String dcode, String mcode, String cropyear,String data_src) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String tableName;
		String tableName1 = "wbvillage_mst";
		
		if (seasonYear >= 2023) {
			tableName = "ecrop" + seasonYear + "." + "cr_booking_nwb";
			

		} else {
			
			tableName = "cr_booking_nwb";
			tableName1 = "wbvillage_mst";
			

		}

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);
		

		String Sql = "select  wbvname,oc_name as ocname,oc_fname as ocfname, occupname as farmer_name,occupfname as father_name,cr_sno as\r\n"
				+ " survey_no,cast(kh_no as character varying) as khno, cast(occupant_extent as character varying) as occupant_extent,\r\n"
				+ " cast(tot_extent  as character varying) as tot_extent  from\r\n"
				+ " "+tableName+" a,"+tableName1+" b  where data_src='R' \r\n"
				+ " and a.cr_vcode=b.wbvcode  and a.dcode=? and a.mcode=?  order by wbvname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
//		insertQuery.setParameter(3, seasonYear);
//		insertQuery.setParameter(4, seasonType);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<EfishDetailsRC> detailsEntities = new ArrayList<EfishDetailsRC>();

		for (Object[] row : detailsEntities1) {

			EfishDetailsRC entity = new EfishDetailsRC();

			entity.setWbvname((String) row[0]);
			entity.setOcname((String) row[1]);
			entity.setOcfname((String) row[2]);
			entity.setFarmer_name((String) row[3]);
			entity.setFather_name((String) row[4]);
			entity.setSurvey_no((String) row[5]);
			entity.setKhno((String) row[6]);
			entity.setOccupant_extent((String) row[8]);
			entity.setTot_extent((String) row[9]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

//**********************************************//	
	public List<EfishDetailsRC> efishDetailsC(String dcode, String mcode, String cropyear,String data_src) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String tableName;
		String tableName1;
		
		if (seasonYear >= 2023) {
			tableName = "ecrop" + seasonYear + "." + "cr_details_efish_2023";
			tableName1 = "ecrop" + seasonYear + "." + "wbvillage_mst";
			

		} else {
			
			tableName = "cr_details_efish_2023";
			tableName1 = "wbvillage_mst";
			

		}

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);
		

		String Sql = "select village_name as wbvname,pattadar_name as ocname,pattadar_father_name as \r\n"
				+ "ocfname,occupant_name  as farmer_name,occupant_father_name  as father_name,cr_sno  as survey_no, kh_no, occupant_extent,\r\n"
				+ " total_extent as tot_extent,allowable_ext,booking_available,b.dcode,b.mcode from "+tableName+" a,\r\n"
				+ " "+tableName1+" b where booking_available='Y' and cast(a.cr_vcode  as character varying)= cast(b.wbvcode  as character varying)  \r\n"
				+ " and b.dcode=? and b.mcode=?  order by wbvname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
//		insertQuery.setParameter(3, seasonYear);
//		insertQuery.setParameter(4, seasonType);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<EfishDetailsRC> detailsEntities = new ArrayList<EfishDetailsRC>();

		for (Object[] row : detailsEntities1) {

			EfishDetailsRC entity = new EfishDetailsRC();

			entity.setWbvname((String) row[0]);
			entity.setOcname((String) row[1]);
			entity.setOcfname((String) row[2]);
			entity.setFarmer_name((String) row[3]);
			entity.setFather_name((String) row[4]);
			entity.setSurvey_no((String) row[5]);
			entity.setKhno((String) row[6]);
			entity.setOccupant_extent((String) row[8]);
			entity.setTot_extent((String) row[9]);
			entity.setAllowable_ext((String) row[10].toString());
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}


}
