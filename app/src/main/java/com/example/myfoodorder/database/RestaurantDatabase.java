package com.example.myfoodorder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfoodorder.models.Restaurant;

@Database(entities = {Restaurant.class}, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "restaurant.db";

    private static RestaurantDatabase instance;

    public static synchronized RestaurantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RestaurantDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract RestaurantDAO restaurantDAO();
}
