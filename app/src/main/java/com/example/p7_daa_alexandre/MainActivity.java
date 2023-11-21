package com.example.p7_daa_alexandre;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.p7_daa_alexandre.databinding.ActivityMainBinding;
import com.example.p7_daa_alexandre.ui.home.HomeFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<AuthUI.IdpConfig> providers =
                Arrays.asList(
                        new AuthUI.IdpConfig.FacebookBuilder().build(),
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
                        .setLogo(R.mipmap.ic_launcher_round)
                        .build(),
                RC_SIGN_IN);
    }


    /**@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }*/
    // Method that handles response after SignIn Activity close
    //Nous avons créé une méthode  handleResponseAfterSignIn permettant de récupérer plus facilement
    // le résultat renvoyé par l'activité de connexion/inscription auto-générée par FirebaseUI
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                //userManager.createUser();
                //WorkmateRepository.getInstance().createWorkmates();
                showSnackBar("connection_succeed");
                Toast.makeText( this, "connection_succeed", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), HomeFragment.class);
                startActivity(intent);

            } else {
                // ERRORS
                if (response == null) {
                    showSnackBar("error_authentication_canceled");
                } else if (response.getError()!= null) {
                    if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                        showSnackBar("error_no_internet");
                    } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        showSnackBar("error_unknown_error");
                        Toast.makeText( this, "error_unknown_error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    // Show Snack Bar with a message
    private void showSnackBar( String message){
        //Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT).show();
        Toast.makeText( this, message, Toast.LENGTH_SHORT).show();
    }

}