package com.example.p7_daa_alexandre.ui.details;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.databinding.ActivityDetailsBinding;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.repository.CoworkerRepository;

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

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailsViewModel.class);
        adapter = new DetailsAdapter(coworkerList);
        binding.listworkmatesjoining.setLayoutManager(new LinearLayoutManager(this));
        binding.listworkmatesjoining.setAdapter(adapter);
        String restaurant = getIntent().getStringExtra("restaurant");
        viewModel.getRestaurantDetails(restaurant).observe(this, new Observer<DetailsResponse>() {
            @Override
            public void onChanged(DetailsResponse details) {
                DetailsActivity.this.details = details;
                //int i = 0;
                binding.nameRestaurant.setText(details.getResult().getName().toLowerCase(Locale.ROOT));
                binding.adresseResto.setText(details.getResult().getVicinity());
                binding.rating.setRating(details.getResult().getRating());

                String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + details.getResult().getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";

                Glide.with(DetailsActivity.this)
                        .load(urlPhoto)
                        .centerCrop()
                        .into(binding.pictureResto);
            }

        });

        viewModel.getCoworkerWhoChoseRestaurant(restaurant).observe(this, new Observer<List<Coworker>>() {
            @Override
            public void onChanged(List<Coworker> coworkers) {
                coworkerList.clear();
                coworkerList.addAll(coworkers);
                adapter.notifyDataSetChanged();
            }
        });

        // clique sur phone
        binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (details.getResult().getFormattedPhoneNumber() != null && !details.getResult().getFormattedPhoneNumber().isEmpty()) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + details.getResult().getFormattedPhoneNumber()));
                    if (dialIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(dialIntent);
                    } else {
                        Toast.makeText(DetailsActivity.this, "Aucune application pour gérer l'appel téléphonique n'est disponible.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "Aucun numéro de téléphone disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // clique sur website
        binding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (details != null && details.getResult().getWebsite() != null && !details.getResult().getWebsite().isEmpty()) {
                    String websiteUrl = details.getResult().getWebsite();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(webIntent);
                    } else {
                        Toast.makeText(DetailsActivity.this, "Aucune application pour ouvrir le site web n'est disponible.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "Aucun site web disponible pour ce restaurant.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click on button choice
        binding.choiceButton.setOnClickListener(v -> {
            if (details != null) {
                String placeId = details.getResult().getPlaceId();
                String restaurantName = details.getResult().getName();
                String address= details.getResult().getFormattedAddress();
                viewModel.restaurantChoosed(placeId, restaurantName, address);
                Toast.makeText(DetailsActivity.this, "Restaurant ajouté à vos favoris", Toast.LENGTH_SHORT).show();
            }
        });

    }

}