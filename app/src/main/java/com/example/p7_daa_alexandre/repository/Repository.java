package com.example.p7_daa_alexandre.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
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


    public Repository() { //application is subclass of context

        //cant call abstract func but since instance is there we can do this
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();
        restaurantApi = RetrofitService.getInstance().getRestaurantApi();

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

}
