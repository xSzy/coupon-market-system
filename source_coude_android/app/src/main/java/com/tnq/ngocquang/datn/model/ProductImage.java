package com.tnq.ngocquang.datn.model;

import android.os.Parcel;
import android.os.Parcelable;

class ProductImage implements Parcelable {
    private String image;

    public ProductImage() {
    }

    public ProductImage(String image) {
        this.image = image;
    }

    protected ProductImage(Parcel in) {
        image = in.readString();
    }

    public static final Creator<ProductImage> CREATOR = new Creator<ProductImage>() {
        @Override
        public ProductImage createFromParcel(Parcel in) {
            return new ProductImage(in);
        }

        @Override
        public ProductImage[] newArray(int size) {
            return new ProductImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
    }
}
