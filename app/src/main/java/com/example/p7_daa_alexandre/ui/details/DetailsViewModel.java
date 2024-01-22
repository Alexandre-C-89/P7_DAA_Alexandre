package com.example.p7_daa_alexandre.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;

public class DetailsViewModel extends ViewModel {

    private final Repository repository ;


    public DetailsViewModel() {
        repository = new Repository();
    }

    public MutableLiveData<DetailsResponse> getRestaurantDetails(String placeId) {
        return repository.getRestaurantDetails(placeId);
    }

}
