package com.example.p7_daa_alexandre.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.repository.CoworkerRepository;

public class SettingsViewModel extends ViewModel {

    private final CoworkerRepository repository;

    private MutableLiveData<MutableLiveData<Boolean>> notificationStatus = new MutableLiveData<MutableLiveData<Boolean>>();

    public SettingsViewModel() {
        repository = new CoworkerRepository();
    };

    public void updateNotificationStatus() {
        notificationStatus.setValue(notificationStatus.getValue());
        repository.updateNotificationStatus();
    }

    public MutableLiveData<Boolean> getNotificationStatus() {
        return repository.updateNotificationStatus();
    }

}
