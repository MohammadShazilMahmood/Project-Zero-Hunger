package com.ass2.final_project_i190727_i190542_i180580;

import android.os.Parcel;
import android.os.Parcelable;

public class donationRequest implements Parcelable {
    String donationID, donorName, donorID, donorAddress, donorCity, foodPicURL, foodDetails, time, number, email;

    public donationRequest() {
    }

    protected donationRequest(Parcel in) {
        donationID = in.readString();
        donorName = in.readString();
        donorID = in.readString();
        donorAddress = in.readString();
        donorCity = in.readString();
        foodPicURL = in.readString();
        foodDetails = in.readString();
        time = in.readString();
        number = in.readString();
        email = in.readString();
    }

    public static final Creator<donationRequest> CREATOR = new Creator<donationRequest>() {
        @Override
        public donationRequest createFromParcel(Parcel in) {
            return new donationRequest(in);
        }

        @Override
        public donationRequest[] newArray(int size) {
            return new donationRequest[size];
        }
    };

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

    public donationRequest(String donationID, String donorName, String donorID, String donorAddress, String donorCity, String foodPicURL, String foodDetails, String time, String number, String email) {
        this.donationID = donationID;
        this.donorName = donorName;
        this.donorID = donorID;
        this.donorAddress = donorAddress;
        this.donorCity = donorCity;
        this.foodPicURL = foodPicURL;
        this.foodDetails = foodDetails;
        this.time = time;
        this.number=number;
        this.email=email;
    }

    public String getDonationID() {
        return donationID;
    }

    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public void setDonorAddress(String donorAddress) {
        this.donorAddress = donorAddress;
    }

    public String getDonorCity() {
        return donorCity;
    }

    public void setDonorCity(String donorCity) {
        this.donorCity = donorCity;
    }

    public String getFoodPicURL() {
        return foodPicURL;
    }

    public void setFoodPicURL(String foodPicURL) {
        this.foodPicURL = foodPicURL;
    }

    public String getFoodDetails() {
        return foodDetails;
    }

    public void setFoodDetails(String foodDetails) {
        this.foodDetails = foodDetails;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(donationID);
        parcel.writeString(donorName);
        parcel.writeString(donorID);
        parcel.writeString(donorAddress);
        parcel.writeString(donorCity);
        parcel.writeString(foodPicURL);
        parcel.writeString(foodDetails);
        parcel.writeString(time);
        parcel.writeString(number);
        parcel.writeString(email);
    }
}
