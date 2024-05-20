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
import android.app.Activity;
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

    private ActivityResultLauncher<Intent> signInLauncher;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Define the launcher
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        handleResponseAfterSignIn(data);
                    } else {
                        // Handle other result codes and errors
                    }
                }
        );

        // Request permissions if needed
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build() // A enlever
        );

        // Launch the sign-in activity
        signInLauncher.launch(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.go4lunch_logo)
                        .build()
        );

        AppRepository appRepository = new AppRepository(this);
        appRepository.scheduleDailyNotification();
    }

    private void handleResponseAfterSignIn(@Nullable Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (response != null) {
            Toast.makeText(this, "Succès de la réponse !", Toast.LENGTH_SHORT).show();
            if (response.isSuccessful()) {
                CoworkerRepository.getInstance().createWorkmates();
                showSnackBar("connection_succeed");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Échec de la connexion !", Toast.LENGTH_SHORT).show();
                if (response.getError() != null) {
                    int errorCode = response.getError().getErrorCode();
                    if (errorCode == ErrorCodes.NO_NETWORK) {
                        showSnackBar("Erreur avec internet");
                    } else if (errorCode == ErrorCodes.UNKNOWN_ERROR) {
                        showSnackBar("Erreur inconnue");
                    }
                }
            }
        } else {
            showSnackBar("Erreur de l'authentification annulé !");
        }
    }

    private void showSnackBar(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}