package com.ecrops.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SuperCheckRevenueEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String mname;
	
	private BigInteger hod_alloted;
	private BigInteger hod_approved;
	private BigInteger hod_rejected;

	private BigInteger dao_alloted;
	private BigInteger dao_approved;
	private BigInteger dao_rejected;

	private BigInteger ada_alloted;
	private BigInteger ada_approved;
	private BigInteger ada_rejected;
	
	private BigInteger mao_alloted;
	private BigInteger mao_approved;
	private BigInteger mao_rejected;
	
	private BigInteger dho_alloted;
	private BigInteger dho_approved;
	private BigInteger dho_rejected;
	
	private BigInteger ho_alloted;
	private BigInteger ho_approved;
	private BigInteger ho_rejected;
	
	private BigInteger rdo_alloted;
	private BigInteger rdo_approved;
	private BigInteger rdo_rejected;
	
	private BigInteger tah_alloted;
	private BigInteger tah_approved;
	private BigInteger tah_rejected;
	
	private BigInteger dc_alloted;
	private BigInteger dc_approved;
	private BigInteger dc_rejected;
	
	private BigInteger jc_alloted;
	private BigInteger jc_approved;
	private BigInteger jc_rejected;
	@Override
	public String toString() {
		return "SuperCheckRevenueEntity [mname=" + mname + ", hod_alloted=" + hod_alloted + ", hod_approved="
				+ hod_approved + ", hod_rejected=" + hod_rejected + ", dao_alloted=" + dao_alloted + ", dao_approved="
				+ dao_approved + ", dao_rejected=" + dao_rejected + ", ada_alloted=" + ada_alloted + ", ada_approved="
				+ ada_approved + ", ada_rejected=" + ada_rejected + ", mao_alloted=" + mao_alloted + ", mao_approved="
				+ mao_approved + ", mao_rejected=" + mao_rejected + ", dho_alloted=" + dho_alloted + ", dho_approved="
				+ dho_approved + ", dho_rejected=" + dho_rejected + ", ho_alloted=" + ho_alloted + ", ho_approved="
				+ ho_approved + ", ho_rejected=" + ho_rejected + ", rdo_alloted=" + rdo_alloted + ", rdo_approved="
				+ rdo_approved + ", rdo_rejected=" + rdo_rejected + ", tah_alloted=" + tah_alloted + ", tah_approved="
				+ tah_approved + ", tah_rejected=" + tah_rejected + ", dc_alloted=" + dc_alloted + ", dc_approved="
				+ dc_approved + ", dc_rejected=" + dc_rejected + ", jc_alloted=" + jc_alloted + ", jc_approved="
				+ jc_approved + ", jc_rejected=" + jc_rejected + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public SuperCheckRevenueEntity(String mname, BigInteger hod_alloted, BigInteger hod_approved,
			BigInteger hod_rejected, BigInteger dao_alloted, BigInteger dao_approved, BigInteger dao_rejected,
			BigInteger ada_alloted, BigInteger ada_approved, BigInteger ada_rejected, BigInteger mao_alloted,
			BigInteger mao_approved, BigInteger mao_rejected, BigInteger dho_alloted, BigInteger dho_approved,
			BigInteger dho_rejected, BigInteger ho_alloted, BigInteger ho_approved, BigInteger ho_rejected,
			BigInteger rdo_alloted, BigInteger rdo_approved, BigInteger rdo_rejected, BigInteger tah_alloted,
			BigInteger tah_approved, BigInteger tah_rejected, BigInteger dc_alloted, BigInteger dc_approved,
			BigInteger dc_rejected, BigInteger jc_alloted, BigInteger jc_approved, BigInteger jc_rejected) {
		super();
		this.mname = mname;
		this.hod_alloted = hod_alloted;
		this.hod_approved = hod_approved;
		this.hod_rejected = hod_rejected;
		this.dao_alloted = dao_alloted;
		this.dao_approved = dao_approved;
		this.dao_rejected = dao_rejected;
		this.ada_alloted = ada_alloted;
		this.ada_approved = ada_approved;
		this.ada_rejected = ada_rejected;
		this.mao_alloted = mao_alloted;
		this.mao_approved = mao_approved;
		this.mao_rejected = mao_rejected;
		this.dho_alloted = dho_alloted;
		this.dho_approved = dho_approved;
		this.dho_rejected = dho_rejected;
		this.ho_alloted = ho_alloted;
		this.ho_approved = ho_approved;
		this.ho_rejected = ho_rejected;
		this.rdo_alloted = rdo_alloted;
		this.rdo_approved = rdo_approved;
		this.rdo_rejected = rdo_rejected;
		this.tah_alloted = tah_alloted;
		this.tah_approved = tah_approved;
		this.tah_rejected = tah_rejected;
		this.dc_alloted = dc_alloted;
		this.dc_approved = dc_approved;
		this.dc_rejected = dc_rejected;
		this.jc_alloted = jc_alloted;
		this.jc_approved = jc_approved;
		this.jc_rejected = jc_rejected;
	}
	public SuperCheckRevenueEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public BigInteger getHod_alloted() {
		return hod_alloted;
	}
	public void setHod_alloted(BigInteger hod_alloted) {
		this.hod_alloted = hod_alloted;
	}
	public BigInteger getHod_approved() {
		return hod_approved;
	}
	public void setHod_approved(BigInteger hod_approved) {
		this.hod_approved = hod_approved;
	}
	public BigInteger getHod_rejected() {
		return hod_rejected;
	}
	public void setHod_rejected(BigInteger hod_rejected) {
		this.hod_rejected = hod_rejected;
	}
	public BigInteger getDao_alloted() {
		return dao_alloted;
	}
	public void setDao_alloted(BigInteger dao_alloted) {
		this.dao_alloted = dao_alloted;
	}
	public BigInteger getDao_approved() {
		return dao_approved;
	}
	public void setDao_approved(BigInteger dao_approved) {
		this.dao_approved = dao_approved;
	}
	public BigInteger getDao_rejected() {
		return dao_rejected;
	}
	public void setDao_rejected(BigInteger dao_rejected) {
		this.dao_rejected = dao_rejected;
	}
	public BigInteger getAda_alloted() {
		return ada_alloted;
	}
	public void setAda_alloted(BigInteger ada_alloted) {
		this.ada_alloted = ada_alloted;
	}
	public BigInteger getAda_approved() {
		return ada_approved;
	}
	public void setAda_approved(BigInteger ada_approved) {
		this.ada_approved = ada_approved;
	}
	public BigInteger getAda_rejected() {
		return ada_rejected;
	}
	public void setAda_rejected(BigInteger ada_rejected) {
		this.ada_rejected = ada_rejected;
	}
	public BigInteger getMao_alloted() {
		return mao_alloted;
	}
	public void setMao_alloted(BigInteger mao_alloted) {
		this.mao_alloted = mao_alloted;
	}
	public BigInteger getMao_approved() {
		return mao_approved;
	}
	public void setMao_approved(BigInteger mao_approved) {
		this.mao_approved = mao_approved;
	}
	public BigInteger getMao_rejected() {
		return mao_rejected;
	}
	public void setMao_rejected(BigInteger mao_rejected) {
		this.mao_rejected = mao_rejected;
	}
	public BigInteger getDho_alloted() {
		return dho_alloted;
	}
	public void setDho_alloted(BigInteger dho_alloted) {
		this.dho_alloted = dho_alloted;
	}
	public BigInteger getDho_approved() {
		return dho_approved;
	}
	public void setDho_approved(BigInteger dho_approved) {
		this.dho_approved = dho_approved;
	}
	public BigInteger getDho_rejected() {
		return dho_rejected;
	}
	public void setDho_rejected(BigInteger dho_rejected) {
		this.dho_rejected = dho_rejected;
	}
	public BigInteger getHo_alloted() {
		return ho_alloted;
	}
	public void setHo_alloted(BigInteger ho_alloted) {
		this.ho_alloted = ho_alloted;
	}
	public BigInteger getHo_approved() {
		return ho_approved;
	}
	public void setHo_approved(BigInteger ho_approved) {
		this.ho_approved = ho_approved;
	}
	public BigInteger getHo_rejected() {
		return ho_rejected;
	}
	public void setHo_rejected(BigInteger ho_rejected) {
		this.ho_rejected = ho_rejected;
	}
	public BigInteger getRdo_alloted() {
		return rdo_alloted;
	}
	public void setRdo_alloted(BigInteger rdo_alloted) {
		this.rdo_alloted = rdo_alloted;
	}
	public BigInteger getRdo_approved() {
		return rdo_approved;
	}
	public void setRdo_approved(BigInteger rdo_approved) {
		this.rdo_approved = rdo_approved;
	}
	public BigInteger getRdo_rejected() {
		return rdo_rejected;
	}
	public void setRdo_rejected(BigInteger rdo_rejected) {
		this.rdo_rejected = rdo_rejected;
	}
	public BigInteger getTah_alloted() {
		return tah_alloted;
	}
	public void setTah_alloted(BigInteger tah_alloted) {
		this.tah_alloted = tah_alloted;
	}
	public BigInteger getTah_approved() {
		return tah_approved;
	}
	public void setTah_approved(BigInteger tah_approved) {
		this.tah_approved = tah_approved;
	}
	public BigInteger getTah_rejected() {
		return tah_rejected;
	}
	public void setTah_rejected(BigInteger tah_rejected) {
		this.tah_rejected = tah_rejected;
	}
	public BigInteger getDc_alloted() {
		return dc_alloted;
	}
	public void setDc_alloted(BigInteger dc_alloted) {
		this.dc_alloted = dc_alloted;
	}
	public BigInteger getDc_approved() {
		return dc_approved;
	}
	public void setDc_approved(BigInteger dc_approved) {
		this.dc_approved = dc_approved;
	}
	public BigInteger getDc_rejected() {
		return dc_rejected;
	}
	public void setDc_rejected(BigInteger dc_rejected) {
		this.dc_rejected = dc_rejected;
	}
	public BigInteger getJc_alloted() {
		return jc_alloted;
	}
	public void setJc_alloted(BigInteger jc_alloted) {
		this.jc_alloted = jc_alloted;
	}
	public BigInteger getJc_approved() {
		return jc_approved;
	}
	public void setJc_approved(BigInteger jc_approved) {
		this.jc_approved = jc_approved;
	}
	public BigInteger getJc_rejected() {
		return jc_rejected;
	}
	public void setJc_rejected(BigInteger jc_rejected) {
		this.jc_rejected = jc_rejected;
	}


}
