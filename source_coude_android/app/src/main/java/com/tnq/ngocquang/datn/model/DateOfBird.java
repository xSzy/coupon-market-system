package com.tnq.ngocquang.datn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DateOfBird extends Date implements Parcelable {

    public DateOfBird(long date) {
        super(date);
    }

    protected DateOfBird(Parcel in) {
    }

    public static final Creator<DateOfBird> CREATOR = new Creator<DateOfBird>() {
        @Override
        public DateOfBird createFromParcel(Parcel in) {
            return new DateOfBird(in);
        }

        @Override
        public DateOfBird[] newArray(int size) {
            return new DateOfBird[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
