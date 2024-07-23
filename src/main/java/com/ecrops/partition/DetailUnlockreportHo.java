package com.ecrops.partition;

import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.Detailunlock;

@Repository
@Transactional
public class DetailUnlockreportHo {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Detailunlock> getdetailunlock( String wbdcode,String mcode, String cropyear,
			String dcode, String approval) {
		
		
		System.out.println("wbdcode----------------->"+wbdcode);
		System.out.println("wbdcode-LKKLJK---------------->"+mcode);
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String t1 = "";
		String qry="";
		
        if (Integer.parseInt(wbdcode) <= 9) {
            t1 = "cr_details_" + season + "0" + wbdcode + cropyear;
        } else {
            t1 = "cr_details_" + season + wbdcode + cropyear;
        }

        String tab = "cr_unlock_ext";

        if (seasonYear>=2023) {
        	t1 = "ecrop" + seasonYear + "." + tab;
            System.out.println("tab======" + tab);
        }
        else {
            t1 = "cr_details_" + season + wbdcode + cropyear;
            System.out.println("tab============" + tab);
        }

		
		if(approval.equals("A"))
		{
		qry = "select updatedby,a.cr_no,ct.cultdesc_loclang,variety,v.varietyname,occupname,occupfname,to_char(cr_sow_date, 'DD-MM-YYYY') as sdate,"
                + " a.cr_dist_code,a.cr_mand_code,a.cr_vcode,b.wbdname,b.wbmname,b.wbvname,a.kh_no,a.bookingid,cn.naturedesc,cr_farmeruid as uid,"
                + " a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,a.cropins,c.cropname,a.unlockedext,a.mao_unlock_rsn,mao_unlock_appr, mao_unlock_remarks,"
                + " unlock_reason,to_char(dt_unlock_ext,'yyyy-mm-dd') as dt_unlock_ext,to_char(dt_mao_unlock_appr,'yyyy-mm-dd') as dt_mao_unlock_appr"
                + " from " + tab + " a,wbvillage_mst_v b,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn"
                + " where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id"
                + " and cr_season= '" + seasonType + "' and cr_dist_code= " + wbdcode + " and cr_crop in(select cropid from cropnames where cropnature='H')"
                + " and a.mcode in(select distinct mcode from homandals_v where divcode= " + mcode + ") and a.variety=v.varietycode and a.cr_crop=c.cropid";
		}
		else
		{
			qry = "select updatedby,a.cr_no,ct.cultdesc_loclang,variety,v.varietyname,occupname,occupfname,to_char(cr_sow_date, 'DD-MM-YYYY') as sdate,"		//7
                    + " a.cr_dist_code,a.cr_mand_code,a.cr_vcode,b.wbdname,b.wbmname,b.wbvname,a.kh_no,a.bookingid,cn.naturedesc,cr_farmeruid as uid,"			//17
                    + " a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,a.cropins,c.cropname,a.unlockedext,a.mao_unlock_rsn,mao_unlock_appr, mao_unlock_remarks,"			//26
                    + " unlock_reason,to_char(dt_unlock_ext,'yyyy-mm-dd') as dt_unlock_ext,to_char(dt_mao_unlock_appr,'yyyy-mm-dd') as dt_mao_unlock_appr"		//29
                    + " from " + t1 + " a,wbvillage_mst_v b,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn"
                    + " where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id"
                    + " and a.cr_crop=c.cropid and cr_season='" + seasonType + "' and mao_unlock_appr='R' and cr_crop in(select cropid from cropnames where cropnature='H')"
                    + " and a.mcode in(select distinct mcode from homandals_v where divcode=" + mcode + ") and a.variety=v.varietycode ";
			
		}

		
		Query SqlQuery = (Query) entityManager.createNativeQuery(qry);
		
		
		

		System.out.println("SqlQuery=>" + SqlQuery.toString());
		
		List<Object[]> detailsEntities1 = SqlQuery.getResultList();
		
		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		
		List<Detailunlock> detailsEntities = new ArrayList<Detailunlock>();

		try {

			for (Object[] row : detailsEntities1) {
				Detailunlock entity = new Detailunlock();
				
				entity.setUpdateby((String) row[0]);
				entity.setBookingid((String) row[15].toString());
				entity.setOc_name((String) row[5].toString());
				 entity.setOc_fname((String) row[6].toString());
			        entity.setKh_no((String) row[14].toString());
			        entity.setCr_sno((String) row[18].toString());
			        entity.setCropname((String) row[22].toString());
			        entity.setCr_mix_unmix_ext((String) row[19].toString());
//			        entity.setExt((BigDecimal) row[28]);
			        entity.setVarietyname((String) row[4]);
			        entity.setUnlock_reason((String) row[27].toString());
			        entity.setUnlockedext((String) row[23].toString());
			        entity.setDt_mao_unlock_appr((String) row[25].toString());
			        entity.setMao_unlock_remarks((String) row[26].toString());
			
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
