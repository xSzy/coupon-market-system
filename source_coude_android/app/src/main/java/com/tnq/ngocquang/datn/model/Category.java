package com.tnq.ngocquang.datn.model;

import java.util.ArrayList;

public class Category {

    private int id;
    private String name;
    private String icon;
    private ArrayList<Category> subCategory;
    private String description;
    private String descriptionEnglish;
    private boolean topCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public boolean isTopCategory() {
        return topCategory;
    }

    public void setTopCategory(boolean topCategory) {
        this.topCategory = topCategory;
    }
}
