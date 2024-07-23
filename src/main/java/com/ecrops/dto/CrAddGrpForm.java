package com.ecrops.dto;

import javax.validation.constraints.NotBlank;

public class CrAddGrpForm {
	
	@NotBlank(message = "Crop Group Name should not Be null or empty")
	private String grpname;

	public String getGrpname() {
		return grpname;
	}

	public void setGrpname(String grpname) {
		this.grpname = grpname;
	}
	
	
}
