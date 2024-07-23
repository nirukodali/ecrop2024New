package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.SpuperChkAppr;
import com.ecrops.entity.SuperCheckRecordsAlloted;

@Repository
@Transactional
public class SpuperChkApprPartition {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<SpuperChkAppr> getSupchkAppr( String wbdcode,String wbmcode,String userid,String cropyear) {
		
		
		String[] season = cropyear.split("@");
		System.out.println("season========="+season);
		String cseason = season[0];
		System.out.println("cseason========="+cseason);
		Integer Year = Integer.parseInt(season[1]);
		System.out.println("Year========="+Year);
	
		String part_key = "",part_key1="";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = cseason + wbdcode + Year;	
			System.out.println("part_key==========>"+part_key);
			
		} else {
			part_key = cseason + "0" + wbdcode + Year; 
			System.out.println("part_key==========>"+part_key);
		}
	       String tableName;
		if(Year >=2023) {
           tableName ="ecrop" + Year + "." +"cr_details_" + part_key;
		System.out.println("tableName---------------->" + tableName);
	       }else {
	    	   tableName ="cr_details_" + part_key;   
	       }
		String Sql = "select wbdname,wbmname,wbvname,cast(bookingid as character varying) as bookingid ,\r\n" + 
				"occupname as occup_name,occupfname as occup_fname,\r\n" + 
				"cropname,varietyname,cast(cr_sow_date as character varying) as cr_sow_dt, cast(kh_no as character varying)as kh_no ,\r\n" + 
				"cr_sno,superchk_remarks as oremarks,cr_no,\r\n" + 
				"case when superchk_remarks='A' then 'Entry Found Correct' when \r\n" + 
				" superchk_remarks='R' then 'Entry Found Incorrect'  end as remarks  \r\n" + 
				" from "+tableName+" a,wbvillage_mst b ,cropnames c ,  cr_variety_master v \r\n" + 
				"  where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and \r\n" + 
				"  a.cr_crop=c.cropid and a.variety=v.varietycode \r\n" + 
				"  and superchk_remarks='A' and cr_dist_code=? and cr_mand_code=? and a.supercheck_userid=? \r\n" + 
				"  order by wbmname,wbvname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		insertQuery.setParameter(1, Integer.parseInt(wbdcode));
		insertQuery.setParameter(2, Integer.parseInt(wbmcode));
		insertQuery.setParameter(3, userid);
		
	
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		//System.out.println("detailsEntities1=>"+detailsEntities1.size());
		//System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<SpuperChkAppr> detailsEntities = new ArrayList<SpuperChkAppr>();
		

		for (Object[] row : detailsEntities1) {

			SpuperChkAppr entity = new SpuperChkAppr();
			
			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setBookingid((String) row[3]);
			entity.setOccup_name((String) row[4]);
			entity.setOccup_fname((String) row[5]);
			entity.setCropname((String) row[6]);
			entity.setVarietyname((String) row[7]);
			entity.setCr_sow_dt((String) row[8]);
			entity.setKh_no((String) row[9]);
			entity.setCr_sno((String) row[10]);
			
			detailsEntities.add(entity);
	
		}
		
		return detailsEntities;

	}

}
