package com.example.p7_daa_alexandre.ui.yourlunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.databinding.FragmentYourLunchBinding;

public class YourLunchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private YourLunchViewModel viewModel;

    FragmentYourLunchBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**public YourLunchFragment() {
        // Required empty public constructor
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentYourLunchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(YourLunchViewModel.class);

        // Placez ici le code pour récupérer les détails du restaurant choisi
        String placeId = getArguments().getString("placeId");
        viewModel.getRestaurantDetails(placeId).observe(getViewLifecycleOwner(), detailsResponse -> {
            if (detailsResponse != null) {
                // take info with detailsResponse
                String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + detailsResponse.getResult().getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";
                Glide.with(YourLunchFragment.this)
                        .load(urlPhoto)
                        .centerCrop()
                        .into(binding.yourLunchImgRestaurant);
                binding.yourLunchNameRestaurant.setText(detailsResponse.getResult().getName());
                binding.yourLunchAddressRestaurant.setText(detailsResponse.getResult().getFormattedAddress());
            }
        });

    }

}