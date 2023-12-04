package com.example.p7_daa_alexandre.ui.list;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.p7_daa_alexandre.databinding.FragmentListBinding;
import com.example.p7_daa_alexandre.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private RestaurantAdapter adapter;

    private List<Restaurant> restaurants = new ArrayList<>();

    private ListViewModel listViewModel;


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

        // Observe la liste de tâches
        listViewModel.getRestaurants().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                // Mettre à jour la liste de meetings
                updateList(restaurants);
            }
        });
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

    private void updateList(List<Restaurant> listRestaurants){
        restaurants.clear();
        restaurants.addAll(listRestaurants);
        updateRestaurants();
    }

    private void updateRestaurants() {
        if (restaurants.size() == 0) {
            binding.textViewNoRestaurant.setVisibility(View.VISIBLE);
            binding.listRestaurants.setVisibility(View.GONE);
        } else {
            binding.textViewNoRestaurant.setVisibility(View.GONE);
            binding.listRestaurants.setVisibility(View.VISIBLE);
            /**switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(restaurants, new Restaurant.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Re.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;

            }*/
            adapter.updateRestaurants(restaurants);
        }
    }


}