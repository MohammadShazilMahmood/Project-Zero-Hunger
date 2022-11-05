package com.ass2.final_project_i190727_i190542_i180580;

public class ContactInformation {
    String email, number;

    public ContactInformation(String email, String number) {
        this.email = email;
        this.number = number;
    }

    public ContactInformation() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
