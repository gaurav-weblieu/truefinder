package com.weblieu.findtrue.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcity {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_alias")
    @Expose
    private String cityAlias;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private Object country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityAlias() {
        return cityAlias;
    }

    public void setCityAlias(String cityAlias) {
        this.cityAlias = cityAlias;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }
}
