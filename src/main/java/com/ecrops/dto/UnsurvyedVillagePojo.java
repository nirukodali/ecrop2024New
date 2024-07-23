package com.ecrops.dto;

public class UnsurvyedVillagePojo {
	private String wbdname;
    private String wbmname;
    private String wbvname;
    private int unsetEst;
    private int unsetInam;
    private int unsurveyedEst;
    private int unsurveyedInam;
	public int getUnsetEst() {
		return unsetEst;
	}
	public int getUnsetInam() {
		return unsetInam;
	}
	public int getUnsurveyedEst() {
		return unsurveyedEst;
	}
	public int getUnsurveyedInam() {
		return unsurveyedInam;
	}
	public String getWbdname() {
		return wbdname;
	}
	public void setUnsetEst(int unsetEst) {
		this.unsetEst = unsetEst;
	}
	public void setUnsetInam(int unsetInam) {
		this.unsetInam = unsetInam;
	}
	public void setUnsurveyedEst(int unsurveyedEst) {
		this.unsurveyedEst = unsurveyedEst;
	}
	public void setUnsurveyedInam(int unsurveyedInam) {
		this.unsurveyedInam = unsurveyedInam;
	}
	public void setWbdname(String wbdname) {
		this.wbdname = wbdname;
	}
	public String getWbmname() {
		return wbmname;
	}
	public void setWbmname(String wbmname) {
		this.wbmname = wbmname;
	}
	public String getWbvname() {
		return wbvname;
	}
	public void setWbvname(String wbvname) {
		this.wbvname = wbvname;
	}
}
