package com.example.p7_daa_alexandre;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.repository.AppRepository;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MyNotificationReceiver extends BroadcastReceiver {

    private Context context;

    private CoworkerRepository coworkerRepository;

    private ArrayList<Coworker> allCoworkers;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        coworkerRepository = CoworkerRepository.getInstance();
        getAllCoworkers();

        // Assuming you have some logic to fetch restaurant details here
        //String title = "Restaurant of the Day";
        /**String content = "Check out today's featured restaurant!";
        String placeId = intent.getStringExtra(String.valueOf(R.string.notification_receiver_message_id));
        CompletableFuture<String> restaurantNameFuture = coworkerRepository.getCoworkerRestaurantName(placeId);
        Log.d("RECEIVER", "\"${restaurantNameFuture}\"");
        restaurantNameFuture.thenAccept(restaurantName -> {
            // Assuming you have some logic to fetch restaurant details here
            String title = String.valueOf(R.string.notification_receiver_title);
            String content = R.string.notification_receiver_title + restaurantName;

            // Create notification channel and show notification
            createNotificationChannel();
            showNotification(title, content);
        });*/

        //AppRepository appRepository = new AppRepository(context);
        /**createNotificationChannel();
         showNotification(title, content);*/
    }

    public void getAllCoworkers() {
        coworkerRepository.getCoworkersCollection()
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allCoworkers = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            allCoworkers.add(document.toObject(Coworker.class));
                            Log.d("Error", "SIZE LIST RESTAURANT " + document.toObject(Coworker.class).getName());
                        }
                        getInfo();
                    } else {
                        Log.d("Error", "Error getting documents: ", task.getException());
                    }
                });

    }

    public void getInfo(){
        Coworker currentCoworker = null;
        String restaurantName;
        String uId = coworkerRepository.getCurrentCoworker().getUid();
        for (Coworker coworker:allCoworkers){
            if (coworker.getIdCoworker() == uId){
                currentCoworker = coworker;
                restaurantName = currentCoworker.getRestaurantName();
                createNotificationChannel();
                showNotification(restaurantName, restaurantName);
                break;
            }
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**CharSequence name = context.getString(R.string.notification_title);
             String description = context.getString(R.string.notification_message);
             int importance = NotificationManager.IMPORTANCE_DEFAULT;
             NotificationChannel channel = new NotificationChannel(String.valueOf(R.string.notification_receiver_manager_id), name, importance);
             channel.setDescription(description);
             NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
             notificationManager.createNotificationChannel(channel);*/
            CharSequence name = context.getString(R.string.notification_title);
            String description = context.getString(R.string.notification_message);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
