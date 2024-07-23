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
import com.ecrops.entity.EditBiometricNominee;

@Component
public class EditBiometricNomineeRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public List<EditBiometricNominee> getDetails(String partitionName, String crd_orgtab, int cropyear, String season,
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
				+ " where correctiontype='8'  \r\n" + " and cr_year=" + cropyear + " and cr_season='" + season
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

		List<EditBiometricNominee> data = new ArrayList<>();
		Query sql = entityManager.createNativeQuery(qry);
		List<Object> objects = sql.getResultList();

		if (objects != null && objects.size() > 0) {

			for (Object patta : objects) {

				Object[] row = (Object[]) patta;

				EditBiometricNominee pojos = new EditBiometricNominee();

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
	
	@Transactional 
	public int updateNominee(String tab1,String booking,String ocfname,int vcode,String ocname,String mobil,String cr_yea,String season,String khn,String cr_sno,String aadhaar,String surveyNo,String khataNo,String aadhaarNo) {
//		String surveyNo=value;				booking_partition
//		String khataNo=value;
//		String aadhaarNo=value;
		BigDecimal khata = null;
		System.out.println(tab1);
		System.out.println(booking);
		System.out.println(ocfname);
		System.out.println(vcode);
		System.out.println(ocname);
		System.out.println(mobil);
		System.out.println(cr_yea);
		System.out.println(season);
		System.out.println(khn);
		System.out.println(cr_sno);
		System.out.println(aadhaar);
		System.out.println(surveyNo);
		System.out.println(khataNo);
		System.out.println(aadhaarNo);
		int bookingid= Integer.parseInt(booking);
		BigDecimal mobile=new BigDecimal(mobil);
		BigDecimal cr_year= new BigDecimal(cr_yea);
		BigDecimal khno=new BigDecimal(khn);
		//int aadhaar=Integer.parseInt(aadhaa);
//		int code= String.valueOf(vcode);
		String qry="update "+tab1+" set occup_change='B',cr_farmeruid_old=cr_farmeruid,cr_farmeruid=?,occupname=?,occupfname=?,mobileno=? where bookingid=? and cr_vcode=? \r\n"
				+ " and cr_year=?   and cr_season=? \r\n";
		
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
		entityManager.createNativeQuery(qry).setParameter(1, aadhaar).setParameter(2, ocname)
		.setParameter(3, ocfname).setParameter(4, mobile).setParameter(5, bookingid)
		.setParameter(6, vcode).setParameter(7, cr_year).setParameter(8, season).executeUpdate();
		
//		System.out.println(qry);
		
		return 1;
	}
	
	@Transactional 
	public int updateNominee2(String tab2,String booking,String ocfname,int vcode,String ocname,String mobil,String cr_yea,String season,String khn,String cr_sno,String aadhaar,String surveyNo,String khataNo,String aadhaarNo,String crop,String cr_no,String vari,Date date) {
//		String surveyNo=value;
//		String khataNo=value;
//		String aadhaarNo=value;
		BigDecimal khata = null;
		System.out.println(tab2);
		System.out.println(booking);
		System.out.println(ocfname);
		System.out.println(vcode);
		System.out.println(ocname);
		System.out.println(mobil);
		System.out.println(cr_yea);
		System.out.println(season);
		System.out.println(khn);
		System.out.println(cr_sno);
		System.out.println(aadhaar);
		System.out.println(surveyNo);
		System.out.println(khataNo);
		System.out.println(aadhaarNo);
		int bookingid= Integer.parseInt(booking);
		BigDecimal mobile=new BigDecimal(mobil);
		BigDecimal cr_year= new BigDecimal(cr_yea);
		BigDecimal khno=new BigDecimal(khn);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		//int aadhaar=Integer.parseInt(aadhaa);
//		int code= String.valueOf(vcode);
		String qry="update "+tab2+" set cr_farmeruid_old=cr_farmeruid,cr_farmeruid=?,occupname=?,occupfname=?,mobileno=? where bookingid=? and cr_vcode=? \r\n"
				+ " and cr_year=?   and cr_season=? and cr_crop=? and cr_no=? and variety=? and cr_sow_date=? \r\n";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
		
		entityManager.createNativeQuery(qry).setParameter(1, aadhaar).setParameter(2, ocname)
		.setParameter(3, ocfname).setParameter(4, mobile).setParameter(5, bookingid)
		.setParameter(6, vcode).setParameter(7, cr_year).setParameter(8, season).setParameter(9, cr_crop)
		.setParameter(10, cr_no).setParameter(11, variety).setParameter(12, date).executeUpdate();
		
//		System.out.println(qry);
		
		return 1;
	}
	
	
	@Transactional
	public int insertNominee1(String partkey,String booking,int dcode,int vcode,String relation,String crop,String vari,
			String cr_no,Date cr_sow_date,String updatedby,String ipaddress, String year,String season,String khno,
			String cr_sno,int mcode,String name,String fname,String org_occupname) {
		
		int bookingid= Integer.parseInt(booking);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		BigDecimal cr_year= new BigDecimal(year);
		BigDecimal kh_no=new BigDecimal(khno);
		
		String qry="INSERT INTO farmer_bio_legal_details(\r\n"
				+ "part_key, bookingid, cr_dist_code, cr_vcode, relation,   \r\n"
				+ " cr_crop,variety,cr_no,cr_sow_date, updatedby, ipaddress ,cr_year, cr_season,kh_no,cr_sno,cr_mand_code,\r\n"
				+ "nominee_name,nominee_fname,org_occupname)  \r\n"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		

		
		
		entityManager.createNativeQuery(qry).setParameter(1, partkey).setParameter(2, bookingid)
		.setParameter(3, dcode).setParameter(4, vcode).setParameter(5, relation)
		.setParameter(6, cr_crop).setParameter(7, variety).setParameter(8, cr_no).setParameter(9, cr_sow_date)
		.setParameter(10, updatedby).setParameter(11, ipaddress).setParameter(12, cr_year).setParameter(13,season ).setParameter(14, kh_no)
		.setParameter(15, cr_sno).setParameter(16, mcode).setParameter(17, name)
		.setParameter(18, fname).setParameter(19, org_occupname).executeUpdate();
		
//		System.out.println(qry);
		return 0;
	}
	
	@Transactional
	public int insertNominee2(String cr_details_org_details, String tab2,String booking,String crop,String cr_no,String vari,String year, String season,Date cr_sow_date,String surveyNo,String khataNo,String aadhaarNo,int vcode) {
		int bookingid= Integer.parseInt(booking);
		int cr_crop = Integer.parseInt(crop);
		int variety = Integer.parseInt(vari);
		BigDecimal cr_year= new BigDecimal(year);
		
		String qry="INSERT INTO "+cr_details_org_details+"( \r\n"
				+ " part_key, bookingid,mobileno, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type,\r\n"
				+ " cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode,\r\n"
				+ " cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age,\r\n"
				+ " soc_category, dt_ins_reg, ins_reg_status, cr_farmeruid, owner_tenant, occupname, occupfname, email,  gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,\r\n"
				+ "  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq,\r\n"
				+ " dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type,\r\n"
				+ " cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold,\r\n"
				+ " approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,\r\n"
				+ " vaa_txn, vro_txn, vro_verify, dt_vroverify, correctiontype, correction_date, vao_verify) \r\n"
				+ " SELECT part_key, bookingid,mobileno, cr_dist_code, cr_mand_code, cr_vcode, kh_no, cr_sno, cr_year, cr_season, cr_month, cr_sow_type, cr_crop, cr_mix_unmix_ext, cr_no, cr_w_draw, cr_irr_type, cr_1st_ext, cr_2nd_ext, cr_3rd_ext, cr_yield, cr_vaa_remarks, cr_mao_remarks, wtr_tax, regno, cr_sow_date, longitude, latitude, imei, uploaded, updatedby, updateon, ipaddress, dcode, mcode, cropage, nooftrees, cropratio, cropins, ctype, variety, irrmethod, crsowtype, crratio, data_src, sno_notmatch, cropseed_scheme, oldbookingid, croptypebyirr, tarahacode, irrcategory, age, soc_category, dt_ins_reg, ins_reg_status,\r\n"
				+ "cr_farmeruid, owner_tenant, occupname, occupfname, email,  gender, avgbodyweight, harvestqty, stocking_size, tankid, harvest_date,  dt_cropins_reg, tot_extent, occupant_extent, sand_extent, erosion_extent, ip_downloaded, qty_produced, qty_available_faq, qty_discolored_nonfaq, qty_sprouted_nonfaq, dt_ipins_reg, dt_change, calamity_id, crpins_family_auth, crpins_family_uid, dt_upd_reg, refbookingid, cr_farmeruid_old, dt_uid_upd, oc_name, oc_fname, anubhavadar_name, landownership_type, cultivator_type, cr_tr_d_ext, cr_tr_i_ext, irr_updatedby, irr_updated_dt, variety_old, dt_varietycd_upd, actual_cultivator, crop_ins_approval, ins_approved_ext, actual_yield, qty_sold, approve_usr_id, ins_approved_dt, ins_cropname_st, crpins_mao_st, crpins_maormks, crpins_jda_st, crpins_jdarmks, uidname_eng, cr_ins_revalidate, reason_non_auth, reauth_ts, auth_date, mismatch, claim_status, ekycname, ekyc_gender, ekyc_add, ekyc_dob, farming_type, seed_production, digitally_signed, \r\n"
				+ "ekyc, dt_ekyc, vaaauth, vroauth, dt_vroauth, dt_vaaauth, maocheck, daocheck, jccheck, smsmobileno, ekytxn, farmer_confirm, ekyc_namematch, diff_rec,\r\n"
				+ "vaa_txn, vro_txn, vro_verify, dt_vroverify,'13',now(), vao_verify  \r\n"
				+ "FROM " + tab2 + " where  bookingid=?  and cr_crop=? and cr_vcode=? and cr_no=? and variety=?  \r\n"
				+ "  and cr_year=? and cr_season=? and cr_sow_date=? \r\n";

		

		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
				
				entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, cr_crop).setParameter(3, vcode)
		.setParameter(4, cr_no).setParameter(5, variety).setParameter(6, cr_year).setParameter(7, season)
		.setParameter(8, cr_sow_date).executeUpdate();
				
//				System.out.println(qry);
		
		return 0;
	}
	
	@Transactional
	public int insertNominee3(String cr_booking_org_details,String tab1,String booking,int vcode,String year,String season,String surveyNo,String khataNo,String aadhaarNo) {
		int bookingid= Integer.parseInt(booking);
		BigDecimal cr_year= new BigDecimal(year);
		System.out.println("-------------------------------------------------------------------");
		
		String qry=" INSERT INTO "+cr_booking_org_details+"( part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime, ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type, refbookingid, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid, ips_flag, digitally_signed, cr_farmeruid_old, correctiontype, correction_date) \r\n"
				+ " SELECT part_key, bookingid, cr_dist_code, cr_mand_code, cr_vcode, cr_year, cr_season, cr_farmeruid, owner_tenant, oc_name, oc_fname, email, mobileno, age, kh_no, cr_sno, tot_extent, cr_tr_d_ext, cr_tr_i_ext, entry_by, entry_date, longitude, latitude, imei, updatedby, updateon, ipaddress, uploaded, data_src, downloadtime, ccrcid, rid, crop_insured, occupant_extent, dcode, mcode, occupname, occupfname, regno, soc_category, gender, sjointoccupant, ctype, variety, irrmethod, downloaded, crt_user, dwld_ver, upld_ver, ageflag, mobileflag, croptype, anubhavadar_name, anubhavadar_extent, cultivator_type, oldbookingid, dt_crt, landownership_type, refbookingid, culti_ext_upd_flag, old_cr_tr_i_ext, old_cr_tr_d_ext, forwarded_booking, insertedby, dataprep_bkid,\r\n"
				+ " ips_flag, digitally_signed, cr_farmeruid_old,'8',now() FROM  " +tab1+"  where  bookingid=?   and   cr_vcode=?   and cr_year=? and cr_season=? \r\n";
		
		if (surveyNo != "" && surveyNo != null && !surveyNo.isEmpty()) {
			qry += " and cr_sno='" + surveyNo + "'  ";
		}
		if (khataNo !="" && khataNo != null && !khataNo.isEmpty()) {
			qry += " and kh_no=" + khataNo + "  ";
		}
		if (aadhaarNo != "" && aadhaarNo != null && !aadhaarNo.isEmpty()) {
			qry += " and cr_farmeruid='" + aadhaarNo + "'  ";
		}
			System.out.println(qry);
		
		entityManager.createNativeQuery(qry).setParameter(1, bookingid).setParameter(2, vcode).setParameter(3,cr_year)
		.setParameter(4, season).executeUpdate();
		return 0;
	}

}
