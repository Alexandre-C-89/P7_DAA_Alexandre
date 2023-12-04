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
        homeViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                // Mettre à jour la liste de meetings
                updateList(tasks);
            }
        });
        initRecyclerViews();

        // Récupérer la nouvelle tâche passée en tant qu'argument
        Bundle args = getArguments();
        if (args != null && args.containsKey("newTask")) {
            Task newTask = args.getParcelable("newTask");
            // Ajouter la nouvelle tâches à la liste des tâches
            homeViewModel.addTask(newTask);
        }
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

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