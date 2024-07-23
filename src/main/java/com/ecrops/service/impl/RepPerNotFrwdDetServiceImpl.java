package com.ecrops.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import com.ecrops.entity.RepPerFrwdDetModel;
import com.ecrops.service.RepPerNotFrwdDetService;

@Service
public class RepPerNotFrwdDetServiceImpl implements RepPerNotFrwdDetService{

	@PersistenceContext
	private EntityManager entitymanager;
	
	
	@Override
	public List<RepPerFrwdDetModel> getPerNotFrwdDet(String tab, String tab2, Integer vcode1) {
		// TODO Auto-generated method stub
	
	       String  sql = " select oc_name,oc_fname,bookingid,varietyname,cropname,cr_mix_unmix_ext,kh_no,cr_sno,cr_sow_date from " + tab + " "
                   + " where cr_vcode=? and (cr_vcode,cr_sno,kh_no) not in (select cr_vcode,cr_sno,kh_no from " + tab2 + " "
                   + " where refbookingid is not null and cr_vcode=?) ";
		
			
	Query insertQuery = (Query) entitymanager.createNativeQuery(sql);
	insertQuery.setParameter(1, vcode1);
	insertQuery.setParameter(2, vcode1);

	
	System.out.println("insertQuery=>"+insertQuery);
	List<Object[]> detailsEntities1 = insertQuery.getResultList();
	System.out.println("detailsEntities=>"+detailsEntities1.size());
	
	List<RepPerFrwdDetModel> list = new ArrayList<RepPerFrwdDetModel>();
	for(Object[] row: detailsEntities1) {
		RepPerFrwdDetModel PerFrwdDetEntity = new RepPerFrwdDetModel();
		PerFrwdDetEntity.setOc_name((String)row[0].toString());
		PerFrwdDetEntity.setOc_fname((String)row[1].toString());
		PerFrwdDetEntity.setBookingid((String)row[2].toString());
		PerFrwdDetEntity.setVarietyname((String)row[3].toString());
		PerFrwdDetEntity.setCropname((String)row[4].toString());
		PerFrwdDetEntity.setCr_mix_unmix_ext((String)row[5].toString());
		PerFrwdDetEntity.setKh_no((String)row[6].toString());
		PerFrwdDetEntity.setCr_sno((String)row[7].toString());
		PerFrwdDetEntity.setCr_sow_date((String)row[8].toString());
		list.add(PerFrwdDetEntity);
	}
	
		return list;
	}	
		
	}

	
	
	
	
	

