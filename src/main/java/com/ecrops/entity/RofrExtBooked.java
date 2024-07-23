package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class RofrExtBooked {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String wbedname;
	private String web_farmers;
	private String web_ext;
	private String nweb_farmers;
	private String nweb_ext;
	private String ccrc_farmers;
	private String ccrc_ext;
	private String rofr_farmers;
	private String rofr_ext;
	private String usus_farmers;
	private String usus_ext;
	
	public RofrExtBooked() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RofrExtBooked(String wbedname, String web_farmers, String web_ext, String nweb_farmers, String nweb_ext,
			String ccrc_farmers, String ccrc_ext, String rofr_farmers, String rofr_ext, String usus_farmers,
			String usus_ext) {
		super();
		this.wbedname = wbedname;
		this.web_farmers = web_farmers;
		this.web_ext = web_ext;
		this.nweb_farmers = nweb_farmers;
		this.nweb_ext = nweb_ext;
		this.ccrc_farmers = ccrc_farmers;
		this.ccrc_ext = ccrc_ext;
		this.rofr_farmers = rofr_farmers;
		this.rofr_ext = rofr_ext;
		this.usus_farmers = usus_farmers;
		this.usus_ext = usus_ext;
	}

	public String getWbedname() {
		return wbedname;
	}

	public void setWbedname(String wbedname) {
		this.wbedname = wbedname;
	}

	public String getWeb_farmers() {
		return web_farmers;
	}

	public void setWeb_farmers(String web_farmers) {
		this.web_farmers = web_farmers;
	}

	public String getWeb_ext() {
		return web_ext;
	}

	public void setWeb_ext(String web_ext) {
		this.web_ext = web_ext;
	}

	public String getNweb_farmers() {
		return nweb_farmers;
	}

	public void setNweb_farmers(String nweb_farmers) {
		this.nweb_farmers = nweb_farmers;
	}

	public String getNweb_ext() {
		return nweb_ext;
	}

	public void setNweb_ext(String nweb_ext) {
		this.nweb_ext = nweb_ext;
	}

	public String getCcrc_farmers() {
		return ccrc_farmers;
	}

	public void setCcrc_farmers(String ccrc_farmers) {
		this.ccrc_farmers = ccrc_farmers;
	}

	public String getCcrc_ext() {
		return ccrc_ext;
	}

	public void setCcrc_ext(String ccrc_ext) {
		this.ccrc_ext = ccrc_ext;
	}

	public String getRofr_farmers() {
		return rofr_farmers;
	}

	public void setRofr_farmers(String rofr_farmers) {
		this.rofr_farmers = rofr_farmers;
	}

	public String getRofr_ext() {
		return rofr_ext;
	}

	public void setRofr_ext(String rofr_ext) {
		this.rofr_ext = rofr_ext;
	}

	public String getUsus_farmers() {
		return usus_farmers;
	}

	public void setUsus_farmers(String usus_farmers) {
		this.usus_farmers = usus_farmers;
	}

	public String getUsus_ext() {
		return usus_ext;
	}

	public void setUsus_ext(String usus_ext) {
		this.usus_ext = usus_ext;
	}

	@Override
	public String toString() {
		return "RofrExtBooked [wbedname=" + wbedname + ", web_farmers=" + web_farmers + ", web_ext=" + web_ext
				+ ", nweb_farmers=" + nweb_farmers + ", nweb_ext=" + nweb_ext + ", ccrc_farmers=" + ccrc_farmers
				+ ", ccrc_ext=" + ccrc_ext + ", rofr_farmers=" + rofr_farmers + ", rofr_ext=" + rofr_ext
				+ ", usus_farmers=" + usus_farmers + ", usus_ext=" + usus_ext + "]";
	}
	

}
