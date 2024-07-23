package com.ecrops.dto.crop.response;

public class SuperCheckVillageData {
    private int Wbvcode;
    private String Wbvname;

    public SuperCheckVillageData() {
        // Default constructor
    }

    public SuperCheckVillageData(int wbvcode, String wbvname) {
        this.Wbvcode = wbvcode;
        this.Wbvname = wbvname;
    }

    public int getWbvcode() {
        return Wbvcode;
    }

    public void setWbvcode(int wbvcode) {
        Wbvcode = wbvcode;
    }

    public String getWbvname() {
        return Wbvname;
    }

    public void setWbvname(String wbvname) {
        Wbvname = wbvname;
    }
}
