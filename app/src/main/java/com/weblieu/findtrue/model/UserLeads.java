package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLeads {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("area_pin_id")
    @Expose
    private String areaPinId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_country_code")
    @Expose
    private String mobileCountryCode;
    @SerializedName("contactno")
    @Expose
    private String contactno;
    @SerializedName("bookingdate")
    @Expose
    private String bookingdate;
    @SerializedName("whatsapp_message_status")
    @Expose
    private String whatsappMessageStatus;
    @SerializedName("msgdatetime")
    @Expose
    private String msgdatetime;
    @SerializedName("ipaddress")
    @Expose
    private String ipaddress;
    @SerializedName("msgstatus")
    @Expose
    private String msgstatus;
    @SerializedName("cat_id")
    @Expose
    private String catId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaPinId() {
        return areaPinId;
    }

    public void setAreaPinId(String areaPinId) {
        this.areaPinId = areaPinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getWhatsappMessageStatus() {
        return whatsappMessageStatus;
    }

    public void setWhatsappMessageStatus(String whatsappMessageStatus) {
        this.whatsappMessageStatus = whatsappMessageStatus;
    }

    public String getMsgdatetime() {
        return msgdatetime;
    }

    public void setMsgdatetime(String msgdatetime) {
        this.msgdatetime = msgdatetime;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(String msgstatus) {
        this.msgstatus = msgstatus;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }

    @Override
    public String toString() {
        return "UserLeads{" +
                "id='" + id + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", areaPinId='" + areaPinId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileCountryCode='" + mobileCountryCode + '\'' +
                ", contactno='" + contactno + '\'' +
                ", bookingdate='" + bookingdate + '\'' +
                ", whatsappMessageStatus='" + whatsappMessageStatus + '\'' +
                ", msgdatetime='" + msgdatetime + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", msgstatus='" + msgstatus + '\'' +
                ", catId='" + catId + '\'' +
                ", flagvalue='" + flagvalue + '\'' +
                '}';
    }
}
