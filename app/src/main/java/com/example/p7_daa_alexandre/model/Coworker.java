package com.example.p7_daa_alexandre.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Coworker {

    private String idCoworker;

    private String picture;
    private String name;
    private String email;

    private String placeId;

    private String restaurantName;

    private String address;

    private Boolean notification;

    private List<String> like;

    public Coworker() {}

    public Coworker(String idCoworker, String name, String email, String picture, String placeId, String restaurantName, String address, Boolean notification, List<String> like) {
        this.idCoworker = idCoworker;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.notification = notification;
        this.like = like;
    }

    // --- GETTERS ---

    public String getName() {
        return name;
    }
    @Nullable
    public String getPicture() {
        return picture;
    }
    public String getEmail() {
        return email;
    }

    public String getIdWorkmate() {
        return idCoworker;
    }

    public List<String> getLike() {
        return like;
    }

    public String getIdCoworker() {
        return idCoworker;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getNotification() {
        return notification;
    }

    // --- SETTERS ---
    public void setIdCoworker(String idCoworker) {
        this.idCoworker = idCoworker;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPicture(@Nullable String picture) {
        this.picture = picture;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

}
