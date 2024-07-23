package com.ecrops.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class EditUidDto {
	
	private int bookingid;
	private String occupname;
	private String occupfname;
	private String oc_name;
	private String oc_fname;
	private String cr_sno;
	private BigDecimal kh_no;
//	private int cr_vcode;
	private int cr_crop;
	private String cr_no;
	private int variety;
	private String farmeruid;
	private BigDecimal aadhaar;
	private Date cr_sow_date;
	private String selectedValue;
	private String value;
	private String vcode;
	
	public int getBookingid() {
		return bookingid;
	}
	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}
	public String getOccupname() {
		return occupname;
	}
	public void setOccupname(String occupname) {
		this.occupname = occupname;
	}
	public String getOccupfname() {
		return occupfname;
	}
	public void setOccupfname(String occupfname) {
		this.occupfname = occupfname;
	}
	public String getOc_name() {
		return oc_name;
	}
	public void setOc_name(String oc_name) {
		this.oc_name = oc_name;
	}
	public String getOc_fname() {
		return oc_fname;
	}
	public void setOc_fname(String oc_fname) {
		this.oc_fname = oc_fname;
	}
	public String getCr_sno() {
		return cr_sno;
	}
	public void setCr_sno(String cr_sno) {
		this.cr_sno = cr_sno;
	}
	public BigDecimal getKh_no() {
		return kh_no;
	}
	public void setKh_no(BigDecimal kh_no) {
		this.kh_no = kh_no;
	}
	public int getCr_crop() {
		return cr_crop;
	}
	public void setCr_crop(int cr_crop) {
		this.cr_crop = cr_crop;
	}
	public int getVariety() {
		return variety;
	}
	public void setVariety(int variety) {
		this.variety = variety;
	}
	public String getFarmeruid() {
		return farmeruid;
	}
	public void setFarmeruid(String cr_farmeruid) {
		this.farmeruid = cr_farmeruid;
	}
	public BigDecimal getAadhaar() {
		return aadhaar;
	}
	public void setAadhaar(BigDecimal aadhaar) {
		this.aadhaar = aadhaar;
	}
	public Date getCr_sow_date() {
		return cr_sow_date;
	}
	public void setCr_sow_date(Date cr_sow_date) {
		this.cr_sow_date = cr_sow_date;
	}
	public String getCr_no() {
		return cr_no;
	}
	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	
}
