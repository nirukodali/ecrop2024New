package com.ecrops.dto.crop.response;

public class SeasonCropYear {
    private String seasonvalue;
    private String cropyear;

    public SeasonCropYear() {
    }

    public SeasonCropYear(String seasonvalue, String cropyear) {
        this.seasonvalue = seasonvalue;
        this.cropyear = cropyear;
    }

    public String getSeasonvalue() {
        return seasonvalue;
    }

    public void setSeasonvalue(String seasonvalue) {
        this.seasonvalue = seasonvalue;
    }

    public String getCropyear() {
        return cropyear;
    }

    public void setCropyear(String cropyear) {
        this.cropyear = cropyear;
    }

    @Override
    public String toString() {
        return "SeasonCropYear{" +
                "seasonvalue='" + seasonvalue + '\'' +
                ", cropyear='" + cropyear + '\'' +
                '}';
    }
}