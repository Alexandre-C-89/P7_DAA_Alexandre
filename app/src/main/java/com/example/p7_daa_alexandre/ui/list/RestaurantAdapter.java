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
import android.location.Location;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<ResultsItem> mRestaurant;
    private static Map<String, Integer> likesMap = new HashMap<>();

    static Location userLocation;

    public RestaurantAdapter(ArrayList<ResultsItem> restaurants, Map<String, Integer> likesMap, Location userLocation) {
        mRestaurant = restaurants;
        this.likesMap = likesMap;
        this.userLocation = userLocation;
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
            String distanceText = calculateDistance(userLocation, userLocation.getLatitude(), userLocation.getLongitude());
            restaurantDistance.setText(distanceText);
            if (restaurant.getPhotos() != null && !restaurant.getPhotos().isEmpty()) {
                imgRestaurant.setTag(restaurant.getPhotos());
                String urlPhoto = R.string.restaurant_adapter_url_photo
                        + restaurant.getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";
                Glide.with(imgRestaurant.getRootView())
                        .load(urlPhoto)
                        .centerCrop()
                        .into(imgRestaurant);
            } else {
                imgRestaurant.setImageResource(R.drawable.no_image_icon);
            }
            restaurantAddress.setText(restaurant.getVicinity());
            Integer likesCount = likesMap.get(restaurant.getPlaceId());
            restaurantCoworker.setText(likesCount == null ? String.valueOf(R.string.restaurant_adapter_like_number) : likesCount + String.valueOf(R.string.restaurant_adapter_like_number));
            if (restaurant.getOpeningHours() != null) {
                restaurantHour.setText(restaurant.getOpeningHours().isOpenNow() ? String.valueOf(R.string.restaurant_adapter_restaurant_hour_open) : String.valueOf(R.string.restaurant_adapter_restaurant_hour_closed));
            } else {
                restaurantHour.setText(String.valueOf(R.string.restaurant_adapter_restaurant_hour_error));
            }
            restaurantRating.setTag(restaurant.getRating());
        }

        private String calculateDistance(Location startLocation, double endLatitude, double endLongitude) {
            if (startLocation != null) {
                float[] results = new float[1];
                Location.distanceBetween(startLocation.getLatitude(), startLocation.getLongitude(), endLatitude, endLongitude, results);
                float distanceInMeters = results[0];
                return String.format(Locale.US, String.valueOf(R.string.restaurant_adapter_restaurant_local_us), distanceInMeters / 1000); // Convert meters to kilometers
            }
            return String.valueOf(R.string.restaurant_adapter_restaurant_distance_message_error);
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
        float adjustedRating = adjustRating(restaurant.getRating());
        holder.restaurantRating.setRating(adjustedRating);
        holder.bind(restaurant);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(String.valueOf(R.string.restaurant_adapter_intent_message), restaurant.getPlaceId());
                view.getContext().startActivity(intent);
            }
        });
    }

    private float adjustRating(double rating) {
        // Map the rating from 0-5 scale to 0-3 scale
        return (float) ((rating / 5.0) * 3.0);
    }

    @Override
    public int getItemCount() {
        return mRestaurant.size();
    }

    public void setLikesMap(Map<String, Integer> likesMap) {
        this.likesMap = likesMap;
        notifyDataSetChanged(); // Refresh data when the likes map is updated
    }
}

