package com.ecrops.entity;

import java.math.BigDecimal; 
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class  Cr_crop_det_new_v_swbdistyear{
	@Id
    private BigDecimal cr_dist_code;
    private BigDecimal cr_mand_code;
    private int bookingid;
    private String dname;
    private String mname;
    private String vname;
    private int cr_vcode;
    private int dcode;
    private int mcode;
    private int cr_crop;
    private String cr_no;
    private String ekycfarmername;
    private String oc_name;
    private String oc_fname;
    private int variety;
    private BigDecimal mobileno;
    private Date cr_sow_date;
    private String varietyname;
    @Column(name="watersource")
    private String wsrcdesc; 
    private String cropname;
    private String part_key;
    private BigDecimal kh_no;
    private String cr_sno;
    private BigDecimal cr_mix_unmix_ext;
    private BigDecimal cr_w_draw;
    private String exception_type;
//    @Column(name="oc_name")
//    private String occupname; 
//    @Column(name="oc_fname")
//    private String occupfname; 
    private BigDecimal occupant_extent;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exception_catg")
    private Supercheck_exceptions_syear supercheck_exceptions_syear;
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
	public int getBookingid() {
		return bookingid;
	}
	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
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
	public int getCr_vcode() {
		return cr_vcode;
	}
	public void setCr_vcode(int cr_vcode) {
		this.cr_vcode = cr_vcode;
	}
	public int getDcode() {
		return dcode;
	}
	public void setDcode(int dcode) {
		this.dcode = dcode;
	}
	public int getMcode() {
		return mcode;
	}
	public void setMcode(int mcode) {
		this.mcode = mcode;
	}
	public int getCr_crop() {
		return cr_crop;
	}
	public void setCr_crop(int cr_crop) {
		this.cr_crop = cr_crop;
	}
	public String getCr_no() {
		return cr_no;
	}
	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}
	public String getEkycfarmername() {
		return ekycfarmername;
	}
	public void setEkycfarmername(String ekycfarmername) {
		this.ekycfarmername = ekycfarmername;
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
	public int getVariety() {
		return variety;
	}
	public void setVariety(int variety) {
		this.variety = variety;
	}
	public BigDecimal getMobileno() {
		return mobileno;
	}
	public void setMobileno(BigDecimal mobileno) {
		this.mobileno = mobileno;
	}
	public Date getCr_sow_date() {
		return cr_sow_date;
	}
	public void setCr_sow_date(Date cr_sow_date) {
		this.cr_sow_date = cr_sow_date;
	}
	public String getVarietyname() {
		return varietyname;
	}
	public void setVarietyname(String varietyname) {
		this.varietyname = varietyname;
	}
	public String getWsrcdesc() {
		return wsrcdesc;
	}
	public void setWsrcdesc(String wsrcdesc) {
		this.wsrcdesc = wsrcdesc;
	}
	public String getCropname() {
		return cropname;
	}
	public void setCropname(String cropname) {
		this.cropname = cropname;
	}
	public String getPart_key() {
		return part_key;
	}
	public void setPart_key(String part_key) {
		this.part_key = part_key;
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
	public BigDecimal getCr_mix_unmix_ext() {
		return cr_mix_unmix_ext;
	}
	public void setCr_mix_unmix_ext(BigDecimal cr_mix_unmix_ext) {
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
	}
	public BigDecimal getCr_w_draw() {
		return cr_w_draw;
	}
	public void setCr_w_draw(BigDecimal cr_w_draw) {
		this.cr_w_draw = cr_w_draw;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public BigDecimal getOccupant_extent() {
		return occupant_extent;
	}
	public void setOccupant_extent(BigDecimal occupant_extent) {
		this.occupant_extent = occupant_extent;
	}
	public Supercheck_exceptions_syear getSupercheck_exceptions_syear() {
		return supercheck_exceptions_syear;
	}
	public void setSupercheck_exceptions_syear(Supercheck_exceptions_syear supercheck_exceptions_syear) {
		this.supercheck_exceptions_syear = supercheck_exceptions_syear;
	}
    
    
}
