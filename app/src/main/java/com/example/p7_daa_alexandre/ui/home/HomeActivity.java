package com.example.p7_daa_alexandre.ui.home;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p7_daa_alexandre.MainActivity;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.databinding.ActivityHomeBinding;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.ui.coworker.CoworkerFragment;
import com.example.p7_daa_alexandre.ui.details.DetailsActivity;
import com.example.p7_daa_alexandre.ui.list.ListFragment;
import com.example.p7_daa_alexandre.ui.list.RestaurantAdapter;
import com.example.p7_daa_alexandre.ui.map.MapFragment;
import com.example.p7_daa_alexandre.ui.settings.SettingsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    ActivityHomeBinding binding;
    HomeViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize variables
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(getApplicationContext())).get(HomeViewModel.class);
        replaceFragment(new MapFragment());
        setSupportActionBar(binding.toolbar);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                replaceFragment(new MapFragment());
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                replaceFragment(new MapFragment());
                            } else {
                                Toast.makeText(HomeActivity.this, R.string.toast_home_activity_message_fine_permission, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        }

        binding.toolbar.setNavigationOnClickListener(v -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.list:
                    replaceFragment(new ListFragment());
                    break;
                case R.id.coworker:
                    replaceFragment(new CoworkerFragment());
                    break;
            }
            return true;
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            // Vérifiez quel élément de menu a été sélectionné
            switch (item.getItemId()) {
                case R.id.your_lunch:
                    viewModel.getUserProfil().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Coworker coworker = documentSnapshot.toObject(Coworker.class);
                            if (coworker != null && coworker.getPlaceId() != null) {
                                Intent detailsIntent = new Intent(HomeActivity.this, DetailsActivity.class);
                                detailsIntent.putExtra("restaurant", coworker.getPlaceId());
                                startActivity(detailsIntent);
                            } else {
                                Toast.makeText(HomeActivity.this, R.string.toast_home_activity_message_restaurant_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
                case R.id.settings:
                    // Action for "settings"
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.log_out:
                    // Action for "Log Out"
                    LogOut();
                    break;
            }
            return true;
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_btn) {
            return true;
        }else if (id == R.id.sort_btn) {
            Log.d("SORTBUTTON", "BUTTON");
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
            if (currentFragment instanceof ListFragment) {
                ((ListFragment) currentFragment).sortResultsAlphabetically();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_btn);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tapez ici !");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
                if (currentFragment instanceof MapFragment) {
                    ((MapFragment) currentFragment).searchRestaurants(query);
                }else if (currentFragment instanceof ListFragment) {
                    ((ListFragment) currentFragment).searchRestaurants(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    private void LogOut() {
        viewModel.signOut(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(HomeActivity.this, R.string.toast_home_activity_message_disconnect_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                getUserLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                getUserLocation();
                            } else {
                                Toast.makeText(HomeActivity.this, R.string.toast_home_activity_message_fine_permission, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private void getUserLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        userLocation = location;
                        // Update ListFragment with user location
                        replaceFragment(ListFragment.newInstance(userLocation));
                    } else {
                        // Handle location not found scenario
                        replaceFragment(new ListFragment()); // Fallback in case location is not found
                    }
                });
    }

}