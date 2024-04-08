package com.example.p7_daa_alexandre.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.MapViewModelFactory;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.databinding.FragmentMapBinding;
import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import com.example.p7_daa_alexandre.ui.list.ListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    private MapViewModel viewModel;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private GoogleMap googleMap;

    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);

        LocationRepository locationRepository = new LocationRepository(requireContext().getApplicationContext());
        Repository repository = new Repository();

        MapViewModelFactory factory = new MapViewModelFactory(locationRepository, repository);
        viewModel = new ViewModelProvider(this, factory).get(MapViewModel.class);

        requestLocationPermission();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, new ViewModelFactory(getActivity().getApplicationContext())).get(MapViewModel.class);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            showMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showMap();
            } else {
                Toast.makeText(requireContext(), "Permission de localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            viewModel.getLastKnownLocation().observe(getViewLifecycleOwner(), location -> {
                if (location != null) {
                    LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
                    viewModel.getNearbyRestaurants(location.getLatitude(), location.getLongitude()).observe(getViewLifecycleOwner(), restaurants -> {
                        for (ResultsItem restaurant : restaurants) {
                            LatLng restaurantPosition = new LatLng((Double) restaurant.getGeometry().getLocation().getLat(), (Double) restaurant.getGeometry().getLocation().getLng());
                            googleMap.addMarker(new MarkerOptions().position(restaurantPosition).title(restaurant.getName()));
                        }
                    });
                }
            });
        } else {
            requestLocationPermission();
        }
    }

    public void showMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(this);
    }



    public void searchRestaurants(String query) {
        viewModel.searchRestaurants(query).observe(getViewLifecycleOwner(), restaurants -> {
            googleMap.clear(); // Clear existing markers
            for (ResultsItem restaurant : restaurants) {
                LatLng restaurantPosition = new LatLng(restaurant.getGeometry().getLocation().getLat(), restaurant.getGeometry().getLocation().getLng());
                googleMap.addMarker(new MarkerOptions().position(restaurantPosition).title(restaurant.getName()));
            }
        });
    }

}