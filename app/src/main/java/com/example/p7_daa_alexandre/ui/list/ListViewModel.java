package com.example.p7_daa_alexandre.ui.list;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {

    private final Repository repository ;

    private final CoworkerRepository coworkerRepository ;

    private LiveData<Location> lastKnownLocation;


    public ListViewModel(LocationRepository locationRepository) {
        repository = new Repository();
        coworkerRepository = new CoworkerRepository();
        lastKnownLocation = locationRepository.getLastLocation();
    }
    public MutableLiveData<ArrayList<ResultsItem>> loadData() {
        return repository.callAPI();
    }

    public LiveData<List<Coworker>> getCoworkerWhoChoseRestaurant(String placeId) {
        return coworkerRepository.getCoworkerWhoChoseRestaurant(placeId);
    }

    public LiveData<Location> getLastKnownLocation() {
        return lastKnownLocation;
    }

}
