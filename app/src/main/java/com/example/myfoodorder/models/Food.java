package com.example.myfoodorder.models;

import android.database.Observable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.listeners.IClickItemInBagListener;
import com.example.myfoodorder.utils.GlideUtils;
import com.example.myfoodorder.views.activities.FoodDetailActivity;
import com.google.firebase.database.Exclude;

import java.io.Serializable;



@Entity(foreignKeys = @ForeignKey(entity = Restaurant.class,
        parentColumns = "id",
        childColumns = "restaurantID"))
public class Food extends BaseObservable implements Serializable {
    @PrimaryKey
    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private int price;
    private int availableQuantity;
    private int orderQuantity = 1;

    @Ignore
    @com.google.firebase.database.Exclude
    private int adapterPosition;

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    @com.google.firebase.database.Exclude
    @Ignore
    IClickItemInBagListener iClickItemCartListener;


    public int getTotalPrice() {
        if (totalPrice == 0) {
            totalPrice = price;
        }
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    private int totalPrice;

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    private int restaurantID;

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
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

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
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

    public void onClickButtonSubtract(View view) {
        int count = getOrderQuantity();
        if (count <= 1) {
            return;
        }
        int newCount = count - 1;

        int totalPrice = getPrice() * newCount;
        setOrderQuantity(newCount);
        setTotalPrice(totalPrice);

        iClickItemCartListener.updateItemFood(view.getContext(), this, getAdapterPosition());
    }

    public void onClickButtonAdd(View view) {
        int newCount = getOrderQuantity() + 1;

        int totalPrice = getPrice() * newCount;
        setOrderQuantity(newCount);
        setTotalPrice(totalPrice);

        iClickItemCartListener.updateItemFood(view.getContext(), this, getAdapterPosition());
    }

    public IClickItemInBagListener getiClickItemCartListener() {
        return iClickItemCartListener;
    }

    public void setiClickItemCartListener(IClickItemInBagListener iClickItemCartListener) {
        this.iClickItemCartListener = iClickItemCartListener;
    }

    public void onClickButtonDelete(View view) {
        iClickItemCartListener.clickDeteteFood(view.getContext(), this, getAdapterPosition());
    }

}
