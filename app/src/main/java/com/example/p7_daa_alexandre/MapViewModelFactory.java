package com.example.p7_daa_alexandre;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import com.example.p7_daa_alexandre.ui.map.MapViewModel;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private final LocationRepository locationRepository;
    private final Repository repository;

    public MapViewModelFactory(LocationRepository locationRepository, Repository repository) {
        this.locationRepository = locationRepository;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(locationRepository, repository);
        }
        throw new IllegalArgumentException(String.valueOf(R.string.map_viewmodel_message_error));
    }

}