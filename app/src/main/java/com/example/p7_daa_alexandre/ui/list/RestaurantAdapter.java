package com.example.p7_daa_alexandre.ui.list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.ui.details.DetailsActivity;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<ResultsItem> mRestaurant;

    public RestaurantAdapter(ArrayList<ResultsItem> restaurants) {
        mRestaurant = restaurants;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView restaurantName;
        private final TextView restaurantDistance;
        private final AppCompatImageView imgRestaurant;
        private final TextView restaurantAddress;
        private final TextView restaurantCoworker;
        private final TextView restaurantHour;
        private final RatingBar restaurantRating;

        public ViewHolder (View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.linear_layout_restaurant_name);
            restaurantDistance = itemView.findViewById(R.id.linear_layout_restaurant_distance);
            imgRestaurant = itemView.findViewById(R.id.linear_layout_restaurant_img);
            restaurantAddress = itemView.findViewById(R.id.linear_layout_restaurant_address);
            restaurantCoworker = itemView.findViewById(R.id.linear_layout_restaurant_number_of_coworker);
            restaurantHour = itemView.findViewById(R.id.linear_layout_restaurant_hour);
            restaurantRating = itemView.findViewById(R.id.linear_layout_restaurant_rating);

        }

        public void bind(ResultsItem restaurant) {
            restaurantName.setText(restaurant.getName());
            restaurantDistance.setText(restaurant.getScope());
            if (restaurant.getPhotos() != null && !restaurant.getPhotos().isEmpty()) {
                imgRestaurant.setTag(restaurant.getPhotos());
                String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                        + restaurant.getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";
                Glide.with(imgRestaurant.getRootView())
                        .load(urlPhoto)
                        .centerCrop()
                        .into(imgRestaurant);
            } else {
                imgRestaurant.setImageResource(R.drawable.no_image_icon);
            }
            restaurantAddress.setText(restaurant.getVicinity());
            restaurantCoworker.setTag(restaurant.getUserRatingsTotal());
            if (restaurant.getOpeningHours() != null) {
                // Assuming getReadableHours() method exists and returns a String
                restaurantHour.setText(restaurant.getOpeningHours().isOpenNow() ? "Open" : "Closed");
            } else {
                restaurantHour.setText("No hours available"); // Placeholder text
            }
            restaurantRating.setTag(restaurant.getRating());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultsItem restaurant = mRestaurant.get(position);
        holder.restaurantRating.setRating((float) restaurant.getRating());
        holder.bind(restaurant);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("restaurant", restaurant.getPlaceId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurant.size();
    }
}

