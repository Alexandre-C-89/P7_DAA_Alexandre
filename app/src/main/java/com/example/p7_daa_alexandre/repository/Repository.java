package com.example.p7_daa_alexandre.repository;

import android.util.Log;

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

    private MutableLiveData<DetailsResponse> restaurantDetails;


    public Repository() {
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();
        restaurantApi = RetrofitService.getInstance().getRestaurantDetails();
        restaurantDetails = new MutableLiveData<>();
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
        Call<DetailsResponse> call = restaurantApi.getRestaurantDetails(placeId , "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");

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
    };

    public MutableLiveData<ArrayList<ResultsItem>> searchRestaurant(String query) {
        MutableLiveData<ArrayList<ResultsItem>> searchResults = new MutableLiveData<>();

        // Appeler l'API de recherche de restaurants avec la chaîne de requête (query)
        Call<NearbysearchResponse> call = restaurantApi.searchRestaurants(query, "AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow");

        call.enqueue(new Callback<NearbysearchResponse>() {
            @Override
            public void onResponse(Call<NearbysearchResponse> call, Response<NearbysearchResponse> response) {
                if (response.isSuccessful()) {
                    // Si la réponse est réussie, mettez à jour les résultats de recherche MutableLiveData avec les résultats de la réponse
                    ArrayList<ResultsItem> results = (ArrayList<ResultsItem>) response.body().getResults();
                    searchResults.setValue(results);
                } else {
                    // Si la réponse n'est pas réussie, définissez les résultats de recherche MutableLiveData sur null ou une liste vide selon votre besoin
                    searchResults.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<NearbysearchResponse> call, Throwable t) {
                // En cas d'échec de la requête, définissez les résultats de recherche MutableLiveData sur null ou une liste vide selon votre besoin
                searchResults.setValue(new ArrayList<>());
            }
        });

        return searchResults;
    }

}
