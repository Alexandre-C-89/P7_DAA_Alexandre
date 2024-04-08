package com.example.p7_daa_alexandre.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.repository.CoworkerRepository;

public class SettingsViewModel extends ViewModel {

    private final CoworkerRepository repository;

    private final LiveData<Boolean> notificationStatus;

    public SettingsViewModel() {
        repository = new CoworkerRepository();
        notificationStatus = repository.getNotificationStatus();
    };

    public LiveData<Boolean> getNotificationStatus() {
        return notificationStatus;
    }

    public void updateNotificationStatus(boolean status) {
        repository.setNotificationStatus(status);
    }

}
