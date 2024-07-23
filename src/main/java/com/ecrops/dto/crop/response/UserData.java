package com.ecrops.dto.crop.response;

public class UserData {
    private String vill;
    private String name;
    private String mobile;
    private String email;
    private String aadhaar;

    // Getters and setters
    public String getVill() {
        return vill;
    }

    public void setVill(String vill) {
        this.vill = vill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }
    
    public UserData() {
    	
    }

	public UserData(String vill, String name, String mobile, String email, String aadhaar) {
		super();
		this.vill = vill;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.aadhaar = aadhaar;
	}

	@Override
	public String toString() {
		return "UserData [vill=" + vill + ", name=" + name + ", mobile=" + mobile + ", email=" + email + ", aadhaar="
				+ aadhaar + "]";
	}
    
    
}
