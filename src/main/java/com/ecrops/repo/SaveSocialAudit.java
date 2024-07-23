package com.ecrops.repo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class SaveSocialAudit {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public String saveSocialAuditDetails(String[] bookingIds, String[] kh_no, String[] sr_no, String[] sowDt, String[] cropName, 
			String[] varietyName, String[] totExtent, String[] farmername, String[] farmerFather, String[] aadharId, String[] waterId, 
			String[] cropNatureId, String[] farmingType, String[] bookingIds_org, String[] cr_no_org, String[] kh_no_org, String[] sr_no_org, 
			String[] sowDt_org, String[] cropName_org, String[] varietyName_org, String[] totExtent_org, String[] farmername_org, 
			String[] farmerFather_org, String[] aadharId_org, String[] waterId_org, String[] cropNatureId_org, String[] farmingType_org,   
			String[] action_type, String[] del_list, String applName, String appMobId, String applicatonId, String clientip, 
			String season, String cropyear, Integer wbldcode, Integer mandcode, Integer villcode,String activeYear) {
		
		Integer rs1 = 0, flag = 0, grievance_update = 0, social_update = 0, greivanceId = 0;
		ResultSet rs = null;
		String partitionName = "cr_details_";
		String farmerGrievTab = "farmer_grievances";
        String socialAuditRejTab = "socialaudit_rej_details";
        
        Query pst = null, pst1 = null, ps1 = null, ps2 = null;
        
        System.out.println("farmername_org---->"+Arrays.toString(farmername_org)+"farmerFather_org -----> "+Arrays.toString(farmerFather_org));
        System.out.println("farmername---->"+Arrays.toString(farmername)+"farmerFather -----> "+Arrays.toString(farmerFather));
        
        String msg = "";

        if (wbldcode <= 9) {
            partitionName = partitionName + season + "0" + wbldcode + cropyear;
        } else {
            partitionName = partitionName + season + wbldcode + cropyear;
        }

        if (activeYear.equals(cropyear)) {
            partitionName = "ecrop" + activeYear + "." + partitionName;
            farmerGrievTab = "ecrop" + activeYear + "." + farmerGrievTab;
            socialAuditRejTab = "ecrop" + activeYear + "." + socialAuditRejTab;
        }

//        con = sql.getConnection();
//        con.setAutoCommit(false);

        String qry4 = "select * from " + farmerGrievTab + " where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and kh_no=? and cr_sow_date=?";
        ps2 = entityManager.createNativeQuery(qry4);
        
//        ps2 = con.prepareStatement(qry4);
        
        for (int i = 0; i < bookingIds_org.length; i++) {
            ps2.setParameter(1, wbldcode);
            ps2.setParameter(2, Integer.parseInt(bookingIds_org[i]));
            ps2.setParameter(3, Integer.parseInt(cropName_org[i]));
            ps2.setParameter(4, cr_no_org[i]);
            ps2.setParameter(5, villcode);
            ps2.setParameter(6, Integer.parseInt(varietyName_org[i]));
            ps2.setParameter(7, Integer.parseInt(kh_no_org[i]));
            ps2.setParameter(8, Date.valueOf(sowDt_org[i]));
            List resultList = ps2.getResultList();

            if (!resultList.isEmpty()) {
                String qry5 = "update " + farmerGrievTab + " set mro_flag='V',variety_sug=?,farmername_sug=?,fathername_sug=?,extent_sug=?,cr_w_draw_sug=?,cr_crop_sug=?,cropschtype_sug=?,cr_sow_date_sug=?,resubmitbyva='Y',"
                        + " resubmitdt=now() where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and kh_no=? and cr_sow_date=? ";
                ps1 = entityManager.createNativeQuery(qry5);
//                ps1 = con.prepareStatement(qry5);
                if (varietyName[i].trim().isEmpty()) {
                    ps1.setParameter(1, 0);
                } else {
                    ps1.setParameter(1, Integer.parseInt(varietyName[i].trim()));
                }

                if (farmername[i].trim().isEmpty()) {
                    ps1.setParameter(2, null);
                } else {
                    ps1.setParameter(2, farmername[i]);
                }
                if (farmerFather[i].trim().isEmpty()) {
                    ps1.setParameter(3, null);
                } else {
                    ps1.setParameter(3, farmerFather[i]);
                }
                if (totExtent[i].trim().isEmpty()) {
                    ps1.setParameter(4, 0);
                } else {
                    ps1.setParameter(4, Double.valueOf(totExtent[i]));
                }
                if (waterId[i].trim().isEmpty()) {
                    ps1.setParameter(5, 0);
                } else {
                    ps1.setParameter(5, Integer.parseInt(waterId[i]));
                }
                if (cropName[i].trim().isEmpty()) {
                    ps1.setParameter(6, 0);
                } else {
                    ps1.setParameter(6, Integer.parseInt(cropName[i]));
                }
                if (farmingType[i].trim().isEmpty()) {
                    ps1.setParameter(7, null);
                } else {
                    ps1.setParameter(7, (farmingType[i]));
                }
                if (sowDt[i].trim().isEmpty()) {
                    ps1.setParameter(8, null);
                } else {
                    ps1.setParameter(8, Date.valueOf(sowDt[i]));
                }
                ps1.setParameter(9, wbldcode);
                ps1.setParameter(10, Integer.parseInt(bookingIds_org[i]));
                ps1.setParameter(11, Integer.parseInt(cropName_org[i]));
                ps1.setParameter(12, cr_no_org[i]);
                ps1.setParameter(13, villcode);
                ps1.setParameter(14, Integer.parseInt(varietyName_org[i]));
                ps1.setParameter(15, Integer.parseInt(kh_no_org[i]));
                ps1.setParameter(16, Date.valueOf(sowDt_org[i]));
                
                
//                ps1.addBatch();

                int res1 = ps1.executeUpdate();
                if (res1 > 0) {
                    flag = 1;
                }
                if (action_type[i].equals("del")) {

                    String record_delete = "Y";
                    String qry2 = "update " + partitionName + " set mark_delet='Y', social_status=?, reason_non_auth=? where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and cr_sow_date=?";
                    
//                    ps1 = con.prepareStatement(qry2);
                    ps1 = entityManager.createNativeQuery(qry2);
                    
                    ps1.setParameter(1, record_delete);
                    ps1.setParameter(2, Integer.parseInt(del_list[i]));
                    ps1.setParameter(3, wbldcode);
                    ps1.setParameter(4, Integer.parseInt(bookingIds_org[i]));
                    ps1.setParameter(5, Integer.parseInt(cropName_org[i]));
                    ps1.setParameter(6, cr_no_org[i]);
                    ps1.setParameter(7, villcode);
                    ps1.setParameter(8, Integer.parseInt(varietyName_org[i]));
                    ps1.setParameter(9, Date.valueOf(sowDt_org[i]));
                    
//                    ps1.addBatch();
                    ps1.executeUpdate(); 
                    
//                    int[] res2 = ps1.executeBatch(); //System.out.println("ps1:::"+ps1);
                    for (int k = 0; k < bookingIds.length; k++) {
                        if (res1 == 0) {
                            flag = 0;
                            return "";  // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                            con.rollback();
                        }
                        break;
                    }
                }

            } else {
//                con.setAutoCommit(false);
                String qry1 = "insert into " + farmerGrievTab + "(cr_dist_code, cr_mand_code, cr_vcode,  bookingid, cr_sno, kh_no, "
                        + " cr_crop, variety, cr_sow_date, cr_no, uid_crd, cr_mix_unmix_ext,cr_w_draw,"
                        + " variety_sug, farmername_sug, fathername_sug, extent_sug, "
                        + " cr_w_draw_sug, cr_sow_date_sug,cr_crop_sug,cropschtype,cropschtype_sug,cr_no_sug,dt_crt,applicant_name,applicant_mobile,applicant_id,clientip,correction_type,re_submit_flag,cropyear,season,soc_status) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?)";
                
//                pst = con.prepareStatement(qry1);
                pst = entityManager.createNativeQuery(qry1);
                
                pst.setParameter(1, wbldcode);
                pst.setParameter(2, mandcode);
                pst.setParameter(3, villcode);
                pst.setParameter(4, Integer.parseInt(bookingIds_org[i]));
                pst.setParameter(5, sr_no_org[i]);
                pst.setParameter(6, Integer.parseInt(kh_no_org[i]));
                pst.setParameter(7, Integer.parseInt(cropName_org[i]));
                pst.setParameter(8, Integer.parseInt(varietyName_org[i]));
                pst.setParameter(9, Date.valueOf(sowDt_org[i]));
                pst.setParameter(10, cr_no_org[i]);
                pst.setParameter(11, aadharId_org[i]);
                pst.setParameter(12, Double.valueOf(totExtent_org[i]));
                pst.setParameter(13, Integer.parseInt(waterId_org[i]));
                if (varietyName[i].trim().isEmpty()) {
                    pst.setParameter(14, 0);
                } else {
                    pst.setParameter(14, Integer.parseInt(varietyName[i].trim()));
                }

                if (farmername[i].trim().isEmpty()) {
                    pst.setParameter(15, null);
                } else {
                    pst.setParameter(15, farmername[i]);
                }
                if (farmerFather[i].trim().isEmpty()) {
                    pst.setParameter(16, null);
                } else {
                    pst.setParameter(16, farmerFather[i]);
                }
                if (totExtent[i].trim().isEmpty()) {
                    pst.setParameter(17, 0);
                } else {
                    pst.setParameter(17, Double.valueOf(totExtent[i]));
                }
                if (waterId[i].trim().isEmpty()) {
                    pst.setParameter(18, 0);
                } else {
                    pst.setParameter(18, Integer.parseInt(waterId[i]));
                }
                if (sowDt[i].trim().isEmpty()) {
                    pst.setParameter(19, null);
                } else {
                    pst.setParameter(19, Date.valueOf(sowDt[i]));
                }
                if (cropName[i].trim().isEmpty()) {
                    pst.setParameter(20, 0);
                } else {
                    pst.setParameter(20, Integer.parseInt(cropName[i]));
                }
                pst.setParameter(21, farmingType_org[i]);
                if (farmingType[i].trim().isEmpty()) {
                    pst.setParameter(22, null);
                } else {
                    pst.setParameter(22, (farmingType[i]));
                }
                if (cr_no_org[i].trim().isEmpty()) {
                    pst.setParameter(23, null);
                } else {
                    pst.setParameter(23, null);
                }

                pst.setParameter(24, applName);
                pst.setParameter(25, appMobId);
                pst.setParameter(26, applicatonId);
                pst.setParameter(27, clientip);
                pst.setParameter(28, "1");
                pst.setParameter(29, "Y");
                pst.setParameter(30, 2023);
                pst.setParameter(31, "R");
                if (action_type[i].equals("del")) {
                    pst.setParameter(32, "Y");
                } else {
                    pst.setParameter(32, null);
                }
                
                grievance_update = pst.executeUpdate();

                if (action_type[i].equals("del")) {

                    String record_delete = "Y";
                    String qry2 = "update " + partitionName + " set mark_delet='Y',social_status=?, reason_non_auth=? where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and cr_sow_date=?";
                   
//                    ps1 = con.prepareStatement(qry2);
                    ps1 = entityManager.createNativeQuery(qry2);
                    
                    ps1.setParameter(1, record_delete);
                    ps1.setParameter(2, Integer.parseInt(del_list[i]));
                    ps1.setParameter(3, wbldcode);
                    ps1.setParameter(4, Integer.parseInt(bookingIds_org[i]));
                    ps1.setParameter(5, Integer.parseInt(cropName_org[i]));
                    ps1.setParameter(6, cr_no_org[i]);
                    ps1.setParameter(7, villcode);
                    ps1.setParameter(8, Integer.parseInt(varietyName_org[i]));
                    ps1.setParameter(9, Date.valueOf(sowDt_org[i]));
                    
//                    ps1.addBatch();
                    ps1.executeUpdate(); 
//                    int[] res1 = ps1.executeBatch();
                    int res1 = ps1.executeUpdate();
                    for (int k = 0; k < bookingIds.length; k++) {
                        if (res1 == 0) {
                            flag = 0;
                            return "";   // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                            con.rollback();
                        }
                        break;
                    }
                }

                String qry = "select grievance_id from " + farmerGrievTab + " where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and kh_no=? and cr_sow_date=?";
                
//                ps1 = con.prepareStatement(qry);
                ps1 = entityManager.createNativeQuery(qry);
                
                ps1.setParameter(1, wbldcode);
                ps1.setParameter(2, Integer.parseInt(bookingIds_org[i]));
                ps1.setParameter(3, Integer.parseInt(cropName_org[i]));
                ps1.setParameter(4, cr_no_org[i]);
                ps1.setParameter(5, villcode);
                ps1.setParameter(6, Integer.parseInt(varietyName_org[i]));
                ps1.setParameter(7, Integer.parseInt(kh_no_org[i]));
                ps1.setParameter(8, Date.valueOf(sowDt_org[i]));
                
                List<Object[]> object = ps1.getResultList();
                
//                rs = ps1.executeQuery();
                
                if (object.size() > 0) {
                    greivanceId = object.indexOf(0);

                }

                String qry3 = "INSERT INTO " + socialAuditRejTab + "(part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, "
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
                        + "oldextent, uidmultiple, grievance_id, social_status) select part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, "
                        + "cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, "
                        + "imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, "
                        + "cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, "
                        + "email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, damaged_ext_bel_33, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, "
                        + "erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, "
                        + "crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, "
                        + "cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, "
                        + "qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, "
                        + "reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, "
                        + "dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, vao_verify, vro_obj_verify, "
                        + "dt_obj_verify, vroauthuserid, pmfbycode, vro_auth_uid, supercheck_userid, ins_scheme, exception_catg, superchk_remarks, suprejreason, sms_sent, 'Y', mark_delet, finalrec, "
                        + "seedprod_agency, crop_status, crop_agency, ack_gen, vaaauthuserid, vaa_auth_uid, damaged_ext_abv_33, lockedext, unlockedext, dt_damage_entry, lock_reason, unlock_reason, iutype, "
                        + "farmer_type, dt_unlock_ext, dt_lock_ext, invaliduid, sms_send, oldharvest_date, supercheckts, mao_unlock_appr,dt_mao_unlock_appr, mao_unlock_rsn, mao_unlock_remarks, ekyc_uid_mismatch, "
                        + "supercheck_appr, extent_check, oldextent, uidmultiple," + greivanceId + ", social_status from " + partitionName + " where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and kh_no=? and cr_sow_date=?";
               
//                ps1 = con.prepareStatement(qry3);
                ps1 = entityManager.createNativeQuery(qry3);
                
                ps1.setParameter(1, wbldcode);
                ps1.setParameter(2, Integer.parseInt(bookingIds_org[i]));
                ps1.setParameter(3, Integer.parseInt(cropName_org[i]));
                ps1.setParameter(4, cr_no_org[i]);
                ps1.setParameter(5, villcode);
                ps1.setParameter(6, Integer.parseInt(varietyName_org[i]));
                ps1.setParameter(7, Integer.parseInt(kh_no_org[i]));
                ps1.setParameter(8, Date.valueOf(sowDt_org[i]));
                
                social_update = ps1.executeUpdate();

                String griev_comp = "Y";
                String qry2 = "update " + partitionName + " set griev_comp=?,grievance_id=? where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=? and cr_sow_date=?";
                
//                ps1 = con.prepareStatement(qry2);
                ps1 = entityManager.createNativeQuery(qry2);
                
                ps1.setParameter(1, griev_comp);
                ps1.setParameter(2, greivanceId);
                ps1.setParameter(3, wbldcode);
                ps1.setParameter(4, Integer.parseInt(bookingIds_org[i]));
                ps1.setParameter(5, Integer.parseInt(cropName_org[i]));
                ps1.setParameter(6, cr_no_org[i]);
                ps1.setParameter(7, villcode);
                ps1.setParameter(8, Integer.parseInt(varietyName_org[i]));
                ps1.setParameter(9, Date.valueOf(sowDt_org[i]));
                
//                ps1.addBatch();

//                int[] res1 = ps1.executeBatch();
                int res1 = ps1.executeUpdate(); 

            }
        }
        
        if (grievance_update > 0 && social_update > 0) {
            flag = 1;

        }

        if (flag == 1) {
//            con.commit();
            msg = "Successfully Updated Rabi 2023-2024 Grievance Details";
//            response.sendRedirect("SocialAudit.jsp?msg=" + msg);

        } else {

//            con.rollback();
            msg = "Updation Failed";
//            response.sendRedirect("SocialAudit.jsp?msg=" + msg);
        }
        
        return msg;
		
		
		
		
		
		
		
		
		
		
		
		
//		int executeInsert = 0, executeDelete = 0;
//		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//		String insertQuery = "insert into ecrop2023.farmer_grievances(cr_dist_code, cr_mand_code, cr_vcode,  bookingid, cr_sno, kh_no, "
//                + " cr_crop, variety, cr_sow_date, cr_no, famername_crd, fathername_crd, uid_crd, cr_mix_unmix_ext,cr_w_draw,"
//                + " cr_sow_type,variety_sug, farmername_sug, fathername_sug, extent_sug, "
//                + " cr_w_draw_sug, cr_sow_type_sug, cr_sow_date_sug,cr_crop_sug,cropschtype,cropschtype_sug,applicant_name, "
//                + " applicant_mobile,applicant_id,clientip,correction_type,re_submit_flag,dt_crt) "
//                + "values(?,?,?,?,?"
//                + ",?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,now())";
//		Query pst = entityManager.createNativeQuery(insertQuery);
//		
//		for (int i = 0; i < bookingIds_org.length; i++) {
//            pst.setParameter(1, wbldcode);
//            pst.setParameter(2, mandcode);
//            pst.setParameter(3, villcode);
//            pst.setParameter(4, Integer.parseInt(bookingIds_org[i]));
//            pst.setParameter(5, sr_no_org[i]);
//            pst.setParameter(6, Integer.parseInt(kh_no_org[i]));
//            pst.setParameter(7, Integer.parseInt(cropName_org[i]));
//            pst.setParameter(8, Integer.parseInt(varietyName_org[i]));
//            pst.setParameter(9, Date.valueOf(sowDt_org[i]));
//            pst.setParameter(10, cr_no_org[i]);
//            pst.setParameter(11, farmername_org[i]);
//            pst.setParameter(12, farmerFather_org[i]);
//            pst.setParameter(13, aadharId_org[i]);
//            pst.setParameter(14, Double.valueOf(totExtent_org[i]));
//            pst.setParameter(15, Integer.parseInt(waterId_org[i]));
//            pst.setParameter(16, Integer.parseInt(cropNatureId_org[i]));
//            if (varietyName[i].trim().isEmpty()) {
//                pst.setParameter(17, 0);
//            } else {
//                pst.setParameter(17, Integer.parseInt(varietyName[i].trim()));
//            }
//
//            if (farmername[i].trim().isEmpty()) {
//                pst.setParameter(18, null);
//            } else {
//                pst.setParameter(18, farmername[i]);
//            }
//            if (farmerFather[i].trim().isEmpty()) {
//                pst.setParameter(19, null);
//            } else {
//                pst.setParameter(19, farmerFather[i]);
//            }
//            if (totExtent[i].trim().isEmpty()) {
//                pst.setParameter(20, 0);
//            } else {
//                pst.setParameter(20, Double.valueOf(totExtent[i]));
//            }
//            if (waterId[i].trim().isEmpty()) {
//                pst.setParameter(21, 0);
//            } else {
//                pst.setParameter(21, Integer.parseInt(waterId[i].trim()));
//            }
//            if (cropNatureId[i].trim().isEmpty()) {
//                pst.setParameter(22, 0);
//            } else {
//                pst.setParameter(22, Integer.parseInt(cropNatureId[i]));
//            }
//            if (sowDt[i].trim().isEmpty()) {
//                pst.setParameter(23, null);
//            } else {
//                pst.setParameter(23, Date.valueOf(sowDt[i]));
//            }
//            if (cropName[i].trim().isEmpty()) {
//                pst.setParameter(24, 0);
//            } else {
//                pst.setParameter(24, Integer.parseInt(cropName[i]));
//            }
//            pst.setParameter(25, farmingType_org[i]);
//            if (farmingType[i].trim().isEmpty()) {
//                pst.setParameter(26, null);
//            } else {
//                pst.setParameter(26, (farmingType[i]));
//            }
//            
//            pst.setParameter(27, applName);
//            pst.setParameter(28, appMobId);
//            pst.setParameter(29, applicatonId);
//            pst.setParameter(30, clientip);
//            pst.setParameter(31, "1");
//            pst.setParameter(32, "Y");
//            
//            executeInsert = pst.executeUpdate();
//
//            if (action_type[i].equals("del")) {
//                String partitionName = "cr_details_";
//
//                if (wbldcode <= 9) {
//                    partitionName = "ecrop" + cropyear + "." + partitionName + season + "0" + wbldcode + cropyear;
//                } else {
//                    partitionName = "ecrop" + cropyear + "." + partitionName + season + wbldcode + cropyear;
//                }
//               
//                String record_delete = "Y";
//                String qry2 = "update " + partitionName + " set mark_delet=?, reason_non_auth=? where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=?";
//                Query ps1 = entityManager.createNativeQuery(qry2);
//                
//                System.out.println("wbldcode -------> "+wbldcode);
//                System.out.println("bookingIds_org -------> "+bookingIds_org[i]);
//                System.out.println("cropName_org -------> "+cropName_org[i]);
//                System.out.println("cr_no_org -------> "+cr_no_org[i]);
//                System.out.println("villcode -------> "+villcode);
//                System.out.println("varietyName_org -------> "+varietyName_org[i]);
//
//                
//                ps1.setParameter(1, record_delete);
//                ps1.setParameter(2, Integer.parseInt(del_list[i]));
//                ps1.setParameter(3, wbldcode);
//                ps1.setParameter(4, Integer.parseInt(bookingIds_org[i]));
//                ps1.setParameter(5, Integer.parseInt(cropName_org[i]));
//                ps1.setParameter(6, cr_no_org[i]);
//                ps1.setParameter(7, villcode);
//                ps1.setParameter(8, Integer.parseInt(varietyName_org[i]));
//                executeDelete = ps1.executeUpdate();
//        }
//		
//	}
//		return executeInsert;
//	
	}
}




















//package com.ecrops.repo;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.Arrays;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.transaction.Transactional;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class SaveSocialAudit {
//	
//	@PersistenceContext
//	private EntityManager entityManager;
//	
//	@Transactional
//	public int saveSocialAuditDetails(String[] bookingIds, String[] kh_no, String[] sr_no, String[] sowDt, String[] cropName, 
//			String[] varietyName, String[] totExtent, String[] farmername, String[] farmerFather, String[] aadharId, String[] waterId, 
//			String[] cropNatureId, String[] farmingType, String[] bookingIds_org, String[] cr_no_org, String[] kh_no_org, String[] sr_no_org, 
//			String[] sowDt_org, String[] cropName_org, String[] varietyName_org, String[] totExtent_org, String[] farmername_org, 
//			String[] farmerFather_org, String[] aadharId_org, String[] waterId_org, String[] cropNatureId_org, String[] farmingType_org,   
//			String[] action_type, String[] del_list, String applName, String appMobId, String applicatonId, String clientip, 
//			String season, String cropyear, Integer wbldcode, Integer mandcode, Integer villcode) {
//		System.out.println("farmerName---->"+Arrays.toString(farmername_org)+"farmer"+Arrays.toString(farmername));
//		int executeInsert = 0, executeDelete = 0;
//		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//		String insertQuery = "insert into ecrop2024.farmer_grievances(cr_dist_code, cr_mand_code, cr_vcode,  bookingid, cr_sno, kh_no, "
//                + " cr_crop, variety, cr_sow_date, cr_no, famername_crd, fathername_crd, uid_crd, cr_mix_unmix_ext,cr_w_draw,"
//                + " cr_sow_type,variety_sug, farmername_sug, fathername_sug, extent_sug, "
//                + " cr_w_draw_sug, cr_sow_type_sug, cr_sow_date_sug,cr_crop_sug,cropschtype,cropschtype_sug,applicant_name, "
//                + " applicant_mobile,applicant_id,clientip,correction_type,re_submit_flag,dt_crt) "
//                + "values(?,?,?,?,?"
//                + ",?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,?,?,?,"
//                + "?,?,now())";
//		Query pst = entityManager.createNativeQuery(insertQuery);
//		
//		for (int i = 0; i < bookingIds_org.length; i++) {
//            pst.setParameter(1, wbldcode);
//            pst.setParameter(2, mandcode);
//            pst.setParameter(3, villcode);
//            pst.setParameter(4, Integer.parseInt(bookingIds_org[i]));
//            pst.setParameter(5, sr_no_org[i]);
//            pst.setParameter(6, Integer.parseInt(kh_no_org[i]));
//            pst.setParameter(7, Integer.parseInt(cropName_org[i]));
//            pst.setParameter(8, Integer.parseInt(varietyName_org[i]));
//            pst.setParameter(9, Date.valueOf(sowDt_org[i]));
//            pst.setParameter(10, cr_no_org[i]);
//            pst.setParameter(11, farmername_org[i]);
//            pst.setParameter(12, farmerFather_org[i]);
//            pst.setParameter(13, aadharId_org[i]);
//            pst.setParameter(14, Double.valueOf(totExtent_org[i]));
//            pst.setParameter(15, Integer.parseInt(waterId_org[i]));
//            pst.setParameter(16, Integer.parseInt(cropNatureId_org[i]));
//            if (varietyName[i].trim().isEmpty()) {
//                pst.setParameter(17, 0);
//            } else {
//                pst.setParameter(17, Integer.parseInt(varietyName[i].trim()));
//            }
//
//            if (farmername[i].trim().isEmpty()) {
//                pst.setParameter(18, null);
//            } else {
//                pst.setParameter(18, farmername[i]);
//            }
//            if (farmerFather[i].trim().isEmpty()) {
//                pst.setParameter(19, null);
//            } else {
//                pst.setParameter(19, farmerFather[i]);
//            }
//            if (totExtent[i].trim().isEmpty()) {
//                pst.setParameter(20, 0);
//            } else {
//                pst.setParameter(20, Double.valueOf(totExtent[i]));
//            }
//            if (waterId[i].trim().isEmpty()) {
//                pst.setParameter(21, 0);
//            } else {
//                pst.setParameter(21, Integer.parseInt(waterId[i].trim()));
//            }
//            if (cropNatureId[i].trim().isEmpty()) {
//                pst.setParameter(22, 0);
//            } else {
//                pst.setParameter(22, Integer.parseInt(cropNatureId[i]));
//            }
//            if (sowDt[i].trim().isEmpty()) {
//                pst.setParameter(23, null);
//            } else {
//                pst.setParameter(23, Date.valueOf(sowDt[i]));
//            }
//            if (cropName[i].trim().isEmpty()) {
//                pst.setParameter(24, 0);
//            } else {
//                pst.setParameter(24, Integer.parseInt(cropName[i]));
//            }
//            pst.setParameter(25, farmingType_org[i]);
//            if (farmingType[i].trim().isEmpty()) {
//                pst.setParameter(26, null);
//            } else {
//                pst.setParameter(26, (farmingType[i]));
//            }
//            
//            pst.setParameter(27, applName);
//            pst.setParameter(28, appMobId);
//            pst.setParameter(29, applicatonId);
//            pst.setParameter(30, clientip);
//            pst.setParameter(31, "1");
//            pst.setParameter(32, "Y");
//            
//            executeInsert = pst.executeUpdate();
//
//            if (action_type[i].equals("del")) {
//                String partitionName = "cr_details_";
//
//                if (wbldcode <= 9) {
//                    partitionName = "ecrop" + cropyear + "." + partitionName + season + "0" + wbldcode + cropyear;
//                } else {
//                    partitionName = "ecrop" + cropyear + "." + partitionName + season + wbldcode + cropyear;
//                }
//               
//                String record_delete = "Y";
//                String qry2 = "update " + partitionName + " set mark_delet=?, reason_non_auth=? where cr_dist_code=? and bookingid=? and cr_crop=? and cr_no=? and cr_vcode=? and variety=?";
//                Query ps1 = entityManager.createNativeQuery(qry2);
//                
//                System.out.println("wbldcode -------> "+wbldcode);
//                System.out.println("bookingIds_org -------> "+bookingIds_org[i]);
//                System.out.println("cropName_org -------> "+cropName_org[i]);
//                System.out.println("cr_no_org -------> "+cr_no_org[i]);
//                System.out.println("villcode -------> "+villcode);
//                System.out.println("varietyName_org -------> "+varietyName_org[i]);
//
//                
//                ps1.setParameter(1, record_delete);
//                ps1.setParameter(2, Integer.parseInt(del_list[i]));
//                ps1.setParameter(3, wbldcode);
//                ps1.setParameter(4, Integer.parseInt(bookingIds_org[i]));
//                ps1.setParameter(5, Integer.parseInt(cropName_org[i]));
//                ps1.setParameter(6, cr_no_org[i]);
//                ps1.setParameter(7, villcode);
//                ps1.setParameter(8, Integer.parseInt(varietyName_org[i]));
//                executeDelete = ps1.executeUpdate();
//        }
//		
//	}
//		return executeInsert;
//	
//	}
//}
