package com.ecrops.dto.crop.response;

public class DistrictCodeAndDistrictName {
	public int wbdcode;
	public  String dname;
	
	public DistrictCodeAndDistrictName() {
		
	}
	public int getWbdcode() {
		return wbdcode;
	}
	public void setWbdcode(int wbdcode) {
		this.wbdcode = wbdcode;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public DistrictCodeAndDistrictName(int wbdcode, String dname) {
		super();
		this.wbdcode = wbdcode;
		this.dname = dname;
	}
	@Override
	public String toString() {
		return "DistrictCodeAndDistrictName [wbdcode=" + wbdcode + ", dname=" + dname + "]";
	}
	
}
