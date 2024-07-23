package com.ecrops.dto.crop.response;

public class InterCropNaturePattern {

	private int id;
	private String naturedesc;

	public InterCropNaturePattern() {

	}

	public InterCropNaturePattern(int id, String naturedesc) {
		super();
		this.id = id;
		this.naturedesc = naturedesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaturedesc() {
		return naturedesc;
	}

	public void setNaturedesc(String naturedesc) {
		this.naturedesc = naturedesc;
	}

	@Override
	public String toString() {
		return "InterCropNaturePattern [id=" + id + ", naturedesc=" + naturedesc + "]";
	}

}
