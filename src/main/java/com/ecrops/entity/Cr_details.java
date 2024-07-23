package com.ecrops.entity;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Cr_details {

	private int bookingid;
	private BigDecimal mixUnmixExt;
	private BigDecimal cr_dist_code;
	private BigDecimal cr_mand_code;
	private int cr_vcode;
	private BigDecimal cr_year;
	private BigDecimal wDraw;
	private Character cr_season;
	private BigDecimal kh_no;
	private String cr_sno;
	private String photo;
	private int dcode;
	private int mcode;
	private int irrMethod;
	private String dataSrc;
	private int croptypebyirr; 
	private Character tarahacode ;
	private int irrcategory ;
	private int soc_category; 
	private String cr_farmeruid ;
	private Character owner_tenant ;
	private String occupname ;
	private String occupfname ;
	private BigDecimal  tot_extent; 
	private BigDecimal occupant_extent; 
	private String   oc_name ;
	private String  oc_fname ;
	private String anubhavadar_name ;
	private Character landownership_type ;
	private Character cultivator_type ;
	private String seedprod_agency;
	private int cr_crop;
	private Character cropseed_scheme;
	private String cr_no;
	private String damaged_ext_img;
	private int damaged_img_long;
	private int damaged_img_lat;
	private int lgdvcode;
	private String footage_status;
	private int flgdvcode;
	private String flgdvname;
	private Date cr_sow_date;
	private int cr_sow_type;
	private int variety;

	public int getCroptypebyirr() {
		return croptypebyirr;
	}

	public void setCroptypebyirr(int croptypebyirr) {
		this.croptypebyirr = croptypebyirr;
	}

	public Character getTarahacode() {
		return tarahacode;
	}

	public void setTarahacode(Character tarahacode) {
		this.tarahacode = tarahacode;
	}

	public int getIrrcategory() {
		return irrcategory;
	}

	public void setIrrcategory(int irrcategory) {
		this.irrcategory = irrcategory;
	}

	public int getSoc_category() {
		return soc_category;
	}

	public void setSoc_category(int soc_category) {
		this.soc_category = soc_category;
	}

	public String getCr_farmeruid() {
		return cr_farmeruid;
	}

	public void setCr_farmeruid(String cr_farmeruid) {
		this.cr_farmeruid = cr_farmeruid;
	}

	public Character getOwner_tenant() {
		return owner_tenant;
	}

	public void setOwner_tenant(Character owner_tenant) {
		this.owner_tenant = owner_tenant;
	}

	public String getOccupname() {
		return occupname;
	}

	public void setOccupname(String occupname) {
		this.occupname = occupname;
	}

	public String getOccupfname() {
		return occupfname;
	}

	public void setOccupfname(String occupfname) {
		this.occupfname = occupfname;
	}

	public BigDecimal getTot_extent() {
		return tot_extent;
	}

	public void setTot_extent(BigDecimal tot_extent) {
		this.tot_extent = tot_extent;
	}

	public BigDecimal getOccupant_extent() {
		return occupant_extent;
	}

	public void setOccupant_extent(BigDecimal occupant_extent) {
		this.occupant_extent = occupant_extent;
	}

	public String getOc_name() {
		return oc_name;
	}

	public void setOc_name(String oc_name) {
		this.oc_name = oc_name;
	}

	public String getOc_fname() {
		return oc_fname;
	}

	public void setOc_fname(String oc_fname) {
		this.oc_fname = oc_fname;
	}

	public String getAnubhavadar_name() {
		return anubhavadar_name;
	}

	public void setAnubhavadar_name(String anubhavadar_name) {
		this.anubhavadar_name = anubhavadar_name;
	}

	public Character getLandownership_type() {
		return landownership_type;
	}

	public void setLandownership_type(Character landownership_type) {
		this.landownership_type = landownership_type;
	}

	public Character getCultivator_type() {
		return cultivator_type;
	}

	public void setCultivator_type(Character cultivator_type) {
		this.cultivator_type = cultivator_type;
	}

	

	public String getSeedprod_agency() {
		return seedprod_agency;
	}

	public void setSeedprod_agency(String seedprod_agency) {
		this.seedprod_agency = seedprod_agency;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public int getIrrMethod() {
		return irrMethod;
	}

	public void setIrrMethod(int irrMethod) {
		this.irrMethod = irrMethod;
	}

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public BigDecimal getCr_dist_code() {
		return cr_dist_code;
	}

	public void setCr_dist_code(BigDecimal cr_dist_code) {
		this.cr_dist_code = cr_dist_code;
	}

	public BigDecimal getCr_mand_code() {
		return cr_mand_code;
	}

	public void setCr_mand_code(BigDecimal cr_mand_code) {
		this.cr_mand_code = cr_mand_code;
	}

	public int getCr_vcode() {
		return cr_vcode;
	}

	public void setCr_vcode(int cr_vcode) {
		this.cr_vcode = cr_vcode;
	}

	public BigDecimal getCr_year() {
		return cr_year;
	}

	public void setCr_year(BigDecimal cr_year) {
		this.cr_year = cr_year;
	}

	public Character getCr_season() {
		return cr_season;
	}

	public void setCr_season(Character cr_season) {
		this.cr_season = cr_season;
	}

	public BigDecimal getKh_no() {
		return kh_no;
	}

	public void setKh_no(BigDecimal kh_no) {
		this.kh_no = kh_no;
	}

	public String getCr_sno() {
		return cr_sno;
	}

	public void setCr_sno(String cr_sno) {
		this.cr_sno = cr_sno;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getDcode() {
		return dcode;
	}

	public void setDcode(int dcode) {
		this.dcode = dcode;
	}

	public int getMcode() {
		return mcode;
	}

	public void setMcode(int mcode) {
		this.mcode = mcode;
	}

	public int getCr_crop() {
		return cr_crop;
	}

	public void setCr_crop(int cr_crop) {
		this.cr_crop = cr_crop;
	}

	public Character getCropseed_scheme() {
		return cropseed_scheme;
	}

	public void setCropseed_scheme(Character cropseed_scheme) {
		this.cropseed_scheme = cropseed_scheme;
	}

	public String getCr_no() {
		return cr_no;
	}

	public void setCr_no(String cr_no) {
		this.cr_no = cr_no;
	}

	public String getDamaged_ext_img() {
		return damaged_ext_img;
	}

	public void setDamaged_ext_img(String damaged_ext_img) {
		this.damaged_ext_img = damaged_ext_img;
	}

	public int getDamaged_img_long() {
		return damaged_img_long;
	}

	public void setDamaged_img_long(int damaged_img_long) {
		this.damaged_img_long = damaged_img_long;
	}

	public int getDamaged_img_lat() {
		return damaged_img_lat;
	}

	public void setDamaged_img_lat(int damaged_img_lat) {
		this.damaged_img_lat = damaged_img_lat;
	}

	public int getLgdvcode() {
		return lgdvcode;
	}

	public void setLgdvcode(int lgdvcode) {
		this.lgdvcode = lgdvcode;
	}

	public String getFootage_status() {
		return footage_status;
	}

	public void setFootage_status(String footage_status) {
		this.footage_status = footage_status;
	}

	public int getFlgdvcode() {
		return flgdvcode;
	}

	public void setFlgdvcode(int flgdvcode) {
		this.flgdvcode = flgdvcode;
	}

	public String getFlgdvname() {
		return flgdvname;
	}

	public void setFlgdvname(String flgdvname) {
		this.flgdvname = flgdvname;
	}

	public Date getCr_sow_date() {
		return cr_sow_date;
	}

	public void setCr_sow_date(Date cr_sow_date) {
		this.cr_sow_date = cr_sow_date;
	}

	public int getCr_sow_type() {
		return cr_sow_type;
	}

	public void setCr_sow_type(int cr_sow_type) {
		this.cr_sow_type = cr_sow_type;
	}

	public int getVariety() {
		return variety;
	}

	public void setVariety(int variety) {
		this.variety = variety;
	}

	public BigDecimal getMixUnmixExt() {
		return mixUnmixExt;
	}

	public void setMixUnmixExt(BigDecimal mixUnmixExt) {
		this.mixUnmixExt = mixUnmixExt;
	}

	public BigDecimal getwDraw() {
		return wDraw;
	}

	public void setwDraw(BigDecimal wDraw) {
		this.wDraw = wDraw;
	}

}
