package com.ecrops.model;

import java.util.List;

public class EditCrBookingDtlsEntity {
    private Integer bookingid;
    private String oc_name;
    private String oc_fname;
    private String occupname;
    private String occupfname;
    private String cr_sno;
    private Integer kh_no;
    private Integer cr_vcode;
    private Integer cr_crop;
    private String cr_no;
    private String cropname;
    private String cr_sow_date;
    private String varietyname;
    private String variety;
    private String variety_new;
    private String cr_farmeruid;

    private String season;

    private Integer cropyear;

    private Integer wbdcode;
    private String newAadharNo;

    private Integer correctionType;

    private String newOccupName;

    private String newOccupFName;

    private String farmTypeId;

    private String waterResId;

    private String bookedExt;

    private String cropschdesc;

    private String cropSeedScheme;

    private String crMixUnmixExt;

    private String seedProduction;

    private String irrmethod;

    private String irgdesc;

    private String crWDraw;

    private List<KeyValueEntity> varietyList;
    
    private List<KeyValueEntity> waterResourcesList;

    private List<KeyValueEntity> cropSeedList;

    private List<KeyValueEntity> cropIrrigMethodList;
    
    private Integer rec_id;
    

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
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

    public String getCr_sno() {
        return cr_sno;
    }

    public void setCr_sno(String cr_sno) {
        this.cr_sno = cr_sno;
    }

    public Integer getKh_no() {
        return kh_no;
    }

    public void setKh_no(Integer kh_no) {
        this.kh_no = kh_no;
    }

    public Integer getCr_vcode() {
        return cr_vcode;
    }

    public void setCr_vcode(Integer cr_vcode) {
        this.cr_vcode = cr_vcode;
    }

    public Integer getCr_crop() {
        return cr_crop;
    }

    public void setCr_crop(Integer cr_crop) {
        this.cr_crop = cr_crop;
    }

    public String getCr_no() {
        return cr_no;
    }

    public void setCr_no(String cr_no) {
        this.cr_no = cr_no;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getCr_sow_date() {
        return cr_sow_date;
    }

    public void setCr_sow_date(String cr_sow_date) {
        this.cr_sow_date = cr_sow_date;
    }

    public String getVarietyname() {
        return varietyname;
    }

    public void setVarietyname(String varietyname) {
        this.varietyname = varietyname;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getCr_farmeruid() {
        return cr_farmeruid;
    }

    public void setCr_farmeruid(String cr_farmeruid) {
        this.cr_farmeruid = cr_farmeruid;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getCropyear() {
        return cropyear;
    }

    public void setCropyear(Integer cropyear) {
        this.cropyear = cropyear;
    }

    public Integer getWbdcode() {
        return wbdcode;
    }

    public void setWbdcode(Integer wbdcode) {
        this.wbdcode = wbdcode;
    }

    public String getNewAadharNo() {
        return newAadharNo;
    }

    public void setNewAadharNo(String newAadharNo) {
        this.newAadharNo = newAadharNo;
    }

    public Integer getCorrectionType() {
        return correctionType;
    }

    public void setCorrectionType(Integer correctionType) {
        this.correctionType = correctionType;
    }

    public String getNewOccupName() {
        return newOccupName;
    }

    public void setNewOccupName(String newOccupName) {
        this.newOccupName = newOccupName;
    }

    public String getNewOccupFName() {
        return newOccupFName;
    }

    public void setNewOccupFName(String newOccupFName) {
        this.newOccupFName = newOccupFName;
    }

    public List<KeyValueEntity> getVarietyList() {
        return varietyList;
    }

    public void setVarietyList(List<KeyValueEntity> varietyList) {
        this.varietyList = varietyList;
    }

    public List<KeyValueEntity> getWaterResourcesList() {
        return waterResourcesList;
    }

    public void setWaterResourcesList(List<KeyValueEntity> waterResourcesList) {
        this.waterResourcesList = waterResourcesList;
    }

    public List<KeyValueEntity> getCropSeedList() {
        return cropSeedList;
    }

    public void setCropSeedList(List<KeyValueEntity> cropSeedList) {
        this.cropSeedList = cropSeedList;
    }

    public List<KeyValueEntity> getCropIrrigMethodList() {
        return cropIrrigMethodList;
    }

    public void setCropIrrigMethodList(List<KeyValueEntity> cropIrrigMethodList) {
        this.cropIrrigMethodList = cropIrrigMethodList;
    }

    public String getFarmTypeId() {
        return farmTypeId;
    }

    public void setFarmTypeId(String farmTypeId) {
        this.farmTypeId = farmTypeId;
    }

    public String getWaterResId() {
        return waterResId;
    }

    public void setWaterResId(String waterResId) {
        this.waterResId = waterResId;
    }

    public String getBookedExt() {
        return bookedExt;
    }

    public void setBookedExt(String bookedExt) {
        this.bookedExt = bookedExt;
    }

    public String getCropschdesc() {
        return cropschdesc;
    }

    public void setCropschdesc(String cropschdesc) {
        this.cropschdesc = cropschdesc;
    }

    public String getCropSeedScheme() {
        return cropSeedScheme;
    }

    public void setCropSeedScheme(String cropSeedScheme) {
        this.cropSeedScheme = cropSeedScheme;
    }

    public String getCrMixUnmixExt() {
        return crMixUnmixExt;
    }

    public void setCrMixUnmixExt(String crMixUnmixExt) {
        this.crMixUnmixExt = crMixUnmixExt;
    }

    public String getSeedProduction() {
        return seedProduction;
    }

    public void setSeedProduction(String seedProduction) {
        this.seedProduction = seedProduction;
    }

    public String getIrrmethod() {
        return irrmethod;
    }

    public void setIrrmethod(String irrmethod) {
        this.irrmethod = irrmethod;
    }

    public String getIrgdesc() {
        return irgdesc;
    }

    public void setIrgdesc(String irgdesc) {
        this.irgdesc = irgdesc;
    }

    public String getCrWDraw() {
        return crWDraw;
    }

    public void setCrWDraw(String crWDraw) {
        this.crWDraw = crWDraw;
    }

	public Integer getRec_id() {
		return rec_id;
	}

	public void setRec_id(Integer rec_id) {
		this.rec_id = rec_id;
	}

	public String getVariety_new() {
		return variety_new;
	}

	public void setVariety_new(String variety_new) {
		this.variety_new = variety_new;
	}
    
    
    
}
