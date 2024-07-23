package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import com.ecrops.entity.CultivatorPeri;

@Component


public class CultivatorPeriRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public List<CultivatorPeri> getPeri(int cr_vcode,String cr_sno,BigDecimal kh_no,String cr_farmeruid){
		
		String qry="select cropname,varietyname,cr_sno,kh_no,occupname,occupfname,cr_mix_unmix_ext from ecrop2023.peri_k2023"
				+ " where cr_vcode="+cr_vcode+" and cr_sno='"+cr_sno+"' and kh_no="+kh_no+" and cr_farmeruid='"+cr_farmeruid+"' ";
		List<CultivatorPeri> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				CultivatorPeri pojos = new CultivatorPeri();

				pojos.setCropname((String) row[0]);
				pojos.setVarietyname((String) row[1]);
				pojos.setCr_sno((String) row[2]);
				pojos.setKh_no((BigDecimal) row[3]);
				pojos.setOccupname((String) row[4]);
				pojos.setOccupfname((String) row[5]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[6]);
				data.add(pojos);
			}
		}
		
		return data;
		
		
		
	}
	

}
