package com.example.p7_daa_alexandre.ui.home;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Initialize the ViewModel with the MeetingRepository
        //homeViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(HomeViewModel.class);

        return binding.getRoot();
    }

    /**@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.inflateMenu(R.menu.actions);

       binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_restaurant:{
                    //sortMethod = SortMethod.ALPHABETICAL;
                    //searchMethod = SearchMethod.;
                    break;
                }
                default: {
                    sortMethod = SortMethod.NONE;
                    break;
                }
            }
            updateTasks();
            return super.onOptionsItemSelected(item);
        });


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

    }*/


}
