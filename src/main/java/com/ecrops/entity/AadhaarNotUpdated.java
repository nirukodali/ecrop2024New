package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AadhaarNotUpdated {
	@Id
	private String lgdmname;
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String vname;
	
	
	public AadhaarNotUpdated() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AadhaarNotUpdated(String lgdmname, String vname) {
		super();
		this.lgdmname = lgdmname;
		this.vname = vname;
	}


	public String getLgdmname() {
		return lgdmname;
	}


	public void setLgdmname(String lgdmname) {
		this.lgdmname = lgdmname;
	}


	public String getVname() {
		return vname;
	}


	public void setVname(String vname) {
		this.vname = vname;
	}


	@Override
	public String toString() {
		return "AadhaarNotUpdated [lgdmname=" + lgdmname + ", vname=" + vname + "]";
	}
	
	

	
}
