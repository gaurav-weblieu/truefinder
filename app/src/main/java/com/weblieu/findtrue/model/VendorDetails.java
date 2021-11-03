package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendorid")
    @Expose
    private String vendorid;
    @SerializedName("establish")
    @Expose
    private String establish;
    @SerializedName("accepts")
    @Expose
    private String accepts;
    @SerializedName("office_hour")
    @Expose
    private String officeHour;
    @SerializedName("package_price")
    @Expose
    private String packagePrice;
    @SerializedName("package_price_detail")
    @Expose
    private String packagePriceDetail;
    @SerializedName("flagvalue")
    @Expose
    private String flagvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getEstablish() {
        return establish;
    }

    public void setEstablish(String establish) {
        this.establish = establish;
    }

    public String getAccepts() {
        return accepts;
    }

    public void setAccepts(String accepts) {
        this.accepts = accepts;
    }

    public String getOfficeHour() {
        return officeHour;
    }

    public void setOfficeHour(String officeHour) {
        this.officeHour = officeHour;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPackagePriceDetail() {
        return packagePriceDetail;
    }

    public void setPackagePriceDetail(String packagePriceDetail) {
        this.packagePriceDetail = packagePriceDetail;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }
}
