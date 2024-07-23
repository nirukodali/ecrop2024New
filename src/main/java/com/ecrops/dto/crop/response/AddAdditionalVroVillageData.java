package com.ecrops.dto.crop.response;

public class AddAdditionalVroVillageData {
    private int Wbvcode;
    private String Wbvname;

    public AddAdditionalVroVillageData() {
    }

    public AddAdditionalVroVillageData(int wbvcode, String wbvname) {
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
