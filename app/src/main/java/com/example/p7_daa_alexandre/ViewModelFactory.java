package com.example.p7_daa_alexandre;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import com.example.p7_daa_alexandre.ui.coworker.CoworkerViewModel;
import com.example.p7_daa_alexandre.ui.details.DetailsViewModel;
import com.example.p7_daa_alexandre.ui.home.HomeViewModel;
import com.example.p7_daa_alexandre.ui.list.ListViewModel;
import com.example.p7_daa_alexandre.ui.settings.SettingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private static LocationRepository locationRepository;

    private static Repository repository;
    private static CoworkerRepository coworkerRepository;

    public ViewModelFactory(Context context) {
        locationRepository = new LocationRepository(context);
        repository = new Repository();
        coworkerRepository = new CoworkerRepository();
    }

    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel();
        }
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(locationRepository, repository, coworkerRepository);
        }
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel();
        }
        if (modelClass.isAssignableFrom(CoworkerViewModel.class)) {
            return (T) new CoworkerViewModel();
        }
        if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel();
        }
        throw new IllegalArgumentException(String.valueOf(R.string.map_viewmodel_message_error));
    }

}
