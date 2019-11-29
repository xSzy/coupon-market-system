package com.tnq.ngocquang.datn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class User implements Parcelable {
    private int id;
    Account account;
    private String avatarUrl;
    private String name;
    private Date dob;
    private String email;
    private String address;
    private String phoneNumber;
    private String citizenId;
    private int gender; // 1 : male // 2 : female
    private int role; // 1 : user // 2 : admin

    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account=" + account.toString() +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", citizenId='" + citizenId + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    protected User(Parcel in) {
        id  = in.readInt();
        account = in.readParcelable(Account.class.getClassLoader());
        avatarUrl = in.readString();
        name = in.readString();
        dob = new Date(in.readLong());
        email = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        citizenId = in.readString();
        gender = in.readInt();
        role = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(id);
        parcel.writeParcelable(account, flag);
        parcel.writeString(avatarUrl);
        parcel.writeString(name);
        if(dob != null){
            parcel.writeLong(dob.getTime());
        }else{
            parcel.writeLong(Calendar.getInstance().getTimeInMillis());
        }
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(citizenId);
        parcel.writeInt(gender);
        parcel.writeInt(role);
    }
}
