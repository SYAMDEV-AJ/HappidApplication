package com.example.happidapplication.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitRequest {

    @SerializedName("firstname")
    @Expose
    private String Firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;


    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("postalcode")
    @Expose
    private String postalcode;


    @SerializedName("imagestring")
    @Expose
    private String imagestring;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getImagestring() {
        return imagestring;
    }

    public void setImagestring(String imagestring) {
        this.imagestring = imagestring;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
}
