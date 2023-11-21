package com.example.p7_daa_alexandre.model;

import androidx.annotation.Nullable;

public class Coworker {

    private String idCoworker;
    private String name;
    private String email;
    private Boolean isNotificationActive;
    @Nullable
    private String urlPicture;
    public Coworker() {
    }

    public Coworker(String idCoworker, String name, String email, String urlPicture, Boolean isNotificationActive) {
        this.idCoworker = idCoworker;
        this.name = name;
        this.email = email;
        this.urlPicture = urlPicture;
        this.isNotificationActive=isNotificationActive;
    }
    public Coworker(String idCoworker, String name, String email, String urlPicture) {
        this.idCoworker = idCoworker;
        this.name = name;
        this.email = email;
        this.urlPicture = urlPicture;
        this.isNotificationActive=true;
    }

    // --- GETTERS ---

    public String getName() {
        return name;
    }
    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }
    public String getEmail() {
        return email;
    }

    public String getIdWorkmate() {
        return idCoworker;
    }

    public Boolean getIsNotificationActive() {
        return isNotificationActive;
    }

    // --- SETTERS ---
    public void setIdCoworker(String idCoworker) {
        this.idCoworker = idCoworker;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void getIsNotificationActive(Boolean isNotificationActive) {
        isNotificationActive = isNotificationActive;
    }

}
