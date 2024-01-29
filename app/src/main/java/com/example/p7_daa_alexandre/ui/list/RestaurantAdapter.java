package com.example.p7_daa_alexandre.ui.list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Restaurant;
import com.example.p7_daa_alexandre.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<ResultsItem> mRestaurant;

    //private OnItemClickListener mListener;

    public RestaurantAdapter(ArrayList<ResultsItem> restaurants) {
        mRestaurant = restaurants;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView restaurantName;
        private final TextView restaurantDistance;
        private final AppCompatImageView imgRestaurant;
        //private final TextView restaurantType;
        private final TextView restaurantAddress;
        private final TextView restaurantCoworker;
        private final TextView restaurantHour;
        private final TextView restaurantRating;

        public ViewHolder (View itemView) {
            super(itemView);

            //onClickRestaurant = itemView.findViewById(R.id.list_restaurants);
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
            // Calcule de la distance par rapport à ma position
            restaurantDistance.setText(restaurant.getBusinessStatus());
            // Vérifiez si la liste de photos n'est pas null et qu'elle a au moins une photo
            if (restaurant.getPhotos() != null && !restaurant.getPhotos().isEmpty()) {
                imgRestaurant.setTag(restaurant.getPhotos());

                String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                        + restaurant.getPhotos().get(0).getPhotoReference() + "&key=AIzaSyCdjoEFb1ArPZYQBXpBdkkmIMdUaGycFow";

                Glide.with(imgRestaurant.getRootView())
                        .load(urlPhoto)
                        .centerCrop()
                        .into(imgRestaurant);
            } else {
                // Gérez le cas où la liste de photos est null ou vide
                imgRestaurant.setImageResource(R.drawable.no_image_icon); // Remplacez avec votre image par défaut
            }

            restaurantAddress.setText(restaurant.getVicinity());
            restaurantCoworker.setTag(restaurant.getUserRatingsTotal());
            // utiliser open_now pour récupérer le boolean
            restaurantHour.setTag(restaurant.getOpeningHours());
            restaurantRating.setTag(restaurant.getRating());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(itemView);
    }

    void updateRestaurants(@NonNull final ArrayList<ResultsItem> restaurants) {
        this.mRestaurant = restaurants;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultsItem restaurant = mRestaurant.get(position);
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

