package com.example.p7_daa_alexandre.database;

import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.NearbysearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantApi {

    @GET("details/json")
    Call <DetailsResponse> getDetailsRestaurant();// Passer des paramètres pour les détails

    @GET("nearbysearch/json?type=restaurant")
    Call <NearbysearchResponse>getListOfRestaurants(
            @Query("location") String location,
            @Query("radius") int radius,
            //@Query("type") String type,
            @Query("key") String apiKey
    );
}
