package com.example.myfoodorder.models;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.utils.GlideUtils;
import com.example.myfoodorder.views.activities.RestaurantDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "restaurant")
public class Restaurant extends BaseObservable implements Serializable {
    @PrimaryKey
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

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

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
