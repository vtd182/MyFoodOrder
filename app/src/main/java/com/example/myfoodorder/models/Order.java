package com.example.myfoodorder.models;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.database.RestaurantWithFoods;
import com.example.myfoodorder.views.activities.OrderDetailActivity;
import com.example.myfoodorder.views.activities.RestaurantDetailActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order extends BaseObservable implements Serializable {
    private String id;
    private String userId;
    private String name;
    private String phone;
    private String paymentMethod;
    private String address;

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    private String orderDate;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private RestaurantWithFoods restaurantWithFoods;

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RestaurantWithFoods getRestaurantWithFoods() {
        return restaurantWithFoods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRestaurantWithFoods(RestaurantWithFoods restaurantWithFoods) {
        this.restaurantWithFoods = restaurantWithFoods;
    }

    public void calculateAmount() {
        if (restaurantWithFoods != null && restaurantWithFoods.foods != null) {
            int total = 0;
            for (Food food : restaurantWithFoods.foods) {
                total = total + food.getTotalPrice();
            }
            amount = total;
        }
    }

    public void goToOrderDetail(View view) {
        // Go to restaurant detail
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_INTENT_ORDER_OBJECT, this);
        GlobalFunction.startActivity(view.getContext(), OrderDetailActivity.class, bundle);
    }
}
