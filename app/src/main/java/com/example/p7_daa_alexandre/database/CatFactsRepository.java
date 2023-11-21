package com.example.p7_daa_alexandre.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.p7_daa_alexandre.model.CatFact;
import com.example.p7_daa_alexandre.model.CatFactsResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatFactsRepository {

    private final CatFactsApi catFactsApi;

    // In this Map we will store the responses we get from the server (corresponding to their page number),
    // so if needed we can "get back in time", this will act like a cache !
    // (Check 'caching' on Google or ask your mentor for more information)
    private final Map<Integer, CatFactsResponse> alreadyFetchedResponses = new HashMap<>();

    public CatFactsRepository(CatFactsApi catFactsApi) {
        this.catFactsApi = catFactsApi;
    }

    public LiveData<List<CatFact>> getCatFactsLiveData(int page) {
        MutableLiveData<List<CatFact>> catFactsMutableLiveData = new MutableLiveData<>();

        // Check in our cache if we already queried and stored the response

        CatFactsResponse response = alreadyFetchedResponses.get(page);

        if (response != null) {
            // We already have the response (because we already queried this page in the past) ! No need to call the api !
            catFactsMutableLiveData.setValue(response.getCatFacts());
        } else {
            // First time this page is queried, let's call the server ('enqueue()' makes the request on another thread)...
            catFactsApi.getListOfCats(page).enqueue(new Callback<CatFactsResponse>() {
                @Override
                public void onResponse(@NonNull Call<CatFactsResponse> call, @NonNull Response<CatFactsResponse> response) {
                    if (response.body() != null) {
                        // ... and once we have the result, we store it in our Map for potential future use !
                        alreadyFetchedResponses.put(page, response.body());

                        // Publish the result to the LiveData, we can use 'setValue()' instead of 'postValue()'
                        // because Retrofit goes back to the Main Thread once the query is finished !
                        catFactsMutableLiveData.setValue(response.body().getCatFacts());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CatFactsResponse> call, @NonNull Throwable t) {
                    catFactsMutableLiveData.setValue(null);
                }
            });
        }

        return catFactsMutableLiveData;
    }
}
