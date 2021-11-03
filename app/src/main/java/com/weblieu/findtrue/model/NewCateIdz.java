package com.weblieu.findtrue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCateIdz {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_alias")
    @Expose
    private String categoryAlias;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcon;
    @SerializedName("cat_order")
    @Expose
    private String catOrder;
    @SerializedName("flagvalue")
    @Expose
    private String flagvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCatOrder() {
        return catOrder;
    }

    public void setCatOrder(String catOrder) {
        this.catOrder = catOrder;
    }

    public String getFlagvalue() {
        return flagvalue;
    }

    public void setFlagvalue(String flagvalue) {
        this.flagvalue = flagvalue;
    }

    @Override
    public String toString() {
        return "NewCateIdz{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryAlias='" + categoryAlias + '\'' +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", catOrder='" + catOrder + '\'' +
                ", flagvalue='" + flagvalue + '\'' +
                '}';
    }
}
