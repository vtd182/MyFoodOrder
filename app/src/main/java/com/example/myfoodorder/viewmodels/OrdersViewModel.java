package com.example.myfoodorder.viewmodels;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.adapters.OrderAdapter;
import com.example.myfoodorder.adapters.RestaurantAdapter;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.models.Order;
import com.example.myfoodorder.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class OrdersViewModel {
    Context mContext;

    public ObservableList<Order> listOrders = new ObservableArrayList<>();

    public OrdersViewModel(Context mContext) {
        this.mContext = mContext;
        loadListOrdersFromDatabase();
    }


    public void release() {
        mContext = null;
    }

    public void setListOrders(ObservableList<Order> listOrders) {
        this.listOrders = listOrders;
    }

    public ObservableList<Order> getListOrders() {
        return listOrders;
    }

    public void loadListOrdersFromDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (listOrders != null || listOrders.size() > 0){
                listOrders.clear();
            }
            ControllerApplication.get(mContext).getOrdersByUserId(user.getUid(), data -> {
                        listOrders.clear();
                        listOrders.addAll(data);
                    }
            );

        }
    }

    @BindingAdapter("list_order_data")
    public static void loadListRestaurant(RecyclerView recyclerView, ObservableList<Order> list) {
        if (list == null) {
            GlobalFunction.showToastMessage(recyclerView.getContext(),
                    "Error getting order list");
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(list);
        recyclerView.setAdapter(orderAdapter);
    }
}
