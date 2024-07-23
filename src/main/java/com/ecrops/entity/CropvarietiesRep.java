package com.ecrops.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cr_variety_master")
public class CropvarietiesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int varietycode;
    private String varietyname;
    private Integer releasedays; // Change from int to Integer
    private Integer newreleasedays;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cropcode")
    private CropnamesFCVR cropnamesFCVR;

    public CropvarietiesRep() {
    }

    public CropvarietiesRep(int varietycode, String varietyname, Integer releasedays, Integer newreleasedays,
    		CropnamesFCVR cropnamesFCVR) {
		super();
		this.varietycode = varietycode;
		this.varietyname = varietyname;
		this.releasedays = releasedays;
		this.newreleasedays = newreleasedays;
		this.cropnamesFCVR = cropnamesFCVR;
	}

	// Getters and setters
	public int getVarietycode() {
		return varietycode;
	}
	public void setVarietycode(int varietycode) {
		this.varietycode = varietycode;
	}

	public String getVarietyname() {
		return varietyname;
	}
	public void setVarietyname(String varietyname) {
		this.varietyname = varietyname;
	}

	public Integer getReleasedays() {
		return releasedays;
	}
	public void setReleasedays(Integer releasedays) {
		this.releasedays = releasedays;
	}

	public Integer getNewreleasedays() {
		return newreleasedays;
	}
	public void setNewreleasedays(Integer newreleasedays) {
		this.newreleasedays = newreleasedays;
	}

	public CropnamesFCVR getCropnamesFCVR() {
		return cropnamesFCVR;
	}
	public void setCropnamesFCVR(CropnamesFCVR cropnamesFCVR) {
		this.cropnamesFCVR = cropnamesFCVR;
	}
	 
    @Override
    public String toString() {
        return "RepCropVarieties [varietycode=" + varietycode + ", varietyname=" + varietyname + ", releasedays="
                + releasedays + ", newreleasedays=" + newreleasedays + ", cropnamesFCVR=" + cropnamesFCVR + "]";
    }

}
