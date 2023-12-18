package com.example.p7_daa_alexandre.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import dagger.hilt.android.lifecycle.HiltViewModel;

public class ListViewModel extends ViewModel {

    private final Repository repository ;


    public ListViewModel() {
        repository = new Repository();
    }
    public MutableLiveData<ArrayList<ResultsItem>> loadData() {
        return repository.callAPI();
    }

}
