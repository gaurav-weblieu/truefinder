package com.weblieu.findtrue.model;

public class DataType {
    public static final int SLIDER_IMAGE_TYPE = 0;
    public static final int CATEGORIES_TYPE = 1;

    public int type;
    private String imageUrl;

    public DataType(int type)
    {
        this.type=type;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
