package com.ecrops.repo.crop;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.dto.crop.response.McodeMnameSuperCheck;
import com.ecrops.entity.crop.CrCropDetNewV;

@Service
@Transactional
@Component
public class SuperCheckAdaService {

	@Autowired
	private SuperCheckAdaRepository checkRecordsIntf;

	@PersistenceContext
	EntityManager entityManager;

	public List<McodeMnameSuperCheck> getMcodeAndMname(String wbdcode, String season, String activeyear, int cropyear,
			String userid, int dcode, String role) {
		if (wbdcode == null || wbdcode.isEmpty() || season == null || season.isEmpty() || activeyear == null
				|| activeyear.isEmpty() || userid == null || userid.isEmpty()) {
			throw new IllegalArgumentException("Input parameters cannot be null or empty.");
		}

		try {
			if (cropyear <= 0) {
				throw new IllegalArgumentException("Invalid cropyear value: " + cropyear);
			}

			if (dcode <= 0) {
				throw new IllegalArgumentException("Invalid dcode value: " + dcode);
			}

			int dcodes = 0;
			if (role.equals("46")) {
				dcodes = checkRecordsIntf.getDcode(Integer.parseInt(wbdcode));
			} else {
				dcodes = dcode;
			}

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab = "cr_details_" + season + wbdcode + cropyear;
			if (activeyear.equals(String.valueOf(cropyear))) {
				tab = "ecrop" + activeyear + "." + tab;
			}
//			String tab = "cr_details_K042023";
//			if (activeyear.equals(String.valueOf(cropyear))) {
//				tab = "ecrop2023." + tab;
//			}


			String qry1 = "SELECT DISTINCT(mcode), mname FROM mandal_2011_cs WHERE dcode = ? AND mcode IN "
					+ "(SELECT DISTINCT mcode FROM " + tab + " WHERE cr_dist_code = ? "
					+ "AND supercheck_userid = ? AND superchk_remarks IS NULL) ORDER BY mname";

			Query query = entityManager.createNativeQuery(qry1);
			query.setParameter(1, dcodes);
			query.setParameter(2, Integer.parseInt(wbdcode));
			query.setParameter(3, userid);

			List<Object[]> resultList = query.getResultList();

			List<McodeMnameSuperCheck> mcodeMnameData = new ArrayList<>();
			for (Object[] ob : resultList) {
				int mcode = (int) ob[0];
				String mname = (String) ob[1];

				McodeMnameSuperCheck result = new McodeMnameSuperCheck();
				result.setMcode(mcode);
				result.setMname(mname);

				mcodeMnameData.add(result);
			}

			return mcodeMnameData;
		} catch (Exception e) {
			System.err.println("An unexpected error occurred while fetching Mcode and Mname: " + e.getMessage());
			throw new RuntimeException("Failed to fetch Mcode and Mname.", e);
		}
	}

	@Transactional
	public List<Object> getData(int cropyear, String wbdcode, int wbmcode, String userid, String season) {
		try {
			if (wbdcode == null || wbdcode.isEmpty() || userid == null || userid.isEmpty() || season == null
					|| season.isEmpty()) {
				throw new IllegalArgumentException("Input parameters cannot be null or empty.");
			}

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab = "ecrop" + cropyear + ".cr_crop_det_new_v_" + season + wbdcode + cropyear;

//			String qry1 = "SELECT DISTINCT(wbvcode), wbvname FROM wbvillage_mst WHERE wbdcode = ? AND wbmcode = ? AND wbvcode IN ("
//					+ "SELECT DISTINCT cr_vcode FROM " + tab + " WHERE cr_dist_code = ? AND cr_mand_code = ? AND "
//					+ "supercheck_userid = ? AND superchk_remarks IS NULL) ORDER BY wbvname";
//			
			String qry1 = "SELECT DISTINCT(wbvcode), wbvname FROM wbvillage_mst WHERE wbdcode = ? AND wbmcode = ? AND wbvcode IN ("
					+ "SELECT DISTINCT cr_vcode FROM " + tab + " WHERE cr_dist_code = ? AND cr_mand_code = ?  "
					+ " AND superchk_remarks IS NULL) ORDER BY wbvname";


			Query query = entityManager.createNativeQuery(qry1);
			query.setParameter(1, Integer.parseInt(wbdcode));
			query.setParameter(2, wbmcode);
			query.setParameter(3, Integer.parseInt(wbdcode));
			query.setParameter(4, wbmcode);
//			query.setParameter(5, userid);

			List<Object> result = query.getResultList();
			return result;
		} catch (NumberFormatException e) {
			System.err.println("Invalid input parameter format: " + e.getMessage());
			throw new IllegalArgumentException("Invalid input parameter format.", e);
		} catch (Exception e) {
			System.err.println("An unexpected error occurred while fetching data: " + e.getMessage());
			throw new RuntimeException("Failed to fetch data.", e);
		}
	}

	public List<AuthorityVerifyReasons> getCodeAndReason() {
		try {
			String query = "SELECT code, reason FROM authority_verify_reasons WHERE active = 'A'";
			Query rs = entityManager.createNativeQuery(query);

			List<Object[]> resultList = rs.getResultList();
			List<AuthorityVerifyReasons> dataAuthorityVerifyReasons = new ArrayList<>();

			for (Object[] ob : resultList) {
				if (ob.length == 2 && ob[0] instanceof Integer && ob[1] instanceof String) {
					int code = (int) ob[0];
					String reason = (String) ob[1];

					AuthorityVerifyReasons getCodeAndReason = new AuthorityVerifyReasons();
					getCodeAndReason.setCode(code);
					getCodeAndReason.setReason(reason);

					dataAuthorityVerifyReasons.add(getCodeAndReason);
				} else {
					throw new IllegalStateException("Unexpected data format in result set.");
				}
			}
			return dataAuthorityVerifyReasons;
		} catch (Exception e) {
			System.err.println("An unexpected error occurred while fetching data: " + e.getMessage());
			throw new RuntimeException("Failed to fetch code and reason data.", e);
		}
	}

	public List<CrCropDetNewV> checkAndViewHomeMandalViewData(int cropyear, String wbdcode, int wbmcode, String userid,
			String season, String role, String vcode) {
		try {

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			// System.out.println("role---->"+role);

			String tab = "ecrop" + cropyear + ".cr_crop_det_new_v_" + season + wbdcode + cropyear;
			

			String qry = "";
			if (role.equals("44") || role.equals("45") || role.equals("9") || role.equals("19") || role.equals("46")
					|| role.equals("31")) {

				qry = "SELECT ekycfarmername, se.exception_type, a.cr_dist_code, a.cr_mand_code, oc_name, oc_fname, variety, mobileno, cr_sow_date, varietyname, bookingid, watersource AS wsrcdesc, cropname, "
						+ "a.dname AS dname, a.mname AS mname, a.vname AS vname, part_key, a.cr_vcode, a.dcode, a.mcode, kh_no, cr_sno, a.cr_crop, cr_mix_unmix_ext, a.cr_no, cr_w_draw, occupant_extent "
						+ "FROM " + tab
						+ " a LEFT JOIN ecrop2023.supercheck_exceptions_k2023 se ON a.exception_catg = se.exception_catg "
						+ "WHERE a.cr_dist_code = ? AND a.cr_mand_code = ? AND a.cr_vcode = ? AND supercheck_userid = ? "
						+ "AND superchk_remarks is null ORDER BY dname, mname, vname, cr_sno, kh_no";

			} else if (role.equals("2") || role.equals("5")) {

				qry = "select   ekycfarmername,se.exception_type,a.cr_dist_code,a.cr_mand_code,oc_name,oc_fname, variety, mobileno,cr_sow_date, varietyname, bookingid, watersource as wsrcdesc, cropname, "
						+ "a.dname as dname,a.mname as mname,a.vname as vname,part_key,a.cr_vcode,a.dcode,a.mcode,kh_no,cr_sno,a.cr_crop,cr_mix_unmix_ext,a.cr_no,cr_w_draw,occupant_extent "
						+ " from " + tab
						+ " a left join ecrop2023.supercheck_exceptions_k2023 se on a.exception_catg= se.exception_catg "
						+ " where a.cr_dist_code=? and a.cr_mand_code=? and a.cr_vcode=? and supercheck_userid =? "
						+ " and superchk_remarks is null   order by dname,mname,vname,cr_sno,kh_no ";

			} else if (role.equals("22")) {

				qry = "select   ekycfarmername,se.exception_type,a.cr_dist_code,a.cr_mand_code,oc_name,oc_fname, variety, mobileno,cr_sow_date, varietyname, bookingid, watersource as wsrcdesc, cropname,"
						+ "a.dname,a.mname,a.vname,part_key,a.cr_vcode,a.dcode,a.mcode,kh_no,cr_sno,a.cr_crop,cr_mix_unmix_ext,  occupant_extent "
						+ " from " + tab
						+ " a left join supercheck_exceptions se on a.exception_catg= se.exception_catg "
						+ " where a.cr_dist_code=? and a.cr_mand_code=? and a.cr_vcode=? and supercheck_userid =? "
						+ " and superchk_remarks is null   order by dname,mname,vname,cr_sno,kh_no ";
			}

			Query query = entityManager.createNativeQuery(qry);
			query.setParameter(1, Double.parseDouble(wbdcode));
			query.setParameter(2, wbmcode);
			query.setParameter(3, Integer.parseInt(vcode));
			query.setParameter(4, userid);

			List<Object[]> resultList = query.getResultList();
			List<CrCropDetNewV> entities = new ArrayList<>();
			for (Object obj : resultList) {
				Object[] data = (Object[]) obj;
				String ekycfarmername = (String) data[0];
				String exception_type = (String) data[1];
				BigDecimal cr_dist_code = (BigDecimal) data[2];
				BigDecimal cr_mand_code = (BigDecimal) data[3];
				String oc_name = (String) data[4];
				String oc_fname = (String) data[5];
				int variety = (int) data[6];
				BigDecimal mobileno = (BigDecimal) data[7];
				Date cr_sow_date = (Date) data[8];
				String varietyname = (String) data[9];
				int bookingid = (int) data[10];
				String wsrcdesc = (String) data[11];
				String cropname = (String) data[12];
				String dname = (String) data[13];
				String mname = (String) data[14];
				String vname = (String) data[15];
				String part_key = (String) data[16];
				int cr_vcode = (int) data[17];
				int dcode = (int) data[18];
				int mcode = (int) data[19];
				BigDecimal kh_no = (BigDecimal) data[20];
				String cr_sno = (String) data[21];
				int cr_crop = (int) data[22];
				BigDecimal cr_mix_unmix_ext = (BigDecimal) data[23];
				String cr_no = (String) data[24];
				BigDecimal cr_w_draw = (BigDecimal) data[25];
				BigDecimal occupant_extent = (BigDecimal) data[26];

				CrCropDetNewV cropData = new CrCropDetNewV();
				cropData.setBookingid(bookingid);
				cropData.setVarietyname(varietyname);
				cropData.setMobileno(mobileno);

				if (role.equals("22") || exception_type == null) {
					cropData.setException_type("-------");
				} else {
					cropData.setException_type(exception_type);
				}
				cropData.setWsrcdesc(wsrcdesc);
				cropData.setCropname(cropname);
				cropData.setDname(dname);
				cropData.setMname(mname);
				cropData.setVname(vname);
				cropData.setPart_key(part_key);
				cropData.setCr_mix_unmix_ext(cr_mix_unmix_ext);
				cropData.setCr_w_draw(cr_w_draw);
				cropData.setOccupant_extent(occupant_extent);
				cropData.setCr_crop(cr_crop);
				cropData.setCr_no(cr_no);
				cropData.setCr_sow_date(cr_sow_date);
				cropData.setCr_sno(cr_sno);
				cropData.setKh_no(kh_no);
				cropData.setVariety(variety);
				cropData.setCr_vcode(cr_vcode);
				cropData.setCr_dist_code(cr_dist_code);
				cropData.setCr_mand_code(cr_mand_code);
				cropData.setEkycfarmername(ekycfarmername);
				cropData.setOc_name(oc_name);
				cropData.setOc_fname(oc_fname);
				cropData.setDcode(dcode);
				cropData.setMcode(mcode);
				entities.add(cropData);
			}
			return entities;
		} catch (Exception e) {

			System.err.println("An unexpected error occurred while fetching data: " + e.getMessage());
			throw new RuntimeException("Failed to fetch crop data.", e);

		}
	}

	@Transactional
	public String rejOrApprSupck(String activeYear, String bkId, String cropcode, String crno, String sowdt,
			String khata, String surveyNo, String varietyCode, String reasonCodes, String apprStatus, String userid,
			String ipaddress, String wbvillcode, String pwbdcode, String pwbmcode, String occupName, String occupFname,
			String cropyear, String season) {
		System.out.println("bkId---->" + bkId);
		System.out.println("sowdt---->" + sowdt);
		System.out.println("cropcode---->" + cropcode);
		System.out.println("varietyCode---->" + varietyCode);
		System.out.println("crno---->" + crno);
		System.out.println("userid---->" + userid);

		String msg = " ";
		try {
			String wbdist = pwbdcode.length() == 1 ? "0" + pwbdcode : pwbdcode;

			String tab = "cr_details_" + season + wbdist + cropyear;
			String tab2 = "supercheck_details";
			String tab3 = "supercheck_rejdet";
			if (activeYear.equals(cropyear)) {
				tab = "ecrop" + activeYear + "." + tab;
				tab2 = "ecrop" + activeYear + "." + tab2;
				tab3 = "ecrop" + activeYear + "." + tab3;
			}

			String[] reasonArr = reasonCodes != null ? reasonCodes.split(",") : new String[0];

			String pst = "UPDATE " + tab
					+ " SET superchk_remarks = ?, suprejreason = ? WHERE bookingid = ? AND cr_sow_date = ? "
					+ "AND cr_crop = ? AND variety = ? AND cr_no = ? AND supercheck_userid = ? AND cr_year = ? AND cr_season = ?";
			Query query = entityManager.createNativeQuery(pst);
			query.setParameter(1, apprStatus);
			query.setParameter(2, apprStatus.equals("A") ? "" : reasonCodes);
			query.setParameter(3, Integer.parseInt(bkId));
			query.setParameter(4, Date.valueOf(sowdt));
			query.setParameter(5, Integer.parseInt(cropcode));
			query.setParameter(6, Integer.parseInt(varietyCode));
			query.setParameter(7, crno);
			query.setParameter(8, userid);
			query.setParameter(9, Integer.parseInt(cropyear));
			query.setParameter(10, season);
			int status = query.executeUpdate();

			// Insert statement
			String qry = "INSERT INTO " + tab2
					+ "(cr_dist_code,cr_mand_code,bookingid,cr_crop,cr_no,variety,kh_no,cr_sno,cr_sow_dt,remarks,rej_reason,"
					+ "part_key,crt_user,clientip,cr_vcode,occup_name,occup_fname,cr_season,cr_year) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Query pst1 = entityManager.createNativeQuery(qry);
			if (apprStatus.equals("A")) {
				pst1.setParameter(1, Integer.parseInt(wbdist));
				pst1.setParameter(2, Integer.parseInt(pwbmcode));
				pst1.setParameter(3, Integer.parseInt(bkId));
				pst1.setParameter(4, Integer.parseInt(cropcode));
				pst1.setParameter(5, crno);
				pst1.setParameter(6, Integer.parseInt(varietyCode));
				pst1.setParameter(7, Integer.parseInt(khata));
				pst1.setParameter(8, surveyNo);
				pst1.setParameter(9, Date.valueOf(sowdt));
				pst1.setParameter(10, apprStatus);
				pst1.setParameter(11, "0");
				pst1.setParameter(12, season + wbdist + cropyear);
				pst1.setParameter(13, userid);
				pst1.setParameter(14, ipaddress);
				pst1.setParameter(15, Integer.parseInt(wbvillcode));
				pst1.setParameter(16, occupName);
				pst1.setParameter(17, occupFname);
				pst1.setParameter(18, season);
				pst1.setParameter(19, Integer.parseInt(cropyear));
				int aprUpd = pst1.executeUpdate();
				if (aprUpd > 0) {
					msg = "Records are approved and successfully inserted into the database.";
				} else {
					msg = "Approved records failed to insert into the database.";
				}
			} else if (apprStatus.equals("R")) {
				// Loop through reason codes for rejection
				for (String reason : reasonArr) {
					pst1.setParameter(1, Integer.parseInt(wbdist));
					pst1.setParameter(2, Integer.parseInt(pwbmcode));
					pst1.setParameter(3, Integer.parseInt(bkId));
					pst1.setParameter(4, Integer.parseInt(cropcode));
					pst1.setParameter(5, crno);
					pst1.setParameter(6, Integer.parseInt(varietyCode));
					pst1.setParameter(7, Integer.parseInt(khata));
					pst1.setParameter(8, surveyNo);
					pst1.setParameter(9, Date.valueOf(sowdt));
					pst1.setParameter(10, apprStatus);
					pst1.setParameter(11, reason);
					pst1.setParameter(12, season + wbdist + cropyear);
					pst1.setParameter(13, userid);
					pst1.setParameter(14, ipaddress);
					pst1.setParameter(15, Integer.parseInt(wbvillcode));
					pst1.setParameter(16, occupName);
					pst1.setParameter(17, occupFname);
					pst1.setParameter(18, season);
					pst1.setParameter(19, Integer.parseInt(cropyear));
					pst1.executeUpdate();
				}
				msg = "Records are rejected and successfully inserted into the database.";
			}

			if (status > 0) {
				System.out.println("status---" + status);
			}

			String inssuprejQry = "INSERT INTO " + tab3 + "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, "
					+ "kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, "
					+ "cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, "
					+ "cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, "
					+ "updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, "
					+ "irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, "
					+ "tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, "
					+ "occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, "
					+ "harvest_date, damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, "
					+ "erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, "
					+ "dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, "
					+ "cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
					+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, "
					+ "crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
					+ "crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, "
					+ "reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, "
					+ "seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, "
					+ "daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, "
					+ "vro_verify, dt_vroverify, vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, pmfbycode, vro_auth_uid, "
					+ "supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, "
					+ "finalrec, seedprod_agency, crop_status, crop_agency, ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, "
					+ "lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, farmer_type, dt_unlock_ext, dt_lock_ext, "
					+ "invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr, dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks) "
					+ "SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, "
					+ "cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, "
					+ "cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, "
					+ "updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, "
					+ "crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, "
					+ "age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, "
					+ "gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, tot_extent, "
					+ "occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, "
					+ "qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, "
					+ "cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, "
					+ "cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, "
					+ "ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, "
					+ "crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, "
					+ "claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, "
					+ "vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, "
					+ "diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, pmfbycode, "
					+ "vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, "
					+ "finalrec, seedprod_agency, crop_status, crop_agency, ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, "
					+ "unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, sms_send, "
					+ "oldharvest_date, supercheckts, mao_unlock_appr, dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks "
					+ "FROM " + tab
					+ " WHERE bookingid=? AND cr_crop=? AND cr_no=? AND variety=? AND kh_no=? AND cr_sno=? AND cr_sow_date=? AND cr_vcode=?";
			Query pst2 = entityManager.createNativeQuery(inssuprejQry);
			if (apprStatus.equals("R")) {
				pst2.setParameter(1, Integer.parseInt(bkId));
				pst2.setParameter(2, Integer.parseInt(cropcode));
				pst2.setParameter(3, crno);
				pst2.setParameter(4, Integer.parseInt(varietyCode));
				pst2.setParameter(5, Integer.parseInt(khata));
				pst2.setParameter(6, surveyNo);
				pst2.setParameter(7, Date.valueOf(sowdt));
				pst2.setParameter(8, Integer.parseInt(wbvillcode));
				pst2.executeUpdate();
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return "An error occurred while processing the request.";
		}
	}
}
