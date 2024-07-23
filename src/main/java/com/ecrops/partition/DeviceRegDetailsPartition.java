package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DeviceRegDetails;
import com.ecrops.entity.EmployeeList;

@Repository
@Transactional
public class DeviceRegDetailsPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<DeviceRegDetails> getDevRegDet( String mcode, String cropyear) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String tableName="user_registration";
		String tableName1="devicedet";
	
//		if (seasonYear >= 2023) {
//			tableName = "ecrop" + seasonYear + "." + "user_registration";
//			tableName1 = "ecrop" + seasonYear + "." + "devicedet";
//		
//
//		} else {
//			
//			tableName = "user_registration";
//			tableName1 = "devicedet";
//		
//
//		}

		System.out.println("tableName---------------->" + tableName);
		System.out.println("tableName1---------------->" + tableName1);
	

//		String Sql = "select distinct imei1,imei2,mobile_phone,emailid,cast(dt.status as varchar) as status , CAST(dt.dt_crt AS VARCHAR) AS dt_crt\r\n"
//				+ " ,blockortehsil,village,userid\r\n"
//				+ "from "+tableName+" ur, "+tableName1+" dt where cast(ur.village as varchar)=cast(dt.vcode as varchar) \r\n"
//				+ "and cast(blockortehsil as varchar)=? and type_user='25' order by village ";
		

				String Sql="select distinct imei1,imei2,mobile_phone,emailid,cast(dt.status as varchar) as status , CAST(dt.dt_crt AS VARCHAR) AS dt_crt\r\n"
						+ " ,blockortehsil,village,userid,wb.vsmname,wb.vsname\r\n"
						+ "from user_registration ur, devicedet dt,ecrop2024.villsec_rev_v wb where cast(ur.village as varchar)=cast(dt.vcode as varchar) \r\n"
						+ "and cast(blockortehsil as varchar)=? and type_user='25' and cast(blockortehsil as varchar)=cast(mcode as varchar) and cast(village as varchar)=cast(wb.vscode as varchar)  order by wb.vsname,dt_crt \r\n";
						


		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, mcode);
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<DeviceRegDetails> detailsEntities = new ArrayList<DeviceRegDetails>();

		for (Object[] row : detailsEntities1) {

			DeviceRegDetails entity = new DeviceRegDetails();

			entity.setImei1((String) row[0]);
			entity.setImei2((String) row[1]);
			entity.setMobile_phone((String) row[2]);
			entity.setEmailid((String) row[3]);
			entity.setStatus((String) row[4]);
			entity.setDt_crt((String) row[5]);
			entity.setVsmname((String) row[9]);
			entity.setVsname((String) row[10]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
