package com.ecrops.repo.crop;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
@Transactional
public class SaveUidRejVro {

    @PersistenceContext
    private EntityManager entityManager;

    public String saveVroUidRejVroDetails(Integer wbdcode, String cropyear, String season, String surveyno, String khno_bkid,
			String uid, String vcode, String activeYear, String bkIdsList, String cropNames, String varietyNames,
			String sowDates, String crNumber, String oldcrFarmerUid, String newcrFarmerUid) {

    	int executeInsert = 0, executeUpdate = 0;
		String tab2 = "", status = "";
		if (wbdcode <= 9) {
			tab2 = "cr_details_" + season + "0" + wbdcode + cropyear;
		} else {
			tab2 = "cr_details_" + season + wbdcode + cropyear;
		}
		String crd_orgtab = "cr_details_org_details";

		if (activeYear.equals(cropyear)) {
			tab2 = "ecrop" + activeYear + "." + tab2;
			crd_orgtab = "ecrop" + activeYear + "." + crd_orgtab;
		}
		
		String crdorg_qry=" INSERT INTO "+crd_orgtab+"( "+
				" part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) "+
			    " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, "
			+ " mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn,"
			+ "  vro_verify, dt_vroverify,'11',now(), vao_verify  "+
				" FROM "+tab2+ " where  bookingid=?  and cr_crop=? and cr_vcode=? and cr_no=? and variety=?  "+
			                    "   and cr_year=? and cr_season=? and cr_sow_date=?";
		
		if (!surveyno.equals("")) {
			crdorg_qry += " and cr_sno='" + surveyno + "'";
		} else if (!khno_bkid.equals("")) {
			crdorg_qry += " and kh_no=" + khno_bkid;
		} else if (!uid.equals("")) {
			crdorg_qry += " and cr_farmeruid='" + uid + "'";
		}

		String[] bkIds = bkIdsList.split(",");
		String[] cropCodes = cropNames.split(",");
		String[] varietyCodes = varietyNames.split(",");
		String[] sowDtLst = sowDates.split(",");
		String[] oldCropNOs = crNumber.split(",");

		char seasonChar = season.charAt(0);

		Query pst2 = entityManager.createNativeQuery(crdorg_qry);

		for (int i = 0; i < bkIds.length; i++) {
			pst2.setParameter(1, Integer.parseInt(bkIds[i]));
			pst2.setParameter(2, Integer.parseInt(cropCodes[i]));
			pst2.setParameter(3, Integer.parseInt(vcode));
			pst2.setParameter(4, oldCropNOs[i]);
			pst2.setParameter(5, Integer.parseInt(varietyCodes[i]));
			pst2.setParameter(6, Integer.parseInt(cropyear));
			pst2.setParameter(7, seasonChar);
			pst2.setParameter(8, Date.valueOf(sowDtLst[i]));

			executeInsert += pst2.executeUpdate();

		}
		
		if (executeInsert > 0) {
			String qry="  UPDATE "+tab2+" set vaaauth='N', vao_verify='Y',cr_farmeruid_old=?,cr_farmeruid=?  where "
						+ "bookingid=?  and cr_crop=? and cr_vcode=? and cr_no=? and variety=?   and cr_year=?  and cr_season=? and cr_sow_date=? ";
			if (!surveyno.equals("")) {
				crdorg_qry += " and cr_sno='" + surveyno + "'";
			} else if (!khno_bkid.equals("")) {
				crdorg_qry += " and kh_no=" + khno_bkid;
			} else if (!uid.equals("")) {
				crdorg_qry += " and cr_farmeruid='" + uid + "'";
			}
			
			String[] oldUIds = oldcrFarmerUid.split(",");
			String[] newUIds = newcrFarmerUid.split(",");

			Query pst = entityManager.createNativeQuery(qry);

			for (int i = 0; i < bkIds.length; i++) {
				pst.setParameter(1, oldUIds[i]);
				pst.setParameter(2, newUIds[i]);
				pst.setParameter(3, Integer.parseInt(bkIds[i]));
				pst.setParameter(4, Integer.parseInt(cropCodes[i]));
				pst.setParameter(5, Integer.parseInt(vcode));
				pst.setParameter(6, oldCropNOs[i]);
				pst.setParameter(7, Integer.parseInt(varietyCodes[i]));
				pst.setParameter(8, Integer.parseInt(cropyear));
				pst.setParameter(9, seasonChar);
				pst.setParameter(10, Date.valueOf(sowDtLst[i]));
				
				executeUpdate += pst.executeUpdate();

			}
		}
		
		if (executeUpdate > 0 && executeInsert > 0) {
			status = executeUpdate+" Records Inserted and updated Sucessfully";
		} else if (!(executeUpdate > 0) && executeInsert > 0){
			status = executeUpdate+" Records Inserted but not updated";
		} else {
			status = executeUpdate+" Records Insert and Update is Failed";
		}
		return status;
		
    }
}
