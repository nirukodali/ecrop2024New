package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MaoAuthVaaVroekyc {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	private String updatedby;
	private Integer cr_crop;
	private String vname; 
	private String rbkname;
	private Long totalbookings;
	private Integer totextent;
	private Long vaaauthcount;
	private Integer vaaauthextent;
	private Long vroauthcount;
	private Integer vroauthextent;
	private Long totekycbookings;
	private Long totfarmercount;
	private Long ekycfarmercount;
	private Integer ekycbookedext;
	public MaoAuthVaaVroekyc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MaoAuthVaaVroekyc(Integer id, String updatedby, Integer cr_crop, String vname, String rbkname,
			Long totalbookings, Integer totextent, Long vaaauthcount, Integer vaaauthextent, Long vroauthcount,
			Integer vroauthextent, Long totekycbookings, Long totfarmercount, Long ekycfarmercount,
			Integer ekycbookedext) {
		super();
		this.id = id;
		this.updatedby = updatedby;
		this.cr_crop = cr_crop;
		this.vname = vname;
		this.rbkname = rbkname;
		this.totalbookings = totalbookings;
		this.totextent = totextent;
		this.vaaauthcount = vaaauthcount;
		this.vaaauthextent = vaaauthextent;
		this.vroauthcount = vroauthcount;
		this.vroauthextent = vroauthextent;
		this.totekycbookings = totekycbookings;
		this.totfarmercount = totfarmercount;
		this.ekycfarmercount = ekycfarmercount;
		this.ekycbookedext = ekycbookedext;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Integer getCr_crop() {
		return cr_crop;
	}
	public void setCr_crop(Integer cr_crop) {
		this.cr_crop = cr_crop;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getRbkname() {
		return rbkname;
	}
	public void setRbkname(String rbkname) {
		this.rbkname = rbkname;
	}
	public Long getTotalbookings() {
		return totalbookings;
	}
	public void setTotalbookings(Long totalbookings) {
		this.totalbookings = totalbookings;
	}
	public Integer getTotextent() {
		return totextent;
	}
	public void setTotextent(Integer totextent) {
		this.totextent = totextent;
	}
	public Long getVaaauthcount() {
		return vaaauthcount;
	}
	public void setVaaauthcount(Long vaaauthcount) {
		this.vaaauthcount = vaaauthcount;
	}
	public Integer getVaaauthextent() {
		return vaaauthextent;
	}
	public void setVaaauthextent(Integer vaaauthextent) {
		this.vaaauthextent = vaaauthextent;
	}
	public Long getVroauthcount() {
		return vroauthcount;
	}
	public void setVroauthcount(Long vroauthcount) {
		this.vroauthcount = vroauthcount;
	}
	public Integer getVroauthextent() {
		return vroauthextent;
	}
	public void setVroauthextent(Integer vroauthextent) {
		this.vroauthextent = vroauthextent;
	}
	public Long getTotekycbookings() {
		return totekycbookings;
	}
	public void setTotekycbookings(Long totekycbookings) {
		this.totekycbookings = totekycbookings;
	}
	public Long getTotfarmercount() {
		return totfarmercount;
	}
	public void setTotfarmercount(Long totfarmercount) {
		this.totfarmercount = totfarmercount;
	}
	public Long getEkycfarmercount() {
		return ekycfarmercount;
	}
	public void setEkycfarmercount(Long ekycfarmercount) {
		this.ekycfarmercount = ekycfarmercount;
	}
	public Integer getEkycbookedext() {
		return ekycbookedext;
	}
	public void setEkycbookedext(Integer ekycbookedext) {
		this.ekycbookedext = ekycbookedext;
	}
	@Override
	public String toString() {
		return "MaoAuthVaaVroekyc [id=" + id + ", updatedby=" + updatedby + ", cr_crop=" + cr_crop + ", vname=" + vname
				+ ", rbkname=" + rbkname + ", totalbookings=" + totalbookings + ", totextent=" + totextent
				+ ", vaaauthcount=" + vaaauthcount + ", vaaauthextent=" + vaaauthextent + ", vroauthcount="
				+ vroauthcount + ", vroauthextent=" + vroauthextent + ", totekycbookings=" + totekycbookings
				+ ", totfarmercount=" + totfarmercount + ", ekycfarmercount=" + ekycfarmercount + ", ekycbookedext="
				+ ekycbookedext + "]";
	}
	

}
