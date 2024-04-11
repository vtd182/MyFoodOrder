package com.example.myfoodorder.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myfoodorder.models.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDAO {
    @Insert
    void insertRestaurant(Restaurant restaurant);

    @Query("SELECT * FROM restaurant")
    List<Restaurant> getAllRestaurants();

    @Query("SELECT * FROM restaurant WHERE id=:id")
    Restaurant getRestaurantById(int id);

    // if List<Restaurant> size is 0, then the restaurant is not in the database
    @Query("SELECT * FROM restaurant WHERE id=:id")
    List<Restaurant> checkRestaurant(int id);

    @Query("DELETE from restaurant")
    void deleteAllRestaurants();

    @Update
    void updateRestaurant(Restaurant restaurant);

    @Transaction
    @Query("SELECT * FROM Restaurant")
    List<RestaurantWithFoods> getRestaurantsWithFoods();
}
