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
import com.ecrops.entity.SuperChkVeriftyEntity;

@Repository
@Transactional
public class SuperCheckVerifyPartition {

	@PersistenceContext
	private EntityManager entityManager;

	public List<SuperChkVeriftyEntity> getsupercheckverify(String wbdcode,String userid, String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
        String tab1 = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
        String tab2 = "supercheck_exceptions_"+ seasonType +seasonYear;
        if (seasonYear>=2023) {
            tab1 = "ecrop" + seasonYear + "." + tab1;
            tab2 = "ecrop" + seasonYear + "." + tab2;
            System.out.println("tab1" + tab1);
            System.out.println("tab2" + tab2);

        }
		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);

		String Sql ="select ekycfarmername,se.exception_type,cr_sow_date,varietyname, bookingid, watersource as wsrcdesc,a.mname,a.vname,\r\n"
				+ "kh_no,cr_sno,cr_mix_unmix_ext,oc_name as occupname,oc_fname as occupfname,occupant_extent,cropname from "+tab1+" a left join "+tab2+"\r\n"
				+ "se on a.exception_catg= se.exception_catg where a.cr_dist_code="+wbdcode+" and supercheck_userid ='"+userid+"'order by dname,mname,vname,cr_sno,kh_no ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<SuperChkVeriftyEntity> detailsEntities = new ArrayList<SuperChkVeriftyEntity>();

		try {
			for (Object[] row : detailsEntities1) {
				SuperChkVeriftyEntity entity = new SuperChkVeriftyEntity();

				entity.setEkycfarmername((String) row[0]);
				entity.setException_type((String) row[1]);
				entity.setCr_sow_date((Date) row[2]);
				entity.setVarietyname((String) row[3]);
				entity.setBookingid((Integer) row[4]);
				entity.setWsrcdesc((String) row[5]);
				entity.setMname((String) row[6]);
				entity.setVname((String) row[7]);
				entity.setKh_no((BigDecimal) row[8]);
				entity.setCr_sno((String) row[9]);
				entity.setCr_mix_unmix_ext((BigDecimal) row[10]);
				entity.setOccupname((String) row[11]);
				entity.setOccupfname((String) row[12]);
				entity.setOccupant_extent((BigDecimal) row[13]);
				entity.setCropname((String) row[14]);
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