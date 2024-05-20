package com.example.p7_daa_alexandre.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.databinding.ActivityDetailsBinding;
import com.example.p7_daa_alexandre.model.Coworker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    private DetailsViewModel viewModel;

    private DetailsAdapter adapter;

    private DetailsResponse details;

    private List<Coworker> coworkerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(DetailsViewModel.class);
        adapter = new DetailsAdapter(coworkerList);
        binding.listworkmatesjoining.setLayoutManager(new LinearLayoutManager(this));
        binding.listworkmatesjoining.setAdapter(adapter);
        String restaurant = getIntent().getStringExtra("restaurant");
        Log.d("DetailsActivity", "Retrieving restaurant details for: " + restaurant);
        viewModel.getRestaurantDetails(restaurant).observe(this, new Observer<DetailsResponse>() {
            @Override
            public void onChanged(DetailsResponse details) {
                DetailsActivity.this.details = details;
                if (details != null && details.getResult() != null) {
                    if (details.getResult().getName() == null){
                        binding.nameRestaurant.setText("No name available");
                    } else {
                        binding.nameRestaurant.setText(details.getResult().getName().toLowerCase(Locale.ROOT));
                    }
                    binding.adresseResto.setText(details.getResult().getVicinity());
                    binding.rating.setRating(details.getResult().getRating());

                    String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + details.getResult().getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";

                    Glide.with(DetailsActivity.this)
                            .load(urlPhoto)
                            .centerCrop()
                            .into(binding.pictureResto);
                } else {
                    binding.nameRestaurant.setText(R.string.restaurant_adapter_restaurant_name_error);
                    binding.adresseResto.setText(R.string.restaurant_adapter_restaurant_address_error);
                    binding.adresseResto.setText(R.string.restaurant_adapter_restaurant_rating_error);
                }
            }

        });

        viewModel.getCoworkerWhoChoseRestaurant(restaurant).observe(this, new Observer<List<Coworker>>() {
            @Override
            public void onChanged(List<Coworker> coworkers) {
                coworkerList.clear();
                coworkerList.addAll(coworkers);
                if (coworkers.isEmpty()) {
                    binding.listworkmatesjoining.setVisibility(View.GONE); // Or set a placeholder view
                    binding.textViewNoCoworker.setVisibility(View.VISIBLE); // Assuming a placeholder TextView
                } else {
                    binding.listworkmatesjoining.setVisibility(View.VISIBLE);
                    binding.textViewNoCoworker.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (details != null && details.getResult().getFormattedPhoneNumber() != null) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + details.getResult().getFormattedPhoneNumber()));
                    if (dialIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(dialIntent);
                    } else {
                        Toast.makeText(DetailsActivity.this, R.string.toast_details_activity_message_call_button, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, R.string.toast_details_activity_message_call_number_button, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (details != null && details.getResult().getWebsite() != null) {
                    String websiteUrl = details.getResult().getWebsite();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(webIntent);
                    } else {
                        Toast.makeText(DetailsActivity.this, R.string.toast_details_activity_message_website_button, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, R.string.toast_details_activity_message_website, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.choiceButton.setOnClickListener(v -> {
            if (details != null) {
                String placeId = details.getResult().getPlaceId();
                String restaurantName = details.getResult().getName();
                String address = details.getResult().getFormattedAddress();
                viewModel.restaurantChoosed(placeId, restaurantName, address);
                Toast.makeText(DetailsActivity.this, R.string.toast_details_activity_message_add_restaurant, Toast.LENGTH_SHORT).show();
            }
        });

    }

}