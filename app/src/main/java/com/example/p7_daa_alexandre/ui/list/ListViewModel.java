package com.example.p7_daa_alexandre.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.List;
import java.util.concurrent.Executor;

public class ListViewModel extends ViewModel {

    private final Repository repository;
    //private final Executor executor;

    public ListViewModel(Repository dataSource) {
        this.repository = dataSource;
        //this.executor = executor;
    }

    /**
     * Liste de meeting
     */
    public LiveData<List<Restaurant>> getRestaurants() {
        return repository.getRestaurantsLiveData();
    }

}
