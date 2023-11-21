package com.example.p7_daa_alexandre.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String idR;
    private String name;
    @Nullable
    private String phone;
    private Float rating;
    @Nullable
    private String type;
    @Nullable
    private String urlPicture;
    @Nullable
    private String webSite;
    @Nullable
    private String address;

    private String hourClosed;

    public Restaurant() {
    }

    public Restaurant(String idR, String name, @Nullable String phone, Float rating, @Nullable String type, @Nullable String urlPicture, @Nullable String webSite, @Nullable String address, String hourClosed, boolean isChoosedForLunchToday) {
        this.idR = idR;
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.type = type;
        this.urlPicture = urlPicture;
        this.webSite = webSite;
        this.address = address;
        this.hourClosed = hourClosed;
    }

    public Restaurant(String idR, String name, @Nullable String phone, Float rating, @Nullable String type, @Nullable String urlPicture, @Nullable String webSite, @Nullable String address, String hourClosed) {
        this.idR=idR;
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.type = type;
        this.urlPicture = urlPicture;
        this.webSite = webSite;
        this.address = address;
        this.hourClosed = hourClosed;
    }

    // --- GETTERS ---
    public String getIdR() {
        return idR;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public Float getRating() {
        return rating;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    @Nullable
    public String getWebSite() {
        return webSite;
    }
    // --- SETTERS ---
    public void setIdR(String idR) {
        this.idR = idR;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public void setWebSite(@Nullable String webSite) {
        this.webSite = webSite;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public String getHourClosed() {
        return hourClosed;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public void setHourClosed(String hourClosed) {
        this.hourClosed = hourClosed;
    }

}
