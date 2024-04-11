package com.example.myfoodorder.listeners;

import android.content.Context;

import androidx.room.Ignore;

import com.example.myfoodorder.models.Food;
import com.google.firebase.database.Exclude;

public interface IClickItemInBagListener {
    void clickDeteteFood(Context context, Food food, int position);
    void updateItemFood(Context context, Food food, int position);
}
