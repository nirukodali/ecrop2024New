package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.entity.CropInsAbstractt;
import com.ecrops.entity.CropInsGrievance;

@Repository
@Transactional
public class CropInsAbstracttPartition {
	@PersistenceContext
	private EntityManager entityManager;

	public List<CropInsAbstractt> getCropInss(String dcode) {
//		String[] season = cropyear.split("@");
//		String seasonType = season[0];
//		Integer seasonYear = Integer.parseInt(season[1]);
//
//		String part_key = "";
//
//		part_key = seasonType + seasonYear;
		
		String tableName =  "pmfby_wb_k2022_mv" ;
		
//		if (seasonYear >= 2023) {
//			tableName = "ecrop" + seasonYear + "." + "rep_cropins_gri_v" ;
//		} else {
//			tableName = "rep_cropins_gri_v" ;
//		}
		System.out.println("tableName---------------->" + tableName);

		String Sql = "select district,mandal,rbkname,coalesce(yb_farmers,0) as yb_farmers,coalesce(yb_extent,0) as yb_extent,\r\n"
				+ "coalesce(yb_claimamt,0) as yb_claimamt,coalesce(wb_farmers,0) as wb_farmers,\r\n"
				+ "coalesce(wb_extent,0) as wb_extent,coalesce(wb_claimamt,0) as wb_claimamt,\r\n"
				+ "dcode,mcode,vcode from(select dcode,dname as district,mcode,mname \r\n"
				+ "as mandal,vcode,vname as rbkname,wb_farmers,wb_extent,wb_claimamt,yb_farmers,yb_extent,yb_claimamt from \r\n"
				+ "(select distinct dcode,mcode,vcode,dname,mname,vname from villsec_det_v) a left join (select district,mandal,rbkid,rbkname,\r\n"
				+ "sum(farmers) as wb_farmers,round(sum(extent),2) as wb_extent,round(sum(claimamt))as wb_claimamt from pmfby_wb_k2022_mv\r\n"
				+ "group by district,mandal,rbkid,rbkname) b on vcode=cast(substr(rbkid,5) as integer)  left join  (select \"Crop District Name\",\r\n"
				+ "\"level4Name\",rbkuserid,rbkname,sum(farmer_count) as yb_farmers,round(sum(area_insured*2.5),2) \r\n"
				+ "as yb_extent,round(sum(claim_amt)) as yb_claimamt from pmfby_yb_k2022_mv group by \"Crop District Name\",\"level4Name\",\r\n"
				+ "rbkuserid,rbkname) c on vcode= cast(substr(rbkuserid,5) as integer)) e where dcode=? and  mandal not ilike 'kurnool%' and\r\n"
				+ "(wb_claimamt is not null or yb_claimamt is not null) order by district,mandal,rbkname";
		

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		insertQuery.setParameter(1, Integer.parseInt(dcode));
		//insertQuery.setParameter(2, Integer.parseInt(mcode));
		//insertQuery.setParameter(3, seasonYear);
		//insertQuery.setParameter(4, seasonType);

		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		List<CropInsAbstractt> detailsEntities = new ArrayList<CropInsAbstractt>();

		try {

			for (Object[] row : detailsEntities1) {
				CropInsAbstractt entity = new CropInsAbstractt();
				System.out.println("row[0]===========>"+row[0].toString());
				entity.setDistrict((String) row[0]);
				entity.setMandal((String) row[1]);
				entity.setRbkname((String) row[2]);
				entity.setYb_farmers(((BigDecimal) row[3]));
				entity.setYb_extent((BigDecimal) row[4]);
				entity.setYb_claimamt((BigDecimal) row[5]);
				entity.setWb_farmers((BigDecimal) row[6]);
				entity.setWb_extent((BigDecimal) row[7]);
				entity.setWb_claimamt((BigDecimal) row[8]);
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
