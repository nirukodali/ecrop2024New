package com.ecrops.partition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DaoCropDetEntity;

@Repository
@Transactional
public class DaoCropBookDetRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<DaoCropDetEntity> getdaoCropBookingDetails(String wbdcode,String dcode, String mcode,String cropyear) {
		
		if (wbdcode.length() == 1) {
            wbdcode = "0" + wbdcode;
        }
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
        String tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
        if(seasonYear==2023 && seasonType.equals("S")){
        	tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
        }
        else if (seasonYear>=2023){
            tab = "ecrop" + seasonYear + "." + tab;
        }else{
         tab = "cr_crop_det_new_v_" + seasonType + wbdcode + seasonYear;
        }
		
		String Sql = "select wbmname,wbvname,oc_name,oc_fname, kh_no,cr_sno, cropname, tot_extent,mobileno\r\n"
				+ "from (select cropname,cr_vcode,oc_name,oc_fname,mobileno,kh_no,cr_sno,tot_extent\r\n"
				+ "from "+tab+" a where dcode="+dcode+" and mcode="+mcode+" and cr_year="+seasonYear+" \r\n"
				+ "and cr_season='"+seasonType+"' order by cr_vcode,kh_no,cr_sno,oc_name) a inner join wbvillage_mst b  on a.cr_vcode=b.wbvcode";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		//System.out.println("detailsEntities1=>" + detailsEntities1.size());
		//System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<DaoCropDetEntity> detailsEntities = new ArrayList<DaoCropDetEntity>();
		try {
			for (Object[] row : detailsEntities1) {
				DaoCropDetEntity entity = new DaoCropDetEntity();

				entity.setWbmname((String) row[0]);
				entity.setWbvname((String) row[1]);
				entity.setOc_name((String) row[2]);
				entity.setOc_fname((String) row[3]);
				entity.setKh_no((BigDecimal) row[4]);
				entity.setCr_sno((String) row[5]);
				entity.setCropname((String) row[6]);
				entity.setTot_extent((BigDecimal) row[7]);
				entity.setMobileno((BigDecimal) row[8]);
				detailsEntities.add(entity);
			}
			//System.out.println("detailsEntities===========>" + detailsEntities.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;
	}
}