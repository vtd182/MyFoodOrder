package com.example.myfoodorder.viewmodels;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.ObservableField;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.database.RestaurantWithFoods;
import com.example.myfoodorder.listeners.ISendOrderSuccessListener;
import com.example.myfoodorder.models.Order;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DialogCheckoutViewModel {
    Context mContext;
    BottomSheetDialog mBottomSheetDialog;
    List<RestaurantWithFoods> listRestaurantWithFoods;
    public ObservableField<String> strName = new ObservableField<>();
    public ObservableField<String> strAddress = new ObservableField<>();
    public ObservableField<String> strPhone = new ObservableField<>();
    public ObservableField<String> paymentMethod = new ObservableField<>();
    ISendOrderSuccessListener iSendOrderSuccessListener;
    Date orderDate;

    private AtomicLong nextId = new AtomicLong(System.currentTimeMillis());


    public DialogCheckoutViewModel(Context mContext, BottomSheetDialog mBottomSheetDialog, List<RestaurantWithFoods> listRestaurantWithFoods, ISendOrderSuccessListener listener) {
        this.mContext = mContext;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.listRestaurantWithFoods = listRestaurantWithFoods;
        this.iSendOrderSuccessListener = listener;
    }

    public void onConfirmClick() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Map<String, Order> ordersMap = new HashMap<>();
        orderDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(orderDate);

        for (RestaurantWithFoods restaurantWithFoods : listRestaurantWithFoods) {
            restaurantWithFoods.setNullForListener();
            if (restaurantWithFoods.foods != null && !restaurantWithFoods.foods.isEmpty()) {
                Order order = new Order();
                order.setId(autoGenerateId());
                order.setAddress(strAddress.get());
                order.setName(strName.get());
                order.setPhone(strPhone.get());
                order.setPaymentMethod(paymentMethod.get());
                order.setOrderDate(formattedDate);
                order.setRestaurantWithFoods(restaurantWithFoods);
                order.setUserId(userId);
                order.calculateAmount();
                ordersMap.put(order.getId(), order);
                ControllerApplication.get(mContext).getOrdersDatabaseReference()
                        .child(order.getId()).setValue(order);
            }
        }
        iSendOrderSuccessListener.sendOrderSuccess();
    }
    private String autoGenerateId() {
        return String.valueOf(nextId.getAndIncrement());
    }

    public void release() {
        mContext = null;
        mBottomSheetDialog = null;
        listRestaurantWithFoods = null;
        strName = null;
        strAddress = null;
        strPhone = null;
        paymentMethod = null;
        iSendOrderSuccessListener = null;
    }
}
