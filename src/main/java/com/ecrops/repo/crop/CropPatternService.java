package com.ecrops.repo.crop;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ecrops.dto.crop.response.CropnatureCropidCropname;
import com.ecrops.dto.crop.response.VarietyCodeVarietyNameCropPattern;
import com.ecrops.dto.crop.response.VcodeWbvnameForCropPattern;
import com.ecrops.entity.crop.CropPatternDetailsEntity;

@Service
@Transactional
public class CropPatternService {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VcodeWbvnameForCropPattern> getVillageCodeAndName(int vscode) {
System.out.println("---------------called");
		String qry1 = "select  vcode, wbvname from ecrop2023.villsec_rev_v where vscode=? ";

		Query query = entityManager.createNativeQuery(qry1);
		query.setParameter(1, vscode);
		List<Object[]> resultList = query.getResultList();

		List<VcodeWbvnameForCropPattern> getVcodeWbvname = new ArrayList<>();
		for (Object[] ob : resultList) {

			int vcode = (int) ob[0];
			String wbvname = (String) ob[1];

			VcodeWbvnameForCropPattern getData = new VcodeWbvnameForCropPattern();

			getData.setVcode(vcode);
			getData.setWbvname(wbvname);

			getVcodeWbvname.add(getData);

		}
		return getVcodeWbvname;

	}

	public List<CropPatternDetailsEntity> getCropViewDetails(String wbdcode, int vcode, String surveyNo, String khataNo,
			String aadharNo,String season,int cropyear) {

		String partitionName = "cr_crop_det_new_v_";

		if (Integer.parseInt(wbdcode) <= 9) {
			//partitionName = "ecrop2023."+partitionName + season + "0" + wbdcode + cropyear;

			partitionName = "ecrop2023."+partitionName + "r" + "0" + wbdcode + "2023";
		} else {
			//partitionName = "ecrop2023."+partitionName + season + wbdcode + cropyear;
			partitionName = "ecrop2023."+partitionName + "r" + wbdcode + "2023";

		}
		
		
		System.out.println("wbdcode----->"+wbdcode);
		String sql1 = "select  distinct kh_no,cr_sno,bookingid,cr_farmeruid,tot_extent,oc_name,oc_fname,cropname,varietyname,cr_mix_unmix_ext,b.description as cropno,c.wsrcdesc as watersource, d.naturedesc as cropnature,cr_sow_date, farmingtype, cr_crop,variety,"
				+ " cr_dist_code,cr_mand_code,cr_year,cr_season,cr_no,dname,mname,vname, wsrcid, cr_sow_type,cropseed_scheme,d.id , cr_vcode "
				+ "from " + partitionName
				+ " a, cropnumber b, waterresources c, cropnature d, cropseed_scheme e where cr_vcode= ? "
				+ " and   CAST(a.cr_no AS TEXT)=CAST(b.id AS TEXT) and a.cr_w_draw=c.wsrcid and a.cr_sow_type=d.id and a.cropseed_scheme=e.cropschtype and mark_delet is null and ekyc='Y' and mismatch is null and cr_sow_type=2 ";

		if (!surveyNo.equals("")) {
			sql1 += " and cr_sno='" + surveyNo + "'";
		} else if (!khataNo.equals("")) {
			sql1 += " and kh_no=" + khataNo;
		} else if (!aadharNo.equals("")) {
			sql1 += " and cr_farmeruid='" + aadharNo + "'";
		}
		System.out.println(sql1);
		Query query = entityManager.createNativeQuery(sql1);
		query.setParameter(1, vcode);
		List<Object[]> resultList = query.getResultList();

		int j = 0;
		List<CropPatternDetailsEntity> pojoList = new ArrayList<>();
		for (Object[] data : resultList) {
			BigDecimal kh_no = (BigDecimal) data[0];
			String cr_sno = (String) data[1];
			int bookingid = (int) data[2];
			String cr_farmeruid = (String) data[3];
			BigDecimal tot_extent = (BigDecimal) data[4];
			
			String oc_name = (String) data[5];
			String oc_fname = (String) data[6];
			String cropname = (String) data[7];
			String varietyname = (String) data[8];
			BigDecimal cr_mix_unmix_ext = (BigDecimal) data[9];
			
			String cropno = (String) data[10];
			String watersource = (String) data[11];
			String cropnature = (String) data[12];
			Date cr_sow_date = (Date) data[13];
			String farmingtype = (String) data[14];
			
			int cr_crop = (int) data[15];
			int variety = (int) data[16];
			BigDecimal cr_dist_code = (BigDecimal) data[17];
			BigDecimal cr_mand_code = (BigDecimal) data[18];
			BigDecimal cr_year = (BigDecimal) data[19];
			
			char cr_season = (char) data[20];
			String cr_no = (String) data[21];
			String dname = (String) data[22];
			String mname = (String) data[23];
			String vname = (String) data[24];
			
			int wsrcid = (int) data[25];
			int cr_sow_type = (int) data[26];
			char cropseed_schme = (char) data[27];
			int villCode = (int) data[29];

			CropPatternDetailsEntity setData = new CropPatternDetailsEntity();

			setData.setKh_no(kh_no);
			setData.setCr_sno(cr_sno);
			setData.setBookingid(bookingid);
			setData.setCr_farmeruid(cr_farmeruid);
			setData.setTot_extent(tot_extent);
			setData.setOc_name(oc_name);
			setData.setOc_fname(oc_fname);
			setData.setCropname(cropname);
			setData.setVarietyname(varietyname);
			setData.setCr_mix_unmix_ext(cr_mix_unmix_ext);
			setData.setCropno(cropno);
			setData.setWatersource(watersource);
			setData.setCropnature(cropnature);
			setData.setCr_sow_date(cr_sow_date);
			setData.setFarmingtype(farmingtype);
			setData.setCr_crop(cr_crop);
			setData.setVariety(variety);
			setData.setCr_dist_code(cr_dist_code);
			setData.setCr_mand_code(cr_mand_code);
			setData.setCr_year(cr_year);
			setData.setCr_season(cr_season);
			setData.setCr_no(cr_no);
			setData.setDname(dname);
			setData.setMname(mname);
			setData.setVname(vname);
			setData.setWsrcid(wsrcid);
			setData.setCr_sow_type(cr_sow_type);
			setData.setCropseed_schme(cropseed_schme);
			setData.setCr_vcode(villCode);

			pojoList.add(setData);

		}

		return pojoList;
	}

	// for the crop list
	public List<CropnatureCropidCropname> getCropList(String crop_type) {
		System.out.println("cropType--->" + crop_type);
		String qry2 = "";

		if (crop_type.equalsIgnoreCase("1")) {
			qry2 = "select cropid,cropname from cropnames where cropclass in('P','B')";
		} else {
			qry2 = "select cropid,cropname from cropnames";
		}

		Query query = entityManager.createNativeQuery(qry2);
		List<Object[]> resultList = query.getResultList();
		List<CropnatureCropidCropname> setData = new ArrayList<>();
		for (Object[] ob : resultList) {
			int cropid = (int) ob[0];
			String cropname = (String) ob[1];

			CropnatureCropidCropname cropnatureDetails = new CropnatureCropidCropname();
			cropnatureDetails.setCropid(cropid);
			cropnatureDetails.setCropname(cropname);

			setData.add(cropnatureDetails);
		}
		return setData;
	}

	public List<CropnatureCropidCropname> getCropNames() {
		String qry2 = "";

		qry2 = "select cropid,cropname from cropnames where cropnature in('A','H','S')";

		Query query = entityManager.createNativeQuery(qry2);
		List<Object[]> resultList = query.getResultList();
		List<CropnatureCropidCropname> setData = new ArrayList<>();
		for (Object[] ob : resultList) {
			int cropid = (int) ob[0];
			String cropname = (String) ob[1];

			CropnatureCropidCropname cropnatureDetails = new CropnatureCropidCropname();
			cropnatureDetails.setCropid(cropid);
			cropnatureDetails.setCropname(cropname);

			setData.add(cropnatureDetails);
		}
		return setData;
	}

	// for crop varities
	public List<VarietyCodeVarietyNameCropPattern> getVarietyCodeVarietyName(int cropcode) {
		Character cropClass = null;

		try {
			String cropClassQuery = "SELECT cropclass FROM cropnames WHERE cropid = ?";
			Query cropClassQueryObj = entityManager.createNativeQuery(cropClassQuery);
			cropClassQueryObj.setParameter(1, cropcode);

			cropClass = (Character) cropClassQueryObj.getSingleResult();
			System.out.println("cropclass" + cropClass);
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// Handle other exceptions
			e.printStackTrace();
		}

		List<VarietyCodeVarietyNameCropPattern> varietyData = new ArrayList<>();

		if (cropClass != null) {
			try {
				String varietyQuery = "SELECT varietycode, varietyname FROM cr_variety_master WHERE cropcode = ?";
				Query varietyQueryObj = entityManager.createNativeQuery(varietyQuery);
				varietyQueryObj.setParameter(1, cropcode);

				List<Object[]> result = varietyQueryObj.getResultList();

				for (Object[] ob : result) {
					int varietycode = (int) ob[0];
					String varietyname = (String) ob[1];
				//	System.out.println(ob[0].toString() + ob[1].toString() + "hii");
					VarietyCodeVarietyNameCropPattern cr_variety_masterDetails = createVarietyCodeVarietyNameCropPattern(
							varietycode, varietyname);
					varietyData.add(cr_variety_masterDetails);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return varietyData;
	}

	private VarietyCodeVarietyNameCropPattern createVarietyCodeVarietyNameCropPattern(int varietycode,
			String varietyname) {
		VarietyCodeVarietyNameCropPattern cr_variety_masterDetails = new VarietyCodeVarietyNameCropPattern();
		cr_variety_masterDetails.setVarietycode(varietycode);
		cr_variety_masterDetails.setVarietyname(varietyname);
		return cr_variety_masterDetails;
	}

	public String isBlankInt(String str) {
		str = str == null ? "0" : str;

		return str;
	}

	public boolean isNum(String str) {
		str = isBlankInt(str);
		String str1 = str;
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(str1);
		boolean b = m.matches();
		return b;
	}

	public boolean saveCropDetails(String sowDt, String crSowType, String bookingIds, String CropNo,
			String orgVarietyCode, String orgCropCode, String varietyNameCode, String varCodeSec, String cropNameCode,
			String cropCodeSec, String kh_no, String sr_no, String totExtent, String orgExt, String cropNo,
			String croppingType, String croppingTypeSec, String crpNatId, String cropYear, String season, String dcode,
			String vcode, String clientIp) {



		String partitionName = " ";
		int social_update = 0;
		int flag = 0;
		String status = " ";
		try {

			partitionName = "cr_details_";
			if (Integer.parseInt(dcode) <= 9) {
				partitionName = partitionName + season + "0" + dcode + cropYear;
			} else {
				partitionName = partitionName + season + dcode + cropYear;
			}

			if (orgCropCode.equals(cropCodeSec)) {
				String qry3 = "INSERT INTO " + partitionName
						+ "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, "
						+ "cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no,cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, "
						+ "regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, "
						+ "crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, "
						+ "owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, "
						+ "tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
						+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
						+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, "
						+ "ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, "
						+ "claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, "
						+ "jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, pmfbycode,"
						+ " vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, finalrec, seedprod_agency, crop_status, crop_agency, "
						+ "ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, farmer_type, dt_unlock_ext, dt_lock_ext, "
						+ "invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr,dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, ekyc_uid_mismatch, supercheck_appr, extent_check, "
						+ "oldextent, uidmultiple,monotomixed) select part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, '1', "
						+ cropNameCode + ", "
						+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, "
						+ "imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, "
						+ varietyNameCode + ", irrmethod, crsowtype, crratio, data_src, sno_notmatch, "
						+ "cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, now(), ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, "
						+ "email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, "
						+ "erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, "
						+ "crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
						+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, "
						+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, "
						+ "reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
						+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, "
						+ "dt_obj_verify, vroauthuserid, pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, finalrec, "
						+ "seedprod_agency, crop_status, crop_agency, ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, "
						+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr,dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, ekyc_uid_mismatch, "
						+ "supercheck_appr, extent_check, oldextent, uidmultiple,'Y' from " + partitionName
						+ " where cr_dist_code=? and bookingid=? and cr_crop=?  and cr_vcode=? and variety=? and kh_no=?";

				Query query = entityManager.createNativeQuery(qry3);
				query.setParameter(1, Integer.parseInt(dcode));
				query.setParameter(2, Integer.parseInt(bookingIds));
				query.setParameter(3, Integer.parseInt(orgCropCode));
				query.setParameter(4, Integer.parseInt(vcode));
				query.setParameter(5, Integer.parseInt(orgVarietyCode));
				query.setParameter(6, Integer.parseInt(kh_no));
				social_update = query.executeUpdate();

				String qry2 = "update " + partitionName
						+ " set cr_sow_type=? ,monotomixed='Y' where  bookingid=? and cr_crop=?  and cr_vcode=? and variety=? and cr_sow_date=?";
				Query ps2 = entityManager.createNativeQuery(qry2);
				ps2.setParameter(1, 1);
				ps2.setParameter(2, Integer.parseInt(bookingIds));
				ps2.setParameter(3, Integer.parseInt(orgCropCode));
				ps2.setParameter(4, Integer.parseInt(vcode));
				ps2.setParameter(5, Integer.parseInt(orgVarietyCode));
				ps2.setParameter(6, Date.valueOf(sowDt));
				int res1 = ps2.executeUpdate();// System.out.println("ps1 update"+ps1);
				if (res1 > 0) {
					flag = 1;
				}

			} else {

				String qry3 = "INSERT INTO " + partitionName
						+ "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, "
						+ "cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no,cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, "
						+ "regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, "
						+ "crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, "
						+ "owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, "
						+ "tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, "
						+ "calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
						+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, "
						+ "ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, "
						+ "claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, "
						+ "jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, dt_obj_verify, vroauthuserid, pmfbycode,"
						+ " vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, finalrec, seedprod_agency, crop_status, crop_agency, "
						+ "ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, farmer_type, dt_unlock_ext, dt_lock_ext, "
						+ "invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr,dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, ekyc_uid_mismatch, supercheck_appr, extent_check, "
						+ "oldextent, uidmultiple,monotomixed) select part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, '1', "
						+ cropCodeSec + ", "
						+ "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, "
						+ "imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, "
						+ varCodeSec + ", irrmethod, crsowtype, crratio, data_src, sno_notmatch, "
						+ "cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, now(), ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, "
						+ "email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, "
						+ "erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, "
						+ "crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
						+ "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, "
						+ "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, "
						+ "reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
						+ "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, "
						+ "dt_obj_verify, vroauthuserid, pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, griev_comp, mark_delet, finalrec, "
						+ "seedprod_agency, crop_status, crop_agency, ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, "
						+ "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr,dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, ekyc_uid_mismatch, "
						+ "supercheck_appr, extent_check, oldextent, uidmultiple,'Y' from " + partitionName
						+ " where cr_dist_code=? and bookingid=? and cr_crop=?  and cr_vcode=? and variety=? and kh_no=?";

				System.err.println("varietyCode" + varietyNameCode);

				Query ps1 = entityManager.createNativeQuery(qry3);
				ps1.setParameter(1, Integer.parseInt(dcode));
				ps1.setParameter(2, Integer.parseInt(bookingIds));
				ps1.setParameter(3, Integer.parseInt(orgCropCode));
				ps1.setParameter(4, Integer.parseInt(vcode));
				ps1.setParameter(5, Integer.parseInt(orgVarietyCode));
				ps1.setParameter(6, Integer.parseInt(kh_no));
				social_update = ps1.executeUpdate();

				String qry2 = "update " + partitionName
						+ " set cr_sow_type=?,monotomixed='Y' where  bookingid=? and cr_crop=?  and cr_vcode=? and variety=? and cr_sow_date=? ";
				Query ps2 = entityManager.createNativeQuery(qry2);
				ps2.setParameter(1, 1);
				ps2.setParameter(2, Integer.parseInt(bookingIds));
				ps2.setParameter(3, Integer.parseInt(orgCropCode));
				ps2.setParameter(4, Integer.parseInt(vcode));
				ps2.setParameter(5, Integer.parseInt(orgVarietyCode));
				ps2.setParameter(6, Date.valueOf(sowDt));
				
				int res1 = ps2.executeUpdate();
				if (res1 > 0) {
					flag = 1;
				}
			}
			if (flag == 1 && social_update > 0) {
				status = "Successfully Updated Rabi 2022-23 Mono To Mixed Cropping";
			} else {
				status = "updation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return true;
	}
}
