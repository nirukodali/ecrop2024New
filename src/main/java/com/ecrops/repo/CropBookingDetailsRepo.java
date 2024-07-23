package com.ecrops.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ecrops.entity.CropBookingDetails;

@Repository
public class CropBookingDetailsRepo {
	@PersistenceContext
	private EntityManager entitymanger;

	public List<CropBookingDetails> getCropBookingDetails(int dcode, int wbmcode, int vcode, String cropname,
			String cr_year, String wbdcode, String activeYear, String display) throws ParseException

	{
		BigDecimal numericValue = new BigDecimal(wbmcode);
		List<CropBookingDetails> list = new ArrayList<>();

		String qry = "";
		String season = cr_year.split("@")[0];
		Integer year = Integer.parseInt(cr_year.split("@")[1]);

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			// Register PostgreSQL JDBC driver (if not done already)
			Class.forName("org.postgresql.Driver");

			// Database URL, username, and password
			String url = "jdbc:postgresql://10.72.4.71:5432/ecrop";
			String user = "postgres";
			String password = "postgres";

			// Establish connection
			conn = DriverManager.getConnection(url, user, password);

			if (wbdcode.length() == 1) {
				wbdcode = (String) ("0" + wbdcode);
			}
			String tab1 = "cr_crop_det_new_v_" + season + wbdcode + year;
			if (year >= 2023) {
				tab1 = "ecrop" + year + "." + tab1;
			}
			if (year + "" != "") {
				qry = "select  farmingtype,bookingid, rbkname, a.dcode,a.mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
						+ "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
						+ "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude,varietyname ,cropnature,cr_sow_date,watersource,irrmethoddesc,"
						+ "       cropname, data_src, updatedby, vname, mname  from " + tab1 + " a ";

				if (Integer.parseInt(display) == 2) {
					qry += " join cr_insured_crops b on  a.code=b.dcode and a.cr_season=b.season and a.cr_year=b.cropyear and a.cr_crop=b.cropcode "
							+ " and (b.rainfed is null or (b.rainfed='Y' and a.cr_w_draw=1) or (b.rainfed='N' and a.cr_w_draw!=1) ) ";
				}

				qry += " where a.dcode=? and a.cr_mand_code=? and a.cr_vcode=? and cr_crop=? and cr_year=? and cr_season=? ";
				if (!(display == "" || display == null)) {
					if (Integer.parseInt(display) == 1) {
						qry += " and dt_cropins_reg is null ";
					}
					if (Integer.parseInt(display) == 2) {
						qry += " and dt_cropins_reg is null ";
					}
					if (Integer.parseInt(display) == 3) {
						qry += " and cr_ins_revalidate is not null and reason_non_auth is null ";
					}
					if (Integer.parseInt(display) == 4) {
						qry += " and cr_ins_revalidateis is not null and reason_non_auth is not null ";
					}
				}
				qry += " order by cr_vcode,kh_no,cr_sno,oc_name ";
				pst = conn.prepareStatement(qry);
				pst.setInt(1, dcode);
				pst.setBigDecimal(2, numericValue);
				pst.setInt(3, vcode);
				pst.setInt(4, Integer.parseInt(cropname));
				pst.setInt(5, year);
				pst.setString(6, season);
			} else {

				qry += "select farmingtype, bookingid,rbkname,   a.dcode,a.mcode,cr_dist_code,    cr_mand_code,    cr_vcode,    cr_year,    cr_season,   concat('XXXXXXX',substr(cr_farmeruid,9,4)) as cr_farmeruid,    owner_tenant,"
						+ "    oc_name,    oc_fname,    mobileno,    age,    email,    kh_no,    cr_sno,    tot_extent,     cultivable_land,   uncultivable_land,"
						+ "   cr_month,   cr_sow_type,   cr_crop,   cr_mix_unmix_ext,   cr_no,   cr_w_draw,   cr_irr_type,   wtr_tax,   longitude,   latitude, varietyname ,cropnature,cr_sow_date,watersource,irrmethoddesc,"
						+ "        cropname, data_src, updatedby, vname, mname from " + tab1 + " a";
				if (Integer.parseInt(display) == 2) {
					qry += " join cr_insured_crops b on  a.dcode=b.dcode and a.cr_season=b.season and a.cr_year=b.cropyear and a.cr_crop=b.cropcode "
							+ " and (b.rainfed is null or (b.rainfed='Y' and a.cr_w_draw=1) or (b.rainfed='N' and a.cr_w_draw!=1) ) ";
				}

				qry += " where a.dcode=? and a.mcode=? and cr_vcode=? and cr_year=? and cr_season=? ";

				if (!(display == "" || display == null)) {
					if (Integer.parseInt(display) == 1) {
						qry += " and dt_cropins_reg is not null ";
					}
					if (Integer.parseInt(display) == 2) {
						qry += " and dt_cropins_reg is null  ";
					}
					if (Integer.parseInt(display) == 3) {
						qry += " and cr_ins_revalidate is not null and reason_non_auth is null ";
					}
					if (Integer.parseInt(display) == 4) {
						qry += " and cr_ins_revalidate  is not null and reason_non_auth is not null ";
					}
				}
				qry += " order by cr_vcode,kh_no,cr_sno,oc_name ";
				pst = conn.prepareStatement(qry);
				pst.setInt(1, dcode);
				pst.setBigDecimal(2, numericValue);
				pst.setInt(3, vcode);
				pst.setInt(4, year);
				pst.setString(5, season);
			}
			rs = pst.executeQuery();
			// Process ResultSet
			while (rs.next()) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				CropBookingDetails cropBookingDetails = new CropBookingDetails();
				cropBookingDetails.setBookingid(rs.getInt("bookingid"));
				cropBookingDetails.setVillage(rs.getString("vname"));
				cropBookingDetails.setRbkname(rs.getString("rbkname"));
				cropBookingDetails.setOc_name(rs.getString("oc_name"));
				cropBookingDetails.setCr_farmeruid(rs.getString("cr_farmeruid"));
				cropBookingDetails.setOc_fname(rs.getString("oc_fname"));
				cropBookingDetails.setMname(rs.getString("mname"));
				cropBookingDetails.setKh_no(rs.getBigDecimal("kh_no"));
				cropBookingDetails.setCr_sno(rs.getString("cr_sno"));
				cropBookingDetails.setCropname(rs.getString("cropname"));
				cropBookingDetails.setVarietyname(rs.getString("varietyname"));
				cropBookingDetails.setCr_sow_date(java.sql.Date.valueOf(rs.getString("cr_sow_date")));
				cropBookingDetails.setCultivable_land(rs.getBigDecimal("cultivable_land"));
				cropBookingDetails.setCropnature(rs.getString("cropnature"));
				cropBookingDetails.setWatersource(rs.getString("watersource"));
				cropBookingDetails.setIrrmethoddesc(rs.getString("irrmethoddesc"));
				cropBookingDetails.setFarmingtype(rs.getString("farmingtype"));
				cropBookingDetails.setUpdatedby(rs.getString("updatedby"));
				list.add(cropBookingDetails);
			}
			// step-5:close the connection
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
}


