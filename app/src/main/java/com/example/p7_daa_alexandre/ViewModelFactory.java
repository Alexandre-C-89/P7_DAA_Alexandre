package com.example.p7_daa_alexandre;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.p7_daa_alexandre.database.CatFactsRepository;
import com.example.p7_daa_alexandre.database.RetrofitService;
import com.example.p7_daa_alexandre.ui.home.HomeViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private final CatFactsRepository catFactsRepository = new CatFactsRepository(
            // We inject the CatApi in the Repository constructor
            RetrofitService.getCatApi()
    );

    private ViewModelFactory() {

    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new HomeViewModel(catFactsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
