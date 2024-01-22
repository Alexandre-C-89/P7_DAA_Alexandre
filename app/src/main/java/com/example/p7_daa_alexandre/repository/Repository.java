package com.example.p7_daa_alexandre.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.NearbysearchResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final MutableLiveData<ArrayList<ResultsItem>> allRestaurant;
    private final ArrayList<ResultsItem> restaurantList;
    private RestaurantApi restaurantApi;

    //private static final String API_KEY = BuildConfig.API_KEY;

    private final MutableLiveData<DetailsResponse> restaurantDetails;


    public Repository() { //application is subclass of context

        //cant call abstract func but since instance is there we can do this
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();
        restaurantApi = RetrofitService.getInstance().getRestaurantDetails();
        restaurantDetails = (MutableLiveData<DetailsResponse>) restaurantApi.getRestaurantDetails("1", "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");

    }

    public MutableLiveData<ArrayList<ResultsItem>> callAPI() {

        Call<NearbysearchResponse> call = restaurantApi.getListOfRestaurants("48.936752,2.425377", 1500, "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");
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

    public MutableLiveData<DetailsResponse> getRestaurantDetails(String placeId){
        Call<DetailsResponse> call = restaurantApi.getRestaurantDetails(placeId , "AIzaSyBKc-guxXiTa3i-JcZVWNffI8Cfd64U2jY");

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
