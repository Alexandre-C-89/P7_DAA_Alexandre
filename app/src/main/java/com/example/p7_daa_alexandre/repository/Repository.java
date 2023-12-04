package com.example.p7_daa_alexandre.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.model.RestaurantsResponse;
import com.google.android.gms.common.api.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;


public class Repository {
    private final RestaurantApi restaurantApi;

    // In this Map we will store the responses we get from the server (corresponding to their page number),
    // so if needed we can "get back in time", this will act like a cache !
    // (Check 'caching' on Google or ask your mentor for more information)
    private final Map<Integer, RestaurantsResponse> alreadyFetchedResponses = new HashMap<>();

    public Repository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
    }

    public LiveData<List<Restaurant>> getRestaurantsLiveData() {
        MutableLiveData<List<Restaurant>> restaurantsMutableLiveData = new MutableLiveData<>();

        // Check in our cache if we already queried and stored the response

        RestaurantsResponse response = alreadyFetchedResponses.get();

        if (response != null) {
            // We already have the response (because we already queried this page in the past) ! No need to call the api !
            restaurantsMutableLiveData.setValue(response.getRestaurants());
        } else {
            // First time this page is queried, let's call the server ('enqueue()' makes the request on another thread)...
            restaurantApi.getListOfRestaurants().enqueue(new Callback<RestaurantsResponse>() {
                @Override
                public void onResponse(@NonNull Call<RestaurantsResponse> call, @NonNull Response<RestaurantsResponse> response) {
                    if (response.body() != null) {
                        // ... and once we have the result, we store it in our Map for potential future use !
                        alreadyFetchedResponses.put(response.body());

                        // Publish the result to the LiveData, we can use 'setValue()' instead of 'postValue()'
                        // because Retrofit goes back to the Main Thread once the query is finished !
                        restaurantsMutableLiveData.setValue(response.body().getCatFacts());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantsResponse> call, @NonNull Throwable t) {
                    restaurantsMutableLiveData.setValue(null);
                }
            });
        }

        return restaurantsMutableLiveData;
    }

    public LiveData<List<Coworker>> getCoworkers() {

    }

}
