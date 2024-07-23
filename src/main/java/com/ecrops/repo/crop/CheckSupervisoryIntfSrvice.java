package com.ecrops.repo.crop;

import java.math.BigDecimal; 

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecrops.dto.crop.response.AuthorityVerifyReasons;
import com.ecrops.entity.crop.Cr_detailsPojo;

@Service
@Transactional
@Component
public class CheckSupervisoryIntfSrvice {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<AuthorityVerifyReasons> getcodeAndreason() {

		String query = "select code, reason from authority_verify_reasons where active='A'";

		Query rs = entityManager.createNativeQuery(query);

		List<Object[]> resultList = rs.getResultList();
		List<AuthorityVerifyReasons> getDataauthority_verify_reasons = new ArrayList<>();
		for (Object[] ob : resultList) {
			Object[] data = (Object[]) ob;
			int code = (int) data[0];
			String reason = (String) data[1];

			AuthorityVerifyReasons getCodeAndReason = new AuthorityVerifyReasons();
			getCodeAndReason.setCode(code);
			getCodeAndReason.setReason(reason);
			getDataauthority_verify_reasons.add(getCodeAndReason);

		}
		return getDataauthority_verify_reasons;
	}

	public List<Cr_detailsPojo> checkAndViewCrDtailsPartionData(int cropyear, String wbdcode, int wbmcode,
			String userid, String season, String role) {
		try {

			List<Cr_detailsPojo> entities = null;

			if (wbdcode.length() == 1) {
				wbdcode = "0" + wbdcode;
			}

			String tab = "ecrop" + cropyear + ".cr_details_" + season + wbdcode + cropyear;

			String qry = "";
			Query query = null;
			if (role.equals("44") || role.equals("45") || role.equals("9") || role.equals("19") || role.equals("20")
					|| role.equals("31") || role.equals("21")) {
				qry = "select se.exception_type,a.cr_dist_code,a.cr_mand_code,oc_name,oc_fname,a.variety,a.mobileno,cr_sow_date,v.varietyname,bookingid,w.wsrcdesc,c.cropname,b.wbdname,b.wbmname,b.wbvname,part_key,cr_vcode,kh_no,cr_sno,cr_crop,cr_mix_unmix_ext,cr_no,cr_w_draw,occupname,occupfname,occupant_extent "
						+ " from " + tab + " a "
						+ " inner join wbvillage_mst b on a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode "
						+ " inner join supercheck_exceptions se on a.exception_catg= se.exception_catg "
						+ " inner join cropnames c on a.cr_crop=c.cropid "
						+ " inner join  waterresources w on a.cr_w_draw=w.wsrcid "
						+ " inner join  cr_variety_master v on a.variety=v.varietycode "
						+ " where cr_dist_code=? and supercheck_userid =?  and superchk_remarks is null  order by wbdname,wbmname,wbvname ";
				query = entityManager.createNativeQuery(qry);
				query.setParameter(1, Integer.parseInt(wbdcode));
				query.setParameter(2, userid);
			} else if (role.equals("2") || role.equals("5")) {
				qry = "select  se.exception_type,a.cr_dist_code,a.cr_mand_code,oc_name,oc_fname,a.variety,a.mobileno,cr_sow_date,v.varietyname,bookingid,w.wsrcdesc,c.cropname,b.wbdname,b.wbmname,b.wbvname,part_key,cr_vcode,kh_no,cr_sno,cr_crop,cr_mix_unmix_ext,cr_no,cr_w_draw,occupname,occupfname,  occupant_extent "
						+ " from " + tab + " a "
						+ " inner join wbvillage_mst b on a.cr_dist_code=b.wbdcode and a.cr_mand_code=b.wbmcode and a.cr_vcode=b.wbvcode "
						+ " inner join supercheck_exceptions se on a.exception_catg= se.exception_catg "
						+ " inner join cropnames c on a.cr_crop=c.cropid "
						+ " inner join  waterresources w on a.cr_w_draw=w.wsrcid "
						+ " inner join  cr_variety_master v on a.variety=v.varietycode "
						+ " where cr_dist_code=? and cr_mand_code=? and supercheck_userid =?  and superchk_remarks is null  order by wbdname,wbmname,wbvname ";
				query = entityManager.createNativeQuery(qry);
				query.setParameter(1, Integer.parseInt(wbdcode));
				query.setParameter(2, wbmcode);
				query.setParameter(3, userid);
			}

			List<Object[]> resultList = query.getResultList();
			if (resultList != null) {
				entities = new ArrayList<>();
				for (Object[] obj : resultList) {
					String exception_type = (String) obj[0];
					BigDecimal cr_dist_code = (BigDecimal) obj[1];
					BigDecimal cr_mand_code = (BigDecimal) obj[2];
					String oc_name = (String) obj[3];
					String oc_fname = (String) obj[4];
					int variety = (int) obj[5];
					BigDecimal mobileno = (BigDecimal) obj[6];
					Date cr_sow_date = (Date) obj[7];
					String varietyname = (String) obj[8];
					int bookingid = (int) obj[9];
					String wsrcdesc = (String) obj[10];
					String cropname = (String) obj[11];
					String wbdname = (String) obj[12];
					String wbmname = (String) obj[13];
					String wbvname = (String) obj[14];
					String part_key = (String) obj[15];
					int cr_vcode = (int) obj[16];
					BigDecimal kh_no = (BigDecimal) obj[17];
					String cr_sno = (String) obj[18];
					int cr_crop = (int) obj[19];
					BigDecimal cr_mix_unmix_ext = (BigDecimal) obj[20];
					String cr_no = (String) obj[21];
					BigDecimal cr_w_draw = (BigDecimal) obj[22];
					String occupname = (String) obj[23];
					String occupfname = (String) obj[24];
					BigDecimal occupant_extent = (BigDecimal) obj[25];

					Cr_detailsPojo cropData = new Cr_detailsPojo();
					cropData.setBookingid(bookingid);
					cropData.setVarietyname(varietyname);
					cropData.setMobileno(mobileno);
					cropData.setException_type(exception_type);
					cropData.setWsrcdesc(wsrcdesc);
					cropData.setCropname(cropname);
					cropData.setWbdname(wbdname);
					cropData.setWbmname(wbmname);
					cropData.setWbvname(wbvname);
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
					cropData.setOc_name(oc_name);
					cropData.setOc_fname(oc_fname);
					cropData.setOccupname(occupname);
					cropData.setOccupfname(occupfname);
					entities.add(cropData);
				}
			} else {
				System.err.println(" data: " + resultList);
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
				System.out.println("rejIns---" + status);
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
