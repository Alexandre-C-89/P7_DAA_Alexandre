package com.example.p7_daa_alexandre.repository;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.p7_daa_alexandre.MyNotificationReceiver;
import com.example.p7_daa_alexandre.R;

import java.util.Calendar;
import java.util.TimeZone;

public class AppRepository {

    private final Context context;

    public AppRepository(Context context) {
        this.context = context;
    }

    public void scheduleDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyNotificationReceiver.class); // MyNotificationReceiver is a BroadcastReceiver that triggers the notification
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to start immediately and repeat every 5 minutes
        long intervalMillis = AlarmManager.INTERVAL_FIFTEEN_MINUTES / 3; // Calculate interval for 5 minutes
        long triggerAtMillis = System.currentTimeMillis();

        // With setInexactRepeating(), you have to use one of the AlarmManager interval constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
    }

}
