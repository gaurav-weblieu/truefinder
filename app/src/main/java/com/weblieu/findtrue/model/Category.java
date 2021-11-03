package com.weblieu.findtrue.model;


public class Category {
    private String id;

    private String category_name;

    private String category_icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", category_icon='" + category_icon + '\'' +
                '}';
    }
}
