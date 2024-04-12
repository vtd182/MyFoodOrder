package com.example.myfoodorder.viewmodels;

import android.app.Activity;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.adapters.FoodAdapter;
import com.example.myfoodorder.adapters.FoodInOrderAdapter;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Order;
import com.example.myfoodorder.models.Restaurant;

public class OrderDetailViewModel {
    Activity mActivity;
    Order order;
    ObservableList<Food> listFoods = new ObservableArrayList<>();

    public ObservableList<Food> getListFoods() {
        return listFoods;
    }

    public void setListFoods(ObservableList<Food> listFoods) {
        this.listFoods = listFoods;
    }

    public OrderDetailViewModel(Activity mActivity, Order order) {
        this.mActivity = mActivity;
        this.order = order;
        initData();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private void initData() {
        if (order == null) {
            return;
        }
        // get list of foods from restaurant
        if (order.getRestaurantWithFoods().foods != null) {
            listFoods.addAll(order.getRestaurantWithFoods().foods);
        }
    }

    @BindingAdapter("list_food_data_order_detail")
    public static void loadListFoods(RecyclerView recyclerView, ObservableList<Food> foods) {
        if (foods == null) {
            return;
        }
        FoodInOrderAdapter foodInOrderAdapter = new FoodInOrderAdapter(foods);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(foodInOrderAdapter);
    }

    public void onClickButtonBack() {
        mActivity.onBackPressed();
    }

}
