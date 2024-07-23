package com.ecrops.entity;

import java.math.BigDecimal; 
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
@Entity
@Table(name="cr_authdetails_mand_mv_")
public class Cr_authdetails_mand_mv_kyearFfekyc {
	
	@Id
    private BigDecimal totalbookings;
    private BigDecimal totextent;
    private BigDecimal vaaauthcount;
    private BigDecimal vaaauthextent;
    private BigDecimal vroauthcount;
    private BigDecimal vroauthextent;
    private BigDecimal totekycbookings;
    private BigDecimal totfarmercount;
    private BigDecimal ekycfarmercount;
    private BigDecimal ekycbookedext;
    private String wbmname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mname")
    private Wbvillage_mstFfekyc wbvillage_mst;
	public Cr_authdetails_mand_mv_kyearFfekyc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cr_authdetails_mand_mv_kyearFfekyc(BigDecimal totalbookings, BigDecimal totextent, BigDecimal vaaauthcount,
			BigDecimal vaaauthextent, BigDecimal vroauthcount, BigDecimal vroauthextent, BigDecimal totekycbookings,
			BigDecimal totfarmercount, BigDecimal ekycfarmercount, BigDecimal ekycbookedext, String wbmname,
			Wbvillage_mstFfekyc wbvillage_mst) {
		super();
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
		this.wbmname = wbmname;
		this.wbvillage_mst = wbvillage_mst;
	}
	public BigDecimal getTotalbookings() {
		return totalbookings;
	}
	public void setTotalbookings(BigDecimal totalbookings) {
		this.totalbookings = totalbookings;
	}
	public BigDecimal getTotextent() {
		return totextent;
	}
	public void setTotextent(BigDecimal totextent) {
		this.totextent = totextent;
	}
	public BigDecimal getVaaauthcount() {
		return vaaauthcount;
	}
	public void setVaaauthcount(BigDecimal vaaauthcount) {
		this.vaaauthcount = vaaauthcount;
	}
	public BigDecimal getVaaauthextent() {
		return vaaauthextent;
	}
	public void setVaaauthextent(BigDecimal vaaauthextent) {
		this.vaaauthextent = vaaauthextent;
	}
	public BigDecimal getVroauthcount() {
		return vroauthcount;
	}
	public void setVroauthcount(BigDecimal vroauthcount) {
		this.vroauthcount = vroauthcount;
	}
	public BigDecimal getVroauthextent() {
		return vroauthextent;
	}
	public void setVroauthextent(BigDecimal vroauthextent) {
		this.vroauthextent = vroauthextent;
	}
	public BigDecimal getTotekycbookings() {
		return totekycbookings;
	}
	public void setTotekycbookings(BigDecimal totekycbookings) {
		this.totekycbookings = totekycbookings;
	}
	public BigDecimal getTotfarmercount() {
		return totfarmercount;
	}
	public void setTotfarmercount(BigDecimal totfarmercount) {
		this.totfarmercount = totfarmercount;
	}
	public BigDecimal getEkycfarmercount() {
		return ekycfarmercount;
	}
	public void setEkycfarmercount(BigDecimal ekycfarmercount) {
		this.ekycfarmercount = ekycfarmercount;
	}
	public BigDecimal getEkycbookedext() {
		return ekycbookedext;
	}
	public void setEkycbookedext(BigDecimal ekycbookedext) {
		this.ekycbookedext = ekycbookedext;
	}
	public String getWbmname() {
		return wbmname;
	}
	public void setWbmname(String wbmname) {
		this.wbmname = wbmname;
	}
	public Wbvillage_mstFfekyc getWbvillage_mst() {
		return wbvillage_mst;
	}
	public void setWbvillage_mst(Wbvillage_mstFfekyc wbvillage_mst) {
		this.wbvillage_mst = wbvillage_mst;
	}
	@Override
	public String toString() {
		return "Cr_authdetails_mand_mv_kyearFfekyc [totalbookings=" + totalbookings + ", totextent=" + totextent
				+ ", vaaauthcount=" + vaaauthcount + ", vaaauthextent=" + vaaauthextent + ", vroauthcount="
				+ vroauthcount + ", vroauthextent=" + vroauthextent + ", totekycbookings=" + totekycbookings
				+ ", totfarmercount=" + totfarmercount + ", ekycfarmercount=" + ekycfarmercount + ", ekycbookedext="
				+ ekycbookedext + ", wbmname=" + wbmname + ", wbvillage_mst=" + wbvillage_mst + "]";
	}
    
    
}
