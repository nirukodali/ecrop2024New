package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.MandalwiseEKYCEntity;

@Repository
@Transactional
public class MandalwiseEKYC {
	@PersistenceContext
	private EntityManager entityManager;

	public List<MandalwiseEKYCEntity> getMandalwiseEKYC(String wbdcode, String cropyear) {
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "";

		part_key = seasonType + wbdcode + seasonYear;

		String tableName = "cr_details_" + part_key;

		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + tableName;

			System.out.println("tableName-----------------------" + tableName);
		} else {
			tableName = "cr_details_" + seasonType + wbdcode + seasonYear;

			System.out.println("tableName==================" + tableName);

		}

		String Sql = "SELECT\r\n"
				+ "    mname,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='A' AND vaaauth='Y'), 0) as vaa_agri_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='A' AND vroauth='Y'), 0) as vro_agri_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='A' AND ekyc='Y'), 0) as ekyc_agri_farmers,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='A' AND vaaauth='Y')), 0) as vaa_agri_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='A' AND vroauth='Y')), 0) as vro_agri_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='A' AND ekyc='Y')), 0) as ekyc_agri_ext,\r\n"
				+ "\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='H' AND vaaauth='Y'), 0) as vaa_horti_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='H' AND vroauth='Y'), 0) as vro_horti_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='H' AND ekyc='Y'), 0) as ekyc_horti_farmers,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='H' AND vaaauth='Y')), 0) as vaa_horti_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='H' AND vroauth='Y')), 0) as vro_horti_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='H' AND ekyc='Y')), 0) as ekyc_horti_ext,\r\n"
				+ "\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='S' AND vaaauth='Y'), 0) as vaa_seri_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='S' AND vroauth='Y'), 0) as vro_seri_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='S' AND ekyc='Y'), 0) as ekyc_seri_farmers,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='S' AND vaaauth='Y')), 0) as vaa_seri_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='S' AND vroauth='Y')), 0) as vro_seri_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='S' AND ekyc='Y')), 0) as ekyc_seri_ext,\r\n"
				+ "\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='R' AND vaaauth='Y'), 0) as vaa_forestry_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='R' AND vroauth='Y'), 0) as vro_forestry_farmers,\r\n"
				+ "    COALESCE(count(distinct cr_farmeruid) filter (WHERE cropnature='R' AND ekyc='Y'), 0) as ekyc_forestry_farmers,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='R' AND vaaauth='Y')), 0) as vaa_forestry_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='R' AND vroauth='Y')), 0) as vro_forestry_ext,\r\n"
				+ "    COALESCE(round(sum(cr_mix_unmix_ext) filter (WHERE cropnature='R' AND ekyc='Y')), 0) as ekyc_forestry_ext\r\n"
				+ "FROM\r\n"
				+ " "+tableName+" a \r\n"
				+ "INNER JOIN\r\n"
				+ "    cropnames b ON cr_crop = cropid   \r\n"
				+ "INNER JOIN\r\n"
				+ "    mandal_2011_cs c USING(mcode) \r\n"
				+ "WHERE\r\n"
				+ "    a.cr_season='"+seasonType+"' \r\n"
				+ "    AND a.cr_year="+seasonYear+" \r\n"
				+ "GROUP BY\r\n"
				+ "    mname;\r\n"
				+ "";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery.toString());
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<MandalwiseEKYCEntity> detailsEntities = new ArrayList<MandalwiseEKYCEntity>();

		try {

			for (Object[] row : detailsEntities1) {
				MandalwiseEKYCEntity entity = new MandalwiseEKYCEntity();
				entity.setMname((String) row[0]);
				entity.setVaa_agri_farmers((BigInteger) row[1]);
				entity.setVro_agri_farmers((BigInteger) row[2]);
				entity.setEkyc_agri_farmers((BigInteger) row[3]);
				entity.setVaa_agri_ext((BigDecimal) row[4]);
				entity.setVro_agri_ext((BigDecimal) row[5]);
				entity.setEkyc_agri_ext((BigDecimal) row[6]);
				entity.setVaa_horti_farmers((BigInteger) row[7]);
				entity.setVro_horti_farmers((BigInteger) row[8]);
				entity.setEkyc_horti_farmers((BigInteger) row[9]);
				entity.setVaa_horti_ext((BigDecimal) row[10]);
				entity.setVro_horti_ext((BigDecimal) row[11]);
				entity.setEkyc_horti_ext((BigDecimal) row[12]);
				entity.setVaa_seri_farmers((BigInteger) row[13]);
				entity.setVro_seri_farmers((BigInteger) row[14]);
				entity.setEkyc_seri_farmers((BigInteger) row[15]);
				entity.setVaa_seri_ext((BigDecimal) row[16]);
				entity.setVro_seri_ext((BigDecimal) row[17]);
				entity.setEkyc_seri_ext((BigDecimal) row[18]);
				entity.setVaa_forestry_farmers((BigInteger) row[19]);
				entity.setVro_forestry_farmers((BigInteger) row[20]);
				entity.setEkyc_forestry_farmers((BigInteger) row[21]);
				entity.setVaa_forestry_ext((BigDecimal) row[22]);
				entity.setVro_forestry_ext((BigDecimal) row[23]);
				entity.setEkyc_forestry_ext((BigDecimal) row[24]);
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
