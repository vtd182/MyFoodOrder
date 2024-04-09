package com.example.myfoodorder.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfoodorder.models.Food;

import java.util.List;

@Dao
public interface FoodDAO {
    @Insert
    void insertFood(Food food);

    @Query("SELECT * FROM food")
    List<Food> getListFoodBag();

    @Query("SELECT * FROM food WHERE id=:id")
    List<Food> checkFoodInBag(int id);

    @Delete
    void deleteFood(Food food);

    @Update
    void updateFood(Food food);

    @Query("DELETE from food")
    void deleteAllFood();
}
