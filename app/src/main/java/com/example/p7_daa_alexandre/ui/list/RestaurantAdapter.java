package com.example.p7_daa_alexandre.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> mRestaurant;

    private OnItemClickListener mListener;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        mRestaurant = restaurants;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imgRestaurant;

        private final TextView restaurantName;

        private final TextView restaurantDistance;

        //private final TextView restaurantType;

        private final TextView restaurantAddress;

        //private final TextView restaurantCoworkerLike;

        public ViewHolder (View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgRestaurant = itemView.findViewById(R.id.linear_layout_restaurant_img);
            restaurantName = itemView.findViewById(R.id.linear_layout_restaurant_name);
            //restaurantType = itemView.findViewById(R.id.linear_layout_restaurant_type);
            //restaurantCoworkerLike = itemView.findViewById(R.id.linear_layout_restaurant_coworker_like);
            //restaurantTime = itemView.findViewById(R.id.linear_layout_restaurant_time);
            restaurantAddress = itemView.findViewById(R.id.linear_layout_restaurant_address);
            restaurantDistance = itemView.findViewById(R.id.linear_layout_restaurant_distance);
        }

        public void bind(Restaurant restaurant) {
            restaurantName.setText(restaurant.getName());
            imgRestaurant.setTag(restaurant);

            //final Coworker coworker = restaurant.getCoworker();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    void updateRestaurants(@NonNull final List<Restaurant> restaurants) {
        this.mRestaurant = restaurants;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = mRestaurant.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return mRestaurant.size();
    }
}

