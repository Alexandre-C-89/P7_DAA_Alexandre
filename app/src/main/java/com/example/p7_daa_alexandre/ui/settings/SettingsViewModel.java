package com.example.p7_daa_alexandre.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.repository.CoworkerRepository;

public class SettingsViewModel extends ViewModel {

    private final CoworkerRepository repository;

    private MutableLiveData<Boolean> notificationStatus = new MutableLiveData<>();

    public SettingsViewModel() {
        repository = new CoworkerRepository();
    };

    public void updateNotificationStatus(Boolean isChecked) {
        notificationStatus.setValue(isChecked);
        repository.updateNotificationStatus(isChecked);
    }

    public LiveData<Boolean> getNotificationStatus() {
        return notificationStatus;
    }

}
