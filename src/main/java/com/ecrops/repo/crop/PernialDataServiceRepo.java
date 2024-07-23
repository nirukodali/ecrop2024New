package com.ecrops.repo.crop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
public class PernialDataServiceRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public String updateVerifyDataDownload(int periCnt, int crDistCode, int crMandCode, int crVcode, int crYear,
			String crSeason) {
		String updateQuery = "UPDATE ecrop2024.verify_datadownload "
				+ "SET peri_data = 'Y', peri_cnt = :periCnt, dt_peri_data = now() "
				+ "WHERE cr_dist_code = :crDistCode AND cr_mand_code = :crMandCode AND cr_vcode = :crVcode "
				+ "AND cr_year = :crYear AND cr_season = :crSeason";

		int updatedRows = 0;
		if (periCnt > 0) {
			Query query = entityManager.createNativeQuery(updateQuery).setParameter("periCnt", periCnt)
					.setParameter("crDistCode", crDistCode).setParameter("crMandCode", crMandCode)
					.setParameter("crVcode", crVcode).setParameter("crYear", crYear).setParameter("crSeason", crSeason);

			updatedRows = query.executeUpdate();
		} else {
			Query query = entityManager.createNativeQuery(updateQuery).setParameter("periCnt", 0)
					.setParameter("crDistCode", crDistCode).setParameter("crMandCode", crMandCode)
					.setParameter("crVcode", crVcode).setParameter("crYear", crYear).setParameter("crSeason", crSeason);

			updatedRows = query.executeUpdate();
		}
		if (updatedRows > 0) {
			return "Successfully Updated";
		} else {
			return "Failed to Update";
		}
	}

	@Transactional
	public String InsertPerinialData(String wbdcode, int villageCode, int cropyear, String season) {

		int count = 0;
		int count2 = 0;
		String status = "";
		if (wbdcode.length() == 1) {
			wbdcode = "0" + wbdcode;
		}

		String insertTableName = "ecrop2024.peri_k2024";
		String insertTableNameWithRabiSeason = "ecrop2024.peri_r2024";
		String selectTable = "cr_details_";
		selectTable = "ecrop" + (cropyear - 1) + "." + selectTable + season + wbdcode + (cropyear - 1);

		if (season.equals("K")) {
			String selectTableWithRabiSeason = "cr_details_";
			selectTableWithRabiSeason = "ecrop" + (cropyear - 1) + "." + selectTableWithRabiSeason + "R" + wbdcode
					+ (cropyear - 1);

			String query1 = "INSERT INTO " + insertTableNameWithRabiSeason
					+ "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, "
					+ "cr_year, cr_season, cr_month, cr_sow_type, cr_crop, "
					+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, "
					+ "cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, "
					+ "wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, "
					+ "updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, "
					+ "cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, "
					+ "data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, "
					+ "tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, "
					+ "cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, "
					+ "avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, "
					+ "damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, "
					+ "sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, "
					+ "qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
					+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, "
					+ "refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, "
					+ "anubhavadar_name, landownership_type, cultivator_type, "
					+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, "
					+ "variety_old, dt_varietycd_upd, actual_cultivator, "
					+ "crop_ins_approval, ins_approved_ext, actual_yield, "
					+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
					+ "crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, "
					+ "uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, "
					+ "auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
					+ "ekyc_add, ekyc_dob, farming_type, seed_production, "
					+ "digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
					+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, "
					+ "smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, "
					+ "diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, "
					+ "vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, "
					+ "pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, "
					+ "exception_catg, superchk_remarks, suprejreason, sms_sent, "
					+ "griev_comp, mark_delet, finalrec, seedprod_agency, "
					+ "crop_status, crop_agency, ack_gen, vaaauthuserid, "
					+ "vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, "
					+ "dt_damage_entry, lock_reason, unlock_reason, iutype, "
					+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, "
					+ "sms_send, oldharvest_date, supercheckts, mao_unlock_appr, "
					+ "dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, "
					+ "ekyc_uid_mismatch, supercheck_appr, extent_check, "
					+ "oldextent, uidmultiple, social_status, grievance_id, "
					+ "extent_upd, monotomixed, dt_sms, excess_booking, sup_status, "
					+ "geo_referred, cr_wsno, rec_id, sjointoccupant, supid, excess_booking_khno,cropname)" + ""

					+ "  SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, "
					+ "cr_year, cr_season, cr_month, cr_sow_type, cr_crop, "
					+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, "
					+ "cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, "
					+ "wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, "
					+ "updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, "
					+ "cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, "
					+ "data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, "
					+ "tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, "
					+ "cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, "
					+ "avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, "
					+ "damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, "
					+ "sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, "
					+ "qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
					+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, "
					+ "refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, "
					+ "anubhavadar_name, landownership_type, cultivator_type, "
					+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, "
					+ "variety_old, dt_varietycd_upd, actual_cultivator, "
					+ "crop_ins_approval, ins_approved_ext, actual_yield, "
					+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
					+ "crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, "
					+ "uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, "
					+ "auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
					+ "ekyc_add, ekyc_dob, farming_type, seed_production, "
					+ "digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
					+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, "
					+ "smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, "
					+ "diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, "
					+ "vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, "
					+ "pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, "
					+ "exception_catg, superchk_remarks, suprejreason, sms_sent, "
					+ "griev_comp, mark_delet, finalrec, seedprod_agency, "
					+ "crop_status, crop_agency, ack_gen, vaaauthuserid, "
					+ "vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, "
					+ "dt_damage_entry, lock_reason, unlock_reason, iutype, "
					+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, "
					+ "sms_send, oldharvest_date, supercheckts, mao_unlock_appr, "
					+ "dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, "
					+ "ekyc_uid_mismatch, supercheck_appr, extent_check, "
					+ "oldextent, uidmultiple, social_status, grievance_id, "
					+ "extent_upd, monotomixed, dt_sms, excess_booking, sup_status, "
					+ "geo_referred, cr_wsno, rec_id, sjointoccupant, supid, excess_booking_khno,ecrop2024.get_crop_name(cr_crop::::integer) as cropname    FROM "
					+ "" + selectTableWithRabiSeason + " WHERE cr_vcode =? AND "
					+ "cr_crop IN (SELECT cropid FROM cropnames WHERE cropclass IN ('B','P') AND "
					+ "harvest_date >= NOW())";

			Query pst = entityManager.createNativeQuery(query1);
			pst.setParameter(1, villageCode);
			count2 = pst.executeUpdate();
		}

		String query2 = "INSERT INTO " + insertTableName
				+ "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, "
				+ "cr_year, cr_season, cr_month, cr_sow_type, cr_crop, "
				+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, "
				+ "cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, "
				+ "wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, "
				+ "updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, "
				+ "cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, "
				+ "data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, "
				+ "tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, "
				+ "cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, "
				+ "avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, "
				+ "damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, "
				+ "sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, "
				+ "qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
				+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, "
				+ "refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, "
				+ "anubhavadar_name, landownership_type, cultivator_type, "
				+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, "
				+ "variety_old, dt_varietycd_upd, actual_cultivator, "
				+ "crop_ins_approval, ins_approved_ext, actual_yield, "
				+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
				+ "crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, "
				+ "uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, "
				+ "auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
				+ "ekyc_add, ekyc_dob, farming_type, seed_production, "
				+ "digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
				+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, "
				+ "smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, "
				+ "diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, "
				+ "vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, "
				+ "pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, "
				+ "exception_catg, superchk_remarks, suprejreason, sms_sent, "
				+ "griev_comp, mark_delet, finalrec, seedprod_agency, "
				+ "crop_status, crop_agency, ack_gen, vaaauthuserid, "
				+ "vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, "
				+ "dt_damage_entry, lock_reason, unlock_reason, iutype, "
				+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, "
				+ "sms_send, oldharvest_date, supercheckts, mao_unlock_appr, "
				+ "dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, "
				+ "ekyc_uid_mismatch, supercheck_appr, extent_check, "
				+ "oldextent, uidmultiple, social_status, grievance_id, "
				+ "extent_upd, monotomixed, dt_sms, excess_booking, sup_status, "
				+ "geo_referred, cr_wsno, rec_id, sjointoccupant, supid, excess_booking_khno,cropname)" + ""

				+ "  SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, "
				+ "cr_year, cr_season, cr_month, cr_sow_type, cr_crop, "
				+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, "
				+ "cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, "
				+ "wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, "
				+ "updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, "
				+ "cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, "
				+ "data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, "
				+ "tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, "
				+ "cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, "
				+ "avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, "
				+ "damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, "
				+ "sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, "
				+ "qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
				+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, "
				+ "refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, "
				+ "anubhavadar_name, landownership_type, cultivator_type, "
				+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, "
				+ "variety_old, dt_varietycd_upd, actual_cultivator, "
				+ "crop_ins_approval, ins_approved_ext, actual_yield, "
				+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
				+ "crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, "
				+ "uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, "
				+ "auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
				+ "ekyc_add, ekyc_dob, farming_type, seed_production, "
				+ "digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
				+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, "
				+ "smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, "
				+ "diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, "
				+ "vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, "
				+ "pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, "
				+ "exception_catg, superchk_remarks, suprejreason, sms_sent, "
				+ "griev_comp, mark_delet, finalrec, seedprod_agency, "
				+ "crop_status, crop_agency, ack_gen, vaaauthuserid, "
				+ "vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, "
				+ "dt_damage_entry, lock_reason, unlock_reason, iutype, "
				+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, "
				+ "sms_send, oldharvest_date, supercheckts, mao_unlock_appr, "
				+ "dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, "
				+ "ekyc_uid_mismatch, supercheck_appr, extent_check, "
				+ "oldextent, uidmultiple, social_status, grievance_id, "
				+ "extent_upd, monotomixed, dt_sms, excess_booking, sup_status, "
				+ "geo_referred, cr_wsno, rec_id, sjointoccupant, supid, excess_booking_khno,ecrop2024.get_crop_name(cr_crop::::integer) as cropname    FROM "
				+ "" + selectTable + " WHERE cr_vcode =? AND "
				+ "cr_crop IN (SELECT cropid FROM cropnames WHERE cropclass IN ('B','P') AND "
				+ "harvest_date >= NOW())";

		Query pst1 = entityManager.createNativeQuery(query2);
		pst1.setParameter(1, villageCode);
		count = pst1.executeUpdate();
		if (count > 0 || count2 > 0) {
			status = "Data Successfully Inserted Into table";
		} else {
			status = "No Data Available For This Village";
		}
		return status;
	}

}
