package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SuperCheckDistWise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String dname;
	private String dao_allotted;
	private String dao_approved;
	private String dao_rejected;
	private String dho_allotted;
	private String dho_approved;
	private String dho_rejected;
	private String rdo_allotted;
	private String rdo_approved;
	private String rdo_rejected;
	private String ada_allotted;
	private String ada_approved;
	private String ada_rejected;
	private String tah_allotted;
	private String tah_approved;
	private String tah_rejected;
	private String mao_allotted;
	private String mao_approved;
	private String mao_rejected;
	private String ho_allotted;
	private String ho_approved;
	private String ho_rejected;
	private String dc_allotted;
	private String dc_approved;
	private String dc_rejected;
	private String jc_allotted;
	private String jc_approved;
	private String jc_rejected;
	private String hod_allotted;
	private String hod_approved;
	private String hod_rejected;
	
	
	public SuperCheckDistWise() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SuperCheckDistWise(String dname, String dao_allotted, String dao_approved, String dao_rejected, String dho_allotted,
			String dho_approved, String dho_rejected, String rdo_allotted, String rdo_approved, String rdo_rejected,
			String ada_allotted, String ada_approved, String ada_rejected, String tah_allotted, String tah_approved,
			String tah_rejected, String mao_allotted, String mao_approved, String mao_rejected, String ho_allotted,
			String ho_approved, String ho_rejected, String dc_allotted, String dc_approved, String dc_rejected, String jc_allotted,
			String jc_approved, String jc_rejected, String hod_allotted, String hod_approved, String hod_rejected) {
		super();
		this.dname = dname;
		this.dao_allotted = dao_allotted;
		this.dao_approved = dao_approved;
		this.dao_rejected = dao_rejected;
		this.dho_allotted = dho_allotted;
		this.dho_approved = dho_approved;
		this.dho_rejected = dho_rejected;
		this.rdo_allotted = rdo_allotted;
		this.rdo_approved = rdo_approved;
		this.rdo_rejected = rdo_rejected;
		this.ada_allotted = ada_allotted;
		this.ada_approved = ada_approved;
		this.ada_rejected = ada_rejected;
		this.tah_allotted = tah_allotted;
		this.tah_approved = tah_approved;
		this.tah_rejected = tah_rejected;
		this.mao_allotted = mao_allotted;
		this.mao_approved = mao_approved;
		this.mao_rejected = mao_rejected;
		this.ho_allotted = ho_allotted;
		this.ho_approved = ho_approved;
		this.ho_rejected = ho_rejected;
		this.dc_allotted = dc_allotted;
		this.dc_approved = dc_approved;
		this.dc_rejected = dc_rejected;
		this.jc_allotted = jc_allotted;
		this.jc_approved = jc_approved;
		this.jc_rejected = jc_rejected;
		this.hod_allotted = hod_allotted;
		this.hod_approved = hod_approved;
		this.hod_rejected = hod_rejected;
	}


	public String getDname() {
		return dname;
	}


	public void setDname(String dname) {
		this.dname = dname;
	}


	public String getDao_allotted() {
		return dao_allotted;
	}


	public void setDao_allotted(String dao_allotted) {
		this.dao_allotted = dao_allotted;
	}


	public String getDao_approved() {
		return dao_approved;
	}


	public void setDao_approved(String dao_approved) {
		this.dao_approved = dao_approved;
	}


	public String getDao_rejected() {
		return dao_rejected;
	}


	public void setDao_rejected(String dao_rejected) {
		this.dao_rejected = dao_rejected;
	}


	public String getDho_allotted() {
		return dho_allotted;
	}


	public void setDho_allotted(String dho_allotted) {
		this.dho_allotted = dho_allotted;
	}


	public String getDho_approved() {
		return dho_approved;
	}


	public void setDho_approved(String dho_approved) {
		this.dho_approved = dho_approved;
	}


	public String getDho_rejected() {
		return dho_rejected;
	}


	public void setDho_rejected(String dho_rejected) {
		this.dho_rejected = dho_rejected;
	}


	public String getRdo_allotted() {
		return rdo_allotted;
	}


	public void setRdo_allotted(String rdo_allotted) {
		this.rdo_allotted = rdo_allotted;
	}


	public String getRdo_approved() {
		return rdo_approved;
	}


	public void setRdo_approved(String rdo_approved) {
		this.rdo_approved = rdo_approved;
	}


	public String getRdo_rejected() {
		return rdo_rejected;
	}


	public void setRdo_rejected(String rdo_rejected) {
		this.rdo_rejected = rdo_rejected;
	}


	public String getAda_allotted() {
		return ada_allotted;
	}


	public void setAda_allotted(String ada_allotted) {
		this.ada_allotted = ada_allotted;
	}


	public String getAda_approved() {
		return ada_approved;
	}


	public void setAda_approved(String ada_approved) {
		this.ada_approved = ada_approved;
	}


	public String getAda_rejected() {
		return ada_rejected;
	}


	public void setAda_rejected(String ada_rejected) {
		this.ada_rejected = ada_rejected;
	}


	public String getTah_allotted() {
		return tah_allotted;
	}


	public void setTah_allotted(String tah_allotted) {
		this.tah_allotted = tah_allotted;
	}


	public String getTah_approved() {
		return tah_approved;
	}


	public void setTah_approved(String tah_approved) {
		this.tah_approved = tah_approved;
	}


	public String getTah_rejected() {
		return tah_rejected;
	}


	public void setTah_rejected(String tah_rejected) {
		this.tah_rejected = tah_rejected;
	}


	public String getMao_allotted() {
		return mao_allotted;
	}


	public void setMao_allotted(String mao_allotted) {
		this.mao_allotted = mao_allotted;
	}


	public String getMao_approved() {
		return mao_approved;
	}


	public void setMao_approved(String mao_approved) {
		this.mao_approved = mao_approved;
	}


	public String getMao_rejected() {
		return mao_rejected;
	}


	public void setMao_rejected(String mao_rejected) {
		this.mao_rejected = mao_rejected;
	}


	public String getHo_allotted() {
		return ho_allotted;
	}


	public void setHo_allotted(String ho_allotted) {
		this.ho_allotted = ho_allotted;
	}


	public String getHo_approved() {
		return ho_approved;
	}


	public void setHo_approved(String ho_approved) {
		this.ho_approved = ho_approved;
	}


	public String getHo_rejected() {
		return ho_rejected;
	}


	public void setHo_rejected(String ho_rejected) {
		this.ho_rejected = ho_rejected;
	}


	public String getDc_allotted() {
		return dc_allotted;
	}


	public void setDc_allotted(String dc_allotted) {
		this.dc_allotted = dc_allotted;
	}


	public String getDc_approved() {
		return dc_approved;
	}


	public void setDc_approved(String dc_approved) {
		this.dc_approved = dc_approved;
	}


	public String getDc_rejected() {
		return dc_rejected;
	}


	public void setDc_rejected(String dc_rejected) {
		this.dc_rejected = dc_rejected;
	}


	public String getJc_allotted() {
		return jc_allotted;
	}


	public void setJc_allotted(String jc_allotted) {
		this.jc_allotted = jc_allotted;
	}


	public String getJc_approved() {
		return jc_approved;
	}


	public void setJc_approved(String jc_approved) {
		this.jc_approved = jc_approved;
	}


	public String getJc_rejected() {
		return jc_rejected;
	}


	public void setJc_rejected(String jc_rejected) {
		this.jc_rejected = jc_rejected;
	}


	public String getHod_allotted() {
		return hod_allotted;
	}


	public void setHod_allotted(String hod_allotted) {
		this.hod_allotted = hod_allotted;
	}


	public String getHod_approved() {
		return hod_approved;
	}


	public void setHod_approved(String hod_approved) {
		this.hod_approved = hod_approved;
	}


	public String getHod_rejected() {
		return hod_rejected;
	}


	public void setHod_rejected(String hod_rejected) {
		this.hod_rejected = hod_rejected;
	}


	@Override
	public String toString() {
		return "SuperCheckDistWise [dname=" + dname + ", dao_allotted=" + dao_allotted + ", dao_approved="
				+ dao_approved + ", dao_rejected=" + dao_rejected + ", dho_allotted=" + dho_allotted + ", dho_approved="
				+ dho_approved + ", dho_rejected=" + dho_rejected + ", rdo_allotted=" + rdo_allotted + ", rdo_approved="
				+ rdo_approved + ", rdo_rejected=" + rdo_rejected + ", ada_allotted=" + ada_allotted + ", ada_approved="
				+ ada_approved + ", ada_rejected=" + ada_rejected + ", tah_allotted=" + tah_allotted + ", tah_approved="
				+ tah_approved + ", tah_rejected=" + tah_rejected + ", mao_allotted=" + mao_allotted + ", mao_approved="
				+ mao_approved + ", mao_rejected=" + mao_rejected + ", ho_allotted=" + ho_allotted + ", ho_approved="
				+ ho_approved + ", ho_rejected=" + ho_rejected + ", dc_allotted=" + dc_allotted + ", dc_approved="
				+ dc_approved + ", dc_rejected=" + dc_rejected + ", jc_allotted=" + jc_allotted + ", jc_approved="
				+ jc_approved + ", jc_rejected=" + jc_rejected + ", hod_allotted=" + hod_allotted + ", hod_approved="
				+ hod_approved + ", hod_rejected=" + hod_rejected + "]";
	}
	
	
	
}
