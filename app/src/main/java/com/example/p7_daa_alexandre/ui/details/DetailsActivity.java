package com.example.p7_daa_alexandre.ui.details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.databinding.ActivityDetailsBinding;
import com.example.p7_daa_alexandre.databinding.ActivityHomeBinding;
import com.example.p7_daa_alexandre.ui.list.ListViewModel;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

        viewModel.getRestaurantDetails().observe();

        //Bundle extras = getIntent().getIntExtras();

        setContentView(binding.getRoot());
    }

    private void initRecyclerViews() {
        adapter = new DetailsAdapter(tasks);
        adapter.setOnItemClickListener(new DetailsAdapter().OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Ici, tu peux gérer les clics sur les éléments de la liste
                // Avant la suppression ou effectuer d'autres actions si nécessaire
                deleteMeeting(position);
            }
        });

        RecyclerView recyclerView = binding.listTasks;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


}