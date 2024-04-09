package com.example.myfoodorder.models;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.utils.GlideUtils;
import com.example.myfoodorder.views.activities.RestaurantDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends BaseObservable implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String imageUrl;
    private int rating;

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getRating() {
        return rating;
    }

    @BindingAdapter("restaurant_banner_image")
    public static void loadRestaurantBanner(ImageView imageView, String imageUrl) {
        GlideUtils.loadUrlBanner(imageUrl, imageView);
    }

    public void goToRestaurantDetail(View view) {
        // Go to restaurant detail
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_INTENT_RESTAURANT_OBJECT, this);
        GlobalFunction.startActivity(view.getContext(), RestaurantDetailActivity.class, bundle);
    }

}
