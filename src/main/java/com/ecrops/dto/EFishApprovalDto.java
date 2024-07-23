package com.ecrops.dto;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cr_details_efish_2024")
public class EFishApprovalDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recid")
	private String RecID;
	
	@Column(name = "kh_no")
	private String KhathaNo;
	
	@Column(name = "cr_vcode")
	private String VCode;
	
	@Column(name = "village_name")
	private String VName;
	
	@Column(name = "occupant_extent")
	private String Occupant_Extent;
	
	@Column(name = "mapped_extent")
	private String Mapped_Extent;

	@Column(name = "allowable_ext")
	private String Allowable_Ext;
	
	@Column(name = "occupant_name")
	private String Occupant_Name;
	
	@Column(name = "occupant_father_name")
	private String Occupant_FName;

	@Column(name = "cr_sno")
	private String SurveyNo;
	
	@Column(name = "req_ext")
	private String ReqExt;
	
	@Column(name = "dist_code")
	private String DistCode;
	
	@Column(name = "dist_name")
	private String DistName;
	
	@Column(name = "mand_code")
	private String MandCode;
	
	@Column(name = "mand_name")
	private String MandName;
	
	@Column(name = "dfo_sug_ext")
	private String DFO_SugExt;
	
	public String getDFO_SugExt() {
		return DFO_SugExt;
	}

	public void setDFO_SugExt(String dFO_SugExt) {
		DFO_SugExt = dFO_SugExt;
	}

	public String getDistCode() {
		return DistCode;
	}

	public void setDistCode(String distCode) {
		DistCode = distCode;
	}

	public String getDistName() {
		return DistName;
	}

	public void setDistName(String distName) {
		DistName = distName;
	}

	public String getMandCode() {
		return MandCode;
	}

	public void setMandCode(String mandCode) {
		MandCode = mandCode;
	}

	public String getMandName() {
		return MandName;
	}

	public void setMandName(String mandName) {
		MandName = mandName;
	}

	public String getVCode() {
		return VCode;
	}

	public void setVCode(String vCode) {
		VCode = vCode;
	}

	public String getVName() {
		return VName;
	}

	public void setVName(String vName) {
		VName = vName;
	}
	
	public String getKhathaNo() {
		return KhathaNo;
	}

	public void setKhathaNo(String khathaNo) {
		KhathaNo = khathaNo;
	}

	public String getOccupant_Extent() {
		return Occupant_Extent;
	}

	public void setOccupant_Extent(String occupant_Extent) {
		Occupant_Extent = occupant_Extent;
	}

	public String getMapped_Extent() {
		return Mapped_Extent;
	}

	public void setMapped_Extent(String mapped_Extent) {
		Mapped_Extent = mapped_Extent;
	}

	public String getAllowable_Ext() {
		return Allowable_Ext;
	}

	public void setAllowable_Ext(String allowable_Ext) {
		Allowable_Ext = allowable_Ext;
	}

	public String getOccupant_Name() {
		return Occupant_Name;
	}

	public void setOccupant_Name(String occupant_Name) {
		Occupant_Name = occupant_Name;
	}

	public String getOccupant_FName() {
		return Occupant_FName;
	}

	public void setOccupant_FName(String occupant_FName) {
		Occupant_FName = occupant_FName;
	}

	public String getSurveyNo() {
		return SurveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		SurveyNo = surveyNo;
	}

	public String getRecID() {
		return RecID;
	}

	public void setRecID(String recID) {
		RecID = recID;
	}

	public String getReqExt() {
		return ReqExt;
	}

	public void setReqExt(String reqExt) {
		ReqExt = reqExt;
	}

	@Override
	public String toString() {
		return "EFishApprovalDto [RecID=" + RecID + ", KhathaNo=" + KhathaNo + ", Occupant_Extent=" + Occupant_Extent
				+ ", Mapped_Extent=" + Mapped_Extent + ", Allowable_Ext=" + Allowable_Ext + ", Occupant_Name="
				+ Occupant_Name + ", Occupant_FName=" + Occupant_FName + ", SurveyNo=" + SurveyNo + ", ReqExt=" + ReqExt
				+ "]";
	}

	public EFishApprovalDto(String recID, String khathaNo, String occupant_Extent, String mapped_Extent,
			String allowable_Ext, String occupant_Name, String occupant_FName, String surveyNo, String reqExt) {
		super();
		RecID = recID;
		KhathaNo = khathaNo;
		Occupant_Extent = occupant_Extent;
		Mapped_Extent = mapped_Extent;
		Allowable_Ext = allowable_Ext;
		Occupant_Name = occupant_Name;
		Occupant_FName = occupant_FName;
		SurveyNo = surveyNo;
		ReqExt = reqExt;
	}

	public EFishApprovalDto() {
		
	}
	
}
