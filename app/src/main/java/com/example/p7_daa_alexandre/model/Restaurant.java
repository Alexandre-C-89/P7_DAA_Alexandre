package com.example.p7_daa_alexandre.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Restaurant implements Serializable {

    private String idR;
    @SerializedName("restaurant_name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("restaurant_type")
    private String type;
    @SerializedName("picture")
    private String urlPicture;
    @SerializedName("website")
    private String webSite;
    @SerializedName("address")
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

}
