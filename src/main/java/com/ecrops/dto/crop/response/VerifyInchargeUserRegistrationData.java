package com.ecrops.dto.crop.response;

public class VerifyInchargeUserRegistrationData {

	private String userid;
	private String tempuid;
	private String mobile_phone;

	public VerifyInchargeUserRegistrationData() {
		// Default constructor
	}

	public VerifyInchargeUserRegistrationData(String userid, String tempuid, String mobile_phone) {
		this.userid = userid;
		this.tempuid = tempuid;
		this.mobile_phone = mobile_phone;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTempuid() {
		return tempuid;
	}

	public void setTempuid(String tempuid) {
		this.tempuid = tempuid;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

}
