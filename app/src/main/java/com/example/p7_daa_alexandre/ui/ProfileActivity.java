package com.example.p7_daa_alexandre.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.p7_daa_alexandre.MainActivity;
import com.example.p7_daa_alexandre.databinding.ActivityProfileBinding;

public class ProfileActivity extends MainActivity<ActivityProfileBinding> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListeners();
    }

    private void setupListeners(){
        binding.updateButton.setOnClickListener(view -> { });
        binding.signOutButton.setOnClickListener(view -> { });
        binding.deleteButton.setOnClickListener(view -> { });
    }

}
