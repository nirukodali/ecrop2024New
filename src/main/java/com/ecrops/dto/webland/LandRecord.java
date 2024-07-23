package com.ecrops.dto.webland;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LandRecord {
    @JsonProperty("Dist_code")
    private int distCode;
    
    @JsonProperty("Mand_code")
    private int mandCode;
    
    @JsonProperty("Village_Code")
    private Integer villageCode;
    
    @JsonProperty("Pattadar_uid")
    private String pattadarUid;
    
    @JsonProperty("Pname")
    private String pname;
    
    @JsonProperty("Pfname")
    private String pfname;
    
    @JsonProperty("Pmobileno")
    private String pmobileno;
    
    @JsonProperty("Psurveyno")
    private String psurveyno;
    
    @JsonProperty("Pkhatano")
    private String pkhatano;
    
    @JsonProperty("OccuptName")
    private String occuptName;
    
    @JsonProperty("OccuptFname")
    private String occuptFname;
    
    @JsonProperty("Pextent")
    private String pextent;
    
    @JsonProperty("Totalextent")
    private String totalExtent;
    
    @JsonProperty("Uncultivated_Land")
    private String uncultivatedLand;
    
    @JsonProperty("Cultivatable_land")
    private String cultivatableLand;
    
    @JsonProperty("Regno")
    private String regno;
    
    @JsonProperty("ResponseCode")
    private String responseCode;
    
    @JsonProperty("Response")
    private String response;
    
    @JsonProperty("Singlejointoccupant")
    private String singleJointOccupant;
    
    @JsonProperty("DataSrc")
    private String dataSrc;
    
    @JsonProperty("IsSignedorNot")
    private String isSignedOrNot;
    
    @JsonProperty("BaseSurveyNo")
    private String baseSurveyNo;
    
    @JsonProperty("Owner_tenant")
    private String ownerTenant;
    
    @JsonProperty("Cultivator_aadhaar")
    private String cultivatorAadhaar;
    
    @JsonProperty("Beneficiaryid")
    private int beneficiaryId;
    
    @JsonProperty("Plotid")
    private int plotId;
    
    @JsonProperty("Lgddcode")
    private String lgddCode;
    
    @JsonProperty("Lgdmcode")
    private String lgdmCode;
    
    @JsonProperty("Lgd_vcode")
    private String lgdVcode;
    
    
    public LandRecord() {
    	
    }

	public int getDistCode() {
		return distCode;
	}

	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}

	public int getMandCode() {
		return mandCode;
	}

	public void setMandCode(int mandCode) {
		this.mandCode = mandCode;
	}

	public Integer getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(Integer villageCode) {
		this.villageCode = villageCode;
	}

	public String getPattadarUid() {
		return pattadarUid;
	}

	public void setPattadarUid(String pattadarUid) {
		this.pattadarUid = pattadarUid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPfname() {
		return pfname;
	}

	public void setPfname(String pfname) {
		this.pfname = pfname;
	}

	public String getPmobileno() {
		return pmobileno;
	}

	public void setPmobileno(String pmobileno) {
		this.pmobileno = pmobileno;
	}

	public String getPsurveyno() {
		return psurveyno;
	}

	public void setPsurveyno(String psurveyno) {
		this.psurveyno = psurveyno;
	}

	public String getPkhatano() {
		return pkhatano;
	}

	public void setPkhatano(String pkhatano) {
		this.pkhatano = pkhatano;
	}

	public String getOccuptName() {
		return occuptName;
	}

	public void setOccuptName(String occuptName) {
		this.occuptName = occuptName;
	}

	public String getOccuptFname() {
		return occuptFname;
	}

	public void setOccuptFname(String occuptFname) {
		this.occuptFname = occuptFname;
	}

	public String getPextent() {
		return pextent;
	}

	public void setPextent(String pextent) {
		this.pextent = pextent;
	}

	public String getTotalExtent() {
		return totalExtent;
	}

	public void setTotalExtent(String totalExtent) {
		this.totalExtent = totalExtent;
	}

	public String getUncultivatedLand() {
		return uncultivatedLand;
	}

	public void setUncultivatedLand(String uncultivatedLand) {
		this.uncultivatedLand = uncultivatedLand;
	}

	public String getCultivatableLand() {
		return cultivatableLand;
	}

	public void setCultivatableLand(String cultivatableLand) {
		this.cultivatableLand = cultivatableLand;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getSingleJointOccupant() {
		return singleJointOccupant;
	}

	public void setSingleJointOccupant(String singleJointOccupant) {
		this.singleJointOccupant = singleJointOccupant;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getIsSignedOrNot() {
		return isSignedOrNot;
	}

	public void setIsSignedOrNot(String isSignedOrNot) {
		this.isSignedOrNot = isSignedOrNot;
	}

	public String getBaseSurveyNo() {
		return baseSurveyNo;
	}

	public void setBaseSurveyNo(String baseSurveyNo) {
		this.baseSurveyNo = baseSurveyNo;
	}

	public String getOwnerTenant() {
		return ownerTenant;
	}

	public void setOwnerTenant(String ownerTenant) {
		this.ownerTenant = ownerTenant;
	}

	public String getCultivatorAadhaar() {
		return cultivatorAadhaar;
	}

	public void setCultivatorAadhaar(String cultivatorAadhaar) {
		this.cultivatorAadhaar = cultivatorAadhaar;
	}

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public int getPlotId() {
		return plotId;
	}

	public void setPlotId(int plotId) {
		this.plotId = plotId;
	}

	public String getLgddCode() {
		return lgddCode;
	}

	public void setLgddCode(String lgddCode) {
		this.lgddCode = lgddCode;
	}

	public String getLgdmCode() {
		return lgdmCode;
	}

	public void setLgdmCode(String lgdmCode) {
		this.lgdmCode = lgdmCode;
	}

	public String getLgdVcode() {
		return lgdVcode;
	}

	public void setLgdVcode(String lgdVcode) {
		this.lgdVcode = lgdVcode;
	}

	public LandRecord(int distCode, int mandCode, Integer villageCode, String pattadarUid, String pname, String pfname,
			String pmobileno, String psurveyno, String pkhatano, String occuptName, String occuptFname, String pextent,
			String totalExtent, String uncultivatedLand, String cultivatableLand, String regno, String responseCode,
			String response, String singleJointOccupant, String dataSrc, String isSignedOrNot, String baseSurveyNo,
			String ownerTenant, String cultivatorAadhaar, int beneficiaryId, int plotId, String lgddCode,
			String lgdmCode, String lgdVcode) {
		super();
		this.distCode = distCode;
		this.mandCode = mandCode;
		this.villageCode = villageCode;
		this.pattadarUid = pattadarUid;
		this.pname = pname;
		this.pfname = pfname;
		this.pmobileno = pmobileno;
		this.psurveyno = psurveyno;
		this.pkhatano = pkhatano;
		this.occuptName = occuptName;
		this.occuptFname = occuptFname;
		this.pextent = pextent;
		this.totalExtent = totalExtent;
		this.uncultivatedLand = uncultivatedLand;
		this.cultivatableLand = cultivatableLand;
		this.regno = regno;
		this.responseCode = responseCode;
		this.response = response;
		this.singleJointOccupant = singleJointOccupant;
		this.dataSrc = dataSrc;
		this.isSignedOrNot = isSignedOrNot;
		this.baseSurveyNo = baseSurveyNo;
		this.ownerTenant = ownerTenant;
		this.cultivatorAadhaar = cultivatorAadhaar;
		this.beneficiaryId = beneficiaryId;
		this.plotId = plotId;
		this.lgddCode = lgddCode;
		this.lgdmCode = lgdmCode;
		this.lgdVcode = lgdVcode;
	}

	@Override
	public String toString() {
		return "LandRecord [distCode=" + distCode + ", mandCode=" + mandCode + ", villageCode=" + villageCode
				+ ", pattadarUid=" + pattadarUid + ", pname=" + pname + ", pfname=" + pfname + ", pmobileno="
				+ pmobileno + ", psurveyno=" + psurveyno + ", pkhatano=" + pkhatano + ", occuptName=" + occuptName
				+ ", occuptFname=" + occuptFname + ", pextent=" + pextent + ", totalExtent=" + totalExtent
				+ ", uncultivatedLand=" + uncultivatedLand + ", cultivatableLand=" + cultivatableLand + ", regno="
				+ regno + ", responseCode=" + responseCode + ", response=" + response + ", singleJointOccupant="
				+ singleJointOccupant + ", dataSrc=" + dataSrc + ", isSignedOrNot=" + isSignedOrNot + ", baseSurveyNo="
				+ baseSurveyNo + ", ownerTenant=" + ownerTenant + ", cultivatorAadhaar=" + cultivatorAadhaar
				+ ", beneficiaryId=" + beneficiaryId + ", plotId=" + plotId + ", lgddCode=" + lgddCode + ", lgdmCode="
				+ lgdmCode + ", lgdVcode=" + lgdVcode + "]";
	}
    
    

}
