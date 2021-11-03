package com.weblieu.findtrue.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("telephoneno")
    @Expose
    private String telephoneno;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("mobile_otp")
    @Expose
    private Object mobileOtp;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("profile_image_partner")
    @Expose
    private String profileImagePartner;
    @SerializedName("ext_partner")
    @Expose
    private String extPartner;
    @SerializedName("doj")
    @Expose
    private String doj;
    @SerializedName("verification")
    @Expose
    private String verification;
    @SerializedName("flagvalue")
    @Expose
    private String flagvalue;
    @SerializedName("new_pofile_image")
    @Expose
    private String newPofileImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(String telephoneno) {
        this.telephoneno = telephoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public Object getMobileOtp() {
        return mobileOtp;
    }

    public void setMobileOtp(Object mobileOtp) {
        this.mobileOtp = mobileOtp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getProfileImagePartner() {
        return profileImagePartner;
    }

    public void setProfileImagePartner(String profileImagePartner) {
        this.profileImagePartner = profileImagePartner;
    }

    public String getExtPartner() {
        return extPartner;
    }

    public void setExtPartner(String extPartner) {
        this.extPartner = extPartner;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }

    public String getNewPofileImage() {
        return newPofileImage;
    }

    public void setNewPofileImage(String newPofileImage) {
        this.newPofileImage = newPofileImage;
    }
}
