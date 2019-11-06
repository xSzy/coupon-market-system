package com.tnq.ngocquang.datn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Product implements Parcelable {
    private String name;
    private String manufacturer;
    private ArrayList<ProductImage> productImages;
    private String contact;

    public Product() {
    }

    public Product(String name, String manufacturer, ArrayList<ProductImage> productImages, String contact) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.productImages = productImages;
        this.contact = contact;
    }

    protected Product(Parcel in) {
        name = in.readString();
        manufacturer = in.readString();
        productImages = in.createTypedArrayList(ProductImage.CREATOR);
        contact = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(manufacturer);
        parcel.writeTypedList(productImages);
        parcel.writeString(contact);
    }
}
