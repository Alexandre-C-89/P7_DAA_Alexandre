package com.example.p7_daa_alexandre.ui.home;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.p7_daa_alexandre.model.CatFact;
import com.example.p7_daa_alexandre.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final Repository repository;

    private final MutableLiveData<Integer> currentPageMutableLiveData = new MutableLiveData<>();

    private final LiveData<HomeViewState> homeViewStateLiveData;

    public HomeViewModel(Repository repository) {
        repository = Repository;

        // We start the page at 0 (this will trigger the switchMap to query the first page from the server)
        currentPageMutableLiveData.setValue(0);

        // If the LiveData that contains the current page information changes...
        homeViewStateLiveData = Transformations.switchMap(currentPageMutableLiveData, currentPage ->
                // ... we query the repository to get the page (with a Transformations.switchMap)...
                Transformations.map(repository.getRestaurantsLiveData(currentPage), restaurants ->
                        // ... and we transform the data from the server to the ViewState (with a Transformations.map)
                        mapDataToViewState(restaurants)
                )
        );
    }

    // This is the "final product" of our ViewModel : every data needed from the view is in this LiveData
    public LiveData<HomeViewState> getViewStateLiveData() {
        return homeViewStateLiveData;
    }

    public void onPreviousPageButtonClicked() {
        Integer currentValue = currentPageMutableLiveData.getValue();
        if (currentValue == null || currentValue == 0) {
            return;
        }
        currentPageMutableLiveData.setValue(currentValue - 1);
    }

    public void onNextPageButtonClicked() {
        //noinspection ConstantConditions
        currentPageMutableLiveData.setValue(currentPageMutableLiveData.getValue() + 1);
    }

    private HomeViewState mapDataToViewState(@Nullable List<CatFact> catFacts, int currentPage) {
        List<String> catFactsToBeDisplayed = new ArrayList<>();

        if (catFacts != null) {
            // Mapping data from remote source to view data, ask to your mentor to know why it is important to do so
            for (CatFact cat : catFacts) {
                catFactsToBeDisplayed.add(cat.getFact());
            }
        }

        // Don't let user click to the previous button if the current page is 0 ;)
        boolean isPreviousPageButtonClickable = currentPage != 0;

        return new HomeViewState(
                catFactsToBeDisplayed,
                isPreviousPageButtonClickable
        );
    }
}
