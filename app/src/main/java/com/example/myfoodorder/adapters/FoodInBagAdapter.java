package com.example.myfoodorder.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.database.AppDatabase;
import com.example.myfoodorder.databinding.ItemFoodInBagListBinding;
import com.example.myfoodorder.listeners.ICalculatePriceListener;
import com.example.myfoodorder.listeners.IClickItemInBagListener;
import com.example.myfoodorder.models.Food;

import java.util.List;

public class FoodInBagAdapter extends RecyclerView.Adapter<FoodInBagAdapter.FoodInBagViewHolder>
        implements IClickItemInBagListener{
    private final List<Food> mListFoods;

    ICalculatePriceListener iCalculatePriceListener;
    public FoodInBagAdapter(List<Food> mListFoods, ICalculatePriceListener listener) {
        this.mListFoods = mListFoods;
        this.iCalculatePriceListener = listener;
    }

    @NonNull
    @Override
    public FoodInBagAdapter.FoodInBagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodInBagListBinding itemFoodInBagListBinding = ItemFoodInBagListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodInBagAdapter.FoodInBagViewHolder(itemFoodInBagListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodInBagAdapter.FoodInBagViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        food.setAdapterPosition(holder.getAdapterPosition());
        food.setiClickItemCartListener(this);
        holder.itemFoodInBagListBinding.setFoodModel(food);
    }

    @Override
    public int getItemCount() {
        return (null == mListFoods ? 0 : mListFoods.size());
    }

    @Override
    public void clickDeteteFood(Context context, Food food, int position) {
        showConfirmDialogDeleteFood(context, food, position);
    }

    private void showConfirmDialogDeleteFood(Context context, Food food, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete food")
                .setMessage("Are you sure you want to delete this food?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    AppDatabase.getInstance(context).foodDAO().deleteFood(food);

                    mListFoods.remove(position);
                    notifyItemRemoved(position);
                    calculateTotalPrice(context);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void updateItemFood(Context context, Food food, int position) {
        AppDatabase.getInstance(context).foodDAO().updateFood(food);
        notifyItemChanged(position);
        calculateTotalPrice(context);
    }

    private void calculateTotalPrice(Context context) {
        List<Food> listFoodCart = AppDatabase.getInstance(context).foodDAO().getListFoodBag();
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            String strZero = 0 + " VND";
            iCalculatePriceListener.calculatePrice(strZero, 0);
            return;
        }

        int totalPrice = 0;
        for (Food food : listFoodCart) {
            totalPrice = totalPrice + food.getTotalPrice();
        }

        String totalString = totalPrice + " VND";
        iCalculatePriceListener.calculatePrice(totalString, totalPrice);
    }

    public static class FoodInBagViewHolder extends RecyclerView.ViewHolder {
        ItemFoodInBagListBinding itemFoodInBagListBinding;

        public FoodInBagViewHolder(ItemFoodInBagListBinding itemFoodInBagListBinding) {
            super(itemFoodInBagListBinding.getRoot());
            this.itemFoodInBagListBinding = itemFoodInBagListBinding;
        }
    }
}
