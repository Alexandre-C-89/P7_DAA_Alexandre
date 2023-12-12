package com.example.p7_daa_alexandre.ui.list;

import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.p7_daa_alexandre.databinding.FragmentListBinding;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private RestaurantAdapter adapter;

    private List<Restaurant> restaurants = new ArrayList<>();

    private ListViewModel viewModel;

    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        initRecyclerViews();
    }

    private void initRecyclerViews() {
        adapter = new RestaurantAdapter(restaurants);
        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Ici, tu peux gérer les clics sur les éléments de la liste
                // Avant la suppression ou effectuer d'autres actions si nécessaire
                //deleteMeeting(position);
            }
        });

        RecyclerView recyclerView = binding.listRestaurants;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}