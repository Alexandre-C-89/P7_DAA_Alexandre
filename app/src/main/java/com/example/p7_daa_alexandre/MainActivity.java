package com.example.p7_daa_alexandre;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.p7_daa_alexandre.databinding.ActivityMainBinding;
import com.example.p7_daa_alexandre.repository.AppRepository;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;
import com.example.p7_daa_alexandre.ui.home.HomeActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    ActivityMainBinding binding;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(MainActivity.this, R.string.notification_toast_message_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.notification_toast_message_error, Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        List<AuthUI.IdpConfig> providers =
                Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.TwitterBuilder().build()
                );

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.go4lunch_logo)
                        .build(),
                RC_SIGN_IN);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "test")
                .setSmallIcon(R.drawable.world_svgrepo_com)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_message_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        AppRepository appRepository = new AppRepository(this);
        //appRepository.createNotificationChannel();

        appRepository.scheduleDailyNotification();
        /**try {
            appRepository.scheduleDailyNotification();
            Log.d("MainActivity", "Daily notification scheduled successfully");
        } catch (Exception e) {
            Log.e("MainActivity", "Error scheduling daily notification", e);
            // Handle the error as needed
        }*/
        
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            Toast.makeText( this, R.string.toast_succes, Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_OK) {
                CoworkerRepository.getInstance().createWorkmates();
                showSnackBar("connection_succeed");
                Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText( this, R.string.toast_succes, Toast.LENGTH_SHORT).show();
                if (response == null) {
                    showSnackBar(String.valueOf(R.string.snackbar_authentication_error));
                } else if (response.getError()!= null) {
                    if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                        showSnackBar(String.valueOf(R.string.snackbar_internet_error));
                    } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        showSnackBar(String.valueOf(R.string.snackbar_unknow_error));
                    }
                }
            }
        }
    }

    private void showSnackBar( String message){
        Toast.makeText( this, message, Toast.LENGTH_SHORT).show();
    }


}