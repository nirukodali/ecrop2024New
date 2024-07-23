package com.ecrops.dto.crop.response;

public class VarietyCodeVarietyNameCropPattern {
	
	private int varietycode;
	private String varietyname;
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
	
	public VarietyCodeVarietyNameCropPattern()
	{
		
	}
	
	@Override
	public String toString() {
		return "VarietyCodeVarietyNameCropPattern [varietycode=" + varietycode + ", varietyname=" + varietyname + "]";
	}
	public VarietyCodeVarietyNameCropPattern(int varietycode, String varietyname) {
		this.varietycode = varietycode;
		this.varietyname = varietyname;
	}
		

}
