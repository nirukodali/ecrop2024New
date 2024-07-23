package com.ecrops.entity;

import java.util.Date; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "vro_rej_details")
public class VroRejDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define other fields corresponding to columns in the table
    private int cr_dist_code;
    private int cr_mand_code;
    private int bookingid;
    private String cr_no;
    private Date cr_sow_dt;
    private String cr_sno;
    private int kh_no;

    @ManyToOne
    @JoinColumn(name = "rej_reason", referencedColumnName = "code")
    private VroRejReasons rejectionReason;

    @ManyToOne
    @JoinColumn(name = "cr_crop", referencedColumnName = "cropid")
    private CropName cropName;

    @ManyToOne
    @JoinColumn(name = "variety", referencedColumnName = "varietycode")
    private CrVarietyMaster varietyMaster;

    @ManyToOne
    @JoinColumn(name = "cr_vcode", referencedColumnName = "wbvcode")
    private WbVillageMst villageMaster;

	public void setRejectionReason(Integer integer) {
		// TODO Auto-generated method stub
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCr_dist_code() {
		return cr_dist_code;
	}

	public void setCr_dist_code(int cr_dist_code) {
		this.cr_dist_code = cr_dist_code;
	}

	public int getCr_mand_code() {
		return cr_mand_code;
	}

	public void setCr_mand_code(int cr_mand_code) {
		this.cr_mand_code = cr_mand_code;
	}

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public String getCr_no() {
		return cr_no;
	}

	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}

	public Date getCr_sow_dt() {
		return cr_sow_dt;
	}

	public void setCr_sow_dt(Date cr_sow_dt) {
		this.cr_sow_dt = cr_sow_dt;
	}

	public String getCr_sno() {
		return cr_sno;
	}

	public void setCr_sno(String cr_sno) {
		this.cr_sno = cr_sno;
	}

	public int getKh_no() {
		return kh_no;
	}

	public void setKh_no(int kh_no) {
		this.kh_no = kh_no;
	}

	public VroRejReasons getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(VroRejReasons rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public CropName getCropName() {
		return cropName;
	}

	public void setCropName(CropName cropName) {
		this.cropName = cropName;
	}

	public CrVarietyMaster getVarietyMaster() {
		return varietyMaster;
	}

	public void setVarietyMaster(CrVarietyMaster varietyMaster) {
		this.varietyMaster = varietyMaster;
	}

	public WbVillageMst getVillageMaster() {
		return villageMaster;
	}

	public void setVillageMaster(WbVillageMst villageMaster) {
		this.villageMaster = villageMaster;
	}

	
    
    // Other mappings and fields
}
