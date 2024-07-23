package com.ecrops.dto.crop.response;

import javax.validation.constraints.NotBlank;

public class FormData {
	
	@NotBlank()
	private String cropyear;
	@NotBlank()
	private String village;	
	
	public String getCropyear() {
		return cropyear;
	}
	public void setCropyear(String cropyear) {
		this.cropyear = cropyear;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	
	

}
