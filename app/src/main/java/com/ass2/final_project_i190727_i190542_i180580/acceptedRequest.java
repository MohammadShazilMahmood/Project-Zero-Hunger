package com.ass2.final_project_i190727_i190542_i180580;

public class acceptedRequest {
    donationRequest request;
    String NGO_Name, NGO_ID, NGO_Address, NGO_City, acceptedTime, NGO_Number, NGO_Email;

    public acceptedRequest() {
    }

    public acceptedRequest(donationRequest request, String NGO_Name, String NGO_ID, String NGO_Address, String NGO_City, String acceptedTime, String NGO_Number, String NGO_Email) {
        this.request = request;
        this.NGO_Name = NGO_Name;
        this.NGO_ID = NGO_ID;
        this.NGO_Address = NGO_Address;
        this.NGO_City = NGO_City;
        this.acceptedTime = acceptedTime;
        this.NGO_Number = NGO_Number;
        this.NGO_Email = NGO_Email;
    }


    public donationRequest getRequest() {
        return request;
    }

    public void setRequest(donationRequest request) {
        this.request = request;
    }

    public String getNGO_Name() {
        return NGO_Name;
    }

    public void setNGO_Name(String NGO_Name) {
        this.NGO_Name = NGO_Name;
    }

    public String getNGO_ID() {
        return NGO_ID;
    }

    public void setNGO_ID(String NGO_ID) {
        this.NGO_ID = NGO_ID;
    }

    public String getNGO_Address() {
        return NGO_Address;
    }

    public void setNGO_Address(String NGO_Address) {
        this.NGO_Address = NGO_Address;
    }

    public String getNGO_City() {
        return NGO_City;
    }

    public void setNGO_City(String NGO_City) {
        this.NGO_City = NGO_City;
    }

    public String getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public String getNGO_Number() {
        return NGO_Number;
    }

    public void setNGO_Number(String NGO_Number) {
        this.NGO_Number = NGO_Number;
    }

    public String getNGO_Email() {
        return NGO_Email;
    }

    public void setNGO_Email(String NGO_Email) {
        this.NGO_Email = NGO_Email;
    }
}
