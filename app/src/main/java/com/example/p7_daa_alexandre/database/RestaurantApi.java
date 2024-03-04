package com.example.p7_daa_alexandre.database;

import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.NearbysearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantApi {

    @GET("details/json")
    Call <DetailsResponse> getRestaurantDetails(
            // placeId
            @Query("place_id") String placeId,
            // Key
            @Query("key") String apiKey
    );

    @GET("nearbysearch/json?type=restaurant")
    Call <NearbysearchResponse>getListOfRestaurants(
            @Query("location") String location,
            @Query("radius") int radius,
            //@Query("type") String type,
            @Query("key") String apiKey
    );

    @GET("textsearch/json")
    Call<NearbysearchResponse> searchRestaurants(
            @Query("query") String query,
            @Query("key") String key
    );

}
