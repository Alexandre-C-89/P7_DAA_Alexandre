package com.example.p7_daa_alexandre.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.model.Restaurant;

import java.util.List;
import java.util.concurrent.Executor;

public class ListViewModel extends ViewModel {

    private final DataRepository dataRepository;
    private final Executor executor;

    public ListViewModel(DataRepository dataSource, Executor executor) {
        this.dataRepository = dataSource;
        this.executor = executor;
    }

    /**
     * Liste de meeting
     */
    public LiveData<List<Restaurant>> getRestaurants() {
        return dataRepository.getRestaurants();
    }

}
