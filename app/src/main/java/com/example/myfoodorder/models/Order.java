package com.example.myfoodorder.models;

import androidx.databinding.BaseObservable;

import java.io.Serializable;
import java.util.List;

public class Order extends BaseObservable implements Serializable {
    private int id;
    private int userId;
    private int restaurantId;
    private List<Food> foods;
    private int totalAmount;
}
