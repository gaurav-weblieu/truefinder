package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageDetails {
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("expiry_day")
    @Expose
    private String expiryDay;
    @SerializedName("leads_count")
    @Expose
    private String leadsCount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiryDay() {
        return expiryDay;
    }

    public void setExpiryDay(String expiryDay) {
        this.expiryDay = expiryDay;
    }

    public String getLeadsCount() {
        return leadsCount;
    }

    public void setLeadsCount(String leadsCount) {
        this.leadsCount = leadsCount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
