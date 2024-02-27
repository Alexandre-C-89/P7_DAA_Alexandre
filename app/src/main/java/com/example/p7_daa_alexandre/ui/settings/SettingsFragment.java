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

        binding.notificationBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Activer les notifications si le bouton est activé
            viewModel.updateNotificationStatus(isChecked);
            if (isChecked == true) {
                Toast.makeText(getActivity(), "Notification activated !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Notification disabled !", Toast.LENGTH_SHORT).show();
            }
        });

        // Observer pour mettre à jour l'état du bouton lorsque la valeur de notification change
        viewModel.getNotificationStatus().observe(getViewLifecycleOwner(), isChecked -> {
            // Mettez à jour l'état du bouton en fonction de la valeur de notification
            binding.notificationBtn.setChecked(isChecked);
        });

    }

}
