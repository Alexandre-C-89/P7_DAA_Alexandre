package com.example.p7_daa_alexandre.repository;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.NearbysearchResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final MutableLiveData<ArrayList<ResultsItem>> allRestaurant;
    private final ArrayList<ResultsItem> restaurantList;
    private RestaurantApi restaurantApi;

    private final MutableLiveData<DetailsResponse> restaurantDetails;

    // Define a Place ID.
    final String placeId = "INSERT_PLACE_ID_HERE";

    // Specify the fields to return.
    final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

    // Construct a request object, passing the place ID and fields array.
    final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);



    public Repository() { //application is subclass of context

        //cant call abstract func but since instance is there we can do this
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();
        restaurantApi = RetrofitService.getInstance().getRestaurantDetails();
        restaurantDetails = restaurantApi.getRestaurantDetails();

    }

    public MutableLiveData<ArrayList<ResultsItem>> callAPI() {

        Call<NearbysearchResponse> call = restaurantApi.getListOfRestaurants("48.936752,2.425377", 1500, "AIzaSyBKc-guxXiTa3i-JcZVWNffI8Cfd64U2jY");
        call.enqueue(new Callback<NearbysearchResponse>() {

            @Override
            public void onResponse(Call<NearbysearchResponse> call, Response<NearbysearchResponse> response) {

                if (response.isSuccessful()) {
                    restaurantList.clear();
                    restaurantList.addAll(response.body().getResults());
                }
                allRestaurant.setValue(restaurantList);

            }

            @Override
            public void onFailure(Call<NearbysearchResponse> call, Throwable t) {
                System.out.println("t.getMessage() = " + t.getMessage());
            }
        });
        return allRestaurant;
    }

    public MutableLiveData<DetailsResponse> getRestaurantDetails(){
        Call<DetailsResponse> call = restaurantApi.getRestaurantDetails(placeId = "", apiKey = "AIzaSyBKc-guxXiTa3i-JcZVWNffI8Cfd64U2jY");

        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful()) {
                    restaurantDetails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {

            }
        });
        return restaurantDetails;
    };

}
