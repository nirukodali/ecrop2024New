package com.ecrops.entity;

import java.math.BigDecimal; 
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cr_authdetails_mand_mv_")
public class Cr_authdetails_mand_cropwise_v_syearFfekycd {
	
	@Id
    private int totalbookings;
    private BigDecimal totextent;
    private int vaaauthcount;
    private BigDecimal vaaauthextent;
    private int vroauthcount;
    private BigDecimal vroauthextent;
    private int totekycbookings;
    private int totfarmercount;
    private int ekycfarmercount;
    private BigDecimal ekycbookedext;
    private String wbmname;
    private String wbdname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mname")
    private Wbvillage_mstFfekycm wbvillage_mst;
	public int getTotalbookings() {
		return totalbookings;
	}
	public void setTotalbookings(int totalbookings) {
		this.totalbookings = totalbookings;
	}
	public BigDecimal getTotextent() {
		return totextent;
	}
	public void setTotextent(BigDecimal totextent) {
		this.totextent = totextent;
	}
	public int getVaaauthcount() {
		return vaaauthcount;
	}
	public void setVaaauthcount(int vaaauthcount) {
		this.vaaauthcount = vaaauthcount;
	}
	public BigDecimal getVaaauthextent() {
		return vaaauthextent;
	}
	public void setVaaauthextent(BigDecimal vaaauthextent) {
		this.vaaauthextent = vaaauthextent;
	}
	public int getVroauthcount() {
		return vroauthcount;
	}
	public void setVroauthcount(int vroauthcount) {
		this.vroauthcount = vroauthcount;
	}
	public BigDecimal getVroauthextent() {
		return vroauthextent;
	}
	public void setVroauthextent(BigDecimal vroauthextent) {
		this.vroauthextent = vroauthextent;
	}
	public int getTotekycbookings() {
		return totekycbookings;
	}
	public void setTotekycbookings(int totekycbookings) {
		this.totekycbookings = totekycbookings;
	}
	public int getTotfarmercount() {
		return totfarmercount;
	}
	public void setTotfarmercount(int totfarmercount) {
		this.totfarmercount = totfarmercount;
	}
	public int getEkycfarmercount() {
		return ekycfarmercount;
	}
	public void setEkycfarmercount(int ekycfarmercount) {
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
	public String getWbdname() {
		return wbdname;
	}
	public void setWbdname(String wbdname) {
		this.wbdname = wbdname;
	}
	public Wbvillage_mstFfekycm getWbvillage_mst() {
		return wbvillage_mst;
	}
	public void setWbvillage_mst(Wbvillage_mstFfekycm wbvillage_mst) {
		this.wbvillage_mst = wbvillage_mst;
	}
    
    
}
