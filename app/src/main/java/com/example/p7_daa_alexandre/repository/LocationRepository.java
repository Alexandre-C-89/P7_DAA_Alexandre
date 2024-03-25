package com.example.p7_daa_alexandre.repository;

import android.content.Context;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

public class LocationRepository {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private MutableLiveData<Location> lastKnownLocation = new MutableLiveData<>();

    private Context applicationContext;

    public LocationRepository(Context context) {
        this.applicationContext = context.getApplicationContext();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public LiveData<Location> getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        applicationContext,
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
