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

import com.ecrops.dto.DeceasedFarmerDto;
import com.ecrops.entity.DeceasedFarmer;

@Component
public class DeceasedFarmerRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<DeceasedFarmer> getDetails(String partitionName, String crd_orgtab, int cropyear, String season,
			int vcode, String surveyNo, String khataNo, String aadhaarNo) {
		System.out.println("kh_no---->" + khataNo);
		System.out.println("cr_snno--->" + surveyNo);
		System.out.println("aadhaarNo-->" + aadhaarNo);

		String qry = "select cs.cropschdesc,cropseed_scheme,cr_mix_unmix_ext,seed_production,irrmethod,irm.irgdesc,cr_w_draw,\r\n"
				+ "oc_name,oc_fname,cr_sno,kh_no,occupname,occupfname,cr_vcode,bookingid,cr_crop,cr_no,cr_sow_date,variety,cr_farmeruid \r\n"
				+ "from  " + partitionName + " a,cropirrgmethod_master irm ,cropseed_scheme cs \r\n"
				+ " where cs.cropschtype=a.cropseed_scheme and  irrmethod=irm.irgcode and cr_vcode=" + vcode
				+ " and cr_year=" + cropyear + " and cr_season='" + season + " '  and vroauth ='Y' and vaaauth ='Y' "
				+ "and ekyc is null and (  bookingid not in (select bookingid from " + crd_orgtab
				+ " where correctiontype='8'  and cr_year=+" + cropyear + " and cr_season='" + season
				+ "' and cr_vcode=" + vcode + " )) \r\n";

		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}

		List<DeceasedFarmer> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				DeceasedFarmer pojos = new DeceasedFarmer();

				pojos.setCropschdesc((String) row[0]);
				pojos.setCropseed_scheme((Character) row[1]);
				pojos.setCr_mix_unmix_ext((BigDecimal) row[2]);
				pojos.setSeed_production((int) row[3]);
				pojos.setIrrmethod((int) row[4]);
				pojos.setIrgdesc((String) row[5]);
				pojos.setCr_w_draw((BigDecimal) row[6]);
				pojos.setOc_name((String) row[7]);
				pojos.setOc_fname((String) row[8]);
				pojos.setCr_sno((String) row[9]);
				pojos.setKh_no((BigDecimal) row[10]);
				pojos.setOccupname((String) row[11]);
				pojos.setOccupfname((String) row[12]);
				pojos.setCr_vcode((int) row[13]);
				pojos.setBookingid((int) row[14]);
				pojos.setCr_crop((int) row[15]);
				pojos.setCr_no((String) row[16]);
				pojos.setCr_sow_date((Date) row[17]);
				pojos.setVariety((int) row[18]);
				pojos.setCr_farmeruid((String) row[19]);

				data.add(pojos);
			}
		}
		return data;
		
	}
	
//	
//	@Transactional
//	public int insertDeceasedFarmer2(String tab2,String booking,Date cr_sow_date,String crop,int vcode,String cr_no,String vari,int year,String season,String surveyNo,String khataNo,String aadhaarNo) {
//		int bookingid= Integer.parseInt(booking);
//		int cr_crop = Integer.parseInt(crop);
//		int variety = Integer.parseInt(vari);
//		
//		
//		String qry=" INSERT INTO ekycuid_details(\r\n"
//				+ "part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, \r\n"
//				+ " cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, \r\n"
//				+ " cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age,\r\n"
//				+ " soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, \r\n"
//				+ "  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, \r\n"
//				+ " dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type,\r\n"
//				+ " cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, \r\n"
//				+ " approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch,\r\n"
//				+ " claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify)\r\n"
//				+ "  SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm,\r\n"
//				+ " ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'7',now(), vao_verify  \r\n"
//				+ "FROM "+tab2+" where  bookingid=? and cr_sow_date=? and cr_crop=? and cr_vcode=? and cr_no=? and variety=? and cr_year=? and cr_season=?";
//	
//		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
//			qry += " and cr_sno='" + surveyNo + "'  ";
//		}
//		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
//			qry += " and kh_no=" + khataNo + "  ";
//		}
//		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
//			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
//		}
//				
//				entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, cr_sow_date).setParameter(3, cr_crop)
//		.setParameter(4, vcode).setParameter(5, cr_no).setParameter(6, variety).setParameter(7, year)
//		.setParameter(8, season).executeUpdate();
//				
//		
//		
//		
//	return 0;
//	}
	
	@Transactional 
	public int insertDeceasedFarmer(String partkey,String booking,String dcode,int vcode, Date dateofdeath,String relation,String crop,String vari,String cr_no,Date cr_sow_date,String userid,String address,int cr_year,String season,String khno,String cr_sno, int mcode) {
		int bookingid = Integer.parseInt(booking);
		BigDecimal cr_dist_code= new BigDecimal(dcode);
		int cr_crop= Integer.parseInt(crop);
		int variety= Integer.parseInt(vari);
		BigDecimal kh_no= new BigDecimal(khno);
		BigDecimal cr_mand_code= new BigDecimal(mcode);
		String qry="INSERT INTO ecrop2024.deceased_legal_details( part_key, bookingid, cr_dist_code, cr_vcode, date_of_death, relation,cr_crop,variety,\r\n"
				+ "cr_no,cr_sow_date, updatedby, ipaddress ,cr_year, cr_season,kh_no,cr_sno,cr_mand_code) \r\n"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\r\n"
				+ "";
		entityManager.createNativeQuery(qry).setParameter(1, partkey).setParameter(2, bookingid)
		.setParameter(3, cr_dist_code).setParameter(4, vcode).setParameter(5, dateofdeath)
		.setParameter(6, relation).setParameter(7, cr_crop).setParameter(8, variety).setParameter(9, cr_no)
		.setParameter(10, cr_sow_date).setParameter(11, userid).setParameter(12, address)
		.setParameter(13, cr_year).setParameter(14, season).setParameter(15, kh_no).setParameter(16, cr_sno).setParameter(17, cr_mand_code).executeUpdate();
		
		return 1;
	}
	

	
	@Transactional
	public int insert1(String crd_booking_orgtab,String tab,String surveyNo,String khataNo,String aadhaarNo,String booking,int vcode,int year,String season) {
		int bookingid= Integer.parseInt(booking);
		
		String qry="INSERT INTO "+crd_booking_orgtab+" (part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src , occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, ctype, variety, irrmethod,  anubhavadar_name, cultivator_type, oldbookingid,  landownership_type, refbookingid, digitally_signed,cr_farmeruid_old,correctiontype, correction_date,downloadtime, ccrcid, rid, crop_insured, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype,anubhavadar_extent,dt_crt, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking,insertedby, dataprep_bkid, ips_flag) \r\n"
									  + "SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src , occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, ctype, variety, irrmethod,  anubhavadar_name,  cultivator_type, oldbookingid, landownership_type, refbookingid, digitally_signed,cr_farmeruid_old,'8',now(),					  downloadtime, ccrcid, rid, crop_insured, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype,anubhavadar_extent,dt_crt, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking,insertedby, dataprep_bkid, ips_flag  \r\n"
				+ "FROM  "+tab+"  where  bookingid=?   and  cr_vcode=?   and cr_year=? and cr_season=?  ";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, vcode)
		.setParameter(3, year).setParameter(4, season).executeUpdate();
		
		return 0;
	}
	
	@Transactional
	public int insert2(String crd_orgtab,String tab2,String surveyNo,String khataNo,String aadhaarNo,String booking,int vcode,int year,String season,String crop,String cr_no,Date date,String vari) {
		int bookingid= Integer.parseInt(booking);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		
		String qry="INSERT INTO "+crd_orgtab+"( part_key, bookingid,mobileno, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type,\r\n"
				+ " cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode,\r\n"
				+ " cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age,\r\n"
				+ " soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email,  gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,\r\n"
				+ "  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq,\r\n"
				+ " dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type,\r\n"
				+ " cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold,\r\n"
				+ " approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,\r\n"
				+ " vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) \r\n"
				+ " SELECT part_key, bookingid,mobileno, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status,\r\n"
				+ " cr_farmeruid, owner_tenant, occupname, occupfname, email,  gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, \r\n"
				+ "dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, \r\n"
				+ "ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,\r\n"
				+ "vaa_txn, vro_txn, vro_verify, dt_vroverify,'8',now(), vao_verify  \r\n"
				+ "FROM " + tab2 + " where  bookingid=?  and cr_crop=? and cr_vcode=? and cr_no=? and variety=?  \r\n"
				+ "  and cr_year=? and cr_season=? and cr_sow_date=?";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2,cr_crop)
		.setParameter(3, vcode).setParameter(4, cr_no).setParameter(5, variety).setParameter(6, year)
		.setParameter(7, season).setParameter(8, date).executeUpdate();
		
		
		return 0;
	}
	
	@Transactional
	public int update1(String tab,String surveyNo,String khataNo,String aadhaarNo,String booking,int vcode,int year,String season,String crop,String cr_no,Date date,String vari,
			String uid,String name,String fname,String num) {
		System.out.println("updateeee");
		int bookingid= Integer.parseInt(booking);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		BigDecimal number= new BigDecimal(num);
		
		String qry="  UPDATE "+ tab+" set cr_farmeruid=?,occupname=?,occupfname=?,mobileno=?  where bookingid=?  and cr_crop=? and cr_vcode=? and cr_no=? and variety=? and cr_year=?  and cr_season=? and cr_sow_date=?";
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
			
			entityManager.createNativeQuery(qry).setParameter(1, uid).setParameter(2,name)
			.setParameter(3, fname).setParameter(4, number).setParameter(5, bookingid).setParameter(6,cr_crop)
			.setParameter(7, vcode).setParameter(8, cr_no).setParameter(9, variety).setParameter(10, year)
			.setParameter(11, season).setParameter(12, date).executeUpdate();
		
	return 0;
	}
	
	@Transactional
	public int update2(String tab,String surveyNo,String khataNo,String aadhaarNo,String booking,int vcode,int year,String season,String crop,String cr_no,Date date,String vari,
			String uid,String name,String fname,String num,String kh_n,String cr_sno) {
		System.out.println("updateeee");
		int bookingid= Integer.parseInt(booking);
		int kh_no= Integer.parseInt(kh_n);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		BigDecimal number= new BigDecimal(num);
		
		String qry=" UPDATE "+tab+" set cr_farmeruid=?,occupname=?,occupfname=?,mobileno=?,occup_change='D'  where bookingid=?   and cr_vcode=? and cr_year=?  and cr_season=? and kh_no=? and cr_sno=?";
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
			
			entityManager.createNativeQuery(qry).setParameter(1, uid).setParameter(2,name)
			.setParameter(3, fname).setParameter(4, number).setParameter(5, bookingid)
			.setParameter(6, vcode).setParameter(7, year).setParameter(8, season).setParameter(9, kh_no).setParameter(10, cr_sno).executeUpdate();
		
	return 0;
	}
	
	

}