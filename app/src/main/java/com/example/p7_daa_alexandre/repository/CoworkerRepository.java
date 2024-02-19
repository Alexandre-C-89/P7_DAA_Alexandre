package com.example.p7_daa_alexandre.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
            Coworker workmatesToCreate = new Coworker(uid, name, email, urlPicture, "", "", "", new ArrayList<>());
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

    // Add restaurantId in Firestore
    public void restaurantChoosed(String placeId,String restaurantName,String address) {
        FirebaseUser Coworkers = getCurrentCoworker();
        String uid = Coworkers.getUid();
        getCoworkersCollection().document(uid).update("placeId", placeId)
                .addOnSuccessListener(aVoid -> Log.d("CoworkerRepository", "Restaurant added to favorites"))
                .addOnFailureListener(e -> Log.e("CoworkerRepository", "Error adding restaurant to favorites", e));
        getCoworkersCollection().document(uid).update("restaurantName", restaurantName)
                .addOnSuccessListener(aVoid -> Log.d("CoworkerRepository", "Restaurant added to favorites"))
                .addOnFailureListener(e -> Log.e("CoworkerRepository", "Error adding restaurant to favorites", e));
        getCoworkersCollection().document(uid).update("address", address)
                .addOnSuccessListener(aVoid -> Log.d("CoworkerRepository", "Restaurant added to favorites"))
                .addOnFailureListener(e -> Log.e("CoworkerRepository", "Error adding restaurant to favorites", e));

    }

    public LiveData<List<Coworker>> getCoworkerWhoChoseRestaurant(String placeId) {
        MutableLiveData<List<Coworker>> coworkerLiveData = new MutableLiveData<>();

        getCoworkersCollection()
                .whereEqualTo("placeId", placeId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Coworker> coworkerList = new ArrayList<>();
                        for (DocumentSnapshot document: value.getDocuments()) {
                            Coworker coworker = document.toObject(Coworker.class);
                            coworkerList.add(coworker);
                        }
                        coworkerLiveData.setValue(coworkerList);
                    }
                });
        return coworkerLiveData;
    }

    /**public MutableLiveData<Boolean> checkIfCurrentWorkmateLikeThisRestaurant(Restaurant restaurant) {

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
            Coworker coworkersToUpdate = new Coworker(uid, name, email, urlPicture,isNotificationActive, placeId, restaurantName, like, like1);
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
                                isNotificationActiveOfCurrentCoworkers.postValue(task.getResult().toObjects(Coworker.class).get(0).getLikedRestaurants());
                            }
                            else {
                                Log.d("Error", "Error getting documents: ", task.getException());
                            }
                        }
                );
        return isNotificationActiveOfCurrentCoworkers;
    }*/

    // Récupérer les coworkers qui ont liké un restaurant spécifique
    public LiveData<List<Coworker>> getCoworkersLikedRestaurant(String restaurantId) {
        MutableLiveData<List<Coworker>> likedCoworkersLiveData = new MutableLiveData<>();
        List<Coworker> likedCoworkers = new ArrayList<>();

        // Obtenez la référence de la collection des coworkers
        CollectionReference coworkersCollection = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);

        // Requête pour obtenir les coworkers qui ont liké le restaurant
        coworkersCollection.whereEqualTo("likedRestaurants." + restaurantId, true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Ajoutez chaque coworker à la liste
                            likedCoworkers.add(document.toObject(Coworker.class));
                        }
                        // Mettez à jour les données LiveData avec la liste des coworkers qui ont liké le restaurant
                        likedCoworkersLiveData.setValue(likedCoworkers);
                    } else {
                        // Gérez les erreurs
                        likedCoworkersLiveData.setValue(null);
                        Log.d("CoworkerRepository", "Error getting liked coworkers: ", task.getException());
                    }
                });

        return likedCoworkersLiveData;
    }


}
