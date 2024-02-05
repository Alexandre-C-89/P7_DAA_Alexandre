package com.example.p7_daa_alexandre.ui.home;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.google.android.gms.tasks.Task;


public class HomeViewModel extends ViewModel {


    CoworkerRepository coworkerRepository;

    public HomeViewModel() {
        coworkerRepository = new CoworkerRepository();
    }

    public Task<Void> signOut(Context context) {
        return coworkerRepository.signOut(context);
    }

}
