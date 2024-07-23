package com.ecrops.dto.crop.response;

public class LgddcodeLgmcodeFromWbVillageMst {
    private int Lgddcode;
    private int Lgdmcode;

    public LgddcodeLgmcodeFromWbVillageMst() {
    }

	public LgddcodeLgmcodeFromWbVillageMst(int lgddcode, int lgdmcode) {
		super();
		Lgddcode = lgddcode;
		Lgdmcode = lgdmcode;
	}

	public int getLgddcode() {
		return Lgddcode;
	}

	public void setLgddcode(int lgddcode) {
		Lgddcode = lgddcode;
	}

	public int getLgdmcode() {
		return Lgdmcode;
	}

	public void setLgdmcode(int lgdmcode) {
		Lgdmcode = lgdmcode;
	}

	@Override
	public String toString() {
		return "LgddcodeLgmcodeFromWbVillageMst [Lgddcode=" + Lgddcode + ", Lgdmcode=" + Lgdmcode + "]";
	}
    
    

   }
