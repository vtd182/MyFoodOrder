package com.example.myfoodorder;

import android.app.Application;
import android.content.Context;

import com.example.myfoodorder.callbacks.FirebaseCallBack;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ControllerApplication extends Application {
    private static final String FIREBASE_URL = "https://foodorder-842d1-default-rtdb.firebaseio.com";
    private FirebaseDatabase mFirebaseDatabase;

    public static ControllerApplication get(Context context) {
        return (ControllerApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL);
    }

    public DatabaseReference getRestaurantDatabaseReference() {
        return mFirebaseDatabase.getReference("/restaurants");
    }

    public void getFoodsByRestaurantId(int restaurantId, final FirebaseCallBack<List<Food>> callBack) {
        // Get list of foods by restaurant id
        DatabaseReference foodDatabaseReference = mFirebaseDatabase.getReference("/foods");
        Query query = foodDatabaseReference.orderByChild("restaurantID").equalTo(restaurantId);
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                List<Food> foods = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot != null) {
                        Food food = postSnapshot.getValue(Food.class);
                        foods.add(food);
                    }
                }
                callBack.onCallBack(foods);
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                GlobalFunction.showToastMessage(getApplicationContext(), "Get list foods failed");
            }
        });
    }

    public void getRestaurantById(int restaurantId, final FirebaseCallBack<Restaurant> callBack) {
        // Get restaurant by id
        DatabaseReference restaurantDatabaseReference = mFirebaseDatabase.getReference("/restaurants");
        Query query = restaurantDatabaseReference.orderByChild("id").equalTo(restaurantId);
        query.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                Restaurant restaurant = null;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot != null) {
                        restaurant = postSnapshot.getValue(Restaurant.class);
                    }
                }
                callBack.onCallBack(restaurant);
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                GlobalFunction.showToastMessage(getApplicationContext(), "Get restaurant failed");
            }
        });
    }
}
