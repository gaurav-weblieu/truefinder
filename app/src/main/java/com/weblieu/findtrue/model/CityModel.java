package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CityList> data = null;

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public List<CityList> getData() {
        return data;
    }
}
