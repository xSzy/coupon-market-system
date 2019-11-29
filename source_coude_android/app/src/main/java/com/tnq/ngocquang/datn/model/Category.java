package com.tnq.ngocquang.datn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Category implements Parcelable {

    private int id;
    private String name;
    private String icon;
    private ArrayList<Category> subCategory;
    private String description;
    private String descriptionEnglish;
    private boolean topCategory;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", subCategory=" + subCategory +
                ", description='" + description + '\'' +
                ", descriptionEnglish='" + descriptionEnglish + '\'' +
                ", topCategory=" + topCategory +
                '}';
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        icon = in.readString();
        subCategory = in.createTypedArrayList(Category.CREATOR);
        description = in.readString();
        descriptionEnglish = in.readString();
        topCategory = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(icon);
        parcel.writeTypedList(subCategory);
        parcel.writeString(description);
        parcel.writeString(descriptionEnglish);
        parcel.writeByte((byte) (topCategory ? 1 : 0));
    }
}
