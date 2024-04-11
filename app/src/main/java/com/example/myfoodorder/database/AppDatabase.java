package com.example.myfoodorder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;

@Database(entities = {Restaurant.class, Food.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "app_database.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() // Xử lý khi cần thay đổi schema
                    .build();
        }
        return instance;
    }

    public abstract RestaurantDAO restaurantDAO();
    public abstract FoodDAO foodDAO();
}
