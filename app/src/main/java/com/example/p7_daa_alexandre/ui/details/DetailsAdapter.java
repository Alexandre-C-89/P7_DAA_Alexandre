package com.example.p7_daa_alexandre.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.model.Coworker;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private final List<Coworker> mCoworkers;

    public DetailsAdapter(List<Coworker> coworkers) {
        mCoworkers = coworkers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView coworkerNameRestaurantliked;
        private final ImageView coworkerPhoto;

        public ViewHolder (View itemView) {
            super(itemView);
            coworkerNameRestaurantliked = itemView.findViewById(R.id.detail_restaurant_coworker_name_restaurant_liked);
            coworkerPhoto = itemView.findViewById(R.id.detail_restaurant_coworker_img);
        }

        public void bind(Coworker coworker) {

            coworkerNameRestaurantliked.setText(coworker.getName());
            Glide.with(itemView).load(coworker.getPicture()).into(coworkerPhoto);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_coworker,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coworker coworker = mCoworkers.get(position);
        holder.bind(coworker);
    }

    /**
     * Return number of ligne
     */
    @Override
    public int getItemCount() {
        return mCoworkers.size();
    }

}
