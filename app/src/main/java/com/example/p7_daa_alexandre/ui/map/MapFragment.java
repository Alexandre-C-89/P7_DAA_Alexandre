package com.example.p7_daa_alexandre.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(MapFragment.this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng leBourget = new LatLng(48.936752, 2.425377);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leBourget, 15));
        googleMap.addMarker(new MarkerOptions()
                .position(leBourget)
                .title("Marker in Le bourget"));
    }


}