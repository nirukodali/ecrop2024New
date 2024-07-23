package com.ecrops.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SuperChkVeriftyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String mname;
	private String vname;
	private Integer bookingid;
	private BigDecimal kh_no;
	private String cr_sno;
	private String occupname;
	private String ekycfarmername;
	private String occupfname;
	private BigDecimal occupant_extent;
	private String cropname;
	private String varietyname;
	private Date cr_sow_date;
	private BigDecimal cr_mix_unmix_ext;
	private String wsrcdesc;
	private String exception_type;
	public SuperChkVeriftyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SuperChkVeriftyEntity(String mname, String vname, Integer bookingid, BigDecimal kh_no, String cr_sno,
			String occupname, String ekycfarmername, String occupfname, BigDecimal occupant_extent, String cropname,
			String varietyname, Date cr_sow_date, BigDecimal cr_mix_unmix_ext, String wsrcdesc, String exception_type) {
		super();
		this.mname = mname;
		this.vname = vname;
		this.bookingid = bookingid;
		this.kh_no = kh_no;
		this.cr_sno = cr_sno;
		this.occupname = occupname;
		this.ekycfarmername = ekycfarmername;
		this.occupfname = occupfname;
		this.occupant_extent = occupant_extent;
		this.cropname = cropname;
		this.varietyname = varietyname;
		this.cr_sow_date = cr_sow_date;
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
		this.wsrcdesc = wsrcdesc;
		this.exception_type = exception_type;
	}
	@Override
	public String toString() {
		return "SuperChkVeriftyEntity [mname=" + mname + ", vname=" + vname + ", bookingid=" + bookingid + ", kh_no="
				+ kh_no + ", cr_sno=" + cr_sno + ", occupname=" + occupname + ", ekycfarmername=" + ekycfarmername
				+ ", occupfname=" + occupfname + ", occupant_extent=" + occupant_extent + ", cropname=" + cropname
				+ ", varietyname=" + varietyname + ", cr_sow_date=" + cr_sow_date + ", cr_mix_unmix_ext="
				+ cr_mix_unmix_ext + ", wsrcdesc=" + wsrcdesc + ", exception_type=" + exception_type + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public Integer getBookingid() {
		return bookingid;
	}
	public void setBookingid(Integer bookingid) {
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
	public String getOccupname() {
		return occupname;
	}
	public void setOccupname(String occupname) {
		this.occupname = occupname;
	}
	public String getEkycfarmername() {
		return ekycfarmername;
	}
	public void setEkycfarmername(String ekycfarmername) {
		this.ekycfarmername = ekycfarmername;
	}
	public String getOccupfname() {
		return occupfname;
	}
	public void setOccupfname(String occupfname) {
		this.occupfname = occupfname;
	}
	public BigDecimal getOccupant_extent() {
		return occupant_extent;
	}
	public void setOccupant_extent(BigDecimal occupant_extent) {
		this.occupant_extent = occupant_extent;
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
	public Date getCr_sow_date() {
		return cr_sow_date;
	}
	public void setCr_sow_date(Date cr_sow_date) {
		this.cr_sow_date = cr_sow_date;
	}
	public BigDecimal getCr_mix_unmix_ext() {
		return cr_mix_unmix_ext;
	}
	public void setCr_mix_unmix_ext(BigDecimal cr_mix_unmix_ext) {
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
	}
	public String getWsrcdesc() {
		return wsrcdesc;
	}
	public void setWsrcdesc(String wsrcdesc) {
		this.wsrcdesc = wsrcdesc;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}

	
}
