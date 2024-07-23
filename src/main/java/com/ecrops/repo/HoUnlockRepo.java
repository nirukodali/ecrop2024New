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

//import com.ecrops.entity.HoUnlock;
import com.ecrops.entity.LockDropdown;
import com.ecrops.entity.LockEntity;

@Component
public class HoUnlockRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
//	@Transactional
//	public List<HoUnlock> getDetails(String tab,String season,int year,int mcode){
//		
//		String qry=" select a.cr_no,ct.cultdesc_loclang,cultdesc,ftype_short,variety,v.varietyname,occupname,occupfname,cr_sow_date,\r\n"
//				+ " b.wbdname,b.wbmname,b.wbvname,a.kh_no,a.bookingid,cn.naturedesc,cr_farmeruid ,harvest_date,updatedby,\r\n"
//				+ " a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,a.cropins,c.cropname,w.wsrcdesc, a.ins_scheme,a.smsmobileno,a.unlock_reason,a.unlockedext\r\n"
//				+ " from "+tab+" a,wbvillage_mst_v b,waterresources w ,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn,cropseed_scheme cs \r\n"
//				+ " where a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and cr_season='"+season+"' and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id and\r\n"
//				+ " a.variety=v.varietycode and a.cr_crop=c.cropid and a.cr_w_draw=w.wsrcid and cr_year="+year+" and (a.mismatch not in('D') or a.mismatch is null) and c.cropnature in ('H','R') and \r\n"
//				+ " c.active='A' and w.active='A' and a.cropseed_scheme=cs.cropschtype and unlockedext is not null and a.mcode in (select mcode from homandals_v where divcode="+mcode+") \r\n"
//				+ " and mao_unlock_appr is null order by wbvname,updatedby,bookingid";
//		
//		List<HoUnlock> data = new ArrayList<>();
//		Query sql = entityManager.createNativeQuery(qry);
//		List<Object> objects = sql.getResultList();
//
//		if (objects != null && objects.size() > 0) {
//
//			for (Object patta : objects) {
//
//				Object[] row = (Object[]) patta;
//
//				HoUnlock pojos = new HoUnlock();
//				
//				pojos.setCr_no((String) row[0]);
//				pojos.setCultdesc_loclang((String) row[1]);
//				pojos. setCultdesc((String) row[3]);
//				pojos.setFtype_short((String) row[3]);
//				pojos.setVariety((int) row[4]);
//				pojos.setVarietyname((String) row[5]);
//				pojos.setOccupname((String) row[6]);
//				pojos.setOccupfname((String) row[7]);
//				pojos.setCr_sow_date((Date) row[8]);
//				pojos.setWbdname((String) row[9]);
//				pojos.setWbmname((String) row[10]);
//				pojos.setWbvname((String) row[11]);
//				pojos.setKh_no((BigDecimal) row[12]);
//				pojos.setBookingid((int) row[13]);
//				pojos.setNaturedesc((String) row[14]);
//				pojos.setCr_farmeruid((String) row[15]);
//				pojos.setHarvest_date((Date)row[16]);
//				pojos.setUpdatedby((String)row[17]);
//				pojos.setCr_sno((String) row[18]);
//				pojos.setCr_mix_unmix_ext((BigDecimal) row[19]);
//				pojos.setCr_crop((int) row[20]);
//				pojos.setCropins((String) row[21]);
//				pojos.setCropname((String) row[22]);
//				pojos.setWsrcdesc((String) row[23]);
//				pojos.setIns_scheme((String) row[24]);
//				pojos.setSmsmobileno((BigDecimal) row[25]);
//				pojos.setUnlock_reason((int)row[26]);
//				pojos.setUnlockedext((BigDecimal) row[27]);
//				
//				data.add(pojos);
//			}
//		}
//		return data;
//		
//	}
//	
	
	@Transactional
	public List<LockDropdown> dropdown(){
		String qry= "select code,concat(code,'. ',reason),group_reason from lock_unlock_reasons where group_reason='U' and active='A' order by code";
		List<LockDropdown> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				LockDropdown pojos = new LockDropdown();
				
				pojos.setCode((int) row[0]);
				pojos.setConcat((String) row[1]);
				pojos.setGroup_reason((Character) row[2]);
				data.add(pojos);
			}
		}
				return data;
	}
	
	
	@Transactional
	public int insert(String tab,int bookingid,String uid,BigDecimal kh_no,BigDecimal mix,BigDecimal ext,int crop,int variety) {
		String qry="insert into ecrop2023.cr_unlock_ext (select * from "+ tab+" where bookingid=? and cr_farmeruid=? and kh_no=? and cr_mix_unmix_ext=? and unlockedext=? and \r\n"
				+" cr_crop=? and variety=? and unlockedext is not null and mao_unlock_appr='A')";
		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, uid)
		.setParameter(3, kh_no).setParameter(4, mix).setParameter(5, ext).setParameter(6, crop)
		.setParameter(7, variety).executeUpdate();
		System.out.println(qry);
		return 0;
	}
	
	@Transactional
	public int update(String tab,String appr,Date date,String reason,String remarks,int bookingid,String uid,BigDecimal kh_no,BigDecimal mix,BigDecimal ext,int crop,int variety) {
		System.out.println(reason);
		int n=0;
		if(!reason.equals(null) || reason !="") {
		String num=reason.split(". ")[0];
		n= Integer.parseInt(num);
		System.out.println( n+"---------"+ num);
		}
//		int nu= Integer.parseInt(num);
		
		String qry="update "+tab+" set mao_unlock_appr=?, dt_mao_unlock_appr=now(), harvest_date=?, mao_unlock_rsn=?, mao_unlock_remarks=? \r\n"
				+"where bookingid=? and cr_farmeruid=? and kh_no=? and cr_mix_unmix_ext=? and unlockedext=? and cr_crop=? and variety=?";
	
		entityManager.createNativeQuery(qry).setParameter(1, appr).setParameter(2, date)
				.setParameter(3, n).setParameter(4, remarks).setParameter(5, bookingid).setParameter(6, uid)
		.setParameter(7, kh_no).setParameter(8, mix).setParameter(9, ext).setParameter(10, crop)
		.setParameter(11, variety).executeUpdate();
		
		return 0;
	}
	
	@Transactional
	public int delete(String tab,int bookingid,String uid,BigDecimal kh_no,BigDecimal mix,BigDecimal ext,int crop,int variety) {
		
		String qry=" delete from " + tab + " where bookingid=? and cr_farmeruid=? and kh_no=? and cr_mix_unmix_ext=? and unlockedext=? \r\n"
				+ " and cr_crop=? and variety=? and unlockedext is not null and mao_unlock_appr='A' ";
		
		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, uid)
		.setParameter(3, kh_no).setParameter(4, mix).setParameter(5, ext).setParameter(6, crop)
		.setParameter(7, variety).executeUpdate();
		
		
		return 0;
	}
	
	@Transactional
	public int update2(String tab2,int bookingid,String approval) {
		String qry="update " + tab2 + " set mao_unlock_appr=? where bookingid=?";
		entityManager.createNativeQuery(qry).setParameter(1, approval).setParameter(2, bookingid).executeUpdate();
		
		return 0;
	}

}
