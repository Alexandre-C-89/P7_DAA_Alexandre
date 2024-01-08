package com.example.p7_daa_alexandre.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.databinding.ActivityDetailsBinding;
import com.example.p7_daa_alexandre.databinding.ActivityHomeBinding;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}