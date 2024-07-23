package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.MaoSocialAuditcorrection;
import com.ecrops.entity.Superchekupdstatus;

@Repository
@Transactional
public class MaoSocialAuditcorrectionPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<MaoSocialAuditcorrection> getSocialAudit(String wbdcode,String dcode, String mcode, String cropyear ){
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		
		
		String part_key = "";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;	 
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear; 
		}
		 String tableName;
		String tableName1;
		if(seasonYear>=2023) {
		 tableName = "ecrop" + seasonYear + "." + "farmer_grievances";
		 tableName1 = "ecrop" + seasonYear + "." + "socialaudit_rej_details";
		 }else {
			  tableName = "farmer_grievances";
			  tableName1 = "socialaudit_rej_details";
		 }
		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbvname,farmername,crop,variety,extent,src_irr,cr_pattern,sow_date,farming_type,vaadeleted ,tot_corrections,totbookings,\r\n"
				+ "maoapproved,maorejected,maoprocessed,tahrejected,tahapproved,tahprocessed, \r\n"
				+ "lgdvname,coalesce(y.totsupchecks,0)as totalcheck,cropseq,x.cr_vcode from (select vaadeleted,cr_vcode, wbvname,lgdvname,\r\n"
				+ "farming_type,crop,variety,farmername,extent,\r\n"
				+ "src_irr,cr_pattern,sow_date,cropseq,tot_bookings  as totbookings,maoprocessed,tahprocessed,maoapproved,maorejected,tahapproved,\r\n"
				+ "tahrejected,farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date+cropseq as tot_corrections \r\n"
				+ "from (select  b.cr_vcode, w.wbvname,w.lgdvname,  count(b.bookingid) filter(where  cropschtype_sug is not null ) as farming_type,  \r\n"
				+ "count(b.bookingid) filter(where  cr_crop_sug <>0 ) as crop, count(b.bookingid) filter(where  variety_sug <> 0) as variety, \r\n"
				+ "count(b.bookingid) filter(where  farmername_sug is not null) as farmername, count(b.bookingid) filter(where  extent_sug <> 0) \r\n"
				+ "as extent,  count(b.bookingid) filter(where  cr_w_draw_sug <>0 ) as src_irr, count(b.bookingid) filter(where  cr_sow_type_sug <> 0) \r\n"
				+ "as cr_pattern,  count(b.bookingid) filter(where  cr_sow_date_sug is not null) as sow_date, count(b.bookingid) filter(where cr_no_sug \r\n"
				+ "is not null) as cropseq,  count(distinct b.bookingid) as tot_bookings,count(distinct b.bookingid) FILTER (where (mao_remarks='A' \r\n"
				+ "or mao_remarks='D')) as maoprocessed,  count(distinct b.bookingid) FILTER (where mao_remarks='A') as maoapproved,\r\n"
				+ "count(distinct b.bookingid) FILTER (where soc_status='Y') as vaadeleted, count(distinct b.bookingid) FILTER (where mao_remarks='D') \r\n"
				+ "as maorejected,count(distinct b.bookingid) FILTER (where (mro_remarks='A' or mro_remarks='D')   ) as tahprocessed, \r\n"
				+ "count(distinct b.bookingid) FILTER (where mro_remarks='A') as tahapproved,count(distinct b.bookingid) FILTER (where mro_remarks='D'  )\r\n"
				+ "as tahrejected from "+tableName+" b ,(select distinct wbdcode, wbmcode, wbvcode,wbvname,lgdvname from wbvillage_mst \r\n"
				+ "where dcode=? and mcode=?) w where w.wbvcode=b.cr_vcode and cropyear=? and season=? group by b.cr_vcode, w.wbvname,w.lgdvname \r\n"
				+ "order by wbvname) r1 ) x  left join (select cr_vcode, count(distinct cast(bookingid as varchar)||cast(cr_crop as varchar)||\r\n"
				+ "cast(variety as varchar)||cast(cr_sow_date as varchar))\r\n"
				+ "as totsupchecks from "+tableName1+" b where dcode=? and mcode=? and cr_year=? and cr_season=? \r\n"
				+ "group by cr_vcode) y on x.cr_vcode=y.cr_vcode";

		System.out.println("Sql==============>"+Sql);
		Query insertQuery =  (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		insertQuery.setParameter(2, Integer.parseInt(mcode));
		insertQuery.setParameter(3, seasonYear);
		insertQuery.setParameter(4, seasonType);
		insertQuery.setParameter(5, Integer.parseInt(dcode));
		insertQuery.setParameter(6, Integer.parseInt(mcode));
		insertQuery.setParameter(7, seasonYear);
		insertQuery.setParameter(8, seasonType);
		
		System.out.println("insertQuery=>"+insertQuery.toString());
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		
		
		System.out.println("detailsEntities1=>"+detailsEntities1.size());
		System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<MaoSocialAuditcorrection> detailsEntities = new ArrayList<MaoSocialAuditcorrection>();
		

	
		try {
		for (Object[] row : detailsEntities1) {
			MaoSocialAuditcorrection entity = new MaoSocialAuditcorrection();
			
			entity.setWbvname((String) row[0]);
			entity.setFarmername((BigInteger) row[1]); 
			entity.setCrop((BigInteger) row[2]); 
			entity.setVariety((BigInteger) row[3]); 
			entity.setExtent((BigInteger) row[4]); 
			entity.setSrc_irr((BigInteger) row[5]); 
			entity.setCr_pattern((BigInteger) row[6]); 
			entity.setSow_date((BigInteger) row[7]); 
			entity.setFarming_type((BigInteger) row[8]);
			entity.setVaadeleted(((BigInteger) row[9]));
			entity.setTot_corrections((BigInteger) row[10]);
			entity.setTotbookings((BigInteger) row[11]);
			entity.setMaoapproved((BigInteger) row[12]);
			entity.setMaorejected((BigInteger) row[13]);
			entity.setMaoprocessed((BigInteger) row[14]);
			entity.setTahapproved((BigInteger) row[15]);
			entity.setTahrejected((BigInteger) row[16]);
			entity.setTahprocessed((BigInteger) row[17]);
			detailsEntities.add(entity);
		}
		System.out.println("detailsEntities===========>"+detailsEntities.size());

}catch(Exception e) {
	e.printStackTrace();
	System.out.println("erroe==> "+e);
}
		return detailsEntities;

	}
}
