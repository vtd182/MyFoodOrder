package com.example.myfoodorder.viewmodels;

import android.app.Activity;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.adapters.FoodAdapter;
import com.example.myfoodorder.callbacks.FirebaseCallBack;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;

import java.util.List;

public class RestaurantDetailViewModel {
    Activity mActivity;
    Restaurant restaurant;
    ObservableList<Food> listFoods = new ObservableArrayList<>();

    public ObservableList<Food> getListFoods() {
        return listFoods;
    }

    public void setListFoods(ObservableList<Food> listFoods) {
        this.listFoods = listFoods;
    }

    public RestaurantDetailViewModel(Activity mActivity, Restaurant mRestaurant) {
        this.mActivity = mActivity;
        this.restaurant = mRestaurant;
        initData();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    private void initData() {
        if (restaurant == null) {
            return;
        }
        // get list of foods from restaurant
        ControllerApplication app = ControllerApplication.get(mActivity);
        app.getFoodsByRestaurantId(restaurant.getId(), data -> {
            listFoods.clear();
            listFoods.addAll(data);
        });
    }

    @BindingAdapter("list_food_data")
    public static void loadListFoods(RecyclerView recyclerView, ObservableList<Food> foods) {
        if (foods == null) {
            return;
        }
        FoodAdapter foodAdapter = new FoodAdapter(foods);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(foodAdapter);
    }

    public void onClickButtonBack() {
        mActivity.onBackPressed();
    }

}
