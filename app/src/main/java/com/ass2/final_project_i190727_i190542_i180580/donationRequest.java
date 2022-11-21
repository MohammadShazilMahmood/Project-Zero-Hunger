package com.ass2.final_project_i190727_i190542_i180580;

public class donationRequest {
    String donationID, donorName, donorID, donorAddress, donorCity, foodPicURL, foodDetails, time;

    public donationRequest() {
    }

    public donationRequest(String donationID, String donorName, String donorID, String donorAddress, String donorCity, String foodPicURL, String foodDetails, String time) {
        this.donationID = donationID;
        this.donorName = donorName;
        this.donorID = donorID;
        this.donorAddress = donorAddress;
        this.donorCity = donorCity;
        this.foodPicURL = foodPicURL;
        this.foodDetails = foodDetails;
        this.time = time;
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
}
