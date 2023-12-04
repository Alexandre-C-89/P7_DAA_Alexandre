package com.example.p7_daa_alexandre.database;

import com.example.p7_daa_alexandre.model.CatFactsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatFactsApi {
        @GET("facts")
        Call<CatFactsResponse> getListOfCats(@Query("page") int page);

        @GET("restaurants")
        Call<CatFactsResponse> getListOfCats(@Query("restaurants") int list);

}