package com.example.p7_daa_alexandre.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p7_daa_alexandre.BaseActivity;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {

    private UserManager userManager = UserManager.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListeners();
        updateUIWithUserData();
    }

    private void setupListeners(){
        binding.updateButton.setOnClickListener(view -> { });
        binding.signOutButton.setOnClickListener(view -> { });
        binding.deleteButton.setOnClickListener(view -> { });
    }

    private void updateUIWithUserData(){
        if(userManager.isCurrentUserLogged()){
            FirebaseUser user = userManager.getCurrentUser();

            if(user.getPhotoUrl() != null){
                setProfilePicture(user.getPhotoUrl());
            }
            setTextUserData(user);
        }
    }

    private void setProfilePicture(Uri profilePictureUrl){
        Glide.with(this)
                .load(profilePictureUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.profileImageView);
    }

    private void setTextUserData(FirebaseUser user){

        //Get email & username from User
        String email = TextUtils.isEmpty(user.getEmail()) ? getString(R.string.info_no_email_found) : user.getEmail();
        String username = TextUtils.isEmpty(user.getDisplayName()) ? getString(R.string.info_no_username_found) : user.getDisplayName();

        //Update views with data
        binding.usernameEditText.setText(username);
        binding.emailTextView.setText(email);
    }
}
