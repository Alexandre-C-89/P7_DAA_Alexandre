package com.example.p7_daa_alexandre;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.ui.map.MapViewModel;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public MapViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}