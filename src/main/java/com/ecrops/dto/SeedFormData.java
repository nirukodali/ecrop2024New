package com.ecrops.dto;

import javax.validation.constraints.NotBlank;

public class SeedFormData {
	
	@NotBlank 
	private String CropGroup; 
	
	@NotBlank(message= "Crop Name should not be null or empty") 
	private String CropName;
	
	@org.hibernate.validator.constraints.NotBlank(message= "Variety Name should not be null or empty")
	private String VarietyName ;

	public SeedFormData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SeedFormData(@NotBlank(message = "Crop Group should not be null or empty") String cropGroup,
			@NotBlank(message = "Crop Name should not be null or empty") String cropName,
			@NotBlank(message = "Variety Name should not be null or empty") String varietyName) {
		super();
		CropGroup = cropGroup;
		CropName = cropName;
		VarietyName = varietyName;
	}

	public String getCropGroup() {
		return CropGroup;
	}

	public void setCropGroup(String cropGroup) {
		CropGroup = cropGroup;
	}

	public String getCropName() {
		return CropName;
	}

	public void setCropName(String cropName) {
		CropName = cropName;
	}
	
	public String getVarietyName() {
		return VarietyName;
	}
	
	public void setVarietyName(String varietyName) {
		VarietyName = varietyName;
	}
											
	@Override
	public String toString() {
		return "SeedFormData [CropGroup=" + CropGroup + ", CropName=" + CropName + ", VarietyName=" + VarietyName + "]";
	}	
}
