package com.ass2.final_project_i190727_i190542_i180580;

public class ProfileInformation {
    String name, address, city, identityNumber, profileType;

    public ProfileInformation() {
    }

    public ProfileInformation(String name, String address, String city, String identityNumber, String profileType) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.identityNumber = identityNumber;
        this.profileType = profileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
