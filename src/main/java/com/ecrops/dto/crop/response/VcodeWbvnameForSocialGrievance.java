package com.ecrops.dto.crop.response;

public class VcodeWbvnameForSocialGrievance {
 private int vcode;
 private String wbvname;
 
 public VcodeWbvnameForSocialGrievance() {}
 
public VcodeWbvnameForSocialGrievance(int vcode, String wbvname) {
	super();
	this.vcode = vcode;
	this.wbvname = wbvname;
}

public int getVcode() {
	return vcode;
}

public void setVcode(int vcode) {
	this.vcode = vcode;
}

public String getWbvname() {
	return wbvname;
}

public void setWbvname(String wbvname) {
	this.wbvname = wbvname;
}

@Override
public String toString() {
	return "VcodeWbvnameForSocialGrievance [vcode=" + vcode + ", wbvname=" + wbvname + "]";
}


 
 
}
