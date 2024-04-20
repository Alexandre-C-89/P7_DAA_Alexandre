package com.example.p7_daa_alexandre;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.repository.AppRepository;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MyNotificationReceiver extends BroadcastReceiver {

    private Context context;

    private CoworkerRepository coworkerRepository;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        // Assuming you have some logic to fetch restaurant details here
        /**String title = "Restaurant of the Day";
         String content = "Check out today's featured restaurant!";*/
        String placeId = intent.getStringExtra(String.valueOf(R.string.notification_receiver_message_id));
        CompletableFuture<String> restaurantNameFuture = coworkerRepository.getCoworkerRestaurantName(placeId);
        restaurantNameFuture.thenAccept(restaurantName -> {
            // Assuming you have some logic to fetch restaurant details here
            String title = String.valueOf(R.string.notification_receiver_title);
            String content = R.string.notification_receiver_title + restaurantName;

            // Create notification channel and show notification
            createNotificationChannel(restaurantName);
            showNotification(title, content);
        });

        //AppRepository appRepository = new AppRepository(context);
        /**createNotificationChannel();
         showNotification(title, content);*/
    }

    public void createNotificationChannel(String restaurantName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**CharSequence name = context.getString(R.string.notification_title);
             String description = context.getString(R.string.notification_message);
             int importance = NotificationManager.IMPORTANCE_DEFAULT;
             NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
             channel.setDescription(description);
             NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
             notificationManager.createNotificationChannel(channel);*/
            CharSequence name = restaurantName;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(R.string.notification_receiver_manager_id), name, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(R.string.notification_receiver_manager_id))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
