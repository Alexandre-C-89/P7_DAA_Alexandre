package com.example.p7_daa_alexandre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.location.Location;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.database.RestaurantApi;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.repository.LocationRepository;
import com.example.p7_daa_alexandre.repository.Repository;
import com.example.p7_daa_alexandre.ui.details.DetailsViewModel;
import com.example.p7_daa_alexandre.ui.home.HomeViewModel;
import com.example.p7_daa_alexandre.ui.list.ListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RestaurantApi restaurantApi;

    @Mock
    Repository repository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CoworkerRepository coworkerRepository;

    private ListViewModel ListViewModel;

    HomeViewModel homeViewModel;

    private DetailsViewModel detailsViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //repository = Mockito.mock(Repository.class);
        coworkerRepository = Mockito.mock(CoworkerRepository.class);
        ListViewModel = new ListViewModel(locationRepository, repository, coworkerRepository);
        homeViewModel = new HomeViewModel();
        detailsViewModel = new DetailsViewModel();
        detailsViewModel.repository = repository;
        detailsViewModel.coworkerRepository = coworkerRepository;
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

    /**
     * ListViewModel
     * MockWebServer
     */

    @Test
    public void testLoadData() {
        MutableLiveData<ArrayList<ResultsItem>> testData = new MutableLiveData<>();
        when(repository.callAPI()).thenReturn(testData);

        assertNotNull(ListViewModel.loadData());
        verify(repository).callAPI();
    }

    @Test
    public void testGetCoworkerWhoChooseRestaurant() {
        // Create test data for the LiveData
        List<Coworker> testData = new ArrayList<>();
        // Add some dummy data to the list if needed

        // Create a MutableLiveData object and set the test data
        MutableLiveData<List<Coworker>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(testData);

        // Mock the behavior of the coworkerRepository
        when(coworkerRepository.getCoworkerWhoChoseRestaurant(anyString())).thenReturn(mutableLiveData);

        // Call the method to be tested
        LiveData<List<Coworker>> resultLiveData = ListViewModel.getCoworkerWhoChoseRestaurant("test_place_id");

        // Get the LiveData value
        List<Coworker> result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }

    @Test
    public void testGetLastKnownLocation() {
        // Create a dummy location object for testing
        Location dummyLocation = new Location("dummyProvider");
        dummyLocation.setLatitude(37.7749);
        dummyLocation.setLongitude(-122.4194);

        // Create a MutableLiveData object and set the dummy location
        MutableLiveData<Location> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(dummyLocation);

        // Mock the behavior of the locationRepository
        when(locationRepository.getLastLocation()).thenReturn(mutableLiveData);

        // Call the method to be tested
        LiveData<Location> resultLiveData = ListViewModel.getLastKnownLocation();

        // Get the LiveData value
        Location result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }

    @Test
    public void testGetSearchResults() {
        // Create a dummy list of search results
        ArrayList<ResultsItem> dummySearchResults = new ArrayList<>();
        // Add dummy data to the list if needed

        // Create a MutableLiveData object and set the dummy search results
        MutableLiveData<ArrayList<ResultsItem>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(dummySearchResults);

        // Mock the behavior of the repository
        when(repository.getSearchResults()).thenReturn(mutableLiveData);

        // Call the method to be tested
        LiveData<ArrayList<ResultsItem>> resultLiveData = ListViewModel.getSearchResults();

        // Get the LiveData value
        ArrayList<ResultsItem> result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }

    @Test
    public void testSearchRestaurants() {
        // Create a dummy list of search results
        ArrayList<ResultsItem> dummySearchResults = new ArrayList<>();
        // Add dummy data to the list if needed

        // Create a MutableLiveData object and set the dummy search results
        MutableLiveData<ArrayList<ResultsItem>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(dummySearchResults);

        // Mock the behavior of the repository
        String query = "Your search query"; // Provide a search query
        when(repository.searchRestaurants(query)).thenReturn(mutableLiveData);

        // Call the method to be tested
        LiveData<ArrayList<ResultsItem>> resultLiveData = ListViewModel.searchRestaurants(query);

        // Get the LiveData value
        ArrayList<ResultsItem> result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }

    /**
     * HomeViewModel
     */

    /**@Test
    public void testSearchRestaurant() {
        // Define the query
        String query = "Your search query";

        // Call the method to be tested
        homeViewModel.searchRestaurant(query);

        // Verify that the searchRestaurant method in the repository is called with the correct query
        verify(repository).searchRestaurant(query);
    }*/

    /**
     * DetailsViewModel
     */

    @Test
    public void testGetRestaurantDetails() {
        // Define a placeId for testing
        String placeId = "test_place_id";

        // Create a dummy DetailsResponse object for testing
        DetailsResponse dummyResponse = new DetailsResponse();
        // Add dummy data to the response object if needed

        // Create a MutableLiveData object and set the dummy response
        MutableLiveData<DetailsResponse> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(dummyResponse);

        // Mock the behavior of the repository
        when(repository.getRestaurantDetails(placeId)).thenReturn(mutableLiveData);

        // Call the method to be tested
        MutableLiveData<DetailsResponse> resultLiveData = detailsViewModel.getRestaurantDetails(placeId);

        // Get the LiveData value
        DetailsResponse result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }

    @Test
    public void testRestaurantChoosed() {
        // Define parameters for testing
        String placeId = "test_place_id";
        String restaurantName = "Test Restaurant";
        String address = "Test Address";

        // Call the method to be tested
        detailsViewModel.restaurantChoosed(placeId, restaurantName, address);

        // Verify that the restaurantChoosed method in the coworkerRepository is called with the correct parameters
        verify(coworkerRepository).restaurantChoosed(placeId, restaurantName, address);
    }

    @Test
    public void testGetCoworkerWhoChoseRestaurant() {
        // Define a placeId for testing
        String placeId = "test_place_id";

        // Create test data for the LiveData
        List<Coworker> testData = new ArrayList<>();
        // Add some dummy data to the list if needed

        // Create a MutableLiveData object and set the test data
        MutableLiveData<List<Coworker>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(testData);

        // Mock the behavior of the coworkerRepository
        when(coworkerRepository.getCoworkerWhoChoseRestaurant(placeId)).thenReturn(mutableLiveData);

        // Call the method to be tested
        LiveData<List<Coworker>> resultLiveData = detailsViewModel.getCoworkerWhoChoseRestaurant(placeId);

        // Get the LiveData value
        List<Coworker> result = resultLiveData.getValue();

        // Assert that the result is not null
        assertNotNull(result);
        // Additional assertions can be added based on the expected behavior
    }


}