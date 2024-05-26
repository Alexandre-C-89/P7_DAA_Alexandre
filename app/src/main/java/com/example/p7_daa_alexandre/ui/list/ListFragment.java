package com.example.p7_daa_alexandre.ui.list;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private RestaurantAdapter adapter;
    private ArrayList<ResultsItem> restaurants = new ArrayList<>();
    private ListViewModel viewModel;
    private Map<String, Integer> restaurantLikesMap = new HashMap<>();
    private Location userLocation;

    public static ListFragment newInstance(Location userLocation) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelable("userLocation", userLocation);
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        if (getArguments() != null) {
            userLocation = getArguments().getParcelable("userLocation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(getActivity().getApplicationContext())).get(ListViewModel.class);
        initRecyclerViews();
        loadData();
        updateSearch();
    }

    private void loadData() {
        viewModel.loadData().observe(getViewLifecycleOwner(), resultsItems -> {
            restaurants.clear();
            restaurants.addAll(resultsItems);
            updateRestaurantLikes();
            updateUI();
        });
    }

    private void updateSearch() {
        viewModel.getSearchResults().observe(getViewLifecycleOwner(), resultsItems -> {
            restaurants.clear();
            restaurants.addAll(resultsItems);
            updateRestaurantLikes();
            updateUI();
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
        viewModel.getLastKnownLocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null) {
                userLocation = location;
                adapter.setUserLocation(location);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void searchRestaurants(String query) {
        viewModel.searchRestaurants(query);
    }

    public void sortResultsAlphabetically() {
        Collections.sort(restaurants, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        adapter.notifyDataSetChanged();
    }

    private void updateUI() {
        if (restaurants.isEmpty()) {
            binding.listRestaurants.setVisibility(View.GONE);
            binding.textViewNoRestaurant.setVisibility(View.VISIBLE);
        } else {
            binding.listRestaurants.setVisibility(View.VISIBLE);
            binding.textViewNoRestaurant.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

}