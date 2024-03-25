package com.example.p7_daa_alexandre.ui.map;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

public class MapViewModel extends ViewModel {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LiveData<Location> lastKnownLocation;
    private Application application;

    public MapViewModel(LocationRepository locationRepository) {
        this.lastKnownLocation = locationRepository.getLastLocation();
    }
    public LiveData<Location> getLastKnownLocation() {
        return lastKnownLocation;
    }

}
