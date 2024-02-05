package com.example.p7_daa_alexandre.ui.coworker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.Repository;
//import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class CoworkerViewModel extends ViewModel {

    private final CoworkerRepository repository;
    //private final Executor executor;

    public CoworkerViewModel() {
        repository = new CoworkerRepository();
    };

    /**
     * Liste de Coworkers
    */
    public LiveData<ArrayList<Coworker>> getAllCoworkers() {
        return repository.getAllCoworkers();
    }

}
