package com.example.p7_daa_alexandre.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.NearbysearchResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final MutableLiveData<ArrayList<ResultsItem>> allRestaurant;
    private final ArrayList<ResultsItem> restaurantList;
    private RestaurantApi restaurantApi;

    //private static final String API_KEY = BuildConfig.API_KEY;

    private MutableLiveData<DetailsResponse> restaurantDetails;

    private final MutableLiveData<ArrayList<ResultsItem>> searchResults;


    public Repository() {
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();
        restaurantApi = RetrofitService.getInstance().getRestaurantDetails();
        restaurantDetails = new MutableLiveData<>();
        searchResults = new MutableLiveData<>();
    }

    public LiveData<ArrayList<ResultsItem>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<ArrayList<ResultsItem>> callAPI() {

        Call<NearbysearchResponse> call = restaurantApi.getListOfRestaurants("48.936752,2.425377", 1500, "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");
        call.enqueue(new Callback<NearbysearchResponse>() {
            @Override
            public void onResponse(Call<NearbysearchResponse> call, Response<NearbysearchResponse> response) {
                if (response.isSuccessful()) {
                    restaurantList.clear();
                    List<ResultsItem> fetchedResults = response.body().getResults();
                    for (ResultsItem item : fetchedResults) {
                        item.setRating(3.0);
                    }
                    restaurantList.addAll(fetchedResults);
                }
                allRestaurant.setValue(restaurantList);
            }

            @Override
            public void onFailure(Call<NearbysearchResponse> call, Throwable t) {
                System.out.println(R.string.repository_system_message_println + t.getMessage());
            }
        });
        return allRestaurant;
    }

    public MutableLiveData<DetailsResponse> getRestaurantDetails(String placeId) {
        Call<DetailsResponse> call = restaurantApi.getRestaurantDetails(placeId, "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful()) {
                    restaurantDetails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Log.d("Erreur de la requête !", t.getStackTrace().toString());
            }
        });
        return restaurantDetails;
    }

    ;

    public void searchRestaurant(String query) {
        Call<NearbysearchResponse> call = restaurantApi.searchRestaurants(query, String.valueOf(R.string.repository_location_position_lebourget), "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");
        call.enqueue(new Callback<NearbysearchResponse>() {
            @Override
            public void onResponse(Call<NearbysearchResponse> call, Response<NearbysearchResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<ResultsItem> results = (ArrayList<ResultsItem>) response.body().getResults();
                    searchResults.setValue(results);
                } else {
                    searchResults.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<NearbysearchResponse> call, Throwable t) {
                searchResults.setValue(new ArrayList<>());
            }
        });
    }

    public LiveData<ArrayList<ResultsItem>> getNearbyRestaurants(double lat, double lng) {
        // Example API call with Retrofit
        String locationParam = lat + "," + lng; // Format the location query parameter
        restaurantApi.getListOfRestaurants(locationParam, 1500, "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow").enqueue(new Callback<NearbysearchResponse>() {
            @Override
            public void onResponse(Call<NearbysearchResponse> call, Response<NearbysearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allRestaurant.postValue(new ArrayList<>(response.body().getResults()));
                } else {
                    allRestaurant.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<NearbysearchResponse> call, Throwable t) {
                allRestaurant.postValue(new ArrayList<>());
            }
        });

        return allRestaurant;
    }

}
