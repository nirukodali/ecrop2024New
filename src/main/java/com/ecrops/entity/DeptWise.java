package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class DeptWise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String dname;
	private Integer agri_cultivable_land;
	private Integer a_vaaauthextent;
	private Integer a_vroauthextent;
	private Integer a_ekycbookedext;
	
	private Integer hcultivable_land;
	private Integer h_vaaauthextent;
	private Integer h_vroauthextent;
	private Integer h_ekycbookedext;
	
	private Integer seri_cultivable_land;
	private Integer s_vaaauthextent;
	private Integer s_vroauthextent;
	private Integer s_ekycbookedext;
	
	private Integer soc_forestry_cultivable_land;
	private Integer r_vaaauthextent;
	private Integer r_vroauthextent;
	private Integer r_ekycbookedext;
	
	public DeptWise() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeptWise(String dname, Integer agri_cultivable_land, Integer a_vaaauthextent, Integer a_vroauthextent,
			Integer a_ekycbookedext, Integer hcultivable_land, Integer h_vaaauthextent, Integer h_vroauthextent,
			Integer h_ekycbookedext, Integer seri_cultivable_land, Integer s_vaaauthextent, Integer s_vroauthextent,
			Integer s_ekycbookedext, Integer soc_forestry_cultivable_land, Integer r_vaaauthextent,
			Integer r_vroauthextent, Integer r_ekycbookedext) {
		super();
		this.dname = dname;
		this.agri_cultivable_land = agri_cultivable_land;
		this.a_vaaauthextent = a_vaaauthextent;
		this.a_vroauthextent = a_vroauthextent;
		this.a_ekycbookedext = a_ekycbookedext;
		this.hcultivable_land = hcultivable_land;
		this.h_vaaauthextent = h_vaaauthextent;
		this.h_vroauthextent = h_vroauthextent;
		this.h_ekycbookedext = h_ekycbookedext;
		this.seri_cultivable_land = seri_cultivable_land;
		this.s_vaaauthextent = s_vaaauthextent;
		this.s_vroauthextent = s_vroauthextent;
		this.s_ekycbookedext = s_ekycbookedext;
		this.soc_forestry_cultivable_land = soc_forestry_cultivable_land;
		this.r_vaaauthextent = r_vaaauthextent;
		this.r_vroauthextent = r_vroauthextent;
		this.r_ekycbookedext = r_ekycbookedext;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public Integer getAgri_cultivable_land() {
		return agri_cultivable_land;
	}

	public void setAgri_cultivable_land(Integer agri_cultivable_land) {
		this.agri_cultivable_land = agri_cultivable_land;
	}

	public Integer getA_vaaauthextent() {
		return a_vaaauthextent;
	}

	public void setA_vaaauthextent(Integer a_vaaauthextent) {
		this.a_vaaauthextent = a_vaaauthextent;
	}

	public Integer getA_vroauthextent() {
		return a_vroauthextent;
	}

	public void setA_vroauthextent(Integer a_vroauthextent) {
		this.a_vroauthextent = a_vroauthextent;
	}

	public Integer getA_ekycbookedext() {
		return a_ekycbookedext;
	}

	public void setA_ekycbookedext(Integer a_ekycbookedext) {
		this.a_ekycbookedext = a_ekycbookedext;
	}

	public Integer getHcultivable_land() {
		return hcultivable_land;
	}

	public void setHcultivable_land(Integer hcultivable_land) {
		this.hcultivable_land = hcultivable_land;
	}

	public Integer getH_vaaauthextent() {
		return h_vaaauthextent;
	}

	public void setH_vaaauthextent(Integer h_vaaauthextent) {
		this.h_vaaauthextent = h_vaaauthextent;
	}

	public Integer getH_vroauthextent() {
		return h_vroauthextent;
	}

	public void setH_vroauthextent(Integer h_vroauthextent) {
		this.h_vroauthextent = h_vroauthextent;
	}

	public Integer getH_ekycbookedext() {
		return h_ekycbookedext;
	}

	public void setH_ekycbookedext(Integer h_ekycbookedext) {
		this.h_ekycbookedext = h_ekycbookedext;
	}

	public Integer getSeri_cultivable_land() {
		return seri_cultivable_land;
	}

	public void setSeri_cultivable_land(Integer seri_cultivable_land) {
		this.seri_cultivable_land = seri_cultivable_land;
	}

	public Integer getS_vaaauthextent() {
		return s_vaaauthextent;
	}

	public void setS_vaaauthextent(Integer s_vaaauthextent) {
		this.s_vaaauthextent = s_vaaauthextent;
	}

	public Integer getS_vroauthextent() {
		return s_vroauthextent;
	}

	public void setS_vroauthextent(Integer s_vroauthextent) {
		this.s_vroauthextent = s_vroauthextent;
	}

	public Integer getS_ekycbookedext() {
		return s_ekycbookedext;
	}

	public void setS_ekycbookedext(Integer s_ekycbookedext) {
		this.s_ekycbookedext = s_ekycbookedext;
	}

	public Integer getSoc_forestry_cultivable_land() {
		return soc_forestry_cultivable_land;
	}

	public void setSoc_forestry_cultivable_land(Integer soc_forestry_cultivable_land) {
		this.soc_forestry_cultivable_land = soc_forestry_cultivable_land;
	}

	public Integer getR_vaaauthextent() {
		return r_vaaauthextent;
	}

	public void setR_vaaauthextent(Integer r_vaaauthextent) {
		this.r_vaaauthextent = r_vaaauthextent;
	}

	public Integer getR_vroauthextent() {
		return r_vroauthextent;
	}

	public void setR_vroauthextent(Integer r_vroauthextent) {
		this.r_vroauthextent = r_vroauthextent;
	}

	public Integer getR_ekycbookedext() {
		return r_ekycbookedext;
	}

	public void setR_ekycbookedext(Integer r_ekycbookedext) {
		this.r_ekycbookedext = r_ekycbookedext;
	}

	@Override
	public String toString() {
		return "DeptWise [dname=" + dname + ", agri_cultivable_land=" + agri_cultivable_land + ", a_vaaauthextent="
				+ a_vaaauthextent + ", a_vroauthextent=" + a_vroauthextent + ", a_ekycbookedext=" + a_ekycbookedext
				+ ", hcultivable_land=" + hcultivable_land + ", h_vaaauthextent=" + h_vaaauthextent
				+ ", h_vroauthextent=" + h_vroauthextent + ", h_ekycbookedext=" + h_ekycbookedext
				+ ", seri_cultivable_land=" + seri_cultivable_land + ", s_vaaauthextent=" + s_vaaauthextent
				+ ", s_vroauthextent=" + s_vroauthextent + ", s_ekycbookedext=" + s_ekycbookedext
				+ ", soc_forestry_cultivable_land=" + soc_forestry_cultivable_land + ", r_vaaauthextent="
				+ r_vaaauthextent + ", r_vroauthextent=" + r_vroauthextent + ", r_ekycbookedext=" + r_ekycbookedext
				+ "]";
	}
	
}
