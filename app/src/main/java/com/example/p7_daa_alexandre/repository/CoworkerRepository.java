package com.example.p7_daa_alexandre.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CoworkerRepository {

    private static final String COLLECTION_NAME = "Coworkers";
    private static final String COWORKERS_LIKED_RESTAURANT_COLLECTION = "liked_restaurant";
    private static final String COWORKERS_LIKED_RESTAURANT_COLLECTION_NAME_FIELD = "name";
    private static volatile CoworkerRepository instance;
    private final MutableLiveData<ArrayList<Coworker>> listOfCoworkers = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLiked=new MutableLiveData<>();
    private MutableLiveData<Boolean> isNotificationActiveOfCurrentCoworkers =new MutableLiveData<>();


    public CoworkerRepository() {
    }

    public static CoworkerRepository getInstance() {

        if (instance == null) {
            instance = new CoworkerRepository();
        }
        return instance;
    }

    public FirebaseUser getCurrentCoworker() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public Task<Void> signOut(Context context) {
        return AuthUI.getInstance().signOut(context);
    }

    // Get the Collection Reference
    public static CollectionReference getCoworkersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create a Coworker in Firestore
    public void createWorkmates() {
        FirebaseUser coworkers = getCurrentCoworker();
        if (coworkers != null) {
            String urlPicture = (coworkers.getPhotoUrl() != null) ? coworkers.getPhotoUrl().toString() : null;
            String name = coworkers.getDisplayName();
            String uid = coworkers.getUid();
            String email = coworkers.getEmail();
            Coworker workmatesToCreate = new Coworker(uid, name, email, urlPicture, true);
            this.getCoworkersCollection().document(uid).set(workmatesToCreate);
        }
    }

    public MutableLiveData<ArrayList<Coworker>> getAllCoworkers() {
        getCoworkersCollection()
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Coworker> coworkers = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            coworkers.add(document.toObject(Coworker.class));
                            Log.d("Error", "SIZE LIST RESTAURANT " + document.toObject(Coworker.class).getName());
                        }
                        Log.d("Error", "SIZE LIST RESTAURANT " + coworkers.size());
                        listOfCoworkers.setValue(coworkers);
                    } else {
                        Log.d("Error", "Error getting documents: ", task.getException());
                    }
                })
                .addOnFailureListener(e -> listOfCoworkers.postValue(null));
        return listOfCoworkers;

    }

    // Create a Like in Firestore
    public void addLikeRestaurant(Restaurant restaurant) {
        FirebaseUser Coworkers = getCurrentCoworker();
        String uid = Coworkers.getUid();
        this.getCoworkersCollection().document(uid).collection(COWORKERS_LIKED_RESTAURANT_COLLECTION).add(restaurant);
    }

    // Delete a Like in Firestore
    public void deleteLikeRestaurant(Restaurant restaurant) {
        FirebaseUser coworkers = getCurrentCoworker();
        String uid = coworkers.getUid();
        this.getCoworkersCollection().document(uid)
                .collection(COWORKERS_LIKED_RESTAURANT_COLLECTION)
                .whereEqualTo(COWORKERS_LIKED_RESTAURANT_COLLECTION_NAME_FIELD,restaurant.getName())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                    }
                    else {
                        Log.d("Error", "Error delete documents: ", task.getException());
                    }
                });

    }

    public MutableLiveData<Boolean> checkIfCurrentWorkmateLikeThisRestaurant(Restaurant restaurant) {

        FirebaseUser coworkers = getCurrentCoworker();
        String uid = coworkers.getUid();
        getCoworkersCollection()
                .document(uid)
                .collection(COWORKERS_LIKED_RESTAURANT_COLLECTION)
                .whereEqualTo(COWORKERS_LIKED_RESTAURANT_COLLECTION_NAME_FIELD, restaurant.getName())
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                isLiked.postValue(false);
                                if (task.getResult().size() > 0) {
                                    isLiked.postValue(true);
                                }

                            }
                        }
                );
        return isLiked;
    }
    public void setIsNotificationActiveOfCurrentCoworkers(Boolean isNotificationActive) {
        FirebaseUser coworkers = getCurrentCoworker();
        if (coworkers != null) {
            String urlPicture = (coworkers.getPhotoUrl() != null) ? coworkers.getPhotoUrl().toString() : null;
            String name = coworkers.getDisplayName();
            String uid = coworkers.getUid();
            String email = coworkers.getEmail();
            System.out.println("email "+email);
            Coworker coworkersToUpdate = new Coworker(uid, name, email, urlPicture,isNotificationActive);
            this.getCoworkersCollection().document(uid).set(coworkersToUpdate);
        }
    }
    public MutableLiveData<Boolean> getIsNotificationActiveOfCurrentCoworkers() {
        FirebaseUser coworkers = getCurrentCoworker();
        String uid = coworkers.getUid();
        getCoworkersCollection()
                .whereEqualTo("idWorkmate",uid)
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                isNotificationActiveOfCurrentCoworkers.postValue(task.getResult().toObjects(Coworker.class).get(0).getIsNotificationActive());
                            }
                            else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                );
        return isNotificationActiveOfCurrentCoworkers;
    }


}
