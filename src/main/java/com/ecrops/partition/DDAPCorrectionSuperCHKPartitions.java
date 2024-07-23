package com.ecrops.partition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DDAPCorrectionSocialAudit;
import com.ecrops.entity.DDAPCorrectionSuperCHK;

@Repository
@Transactional
public class DDAPCorrectionSuperCHKPartitions {
	@PersistenceContext
	private EntityManager entityManager;

	public List<DDAPCorrectionSuperCHK> ddapsuperchk(String cropyear, int a) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "", tab1 = "", tab2 = "";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tab1 = "ecrop" + seasonYear + "." + "supercheck_upd";
			tab2 = "ecrop" + seasonYear + "." + "supercheck_rejdet";
		} else {
			tab1 = "supercheck_upd";
			tab2 = "supercheck_rejdet";
		}

		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);

		String Sql = "select dname,totsupchecks,crop,variety,extent,cropseq,src_irr,cr_pattern,sow_date,\r\n"
				+ "farming_type,directly_forwarded,totbookings,maoapproved,maorejected,maoprocessed,\r\n"
				+ "tahapproved,tahrejected,tahprocessed from(select \r\n"
				+ "directly_forwarded,dist_code,distname,dname,farming_type,crop,variety,farmername,extent,src_irr,\r\n"
				+ "cr_pattern,sow_date,cropseq,tot_bookings  as totbookings,maoprocessed,tahprocessed,maoapproved,\r\n"
				+ "maorejected,tahapproved,tahrejected,farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date+cropseq \r\n"
				+ "as tot_corrections from ( select  a.cropyear,a.season,a.cr_dist_code as Dist_Code, w.wbdname as distname,\r\n"
				+ "d.dname as dname, count(a.bookingid) filter(where  cropschtype_sug is not null  ) as farming_type,\r\n"
				+ "count(a.bookingid) filter(where  cr_crop_sug <>0  ) as crop, count(a.bookingid) filter(where  variety_sug <> 0 )\r\n"
				+ "as variety,count(a.bookingid) filter(where  farmername_sug is not null ) as farmername, count(a.bookingid)\r\n"
				+ "filter(where  extent_sug <> 0) as extent,count(a.bookingid) filter(where  cr_w_draw_sug <>0 ) as src_irr, \r\n"
				+ "count(a.bookingid) filter(where  cr_sow_type_sug <> 0 ) as cr_pattern,count(a.bookingid) \r\n"
				+ "filter(where  cr_sow_date_sug is not null ) as sow_date, count(a.bookingid) \r\n"
				+ "filter(where cr_no_sug is not null  ) as cropseq,count(*)   as tot_bookings  ,count( a.bookingid) \r\n"
				+ "FILTER (where (mao_remarks='A' or mao_remarks='D')  ) as maoprocessed,count( a.bookingid) \r\n"
				+ "FILTER (where mao_remarks='A'   ) as maoapproved, count( a.bookingid) FILTER (where (mao_remarks='D' )\r\n"
				+ "or (mao_remarks is null  and mro_remarks='D')) as maorejected,count( a.bookingid) \r\n"
				+ "FILTER (where (mro_remarks='A' or mro_remarks='D')  ) as tahprocessed,count( a.bookingid) \r\n"
				+ "FILTER (where mro_remarks='A' ) as tahapproved,count( a.bookingid) FILTER (where mro_remarks='D' ) \r\n"
				+ "as tahrejected,count( bookingid)  FILTER ( where  cropschtype_sug is null \r\n"
				+ "and cr_crop_sug=0 and variety_sug=0 and farmername_sug is null and fathername_sug is null \r\n"
				+ "and uid_sug='0' and extent_sug=0 and cr_w_draw_sug=0 and cr_sow_type_sug=0 and cr_no_sug is null )\r\n"
				+ "as directly_forwarded from "+ tab1 +" a, (select wbdcode, wbmcode, wbvcode, wbdname \r\n"
				+ "from wbvillage_mst where dcode <>999 group by wbdcode, wbmcode, wbvcode, wbdname order by wbdcode) w,\r\n"
				+ "district_2011_cs d where w.wbvcode=a.cr_vcode and w.wbdcode=d.wbdcode  and cropyear=? and season=?  \r\n"
				+ "group by a.cr_dist_code, w.wbdname,d.dname,a.cropyear,a.season order by distname) r1 ) x \r\n"
				+ "left join (select distinct cr_dist_code,count(bookingid) as totsupchecks\r\n"
				+ "from "+tab2 +" b   group by cr_dist_code )y on x.dist_code=y.cr_dist_code ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, seasonYear);
		insertQuery.setParameter(2, seasonType);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<DDAPCorrectionSuperCHK> detailsEntities = new ArrayList<DDAPCorrectionSuperCHK>();

		for (Object[] row : detailsEntities1) {

			DDAPCorrectionSuperCHK entity = new DDAPCorrectionSuperCHK();
			entity.setDname((String) row[0]);
			entity.setTotsupchecks(Long.valueOf(row[1].toString()));
			entity.setCrop(Long.valueOf(row[2].toString()));
			entity.setVariety(Long.valueOf(row[3].toString()));
			entity.setExtent(Long.valueOf(row[4].toString()));
			entity.setCropseq(Long.valueOf(row[5].toString()));
			entity.setSrc_irr(Long.valueOf(row[6].toString()));
			entity.setCr_pattern(Long.valueOf(row[7].toString()));
			entity.setSow_date(Long.valueOf(row[8].toString()));
			entity.setFarming_type(Long.valueOf(row[9].toString()));
			entity.setDirectly_forwarded(Long.valueOf(row[10].toString()));
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
