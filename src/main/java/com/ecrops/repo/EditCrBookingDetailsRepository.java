package com.ecrops.repo;

import com.ecrops.model.EditCrBookingDtlsEntity;
import com.ecrops.model.EditCropBookingDetailsModel;
import com.itextpdf.text.log.SysoCounter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EditCrBookingDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    public EditCrBookingDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EditCrBookingDtlsEntity> getEditCrBookingDetails(EditCropBookingDetailsModel ecbd, String partKey) {

        List<EditCrBookingDtlsEntity> ecbdList = new ArrayList<>();
        
        Integer cropYear=ecbd.getCropYear();
          
      String ecropTable= "ecrop"+cropYear + "." + "cr_details_" + partKey; 
      
      String ecropYears="ecrop"+cropYear;
      
  
        
        if (1 == ecbd.getCorrectionType()) {
            String getCorrection1DetailsQry = "select occupname,occupfname,oc_name,oc_fname,cr_sno,kh_no,cr_vcode,bookingid,cr_no, cr_sow_date,cr_crop,rec_id, variety,c.cropname, v.varietyname,cr_farmeruid " +
                    " from "+ ecropTable +" as cr, cropnames c, cr_variety_master v where cr_vcode=" + ecbd.getvCode() + " and cr_year=" + ecbd.getCropYear() + " and cr_season='" + ecbd.getSeason() + "' " +
                    "and cultivator_type in ('O','L') " + getSearchValue(ecbd) + "and (vaaauth ='N' and c.cropid = cr.cr_crop and v.varietycode = cr.variety " +
                    " and bookingid not in (select bookingid from ecrop2024.cr_details_org_details where correctiontype='" + ecbd.getCorrectionType() + "' and  cr_vcode=" + ecbd.getvCode() + ") " +
                    "or ( vaaauth ='Y' and vro_verify='Y' and bookingid  in (select bookingid from vro_rej_details  where cr_vcode= " + ecbd.getvCode() + " and rej_reason=2 ))) ";

            ecbdList = jdbcTemplate.query(getCorrection1DetailsQry, new EditCrBookingDetailsRowMapper<EditCrBookingDtlsEntity>());
        } else if (3 == ecbd.getCorrectionType()) {
            String getCorrection15DetailsQry = "select occupname,occupfname,oc_name,oc_fname,cr_sno,kh_no,cr_vcode,bookingid,cr_no, cr_sow_date,cr_crop,rec_id,variety,c.cropname, v.varietyname,cr_farmeruid " +
                    " from "+ ecropTable +" as cr, cropnames c, cr_variety_master v where cultivator_type in ('O','L','C') and occupname like '%ఇతరులు%' and cr_vcode=" + ecbd.getvCode() +
                    " and cr_year=" + ecbd.getCropYear() + " and cr_season='" + ecbd.getSeason() + "' " +
                    "and owner_tenant='O' " + getSearchValue(ecbd) + "and vaaauth ='N' and c.cropid = cr.cr_crop and v.varietycode = cr.variety " +
                    " and bookingid not in (select bookingid from ecrop2024.cr_details_org_details where correctiontype=' " + ecbd.getCorrectionType() + "' and  cr_vcode=" + ecbd.getvCode() + ") ";

            ecbdList = jdbcTemplate.query(getCorrection15DetailsQry, new EditCrBookingDetailsRowMapper<EditCrBookingDtlsEntity>()); System.out.println("Itharu qry:"+getCorrection15DetailsQry);
        } else if (5 == ecbd.getCorrectionType()) {
            String getCorrection5DetailsQry = " select cs.cropschdesc,cropseed_scheme,cr_mix_unmix_ext,seed_production,irrmethod,irm.irgdesc,cr_w_draw,oc_name,oc_fname,rec_id, " +
                    "         cr_sno,kh_no,occupname,occupfname,cr_vcode,bookingid,cr_crop,cr_no,cr_sow_date,variety,cr_farmeruid, cn.cropname" +
                    " from "+ ecropTable +" as a,cropirrgmethod_master irm ,cropseed_scheme cs , cropnames cn where cs.cropschtype=a.cropseed_scheme and irrmethod=irm.irgcode  " +
                    " and cr_vcode=" + ecbd.getvCode() + " and cr_year= " + ecbd.getCropYear() + " and cr_season= '" + ecbd.getSeason() + "' " + getSearchValue(ecbd) +
                    //" and  vaaauth ='N' " +
                    " and cn.cropid = a.cr_crop and  bookingid not in (select bookingid from ecrop2024.cr_details_org_details " +
                    " where correctiontype='5' and cr_vcode=" + ecbd.getvCode() + " and cr_year=" + ecbd.getCropYear() + " and cr_season='" + ecbd.getSeason() + "')  ";

            ecbdList = jdbcTemplate.query(getCorrection5DetailsQry, new EditCrBookingDetails2RowMapper<EditCrBookingDtlsEntity>());
            
            System.out.println("getCorrection5DetailsQry---->>>"+getCorrection5DetailsQry);
        } else if (4 == ecbd.getCorrectionType()) {
            String getCorrection4DetailsQry = "select occupname,occupfname,oc_name,oc_fname,cr_sno,kh_no,occupname,occupfname,cr_vcode,bookingid,cr_crop, variety,c.cropname, v.varietyname,cr_no,rec_id," +
                    "cr_sow_date,cr_farmeruid from "+ ecropTable+" as cr, cropnames c, cr_variety_master v " +
                    " where   cultivator_type in ('C') and cr_vcode=" + ecbd.getvCode() + " and cr_year=  " + ecbd.getCropYear() +
                    "  and cr_season= '" + ecbd.getSeason() + "' " + getSearchValue(ecbd) + "and c.cropid = cr.cr_crop and v.varietycode = cr.variety" +
                    "  and vaaauth ='N' and bookingid not in (select bookingid from ecrop2024.cr_booking_org_details where  correctiontype= '" + ecbd.getCorrectionType() + "' and cr_vcode= " + ecbd.getvCode() +
                    "  and cr_year= " + ecbd.getCropYear() + " and cr_season= '" + ecbd.getSeason() + "')";

            ecbdList = jdbcTemplate.query(getCorrection4DetailsQry, new EditCrBookingDetailsRowMapper<EditCrBookingDtlsEntity>());
            System.out.println("getCorrection4DetailsQry>>>>>>>"+getCorrection4DetailsQry);
        } else {
            String getCorrection15DetailsQry = "select occupname,occupfname,oc_name,oc_fname,cr_sno,kh_no,cr_vcode,bookingid, cr_no,cr_sow_date,cr_crop, variety,c.cropname, v.varietyname,cr_farmeruid,rec_id " +
                    " from "+ ecropTable +" as cr, cropnames c, cr_variety_master v where cr_vcode=" + ecbd.getvCode() + " and cr_year=" + ecbd.getCropYear() + " and cr_season='" + ecbd.getSeason() + "' " +
                    "and owner_tenant='O' " + getSearchValue(ecbd) + "and vaaauth ='Y' and vroauth='Y' and ekyc is null and c.cropid = cr.cr_crop and v.varietycode = cr.variety " +
                    " and bookingid not in (select bookingid from ecrop2024.cr_details_org_details where correctiontype='" + ecbd.getCorrectionType() + "' and  cr_vcode=" + ecbd.getvCode() + ") ";

            ecbdList = jdbcTemplate.query(getCorrection15DetailsQry, new EditCrBookingDetailsRowMapper<EditCrBookingDtlsEntity>());
        }
        return ecbdList;
    }

    public void updateCrAadharNo(EditCrBookingDtlsEntity ecbd) {
    	System.out.println("Step::1");

        Integer cropyear = ecbd.getCropyear();
        System.out.println("Step::2"+ecbd.getWbdcode());
        String wbdocde = (ecbd.getWbdcode() <= 9 ? "0" + ecbd.getWbdcode() : String.valueOf(ecbd.getWbdcode()));
     
        String tab1 = "cr_booking_partition_" + ecbd.getSeason() + wbdocde + cropyear;
        String tab2 = "cr_details_" + ecbd.getSeason() + wbdocde + cropyear;
        String crdetails_org = "cr_details_org_details";

        tab1 = "ecrop" + cropyear + "." + tab1;
        tab2 = "ecrop" + cropyear + "." + tab2;
        crdetails_org = "ecrop" + cropyear + "." + crdetails_org;

        String crdorg_qry = " INSERT INTO " + crdetails_org + "( " +
                " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid,"
                + " harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck,"
                + "  smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) " +
                " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext,"
                + "  cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei,"
                + " uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch,"
                + " cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, "
                + " email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent,"
                + " ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, "
                + " dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, "
                + " irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
                + " crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
                + " ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, "
                + " ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'15',now(), vao_verify  " +
                " FROM " + tab2 + " where  bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() + "' and cr_crop=" + ecbd.getCr_crop() +
                " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";

        crdorg_qry += getSearchValue(ecbd);
        
       
        int result = jdbcTemplate.update(crdorg_qry);
        System.out.println("Updated : " + result + " Records.");


        String updateUidQry = "  UPDATE " + tab1 + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ", cr_farmeruid=" + ecbd.getNewAadharNo() +
                " where bookingid=" + ecbd.getBookingid() + " and cr_vcode=" + ecbd.getCr_vcode() + "   and cr_year=" + cropyear + "  and cr_season='" + ecbd.getSeason() + "'" + " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateUidQry += getSearchValue(ecbd);


        int result2 = jdbcTemplate.update(updateUidQry);
        System.out.println("Updated Aadhar number for [" + result2 + "] Records.");

        String updateEkycQry = "  UPDATE " + tab2 + " set  ekyc_uid_mismatch='Y',cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ",cr_farmeruid=" + ecbd.getNewAadharNo() + " where "
                + " ekyc is null and vaaauth='Y' and vroauth='Y' and bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() +
                "' and cr_crop=" + ecbd.getCr_crop() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' " + " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateEkycQry += getSearchValue(ecbd);

		 int result3 = jdbcTemplate.update(updateEkycQry);
		  System.out.println("Updated Ekyc Details for [" + result3 + "] Records.");
		
    }

    public void updateCcrcCultivatorDetails(EditCrBookingDtlsEntity ecbd) {

        Integer cropyear = ecbd.getCropyear();
        String wbdocde = (ecbd.getWbdcode() <= 9 ? "0" + ecbd.getWbdcode() : String.valueOf(ecbd.getWbdcode()));

        String tab1 = "cr_booking_partition_" + ecbd.getSeason() + wbdocde + cropyear;
        String tab2 = "cr_details_" + ecbd.getSeason() + wbdocde + cropyear;
        String crdetails_org = "cr_details_org_details";
        String crbookorg_tab = "cr_booking_org_details";

        tab1 = "ecrop" + cropyear + "." + tab1;
        tab2 = "ecrop" + cropyear + "." + tab2;
        crdetails_org = "ecrop" + cropyear + "." + crdetails_org;
        crbookorg_tab ="ecrop" + cropyear + "." + crbookorg_tab;

        String crdorg_qry = " INSERT INTO " + crdetails_org + "( " +
                " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid,"
                + " harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck,"
                + "  smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) " +
                " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext,"
                + "  cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei,"
                + " uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch,"
                + " cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, "
                + " email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent,"
                + " ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, "
                + " dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, "
                + " irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, "
                + " crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, "
                + " ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, "
                + " ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'4',now(), vao_verify  " +
                " FROM " + tab2 + " where  bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() + "' and cr_crop=" + ecbd.getCr_crop() +
                " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' " + " and rec_id ='" + ecbd.getRec_id() + "' ";

        crdorg_qry += getSearchValue(ecbd);

        System.out.println("crdorg_qry-->"+crdorg_qry);
        int result = jdbcTemplate.update(crdorg_qry);
        System.out.println("Updated : " + result + " Records.");
        
        
        String crborg_qry = " INSERT INTO " + crbookorg_tab + "( "
                + " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime,"
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type,"
                + " refbookingid, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old, correctiontype, correction_date) "
                + " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, "
                + " cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime, "
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, "
                + " crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type, refbookingid, "
                + " culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old,'4',now()  "
                + " FROM " + tab1   + " where  bookingid=" + ecbd.getBookingid() +" and cr_vcode=" + ecbd.getCr_vcode() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";

        crdorg_qry += getSearchValue(ecbd);

        System.out.println("crborg_qry-->"+crborg_qry);
        int result1 = jdbcTemplate.update(crborg_qry);
        System.out.println("Updated : " + result1 + " Records.");
        

        String updateUidQry = "  UPDATE " + tab1 + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ", cr_farmeruid=" + ecbd.getNewAadharNo() +
                ", occupname= '" + ecbd.getNewOccupName() + "', occupfname= '" + ecbd.getNewOccupFName() + "' " +
                " where bookingid=" + ecbd.getBookingid() + " and cr_vcode=" + ecbd.getCr_vcode() + "   and cr_year=" + cropyear + "  and cr_season='" + ecbd.getSeason() + "'" + " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateUidQry += getSearchValue(ecbd);

        System.out.println("updateUidQry-->"+updateUidQry);
        
        int result2 = jdbcTemplate.update(updateUidQry);
        System.out.println("Updated Aadhar number for [" + result2 + "] Records.");

        String updateEkycQry = "  UPDATE " + tab2 + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ",cr_farmeruid=" + ecbd.getNewAadharNo() +
                " , vao_verify='Y', occupname= '" + ecbd.getNewOccupName() + "', occupfname= '" + ecbd.getNewOccupFName() + "' where "
                + " bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() +
                "' and cr_crop=" + ecbd.getCr_crop() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' " + " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateEkycQry += getSearchValue(ecbd);

        int result3 = jdbcTemplate.update(updateEkycQry);
        System.out.println("Updated CCRC Details for [" + result3 + "] Records.");

    }

    public void updateCultivatorCropDetails(EditCrBookingDtlsEntity ecbd) {

        Integer cropyear = ecbd.getCropyear();
        String scheema = "ecrop"+cropyear;
        String wbdocde = (ecbd.getWbdcode() <= 9 ? "0" + ecbd.getWbdcode() : String.valueOf(ecbd.getWbdcode()));
        String tab1 = scheema+".cr_booking_partition_" + ecbd.getSeason() + wbdocde + cropyear;
        String tab2 = scheema+".cr_details_" + ecbd.getSeason() + wbdocde + cropyear;
        String crdetails_org = scheema+".cr_details_org_details";

        String crbooking_org = scheema+".cr_booking_org_details";

        String crbkorg_qry = " INSERT INTO " + crbooking_org + "( "
                + " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime,"
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type,"
                + " refbookingid, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old, correctiontype, correction_date) "
                + " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, "
                + " cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime, "
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, "
                + " crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type, refbookingid, "
                + " culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old,'5',now()  "
                + " FROM " + tab1   + " where  bookingid=" + ecbd.getBookingid() +" and cr_vcode=" + ecbd.getCr_vcode() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        crbkorg_qry += getSearchValue(ecbd);

        System.out.println("crbooking qrt::===>>"+crbkorg_qry);
        int result = jdbcTemplate.update(crbkorg_qry);
        System.out.println("Updated : " + result + " Records.");

        String crdorg_qry=" INSERT INTO "+crdetails_org+"( "+
                " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) "+
                " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, " +
                " diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'5',now(), vao_verify  "+
                " FROM " + tab2 + " where  bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() + "' and cr_crop=" + ecbd.getCr_crop() +
                " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                " and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";

        int result2 = jdbcTemplate.update(crdorg_qry);
        System.out.println("crdorg_qry qrt::===>>"+crdorg_qry);
        
        System.out.println("Updated : " + result2 + " Records.");

        String updateUidQry = "  UPDATE " + tab1 + " set variety="+ecbd.getVariety_new()+",irrmethod= " + ecbd.getIrrmethod() +
                " where bookingid=" + ecbd.getBookingid() + " and cr_vcode=" + ecbd.getCr_vcode() + "   and cr_year=" + cropyear + "  and cr_season='" + ecbd.getSeason() + "'" + " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateUidQry += getSearchValue(ecbd);


        int result3 = jdbcTemplate.update(updateUidQry);
        System.out.println("Updated Variety and irrigation Method Details for [" + result3 + "] Records.");

        String updateEkycQry = "  UPDATE " + tab2 + " set  vao_verify='Y', variety= "+ecbd.getVariety_new()+", cr_w_draw= "+ecbd.getWaterResId()+
                ", cropseed_scheme="+ecbd.getCropSeedScheme()+" where bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() +
                "' and cr_crop=" + ecbd.getCr_crop() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateEkycQry += getSearchValue(ecbd);

        int result4 = jdbcTemplate.update(updateEkycQry);
        System.out.println("Updated Ekyc Details for [" + result4 + "] Records.");

    }

    public void updateAadharNoOfPattaOrEnjoyer(EditCrBookingDtlsEntity ecbd) {

        Integer cropyear = ecbd.getCropyear();
        String wbdocde = (ecbd.getWbdcode() <= 9 ? "0" + ecbd.getWbdcode() : String.valueOf(ecbd.getWbdcode()));
        String tab1 = "cr_booking_partition_" + ecbd.getSeason() + wbdocde + cropyear;
        String tab2 = "cr_details_" + ecbd.getSeason() + wbdocde + cropyear;
        String crdetails_org = "cr_details_org_details";

        tab1 = "ecrop" + cropyear + "." + tab1;
        tab2 = "ecrop" + cropyear + "." + tab2;
        crdetails_org = "ecrop" + cropyear + "." + crdetails_org;


        String crdorg_qry = " INSERT INTO " + crdetails_org + "( " +
                " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext,"
                + " cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude,"
                + " latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype,"
                + " crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status,"
                + " cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, "
                + "dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq,"
                + " qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd,"
                + " oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, "
                + " dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,"
                + " vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) " +
                " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,"
                + " vaa_txn, vro_txn, vro_verify, dt_vroverify,'1',now(), vao_verify  " +
                " FROM "
                + tab2 + "  where  bookingid= " + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() + "' and cr_crop= " + ecbd.getCr_crop() +
                " and cr_vcode= " + ecbd.getCr_vcode() + " and cr_no= '" + ecbd.getCr_no() + "' and variety= " + ecbd.getVariety() +
                " and cr_year= " + cropyear + " and cr_season= '" + ecbd.getSeason() + "' " + " and rec_id ='" + ecbd.getRec_id() + "' ";

        crdorg_qry += getSearchValue(ecbd);

        int result = jdbcTemplate.update(crdorg_qry);
        System.out.println("Updated : " + result + " Records.");


        String updateUidQry = "  UPDATE " + tab1 + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ", cr_farmeruid=" + ecbd.getNewAadharNo() +
                " where bookingid=" + ecbd.getBookingid() + " and cr_vcode=" + ecbd.getCr_vcode() + "   and cr_year=" + cropyear + "  and cr_season='" + ecbd.getSeason() + "'"+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateUidQry += getSearchValue(ecbd);


        int result2 = jdbcTemplate.update(updateUidQry);
        System.out.println("Updated Aadhar number for [" + result2 + "] Records.");

        String updateEkycQry = "  UPDATE " + tab2 + " set  vao_verify='Y', cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ",cr_farmeruid=" + ecbd.getNewAadharNo() + " where "
                + " bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() +
                "' and cr_crop=" + ecbd.getCr_crop() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateEkycQry += getSearchValue(ecbd);

        int result3 = jdbcTemplate.update(updateEkycQry);
        System.out.println("Updated Ekyc Details for [" + result3 + "] Records.");

    }

    public void updateOthersNameChange(EditCrBookingDtlsEntity ecbd) {

        Integer cropyear = ecbd.getCropyear();
        String wbdocde = (ecbd.getWbdcode() <= 9 ? "0" + ecbd.getWbdcode() : String.valueOf(ecbd.getWbdcode()));
        String crbooktab = "ecrop2024.cr_booking_partition_" + ecbd.getSeason() + wbdocde + cropyear;
        String crdtab = "ecrop2024.cr_details_" + ecbd.getSeason() + wbdocde + cropyear;
        String crbookorgtab = "ecrop2024.cr_booking_org_details";
        String crdetorgtab = "ecrop2024.cr_details_org_details";

        //System.out.println("crbooktab<<<<<>>>>>>"+ crbooktab);
        
        String crbkorg_qry = " INSERT INTO " + crbookorgtab + "( "
                + " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime,"
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type,"
                + " refbookingid, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old, correctiontype, correction_date) "
                + " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, "
                + " cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime, "
                + " ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, "
                + " crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type, refbookingid, "
                + " culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old,'3',now()  "
                + " FROM " + crbooktab   + " where  bookingid=" + ecbd.getBookingid() +" and cr_vcode=" + ecbd.getCr_vcode() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";

        crbkorg_qry += getSearchValue(ecbd);

        int result = jdbcTemplate.update(crbkorg_qry);
        System.out.println("Updated : " + result + " Records.");
        System.out.println("crbkorg_qry====>>>"+crbkorg_qry);

        String crdorg_qry = " INSERT INTO " + crdetorgtab + "( "
                + " part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date, dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) "
                + " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, "
                + " cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress,"
                + "  dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode,"
                + "  irrcategory, age, soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email, mobileno, gender, avgbodyweight, harvestqty, stocking_size,"
                + "  tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq,"
                + "  dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, "
                + " cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck,"
                + "  jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec, vaa_txn, vro_txn, vro_verify, dt_vroverify,'3',now(), vao_verify  "
                + " FROM " + crdtab   + " where  bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() + "' and cr_crop=" + ecbd.getCr_crop() +
                " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";

       
        int result1 = jdbcTemplate.update(crdorg_qry);
        System.out.println("Updated : " + result1 + " Records.");
        System.out.println("crdorg_qry-22====>>>"+crdorg_qry);

        String updateUidQry = "  UPDATE " + crbooktab + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ", cr_farmeruid=" + ecbd.getNewAadharNo() +
                ", occupname= '" + ecbd.getNewOccupName() + "', occupfname= '" + ecbd.getNewOccupFName() + "' " +
                " where bookingid=" + ecbd.getBookingid() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_year=" + cropyear + "  and cr_season='" + ecbd.getSeason() + "'"+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateUidQry += getSearchValue(ecbd);


        int result2 = jdbcTemplate.update(updateUidQry);
        System.out.println("Updated Aadhar number for [" + result2 + "] Records.");

        String updateEkycQry = "  UPDATE " + crdtab + " set cr_farmeruid_old=" + ecbd.getCr_farmeruid() + ",cr_farmeruid=" + ecbd.getNewAadharNo() +
                " , vao_verify='Y', occupname= '" + ecbd.getNewOccupName() + "', occupfname= '" + ecbd.getNewOccupFName() + "' where "
                + " bookingid=" + ecbd.getBookingid() + " and CAST(cr_sow_date AS DATE) = '" + ecbd.getCr_sow_date() +
                "' and cr_crop=" + ecbd.getCr_crop() + " and cr_vcode=" + ecbd.getCr_vcode() + " and cr_no='" + ecbd.getCr_no() + "' and variety=" + ecbd.getVariety() +
                "   and cr_year=" + cropyear + " and cr_season='" + ecbd.getSeason() + "' "+ " and rec_id ='" + ecbd.getRec_id() + "' ";
        updateEkycQry += getSearchValue(ecbd);

        int result3 = jdbcTemplate.update(updateEkycQry);
        System.out.println("Updated Ekyc Details for [" + result3 + "] Records.");

    }

    public String getSearchValue(EditCrBookingDtlsEntity ecbd) {
        String searchValue = "";
        if (ecbd.getCr_sno() != null && !ecbd.getCr_sno().isEmpty()) {
            searchValue += " and cr_sno='" + ecbd.getCr_sno() + "'";
        } else if (ecbd.getKh_no() != null) {
            searchValue += " and kh_no=" + ecbd.getKh_no();
        } else if (ecbd.getCr_farmeruid() != null) {
            searchValue += " and cr_farmeruid='" + ecbd.getCr_farmeruid() + "'";
        }
        return searchValue;
    }

    public String getSearchValue(EditCropBookingDetailsModel ecbd) {
        String searchValue = "";
        if ("1".equalsIgnoreCase(ecbd.getSearchType())) {
            searchValue = "and cr_sno='" + ecbd.getSurveyNo() + "' ";
        } else if ("2".equalsIgnoreCase(ecbd.getSearchType())) {
            searchValue = "and kh_no = " + ecbd.getKathaNo();
        } else if ("3".equalsIgnoreCase(ecbd.getSearchType())) {
            searchValue = "and cr_farmeruid='" + ecbd.getAadharNo() + "' ";
        }
        return searchValue;
    }

    public static class EditCrBookingDetailsRowMapper<E> implements RowMapper<EditCrBookingDtlsEntity> {
        public EditCrBookingDtlsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            EditCrBookingDtlsEntity ecbd = new EditCrBookingDtlsEntity();
            ecbd.setOccupname(rs.getString("occupname"));
            ecbd.setOccupfname(rs.getString("occupfname"));
            ecbd.setCr_sno(rs.getString("cr_sno"));
            ecbd.setKh_no(rs.getInt("kh_no"));
            ecbd.setCr_vcode(rs.getInt("cr_vcode"));
            ecbd.setBookingid(rs.getInt("bookingid"));
            ecbd.setCr_crop(rs.getInt("cr_crop"));
            ecbd.setCr_no(rs.getString("cr_no"));
            ecbd.setCr_sow_date(rs.getString("cr_sow_date"));
            ecbd.setVariety(rs.getString("variety"));
            ecbd.setCr_farmeruid(rs.getString("cr_farmeruid"));
            ecbd.setOc_name(rs.getString("oc_name"));
            ecbd.setOc_fname(rs.getString("oc_fname"));
            ecbd.setCropname(rs.getString("cropname"));
            ecbd.setVarietyname(rs.getString("varietyname"));
            ecbd.setRec_id(rs.getInt("rec_id"));
            return ecbd;
        }
    }

    public static class EditCrBookingDetails2RowMapper<E> implements RowMapper<EditCrBookingDtlsEntity> {
        public EditCrBookingDtlsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            EditCrBookingDtlsEntity ecbd = new EditCrBookingDtlsEntity();
            ecbd.setCropschdesc(rs.getString("cropschdesc"));
            ecbd.setCropSeedScheme(rs.getString("cropseed_scheme"));
            ecbd.setCrMixUnmixExt(rs.getString("cr_mix_unmix_ext"));
            ecbd.setSeedProduction(rs.getString("seed_production"));
            ecbd.setIrrmethod(rs.getString("irrmethod"));
            ecbd.setIrgdesc(rs.getString("irgdesc"));
            ecbd.setCrWDraw(rs.getString("cr_w_draw"));
            ecbd.setCr_sno(rs.getString("cr_sno"));
            ecbd.setKh_no(rs.getInt("kh_no"));
            ecbd.setOccupname(rs.getString("occupname"));
            ecbd.setOccupfname(rs.getString("occupfname"));
            ecbd.setCr_vcode(rs.getInt("cr_vcode"));
            ecbd.setBookingid(rs.getInt("bookingid"));
            ecbd.setCr_crop(rs.getInt("cr_crop"));
            ecbd.setCr_no(rs.getString("cr_no"));
            ecbd.setCr_sow_date(rs.getString("cr_sow_date"));
            ecbd.setVariety(rs.getString("variety"));
            ecbd.setCr_farmeruid(rs.getString("cr_farmeruid"));
            ecbd.setCropname(rs.getString("cropname"));
            ecbd.setRec_id(rs.getInt("rec_id"));
            return ecbd;
        }
    }
}
