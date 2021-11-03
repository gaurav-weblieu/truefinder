package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginVendor {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("vendor_email")
    @Expose
    private String vendorEmail;
    @SerializedName("contactno")
    @Expose
    private String contactno;
    @SerializedName("profile_verified_status")
    @Expose
    private String profileVerifiedStatus;
    @SerializedName("profile_verifiedby_adminid")
    @Expose
    private String profileVerifiedbyAdminid;
    @SerializedName("profile_verified_time")
    @Expose
    private String profileVerifiedTime;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public String getProfileVerifiedStatus() {
        return profileVerifiedStatus;
    }

    public void setProfileVerifiedStatus(String profileVerifiedStatus) {
        this.profileVerifiedStatus = profileVerifiedStatus;
    }

    public String getProfileVerifiedbyAdminid() {
        return profileVerifiedbyAdminid;
    }

    public void setProfileVerifiedbyAdminid(String profileVerifiedbyAdminid) {
        this.profileVerifiedbyAdminid = profileVerifiedbyAdminid;
    }

    public String getProfileVerifiedTime() {
        return profileVerifiedTime;
    }

    public void setProfileVerifiedTime(String profileVerifiedTime) {
        this.profileVerifiedTime = profileVerifiedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
