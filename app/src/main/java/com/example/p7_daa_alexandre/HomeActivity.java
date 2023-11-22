package com.example.p7_daa_alexandre;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.p7_daa_alexandre.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }



}