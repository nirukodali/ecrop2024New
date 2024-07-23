package com.ecrops.repo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ecrops.entity.VroObjectionable;
import com.ecrops.entity.VroObjectionableDrop;

@Component
public class VroObjectionableRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public List<VroObjectionable> getData(String partitionName, int vcode) {
		
		String qry="select bookingid,kh_no,cr_sno,cr_mix_unmix_ext,cropname,oc_name,oc_fname,cr_no,varietyname,cr_farmeruid, \r\n"
				+ "  cr_crop,variety,cr_sow_date,cr_dist_code,cr_mand_code,cr_year,cr_season from " + partitionName +" \r\n"
				+ " where cr_vcode=  "+ vcode + " and vro_obj_verify is null limit 20";
		
		List<VroObjectionable> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;
				VroObjectionable pojos = new VroObjectionable();
				
				pojos.setBookingid((int) row[0]);
				pojos.setKh_no((BigDecimal) row[1]);
				pojos.setCr_sno((String) row[2]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[3]);
				pojos.setCropname((String) row[4]);
				pojos.setOc_name((String) row[5]);
				pojos.setOc_fname((String) row[6]);
				pojos.setCr_no((String) row[7]);
				pojos.setVarietyname((String) row[8]);
				pojos.setCr_farmeruid((String) row[9]);
				pojos.setCr_crop((int) row[10]);
				pojos.setVariety((int) row[11]);
				pojos.setCr_sow_date((Date) row[12]);
				pojos.setCr_dist_code((BigDecimal) row[13]);
				pojos.setCr_mand_code((BigDecimal) row[14]);
				pojos.setCr_year((BigDecimal) row[15]);
				pojos.setCr_season((Character) row[16]);

				
				
				data.add(pojos);
				
			}
		}
		
		return data;
		
	}
	
	
	@Transactional
	public List<VroObjectionableDrop> drop(){
		
		String qry="select vro_objection_remark,code,category from obj_unobj where vro_objection_remark='Yes' ";
		
		
		List<VroObjectionableDrop> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;
				VroObjectionableDrop pojos = new VroObjectionableDrop();
				
				pojos.setVro_objection_remark((String)row[0]);
				pojos.setCode((int)row[1]);
				pojos.setCategory((String)row[2]);
				
				data.add(pojos);
			}
		}
		return data;
	}
	
	
	@Transactional
	public int val(String category) {
		String qry ="select distinct(code) from  obj_unobj  where category= "+ category +" ";
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> results = sql.getResultList();

	    if (results != null && !results.isEmpty()) {
	        return ((Number) results.get(0)).intValue();
	    } else {
	        return 0;
	    }
	}
	
	
	@Transactional
	public int insert(String tab,BigDecimal cr_dist_code,BigDecimal cr_mand_code, BigDecimal  cr_vcode,int bookingid,int crop,String cr_no,int variety,BigDecimal kh_no,String cr_sno,Date date,String obj,String part_key,String user,String clientip) {
//		int crNo= Integer.parseInt(cr_no);
	
		String code= obj.split(" - ")[1];
		int cod= Integer.parseInt(code);
		System.out.println(cr_dist_code +" - "+cr_mand_code +" - "+cr_vcode +" - "+bookingid +" - "+crop +" - "+cr_no +" - "+variety +" - "+kh_no +" - "+
				cr_sno +" - "+date +" - "+cod +" - "+part_key +" - "+user +" - "+clientip );
		
		String qry="insert into vro_obj_details (cr_dist_code,cr_mand_code,cr_vcode,bookingid,cr_crop,cr_no,variety,kh_no,cr_sno,cr_sow_dt,landparcel_obj,part_key,crt_user,clientip) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		entityManager.createNativeQuery(qry).setParameter(1, cr_dist_code).setParameter(2, cr_mand_code)
		.setParameter(3, cr_vcode).setParameter(4, bookingid).setParameter(5, crop).setParameter(6, cr_no).setParameter(7, variety)
		.setParameter(8, kh_no).setParameter(9, cr_sno).setParameter(10, date).setParameter(11, cod)
		.setParameter(12, part_key).setParameter(13, user).setParameter(14, clientip).executeUpdate();
		
		System.out.println(qry);
		return 0;
	}
	
	@Transactional
	public int update(String partitionName,BigDecimal cr_dist_code,int bookingid,int crop,String cr_no, BigDecimal  cr_vcode, int variety) {
		
		String qry="update "+ partitionName +" set vro_obj_verify='Y',dt_obj_verify= now() where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=?";
		
		entityManager.createNativeQuery(qry).setParameter(1, cr_dist_code).setParameter(2, bookingid)
		.setParameter(3, crop).setParameter(4, cr_no).setParameter(5, cr_vcode).setParameter(6, variety).executeUpdate();
		
		return 0;
		
		
	}

}