package com.example.p7_daa_alexandre.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Restaurant implements Serializable {

    private String idR;
    private String name;
    private String phone;
    private Float rating;
    private String type;
    private String urlPicture;
    private String webSite;
    private String address;

    private String hourClosed;

    public Restaurant(String name, String phone, Float rating, String type, String picture, String website, String address) {
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.type = type;
        this.urlPicture = picture;
        this.webSite = website;
        this.address = address;
    }


    // -- SETTER --

    public String setName(String name) {
        return this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setHourClosed(String hourClosed) {
        this.hourClosed = hourClosed;
    }

    public void setIdR(String idR) {
        this.idR = idR;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // -- GETTER --

    public String getIdR() {
        return idR;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Float getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getAddress() {
        return address;
    }

    public String getHourClosed() {
        return hourClosed;
    }
}
