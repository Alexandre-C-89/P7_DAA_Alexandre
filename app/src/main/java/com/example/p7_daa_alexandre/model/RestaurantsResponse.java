package com.example.p7_daa_alexandre.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class RestaurantsResponse {

    @SerializedName("current_page")
    @Expose
    private final Integer currentPage;
    @SerializedName("data")
    @Expose
    private final List<Restaurant> restaurants;
    @SerializedName("first_page_url")
    @Expose
    private final String firstPageUrl;
    @SerializedName("from")
    @Expose
    private final Integer from;
    @SerializedName("last_page")
    @Expose
    private final Integer lastPage;
    @SerializedName("last_page_url")
    @Expose
    private final String lastPageUrl;
    /**@SerializedName("links")
    @Expose
    private final List<Link> links;*/
    @SerializedName("next_page_url")
    @Expose
    private final String nextPageUrl;
    @SerializedName("path")
    @Expose
    private final String path;
    @SerializedName("per_page")
    @Expose
    private final Integer perPage;
    @SerializedName("prev_page_url")
    @Expose
    private final String prevPageUrl;
    @SerializedName("to")
    @Expose
    private final Integer to;
    @SerializedName("total")
    @Expose
    private final Integer total;

    public RestaurantsResponse(Integer currentPage, List<Restaurant> restaurants, String firstPageUrl, Integer from, Integer lastPage, String lastPageUrl, String nextPageUrl, String path, Integer perPage, String prevPageUrl, Integer to, Integer total) {
        this.currentPage = currentPage;
        this.restaurants = restaurants;
        this.firstPageUrl = firstPageUrl;
        this.from = from;
        this.lastPage = lastPage;
        this.lastPageUrl = lastPageUrl;
        //this.links = links;
        this.nextPageUrl = nextPageUrl;
        this.path = path;
        this.perPage = perPage;
        this.prevPageUrl = prevPageUrl;
        this.to = to;
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public Integer getTo() {
        return to;
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantsResponse that = (RestaurantsResponse) o;
        return Objects.equals(currentPage, that.currentPage) &&
                Objects.equals(restaurants, that.restaurants) &&
                Objects.equals(firstPageUrl, that.firstPageUrl) &&
                Objects.equals(from, that.from) &&
                Objects.equals(lastPage, that.lastPage) &&
                Objects.equals(lastPageUrl, that.lastPageUrl) &&
                Objects.equals(nextPageUrl, that.nextPageUrl) &&
                Objects.equals(path, that.path) &&
                Objects.equals(perPage, that.perPage) &&
                Objects.equals(prevPageUrl, that.prevPageUrl) &&
                Objects.equals(to, that.to) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, restaurants, firstPageUrl, from, lastPage, lastPageUrl, nextPageUrl, path, perPage, prevPageUrl, to, total);
    }

    @NonNull
    @Override
    public String toString() {
        return "CatFactsResponse{" +
                "currentPage=" + currentPage +
                ", restaurants=" + restaurants +
                ", firstPageUrl='" + firstPageUrl + '\'' +
                ", from=" + from +
                ", lastPage=" + lastPage +
                ", lastPageUrl='" + lastPageUrl + '\'' +
                ", nextPageUrl='" + nextPageUrl + '\'' +
                ", path='" + path + '\'' +
                ", perPage=" + perPage +
                ", prevPageUrl=" + prevPageUrl +
                ", to=" + to +
                ", total=" + total +
                '}';
    }

}
