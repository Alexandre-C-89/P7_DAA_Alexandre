package com.example.p7_daa_alexandre.database;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static RetrofitService instance;
    private Retrofit retrofit;

    private RetrofitService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new NetworkInterceptor()) // Add your custom network interceptor here
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitService getInstance() {
        if (instance == null) {
            instance = new RetrofitService();
        }
        return instance;
    }

    public RestaurantApi getRestaurantDetails() {
        return retrofit.create(RestaurantApi.class);
    }

}