package com.ecrops.entity.crop;

import java.math.BigDecimal;

import java.sql.Date;

public class CrCropDetNewV {
	private String ekycfarmername;
	private String exception_type;
	private BigDecimal cr_dist_code;
	private BigDecimal cr_mand_code;
	private String oc_name;
	private String oc_fname;
	private int variety;
	private BigDecimal mobileno;
	private Date cr_sow_date;
	private String varietyname;
	private int bookingid;
	private String wsrcdesc;
	private String cropname;
	private String dname;
	private String mname;
	private String vname;
	private String part_key;
	private int cr_vcode;
	private int dcode;
	private int mcode;
	private BigDecimal kh_no;
	private String cr_sno;
	private int cr_crop;
	private BigDecimal cr_mix_unmix_ext;
	private String cr_no;
	private BigDecimal cr_w_draw;
	private BigDecimal occupant_extent;
	private String apprsts;
	private String selectedReasons;
	private String cropYear;
	private String season;
	
	public CrCropDetNewV() {

	}

	
	
	public CrCropDetNewV(String ekycfarmername, String exception_type, BigDecimal cr_dist_code, BigDecimal cr_mand_code,
			String oc_name, String oc_fname, int variety, BigDecimal mobileno, Date cr_sow_date, String varietyname,
			int bookingid, String wsrcdesc, String cropname, String dname, String mname, String vname, String part_key,
			int cr_vcode, int dcode, int mcode, BigDecimal kh_no, String cr_sno, int cr_crop,
			BigDecimal cr_mix_unmix_ext, String cr_no, BigDecimal cr_w_draw, BigDecimal occupant_extent, String apprsts,
			String selectedReasons, String cropYear, String season) {
		super();
		this.ekycfarmername = ekycfarmername;
		this.exception_type = exception_type;
		this.cr_dist_code = cr_dist_code;
		this.cr_mand_code = cr_mand_code;
		this.oc_name = oc_name;
		this.oc_fname = oc_fname;
		this.variety = variety;
		this.mobileno = mobileno;
		this.cr_sow_date = cr_sow_date;
		this.varietyname = varietyname;
		this.bookingid = bookingid;
		this.wsrcdesc = wsrcdesc;
		this.cropname = cropname;
		this.dname = dname;
		this.mname = mname;
		this.vname = vname;
		this.part_key = part_key;
		this.cr_vcode = cr_vcode;
		this.dcode = dcode;
		this.mcode = mcode;
		this.kh_no = kh_no;
		this.cr_sno = cr_sno;
		this.cr_crop = cr_crop;
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
		this.cr_no = cr_no;
		this.cr_w_draw = cr_w_draw;
		this.occupant_extent = occupant_extent;
		this.apprsts = apprsts;
		this.selectedReasons = selectedReasons;
		this.cropYear = cropYear;
		this.season = season;
	}



	public String getCropYear() {
		return cropYear;
	}


	public void setCropYear(String cropYear) {
		this.cropYear = cropYear;
	}


	public String getSeason() {
		return season;
	}


	public void setSeason(String season) {
		this.season = season;
	}


	public String getEkycfarmername() {
		return ekycfarmername;
	}

	public void setEkycfarmername(String ekycfarmername) {
		this.ekycfarmername = ekycfarmername;
	}

	public String getException_type() {
		return exception_type;
	}

	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
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

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
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

	public String getPart_key() {
		return part_key;
	}

	public void setPart_key(String part_key) {
		this.part_key = part_key;
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

	public int getCr_crop() {
		return cr_crop;
	}

	public void setCr_crop(int cr_crop) {
		this.cr_crop = cr_crop;
	}

	public BigDecimal getCr_mix_unmix_ext() {
		return cr_mix_unmix_ext;
	}

	public void setCr_mix_unmix_ext(BigDecimal cr_mix_unmix_ext) {
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
	}

	public String getCr_no() {
		return cr_no;
	}

	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}

	public BigDecimal getCr_w_draw() {
		return cr_w_draw;
	}

	public void setCr_w_draw(BigDecimal cr_w_draw) {
		this.cr_w_draw = cr_w_draw;
	}

	public BigDecimal getOccupant_extent() {
		return occupant_extent;
	}

	public void setOccupant_extent(BigDecimal occupant_extent) {
		this.occupant_extent = occupant_extent;
	}
	
	

	public String getApprsts() {
		return apprsts;
	}

	public void setApprsts(String apprsts) {
		this.apprsts = apprsts;
	}

	
	
	public String getSelectedReasons() {
		return selectedReasons;
	}

	public void setSelectedReasons(String selectedReasons) {
		this.selectedReasons = selectedReasons;
	}



	@Override
	public String toString() {
		return "CrCropDetNewV [ekycfarmername=" + ekycfarmername + ", exception_type=" + exception_type
				+ ", cr_dist_code=" + cr_dist_code + ", cr_mand_code=" + cr_mand_code + ", oc_name=" + oc_name
				+ ", oc_fname=" + oc_fname + ", variety=" + variety + ", mobileno=" + mobileno + ", cr_sow_date="
				+ cr_sow_date + ", varietyname=" + varietyname + ", bookingid=" + bookingid + ", wsrcdesc=" + wsrcdesc
				+ ", cropname=" + cropname + ", dname=" + dname + ", mname=" + mname + ", vname=" + vname
				+ ", part_key=" + part_key + ", cr_vcode=" + cr_vcode + ", dcode=" + dcode + ", mcode=" + mcode
				+ ", kh_no=" + kh_no + ", cr_sno=" + cr_sno + ", cr_crop=" + cr_crop + ", cr_mix_unmix_ext="
				+ cr_mix_unmix_ext + ", cr_no=" + cr_no + ", cr_w_draw=" + cr_w_draw + ", occupant_extent="
				+ occupant_extent + ", apprsts=" + apprsts + ", selectedReasons=" + selectedReasons + ", cropYear="
				+ cropYear + ", season=" + season + "]";
	}

	
	
}