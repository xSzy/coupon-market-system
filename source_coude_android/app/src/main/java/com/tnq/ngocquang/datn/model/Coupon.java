package com.tnq.ngocquang.datn.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Coupon implements Parcelable  {
    private int id;
    private String title;
    private String description;
    private Product product;
    private int type;
    private double value;
    private Date expireDate;
    private int valueType;
    private int clickCount;
    private Date createdDate;
    private User createdBy;
    private Category category;

    //private String[] images;


    public Coupon() {
    }

    public Coupon(int id,String title, String description, Product product, int type, double value, Date expireDate, int valueType, int clickCount, Date createDate, User createBy, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.product = product;
        this.type = type;
        this.value = value;
        this.expireDate = expireDate;
        this.valueType = valueType;
        this.clickCount = clickCount;
        this.createdDate = createDate;
        this.createdBy = createBy;
        this.category = category;
    }


    protected Coupon(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
        type = in.readInt();
        value = in.readDouble();
        //
        expireDate = new Date(in.readLong());
        valueType = in.readInt();
        clickCount = in.readInt();
        //
        createdDate = new Date(in.readLong());
        createdBy = in.readParcelable(User.class.getClassLoader());
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

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", product=" + product.toString() +
                ", type=" + type +
                ", value=" + value +
                ", expireDate=" + expireDate +
                ", valueType=" + valueType +
                ", clickCount=" + clickCount +
                ", createdDate=" + createdDate +
                ", createdBy=" + createdBy.toString() +
                ", category=" + category.toString() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return valueType;
    }

    public void setValuetype(int valuetype) {
        this.valueType = valuetype;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public Date getCreateDate() {
        return createdDate;
    }

    public void setCreateDate(Date createDate) {
        this.createdDate = createDate;
    }

    public User getCreateBy() {
        return createdBy;
    }

    public void setCreateBy(User createBy) {
        this.createdBy = createBy;
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
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(product, i);
        parcel.writeInt(type);
        parcel.writeDouble(value);
        //
        if(expireDate != null){
            parcel.writeLong(expireDate.getTime());
        }else{
            parcel.writeLong(Calendar.getInstance().getTimeInMillis());
        }
        parcel.writeInt(valueType);
        parcel.writeInt(clickCount);
        //
        if(createdDate != null){
            parcel.writeLong(createdDate.getTime());
        }else{
            parcel.writeLong(Calendar.getInstance().getTimeInMillis());
        }
        parcel.writeParcelable(createdBy, i);
        parcel.writeParcelable(category, i);
    }
}
