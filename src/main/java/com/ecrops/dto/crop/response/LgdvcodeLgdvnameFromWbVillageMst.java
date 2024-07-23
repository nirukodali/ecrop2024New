package com.ecrops.dto.crop.response;

public class LgdvcodeLgdvnameFromWbVillageMst {
    private int Lgdvcode;
    private String Lgdvname;

    public LgdvcodeLgdvnameFromWbVillageMst() {
    }

	public LgdvcodeLgdvnameFromWbVillageMst(int lgdvcode, String lgdvname) {
		super();
		Lgdvcode = lgdvcode;
		Lgdvname = lgdvname;
	}

	public int getLgdvcode() {
		return Lgdvcode;
	}

	public void setLgdvcode(int lgdvcode) {
		Lgdvcode = lgdvcode;
	}

	public String getLgdvname() {
		return Lgdvname;
	}

	public void setLgdvname(String lgdvname) {
		Lgdvname = lgdvname;
	}

	@Override
	public String toString() {
		return "LgdvcodeLgdvnameFromWbVillageMst [Lgdvcode=" + Lgdvcode + ", Lgdvname=" + Lgdvname + "]";
	}
    

   }
