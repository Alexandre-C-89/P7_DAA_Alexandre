package com.example.p7_daa_alexandre.database;

import androidx.lifecycle.LiveData;
import com.example.p7_daa_alexandre.model.Restaurant;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantApi {
    @GET("restaurants")
    LiveData<List<Restaurant>> getListOfRestaurants();

}
