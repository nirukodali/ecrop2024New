package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.SuperChk_rejReport;

@Repository
@Transactional
public class SuperChk_rejReportPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<SuperChk_rejReport> getSupchkRej( String wbdcode,String wbmcode,String cropyear) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String cseason = season[0];
		System.out.println("cseason========="+cseason);
		Integer Year = Integer.parseInt(season[1]);
		System.out.println("Year========="+Year);

		String part_key = "",part_key1="";
		if (Integer.parseInt(wbdcode) > 9) {
			//part_key = cseason + wbdcode + Year;
			part_key = cseason  + Year;
			
			System.out.println("part_key==========>"+part_key);
		
		} else {
			//part_key = cseason + "0" + wbdcode + Year; 
			part_key = cseason + Year; 
			
			System.out.println("part_key==========>"+part_key);
			
		}
	       String tableName;
		if(Year >=2023) {
           tableName ="ecrop" + Year + "." +"superchk_rej_" + part_key +"_mv";
	       }else {
	    	   tableName ="superchk_rej_" + part_key +"_mv";   
	       }
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbdname,wbmname,wbvname,cast(bookingid as character varying) as bookingid,occupname,ekycfarmername,occupfname,\r\n"
				+ " cropname,varietyname,cast(bookedext as character varying) as bookedext,wsrcdesc, cast(cr_sow_date  as character varying)\r\n"
				+ " as cr_sow_date,cast(kh_no as character varying) as kh_no,cr_sno,supercheck_userid,remarks,\r\n"
				+ " oc_name,oc_fname,reason,cr_vcode,cr_no,cr_crop   \r\n"
				+ " from "+tableName+" where cr_dist_code=? and cr_mand_code=? order by wbvname,cr_sno,kh_no \r\n"
				+ " ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		//insertQuery.setParameter(3, userid);
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<SuperChk_rejReport> detailsEntities = new ArrayList<SuperChk_rejReport>();
		

		for (Object[] row : detailsEntities1) {

			SuperChk_rejReport entity = new SuperChk_rejReport();
			
			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setBookingid((String) row[3]);
			entity.setOccupname((String) row[4]);
			entity.setEkycfarmername((String) row[5]);
			entity.setOccupfname((String) row[6]);
			entity.setCropname((String) row[7]);
			entity.setVarietyname((String) row[8]);
			entity.setBookedext((String) row[9]);
			entity.setWsrcdesc((String) row[10]);
			entity.setCr_sow_date((String) row[11]);
			entity.setKh_no((String) row[12]);
			entity.setCr_sno((String) row[13]);
			entity.setSupercheck_userid((String) row[14]);
			entity.setRemarks((String) row[15]);
			detailsEntities.add(entity);
	
		}
		
		return detailsEntities;

	}

}
