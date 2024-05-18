package com.example.p7_daa_alexandre.repository;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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
import java.util.concurrent.CompletableFuture;

public class CoworkerRepository {

    private static final String COLLECTION_NAME = "Coworkers";
    private static final String COWORKERS_LIKED_RESTAURANT_COLLECTION = "liked_restaurant";
    private static final String COWORKERS_LIKED_RESTAURANT_COLLECTION_NAME_FIELD = "nom";
    private static volatile CoworkerRepository instance;
    private final MutableLiveData<ArrayList<Coworker>> listOfCoworkers = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLiked=new MutableLiveData<>();


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

    // Méthode pour récupérer les informations du profil de l'utilisateur
    public Task<DocumentSnapshot> getUserProfile() {
        FirebaseUser currentUser = getCurrentCoworker();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
                    .document(uid)
                    .get();
        } else {
            return Tasks.forException(new Exception(String.valueOf(R.string.coworker_repository_task_exception_message)));
        }
    }

    public Task<Void> signOut(Context context) {
        return AuthUI.getInstance().signOut(context);
    }

    // Get the Collection Reference
    public static CollectionReference getCoworkersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create a Coworker in Firestore
    /**public void createWorkmates() {
        FirebaseUser coworkers = getCurrentCoworker();
        if (coworkers != null) {
            getUserProfile().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String urlPicture = (coworkers.getPhotoUrl() != null) ? coworkers.getPhotoUrl().toString() : null;
                    String name = coworkers.getDisplayName();
                    String uid = coworkers.getUid();
                    String email = coworkers.getEmail();
                    Coworker workmatesToCreate = new Coworker(uid, name, email, urlPicture, "", "", "",false,  new ArrayList<>());
                    getCoworkersCollection().document(uid).set(workmatesToCreate);
                }
            });
        }
    }*/

    public void createWorkmates() {
        FirebaseUser coworker = getCurrentCoworker();
        if (coworker != null) {
            getUserProfile().addOnCompleteListener(task -> {
                if (!task.isSuccessful() || !task.getResult().exists()) {
                    // If the document does not exist or there was an error fetching it, create a new document
                    String urlPicture = (coworker.getPhotoUrl() != null) ? coworker.getPhotoUrl().toString() : null;
                    String name = coworker.getDisplayName();
                    String uid = coworker.getUid();
                    String email = coworker.getEmail();
                    Coworker workmateToCreate = new Coworker(uid, name, email, urlPicture, "", "", "", false, new ArrayList<>());

                    getCoworkersCollection().document(uid).set(workmateToCreate)
                            .addOnSuccessListener(aVoid -> Log.d("createWorkmates", "Coworker successfully created"))
                            .addOnFailureListener(e -> Log.e("createWorkmates", "Error creating coworker", e));
                } else {
                    Log.d("createWorkmates", "Coworker already exists");
                }
            });
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
                            Log.d("LOOPFOR", "SIZE LIST COWORKERS " + document.toObject(Coworker.class).getName() + coworkers);
                        }
                        //Log.d("AFTERFOR", "SIZE LIST RESTAURANT " + coworkers.size());
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

    public LiveData<Boolean> getNotificationStatus() {
        String uid = getCurrentCoworker().getUid();
        MutableLiveData<Boolean> notificationStatus = new MutableLiveData<>();

        getCoworkersCollection().document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null) {
                            notificationStatus.setValue(coworker.getNotification());
                        }
                    }
                }).addOnFailureListener(e -> {
                    // Handle the error, for example, by setting a default value
                    notificationStatus.setValue(false);
                });

        return notificationStatus;
    }

    // Update the notification status for the current coworker
    public void setNotificationStatus(boolean status) {
        String uid = getCurrentCoworker().getUid();
        getCoworkersCollection().document(uid)
                .update("notification", status)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Notification status updated"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating notification status", e));
    }

}
