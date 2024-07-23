package com.ecrops.entity;

import java.math.BigDecimal; 
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cr_details_")
public class Cr_details_syearFsr {
	
	@Id
	@Column(name="oremarks")
    private String oremarks;
	@Column(name="occup_name")
    private  String occupname;
	@Column(name="occup_fname")
    private String occupfname;
    private int bookingid;
    private BigDecimal kh_no;
    private String cr_sno;
    private Date cr_sow_date;
    private String  cr_no;
    private String wbdname;
    private String wbmname;
    private String wbvname;
    private String cropname;
    private String varietyname;
    private String reason;
    private String remarks;
    
   
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cr_dist_code")
    private Wbvillage_mstFsr wbvillage_mstFsr;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = " cr_crop")
    private CropnamesFsr cropnamesFsr;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "variety")
    private Cr_variety_masterFsr cr_variety_masterFsr;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = " suprejreason")
    private Authority_verify_reasonsFsr authority_verify_reasons;

	public String getOremarks() {
		return oremarks;
	}

	public void setOremarks(String oremarks) {
		this.oremarks = oremarks;
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

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
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

	public String getWbdname() {
		return wbdname;
	}

	public void setWbdname(String wbdname) {
		this.wbdname = wbdname;
	}

	public String getWbmname() {
		return wbmname;
	}

	public void setWbmname(String wbmname) {
		this.wbmname = wbmname;
	}

	public String getWbvname() {
		return wbvname;
	}

	public void setWbvname(String wbvname) {
		this.wbvname = wbvname;
	}

	public String getCropname() {
		return cropname;
	}

	public void setCropname(String cropname) {
		this.cropname = cropname;
	}

	public String getVarietyname() {
		return varietyname;
	}

	public void setVarietyname(String varietyname) {
		this.varietyname = varietyname;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Wbvillage_mstFsr getWbvillage_mstFsr() {
		return wbvillage_mstFsr;
	}

	public void setWbvillage_mstFsr(Wbvillage_mstFsr wbvillage_mstFsr) {
		this.wbvillage_mstFsr = wbvillage_mstFsr;
	}

	public CropnamesFsr getCropnamesFsr() {
		return cropnamesFsr;
	}

	public void setCropnamesFsr(CropnamesFsr cropnamesFsr) {
		this.cropnamesFsr = cropnamesFsr;
	}

	public Cr_variety_masterFsr getCr_variety_masterFsr() {
		return cr_variety_masterFsr;
	}

	public void setCr_variety_masterFsr(Cr_variety_masterFsr cr_variety_masterFsr) {
		this.cr_variety_masterFsr = cr_variety_masterFsr;
	}

	public Authority_verify_reasonsFsr getAuthority_verify_reasons() {
		return authority_verify_reasons;
	}

	public void setAuthority_verify_reasons(Authority_verify_reasonsFsr authority_verify_reasons) {
		this.authority_verify_reasons = authority_verify_reasons;
	}
    
    
    
    
}
