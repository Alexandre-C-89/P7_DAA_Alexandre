package com.example.p7_daa_alexandre.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    private RecyclerView recyclerView;

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
                //binding.adresseResto.setText(details.getResult().getPhotos());

                String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + details.getResult().getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";

                Glide.with(DetailsActivity.this)
                        .load(urlPhoto)
                        .centerCrop()
                        .into(binding.pictureResto);
            }

        });

        viewModel.getCoworkersLikedRestaurant(restaurant).observe(this, new Observer<List<Coworker>>() {
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

                // Vérifie si le numéro de téléphone est disponible
                if (details.getResult().getFormattedPhoneNumber() != null && !details.getResult().getFormattedPhoneNumber().isEmpty()) {
                    // Crée l'intent pour composer le numéro
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + details.getResult().getFormattedPhoneNumber()));

                    // Vérifie si l'application pour gérer l'intent est disponible
                    if (dialIntent.resolveActivity(getPackageManager()) != null) {
                        // Lance l'intent pour composer le numéro
                        startActivity(dialIntent);
                    } else {
                        // Gère le cas où aucune application pour gérer l'intent n'est disponible
                        Toast.makeText(DetailsActivity.this, "Aucune application pour gérer l'appel téléphonique n'est disponible.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Gère le cas où le numéro de téléphone n'est pas disponible
                    Toast.makeText(DetailsActivity.this, "Aucun numéro de téléphone disponible.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // clique sur website

        binding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assurez-vous que l'URL du site web est disponible dans les détails du restaurant
                if (details != null && details.getResult().getWebsite() != null && !details.getResult().getWebsite().isEmpty()) {
                    // Récupérez l'URL du site web du restaurant
                    String websiteUrl = details.getResult().getWebsite();

                    // Créez l'intent pour ouvrir le site web
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));

                    // Vérifiez si l'application pour gérer l'intent est disponible
                    if (webIntent.resolveActivity(getPackageManager()) != null) {
                        // Lancez l'intent pour ouvrir le site web
                        startActivity(webIntent);
                    } else {
                        // Gérez le cas où aucune application pour gérer l'intent n'est disponible
                        Toast.makeText(DetailsActivity.this, "Aucune application pour ouvrir le site web n'est disponible.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Gérez le cas où l'URL du site web n'est pas disponible
                    Toast.makeText(DetailsActivity.this, "Aucun site web disponible pour ce restaurant.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // clique sur le bouton de choix

        binding.choiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (details != null) {
                    // Appelez directement la méthode addLikeRestaurant du CoworkerRepository
                    CoworkerRepository coworkerRepository = CoworkerRepository.getInstance();
                    Restaurant restaurant = new Restaurant(details.getResult().getName(), details.getResult().getName(), details.getResult().getRating(), details.getResult().getBusinessStatus(), details.getResult().getIcon(), details.getResult().getWebsite(), details.getResult().getAdrAddress());
                    coworkerRepository.addLikeRestaurant(restaurant);
                    Toast.makeText(DetailsActivity.this, "Restaurant ajouté à vos favoris", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}