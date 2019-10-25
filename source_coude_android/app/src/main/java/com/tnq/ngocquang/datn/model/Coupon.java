package com.tnq.ngocquang.datn.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Coupon implements Parcelable {
    private String title;
    private String description;
    //private String[] images;


    public Coupon(String title, String description) {
        this.title = title;
        this.description = description;
    }

    protected Coupon(Parcel in){
        title = in.readString();
        description = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
