package com.example.p7_daa_alexandre.ui.details;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.ui.list.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private ArrayList<DetailsResponse> mDetailsRestaurant;

    public DetailsAdapter(ArrayList<DetailsResponse> restaurants) {
        mDetailsRestaurant = detailsRestaurants;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView restaurantName;
        private final TextView restaurantAddress;

        public ViewHolder (View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.linear_layout_restaurant_name);
            restaurantAddress = itemView.findViewById(R.id.linear_layout_restaurant_address);
        }

        public void bind(DetailsResponse restaurant) {
            restaurantName.setText(restaurant.getResult().getName());
            restaurantAddress.setText(restaurant.getResult().getAdrAddress());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, int position) {
        DetailsResponse restaurant = mDetailsRestaurant.get(position);
        holder.bind(restaurant);
    }

}
