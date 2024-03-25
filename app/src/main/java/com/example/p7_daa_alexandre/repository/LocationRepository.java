package com.example.p7_daa_alexandre.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class LocationRepository {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private MutableLiveData<Location> lastKnownLocation = new MutableLiveData<>();

    public LocationRepository(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public LiveData<Location> getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                fusedLocationProviderClient.getApplication().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        fusedLocationProviderClient.getApplication().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permissions are not granted, so we return the current value of lastKnownLocation, which is likely null
            return lastKnownLocation;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations, this can be null.
                if (location != null) {
                    // Logic to handle location object
                    lastKnownLocation.setValue(location);
                }
            }
        }).addOnFailureListener(e -> Log.e("LocationRepository", "Error getting location", e));

        return lastKnownLocation;
    }

}
