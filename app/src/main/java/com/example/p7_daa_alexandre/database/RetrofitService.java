package com.example.p7_daa_alexandre.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {

    private static final Gson gson = new GsonBuilder().setLenient().create();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    /**
     * Url à changer selon l'API que je veux utiliser
     */
    //private static final String BASE_URL = "https://catfact.ninja/";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static RetrofitService instance;
    private Retrofit retrofit;

    private RetrofitService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitService getInstance() {
        if (instance == null) {
            instance = new RetrofitService();
        }
        return instance;
    }

    /**private static final Retrofit retrofit = new Retrofit.Builder()
     .baseUrl(BASE_URL)
     .client(httpClient)
     .addConverterFactory(GsonConverterFactory.create(gson))
     .build();*/

    /**
     * public static CatFactsApi getCatApi() {
     * return retrofit.create(CatFactsApi.class);
     * }
     */

    public RestaurantApi getRestaurantApi() {
        return retrofit.create(RestaurantApi.class);
    }

}