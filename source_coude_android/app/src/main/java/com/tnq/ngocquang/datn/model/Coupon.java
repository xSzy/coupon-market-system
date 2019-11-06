package com.tnq.ngocquang.datn.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Coupon implements Parcelable  {
    private String title;
    private String description;
    private Product product;
    private int type;
    private double value;
    private Date expireDate;
    private int valuetype;
    private int clickCount;
    private Date createDate;
    private User createBy;
    private Category category;

    //private String[] images;


    public Coupon() {
    }

    public Coupon(String title, String description, Product product, int type, double value, Date expireDate, int valuetype, int clickCount, Date createDate, User createBy, Category category) {
        this.title = title;
        this.description = description;
        this.product = product;
        this.type = type;
        this.value = value;
        this.expireDate = expireDate;
        this.valuetype = valuetype;
        this.clickCount = clickCount;
        this.createDate = createDate;
        this.createBy = createBy;
        this.category = category;
    }

    protected Coupon(Parcel in) {
        title = in.readString();
        description = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
        type = in.readInt();
        value = in.readDouble();
        valuetype = in.readInt();
        clickCount = in.readInt();
        category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getValuetype() {
        return valuetype;
    }

    public void setValuetype(int valuetype) {
        this.valuetype = valuetype;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(product, i);
        parcel.writeInt(type);
        parcel.writeDouble(value);
        parcel.writeInt(valuetype);
        parcel.writeInt(clickCount);
        parcel.writeParcelable(category, i);
    }
}
