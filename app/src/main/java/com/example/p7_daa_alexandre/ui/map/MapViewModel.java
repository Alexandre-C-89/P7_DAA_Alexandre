package com.example.p7_daa_alexandre.ui.map;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {

    private LiveData<Location> lastKnownLocation;
    private LiveData<ArrayList<ResultsItem>> nearbyRestaurants;
    private Repository repository;

    public MapViewModel(LocationRepository locationRepository, Repository repository) {
        this.lastKnownLocation = locationRepository.getLastLocation();
        this.repository = repository;
    }
    public LiveData<Location> getLastKnownLocation() {
        return lastKnownLocation;
    }

    public LiveData<ArrayList<ResultsItem>> getNearbyRestaurants(double lat, double lng) {
        nearbyRestaurants = repository.getNearbyRestaurants(lat, lng);
        return nearbyRestaurants;
    }

}
