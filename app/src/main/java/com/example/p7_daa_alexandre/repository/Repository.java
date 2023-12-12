package com.example.p7_daa_alexandre.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.model.RestaurantsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {
    private final MutableLiveData<ArrayList<Restaurant>> allRestaurant;
    private final ArrayList<Restaurant> restaurantList;


    public Repository(Application application) { //application is subclass of context

        //cant call abstract func but since instance is there we can do this
        restaurantList = new ArrayList<>();
        allRestaurant = new MutableLiveData<>();


    }

    public MutableLiveData<ArrayList<Restaurant>> callAPI(){


        Call<ResponseBody> call = RetrofitService.getInstance().getRestaurantApi().getListOfRestaurants();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200) {

                    try {
                        assert response.body() != null;

                        JSONArray dataArray = new JSONArray(response.body().string());

                        for (int i = 0; i < dataArray.length(); i++) {

                            Restaurant modelRecycler = new Restaurant();
                            JSONObject dataobj = dataArray.getJSONObject(i);

                            modelRecycler.setName(dataobj.getString("name"));
                            modelRecycler.setRegion(dataobj.getString("region"));
                            modelRecycler.setCapital(dataobj.getString("capital"));
                            modelRecycler.setFlag(dataobj.getString("flag"));

                            modelRecycler.setSubregion(dataobj.getString("subregion"));
                            modelRecycler.setPopulation(dataobj.getLong("population"));
                            modelRecycler.setBorders(dataobj.getJSONArray("borders"));

                            modelRecycler.setLanguages(dataobj.getJSONArray("languages"));
                            countryList.add(modelRecycler);

                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                    }
                    allRestaurant.setValue(restaurantList);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //failed
                allRestaurant.postValue(null);
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        return allRestaurant;

    }

}
