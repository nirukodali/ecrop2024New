package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.DDAPCorrectionSocialAudit;

@Repository
@Transactional
public class DDAPCorrectionSocialAuditPartition {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<DDAPCorrectionSocialAudit> ddapsocial(String cropyear,int a){

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "",tab1="",tab2="";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			        
        	tab1 = "ecrop" + seasonYear + "." + "farmer_grievances";
        	tab2 = "ecrop" + seasonYear + "." + "socialaudit_rej_details";
        }
        else {
        	tab2 = "farmer_grievances";
        	tab2 = "socialaudit_rej_details";
        }

		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);

		String Sql = "select dname,farmername,crop,variety,extent,src_irr,cr_pattern,sow_date,farming_type,vaadeleted,\r\n"
				+ "tot_corrections,totbookings,maoapproved,maorejected,maoprocessed,tahapproved,tahrejected,tahprocessed \r\n"
				+ "from (select vaadeleted,dist_code,distname,dname,farming_type,crop,variety,farmername,extent,src_irr,\r\n"
				+ "cr_pattern,sow_date,cropseq,tot_bookings  as totbookings,maoprocessed,tahprocessed,maoapproved,\r\n"
				+ "maorejected,tahapproved,tahrejected,farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date \r\n"
				+ "as tot_corrections from (select a.cr_dist_code as Dist_Code,w.wbdname as distname,w.dname   as dname,\r\n"
				+ "count(a.bookingid) filter(where cropschtype_sug is not null ) as farming_type,  count(a.bookingid) filter(\r\n"
				+ "where cr_crop_sug <>0 ) as crop, count(a.bookingid)   filter(where variety_sug <> 0) as variety,count(a.bookingid)\r\n"
				+ "filter(where farmername_sug is not null) as farmername, count(a.bookingid) filter(where extent_sug <> 0) as extent,\r\n"
				+ "count(a.bookingid) filter  (where cr_w_draw_sug <>0 ) as src_irr, count(a.bookingid) filter(where cr_sow_type_sug <> 0) \r\n"
				+ "as cr_pattern,count(a.bookingid) filter(where cr_sow_date_sug is not null) as sow_date, count(a.bookingid) filter(\r\n"
				+ "where cr_no_sug is not null) as cropseq,  count(distinct a.bookingid) as tot_bookings,count(distinct a.bookingid)\r\n"
				+ "FILTER(where (mao_remarks='A' or mao_remarks='D')) as maoprocessed,  count(distinct a.bookingid) FILTER(where\r\n"
				+ "mao_remarks='A') as maoapproved,count(distinct a.bookingid) FILTER(where mao_remarks='D') as maorejected,  \r\n"
				+ "count(distinct a.bookingid) FILTER(where(mro_remarks='A' or mro_remarks='D')) as tahprocessed,count(distinct a.bookingid) \r\n"
				+ "FILTER(where mro_remarks='A') as tahapproved,count(distinct a.bookingid) FILTER(where mro_remarks='D') as tahrejected,\r\n"
				+ "count( distinct a.bookingid) FILTER(where soc_status='Y') as vaadeleted from "+ tab1 +" a,(select\r\n"
				+ "wbdcode,wbmcode,wbvcode,wbdname,dname from wbvillage_mst where dcode <>999 order by wbdcode) w where w.wbvcode=a.cr_vcode  \r\n"
				+ "group by a.cr_dist_code,w.wbdname,w.dname order by distname) r1 ) x left join (select cr_dist_code,COUNT(DISTINCT (bookingid,\r\n"
				+ "cr_crop,variety,cr_sow_date)) AS totsupchecks from "+ tab2 +" b where cr_year=? and cR_season=?   \r\n"
				+ "group by cr_dist_code) y on x.dist_code=y.cr_dist_code";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1,  seasonYear);
		insertQuery.setParameter(2, seasonType);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<DDAPCorrectionSocialAudit> detailsEntities = new ArrayList<DDAPCorrectionSocialAudit>();

		for (Object[] row : detailsEntities1) {

			DDAPCorrectionSocialAudit entity = new DDAPCorrectionSocialAudit();
			entity.setDname((String) row[0]);
			entity.setFarmername(Long.valueOf(row[1].toString()));
			entity.setCrop(Long.valueOf(row[2].toString()));
			entity.setVariety(Long.valueOf(row[3].toString()));
			entity.setExtent(Long.valueOf(row[4].toString()));
			entity.setSrc_irr(Long.valueOf(row[5].toString()));
			entity.setCr_pattern(Long.valueOf(row[6].toString()));
			entity.setSow_date(Long.valueOf(row[7].toString()));
			entity.setFarming_type(Long.valueOf(row[8].toString()));
			entity.setVaadeleted(Long.valueOf(row[9].toString()));
			entity.setTot_corrections(Long.valueOf(row[10].toString()));
			entity.setTotbookings(Long.valueOf(row[11].toString()));
			entity.setMaoapproved(Long.valueOf(row[12].toString()));
			entity.setMaorejected(Long.valueOf(row[13].toString()));
			entity.setMaoprocessed(Long.valueOf(row[14].toString()));
			entity.setTahapproved(Long.valueOf(row[15].toString()));
			entity.setTahrejected(Long.valueOf(row[16].toString()));
			entity.setTahprocessed(Long.valueOf(row[17].toString()));
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}

