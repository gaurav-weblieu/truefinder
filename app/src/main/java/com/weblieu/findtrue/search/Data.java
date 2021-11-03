package com.weblieu.findtrue.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("off_set")
    @Expose
    private Integer offSet;
    @SerializedName("links")
    @Expose
    private String links;
    @SerializedName("vendor")
    @Expose
    private List<Vendor> vendor = null;
    @SerializedName("allcategory")
    @Expose
    private List<Allcategory> allcategory = null;
    @SerializedName("allcities")
    @Expose
    private List<Allcity> allcities = null;
    @SerializedName("allcitiesarea")
    @Expose
    private List<Allcitiesarea> allcitiesarea = null;
    @SerializedName("categoryidbyalias")
    @Expose
    private Integer categoryidbyalias;
    @SerializedName("cityidbyalias")
    @Expose
    private Integer cityidbyalias;
    @SerializedName("searchcat")
    @Expose
    private String searchcat;

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public List<Vendor> getVendor() {
        return vendor;
    }

    public void setVendor(List<Vendor> vendor) {
        this.vendor = vendor;
    }

    public List<Allcategory> getAllcategory() {
        return allcategory;
    }

    public void setAllcategory(List<Allcategory> allcategory) {
        this.allcategory = allcategory;
    }

    public List<Allcity> getAllcities() {
        return allcities;
    }

    public void setAllcities(List<Allcity> allcities) {
        this.allcities = allcities;
    }

    public List<Allcitiesarea> getAllcitiesarea() {
        return allcitiesarea;
    }

    public void setAllcitiesarea(List<Allcitiesarea> allcitiesarea) {
        this.allcitiesarea = allcitiesarea;
    }

    public Integer getCategoryidbyalias() {
        return categoryidbyalias;
    }

    public void setCategoryidbyalias(Integer categoryidbyalias) {
        this.categoryidbyalias = categoryidbyalias;
    }

    public Integer getCityidbyalias() {
        return cityidbyalias;
    }

    public void setCityidbyalias(Integer cityidbyalias) {
        this.cityidbyalias = cityidbyalias;
    }

    public String getSearchcat() {
        return searchcat;
    }

    public void setSearchcat(String searchcat) {
        this.searchcat = searchcat;
    }
}
