package com.ecrops.partition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DaoSocialAuditcorrection;

@Repository
@Transactional
public class DaoSocialAuditcorrectionRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<DaoSocialAuditcorrection> getMandalSocialAudit(String dcode, String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		String tableName = "farmer_grievances";
		if(seasonYear>=2023) {
		 tableName = "ecrop" + seasonYear + "." + "farmer_grievances";
		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select mname,farmername,crop,variety,extent,src_irr,cr_pattern,sow_date,farming_type,vaadeleted,totbookings,\r\n"
				+ "tot_corrections,maoprocessed,maorejected,maoapproved,tahrejected,tahapproved,tahprocessed\r\n"
				+ "from (select  vaadeleted,cr_mand_code, mname,farming_type,crop,variety,farmername,extent,src_irr,\r\n"
				+ "cr_pattern,sow_date,cropseq,tot_bookings  as totbookings,maoprocessed,tahprocessed,maoapproved,maorejected,tahapproved,tahrejected\r\n"
				+ ",farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date  as tot_corrections from (select  b.cr_mand_code, w.mname\r\n"
				+ ", count(b.bookingid) filter(where  cropschtype_sug is not null ) as farming_type,count(b.bookingid) filter(where cr_crop_sug <>0)\r\n"
				+ " as crop, count(b.bookingid) filter(where  variety_sug <> 0) as variety, count(b.bookingid) filter(where  farmername_sug is not\r\n"
				+ " null) as farmername,count(b.bookingid) filter(where extent_sug <> 0) as extent,count(b.bookingid) filter(where cr_w_draw_sug <>0)\r\n"
				+ " as src_irr,count(b.bookingid) filter(where cr_sow_type_sug <> 0) as cr_pattern,count(b.bookingid) filter(where cr_sow_date_sug is\r\n"
				+ " not null) as sow_date, count(b.bookingid) filter(where cr_no_sug is not null) as cropseq,count(distinct b.bookingid) as \r\n"
				+ "tot_bookings,count(distinct b.bookingid) FILTER (where (mao_remarks='A' or mao_remarks='D')) as maoprocessed,count(distinct \r\n"
				+ "b.bookingid) FILTER (where mao_remarks='A') as maoapproved, count(distinct b.bookingid) FILTER ( where soc_status='Y') as \r\n"
				+ "vaadeleted,count(distinct b.bookingid) FILTER (where mao_remarks='D') as maorejected,count(distinct b.bookingid) FILTER \r\n"
				+ "(where (mro_remarks='A' or mro_remarks='D')) as tahprocessed,count(distinct b.bookingid) FILTER (where mro_remarks='A') as\r\n"
				+ "tahapproved,count(distinct b.bookingid) FILTER (where mro_remarks='D') as tahrejected from "
				+ tableName + "\r\n"
				+ "b ,(select distinct wbdcode, wbmcode, wbvcode,dname,mname from wbvillage_mst where dcode=" + dcode
				+ ") w\r\n" + "where w.wbvcode=b.cr_vcode and cropyear=" + seasonYear + " and season='" + seasonType
				+ " 'group by b.cr_mand_code, w.mname order by mname) r1 ) x left join (select cr_mand_code, count(distinct CAST(bookingid AS CHAR) || CAST(cr_crop AS CHAR) || CAST(variety AS CHAR) || CAST(cr_sow_date AS CHAR)) as totsupchecks from ecrop2023.socialaudit_rej_details b where dcode="
				+ dcode + " and cr_year=" + seasonYear + "\r\n" + " and cr_season='" + seasonType
				+ "' group by cr_mand_code) y on x.cr_mand_code=y.cr_mand_code";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<DaoSocialAuditcorrection> detailsEntities = new ArrayList<DaoSocialAuditcorrection>();

		try {
			for (Object[] row : detailsEntities1) {
				DaoSocialAuditcorrection entity = new DaoSocialAuditcorrection();

				entity.setWbmname((String) row[0]);
				entity.setFarmername((BigInteger) row[1]);
				entity.setCrop((BigInteger) row[2]);
				entity.setVariety((BigInteger) row[3]);
				entity.setExtent((BigInteger) row[4]);
				entity.setSrc_irr((BigInteger) row[5]);
				entity.setCr_pattern((BigInteger) row[6]);
				entity.setSow_date((BigInteger) row[7]);
				entity.setFarming_type((BigInteger) row[8]);
				entity.setVaadeleted((BigInteger) row[9]);
				entity.setTotbookings((BigInteger) row[10]);
				entity.setTot_corrections((BigInteger) row[11]);
				entity.setMaoprocessed((BigInteger) row[12]);
				entity.setMaorejected((BigInteger) row[13]);
				entity.setMaoapproved((BigInteger) row[14]);
				entity.setTahrejected((BigInteger) row[15]);
				entity.setTahapproved((BigInteger) row[16]);
				entity.setTahprocessed((BigInteger) row[17]);

				detailsEntities.add(entity);
			}
			System.out.println("detailsEntities===========>" + detailsEntities.size());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;
	}
}