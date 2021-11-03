package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FAQData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("faqquestion")
    @Expose
    private String faqquestion;
    @SerializedName("faqtext")
    @Expose
    private String faqtext;
    @SerializedName("flagvalue")
    @Expose
    private String flagvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getFaqquestion() {
        return faqquestion;
    }

    public void setFaqquestion(String faqquestion) {
        this.faqquestion = faqquestion;
    }

    public String getFaqtext() {
        return faqtext;
    }

    public void setFaqtext(String faqtext) {
        this.faqtext = faqtext;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }
}
