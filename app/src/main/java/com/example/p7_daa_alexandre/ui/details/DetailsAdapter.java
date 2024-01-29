package com.example.p7_daa_alexandre.ui.details;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.details.DetailsResponse;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.ui.list.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private final ArrayList<DetailsResponse> mDetailsRestaurant;

    public DetailsAdapter(ArrayList<DetailsResponse> restaurants) {
        mDetailsRestaurant = restaurants;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView restaurantName;
        private final TextView restaurantAddress;
        private final ImageView restaurantPhotos;

        public ViewHolder (View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.linear_layout_restaurant_name);
            restaurantAddress = itemView.findViewById(R.id.linear_layout_restaurant_address);
            restaurantPhotos = itemView.findViewById(R.id.linear_layout_restaurant_img);
        }

        public void bind(DetailsResponse restaurant) {
            restaurantName.setText(restaurant.getResult().getName());
            restaurantAddress.setText(restaurant.getResult().getAdrAddress());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_coworker,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, int position) {
        DetailsResponse restaurant = mDetailsRestaurant.get(position);
        holder.bind(restaurant);
    }

    /**
     * Return number of ligne
     */
    @Override
    public int getItemCount() {
        return 0;
    }

}
