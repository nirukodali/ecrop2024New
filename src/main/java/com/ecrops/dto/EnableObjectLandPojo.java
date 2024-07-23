package com.ecrops.dto;


import java.math.BigDecimal;



public class EnableObjectLandPojo {
	

    private String vname;
    private String mname;
    private String rbkname;
    private String category;
    private Integer recId;
    private BigDecimal khNo;
  
	public BigDecimal getKhNo() {
		return khNo;
	}
	public void setKhNo(BigDecimal khNo) {
		this.khNo = khNo;
	}
	private BigDecimal occupExtent;
    private String crSno;
    private BigDecimal requestExtent;
    
    

	public String getRbkname() {
		return rbkname;
	}
	public void setRbkname(String rbkname) {
		this.rbkname = rbkname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getRecId() {
		return recId;
	}
	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public BigDecimal getOccupExtent() {
		return occupExtent;
	}
	public void setOccupExtent(BigDecimal occupExtent) {
		this.occupExtent = occupExtent;
	}
	public String getCrSno() {
		return crSno;
	}
	public void setCrSno(String crSno) {
		this.crSno = crSno;
	}
	public BigDecimal getRequestExtent() {
		return requestExtent;
	}
	public void setRequestExtent(BigDecimal requestExtent) {
		this.requestExtent = requestExtent;
	}

	
	public String getVname() {
		return vname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	
  

}
