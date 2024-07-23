package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.JowarMaizeEntity;


@Repository
@Transactional
public class DamagedJowarMaizeRepo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<JowarMaizeEntity> getJowarMaizeCrops(String dcode) {
		
		String Sql = "select wbdname,wbmname,wbvname,occupname,bookingid,kh_no,cr_sno,cr_mix_unmix_ext,cropname,varietyname,not_damaged_ext, \r\n"
			+ "cr_farmeruid,mobileno,bankacno,ifsc_code,caste,bankname,ekyc,input_sub_ext,damaged_ext,est_production,grain_damage_percent,\r\n"
			+ "input_sub_cover, photo  from cr_damaged_4dist_r2022_v a left join wbvillage_mst b on wbvcode=cr_vcode \r\n"
			+ "where dcode="+dcode+" and damaged_ext is not null and est_production is not null";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);
		
		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());
		List<JowarMaizeEntity> detailsEntities = new ArrayList<JowarMaizeEntity>();

		try {
		for (Object[] row : detailsEntities1) {

			JowarMaizeEntity entity = new JowarMaizeEntity();
			
			entity.setWbdname((String) row[0]);
			entity.setWbmname((String) row[1]);
			entity.setWbvname((String) row[2]);
			entity.setOccupname((String) row[3]);
			entity.setBookingid((Integer) row[4]);
			entity.setKh_no((BigDecimal) row[5]);
			entity.setCr_sno((String) row[6]);
			entity.setCr_mix_unmix_ext((BigDecimal) row[7]);
			entity.setCropname((String) row[8]);
			entity.setVarietyname((String) row[9]);
			entity.setNot_damaged_ext((BigDecimal) row[10]);
			entity.setCr_farmeruid((String) row[11]);
			entity.setMobileno((BigDecimal) row[12]);
			entity.setBankacno((String) row[13]);
			entity.setIfsc_code((String) row[14]);
			entity.setCaste((String) row[15]);
			entity.setBankname((String) row[16]);	
			entity.setEkyc((Character) row[17]);
			entity.setInput_sub_ext((BigDecimal) row[18]);
			entity.setDamaged_ext((BigDecimal) row[19]);
			entity.setEst_production((Integer) row[20]);
			entity.setGrain_damage_percent((Integer) row[21]);
			entity.setInput_sub_cover((Character) row[22]);
			entity.setPhoto((String) row[23]);
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