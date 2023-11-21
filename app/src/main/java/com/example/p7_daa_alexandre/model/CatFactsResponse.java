package com.example.p7_daa_alexandre.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class CatFactsResponse {

    @SerializedName("current_page")
    @Expose
    private final Integer currentPage;
    @SerializedName("data")
    @Expose
    private final List<CatFact> catFacts;
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
    @SerializedName("links")
    @Expose
    private final List<Link> links;
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

    public CatFactsResponse(Integer currentPage, List<CatFact> catFacts, String firstPageUrl, Integer from, Integer lastPage, String lastPageUrl, List<Link> links, String nextPageUrl, String path, Integer perPage, String prevPageUrl, Integer to, Integer total) {
        this.currentPage = currentPage;
        this.catFacts = catFacts;
        this.firstPageUrl = firstPageUrl;
        this.from = from;
        this.lastPage = lastPage;
        this.lastPageUrl = lastPageUrl;
        this.links = links;
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

    public List<CatFact> getCatFacts() {
        return catFacts;
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

    public List<Link> getLinks() {
        return links;
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
        CatFactsResponse that = (CatFactsResponse) o;
        return Objects.equals(currentPage, that.currentPage) &&
                Objects.equals(catFacts, that.catFacts) &&
                Objects.equals(firstPageUrl, that.firstPageUrl) &&
                Objects.equals(from, that.from) &&
                Objects.equals(lastPage, that.lastPage) &&
                Objects.equals(lastPageUrl, that.lastPageUrl) &&
                Objects.equals(links, that.links) &&
                Objects.equals(nextPageUrl, that.nextPageUrl) &&
                Objects.equals(path, that.path) &&
                Objects.equals(perPage, that.perPage) &&
                Objects.equals(prevPageUrl, that.prevPageUrl) &&
                Objects.equals(to, that.to) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, catFacts, firstPageUrl, from, lastPage, lastPageUrl, links, nextPageUrl, path, perPage, prevPageUrl, to, total);
    }

    @NonNull
    @Override
    public String toString() {
        return "CatFactsResponse{" +
                "currentPage=" + currentPage +
                ", catFacts=" + catFacts +
                ", firstPageUrl='" + firstPageUrl + '\'' +
                ", from=" + from +
                ", lastPage=" + lastPage +
                ", lastPageUrl='" + lastPageUrl + '\'' +
                ", links=" + links +
                ", nextPageUrl='" + nextPageUrl + '\'' +
                ", path='" + path + '\'' +
                ", perPage=" + perPage +
                ", prevPageUrl=" + prevPageUrl +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
