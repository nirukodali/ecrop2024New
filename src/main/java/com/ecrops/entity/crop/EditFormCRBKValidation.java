package com.ecrops.entity.crop;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditFormCRBKValidation {
	
	@NotBlank(message = "Crop year should not be null or empty")
	private String cropyear;
	
	@NotBlank(message = "village should not be null or empty")
	private String vcode;
	
	@NotBlank(message = "Search by should not be null or empty")
	private String searchParam;
	
	@NotNull(message = "Reject reason should not be null or empty")
	private Integer code;
	
	
	public String getCropyear() {
		return cropyear;
	}
	public void setCropyear(String cropyear) {
		this.cropyear = cropyear;
	}
	public String getvcode() {
		return vcode;
	}
	public void setvcode(String vcode) {
		this.vcode = vcode;
	}
	public String getSearchParam() {
		return searchParam;
	}
	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
