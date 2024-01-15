package com.example.p7_daa_alexandre.ui.details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

        //Bundle extras = getIntent().getIntExtras();

        setContentView(binding.getRoot());
    }


}