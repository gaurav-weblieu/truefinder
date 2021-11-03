package com.weblieu.findtrue.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUpdate {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("img_url")
    @Expose
    private String img_url;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
