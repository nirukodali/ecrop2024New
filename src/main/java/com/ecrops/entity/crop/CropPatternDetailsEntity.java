package com.ecrops.entity.crop;

import java.math.BigDecimal;
import java.sql.Date;

public class CropPatternDetailsEntity {
	
	private BigDecimal kh_no ;
	private String cr_sno;
	private int bookingid;
	private String cr_farmeruid;
	private BigDecimal tot_extent;
	private String oc_name;
	private String oc_fname;	
	private String cropname;
	private String varietyname;
	private BigDecimal cr_mix_unmix_ext;
	private String cropno;
	private String watersource;
	private String cropnature;
	private Date cr_sow_date;
	private String farmingtype;
	private int cr_crop;
	private int variety;
	private String varCode;
		private BigDecimal cr_dist_code;
		private String croppingType;
		private String croppingTypeSec;
	private BigDecimal cr_mand_code;
	private BigDecimal cr_year;
	private char cr_season;
	private String cr_no;
	private String dname;
	private String mname;
	private String vname;
	private int wsrcid;
	private int cr_sow_type;
	private char cropseed_schme;
	private int cr_vcode;
	
	
	public int getCr_vcode() {
		return cr_vcode;
	}


	public void setCr_vcode(int cr_vcode) {
		this.cr_vcode = cr_vcode;
	}


	public  CropPatternDetailsEntity() {
	}


	public CropPatternDetailsEntity(BigDecimal kh_no, String cr_sno, int bookingid, String cr_farmeruid,
			BigDecimal tot_extent, String oc_name, String oc_fname, String cropname, String varietyname, String varCode,
			BigDecimal cr_mix_unmix_ext, String cropno, String watersource, String cropnature, Date cr_sow_date,
			String farmingtype, int cr_crop, int variety, BigDecimal cr_dist_code, BigDecimal cr_mand_code,
			BigDecimal cr_year, char cr_season, String cr_no, String dname, String mname, String vname, int wsrcid,
			int cr_sow_type, char cropseed_schme,String croppingType,String croppingTypeSec) {
		super();
		this.kh_no = kh_no;
		this.cr_sno = cr_sno;
		this.bookingid = bookingid;
		this.cr_farmeruid = cr_farmeruid;
		this.tot_extent = tot_extent;
		this.oc_name = oc_name;
		this.oc_fname = oc_fname;
		this.cropname = cropname;
		this.varietyname = varietyname;
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
		this.cropno = cropno;
		this.watersource = watersource;
		this.cropnature = cropnature;
		this.cr_sow_date = cr_sow_date;
		this.farmingtype = farmingtype;
		this.cr_crop = cr_crop;
		this.variety = variety;
		this.varCode = varCode;
		this.cr_dist_code = cr_dist_code;
		this.cr_mand_code = cr_mand_code;
		this.cr_year = cr_year;
		this.cr_season = cr_season;
		this.cr_no = cr_no;
		this.dname = dname;
		this.mname = mname;
		this.vname = vname;
		this.wsrcid = wsrcid;
		this.cr_sow_type = cr_sow_type;
		this.cropseed_schme = cropseed_schme;
		this.croppingType=croppingType;
		this.croppingTypeSec=croppingTypeSec;
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
	
	public String getVarCode() {
		return varCode;
	}


	public void setVarCode(String varCode) {
		this.varCode = varCode;
	}


	public int getBookingid() {
		return bookingid;
	}


	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}


	public String getCr_farmeruid() {
		return cr_farmeruid;
	}


	public void setCr_farmeruid(String cr_farmeruid) {
		this.cr_farmeruid = cr_farmeruid;
	}
	
	public String getCroppingType() {
		return croppingType;
	}


	public void setCroppingType(String croppingType) {
		this.croppingType = croppingType;
	}


	public String getCroppingTypeSec() {
		return croppingTypeSec;
	}


	public void setCroppingTypeSec(String croppingTypeSec) {
		this.croppingTypeSec = croppingTypeSec;
	}




	public BigDecimal getTot_extent() {
		return tot_extent;
	}


	public void setTot_extent(BigDecimal tot_extent) {
		this.tot_extent = tot_extent;
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


	public BigDecimal getCr_mix_unmix_ext() {
		return cr_mix_unmix_ext;
	}


	public void setCr_mix_unmix_ext(BigDecimal cr_mix_unmix_ext) {
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
	}


	public String getCropno() {
		return cropno;
	}


	public void setCropno(String cropno) {
		this.cropno = cropno;
	}


	public String getWatersource() {
		return watersource;
	}


	public void setWatersource(String watersource) {
		this.watersource = watersource;
	}


	public String getCropnature() {
		return cropnature;
	}


	public void setCropnature(String cropnature) {
		this.cropnature = cropnature;
	}


	public Date getCr_sow_date() {
		return cr_sow_date;
	}


	public void setCr_sow_date(Date cr_sow_date) {
		this.cr_sow_date = cr_sow_date;
	}


	public String getFarmingtype() {
		return farmingtype;
	}


	public void setFarmingtype(String farmingtype) {
		this.farmingtype = farmingtype;
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


	public BigDecimal getCr_year() {
		return cr_year;
	}


	public void setCr_year(BigDecimal cr_year) {
		this.cr_year = cr_year;
	}


	public char getCr_season() {
		return cr_season;
	}


	public void setCr_season(char cr_season) {
		this.cr_season = cr_season;
	}


	public String getCr_no() {
		return cr_no;
	}


	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
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


	public int getWsrcid() {
		return wsrcid;
	}


	public void setWsrcid(int wsrcid) {
		this.wsrcid = wsrcid;
	}


	public int getCr_sow_type() {
		return cr_sow_type;
	}


	public void setCr_sow_type(int cr_sow_type) {
		this.cr_sow_type = cr_sow_type;
	}


	public char getCropseed_schme() {
		return cropseed_schme;
	}


	public void setCropseed_schme(char cropseed_schme) {
		this.cropseed_schme = cropseed_schme;
	}


	

	@Override
	public String toString() {
		return "CropPatternDetailsEntity [kh_no=" + kh_no + ", cr_sno=" + cr_sno + ", bookingid=" + bookingid
				+ ", cr_farmeruid=" + cr_farmeruid + ", tot_extent=" + tot_extent + ", oc_name=" + oc_name
				+ ", oc_fname=" + oc_fname + ", cropname=" + cropname + ", varietyname=" + varietyname
				+ ", cr_mix_unmix_ext=" + cr_mix_unmix_ext + ", cropno=" + cropno + ", watersource=" + watersource
				+ ", cropnature=" + cropnature + ", cr_sow_date=" + cr_sow_date + ", farmingtype=" + farmingtype
				+ ", cr_crop=" + cr_crop + ", variety=" + variety + ", varCode=" + varCode + ", cr_dist_code=" + cr_dist_code + ", cr_mand_code="
				+ cr_mand_code + ", cr_year=" + cr_year + ", cr_season=" + cr_season + ", cr_no=" + cr_no + ", dname="
				+ dname + ", mname=" + mname + ", vname=" + vname + ", wsrcid=" + wsrcid + ", cr_sow_type="
				+ cr_sow_type + ", cropseed_schme=" + cropseed_schme + ",croppingType=" + croppingType + ",croppingTypeSec=" + croppingTypeSec + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	
	




