package com.example.p7_daa_alexandre.ui.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;


public class HomeViewModel extends ViewModel {


    CoworkerRepository coworkerRepository;
    private final Repository repository;

    public HomeViewModel() {
        coworkerRepository = new CoworkerRepository();
        repository = new Repository();
    }

    public Task<Void> signOut(Context context) {
        return coworkerRepository.signOut(context);
    }

    // Méthode pour rechercher un restaurant
    public void searchRestaurant(String query) {
        repository.searchRestaurant(query);
    }

    public Task<DocumentSnapshot>getUserProfil(){
        return coworkerRepository.getUserProfile();
    }

    public Repository getRepository() {
        return repository;
    }

}
