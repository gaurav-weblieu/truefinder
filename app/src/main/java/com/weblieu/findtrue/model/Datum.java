package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("localarea")
    @Expose
    private String localarea;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("vendor_email")
    @Expose
    private String vendorEmail;
    @SerializedName("contactno")
    @Expose
    private String contactno;
    @SerializedName("landline_code")
    @Expose
    private String landlineCode;
    @SerializedName("landline_no")
    @Expose
    private String landlineNo;
    @SerializedName("bigimage")
    @Expose
    private String bigimage;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("area_name")
    @Expose
    private Object areaName;
    @SerializedName("specialmember")
    @Expose
    private String specialmember;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocalarea() {
        return localarea;
    }

    public void setLocalarea(String localarea) {
        this.localarea = localarea;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getLandlineCode() {
        return landlineCode;
    }

    public void setLandlineCode(String landlineCode) {
        this.landlineCode = landlineCode;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public String getBigimage() {
        return bigimage;
    }

    public void setBigimage(String bigimage) {
        this.bigimage = bigimage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getAreaName() {
        return areaName;
    }

    public void setAreaName(Object areaName) {
        this.areaName = areaName;
    }

    public String getSpecialmember() {
        return specialmember;
    }

    public void setSpecialmember(String specialmember) {
        this.specialmember = specialmember;
    }

}
