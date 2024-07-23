package com.ecrops.dto.crop.response;

public class McodeWbemname {
	
	private int mcode;
	private String mandals;
	private String wbemname;
	private String cropyear;
	private int dcode;
	
	public McodeWbemname() {
		
	}
	
	public McodeWbemname(String mandals) {
		super();
		this.mandals = mandals;
	}

	public String getMandals() {
		return mandals;
	}

	public void setMandals(String mandals) {
		this.mandals = mandals;
	}

	public int getMcode() {
		return mcode;
	}

	public void setMcode(int mcode) {
		this.mcode = mcode;
	}

	public String getWbemname() {
		return wbemname;
	}

	public void setWbemname(String wbemname) {
		this.wbemname = wbemname;
	}

	public String getCropyear() {
		return cropyear;
	}

	public void setCropyear(String cropyear) {
		this.cropyear = cropyear;
	}

	public int getDcode() {
		return dcode;
	}

	public void setDcode(int dcode) {
		this.dcode = dcode;
	}

	public McodeWbemname(int mcode, String wbemname, String cropyear, int dcode) {
		super();
		this.mcode = mcode;
		this.wbemname = wbemname;
		this.cropyear = cropyear;
		this.dcode = dcode;
	}

	@Override
	public String toString() {
		return "McodeWbemname [mcode=" + mcode + ", wbemname=" + wbemname + ", cropyear=" + cropyear + ", dcode="
				+ dcode + "]";
	}
	
	

}
