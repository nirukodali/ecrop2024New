package com.ecrops.partition;

import java.math.BigDecimal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.FarmerDetails;

@Repository
@Transactional
public class FarmerDetailsPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<FarmerDetails> farmerDetails(String wbdcode, String cropyear, String wbmcode, String datee) {
		System.out.println("wbdcode===>"+wbdcode);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "";
		if (Integer.parseInt(wbdcode) > 9) {
			part_key = seasonType + wbdcode + seasonYear;
		} else {
			part_key = seasonType + "0" + wbdcode + seasonYear;
		}

		String tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key;

		if (seasonYear >= 2023  && !(seasonYear==2023 && seasonType.equalsIgnoreCase("S"))) {
			tableName = "ecrop" + seasonYear + "." + "cr_details_" + part_key;
		} else {
			tableName = "cr_details_" + part_key;
		}
		System.out.println("tableName---------------->" + tableName);
//
//		String Sql = "select wbmname,wbvname,occupname,occupfname,cr_sno,cropname,varietyname, bookedext,cr_sow_date,mobileno,caste\r\n"
//				+ "from(select occupname,soc_category,occupfname,cr_sno,wbvname,wbmname,cropname, bookedext,mobileno,varietyname,cr_sow_date\r\n"
//				+ "from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,varietyname,\r\n"
//				+ "cr_sow_date from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,\r\n"
//				+ "variety,cr_sow_date from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cr_crop,cr_mix_unmix_ext\r\n"
//				+ "as bookedext,mobileno,variety,cr_sow_date  from " + tableName
//				+ " where cr_mand_code=? and dt_ins_reg = ? )a inner join \r\n"
//				+ "cropnames b on a.cr_crop=b.cropid)m inner join cr_variety_master n on m.variety=n.varietycode)p inner join  wbvillage_mst q \r\n"
//				+ "on p.cr_vcode=q.wbvcode)c inner join caste_mst d on cast(c.soc_category as varchar)=cast(d.castecode as varchar) order by wbmname,wbvname\r\n"
//				+ "";
		String Sql = "select wbmname,wbvname,occupname,occupfname,cr_sno,cropname,varietyname, bookedext,cr_sow_date,mobileno,kh_no,caste\r\n"
				+ "from(select occupname,soc_category,occupfname,cr_sno,wbvname,wbmname,cropname, bookedext,mobileno,varietyname,cr_sow_date,kh_no\r\n"
				+ "from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,varietyname,\r\n"
				+ "cr_sow_date,kh_no from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cropname, bookedext,mobileno,\r\n"
				+ "variety,cr_sow_date,kh_no from (select occupname,soc_category,occupfname,cr_sno,cr_vcode,cr_mand_code,cr_crop,cr_mix_unmix_ext\r\n"
				+ "as bookedext,mobileno,variety,cr_sow_date,kh_no  from " + tableName
				+ " where cr_mand_code="+Integer.parseInt(wbmcode)+" ";
		if(datee!=null && !datee.isEmpty() ) {
			Sql += " and dt_ins_reg =' "+datee+" '";
		}
			Sql+=")a inner join \r\n"
				+ "cropnames b on a.cr_crop=b.cropid)m inner join cr_variety_master n on m.variety=n.varietycode)p inner join  wbvillage_mst q \r\n"
				+ "on p.cr_vcode=q.wbvcode)c inner join caste_mst d on cast(c.soc_category as varchar)=cast(d.castecode as varchar) order by wbmname,wbvname\r\n"
				+ "";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
//		insertQuery.setParameter(1, Integer.parseInt(wbmcode));
//		insertQuery.setParameter(2, date);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		// System.out.println("detailsEntities1=>"+detailsEntities1.size());
		// System.out.println("detailsEntities1=>"+detailsEntities1.toString());
		List<FarmerDetails> detailsEntities = new ArrayList<FarmerDetails>();

		try {
			for (Object[] row : detailsEntities1) {

				FarmerDetails entity = new FarmerDetails();
				entity.setWbmname((String) row[0]);
				entity.setWbvname((String) row[1]);
				entity.setOccupname((String) row[2]);
				entity.setOccupfname((String) row[3]);
				entity.setCr_sno((String) row[4]);
				entity.setCropname((String) row[5]);
				entity.setVarietyname((String) row[6]);
				entity.setBookedext((BigDecimal) row[7]);
				entity.setCr_sow_date(row[8].toString());
				entity.setMobileno((BigDecimal) row[9]);
				entity.setKh_no((BigDecimal) row[10]);
				detailsEntities.add(entity);

			}
		} catch (Exception e) {
			System.out.println("Exception====="+e);
			e.printStackTrace();
		}

		return detailsEntities;

	}

}
