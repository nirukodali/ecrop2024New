package com.ecrops.dto.webland;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cr_details",schema = "ecrop2023")
public class CrDetailsEntity {

	@Id
	@Column(name = "bookingid")
	private String bookingId;

	@Column(name = "cr_crop")
	private String crCrop;

	@Column(name = "cr_no")
	private String crNo;

	@Column(name = "cr_sow_date")
	private String crSowDate;

	@Column(name = "variety")
	private String variety;

	@Column(name = "cr_farmeruid")
	private String crFarmerUid;

	@Column(name = "cr_sno")
	private String crSno;

	@Column(name = "kh_no")
	private String khNo;

	@Column(name = "occupname")
	private String occupName;

	@Column(name = "occupfname")
	private String occupFname;

	@Column(name = "oc_name")
	private String ocName;

	@Column(name = "oc_fname")
	private String ocFname;

	@Column(name = "cr_w_draw")
	private String crWDraw;

	@Column(name = "irrmethod")
	private String irrMethod;

	@Column(name = "irgdesc")
	private String irgDesc;

	@Column(name = "seed_production")
	private String seedProduction;

	@Column(name = "cr_mix_unmix_ext")
	private String crMixUnmixExt;

	@Column(name = "cropseed_scheme")
	private String cropseedScheme;

	@Column(name = "cropschdesc")
	private String cropSchDesc;
	
	@Column(name = "cr_vcode")
	private String cr_vcode;
	
	@Column(name = "cropname")
	private String cropName;

	@Column(name = "varietyname")
	private String varietyName;
	
	@Column(name = "wsrcdesc")
	private String wsrcDesc;

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

	public String getWsrcDesc() {
		return wsrcDesc;
	}

	public void setWsrcDesc(String wsrcDesc) {
		this.wsrcDesc = wsrcDesc;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getCrCrop() {
		return crCrop;
	}

	public void setCrCrop(String crCrop) {
		this.crCrop = crCrop;
	}

	public String getCrNo() {
		return crNo;
	}

	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}

	public String getCrSowDate() {
		return crSowDate;
	}

	public void setCrSowDate(String crSowDate) {
		this.crSowDate = crSowDate;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getCrFarmerUid() {
		return crFarmerUid;
	}

	public void setCrFarmerUid(String crFarmerUid) {
		this.crFarmerUid = crFarmerUid;
	}

	public String getCrSno() {
		return crSno;
	}

	public void setCrSno(String crSno) {
		this.crSno = crSno;
	}

	public String getKhNo() {
		return khNo;
	}

	public void setKhNo(String khNo) {
		this.khNo = khNo;
	}

	public String getOccupName() {
		return occupName;
	}

	public void setOccupName(String occupName) {
		this.occupName = occupName;
	}

	public String getOccupFname() {
		return occupFname;
	}

	public void setOccupFname(String occupFname) {
		this.occupFname = occupFname;
	}

	public String getOcName() {
		return ocName;
	}

	public void setOcName(String ocName) {
		this.ocName = ocName;
	}

	public String getOcFname() {
		return ocFname;
	}

	public void setOcFname(String ocFname) {
		this.ocFname = ocFname;
	}

	public String getCrWDraw() {
		return crWDraw;
	}

	public void setCrWDraw(String crWDraw) {
		this.crWDraw = crWDraw;
	}

	public String getIrrMethod() {
		return irrMethod;
	}

	public void setIrrMethod(String irrMethod) {
		this.irrMethod = irrMethod;
	}

	public String getIrgDesc() {
		return irgDesc;
	}

	public void setIrgDesc(String irgDesc) {
		this.irgDesc = irgDesc;
	}

	public String getSeedProduction() {
		return seedProduction;
	}

	public void setSeedProduction(String seedProduction) {
		this.seedProduction = seedProduction;
	}

	public String getCrMixUnmixExt() {
		return crMixUnmixExt;
	}

	public void setCrMixUnmixExt(String crMixUnmixExt) {
		this.crMixUnmixExt = crMixUnmixExt;
	}

	public String getCropseedScheme() {
		return cropseedScheme;
	}

	public void setCropseedScheme(String cropseedScheme) {
		this.cropseedScheme = cropseedScheme;
	}

	public String getCropSchDesc() {
		return cropSchDesc;
	}

	public void setCropSchDesc(String cropSchDesc) {
		this.cropSchDesc = cropSchDesc;
	}

	public CrDetailsEntity() {

	}
	

	public String getCr_vcode() {
		return cr_vcode;
	}

	public void setCr_vcode(String cr_vcode) {
		this.cr_vcode = cr_vcode;
	}

	public CrDetailsEntity(String bookingId, String crCrop, String crNo, String crSowDate, String variety,
			String crFarmerUid, String crSno, String khNo, String occupName, String occupFname, String ocName,
			String ocFname, String crWDraw, String irrMethod, String irgDesc, String seedProduction,
			String crMixUnmixExt, String cropseedScheme, String cropSchDesc, String cr_vcode, String cropName,
			String varietyName, String wsrcDesc) {
		super();
		this.bookingId = bookingId;
		this.crCrop = crCrop;
		this.crNo = crNo;
		this.crSowDate = crSowDate;
		this.variety = variety;
		this.crFarmerUid = crFarmerUid;
		this.crSno = crSno;
		this.khNo = khNo;
		this.occupName = occupName;
		this.occupFname = occupFname;
		this.ocName = ocName;
		this.ocFname = ocFname;
		this.crWDraw = crWDraw;
		this.irrMethod = irrMethod;
		this.irgDesc = irgDesc;
		this.seedProduction = seedProduction;
		this.crMixUnmixExt = crMixUnmixExt;
		this.cropseedScheme = cropseedScheme;
		this.cropSchDesc = cropSchDesc;
		this.cr_vcode = cr_vcode;
		this.cropName = cropName;
		this.varietyName = varietyName;
		this.wsrcDesc = wsrcDesc;
	}

	@Override
	public String toString() {
		return "CrDetailsEntity [bookingId=" + bookingId + ", crCrop=" + crCrop + ", crNo=" + crNo + ", crSowDate="
				+ crSowDate + ", variety=" + variety + ", crFarmerUid=" + crFarmerUid + ", crSno=" + crSno + ", khNo="
				+ khNo + ", occupName=" + occupName + ", occupFname=" + occupFname + ", ocName=" + ocName + ", ocFname="
				+ ocFname + ", crWDraw=" + crWDraw + ", irrMethod=" + irrMethod + ", irgDesc=" + irgDesc
				+ ", seedProduction=" + seedProduction + ", crMixUnmixExt=" + crMixUnmixExt + ", cropseedScheme="
				+ cropseedScheme + ", cropSchDesc=" + cropSchDesc + ", cr_vcode=" + cr_vcode + ", cropName=" + cropName
				+ ", varietyName=" + varietyName + ", wsrcDesc=" + wsrcDesc + "]";
	}

	
}
