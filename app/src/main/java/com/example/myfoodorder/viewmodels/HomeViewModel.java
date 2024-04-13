package com.example.myfoodorder.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
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

import java.util.ArrayList;

public class HomeViewModel {
    Context mContext;
    ObservableList<Restaurant> listRestaurant = new ObservableArrayList<>();

    public ObservableList<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    public ObservableField<String> hint = new ObservableField<>();

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
                            if (restaurant != null) {
                                if (StringUtils.isEmpty(key)) {
                                    listRestaurant.add(0, restaurant);
                                } else {
                                    if (GlobalFunction.getTextSearch(restaurant.getName()).toLowerCase().trim()
                                            .contains(GlobalFunction.getTextSearch(key))) {
                                        listRestaurant.add(0, restaurant);
                                    }
                                }
                            }

                        }
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
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(list);
        recyclerView.setAdapter(restaurantAdapter);
    }

    public void onClickButtonSearch(EditText editText) {
        // Handle search button click
        String key = editText.getText().toString();
        if (!StringUtils.isEmpty(key)) {
            searchRestaurant(key);
        }
        GlobalFunction.hideSoftKeyboard(ActivityFromContext(mContext));

    }

    public ObservableField<String> getStringHint(EditText editText) {
        hint.set("Search");
        // Get hint text for search bar
        editText.setOnEditorActionListener((v, actionId, event) -> {
            onClickButtonSearch(editText);
            return false;
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strKey = s.toString().trim();
                if (StringUtils.isEmpty(strKey)) {
                    searchRestaurant("");
                }
            }
        });

        return hint;
    }

    private Activity ActivityFromContext(Context mContext) {
        if (mContext instanceof Activity) {
            return (Activity) mContext;
        }
        return null;
    }

    public void searchRestaurant(String key) {
        if (listRestaurant != null) {
            listRestaurant.clear();
        }
        getListRestaurantFromFirebase(key);
    }



}
