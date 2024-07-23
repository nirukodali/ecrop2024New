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
import com.ecrops.entity.LockEntity;

@Component
public class LockRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Transactional
	public List<LockEntity> details(String tab,int year,String season,String userid,String surveyNo,String khataNo,String aadhaarNo) {
		System.out.println("lockedddddddddddddd");
		String qry=" select a.cr_no,ct.cultdesc_loclang,cultdesc,ftype_short,variety,v.varietyname,occupname,occupfname,cr_sow_date,'XXXXXXXX'||substr(cr_farmeruid,9) as farmeruid,\r\n"
				+ "   a.cr_dist_code,a.cr_mand_code,a.cr_vcode,b.wbdname,b.wbmname,b.wbvname,coalesce(lockedext,0) as lockedext,a.kh_no,a.bookingid,a.vaaauth,a.vroauth,a.ekyc,cn.naturedesc,cr_farmeruid as uid,\r\n"
				+ "   a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,a.cropins,c.cropname,w.wsrcdesc, cr_season||','||cr_year as cropduration, a.ins_scheme,a.smsmobileno\r\n"
				+ "  from "+tab+" a,wbvillage_mst_v b,waterresources w ,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn,cropseed_scheme cs \r\n"
				+ "  where  a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id and\r\n"
				+ "  a.variety=v.varietycode and a.cr_crop=c.cropid and a.cr_w_draw=w.wsrcid and cr_year="+year+" and (a.mismatch not in('D') or a.mismatch is null) and \r\n"
				+ "  a.cropseed_scheme=cs.cropschtype and cr_season='"+season+"' and lockedext is null and updatedby= '"+userid+"' ";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
//		if() {
//			qry+= " and (a.mismatch not in('D') or a.mismatch is null)";
//		}
		
//		if() {
//			qry+=" and (harvest_date>=(to_char(now(),'YYYY-MM-DD')::date)+7 or cropclass in ('B','P'))";
//		}
		
		List<LockEntity> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				LockEntity pojos = new LockEntity();

				pojos.setCr_no((String) row[0]);
				pojos.setCultdesc_loclang((String) row[1]);
				pojos. setCultdesc((String) row[3]);
				pojos.setFtype_short((String) row[3]);
				pojos.setVariety((int) row[4]);
				pojos.setVarietyname((String) row[5]);
				pojos.setOccupname((String) row[6]);
				pojos.setOccupfname((String) row[7]);
				pojos.setCr_sow_date((Date) row[8]);
				pojos.setCr_farmeruid((String) row[9]);
				pojos.setCr_dist_code((BigDecimal) row[10]);
				pojos.setCr_mand_code((BigDecimal) row[11]);
				pojos.setCr_vcode((int) row[12]);
				pojos.setWbdname((String) row[13]);
				pojos.setWbmname((String) row[14]);
				pojos.setWbvname((String) row[15]);
				pojos.setUnlockedext((BigDecimal) row[16]);
				pojos.setKh_no((BigDecimal) row[17]);
				pojos.setBookingid((int) row[18]);
				pojos.setVaaauth((Character) row[19]);
				pojos.setVroauth((Character) row[20]);
				pojos.setEkyc((Character) row[21]);
				pojos.setNaturedesc((String) row[22]);
				pojos.setUid((String) row[23]);
				pojos.setCr_sno((String) row[24]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[25]);
				pojos.setCr_crop((int) row[26]);
				pojos.setCropins((Character) row[27]);
				pojos.setCropname((String) row[28]);
				pojos.setWsrcdesc((String) row[29]);
				pojos.setCropduration((String) row[30]);
				pojos.setIns_scheme((String) row[31]);
				pojos.setSmsmobileno((BigDecimal) row[32]);
				

				data.add(pojos);
			}
		}
		return data;
	}
	
	
	@Transactional
	public List<LockEntity> detailsUnlock(String tab,int year,String season,String userid,String surveyNo,String khataNo,String aadhaarNo) {
		System.out.println("--------------------------");
		String qry=" select a.cr_no,ct.cultdesc_loclang,cultdesc,ftype_short,variety,v.varietyname,occupname,occupfname,cr_sow_date,'XXXXXXXX'||substr(cr_farmeruid,9) as farmeruid,\r\n"
				+ "   a.cr_dist_code,a.cr_mand_code,a.cr_vcode,b.wbdname,b.wbmname,b.wbvname,coalesce(unlockedext,0) as unlockedext,a.kh_no,a.bookingid,a.vaaauth,a.vroauth,a.ekyc,cn.naturedesc,cr_farmeruid as uid,\r\n"
				+ "   a.cr_sno,a.cr_mix_unmix_ext,a.cr_crop,a.cropins,c.cropname,w.wsrcdesc, cr_season||','||cr_year as cropduration, a.ins_scheme,a.smsmobileno,harvest_date-cr_sow_date as crop_duration_days, v.releasedays \r\n"
				+ "  from "+tab+" a,wbvillage_mst_v b,waterresources w ,cropnames c,cr_variety_master v,cultivator_types ct,cropnature cn,cropseed_scheme cs \r\n"
				+ "  where  a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode and a.cultivator_type=ct.culttype and a.cr_sow_type=cn.id and\r\n"
				+ "  a.variety=v.varietycode and a.cr_crop=c.cropid and a.cr_w_draw=w.wsrcid and cr_year="+year+"  and \r\n"
				+ "  a.cropseed_scheme=cs.cropschtype and cr_season='"+season+"' and unlockedext is null and updatedby= '"+userid+"' ";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
		if(season.equalsIgnoreCase("K")) {
			qry+= " and  (harvest_date >= (CURRENT_DATE + 7) OR cropclass IN ('B', 'P'))";
		}
//		else {
//			qry+=" and (harvest_date>=(to_char(now(),'YYYY-MM-DD')::date)+7 or cropclass in ('B','P'))";
//		}
		
		List<LockEntity> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				LockEntity pojos = new LockEntity();

				pojos.setCr_no((String) row[0]);
				pojos.setCultdesc_loclang((String) row[1]);
				pojos. setCultdesc((String) row[3]);
				pojos.setFtype_short((String) row[3]);
				pojos.setVariety((int) row[4]);
				pojos.setVarietyname((String) row[5]);
				pojos.setOccupname((String) row[6]);
				pojos.setOccupfname((String) row[7]);
				pojos.setCr_sow_date((Date) row[8]);
				pojos.setCr_farmeruid((String) row[9]);
				pojos.setCr_dist_code((BigDecimal) row[10]);
				pojos.setCr_mand_code((BigDecimal) row[11]);
				pojos.setCr_vcode((int) row[12]);
				pojos.setWbdname((String) row[13]);
				pojos.setWbmname((String) row[14]);
				pojos.setWbvname((String) row[15]);
				pojos.setUnlockedext((BigDecimal) row[16]);
				pojos.setKh_no((BigDecimal) row[17]);
				pojos.setBookingid((int) row[18]);
				pojos.setVaaauth((Character) row[19]);
				pojos.setVroauth((Character) row[20]);
				pojos.setEkyc((Character) row[21]);
				pojos.setNaturedesc((String) row[22]);
				pojos.setUid((String) row[23]);
				pojos.setCr_sno((String) row[24]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[25]);
				pojos.setCr_crop((int) row[26]);
				pojos.setCropins((Character) row[27]);
				pojos.setCropname((String) row[28]);
				pojos.setWsrcdesc((String) row[29]);
				pojos.setCropduration((String) row[30]);
				pojos.setIns_scheme((String) row[31]);
				pojos.setSmsmobileno((BigDecimal) row[32]);
				pojos.setCrop_duration_days((int) row[33]);
				pojos.setReleasedays((int) row[34]);
				
				data.add(pojos);
			}
		}
		return data;
	}
	
	@Transactional
	public int update( String tab,String cr_no, int reason, int bookingid,String farmeruid,int kh_no,BigDecimal mix ,int cr_crop,int variety,BigDecimal ext) {
			System.out.println("repoooooooooo");
			System.out.println(mix);
			System.out.println(reason);
			System.out.println(bookingid);
			System.out.println(farmeruid);
			System.out.println(kh_no);
			System.out.println();
			System.out.println(cr_crop);
			System.out.println(variety);
		String qry=" update "+tab+" set dt_lock_ext=now(),lockedext=?, lock_reason=? where bookingid=? \r\n"
				+ "  and cr_farmeruid=? and kh_no=? and cr_mix_unmix_ext=? and cr_crop=? and variety=?\r\n";
		
		entityManager.createNativeQuery(qry).setParameter(1, ext).setParameter(2, reason)
		.setParameter(3, bookingid).setParameter(4, farmeruid).setParameter(5, kh_no).setParameter(6, mix)
		.setParameter(7, cr_crop).setParameter(8, variety).executeUpdate();

		System.out.println(qry);
		return 0;
	}
	
	@Transactional
	public int updateUnlock( String tab,String cr_no, int reason, int bookingid,String farmeruid,int kh_no,BigDecimal mix ,int cr_crop,int variety,BigDecimal ext) {
			System.out.println("repoooooooooo");
			System.out.println(mix);
			System.out.println(reason);
			System.out.println(bookingid);
			System.out.println(farmeruid);
			System.out.println(kh_no);
			System.out.println();
			System.out.println(cr_crop);
			System.out.println(variety);
		String qry=" update "+tab+" set dt_lock_ext=now(),unlockedext=?, unlock_reason=? where bookingid=? \r\n"
				+ "  and cr_farmeruid=? and kh_no=? and cr_mix_unmix_ext=? and cr_crop=? and variety=?\r\n";
		
		entityManager.createNativeQuery(qry).setParameter(1, ext).setParameter(2, reason)
		.setParameter(3, bookingid).setParameter(4, farmeruid).setParameter(5, kh_no).setParameter(6, mix)
		.setParameter(7, cr_crop).setParameter(8, variety).executeUpdate();

		System.out.println(qry);
		return 0;
	}

}
