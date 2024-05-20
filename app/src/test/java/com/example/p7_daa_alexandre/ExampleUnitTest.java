package com.example.p7_daa_alexandre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
import com.example.p7_daa_alexandre.ui.map.MapViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ExampleUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Repository repository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CoworkerRepository coworkerRepository;

    private ListViewModel listViewModel;
    private HomeViewModel homeViewModel;
    private DetailsViewModel detailsViewModel;
    private MapViewModel mapViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        listViewModel = new ListViewModel(locationRepository, repository, coworkerRepository);
        homeViewModel = new HomeViewModel();
        detailsViewModel = new DetailsViewModel();
        detailsViewModel.repository = repository;
        detailsViewModel.coworkerRepository = coworkerRepository;
        mapViewModel = new MapViewModel(locationRepository, repository);
    }

    @Test
    public void testCallAPI_ReturnsResult() {
        // Mock behavior
        MutableLiveData<ArrayList<ResultsItem>> mockLiveData = new MutableLiveData<>(new ArrayList<>());
        when(repository.getSearchResults()).thenReturn(mockLiveData);

        // Call the method to be tested
        repository.callAPI();

        // Assert LiveData is not null and verify interaction
        assertNotNull(repository.getSearchResults().getValue());
        verify(repository).callAPI();
    }

    @Test
    public void testLoadData() {
        MutableLiveData<ArrayList<ResultsItem>> testData = new MutableLiveData<>();
        when(repository.callAPI()).thenReturn(testData);
        MutableLiveData<ArrayList<ResultsItem>> loadData = listViewModel.loadData();
        verify(repository).callAPI();
        assertNotNull(loadData);
        assertEquals(testData, loadData);
    }

    @Test
    public void testGetCoworkerWhoChooseRestaurant() {
        List<Coworker> testData = new ArrayList<>();
        MutableLiveData<List<Coworker>> mutableLiveData = new MutableLiveData<>(testData);

        when(coworkerRepository.getCoworkerWhoChoseRestaurant(anyString())).thenReturn(mutableLiveData);

        LiveData<List<Coworker>> resultLiveData = listViewModel.getCoworkerWhoChoseRestaurant("test_place_id");

        assertNotNull(resultLiveData.getValue());
        verify(coworkerRepository).getCoworkerWhoChoseRestaurant("test_place_id");
    }

    @Test
    public void testGetLastKnownLocation() {
        Location dummyLocation = mock(Location.class);
        when(dummyLocation.getLatitude()).thenReturn(37.7749);
        when(dummyLocation.getLongitude()).thenReturn(-122.4194);
        MutableLiveData<Location> mutableLiveData = new MutableLiveData<>(dummyLocation);

        when(locationRepository.getLastLocation()).thenReturn(mutableLiveData);
        ListViewModel listViewModel1 = new ListViewModel(locationRepository, repository, coworkerRepository);
        LiveData<Location> resultLiveData = listViewModel1.getLastKnownLocation();

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummyLocation.getLatitude(), resultLiveData.getValue().getLatitude(), 0);
        assertEquals(dummyLocation.getLongitude(), resultLiveData.getValue().getLongitude(), 0);
    }

    @Test
    public void testGetSearchResults() {
        ArrayList<ResultsItem> dummySearchResults = new ArrayList<>();
        MutableLiveData<ArrayList<ResultsItem>> mutableLiveData = new MutableLiveData<>(dummySearchResults);

        when(repository.getSearchResults()).thenReturn(mutableLiveData);

        LiveData<ArrayList<ResultsItem>> resultLiveData = listViewModel.getSearchResults();

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummySearchResults, resultLiveData.getValue());
    }

    @Test
    public void testSearchRestaurants() {
        ArrayList<ResultsItem> dummySearchResults = new ArrayList<>();
        MutableLiveData<ArrayList<ResultsItem>> mutableLiveData = new MutableLiveData<>(dummySearchResults);

        String query = "Your search query";
        when(repository.searchRestaurants(query)).thenReturn(mutableLiveData);

        LiveData<ArrayList<ResultsItem>> resultLiveData = listViewModel.searchRestaurants(query);

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummySearchResults, resultLiveData.getValue());
        verify(repository).searchRestaurants(query);
    }

    @Test
    public void testGetRestaurantDetails() {
        String placeId = "test_place_id";
        DetailsResponse dummyResponse = new DetailsResponse();
        MutableLiveData<DetailsResponse> mutableLiveData = new MutableLiveData<>(dummyResponse);

        when(repository.getRestaurantDetails(placeId)).thenReturn(mutableLiveData);

        LiveData<DetailsResponse> resultLiveData = detailsViewModel.getRestaurantDetails(placeId);

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummyResponse, resultLiveData.getValue());
        verify(repository).getRestaurantDetails(placeId);
    }

    @Test
    public void testRestaurantChoosed() {
        String placeId = "test_place_id";
        String restaurantName = "Test Restaurant";
        String address = "Test Address";

        detailsViewModel.restaurantChoosed(placeId, restaurantName, address);

        verify(coworkerRepository).restaurantChoosed(placeId, restaurantName, address);
    }

    @Test
    public void testGetCoworkerWhoChoseRestaurant() {
        String placeId = "test_place_id";
        List<Coworker> testData = new ArrayList<>();
        MutableLiveData<List<Coworker>> mutableLiveData = new MutableLiveData<>(testData);

        when(coworkerRepository.getCoworkerWhoChoseRestaurant(placeId)).thenReturn(mutableLiveData);

        LiveData<List<Coworker>> resultLiveData = detailsViewModel.getCoworkerWhoChoseRestaurant(placeId);

        assertNotNull(resultLiveData.getValue());
        assertEquals(testData, resultLiveData.getValue());
        verify(coworkerRepository).getCoworkerWhoChoseRestaurant(placeId);
    }

    @Test
    public void testGetLastKnownLocationMapViewModel() {
        Location dummyLocation = mock(Location.class);
        when(dummyLocation.getLatitude()).thenReturn(37.7749);
        when(dummyLocation.getLongitude()).thenReturn(-122.4194);
        MutableLiveData<Location> mutableLiveData = new MutableLiveData<>(dummyLocation);

        when(locationRepository.getLastLocation()).thenReturn(mutableLiveData);
        ListViewModel listViewModel1 = new ListViewModel(locationRepository, repository, coworkerRepository);
        LiveData<Location> resultLiveData = listViewModel1.getLastKnownLocation();

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummyLocation.getLatitude(), resultLiveData.getValue().getLatitude(), 0);
        assertEquals(dummyLocation.getLongitude(), resultLiveData.getValue().getLongitude(), 0);
    }

    @Test
    public void testGetNearbyRestaurants() {
        double lat = 37.7749;
        double lng = -122.4194;
        ArrayList<ResultsItem> dummyNearbyRestaurants = new ArrayList<>();

        when(repository.getNearbyRestaurants(lat, lng)).thenReturn(new MutableLiveData<>(dummyNearbyRestaurants));

        LiveData<ArrayList<ResultsItem>> resultLiveData = mapViewModel.getNearbyRestaurants(lat, lng);

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummyNearbyRestaurants, resultLiveData.getValue());
    }

    /**@Test
    public void testSearchRestaurantsMapViewModel() {
        String query = "Your search query";
        ArrayList<ResultsItem> dummySearchResults = new ArrayList<>();
        MutableLiveData<ArrayList<ResultsItem>> mutableLiveData = new MutableLiveData<>(dummySearchResults);

        LiveData<ArrayList<ResultsItem>> resultLiveData = mapViewModel.searchRestaurants(query);

        when(repository.searchRestaurants(query)).thenReturn(mutableLiveData);

        assertNotNull(resultLiveData.getValue());
        assertEquals(dummySearchResults, resultLiveData.getValue());
        verify(repository).searchRestaurants(query);
    }*/

}