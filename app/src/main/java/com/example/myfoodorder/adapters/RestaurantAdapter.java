package com.example.myfoodorder.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.databinding.ItemRestaurantListBinding;
import com.example.myfoodorder.models.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private final List<Restaurant> mListRestaurant;

    public RestaurantAdapter(List<Restaurant> mListRestaurant) {
        this.mListRestaurant = mListRestaurant;
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestaurantListBinding itemRestaurantListBinding = ItemRestaurantListBinding
                .inflate(LayoutInflater
                        .from(parent.getContext()), parent, false);
        return new RestaurantViewHolder(itemRestaurantListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
        Restaurant restaurant = mListRestaurant.get(position);
        if (restaurant == null) {
            return;
        }
        holder.itemRestaurantListBinding.setRestaurantModel(restaurant);
    }

    @Override
    public int getItemCount() {
        return (null == mListRestaurant ? 0 : mListRestaurant.size());
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ItemRestaurantListBinding itemRestaurantListBinding;

        public RestaurantViewHolder(ItemRestaurantListBinding itemRestaurantListBinding) {
            super(itemRestaurantListBinding.getRoot());
            this.itemRestaurantListBinding = itemRestaurantListBinding;
        }
    }
}
