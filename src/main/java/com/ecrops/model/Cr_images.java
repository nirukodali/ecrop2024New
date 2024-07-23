package com.ecrops.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class Cr_images {

	private int bookingid;
	private BigDecimal cr_dist_code;
	private BigDecimal cr_mand_code;
	private int cr_vcode;
	private BigDecimal kh_no;
	private String cr_sno;
    private String photo ;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public BigDecimal getCr_dist_code() {
		return cr_dist_code;
	}

	public void setCr_dist_code(BigDecimal cr_dist_code) {
		this.cr_dist_code = cr_dist_code;
	}

	public BigDecimal getCr_mand_code() {
		return cr_mand_code;
	}

	public void setCr_mand_code(BigDecimal cr_mand_code) {
		this.cr_mand_code = cr_mand_code;
	}

	public int getCr_vcode() {
		return cr_vcode;
	}

	public void setCr_vcode(int cr_vcode) {
		this.cr_vcode = cr_vcode;
	}

	public BigDecimal getKh_no() {
		return kh_no;
	}

	public void setKh_no(BigDecimal kh_no) {
		this.kh_no = kh_no;
	}

	public String getCr_sno() {
		return cr_sno;
	}

	public void setCr_sno(String cr_sno) {
		this.cr_sno = cr_sno;
	}
	
   
}
