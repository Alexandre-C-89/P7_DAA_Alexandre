package com.example.p7_daa_alexandre;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.p7_daa_alexandre.databinding.ActivityMainBinding;
import com.example.p7_daa_alexandre.ui.home.HomeFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ActivityMainBinding binding;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected ActivityMainBinding getViewBinding() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListeners();
    }

    private void setupListeners(){
        // Login Button
        binding.loginButton.setOnClickListener(view -> {
            startSignInActivity();
        });
    }

    private void startSignInActivity(){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);
    }

}