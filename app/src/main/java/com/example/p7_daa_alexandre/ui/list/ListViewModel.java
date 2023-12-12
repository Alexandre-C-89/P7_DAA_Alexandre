package com.example.p7_daa_alexandre.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ListViewModel extends ViewModel {

    private final RestaurantApi repository ;


    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new ListViewModel(application);

    }
    public MutableLiveData<ArrayList<Restaurant>> loadData() {
        return repository.getListOfRestaurants();
    }

}
