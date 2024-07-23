package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.EmployeeList;
import com.ecrops.entity.RepVaaDetails;

@Repository
@Transactional
public class RepVaaDetailsPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<RepVaaDetails> getVaaDet( String mcode, String cropyear) {

		String[] season = cropyear.split("@");
		System.out.println("season=========" + season);
		String seasonType = season[0];
		System.out.println("seasonType=========" + seasonType);
		Integer seasonYear = Integer.parseInt(season[1]);
		System.out.println("seasonYear=========" + seasonYear);

		String tableName = "user_registration_vs_v";
		
//		if (seasonYear >= 2023) {
//			tableName = "user_registration_vs_v";
//			
//
//		} else {
//			
//			tableName = "user_registration_vs_v";
//			
//
//		}

		System.out.println("tableName---------------->" + tableName);
		
		String Sql = "SELECT districtname,mandalname,villagename,userid,name,mobile_phone,emailid,cast(status as varchar) as status,regdate  \r\n"
				+ "from "+tableName+"  where status='A' and cast(mcode as varchar)=? and type_user='25'\r\n"
				+ "order by mandalname,villagename  ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, mcode);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<RepVaaDetails> detailsEntities = new ArrayList<RepVaaDetails>();

		for (Object[] row : detailsEntities1) {

			RepVaaDetails entity = new RepVaaDetails();

			entity.setDistrictname((String) row[0]);
			entity.setMandalname((String) row[1]);
			entity.setVillagename((String) row[2]);
			entity.setUserid((String) row[3]);
			entity.setName((String) row[4]);
			entity.setMobile_phone((BigDecimal) row[5]);
			entity.setEmailid((String) row[6]);
			entity.setStatus((String) row[7]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
