package com.example.p7_daa_alexandre.ui.details;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.databinding.ActivityDetailsBinding;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    private DetailsViewModel viewModel;

    private RecyclerView recyclerView;

    private DetailsAdapter adapter;

    private DetailsResponse details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailsViewModel.class);

        binding.listworkmatesjoining.setAdapter(adapter);
        String restaurant = getIntent().getStringExtra("restaurant");
        viewModel.getRestaurantDetails(restaurant).observe(this, new Observer<DetailsResponse>() {

            @Override
            public void onChanged(DetailsResponse details) {
                //int i = 0;
                binding.nameRestaurant.setText(details.getResult().getName().toLowerCase(Locale.ROOT));
                binding.adresseResto.setText(details.getResult().getVicinity());
                binding.rating.setRating(details.getResult().getRating());
                //binding.adresseResto.setText(details.getResult().getPhotos());

                Glide.with(DetailsActivity.this)
                        .load(details.getResult().getPhotos())
                        .centerCrop()
                        //.error(R.drawable.no_image_icon)
                        .into(binding.pictureResto);
            }

            // clique sur website
            //onWebsiteClick();
            // clique sur phone

        });
    }

}