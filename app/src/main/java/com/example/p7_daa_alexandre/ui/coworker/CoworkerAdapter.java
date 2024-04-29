package com.example.p7_daa_alexandre.ui.coworker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.database.response.nearbysearch.ResultsItem;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.ui.details.DetailsActivity;
import com.example.p7_daa_alexandre.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerAdapter.ViewHolder> {

    private List<Coworker> mCoworker;

    private OnItemClickListener mListener;

    public CoworkerAdapter(List<Coworker> coworkers) {
        mCoworker = coworkers;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imgCoworker;

        private final TextView coworkerRestaurantNameLiked;


        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgCoworker = itemView.findViewById(R.id.linear_layout_coworker_img);
            coworkerRestaurantNameLiked = itemView.findViewById(R.id.linear_layout_coworker_name_restaurant_liked);
        }

        public void bind(Coworker coworker) {
            String restaurantName = coworker.getRestaurantName();
            String concatenedText = coworker.getName() + " (" + restaurantName + ")";
            coworkerRestaurantNameLiked.setText(concatenedText);
            imgCoworker.setTag(coworker);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coworker, parent, false);
        return new CoworkerAdapter.ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coworker coworker = mCoworker.get(position);
        holder.bind(coworker);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coworker.getPlaceId() != null) {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("restaurant", coworker.getPlaceId());
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "pas de restaurant sélectionner", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCoworker.size();
    }


}
