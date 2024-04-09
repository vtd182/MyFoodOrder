package com.example.myfoodorder.models;

import android.database.Observable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;

import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.utils.GlideUtils;
import com.example.myfoodorder.views.activities.FoodDetailActivity;

import java.io.Serializable;

@Entity(tableName = "food")
public class Food extends BaseObservable implements Serializable {
    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private int price;
    private int availableQuantity;

    private int orderQuantity = 0;

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    private int restaurantId;

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    @BindingAdapter("food_image")
    public static void loadImage(ImageView imageView, String imageUrl) {
        GlideUtils.loadUrlBanner(imageUrl, imageView);
    }

    public void goToFoodDetail(View view) {
        // Go to food detail
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_INTENT_FOOD_OBJECT, this);
        GlobalFunction.startActivity(view.getContext(), FoodDetailActivity.class, bundle);
    }
}
