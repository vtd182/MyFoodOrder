package com.example.myfoodorder.viewmodels;

import android.content.Context;
import android.database.Observable;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.R;
import com.example.myfoodorder.adapters.RestaurantAdapter;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.models.Restaurant;
import com.example.myfoodorder.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel {
    Context mContext;
    ObservableList<Restaurant> listRestaurant = new ObservableArrayList<>();

    public ObservableList<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    public void setListRestaurant(ObservableList<Restaurant> listRestaurant) {
        this.listRestaurant = listRestaurant;
    }

    public HomeViewModel(Context mContext) {
        this.mContext = mContext;
        getListRestaurantFromFirebase("");
    }

    public void release() {
        mContext = null;
    }

    private void getListRestaurantFromFirebase(String key) {
        // Get list of restaurants from Firebase
        if (mContext == null) {
            return;
        }
        ControllerApplication.get(mContext).getRestaurantDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (listRestaurant != null) {
                            listRestaurant.clear();
                        } else {
                            listRestaurant = new ObservableArrayList<>();
                        }

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                            if (restaurant == null) {
                                return;
                            }

                            if (StringUtils.isEmpty(key)) {
                                listRestaurant.add(0, restaurant);
                            } else {
                                // Search restaurant by name
                            }
                        }
                        GlobalFunction.showToastMessage(mContext, "Get list restaurant success");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listRestaurant = null;
                    }
                });
    }

    @BindingAdapter("list_restaurant_data")
    public static void loadListRestaurant(RecyclerView recyclerView, ObservableList<Restaurant> list) {
        if (list == null) {
            GlobalFunction.showToastMessage(recyclerView.getContext(),
                    "Error getting restaurant list");
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(list);
        recyclerView.setAdapter(restaurantAdapter);
    }
}
