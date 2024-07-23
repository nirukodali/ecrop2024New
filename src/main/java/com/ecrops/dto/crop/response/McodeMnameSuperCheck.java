package com.ecrops.dto.crop.response;

public class McodeMnameSuperCheck {

    private int mcode;
    private String mname;
    
    public McodeMnameSuperCheck() {
    	
    }
    
	public McodeMnameSuperCheck(int mcode, String mname) {
		this.mcode = mcode;
		this.mname = mname;
	}
	public int getMcode() {
		return mcode;
	}
	public void setMcode(int mcode) {
		this.mcode = mcode;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
    
}