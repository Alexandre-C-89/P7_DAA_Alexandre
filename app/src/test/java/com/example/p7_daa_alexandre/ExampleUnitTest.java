package com.example.p7_daa_alexandre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.repository.Repository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**@Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();*/

    @Mock
    private RestaurantApi restaurantApi;

    private Repository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = new Repository();
        //RetrofitService retrofitService = new RetrofitService(restaurantApi); // Pass the mocked API service
        //repository.setRestaurantApi(retrofitService.getRestaurantDetails());
    }

    @Test
    public void testCallAPI_ReturnsResult() {
        // Call the method to be tested
        repository.callAPI();

        // Get the LiveData value
        ArrayList<ResultsItem> result = repository.getSearchResults().getValue();

        // Check if the result is not null
        assertNotNull(result);
    }

}