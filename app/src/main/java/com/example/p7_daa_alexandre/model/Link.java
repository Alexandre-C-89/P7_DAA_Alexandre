package com.example.p7_daa_alexandre.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Link {

    @SerializedName("url")
    @Expose
    private final String url;
    @SerializedName("label")
    @Expose
    private final String label;
    @SerializedName("active")
    @Expose
    private final Boolean active;

    public Link(String url, String label, Boolean active) {
        this.url = url;
        this.label = label;
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(url, link.url) &&
                Objects.equals(label, link.label) &&
                Objects.equals(active, link.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, label, active);
    }

    @NonNull
    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", active=" + active +
                '}';
    }
}
