package com.example.p7_daa_alexandre.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class DetailsViewModel extends ViewModel {

    private final Repository repository ;
    private final CoworkerRepository coworkerRepository ;


    public DetailsViewModel() {
        repository = new Repository();
        coworkerRepository = new CoworkerRepository();
    }

    public MutableLiveData<DetailsResponse> getRestaurantDetails(String placeId) {
        return repository.getRestaurantDetails(placeId);
    }

    public LiveData<List<Coworker>> getCoworkersLikedRestaurant(String restaurantId) {
        return coworkerRepository.getCoworkersLikedRestaurant(restaurantId);
    }

    public void restaurantChoosed(String placeId, String restaurantName, String address) {
        coworkerRepository.restaurantChoosed(placeId, restaurantName, address);
    }

    public LiveData<List<Coworker>> getCoworkerWhoChoseRestaurant(String placeId) {
        return coworkerRepository.getCoworkerWhoChoseRestaurant(placeId);
    }

}
