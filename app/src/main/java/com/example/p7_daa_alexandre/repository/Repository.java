package com.example.p7_daa_alexandre.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.model.Restaurant;

import java.util.List;

public class Repository {
    private final RestaurantDao restaurantDao;

    //private final ProjectDao projectDao;

    private final MutableLiveData<List<Restaurant>> tasksLiveData = new MutableLiveData<>();

    //private final List<Restaurant> tasks = DummyGenerator.generateTask();


    public Repository(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
        //this.projectDao = projectDao;
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return this.restaurantDao.getRestaurants();
    }

    /**public LiveData<List<Project>> getProjects(long projectId) {
        return this.projectDao.getProjects();
    }*/

    /**public void resetFilter() {
        tasksLiveData.setValue(restaurants);
    }*/


}
