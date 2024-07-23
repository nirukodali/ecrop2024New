package com.ecrops.dto.crop.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeblandLandData {

	@JsonProperty("dist_code")
    private int districtCode;
    
    @JsonProperty("mand_code")
    private int mandalCode;
    
    @JsonProperty("Village_Code")
    private int villageCode;
    
    @JsonProperty("Survey_No")
    private String surveyNo;
    
    @JsonProperty("LPMNO")
    private String lpmNo;
    
    @JsonProperty("Base_Survey_No")
    private String baseSurveyNo;
    
    @JsonProperty("Total_Extent")
    private double totalExtent;
    
    @JsonProperty("pKhatano")
    private String khatano;
    
    @JsonProperty("pextent")
    private double extent;
    
    @JsonProperty("pName")
    private String name;
    
    @JsonProperty("pFname")
    private String fatherName;
    
    @JsonProperty("OccuptName")
    private String occupantName;
    
    @JsonProperty("OccuptFname")
    private String occupantFatherName;
    
    @JsonProperty("pMobileno")
    private String mobileNo;
    
    @JsonProperty("Uncultivated_Land")
    private String uncultivatedLand;
    
    @JsonProperty("Cultivatable_land")
    private String cultivatedLand;
    
    @JsonProperty("Uid")
    private String uid;
    
    @JsonProperty("Land_Nature_Code")
    private int landNatureCode;
    
    @JsonProperty("BroadCategory_LandNature")
    private String broadCategoryLandNature;
    
    @JsonProperty("BroadCategory_LandCalssification_Code")
    private int broadCategoryLandClassificationCode;
    
    @JsonProperty("BroadCategory_LandCalssification")
    private String broadCategoryLandClassification;
    
    @JsonProperty("SubCategory_LandClassification")
    private String subCategoryLandClassification;
    
    @JsonProperty("SubCategory_LandClassification_Code")
    private int subCategoryLandClassificationCode;
    
    @JsonProperty("LanduseNature")
    private String landUseNature;
    
    @JsonProperty("Source_Of_Irrigation")
    private String sourceOfIrrigation;
    
    @JsonProperty("AcquisitionRights_code")
    private int acquisitionRightsCode;
    
    @JsonProperty("AcquisitionRights")
    private String acquisitionRights;
    
    @JsonProperty("single_joint_occupant")
    private String singleJointOccupant;
    
    @JsonProperty("regno")
    private String regNo;
    
    @JsonProperty("DataSrc")
    private String dataSource;
    
    @JsonProperty("ResponseCode")
    private String responseCode;
    
    @JsonProperty("Response")
    private String response;
    
    @JsonProperty("Notification_Number")
    private String notificationNumber;
    
    @JsonProperty("Notification_Section")
    private String notificationSection;
    
    @JsonProperty("Notification_DATE")
    private String notificationDate;
    
    @JsonProperty("Prohibited")
    private String prohibited;
    
    @JsonProperty("entry_date")
    private String entryDate;
    
    @JsonProperty("Dispute_desc")
    private String disputeDescription;
    
    @JsonProperty("IsSignedorNot")
    private String isSignedOrNot;
    
    @JsonProperty("IsJoint")
    private String isJoint;
    
    @JsonProperty("Code")
    private String code;
    
    @JsonProperty("Resurvey")
    private String resurvey;

	public int getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}

	public int getMandalCode() {
		return mandalCode;
	}

	public void setMandalCode(int mandalCode) {
		this.mandalCode = mandalCode;
	}

	public int getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(int villageCode) {
		this.villageCode = villageCode;
	}

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	public String getLpmNo() {
		return lpmNo;
	}

	public void setLpmNo(String lpmNo) {
		this.lpmNo = lpmNo;
	}

	public String getBaseSurveyNo() {
		return baseSurveyNo;
	}

	public void setBaseSurveyNo(String baseSurveyNo) {
		this.baseSurveyNo = baseSurveyNo;
	}

	public double getTotalExtent() {
		return totalExtent;
	}

	public void setTotalExtent(double totalExtent) {
		this.totalExtent = totalExtent;
	}

	public String getKhatano() {
		return khatano;
	}

	public void setKhatano(String khatano) {
		this.khatano = khatano;
	}

	public double getExtent() {
		return extent;
	}

	public void setExtent(double extent) {
		this.extent = extent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getOccupantName() {
		return occupantName;
	}

	public void setOccupantName(String occupantName) {
		this.occupantName = occupantName;
	}

	public String getOccupantFatherName() {
		return occupantFatherName;
	}

	public void setOccupantFatherName(String occupantFatherName) {
		this.occupantFatherName = occupantFatherName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUncultivatedLand() {
		return uncultivatedLand;
	}

	public void setUncultivatedLand(String uncultivatedLand) {
		this.uncultivatedLand = uncultivatedLand;
	}

	public String getCultivatedLand() {
		return cultivatedLand;
	}

	public void setCultivatedLand(String cultivatedLand) {
		this.cultivatedLand = cultivatedLand;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getLandNatureCode() {
		return landNatureCode;
	}

	public void setLandNatureCode(int landNatureCode) {
		this.landNatureCode = landNatureCode;
	}

	public String getBroadCategoryLandNature() {
		return broadCategoryLandNature;
	}

	public void setBroadCategoryLandNature(String broadCategoryLandNature) {
		this.broadCategoryLandNature = broadCategoryLandNature;
	}

	public int getBroadCategoryLandClassificationCode() {
		return broadCategoryLandClassificationCode;
	}

	public void setBroadCategoryLandClassificationCode(int broadCategoryLandClassificationCode) {
		this.broadCategoryLandClassificationCode = broadCategoryLandClassificationCode;
	}

	public String getBroadCategoryLandClassification() {
		return broadCategoryLandClassification;
	}

	public void setBroadCategoryLandClassification(String broadCategoryLandClassification) {
		this.broadCategoryLandClassification = broadCategoryLandClassification;
	}

	public String getSubCategoryLandClassification() {
		return subCategoryLandClassification;
	}

	public void setSubCategoryLandClassification(String subCategoryLandClassification) {
		this.subCategoryLandClassification = subCategoryLandClassification;
	}

	public int getSubCategoryLandClassificationCode() {
		return subCategoryLandClassificationCode;
	}

	public void setSubCategoryLandClassificationCode(int subCategoryLandClassificationCode) {
		this.subCategoryLandClassificationCode = subCategoryLandClassificationCode;
	}

	public String getLandUseNature() {
		return landUseNature;
	}

	public void setLandUseNature(String landUseNature) {
		this.landUseNature = landUseNature;
	}

	public String getSourceOfIrrigation() {
		return sourceOfIrrigation;
	}

	public void setSourceOfIrrigation(String sourceOfIrrigation) {
		this.sourceOfIrrigation = sourceOfIrrigation;
	}

	public int getAcquisitionRightsCode() {
		return acquisitionRightsCode;
	}

	public void setAcquisitionRightsCode(int acquisitionRightsCode) {
		this.acquisitionRightsCode = acquisitionRightsCode;
	}

	public String getAcquisitionRights() {
		return acquisitionRights;
	}

	public void setAcquisitionRights(String acquisitionRights) {
		this.acquisitionRights = acquisitionRights;
	}

	public String getSingleJointOccupant() {
		return singleJointOccupant;
	}

	public void setSingleJointOccupant(String singleJointOccupant) {
		this.singleJointOccupant = singleJointOccupant;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
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

	public String getNotificationNumber() {
		return notificationNumber;
	}

	public void setNotificationNumber(String notificationNumber) {
		this.notificationNumber = notificationNumber;
	}

	public String getNotificationSection() {
		return notificationSection;
	}

	public void setNotificationSection(String notificationSection) {
		this.notificationSection = notificationSection;
	}

	public String getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getProhibited() {
		return prohibited;
	}

	public void setProhibited(String prohibited) {
		this.prohibited = prohibited;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getDisputeDescription() {
		return disputeDescription;
	}

	public void setDisputeDescription(String disputeDescription) {
		this.disputeDescription = disputeDescription;
	}

	public String getIsSignedOrNot() {
		return isSignedOrNot;
	}

	public void setIsSignedOrNot(String isSignedOrNot) {
		this.isSignedOrNot = isSignedOrNot;
	}

	public String getIsJoint() {
		return isJoint;
	}

	public void setIsJoint(String isJoint) {
		this.isJoint = isJoint;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResurvey() {
		return resurvey;
	}

	public void setResurvey(String resurvey) {
		this.resurvey = resurvey;
	}
	
	public  WeblandLandData() {
		
	}

	public WeblandLandData(int districtCode, int mandalCode, int villageCode, String surveyNo, String lpmNo,
			String baseSurveyNo, double totalExtent, String khatano, double extent, String name, String fatherName,
			String occupantName, String occupantFatherName, String mobileNo, String uncultivatedLand,
			String cultivatedLand, String uid, int landNatureCode, String broadCategoryLandNature,
			int broadCategoryLandClassificationCode, String broadCategoryLandClassification,
			String subCategoryLandClassification, int subCategoryLandClassificationCode, String landUseNature,
			String sourceOfIrrigation, int acquisitionRightsCode, String acquisitionRights,
			String singleJointOccupant, String regNo, String dataSource, String responseCode, String response,
			String notificationNumber, String notificationSection, String notificationDate, String prohibited,
			String entryDate, String disputeDescription, String isSignedOrNot, String isJoint, String code,
			String resurvey) {
		super();
		this.districtCode = districtCode;
		this.mandalCode = mandalCode;
		this.villageCode = villageCode;
		this.surveyNo = surveyNo;
		this.lpmNo = lpmNo;
		this.baseSurveyNo = baseSurveyNo;
		this.totalExtent = totalExtent;
		this.khatano = khatano;
		this.extent = extent;
		this.name = name;
		this.fatherName = fatherName;
		this.occupantName = occupantName;
		this.occupantFatherName = occupantFatherName;
		this.mobileNo = mobileNo;
		this.uncultivatedLand = uncultivatedLand;
		this.cultivatedLand = cultivatedLand;
		this.uid = uid;
		this.landNatureCode = landNatureCode;
		this.broadCategoryLandNature = broadCategoryLandNature;
		this.broadCategoryLandClassificationCode = broadCategoryLandClassificationCode;
		this.broadCategoryLandClassification = broadCategoryLandClassification;
		this.subCategoryLandClassification = subCategoryLandClassification;
		this.subCategoryLandClassificationCode = subCategoryLandClassificationCode;
		this.landUseNature = landUseNature;
		this.sourceOfIrrigation = sourceOfIrrigation;
		this.acquisitionRightsCode = acquisitionRightsCode;
		this.acquisitionRights = acquisitionRights;
		this.singleJointOccupant = singleJointOccupant;
		this.regNo = regNo;
		this.dataSource = dataSource;
		this.responseCode = responseCode;
		this.response = response;
		this.notificationNumber = notificationNumber;
		this.notificationSection = notificationSection;
		this.notificationDate = notificationDate;
		this.prohibited = prohibited;
		this.entryDate = entryDate;
		this.disputeDescription = disputeDescription;
		this.isSignedOrNot = isSignedOrNot;
		this.isJoint = isJoint;
		this.code = code;
		this.resurvey = resurvey;
	}

	@Override
	public String toString() {
		return "WeblandLandData [districtCode=" + districtCode + ", mandalCode=" + mandalCode + ", villageCode="
				+ villageCode + ", surveyNo=" + surveyNo + ", lpmNo=" + lpmNo + ", baseSurveyNo=" + baseSurveyNo
				+ ", totalExtent=" + totalExtent + ", khatano=" + khatano + ", extent=" + extent + ", name=" + name
				+ ", fatherName=" + fatherName + ", occupantName=" + occupantName + ", occupantFatherName="
				+ occupantFatherName + ", mobileNo=" + mobileNo + ", uncultivatedLand=" + uncultivatedLand
				+ ", cultivatedLand=" + cultivatedLand + ", uid=" + uid + ", landNatureCode=" + landNatureCode
				+ ", broadCategoryLandNature=" + broadCategoryLandNature + ", broadCategoryLandClassificationCode="
				+ broadCategoryLandClassificationCode + ", broadCategoryLandClassification="
				+ broadCategoryLandClassification + ", subCategoryLandClassification=" + subCategoryLandClassification
				+ ", subCategoryLandClassificationCode=" + subCategoryLandClassificationCode + ", landUseNature="
				+ landUseNature + ", sourceOfIrrigation=" + sourceOfIrrigation + ", acquisitionRightsCode="
				+ acquisitionRightsCode + ", acquisitionRights=" + acquisitionRights + ", singleJointOccupant="
				+ singleJointOccupant + ", regNo=" + regNo + ", dataSource=" + dataSource + ", responseCode="
				+ responseCode + ", response=" + response + ", notificationNumber=" + notificationNumber
				+ ", notificationSection=" + notificationSection + ", notificationDate=" + notificationDate
				+ ", prohibited=" + prohibited + ", entryDate=" + entryDate + ", disputeDescription="
				+ disputeDescription + ", isSignedOrNot=" + isSignedOrNot + ", isJoint=" + isJoint + ", code=" + code
				+ ", resurvey=" + resurvey + "]";
	}

	


}
