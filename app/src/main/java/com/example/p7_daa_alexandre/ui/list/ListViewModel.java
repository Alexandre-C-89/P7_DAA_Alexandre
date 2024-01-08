package com.example.p7_daa_alexandre.ui.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.repository.Repository;
import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    private final Repository repository ;


    public ListViewModel() {
        repository = new Repository();
    }
    public MutableLiveData<ArrayList<ResultsItem>> loadData() {
        return repository.callAPI();
    }

}
