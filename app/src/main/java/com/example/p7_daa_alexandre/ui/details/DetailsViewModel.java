package com.example.p7_daa_alexandre.ui.details;

import com.example.p7_daa_alexandre.repository.Repository;
public class DetailsViewModel {

    private final Repository repository ;


    public DetailsViewModel() {
        repository = new Repository();
    }
    public MutableLiveData<ArrayList<RestaurantDetails>> getRestaurantDetails() {
        return repository.getRestaurantDetails();
    }

}
