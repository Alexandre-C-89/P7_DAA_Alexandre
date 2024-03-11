package com.example.p7_daa_alexandre.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.MapViewModelFactory;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    private MapViewModel viewModel;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        MapViewModelFactory factory = new MapViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(MapViewModel.class);

        requestLocationPermission();

        return binding.getRoot();
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
                // Permission accordée, vous pouvez maintenant afficher la carte et mettre à jour l'UI
                showMap(); // Méthode pour afficher la carte dans MapFragment
            } else {
                // Permission refusée, handle accordingly
                Toast.makeText(requireContext(), "Permission de localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        viewModel.getLastKnownLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Log.d("LOCATION", location.toString());
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
                googleMap.addMarker(new MarkerOptions()
                        .position(currentPosition)
                        .title("Current Location"));
            }
        }).addOnFailureListener(e -> {
            // Handle failure to get location
            Log.e("MapFragment", "Error getting last known location: " + e.getMessage());
        });
    };

    public void showMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(this);
    }


    public void updateRestaurantList(ArrayList<ResultsItem> results) {
        // Mettez à jour votre interface utilisateur avec les nouveaux résultats
    }

}