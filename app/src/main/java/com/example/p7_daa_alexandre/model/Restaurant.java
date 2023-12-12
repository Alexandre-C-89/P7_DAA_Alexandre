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

    public String getIdR() {
        return idR;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
