package com.example.myfoodorder.viewmodels;

import android.app.Activity;

import com.example.myfoodorder.models.Restaurant;

public class RestaurantDetailViewModel {
    Activity mActivity;
    Restaurant restaurant;

    public RestaurantDetailViewModel(Activity mActivity, Restaurant mRestaurant) {
        this.mActivity = mActivity;
        this.restaurant = mRestaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
