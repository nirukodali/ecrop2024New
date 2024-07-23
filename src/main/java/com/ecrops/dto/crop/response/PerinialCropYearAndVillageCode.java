package com.ecrops.dto.crop.response;

public class PerinialCropYearAndVillageCode {
	private String cropyear;
	private int villageCode;
	
	public PerinialCropYearAndVillageCode()
	{
		
	}
	public PerinialCropYearAndVillageCode(String cropyear, int villageCode) {
		super();
		this.cropyear = cropyear;
		this.villageCode = villageCode;
	}
	public String getCropyear() {
		return cropyear;
	}
	public void setCropyear(String cropyear) {
		this.cropyear = cropyear;
	}
	public int getVillageCode() {
		return villageCode;
	}
	public void setVillageCode(int villageCode) {
		this.villageCode = villageCode;
	}
	

}
