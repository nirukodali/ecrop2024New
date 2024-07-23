package com.ecrops.dto.crop.response;
public class BookingDetailsPojo {
    private String bookingid;
    private String kh_no;
    private String cr_sno;
    private String cr_mix_unmix_ext;
    private String cropname;
    private String varietyname;
    private String occupname;
    private String smsmobileno;
    private String wbdname;
    private String wbmname;
    private String wbvname;
    private String templateId;

    // Constructor, getters, and setters
    public BookingDetailsPojo() {
    }
    
    

    public BookingDetailsPojo(String bookingid, String kh_no, String cr_sno, String cr_mix_unmix_ext, String cropname,
			String varietyname, String occupname, String smsmobileno, String wbdname, String wbmname, String wbvname,
			String templateId) {
		super();
		this.bookingid = bookingid;
		this.kh_no = kh_no;
		this.cr_sno = cr_sno;
		this.cr_mix_unmix_ext = cr_mix_unmix_ext;
		this.cropname = cropname;
		this.varietyname = varietyname;
		this.occupname = occupname;
		this.smsmobileno = smsmobileno;
		this.wbdname = wbdname;
		this.wbmname = wbmname;
		this.wbvname = wbvname;
		this.templateId = templateId;
	}



	public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getKh_no() {
        return kh_no;
    }

    public void setKh_no(String kh_no) {
        this.kh_no = kh_no;
    }

    public String getCr_sno() {
        return cr_sno;
    }

    public void setCr_sno(String cr_sno) {
        this.cr_sno = cr_sno;
    }

    public String getCr_mix_unmix_ext() {
        return cr_mix_unmix_ext;
    }

    public void setCr_mix_unmix_ext(String cr_mix_unmix_ext) {
        this.cr_mix_unmix_ext = cr_mix_unmix_ext;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getVarietyname() {
        return varietyname;
    }

    public void setVarietyname(String varietyname) {
        this.varietyname = varietyname;
    }

    public String getOccupname() {
        return occupname;
    }

    public void setOccupname(String occupname) {
        this.occupname = occupname;
    }

    public String getSmsmobileno() {
        return smsmobileno;
    }

    public void setSmsmobileno(String smsmobileno) {
        this.smsmobileno = smsmobileno;
    }

    public String getWbdname() {
        return wbdname;
    }

    public void setWbdname(String wbdname) {
        this.wbdname = wbdname;
    }

    public String getWbmname() {
        return wbmname;
    }

    public void setWbmname(String wbmname) {
        this.wbmname = wbmname;
    }

    public String getWbvname() {
        return wbvname;
    }

    public void setWbvname(String wbvname) {
        this.wbvname = wbvname;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    // toString method for debugging or logging
    @Override
    public String toString() {
        return "BookingDetails{" +
                "bookingid='" + bookingid + '\'' +
                ", kh_no='" + kh_no + '\'' +
                ", cr_sno='" + cr_sno + '\'' +
                ", cr_mix_unmix_ext='" + cr_mix_unmix_ext + '\'' +
                ", cropname='" + cropname + '\'' +
                ", varietyname='" + varietyname + '\'' +
                ", occupname='" + occupname + '\'' +
                ", smsmobileno='" + smsmobileno + '\'' +
                ", wbdname='" + wbdname + '\'' +
                ", wbmname='" + wbmname + '\'' +
                ", wbvname='" + wbvname + '\'' +
                ", templateId='" + templateId + '\'' +
                '}';
    }
}
