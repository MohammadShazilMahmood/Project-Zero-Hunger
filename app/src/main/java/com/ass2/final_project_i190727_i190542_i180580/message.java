package com.ass2.final_project_i190727_i190542_i180580;

import android.os.Parcel;
import android.os.Parcelable;

public class message implements Parcelable {
    String name, identityNumber, profileType, address, city, number, email, message, dateTime, userID;

    public message() {
    }

    public message(String name, String identityNumber, String profileType, String address, String city, String number, String email, String message, String dateTime, String userID) {
        this.name = name;
        this.identityNumber = identityNumber;
        this.profileType = profileType;
        this.address = address;
        this.city = city;
        this.number = number;
        this.email = email;
        this.message = message;
        this.dateTime = dateTime;
        this.userID = userID;
    }

    protected message(Parcel in) {
        name = in.readString();
        identityNumber = in.readString();
        profileType = in.readString();
        address = in.readString();
        city = in.readString();
        number = in.readString();
        email = in.readString();
        message = in.readString();
        dateTime = in.readString();
        userID = in.readString();
    }

    public static final Creator<message> CREATOR = new Creator<message>() {
        @Override
        public message createFromParcel(Parcel in) {
            return new message(in);
        }

        @Override
        public message[] newArray(int size) {
            return new message[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(identityNumber);
        parcel.writeString(profileType);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(number);
        parcel.writeString(email);
        parcel.writeString(message);
        parcel.writeString(dateTime);
        parcel.writeString(userID);
    }
}
