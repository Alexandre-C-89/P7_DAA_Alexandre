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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

public class MapViewModel extends ViewModel {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private MutableLiveData<Location> lastKnownLocation = new MutableLiveData<>();
    private Application application;

    public MapViewModel(Application application) {
        this.application = application;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application);
    }
    public LiveData<Location> getLastKnownLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return lastKnownLocation;
            }
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    lastKnownLocation.setValue(location);
                }
            });
        } catch (SecurityException e) {
            Log.d("VIEWMODEL", "Lost last position !" + e.getMessage());
        }
        return lastKnownLocation;
    }



}
