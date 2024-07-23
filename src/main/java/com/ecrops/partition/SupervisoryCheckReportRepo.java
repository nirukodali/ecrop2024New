package com.ecrops.partition;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.SupervisoryCheckReportEntity;

@Repository
@Transactional
public class SupervisoryCheckReportRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<SupervisoryCheckReportEntity> getsupercheckreport(String wbdcode,String userid,String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
        String tab1 = "cr_details_" + seasonType + wbdcode + seasonYear;
        if(seasonYear==2023 && seasonType.equalsIgnoreCase("S")) {
        }
        else if (seasonYear>=2023) {
            tab1 = "ecrop" + seasonYear + "." + tab1;
            System.out.println("tab1" + tab1);
        }
		System.out.println("tab1---------------->" + tab1);

		String Sql ="select wbdname,wbmname,wbvname,bookingid,occupname as occup_name,occupfname as occup_fname,cropname,varietyname,cr_sow_date,\r\n"
				+ "kh_no,cr_sno,reason from "+tab1+"  a,wbvillage_mst b ,cropnames c ,\r\n"
				+ "cr_variety_master v,authority_verify_reasons ar where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and\r\n"
				+ "a.cr_vcode=b.wbvcode and a.cr_crop=c.cropid and a.variety=v.varietycode and superchk_remarks='R' and\r\n"
				+ "a.suprejreason =cast(ar.code as text) and cr_dist_code="+wbdcode+" and a.supercheck_userid='"+userid+"' and a.cr_season='"+seasonType+"' and a.cr_year="+seasonYear+"\r\n"
				+ "order by wbmname,wbvname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<SupervisoryCheckReportEntity> detailsEntities = new ArrayList<SupervisoryCheckReportEntity>();

		try {
			for (Object[] row : detailsEntities1) {
				SupervisoryCheckReportEntity entity = new SupervisoryCheckReportEntity();

				entity.setWbdname((String) row[0]);
				entity.setWbmname((String) row[1]);
				entity.setWbvname((String) row[2]);
				entity.setBookingid((Integer) row[3]);
				entity.setOccup_name((String) row[4]);
				entity.setOccup_fname((String) row[5]);
				entity.setCropname((String) row[6]);
				entity.setVarietyname((String) row[7]);
				entity.setCr_sow_date((Date) row[8]);
				entity.setKh_no((BigDecimal) row[9]);
				entity.setCr_sno((String) row[10]);
				entity.setReason((String) row[11]);
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