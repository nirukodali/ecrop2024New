package com.ecrops.partition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.ecrops.entity.DigitalAckEntity;

@Repository
@Transactional
public class DigitalAckSMSRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<DigitalAckEntity> getDitialAckSMS(String wbdcode,String dcode, String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
        String tab1 = "cr_details_" + seasonType + wbdcode + seasonYear;
        if (seasonYear.equals(2023)) {
            tab1 = "ecrop" + seasonYear + "." + tab1;
            System.out.println("tab1" + tab1);

        }
		System.out.println("tab1---------------->" + tab1);

		String Sql = "select mname, count(*) as totSmsCnt, count(*) filter (where sms_sent='Y') as sentSmsCnt, \r\n"
				+ "count(*) filter (where sms_sent is null) as remSmsCnt from "+tab1+" a,mandal_2011_cs b where b.dcode="+dcode+"\r\n"
				+ "and a.mcode=b.mcode and ekyc = 'Y' and cr_crop<>888 and mismatch is null and social_status is null\r\n"
				+ "and mark_delet is null and extent_upd is null and cr_mix_unmix_ext > 0   group by cr_mand_code,mname order by mname ";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<DigitalAckEntity> detailsEntities = new ArrayList<DigitalAckEntity>();

		try {
			for (Object[] row : detailsEntities1) {
				DigitalAckEntity entity = new DigitalAckEntity();

				entity.setMname((String) row[0]);
				entity.setTotsmscnt((BigInteger) row[1]);
				entity.setSentsmscnt((BigInteger) row[2]);
				entity.setRemsmscnt((BigInteger) row[3]);
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