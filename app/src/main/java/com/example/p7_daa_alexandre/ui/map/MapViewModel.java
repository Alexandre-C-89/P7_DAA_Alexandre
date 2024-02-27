package com.example.p7_daa_alexandre.ui.map;

import android.Manifest;
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

    public Task<Location> getLastKnownLocation() {
        // Vérifiez si la permission ACCESS_FINE_LOCATION est accordée
        if (ContextCompat.checkSelfPermission(application,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return fusedLocationProviderClient.getLastLocation();
        } else {
            // Gérer le cas où la permission n'est pas accordée
            return Tasks.forException(new SecurityException("Permission ACCESS_FINE_LOCATION not granted"));
        }
    }
}
