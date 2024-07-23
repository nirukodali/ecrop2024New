package com.ecrops.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cr_crop_det_new_v_k042023")
public class ViewCropBookedDetailsEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "kh_no")
	private Integer khathaNo;
	
	@Column(name = "cr_sno")   
	private String surveyNo;
	
	@Column(name = "bookingid")
	private Integer bookingId;

	@Column(name = "cr_farmeruid")
	private String farmerid;

	@Column(name = "tot_extent")
	private Double totExtent;
	
	@Column(name = "oc_name")
	private String farmerName;   
	
	@Column(name = "oc_fname")
	private String farmerFatherName;

	@Column(name = "varietyname")
	private String varietyName;

	@Column(name = "cr_mix_unmix_ext")  
	private Double totalExtent;
	
	@Column(name = "irrmethoddesc")
	private String irrMethodDesc;
	
	@Column(name = "watersource")
	private String waterSource;

	@Column(name = "cropnature")
	private String cropNature;

	@Column(name = "cr_sow_date")
	private Date sowDt;

	@Column(name = "farmingtype")
	private String farmingType;

	@Column(name = "cr_crop")
	private Integer crcrop;
	
	@Column(name = "variety")
	private Integer variety;
	
	@Column(name = "cr_dist_code") // ########## numeric
	private Integer wbldCode;
	
	@Column(name = "cr_mand_code")  // ########## numeric
	private Integer mandCode;
	
	@Column(name = "cr_year")  // ########## numeric
	private Integer cropYear;
	
	@Column(name = "cr_season")
	private String season;
	
	@Column(name = "cr_no")
	private String cropid;
	
	@Column(name = "dname")
	private String dname;
	
	@Column(name = "mname")
	private String mname;
	
	@Column(name = "vname")
	private String vname;
	
	@Column(name = "cropname")
	private String cropName;
	
	@Column(name = "wsrcid") // ########## numeric
	private Integer waterid;
	
	@Column(name = "cr_sow_type")
	private Integer cropNatId;
	
	@Column(name = "cropseed_scheme") // ################ Character
	private Character cropseedid;
	
	@Column(name = "cr_w_draw") // ########## numeric
	private Integer cr_w_draw;
	
	public Integer getKhathaNo() {
		return khathaNo;
	}

	public void setKhathaNo(Integer khathaNo) {
		this.khathaNo = khathaNo;
	}

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getFarmerid() {
		return farmerid;
	}

	public void setFarmerid(String farmerid) {
		this.farmerid = farmerid;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerFatherName() {
		return farmerFatherName;
	}

	public void setFarmerFatherName(String farmerFatherName) {
		this.farmerFatherName = farmerFatherName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public Double getTotalExtent() {
		return totalExtent;
	}

	public void setTotalExtent(Double totalExtent) {
		this.totalExtent = totalExtent;
	}

	public String getWaterSource() {
		return waterSource;
	}

	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}

	public String getCropNature() {
		return cropNature;
	}

	public void setCropNature(String cropNature) {
		this.cropNature = cropNature;
	}

	public Date getSowDt() {
		return sowDt;
	}

	public void setSowDt(Date sowDt) {
		this.sowDt = sowDt;
	}

	public String getFarmingType() {
		return farmingType;
	}

	public void setFarmingType(String farmingType) {
		this.farmingType = farmingType;
	}

	public Integer getCrcrop() {
		return crcrop;
	}

	public void setCrcrop(Integer crcrop) {
		this.crcrop = crcrop;
	}

	public Integer getVariety() {
		return variety;
	}

	public void setVariety(Integer variety) {
		this.variety = variety;
	}

	public Integer getWbldCode() {
		return wbldCode;
	}

	public void setWbldCode(Integer wbldCode) {
		this.wbldCode = wbldCode;
	}

	public Integer getMandCode() {
		return mandCode;
	}

	public void setMandCode(Integer mandCode) {
		this.mandCode = mandCode;
	}

	public Integer getCropYear() {
		return cropYear;
	}

	public void setCropYear(Integer cropYear) {
		this.cropYear = cropYear;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getCropid() {
		return cropid;
	}

	public void setCropid(String cropid) {
		this.cropid = cropid;
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

	public Integer getWaterid() {
		return waterid;
	}

	public void setWaterid(Integer waterid) {
		this.waterid = waterid;
	}

	public Integer getCropNatId() {
		return cropNatId;
	}

	public void setCropNatId(Integer cropNatId) {
		this.cropNatId = cropNatId;
	}

	public Character getCropseedid() {
		return cropseedid;
	}

	public void setCropseedid(Character cropseedid) {
		this.cropseedid = cropseedid;
	}

	public Integer getCr_w_draw() {
		return cr_w_draw;
	}

	public void setCr_w_draw(Integer cr_w_draw) {
		this.cr_w_draw = cr_w_draw;
	}
	
	public String getIrrMethodDesc() {
		return irrMethodDesc;
	}

	public void setIrrMethodDesc(String irrMethodDesc) {
		this.irrMethodDesc = irrMethodDesc;
	}

	public Double getTotExtent() {
		return totExtent;
	}

	public void setTotExtent(Double totExtent) {
		this.totExtent = totExtent;
	}

	@Override
	public String toString() {
		return "ViewCropBookedDetailsEntity [khathaNo=" + khathaNo + ", surveyNo=" + surveyNo + ", bookingId="
				+ bookingId + ", farmerid=" + farmerid + ", totExtent=" + totExtent + ", farmerName=" + farmerName
				+ ", farmerFatherName=" + farmerFatherName + ", varietyName=" + varietyName + ", totalExtent="
				+ totalExtent + ", irrMethodDesc=" + irrMethodDesc + ", waterSource=" + waterSource + ", cropNature="
				+ cropNature + ", sowDt=" + sowDt + ", farmingType=" + farmingType + ", crcrop=" + crcrop + ", variety="
				+ variety + ", wbldCode=" + wbldCode + ", mandCode=" + mandCode + ", cropYear=" + cropYear + ", season="
				+ season + ", cropid=" + cropid + ", dname=" + dname + ", mname=" + mname + ", vname=" + vname
				+ ", cropName=" + cropName + ", waterid=" + waterid + ", cropNatId=" + cropNatId + ", cropseedid="
				+ cropseedid + ", cr_w_draw=" + cr_w_draw + "]";
	}

	public ViewCropBookedDetailsEntity(Integer khathaNo, String surveyNo, Integer bookingId, String farmerid,
			Double totExtent, String farmerName, String farmerFatherName, String varietyName, Double totalExtent,
			String irrMethodDesc, String waterSource, String cropNature, Date sowDt, String farmingType, Integer crcrop,
			Integer variety, Integer wbldCode, Integer mandCode, Integer cropYear, String season, String cropid,
			String dname, String mname, String vname, String cropName, Integer waterid, Integer cropNatId,
			Character cropseedid, Integer cr_w_draw) {
		super();
		this.khathaNo = khathaNo;
		this.surveyNo = surveyNo;
		this.bookingId = bookingId;
		this.farmerid = farmerid;
		this.totExtent = totExtent;
		this.farmerName = farmerName;
		this.farmerFatherName = farmerFatherName;
		this.varietyName = varietyName;
		this.totalExtent = totalExtent;
		this.irrMethodDesc = irrMethodDesc;
		this.waterSource = waterSource;
		this.cropNature = cropNature;
		this.sowDt = sowDt;
		this.farmingType = farmingType;
		this.crcrop = crcrop;
		this.variety = variety;
		this.wbldCode = wbldCode;
		this.mandCode = mandCode;
		this.cropYear = cropYear;
		this.season = season;
		this.cropid = cropid;
		this.dname = dname;
		this.mname = mname;
		this.vname = vname;
		this.cropName = cropName;
		this.waterid = waterid;
		this.cropNatId = cropNatId;
		this.cropseedid = cropseedid;
		this.cr_w_draw = cr_w_draw;
	}
	
	public ViewCropBookedDetailsEntity() {
        // Initialize any default values if needed
    }
}
