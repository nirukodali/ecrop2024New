package com.ecrops.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ecrops.repo.AppUserRepo.HoMandalsMapProj;

@Component
public class AddHodetailsRepository {
	
	@Autowired private AppUserRepo appUserRepo;

	public AddHodetailsRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int addHoDetails(Integer district,Integer smcode,Integer hmcode) {
		
		List<HoMandalsMapProj> list = appUserRepo.checkRecord( district, smcode, hmcode);
		System.out.println("addHo=>"+list.size());
		
		int executeUpdate = 0;
		
		if(list.size() == 0) {
			String insertQry ="insert into homandalsmap ( dcode,mcode,hqcode,status,dt_crt)  VALUES ( ?, ?, ?, ?, now())";
			Query sql = entityManager.createNativeQuery(insertQry);
			sql.setParameter(1,  district);
			sql.setParameter(2,  hmcode);
			sql.setParameter(3,  smcode);
			sql.setParameter(4, "A");
			executeUpdate = sql.executeUpdate();
		}		
		return executeUpdate;
	}
	
	@Transactional
	public int updatedetails(Integer district,Integer hmcode) {
		String updateQry ="update user_registration set status='A' ,webloginstatus='A' where type_user='22' and district=? and blockortehsil=? ";
		Query sql = entityManager.createNativeQuery(updateQry);
		sql.setParameter(1, district.toString());		
		sql.setParameter(2, hmcode.toString());
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
	}
	
	@Transactional
	public int insertdetails(Integer district,Integer hmcode) {
		String insertQry ="INSERT INTO public.user_registration(district, blockortehsil,userid,password, retype_password,  datetime, encpassword,  status,webloginstatus,type_user)"
				+ "VALUES (?, ?,  ?, '8177da925c9dccb757ae4611dc3e58a4','8177da925c9dccb757ae4611dc3e58a4', now(), '8177da925c9dccb757ae4611dc3e58a4', 'A', 'A','22')";
		Query sql = entityManager.createNativeQuery(insertQry);
		sql.setParameter(1, district);		
		sql.setParameter(2, hmcode);
		sql.setParameter(3, "HO_"+hmcode);
		int executeUpdate = sql.executeUpdate();
		return executeUpdate;
	}
	
	@Transactional
	public int deletedetails(Integer district,Integer hmcode,Integer mcode) {
		String insertQry =" DELETE from homandalsmap where dcode=? and hqcode=? and mcode=? ";
		Query sql = entityManager.createNativeQuery(insertQry);
		
		sql.setParameter(1, district);		
		sql.setParameter(2, hmcode);
		sql.setParameter(3, mcode);
		int executedelete = sql.executeUpdate();
		System.out.println("delete" +sql);
		return executedelete;
	} 
}
