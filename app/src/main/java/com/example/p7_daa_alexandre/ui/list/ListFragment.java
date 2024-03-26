package com.example.p7_daa_alexandre.ui.list;

import static com.example.p7_daa_alexandre.ui.list.RestaurantAdapter.userLocation;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.databinding.FragmentListBinding;
import com.example.p7_daa_alexandre.repository.LocationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private RestaurantAdapter adapter;

    private ArrayList<ResultsItem> restaurants = new ArrayList<>();

    private ListViewModel viewModel;

    private Map<String, Integer> restaurantLikesMap = new HashMap<>();

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

        LocationRepository locationRepository = new LocationRepository(getContext());
        viewModel = new ViewModelProvider(this, new ViewModelFactory(locationRepository)).get(ListViewModel.class);

        initRecyclerViews();

        loadData();
        Log.d("ListFragment", "Retrieving restaurant data from view model");
    }

    private void loadData() {
        viewModel.loadData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ResultsItem>>() {
            @Override
            public void onChanged(ArrayList<ResultsItem> resultsItems) {
                restaurants.clear();
                restaurants.addAll(resultsItems);
                updateRestaurantLikes();
                if (restaurants.isEmpty()) {
                    binding.listRestaurants.setVisibility(View.GONE);
                    binding.textViewNoRestaurant.setVisibility(View.VISIBLE);
                } else {
                    binding.listRestaurants.setVisibility(View.VISIBLE);
                    binding.textViewNoRestaurant.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updateRestaurantLikes() {
        for (ResultsItem restaurant : restaurants) {
            viewModel.getCoworkerWhoChoseRestaurant(restaurant.getPlaceId()).observe(getViewLifecycleOwner(), coworkers -> {
                restaurantLikesMap.put(restaurant.getPlaceId(), coworkers.size());
                adapter.setLikesMap(restaurantLikesMap); // Pass the updated map to the adapter
            });
        }
    }

    private void initRecyclerViews() {
        adapter = new RestaurantAdapter(restaurants, restaurantLikesMap, userLocation);
        RecyclerView recyclerView = binding.listRestaurants;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getLastKnownLocation().observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                // Now pass this location to your adapter
                adapter = new RestaurantAdapter(restaurants, restaurantLikesMap, location);
                binding.listRestaurants.setAdapter(adapter);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void updateRestaurantList(ArrayList<ResultsItem> results) {
        // Mettez à jour votre interface utilisateur avec les nouveaux résultats
    }

}