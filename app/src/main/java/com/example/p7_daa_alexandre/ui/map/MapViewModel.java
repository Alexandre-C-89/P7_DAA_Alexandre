package com.example.p7_daa_alexandre.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

public class MapViewModel extends ViewModel {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Application application;

    public MapViewModel(Application application) {
        this.application = application;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application);
    }
    @SuppressLint("MissingPermission")
    public Task<Location> getLastKnownLocation() {
        return fusedLocationProviderClient.getLastLocation();
    }
}
