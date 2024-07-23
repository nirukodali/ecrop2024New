package com.ecrops.dto.crop.response;

public class VcodeWbvnameForCropPattern {
private int	vcode;
private String wbvname;
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

public VcodeWbvnameForCropPattern() {
	
}

public VcodeWbvnameForCropPattern(int vcode, String wbvname) {
	this.vcode = vcode;
	this.wbvname = wbvname;
}
@Override
public String toString() {
	return "VcodeWbvnameForCropPattern [vcode=" + vcode + ", wbvname=" + wbvname + "]";
}

	

}
