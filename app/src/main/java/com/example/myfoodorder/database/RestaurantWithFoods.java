package com.example.myfoodorder.database;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myfoodorder.adapters.FoodInBagAdapter;
import com.example.myfoodorder.event.PriceUpdateEvent;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

public class RestaurantWithFoods implements Serializable {
    @Embedded
    public Restaurant restaurant;
    @Relation(
            parentColumn = "id",
            entityColumn = "restaurantID"
    )
    public List<Food> foods;

    public void setNullForListener() {
        for (Food food : foods) {
            food.setiClickItemCartListener(null);
        }
    }
    @BindingAdapter("list_food_in_order_restaurant")
    public static void loadFoodInOrderRestaurant(RecyclerView recyclerView, List<Food> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        // Load food in order restaurant
        FoodInBagAdapter mAdapter = new FoodInBagAdapter(list, (totalPrice, amount) -> {
            EventBus.getDefault().post(new PriceUpdateEvent(totalPrice, amount));
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public String toString() {
        return "RestaurantWithFoods{" +
                "restaurant=" + restaurant.getName() +
                ", foods=" + foods.toString() +
                '}';
    }
}
