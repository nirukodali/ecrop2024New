package com.ecrops.dto.crop.response;

public class CropnatureCropidCropname {
	
	private int cropid;
	private String cropname;
	public int getCropid() {
		return cropid;
	}
	public void setCropid(int cropid) {
		this.cropid = cropid;
	}
	public String getCropname() {
		return cropname;
	}
	public void setCropname(String cropname) {
		this.cropname = cropname;
	}
	
	public CropnatureCropidCropname() {
		
	}
	public CropnatureCropidCropname(int cropid, String cropname) {
		this.cropid = cropid;
		this.cropname = cropname;
	}
	@Override
	public String toString() {
		return "CropnatureCropidCropname [cropid=" + cropid + ", cropname=" + cropname + "]";
	}
	
	

}
