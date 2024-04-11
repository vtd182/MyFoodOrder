package com.example.myfoodorder.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.database.RestaurantWithFoods;
import com.example.myfoodorder.databinding.ItemBagListBinding;

import java.util.List;

public class RestaurantWithFoodsInBagAdapter extends
        RecyclerView.Adapter<RestaurantWithFoodsInBagAdapter.RestaurantWithFoodsInBagViewHolder>
{
    private List<RestaurantWithFoods> restaurantList;

    public RestaurantWithFoodsInBagAdapter(List<RestaurantWithFoods> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantWithFoodsInBagAdapter.RestaurantWithFoodsInBagViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBagListBinding itemBagListBinding = ItemBagListBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RestaurantWithFoodsInBagViewHolder(itemBagListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantWithFoodsInBagAdapter.
            RestaurantWithFoodsInBagViewHolder holder, int position) {
        RestaurantWithFoods restaurantWithFoods = restaurantList.get(position);
        if (restaurantWithFoods == null || restaurantWithFoods.foods.isEmpty()) {
            // Nếu restaurantWithFoods là null hoặc foodsList rỗng, loại bỏ nó ra khỏi danh sách
            return;
        }
        holder.itemBagListBinding.setRestaurantWithFoods(restaurantWithFoods);
    }

    @Override
    public int getItemCount() {
        return (null == restaurantList ? 0 : restaurantList.size());
    }


    public class RestaurantWithFoodsInBagViewHolder extends RecyclerView.ViewHolder {
        ItemBagListBinding itemBagListBinding;
        public RestaurantWithFoodsInBagViewHolder (ItemBagListBinding itemBagListBinding) {
            super(itemBagListBinding.getRoot());
            this.itemBagListBinding = itemBagListBinding;
        }
    }
}
