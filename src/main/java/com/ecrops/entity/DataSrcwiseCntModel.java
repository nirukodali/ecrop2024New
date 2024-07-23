package com.ecrops.entity;

public class DataSrcwiseCntModel {

	
private String vcode;
private String	 vsname;
private String	 wbvname;
private String	 wbcount;
private String	 ccrccount;
private String	 efishcount;
private String	rofrcount;
private String	  pericount;

public DataSrcwiseCntModel() {
	super();
	// TODO Auto-generated constructor stub
}

public DataSrcwiseCntModel(String vcode, String vsname, String wbvname, String wbcount, String ccrccount,
		String efishcount, String rofrcount, String pericount) {
	super();
	this.vcode = vcode;
	this.vsname = vsname;
	this.wbvname = wbvname;
	this.wbcount = wbcount;
	this.ccrccount = ccrccount;
	this.efishcount = efishcount;
	this.rofrcount = rofrcount;
	this.pericount = pericount;
}

public String getVcode() {
	return vcode;
}

public void setVcode(String vcode) {
	this.vcode = vcode;
}

public String getVsname() {
	return vsname;
}

public void setVsname(String vsname) {
	this.vsname = vsname;
}

public String getWbvname() {
	return wbvname;
}

public void setWbvname(String wbvname) {
	this.wbvname = wbvname;
}

public String getWbcount() {
	return wbcount;
}

public void setWbcount(String wbcount) {
	this.wbcount = wbcount;
}

public String getCcrccount() {
	return ccrccount;
}

public void setCcrccount(String ccrccount) {
	this.ccrccount = ccrccount;
}

public String getEfishcount() {
	return efishcount;
}

public void setEfishcount(String efishcount) {
	this.efishcount = efishcount;
}

public String getRofrcount() {
	return rofrcount;
}

public void setRofrcount(String rofrcount) {
	this.rofrcount = rofrcount;
}

public String getPericount() {
	return pericount;
}

public void setPericount(String pericount) {
	this.pericount = pericount;
}

@Override
public String toString() {
	return "DataSrcwiseCntModel [vcode=" + vcode + ", vsname=" + vsname + ", wbvname=" + wbvname + ", wbcount="
			+ wbcount + ", ccrccount=" + ccrccount + ", efishcount=" + efishcount + ", rofrcount=" + rofrcount
			+ ", pericount=" + pericount + "]";
}
		
}