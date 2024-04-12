package com.example.myfoodorder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.databinding.ItemFoodInOrderListBinding;
import com.example.myfoodorder.databinding.ItemOrderListBinding;
import com.example.myfoodorder.models.Food;

import java.util.List;

public class FoodInOrderAdapter extends RecyclerView.Adapter<FoodInOrderAdapter.FoodInOrderViewHolder>{

    private final List<Food> mListFoods;

    public FoodInOrderAdapter(List<Food> mListFoods) {
        this.mListFoods = mListFoods;
    }
    @NonNull
    @Override
    public FoodInOrderAdapter.FoodInOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodInOrderListBinding itemFoodInOrderListBinding = ItemFoodInOrderListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodInOrderAdapter.FoodInOrderViewHolder(itemFoodInOrderListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodInOrderAdapter.FoodInOrderViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        holder.itemFoodInOrderListBinding.setFoodModel(food);
    }

    @Override
    public int getItemCount() {
        return (null == mListFoods ? 0 : mListFoods.size());
    }

    public class FoodInOrderViewHolder extends RecyclerView.ViewHolder {
        ItemFoodInOrderListBinding itemFoodInOrderListBinding;
        public FoodInOrderViewHolder(ItemFoodInOrderListBinding itemFoodInOrderListBinding) {
            super(itemFoodInOrderListBinding.getRoot());
            this.itemFoodInOrderListBinding = itemFoodInOrderListBinding;
        }
    }
}
