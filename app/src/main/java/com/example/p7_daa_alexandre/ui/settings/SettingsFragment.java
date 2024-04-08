package com.example.p7_daa_alexandre.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;

    private SettingsViewModel viewModel;

    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        viewModel.getNotificationStatus().observe(getViewLifecycleOwner(), isChecked -> {
            binding.notificationBtn.setChecked(isChecked);

            binding.notificationBtn.setOnCheckedChangeListener((buttonView, Checked) -> {
                viewModel.updateNotificationStatus(Checked);
                if (Checked == true) {
                    Toast.makeText(getActivity(), "Notification activated !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Notification disabled !", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }

}