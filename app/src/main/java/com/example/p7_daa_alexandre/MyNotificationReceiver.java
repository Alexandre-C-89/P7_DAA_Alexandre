package com.example.p7_daa_alexandre;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.p7_daa_alexandre.repository.AppRepository;

public class MyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Assuming you have some logic to fetch restaurant details here
        String title = "Restaurant of the Day";
        String content = "Check out today's featured restaurant!";

        AppRepository appRepository = new AppRepository(context);
        appRepository.showNotification(title, content);
    }
}
