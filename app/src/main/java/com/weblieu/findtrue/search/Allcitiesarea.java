package com.weblieu.findtrue.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcitiesarea {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("area_alias")
    @Expose
    private String areaAlias;
    @SerializedName("area_name")
    @Expose
    private String areaName;
    @SerializedName("flagvalue")
    @Expose
    private String flagvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaAlias() {
        return areaAlias;
    }

    public void setAreaAlias(String areaAlias) {
        this.areaAlias = areaAlias;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }
}
