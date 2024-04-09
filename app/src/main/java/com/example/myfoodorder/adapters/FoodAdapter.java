package com.example.myfoodorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.databinding.ItemFoodListBinding;
import com.example.myfoodorder.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private final List<Food> mListFoods;

    public FoodAdapter(List<Food> mListFoods) {
        this.mListFoods = mListFoods;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodListBinding itemFoodListBinding = ItemFoodListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(itemFoodListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        holder.itemFoodListBinding.setFoodModel(food);
    }

    @Override
    public int getItemCount() {
        return (null == mListFoods ? 0 : mListFoods.size());
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        ItemFoodListBinding itemFoodListBinding;

        public FoodViewHolder(ItemFoodListBinding itemFoodListBinding) {
            super(itemFoodListBinding.getRoot());
            this.itemFoodListBinding = itemFoodListBinding;
        }
    }
}
