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
import com.ecrops.entity.EditUid;

@Component
public class EditUidRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<EditUid> getDetails(String partitionName, String crd_orgtab, int cropyear, String season, int vcode,
			String surveyNo, String khataNo, String aadhaarNo) {
		System.out.println("kh_no---->" + khataNo);
		System.out.println("cr_snno--->" + surveyNo);
		System.out.println("aadhaarNo-->" + aadhaarNo);

		String qry = "select occupname,occupfname,oc_name,oc_fname,cr_sno,kh_no,cr_vcode,bookingid,cr_crop,cr_no,cr_sow_date,variety,cr_farmeruid from  "
				+ partitionName + " where cr_vcode=" + vcode + " and cr_year=" + cropyear + " and cr_season='"
				+ season + "' and vaaauth ='Y' and vroauth ='Y'  and ekyc is null  \r\n"
				+ " and (cr_farmeruid_old is null or bookingid not in (select bookingid from ekycuid_details where correctiontype='7' and  cr_vcode="
				+ vcode + "  ))";

		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}

		List<EditUid> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();
System.out.println(qry);

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				EditUid pojos = new EditUid();

				pojos.setOccupname((String) row[0]);
				pojos.setOccupfname((String) row[1]);
				pojos.setOc_name((String) row[2]);
				pojos.setOc_fname((String) row[3]);
				pojos.setCr_sno((String) row[4]);
				pojos.setKh_no((BigDecimal) row[5]);
				pojos.setCr_vcode((int) row[6]);
				pojos.setBookingid((int) row[7]);
				pojos.setCr_crop((int) row[8]);
				pojos.setCr_no((String) row[9]);
				pojos.setCr_sow_date((Date) row[10]);
				pojos.setVariety((int) row[11]);
				pojos.setCr_farmeruid((String) row[12]);

				data.add(pojos);
			}
		}
		return data;

	}

	@Transactional
	public int update(String tab2, String selectedValue, String value, String surveyNo, String khataNo,
			String aadhaarNo, String farmeruid, BigDecimal aadhaar, int bookingid, Date cr_sow_date, int crop,
			int vcode, int vari, int year, String season, String cr_no) {

		System.out.println("-----" + farmeruid);
		String qry = " UPDATE " + tab2 + " set  cr_farmeruid_old=?,cr_farmeruid=? where bookingid=? \r\n"
				+ " and cr_sow_date=? and cr_crop=? and cr_vcode=? and cr_no=? and variety=?   and cr_year=?  and cr_season=? ";

		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}

		entityManager.createNativeQuery(qry).setParameter(1, farmeruid).setParameter(2, aadhaar)
				.setParameter(3, bookingid).setParameter(4, cr_sow_date).setParameter(5, crop).setParameter(6, vcode)
				.setParameter(7, cr_no).setParameter(8, vari).setParameter(9, year).setParameter(10, season)
				.executeUpdate();

		return 0;
	}

	@Transactional
	public int insert(String tab1, String selectedValue, String value, String surveyNo, String khataNo,
			String aadhaarNo, int bookingid, Date cr_sow_date, int crop,
			int vcode, int vari, int year, String season, String cr_no) {

		String qry = " INSERT INTO ekycuid_details( \r\n"
				+ " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, \r\n"
				+ "cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, \r\n"
				+ "cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age,\r\n"
				+ "soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, \r\n"
				+ " dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, \r\n"
				+ "dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type,\r\n"
				+ "cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, \r\n"
				+ "approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch,\r\n"
				+ "claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, \r\n"
				+ "smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) \r\n"
				+ "SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type,\r\n"
				+ " cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, \r\n"
				+ " dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr,\r\n"
				+ " tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty,\r\n"
				+ " stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, \r\n"
				+ " qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd,\r\n"
				+ " oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator,\r\n"
				+ " crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng,\r\n"
				+ " cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, \r\n"
				+ " vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm,\r\n"
				+ "ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'7',now(), vao_verify   FROM \r\n"
				+ " " + tab1 + " where  bookingid=? and cr_sow_date=? and cr_crop=? and cr_vcode=? and cr_no=? and variety=? and cr_year=? and cr_season=? ";

		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo != "" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}

		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, cr_sow_date)
		.setParameter(3, crop).setParameter(4, vcode).setParameter(5, cr_no).setParameter(6, vari)
		.setParameter(7, year).setParameter(8, season).executeUpdate();
		
		
		return 0;
	}

}
